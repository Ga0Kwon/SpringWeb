package ezenweb.example.day03;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Entity 조작 인터페이스
// extends JpaRepository<entity명, PK필드 자료형>
@Repository
public interface NoteEntityRepository extends JpaRepository<NoteEntity, Integer> { //DAO 역할

}
