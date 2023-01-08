package ron.cyberstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ron.cyberstar.entity.Relationship;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

  @Modifying
  @Query(value = "insert into subscribe (cyberstar_id, subscribe_id) values (:currentUserId, :followingId)", nativeQuery = true)
  void addFollowing(long currentUserId, long followingId);

  @Modifying
  @Query(value = "delete from subscribe where cyberstar_id = :currentUserId and subscribe_id = :followingId", nativeQuery = true)
  void removeFollowing(long currentUserId, long followingId);

  @Query(value = "select count(id) from subscribe where subscribe_id = :currentUserId and cyberstar_id = :followingId", nativeQuery = true)
  int isFriend(long currentUserId, long followingId);

}
