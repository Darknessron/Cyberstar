package ron.cyberstar.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CyberStarDto {
  private String loginId;
  private long followerCount;
  private long subscribeCount;
  private long friendCount;
}
