package ezenweb.web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
// 해당 컨트롤은 http://localhost:3000 요청 CROS 정책
@CrossOrigin(origins = "http://localhost:3000") //리소스를 교차로 지원
public class TodoController {
    @GetMapping("")
    public String get(){
        return "REACT HI";
    }
}
