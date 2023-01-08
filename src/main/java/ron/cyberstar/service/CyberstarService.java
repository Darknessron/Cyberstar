package ron.cyberstar.service;

import java.util.List;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ron.cyberstar.dto.CyberStarDto;
import ron.cyberstar.entity.Cyberstar;
import ron.cyberstar.vo.PagedResult;

public interface CyberstarService {

  CyberStarDto getInfo(String loginId);

  void subscribe(long currenUserId, String loginId) throws NotFoundException, DuplicateKeyException;
  void unsubscribe(long currenUserId, String loginId) throws NotFoundException;

  PagedResult getFollowers(String loginId, int index, int pageSize);

  PagedResult getFollowings(String loginId, int index, int pageSize);

  PagedResult getFriends(String loginId, int index, int pageSize);

}
