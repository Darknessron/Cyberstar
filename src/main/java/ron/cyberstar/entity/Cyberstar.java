package ron.cyberstar.entity;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cyberstar {

  @Id
  @Column(name = "cyberstar_id")
  private long id;
  private String loginId;
  private String name;

  @OneToOne
  @JoinColumn(name = "cyberstar_id", referencedColumnName = "cyberstar_id")
  private Relationship relationship;

}
