package ezenweb.web.controller;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;

@RestController // @Contoller + @ResponseBody
@Slf4j //로그 기능 주입
@RequestMapping("/member")
/*@CrossOrigin(origins = "http://localhost:3000") //리소스를 교차로 지원*/
public class MemberController {
    //1. @Autowired 없이 [싱글톤]
   /*
      MemberService service = new MemberService();
      private Memberservice(){}
      public MemberService getMember(){
        return service;
      }
    */

    /*
        JSP URI 구조 => 프로젝트내 경로
	    SPRING URI 구조 => Restful API
    */
    //서버 사이드 라우팅 : 클라이언트가 서버에게 html 요청하는 방식 [ 리액트 통합 개발일경우 사용안함. ]
    /*@GetMapping("/signup")
    public Resource getSignup(){
        return new ClassPathResource("templates/member/signup.html");
    }

    @GetMapping("/login")
    public Resource getLogin(){
        return new ClassPathResource("templates/member/login.html");
    }

    @GetMapping("/update")
    public Resource getUpdate(){return new ClassPathResource("templates/member/update.html");}

    @GetMapping("/findId")
    public Resource findId(){return new ClassPathResource("templates/member/findId.html");}

    @GetMapping("/findPw")
    public Resource findPw(){return new ClassPathResource("templates/member/findPw.html");}*/

    @Autowired
    MemberService memberService;

    //1. 회원 가입 [C]
    @PostMapping("/info") // URL 같아도 HTTP 메서드가 다르므로 식별 가능
    public boolean write(@RequestBody MemberDto memberDto){ //JAVA 클래스내 메서드 이름은 중복 불가능
        log.info("member info write : {}", memberDto);
        boolean result = memberService.write(memberDto);
        return result;
    }
    //2. 회원 정보 호출 [R]
    @GetMapping("/info")
    public MemberDto info(){
        MemberDto memberDto = memberService.info();
        return memberDto;
    }

    //3. 회원정보 수정 [U]
    @PutMapping("/info")
    public boolean update(@RequestBody MemberDto memberDto) {
        log.info("member info update : {}", memberDto);
        MemberDto dto =  memberService.info();

        if (dto != null) {
            memberDto.setMno(dto.getMno());
            boolean result = memberService.update(memberDto);
            return result;
        }
        return false;
    }

    //4. 회원정보 탈퇴 [D]
    @DeleteMapping("/info")
    public boolean delete(@RequestParam String mpassword){
        System.out.println("controller에 memberpassword 들어옴 : " + mpassword);
        MemberDto memberDto = (MemberDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(memberDto);

        boolean result = false;

        if( memberDto != null && new BCryptPasswordEncoder().matches(mpassword, memberDto.getMpassword())){
            result =  memberService.delete(memberDto.getMno());
        }

       /* if(new BCryptPasswordEncoder().matches(mpassword, memberDto.getMpassword())){
            result =  memberService.delete(memberDto.getMno());
        }*/

        return result;

    }

    //5. 아이디 찾기
    @PostMapping("/find")
    public String findId(@RequestBody MemberDto memberDto){
        System.out.println("아이디 찾기 memberDto: " + memberDto);
        String result = memberService.findId(memberDto);
        return result;
    }
    
    //6. 비밀번호 찾기
    @PutMapping("/find")
    public String findPw(@RequestBody MemberDto memberDto){
        System.out.println("비밀번호 찾기 memberDto: " + memberDto);
        String result = memberService.findPw(memberDto);
        return result;
    }

    //8. 아이디 중복 검사
    @GetMapping("/find")
    public boolean findId(@RequestParam String memail){
        System.out.println("아이디들어오나?" + memail);
        boolean result = memberService.checkId(memail);
        return result;
    }

    //9. 전화번호 중복검사[하나 더 만드는 것보다 delete 안쓰는 거 쓰는게 더 나을 것 같아서 사용.]
    @DeleteMapping("/find")
    public boolean findPhone(@RequestParam String mphone){
        System.out.println("전화번호 들어오나?" + mphone);
        boolean result = memberService.checkPhone(mphone);
        return result;
    }


    // ------------------ 스프링 시큐리티 사용화 전 ------------------------
    // 스프링 시큐리티를 쓰기 때문에 아래의 코드는 사용X
/*    // 로그인
    @PostMapping("/login") //@Mapping이 다르면 URL는 같아도 된다.
    public boolean login(@RequestBody MemberDto memberDto){
        boolean result = memberService.login(memberDto);
        return result;
    }

    // 로그아웃
    @GetMapping("/logout")
    public boolean logout(){
        return memberService.logout();
    }*/
}
