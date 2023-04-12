package ezenweb.web.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberEntityRepository extends JpaRepository<MemberEntity, Integer> {
    // 웬만하면 카멜 표기법 쓰기!

    //1. 해당 이메일로 엔티티 찾기
        // 인수로 들어온 email과 동일한 엔티티[레코드] x 찾아서 반환
        // sql :  select * from members where memail = ?;
    Optional<MemberEntity> findByMemail(String memail);

    //2. 해당 이메일과 비밀번호가 일치한 엔티티 여부 확인
        // 인수로 들온 memail과 mpassword가 모두 일치한 엔티티[레코드] 찾아서 존재 여부 반환
        // sql :  select * from members where memail =? and mpassword =?;
    Optional<MemberEntity> findByMemailAndMpassword(String memail, String mpassword);

    // 3. [중복체크 활용] 만약에 동일한 이메일이 존재하면 true, 아니면 false
    Boolean existsByMemail(String memail);
    //4. [로그인 활용] 만약에 동일한 이메일과 패스워드가 존재하면 true, 아니면 false
    Boolean existsByMemailAndMpassword(String memail, String mpassword);

    // 아이디 찾기 [  이름과 전화번호 ]
    Optional<MemberEntity> findByMnameAndMphone(Integer mname, String mphone);

    // 비밀번호 찾기 [ 아이디와 전화번호]
        // 존재하면 임시 비밀번호 부여
    boolean findByMpasswordAndMphone(Integer mpassword, String mphone);
    
    
    // * Query 예시
    // @Query("select * from memberEntity m where m.memail =?1") // ?1 : 1첫번째 인수번호
    //MemberEntity 아이디로_엔티티_찾기(String memail);
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

        검색 여부 [true, false]
            .existsBy필드명()
            .findBy필드명And필드명()
            .findBy필드명Or필드명()

        *
            Optional 필수 X
            Optional<MemberEntity>
            
        검색된 레코드 반환

    */
}
