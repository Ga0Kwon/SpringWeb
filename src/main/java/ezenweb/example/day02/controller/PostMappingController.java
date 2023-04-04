package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j //로그
@RestController // @ResponseBody + @Controller
@RequestMapping("/post")
public class PostMappingController {

    /* -------------------- 쿼리스트링 형식[경로상의 변수가 보이는 경우]의 매개변수 요청 제외 -------------------- */
    //1.
    //쿼리 스트링 : http://localhost:8080/post/method1?param=value (O)
    // body에다가 적었을 땐 안됨.
/*    @PostMapping("/method1")
    public String method1(HttpServletRequest request){
        String param = request.getParameter("param");
        log.info("post method1 : " + param);
        return param;
    }*/


/*
    //2.
    //경로상 : http://localhost:8080/post/method/value1/value2 (O)
    @PostMapping("/method2/{param1}/{param2}")
    public String method2(@PathVariable String param1, @PathVariable String param2){
        log.info("post method2 : " + param1 + " " + param2);
        return param1 + " " + param2;
    }*/
    // => 의미없는 코드 !



    //1. @RequestBody [성공]
    @PostMapping("/method1")
    public ParamDto method1(@RequestBody ParamDto dto){
        log.info("post method1 : " + dto);
        return dto ;
    }

    //2. @RequsetParam[실패]
    @PostMapping("/method2")
    public String method2(@RequestParam String params1, @RequestParam String params2){
        log.info("post method2 : " + params1 + " " + params2);
        return params1 + " " + params2;
    }

    //3. @RequsetParam[실패] => @RequestBody로 수정 [성공]
    @PostMapping("/method3")
    public Map<String, String> method3(@RequestBody Map<String, String> map){
        log.info("post method3 : " + map);
        return map;
    }

    //4.@ModelAttribute[실패] => @RequestBody로 수정 [성공]
    @PostMapping("/method4")
    public ParamDto method4(@RequestBody ParamDto dto){
        log.info("post method4 : " + dto);
        return dto;
    }
}
