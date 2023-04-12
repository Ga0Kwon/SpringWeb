package ezenweb.web.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberEntityRepository extends JpaRepository<MemberEntity, Integer> {
    // 웬만하면 카멜 표기법 쓰기!

    //1. 해당 이메일로 엔티티 찾기
        // 인수로 들어온 email과 동일한 엔티티[레코드]
        // sql :  select * from members where memail = ?;
    //Optional<MemberEntity> findByMemail(String memail);

    //2. 해당 이메일과 비밀번호가 일치한 엔티티 여부 확인
        // 인수로 들온 memail과 mpassword가 모두 일치한 엔티티[레코드] 찾아서 존재 여부 반환
        // sql :  select * from members where memail =? and mpassword =?;
    Optional<MemberEntity> findByMemailAndMpassword(String memail, String mpassword);

    /*
        .findById(pk 값) : 해당하는 pk값이 검색 후 존재하면 레코드[엔티티] 반환
        .findAll() :  모든 레코드[엔티티] 반환
        .save(엔티티) : 해당 엔티티를 DB 레코드에서 insert
        .delete(엔티티) : 해당 엔티티를 DB레코드에서 delete
        @Transactional : setter 메서드를 이용하여 수정!

        ------------- 그외 추가 메서드 사용 만들기
        검색 [ 레코드 반환 ]
          => findBy필드명(인수) : select * from members where memail = ?;
          => find필드명And필드명() : select * from members where memail =? and mpassword =?;
          => findBy필드명or필드명() : select * from members where memail =? or mpassword =?;
          => <Optional> : NULL과 값을 감싸서, NullPointerException으로 부터 부담을 줄임 => 웬만하면 넣기!
    */
}
