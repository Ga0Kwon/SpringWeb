package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

// 스프링 관리하는 IoC 컨테이너에 빈[객체] 등록
@Controller //서버로부터 HTTP 요청이 왔을때 해당 클래스로 핸들러(컨트롤) 매핑(연결)
@Slf4j // 스프링 로그 메서드 제공 [레벨 : trace < debug < info < warn < error]
public class MappingController {

    @RequestMapping(value = "/orange", method = RequestMethod.GET) //요청
    @ResponseBody
    public String getOrange(){
        log.info("클라이언트로 부터 getOrange메소드 요청청이 들어옴");
        return "정상응답"; //@ResponseBody넣으면 return 값을 응답으로 보냄, @ResponseBody안넣으면 응답 못함
    }

    @RequestMapping(value = "/orange", method = RequestMethod.POST)
    @ResponseBody
    public String postOrange(){
        log.info("클라이언트로 부터 postOrange메소드 요청이 들어옴");
        return "정상응답";
    }

    @RequestMapping(value = "/orange", method = RequestMethod.PUT)
    @ResponseBody
    public String putOrange(){
        log.info("클라이언트로 부터 putOrange메소드 요청이 들어옴");
        return "정상 응답";
    }
    @RequestMapping(value = "/orange", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteOrange(){
        log.info("클라이언트로 부터 deleteOrange메소드 요청이 들어옴");
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
