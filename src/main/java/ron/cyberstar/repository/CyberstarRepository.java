package ron.cyberstar.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ron.cyberstar.entity.Cyberstar;

@Repository
public interface CyberstarRepository extends JpaRepository<Cyberstar, Long> {

  Optional<Cyberstar> findByLoginId(String loginId);

  @Query(value = "select a.name, a.cyberstar_id, a.login_id from cyberstar as a where a.cyberstar_id in (select b.cyberstar_id from subscribe as b where b.subscribe_id = (select c.cyberstar_id from cyberstar as c where c.login_id = :loginId)) order by a.cyberstar_id", nativeQuery = true)
  Page<Cyberstar> findFollowersByLoginId(String loginId, Pageable pageable);

  @Query(value = "select a.name, a.cyberstar_id, a.login_id from cyberstar as a where a.cyberstar_id in (select b.subscribe_id from subscribe as b where b.cyberstar_id = (select c.cyberstar_id from cyberstar as c where c.login_id = :loginId)) order by a.cyberstar_id", nativeQuery = true)
  Page<Cyberstar> findFollowingsByLoginId(String loginId, Pageable pageable);

  @Query(value = "select a.name, a.cyberstar_id, a.login_id from cyberstar as a where a.cyberstar_id in (select b.cyberstar_id from subscribe as b where b.cyberstar_id in (select c.subscribe_id from subscribe as c where c.cyberstar_id = (select cyberstar_id from cyberstar where login_id = :loginId)) and subscribe_id = (select cyberstar_id from cyberstar where login_id = :loginId))", nativeQuery = true)
  Page<Cyberstar> findFriendsByLoginId(String loginId, Pageable pageable);
}
