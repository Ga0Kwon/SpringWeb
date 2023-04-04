package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController// @ResponseBody + @Controller
@Slf4j //로그
@RequestMapping("/get")
public class GetMappingController {
    // ------------ 매개 변수 요청 ------------ //

    //1. HttpServletRequest request 객체를 이용한 매개변수 요청 [JSP 주로 사용]
    @GetMapping("/medthod1") // 쿼리 스트링 http://localhost:8080/get/medthod1?param1=value1
    public String medthod1(HttpServletRequest request) {
        String params = request.getParameter("param1");
        log.info("클라이언트로부터 받은 변수 : " + params);
        return "받은 데이터 그대로 응답 : " + params;
    }

    //2. @ PathVariable : URL 경로상의 변수 요청
    @GetMapping("/medthod2/{param1}/{param2}")   //경로상 : http://localhost:8080/get/medthod2/param1/param2
    public String medthod2(@PathVariable String param1, @PathVariable String param2) {
        log.info("클라이언트로부터 받은 변수 : " + param1 + " , " + param2);
        return "받은 데이터 그대로 응답 : " + param1 + " , " + param2;
    }

    //3.
    @GetMapping("/method3")  // http://localhost:8080/get/method3?param1=value1&params2=vlaue2
    public String method3(@RequestParam String params1, @RequestParam String params2) {
        log.info("클라이언트로부터 받은 변수 :" + params1 + ", " + params2);
        return "받은 데이터 그대로 응답 : " + params1 + ", " + params2;
    }

    //4.
    @GetMapping("/method4")  // http://localhost:8080/get/method3?param1=value1&
    public Map<String, String> method4(@RequestParam Map<String, String> params) {
        log.info("클라이언트로부터 받은 변수 :" + params);
        return params;
    }

    //5. get 메서드에서 Dto로 매개변수 한번에 요청시 @ReqeustParam @ResponseBody 사용 불가
    @GetMapping("/method5")
    public ParamDto method5(ParamDto dto) {
        log.info("클라이언트로부터 받은 변수" + dto);
        return dto;
    }

    //6.
    @GetMapping("/method6")
    public String method6(@ModelAttribute ParamDto dto) {
        log.info("클라이언트로부터 받은 변수 : " + dto);
        return "받은 데이터 그대로 응답 : " + dto;
    }
}
