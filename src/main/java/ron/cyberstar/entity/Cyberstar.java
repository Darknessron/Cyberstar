package ron.cyberstar.entity;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cyberstar {

  @Id
  @Column(name = "CyberstarId")
  private long id;
  private String loginId;
  private Long followerCount;
  private Long subscribeCount;
  private Long friendCount;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "Follower", joinColumns = {@JoinColumn(table = "Follower", name = "CyberstarId", referencedColumnName = "CyberstarId")})
  private List<Cyberstar> followers;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "Friend", joinColumns = {@JoinColumn(table = "Follower", name = "CyberstarId", referencedColumnName = "CyberstarId")})
  private List<Cyberstar> friends;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "Subscribed", joinColumns = {@JoinColumn(table = "Follower", name = "CyberstarId", referencedColumnName = "CyberstarId")})
  private List<Cyberstar> subscribed;


}
