package ezenweb.web.domain.board;

import ezenweb.example.day06.객체관계.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardEntityRepository extends JpaRepository<BoardEntity, Integer> {

}
