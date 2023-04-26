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
            //%% : 전체를 의미함. => 전체 출력
    @Query(value="select * " +
            "from board " +
            "where if(:cno = 0 , cno like '%%', cno = :cno) and " +
            "IF(:key = '', true, " +
            "IF(:key = 'btitle', btitle like %:keyword%, bcontent like %:keyword%))"
            // IF (조건, 참, 거짓IF(조건, 참, 거짓))
            , nativeQuery = true)
    Page<BoardEntity> findBySearch(Pageable pageable, int cno, String key, String keyword); //Pageable pageable : 자동으로 검색

    //1. 동일한 cno 찾기
        //select * from board where cno = ?

    //2. 동일한 필드에서 검색어[like 연산자] 찾기
        //select * from board where btitle = ?
        //select * from board where bcontent = ?
}
