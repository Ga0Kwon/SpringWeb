package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

// 스프링 관리하는 IoC 컨테이너에 빈[객체] 등록
@RestController // @ResponseBody + @Controller => @ResponseBody 생략가능
@Slf4j // 스프링 로그 메서드 제공 [레벨 : trace < debug < info < warn < error]

public class MappingController3 {

    @GetMapping("/green")
    public String getGreen(){
        log.info("클라이언트로 부터 getGreen() 요청이 들어옴");
        return "정상응답"; //@ResponseBody넣으면 return 값을 응답으로 보냄, @ResponseBody안넣으면 응답 못함
    }

    @PostMapping("/green")
    public String postGreen(){
        log.info("클라이언트로 부터 postGreen() 요청이 들어옴");
        return "정상응답";
    }

   @PutMapping("/green")
    public String putGreen(){
        log.info("클라이언트로 부터 putGreen() 요청이 들어옴");
        return "정상 응답";
    }
    @DeleteMapping("/green")
    public String deleteGreen(){
        log.info("클라이언트로 부터 deleteGreen() 요청이 들어옴");
        return "정상 응답";
    }

}

/*

    스프링 부트 동작 구조
    
    크롬/ajax/js --------------- 요청 ----------------> 서블릿 컨테이너 --------> Dispatcher Servlet
            http://localhost:8080/orange                                     핸들러 매핑으로 해당 컨트롤러(스프링 빈 등록) 검색
                                                                    --------> Mapping 검색
                                                                              @RequestMapping(value = "/orange", method = RequestMethod.GET)

                <---------------------------- 응답 ---------------------------
*/
