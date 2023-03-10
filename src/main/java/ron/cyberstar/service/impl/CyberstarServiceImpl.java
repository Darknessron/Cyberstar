package ron.cyberstar.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import javax.persistence.LockModeType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ron.cyberstar.dto.CyberStarDto;
import ron.cyberstar.entity.Cyberstar;
import ron.cyberstar.entity.Relationship;
import ron.cyberstar.repository.CyberstarRepository;
import ron.cyberstar.repository.RelationshipRepository;
import ron.cyberstar.service.CyberstarService;
import ron.cyberstar.vo.PagedResult;

@Service
@AllArgsConstructor
@Slf4j
public class CyberstarServiceImpl implements CyberstarService {

  private final CyberstarRepository cyberstarRepository;
  private final RelationshipRepository relationshipRepository;

  /**
   * @param loginId
   * @return
   */
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public CyberStarDto getInfo(String loginId) {
    Optional<Cyberstar> opt = cyberstarRepository.findByLoginId(loginId);
    if (opt.isEmpty()) {
      return null;
    }
    Cyberstar cyberstar = opt.get();

    CyberStarDto dto = CyberStarDto.builder().loginId(cyberstar.getLoginId())
        .name(cyberstar.getName())
        .followerCount(cyberstar.getRelationship().getFollowerCount())
        .friendCount(cyberstar.getRelationship().getFriendCount())
        .followingCount(
            cyberstar.getRelationship().getFollowingCount()).build();
    return dto;
  }

  /**
   * @param currenUserId
   * @param loginId
   * @return
   */
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
  public void subscribe(long currenUserId, String loginId)
      throws NotFoundException, DuplicateKeyException {

    boolean isFriend = false;

    log.debug("Thread {} start", Thread.currentThread().getName());

    Optional<Cyberstar> starOpt = cyberstarRepository.findByLoginId(loginId);
    if (starOpt.isEmpty()) {
      log.error("subscribe target not found, subscribe login id:{}", loginId);
      throw new NotFoundException("subscribe target not found");
    }
    Optional<Cyberstar> currentUserOpt = cyberstarRepository.findById(currenUserId);
    if (currentUserOpt.isEmpty()) {
      log.error("current user not found, current user id:{}", currenUserId);
      throw new NotFoundException("current user not found");
    }
    Cyberstar star = starOpt.get();
    Cyberstar currentUser = currentUserOpt.get();

    int f = relationshipRepository.isFriend(currenUserId, star.getId());
    isFriend = f > 0;

    Relationship currentUserRel = currentUser.getRelationship();
    Relationship followingRel = star.getRelationship();

    relationshipRepository.addFollowing(currenUserId, star.getId());

    currentUserRel.setFollowingCount(currentUserRel.getFollowingCount() + 1);
    followingRel.setFollowerCount(followingRel.getFollowerCount() + 1);

    if (isFriend) {
      currentUserRel.setFriendCount(currentUserRel.getFriendCount() + 1);
      followingRel.setFriendCount(followingRel.getFriendCount() + 1);
    }
    try {
      relationshipRepository.save(currentUserRel);
      relationshipRepository.save(followingRel);

    } catch (DataIntegrityViolationException ex) {

      log.error("Duplicate subscription, follower id: {} following id: {}", currenUserId,
          star.getId());
      throw new DuplicateKeyException(
          String.format("Duplicate subscription, follower id: %1s  following id: %2s ",
              currenUserId, star.getId()));


    }
    log.debug("Thread {} end", Thread.currentThread().getName());

  }

  /**
   * @param currenUserId
   * @param loginId
   * @return
   */
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
  public void unsubscribe(long currenUserId, String loginId) throws NotFoundException {
    Optional<Cyberstar> starOpt;
    Optional<Cyberstar> currentUserOpt;
    Cyberstar star;
    Cyberstar currentUser;

    boolean isFriend = false;

    starOpt = cyberstarRepository.findByLoginId(loginId);
    if (starOpt.isEmpty()) {
      log.error("subscribe target not found, subscribe login id:{}", loginId);
      throw new NotFoundException("subscribe target not found");
    }
    currentUserOpt = cyberstarRepository.findById(currenUserId);
    if (currentUserOpt.isEmpty()) {
      log.error("current user not found, current user id:{}", currenUserId);
      throw new NotFoundException("current user not found");
    }
    star = starOpt.get();
    currentUser = currentUserOpt.get();

    int f = relationshipRepository.isFriend(currenUserId, star.getId());
    isFriend = f > 0;

    relationshipRepository.removeFollowing(currenUserId, star.getId());

    Relationship followingRel = star.getRelationship();
    Relationship currentUserRel = currentUser.getRelationship();

    followingRel.setFollowerCount(followingRel.getFollowerCount() - 1);
    currentUserRel.setFollowingCount(currentUserRel.getFollowingCount() - 1);

    if (isFriend) {
      followingRel.setFriendCount(followingRel.getFriendCount() - 1);
      currentUserRel.setFriendCount(currentUserRel.getFriendCount() - 1);
    }

    relationshipRepository.save(currentUserRel);
    relationshipRepository.save(followingRel);

  }

  /**
   * @param loginId
   * @param index
   * @param pageSize
   * @return
   */
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  @Lock(LockModeType.READ)
  public PagedResult getFollowers(String loginId, int index, int pageSize) {
    PageRequest pageRequest = PageRequest.of(index, pageSize);
    Page<Cyberstar> stars;
    PagedResult result;

    stars = cyberstarRepository.findFollowersByLoginId(loginId, pageRequest);
    result = PagedResult.builder().index(index).pageSize(pageSize).total(
            stars.getTotalElements())
        .name(stars.stream().map(c -> c.getName()).collect(Collectors.toList())).build();

    return result;
  }

  /**
   * @param loginId
   * @param index
   * @param pageSize
   * @return
   */
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  @Lock(LockModeType.READ)
  public PagedResult getFollowings(String loginId, int index, int pageSize) {
    PageRequest pageRequest = PageRequest.of(index, pageSize);
    Page<Cyberstar> stars;
    PagedResult result;

    stars = cyberstarRepository.findFollowingsByLoginId(loginId, pageRequest);
    result = PagedResult.builder().index(index).pageSize(pageSize).total(
            stars.getTotalElements())
        .name(stars.stream().map(c -> c.getName()).collect(Collectors.toList())).build();

    return result;
  }

  /**
   * @param loginId
   * @param index
   * @param pageSize
   * @return
   */
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  @Lock(LockModeType.READ)
  public PagedResult getFriends(String loginId, int index, int pageSize) {
    PageRequest pageRequest = PageRequest.of(index, pageSize);
    Page<Cyberstar> stars;
    PagedResult result;

    stars = cyberstarRepository.findFriendsByLoginId(loginId, pageRequest);
    result = PagedResult.builder().index(index).pageSize(pageSize).total(
            stars.getTotalElements())
        .name(stars.stream().map(c -> c.getName()).collect(Collectors.toList())).build();

    return result;
  }

}
