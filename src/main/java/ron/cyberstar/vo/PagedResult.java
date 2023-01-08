package ron.cyberstar.vo;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagedResult {

  private long total;
  private int pageSize;
  private int index;
  private List<String> name;

}
