package ron.cyberstar.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ron.cyberstar.entity.Cyberstar;

@Repository
public interface CyberstarRepository extends JpaRepository<Cyberstar, Long> {

  Optional<Cyberstar> findByLoginId(String loginId);
}
