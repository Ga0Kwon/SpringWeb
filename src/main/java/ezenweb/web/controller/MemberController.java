package ezenweb.web.controller;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;

@RestController // @Contoller + @ResponseBody
@Slf4j //로그 기능 주입
@RequestMapping("/member")
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
    @GetMapping("/signup")
    public Resource getSignup(){
        return new ClassPathResource("templates/member/signup.html");
    }

    @GetMapping("/login")
    public Resource getLogin(){
        return new ClassPathResource("templates/member/login.html");
    }

    @GetMapping("/update")
    public Resource getUpdate(){return new ClassPathResource("templates/member/update.html");}
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
    public boolean update(@RequestBody MemberDto memberDto){
        log.info("member info update : {}", memberDto);
        boolean result = memberService.update(memberDto);
        return result;
    }
    //4. 회원정보 탈퇴 [D]
    @DeleteMapping("/info")
    public boolean delete(@RequestParam int mno){
        log.info("member info delete : {}", mno);
        boolean result =  memberService.delete(mno);
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
