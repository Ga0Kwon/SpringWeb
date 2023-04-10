package ezenweb.web.service;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service // 서비스 레이어 => 빈등록
public class MemberService {
    //서비스는 매핑하는 것이 아니니까, @어노테이션 뺌
    //  @Transactional : entity가 setter을 쓴다 그러면 필수!
    @Autowired
    MemberEntityRepository memberEntityRepository;
    @Transactional
    // 1. 회원 가입
    public boolean write(MemberDto memberDto){
        MemberEntity entity = memberEntityRepository.save(memberDto.toEntity());
        if(entity.getMno() > 0 ){
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
