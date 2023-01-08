package ron.cyberstar.controller;

import javassist.NotFoundException;
import javax.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ron.cyberstar.dto.CyberStarDto;
import ron.cyberstar.service.CyberstarService;
import ron.cyberstar.vo.PagedResult;

@RestController
@RequestMapping("/cyberstar")
@AllArgsConstructor
@Slf4j
public class CyberstarController {

  private final CyberstarService cyberstarService;

  @GetMapping(value = "/{loginId}/info", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CyberStarDto> getCyberstar(@PathVariable("loginId") String loginId) {
    log.debug("invoke getCyberstar, loginId: {}", loginId);
    CyberStarDto result = cyberstarService.getInfo(loginId);
    if (result ==  null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(result);
  }
  @PostMapping(value = "/{loginId}/subscribe")
  public ResponseEntity<Object> subscribe(@PathVariable("loginId") String loginId, @RequestParam("currentUserId") long currentUserId)  {
    log.debug("invoke subscribe, following loginId:{}, follower id:{}", loginId, currentUserId);
    try {
      cyberstarService.subscribe(currentUserId, loginId);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (DuplicateKeyException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    return ResponseEntity.ok(true);
  }

  @DeleteMapping(value = "/{loginId}/unsubscribe")
  public ResponseEntity<Boolean> unsubscribe(@PathVariable("loginId") String loginId, @RequestParam("currentUserId") long currentUserId)  {
    log.debug("invoke unsubscribe, following loginId:{}, follower id:{}", loginId, currentUserId);
    try {
      cyberstarService.unsubscribe(currentUserId, loginId);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(true);
  }

  @GetMapping(value = "/{loginId}/follower", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResult> getFollower(@PathVariable("loginId") String loginId, @PathParam("index") int index, @PathParam("pageSize") int pageSize) {
    log.debug("invoke getFollower, loginId:{}, index:{}, pageSize:{}", loginId, index, pageSize);
    PagedResult result = cyberstarService.getFollowers(loginId, index, pageSize);
    return ResponseEntity.ok(result);
  }

  @GetMapping(value = "/{loginId}/following", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResult> getFollowing(@PathVariable("loginId") String loginId, @PathParam("index") int index, @PathParam("pageSize") int pageSize) {
    log.debug("invoke getFollowing, loginId:{}, index:{}, pageSize:{}", loginId, index, pageSize);
    PagedResult result = cyberstarService.getFollowings(loginId, index, pageSize);
    return ResponseEntity.ok(result);
  }

  @GetMapping(value = "/{loginId}/friend", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResult> getFriends(@PathVariable("loginId") String loginId, @PathParam("index") int index, @PathParam("pageSize") int pageSize) {
    log.debug("invoke getFriends, loginId:{}, index:{}, pageSize:{}", loginId, index, pageSize);
    PagedResult result = cyberstarService.getFriends(loginId, index, pageSize);
    return ResponseEntity.ok(result);
  }
}
