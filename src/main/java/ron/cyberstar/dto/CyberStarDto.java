package ron.cyberstar.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CyberStarDto {
  private String loginId;
  private String name;
  private long followerCount;
  private long followingCount;
  private long friendCount;
}
