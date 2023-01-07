package ron.cyberstar.controller;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ron.cyberstar.dto.CyberStarDto;
import ron.cyberstar.entity.Cyberstar;
import ron.cyberstar.repository.CyberstarRepository;

@RestController
@RequestMapping("/cyberstar")
@AllArgsConstructor
public class CyberstarController {

  private final CyberstarRepository cyberstarRepository;

  @GetMapping(value = "/{loginId}/info", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CyberStarDto> getCyberstar(@PathVariable("loginId") String loginId) {
    Optional<Cyberstar> opt = cyberstarRepository.findByLoginId(loginId);
    if (opt.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Cyberstar cyberstar = opt.get();

    CyberStarDto dto = CyberStarDto.builder().loginId(cyberstar.getLoginId())
        .followerCount(cyberstar.getFollowerCount()).friendCount(cyberstar.getFriendCount())
        .subscribeCount(
            cyberstar.getSubscribeCount()).build();

    return ResponseEntity.ok(dto);
  }
}
