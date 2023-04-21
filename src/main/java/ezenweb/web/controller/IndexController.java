package ezenweb.web.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    //서버 사이드 라우팅 : 클라이언트가 서버에게 html 요청하는 방식 [ 리액트 통합 개발일경우 사용안함. ]
    @GetMapping("/") // localhost:8080 요청 시 아래 템플릿[html] 반환
    public Resource getIndex(){
        return new ClassPathResource("templates/index.html");
    }
}
