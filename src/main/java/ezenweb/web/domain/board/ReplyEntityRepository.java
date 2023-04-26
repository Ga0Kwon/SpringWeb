package ezenweb.web.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyEntityRepository extends JpaRepository<ReplyEntity, Integer> {
    @Query(value="select * " +
            "from reply " +
            "where bno = :bno)"
            // IF (조건, 참, 거짓IF(조건, 참, 거짓))
            , nativeQuery = true)
    Page<ReplyEntity> findBySearch(Pageable pageable, int bno);

}
