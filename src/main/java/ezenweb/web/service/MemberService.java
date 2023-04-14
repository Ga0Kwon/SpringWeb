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
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.*;

//UserDetailsService : 일반 유저 서비스 구현 ---> loadUserByUsername 구현
//OAuth2UserService : oauth2 유저 서비스 구현 ----> OAuth2 유저 서비스 구현 ---> loadUser 구현
@Service // 서비스 레이어 => 빈등록
@Slf4j //로그
public class MemberService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 가공된 정보를 얻기 위해 OAuth2UserService 서비스를 구현해줘야한다.
    @Override // 토큰 결과 [JSON { 필드명 : 값, 필드명 : 값} vs Map{키 = 값, 키 = 값}
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // !!! : OAuth2User.getAttributes() map<String, Object> 구조[키 : 값]
        
        //1. 인증[로그인] 결과 토큰 확인
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();

        log.info("OAuthService : " + oAuth2UserService.loadUser(userRequest));

        //2. 서비스로 전달받은 정보 객체
        OAuth2User authUser = oAuth2UserService.loadUser(userRequest);
        log.info("회원 정보 authUser : " + authUser.getAttributes());

        //3. 클라이언트 id 식별 [ 응답된 JSON 구조 다르기 때문에 클라이언트ID별(구글vs카카오vs네이버)로 처리
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registrationId : " + registrationId);

        String email = null;
        String name = null;
        //oAtuth2User.getAttributes() map<String, Object> 구조
        if(registrationId.equals("kakao")){ //카카오
            Map<String, Object> kakao_account =  (Map<String, Object>)authUser.getAttributes().get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>)kakao_account.get("profile");

            email = (String) kakao_account.get("email");
            name = (String) profile.get("nickname");

            log.info("카카오 계정 : " + kakao_account);
        }else if(registrationId.equals("naver")){ //네이버
            Map<String, Object> response = (Map<String, Object>)authUser.getAttributes().get("response");

            email = (String) response.get("email");
            name = (String) response.get("nickname");
            log.info("네이버 계정 : " + response);
        }else if(registrationId.equals("google")){ //구글
            email = (String) authUser.getAttributes().get("email");
            // 구글의 이름 호출
            name = (String) authUser.getAttributes().get("name");

        }

        // 인가 객체 [OAuth2User ----> MemberDto 통합DTo (일반 + oAuth)]
         MemberDto memberDto = new MemberDto();
         //4.
         String authUserInfo =  userRequest
                 .getClientRegistration()
                     .getProviderDetails()
                         .getUserInfoEndpoint()
                              .getUserNameAttributeName();

        memberDto.setMemail(email);
        memberDto.setMname(name);
         log.info("authUserInfo : " + authUserInfo); //sub : 실제 회원의 식별 키
        /*   Map<String, Object> authmap = authUser.getAttributes().get(authUserInfo);*/
            // 구글의 이메일 호출

        Set<GrantedAuthority> rolesList = new HashSet<GrantedAuthority>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_user");
        rolesList.add(authority);
        memberDto.setRolesList(rolesList);

        //DB 처리 [DB에 회원 가입; 첫 방문시에만 DB등록, 두번째 방문시 부터는 DB수정]
        //1) DB 저장하기 전에 해당 이메일로 된 이메일 존재하는지 검사
       Optional<MemberEntity> entity = memberEntityRepository.findByMemail(email);

        if (!entity.isPresent()) {//첫 방문시
            memberDto.setMrole("oauthuser");
            memberEntityRepository.save(memberDto.toEntity());
        }else{ // 두번째 방문 시 수정 처리
            entity.get().setMname(name); //이메일은 바꿀일이 없기 때문에 이름만 수정
        }
        return memberDto; //rolesList OAuth2를 구현했으니까 return 가능
    }

    // [ 스프링 시큐리티 적용했을 때 사용되는 로그인 메서드] => 일반 로그인
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
        //2) 권한 객체 만들기[DB 존재하는 권한명(ROLE_!!!) 으로]
            // 권한 없을 경우 : ROLE_ANONYMOUS / 권한이 있을 경우 ROLE_권한명 : ROLE_admin, ROLE_user
        SimpleGrantedAuthority user = new SimpleGrantedAuthority("ROLE_"+entity.get().getMrole());
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

    // 1-0 아이디 죽복 검사
    public boolean checkId(String memail){
        boolean result = memberEntityRepository.existsByMemail(memail);
        return !result;
    }
    @Transactional //수정은 필수!!! [Commit]
    //3. 화원 수정
    public boolean update(MemberDto memberDto){
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById(memberDto.getMno());

        if(entityOptional.isPresent()){ //포장안의 정보가 들어있으면
            MemberEntity entity = entityOptional.get();

            // 기존 값 그대로

            //setter을 이용한 수정
            entity.setMname(memberDto.getMname());
            entity.setMphone(memberDto.getMphone());
            
            return true;
        }
        return false;
    }
    @Transactional
    //4.회원 삭제
    public boolean delete(int mno){
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById(mno);
        System.out.println("서비스에 mno 들어옴 : " + mno);
        if(entityOptional.isPresent()){ //포장안의 정보가 들어 있고,
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

    // 아이디 찾기
    public String findId(MemberDto memberDto){
        Optional<MemberEntity> optionalMemberEntity = memberEntityRepository.findByMnameAndMphone(memberDto.getMname(), memberDto.getMphone());

        if(optionalMemberEntity.isPresent()){
            return optionalMemberEntity.get().getMemail();
        }
        return null;
    }
    // 비밀번호를 만들기 위한 아스키 범위값
    @Transactional
    // 비밀번호 난수로 제공
    public String findPw(MemberDto memberDto){
        System.out.println("비밀번호 인증 전" + memberDto.toString());
        boolean result = memberEntityRepository.existsByMemailAndMphone(memberDto.getMemail(), memberDto.getMphone());
        String charStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^";

        String newPwd = "";

        if(result){
            Optional<MemberEntity> memberEntityOptional = memberEntityRepository.findByMemail(memberDto.getMemail());
            System.out.println("memberService 비밀번호 찾기 : "+ memberEntityOptional.get());

            if(memberEntityOptional.isPresent()){
                MemberEntity entity = memberEntityOptional.get();

                for(int i = 0; i < 8; i++){
                    Random random = new Random();
                    //ranStr 문자열에서 0인덱스 ~ 마지막 인덱스의 난수 인덱스 만들기
                    int index = random.nextInt(charStr.length()); //0번 인덱스부터 마지막 인덱스[인덱스를 난수로 가져옴]
                    newPwd += charStr.charAt(index);

                }
                System.out.println("새로운 비밀번호 : " + newPwd);

                entity.setMpassword(passwordEncoder.encode(newPwd));

                return newPwd;
            }
        }
        return newPwd;
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
