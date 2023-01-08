package ron.cyberstar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Relationship {

  @Id
  @Column(name = "cyberstar_id")
  private long cyberstarId;
  private long followerCount;
  private long followingCount;
  private long friendCount;

  @Version
  private long version;

}
