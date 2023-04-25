package ezenweb.web.domain.board;

import ezenweb.example.day06.객체관계.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
public interface BoardEntityRepository extends JpaRepository<BoardEntity, Integer> {

    //JPA 형식이 아닌 순수 SQL 적용하는 함수 정의[nativeQuery : 순수 SQL]
        //동일한 cno 찾기
        //[JSP]select * from board where cno = ?
            //ps.setInt(1, cno)
        //[JPA] select * from board where cno = :cno(:매개변수명);
            //:(콜론) : 해당 메소드의 매개변수 이름
    @Query(value="select * " +
            "from board " +
            "where cno = :cno"
            , nativeQuery = true)
    Page<BoardEntity> findBySearch(Pageable pageable, int cno); //Pageable pageable : 자동으로 검색

}
