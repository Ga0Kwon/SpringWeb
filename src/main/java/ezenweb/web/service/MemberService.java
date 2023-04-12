package ezenweb.web.service;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.Optional;

@Service // 서비스 레이어 => 빈등록
@Slf4j //로그
public class MemberService {
    //서비스는 매핑하는 것이 아니니까, @어노테이션 뺌
    //  @Transactional : entity가 setter을 쓴다 그러면 필수!
    @Autowired
    MemberEntityRepository memberEntityRepository;

    @Autowired
    private HttpServletRequest request;

    @Transactional
    // 1. 회원 가입
    public boolean write(MemberDto memberDto){
        MemberEntity entity = memberEntityRepository.save(memberDto.toEntity());
        if(entity.getMno() > 0 ){
            return true;
        }
        return false;
    }

    //5. 로그인 [시큐리티 사용 하기 전]
    @Transactional
    public boolean login(MemberDto memberDto){
        //1. 이메일로 엔티티 찾기
  /*      Optional<MemberEntity> entity = memberEntityRepository.findByMemail(memberDto.getMemail());
        log.info("entity : "+ entity);

        // 2. 패스워드 검증
        if(entity.get().getMpassword().equals(memberDto.getMpassword())){
            // == 스택 메모리 내 데이터 비교
            // .equeals 힙 메모리 비교
            // matches() : 문자열 주어진 패턴 포함 동일 여부 체크
            //세션 사용 : 메서드 밖 필드에 @Autowired private HttpServletRequest request;
            request.getSession().setAttribute("login", entity.get().getMno());
            return true;
        }*/

        //2. 입력받은 이메일과 패스워드가 동일한지
        Optional<MemberEntity> result = memberEntityRepository.findByMemailAndMpassword(memberDto.getMemail(), memberDto.getMpassword());
        log.info("result : "+ result);
        if(result.isPresent()){ //존재하면 로그인 성공이라는 말
            request.getSession().setAttribute("login", result.get().getMno());
            return true;
        }
        
        return false;
    }



    @Transactional
    //2. 회원정보
    public MemberDto info(int mno){

       //Optional : 포장지 [ null값을 참조하는 에러를 회피하기 위해 사용, 해당 Id(PK)에 값이 없을 수도 있음]
       Optional<MemberEntity> entityOptional = memberEntityRepository.findById(mno);

        if(entityOptional.isPresent()){ //포장안의 정보가 들어있으면
            return entityOptional.get().toDto();
        }

        return null;
    }

    @Transactional //수정은 필수!!! [Commit]
    //3. 화원 수정
    public boolean update(MemberDto memberDto){
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById(memberDto.getMno());

        if(entityOptional.isPresent()){ //포장안의 정보가 들어있으면
            MemberEntity entity = entityOptional.get();

            //setter을 이용한 수정
            entity.setMrole(memberDto.getMrole());
            entity.setMname(memberDto.getMname());
            entity.setMphone(memberDto.getMphone());
            entity.setMpassword(memberDto.getMpassword());
            
            return true;
        }
        return false;
    }
    @Transactional
    //4.회원 삭제
    public boolean delete(int mno){
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById(mno);

        if(entityOptional.isPresent()){ //포장안의 정보가 들어있으면
            memberEntityRepository.delete(entityOptional.get()); //삭제하려는 entity를 삭제
            return true;
        }
        
        return false;
    }
}
