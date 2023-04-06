package ezenweb.example.day04.domain.entity.proudct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//엔티티 조작 인터페이스
@Repository
//ProductEntity 를 조작할것인데, Pk의 자료형은 Integer JpaRepository<ProductEntity, Integer>
public interface ProductEntityRepository  extends JpaRepository<ProductEntity, Integer> {
}
