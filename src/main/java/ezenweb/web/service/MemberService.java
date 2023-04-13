package ezenweb.web.service;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service // 서비스 레이어 => 빈등록
@Slf4j //로그
public class MemberService implements UserDetailsService {

    // [ 스프링 시큐리티 적용했을 때 사용되는 로그인 메서드]
    @Override
    public UserDetails loadUserByUsername(String memail) throws UsernameNotFoundException {
        //1. UserDetailService 인터페이스 구현
        //2. LoadUserByUsername() 메서드 : 아이디 검증
            // 패스워드 검증 [ 시큐리티 자동]
        Optional<MemberEntity> entity = memberEntityRepository.findByMemail(memail);
        if(entity.get() == null){
            return null;
        }
        MemberDto dto = entity.get().toDto();

            // dto에 권한 넣어주기
            // dto의 권한  public Collection<? extends GrantedAuthority> getAuthorities() {} : 자료형이 다르다
        //1) 권한 목록 만들기
        Set<GrantedAuthority> roleList = new HashSet<>();
        //SimpleGrantedAuthority 권한명 = new SimpleGrantedAuthority("권한명");
        //2) 권한 객체 만들기
        SimpleGrantedAuthority user = new SimpleGrantedAuthority(entity.get().getMrole());
        //3) 만든 권한 객체를 권한 목록[컬렉션]에 추가
        roleList.add(user);
        //4) UserDetails 에 권한 목록 대입
        dto.setRolesList(roleList);

        log.info("dto : " + dto);
        //3. 검증 후 세션에 저장할 DTO 반환
        return dto; // UserDetials : 원본 데이터의 검증할 계정, 패스워드 포함
    }

    //서비스는 매핑하는 것이 아니니까, @어노테이션 뺌
    //  @Transactional : entity가 setter을 쓴다 그러면 필수!
    @Autowired
    MemberEntityRepository memberEntityRepository;

    @Autowired
    private HttpServletRequest request;

    @Transactional
    // 1. 일반 회원 가입 [본 애플리케이션에서 가입한 회원]
    public boolean write(MemberDto memberDto){

        // 스프링 시큐리티에서 제공하는 암호화[사람이 이해하기 어렵고 컴퓨터는 이해할 수 있는 단어] 사용하기
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        log.info("비코더 암호화 사용 : " + passwordEncoder.encode("qwe"));
        //입력받은 [DTO] 패스워드 암호화 해서 다시 DTO에 저장
        memberDto.setMpassword(passwordEncoder.encode(memberDto.getMpassword()));
        // Encoder : 암호화(특정 형식으로 변경) / decoder : 복호화(원본으로 되돌리기)

        // 등급 부여
        memberDto.setMrole("user");

        MemberEntity entity = memberEntityRepository.save(memberDto.toEntity());
        if(entity.getMno() > 0 ){
            return true;
        }
        return false;
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


    @Transactional
    //2. 회원정보 [세션에 존재하는 회원 번호]
    public MemberDto info(){
        // 1. 시큐리티 인증[로그인] 된 UserDetails 객체[세션]으로 관리 했을 때 [Spring]
       // SecurityContextHolder() : 시큐리티 정보 저장소
        //SecurityContextHolder().getContext() : 시큐리티 저장된 정보 호출
        //SecurityContextHolder.getContext().getAuthentication(); : 인증 전체 정보 호출

        log.info("Auth : " + SecurityContextHolder.getContext().getAuthentication());

        // 인증된 회원의 정보 호출
        log.info("Principal : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());

       Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       if(o.equals("anonymousUser")){ //로그인 안했다는 것
           return null;
       }
        //[Principal]
        // 인증 실패시 : anonymousUser
        // 인증 성공시 : 회원 DTO
       //인증 된 객체내 회원 정보[principal] 타입 변환
        return (MemberDto) o; //MemberDt로 타입 변환 [부모(object) <-> 자식(MemberDto)]


        // 일반 세션으로
 /*       //이메일이 존재
        String memail = (String)request.getSession().getAttribute("login");
        System.out.println("이메일 찾는다 : " + memail);
        if(memail != null){
            MemberEntity entity = memberEntityRepository.findByMemail(memail).get();
            return entity.toDto();
        }*/
        
    }

    // *** 로그인 [ 시큐리티 사용했을때]
    // 시큐리티는 API [누군가 미리 만들어진 메서드 안에서 커스터마이징[수정]

    //5. 로그인
    /*@Transactional
    public boolean login(MemberDto memberDto){
        //1. 이메일로 엔티티 찾기
        Optional<MemberEntity> entity = memberEntityRepository.findByMemail(memberDto.getMemail());
        log.info("entity : "+ entity);

        //2. 찾은 엔티티 안에는 암호화된 패스워드
            // 엔티티 안에 있는 패스워드와 입력받은 패스워드와 비교
        // 2. 패스워드 검증
        //엔티티 안에 있는 입력받은 패스워드[안된 상태]와 패스워드[암호화된 상태] 비교
        if(new BCryptPasswordEncoder().matches(memberDto.getMpassword(), entity.get().getMpassword())){
            // == 스택 메모리 내 데이터 비교
            // .equeals 힙 메모리 비교
            // matches() : 문자열 주어진 패턴 포함 동일 여부 체크
            //세션 사용 : 메서드 밖 필드에 @Autowired private HttpServletRequest request;
            request.getSession().setAttribute("login", entity.get().getMemail());
            return true;
        }

        //2. 입력받은 이메일과 패스워드가 동일한지
        *//*
        Optional<MemberEntity> result = memberEntityRepository.findByMemailAndMpassword(memberDto.getMemail(), memberDto.getMpassword());
        log.info("result : "+ result);
        if(result.isPresent()){ //존재하면 로그인 성공이라는 말
            request.getSession().setAttribute("login", result.get().getMno());
            return true;
        }*//*

        //3. 계정이 있는지 // 못씀
    *//*    boolean result = memberEntityRepository.existsByMemailAndMpassword(memberDto.getMemail(), memberDto.getMpassword());
        if(result == true){
            request.getSession().setAttribute("login", memberDto.getMemail());
            return true;
        }*//*
        return false;
    }

    //7. [세션에 존재하는 정보 제거] 로그아웃
    @Transactional
    public boolean logout(){
        request.getSession().setAttribute("login", null);
        return true;
    }*/


}
