package ezenweb.web.controller;

import ezenweb.web.domain.todo.PageDto;
import ezenweb.web.domain.todo.TodoDto;
import ezenweb.web.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j //로그 기능 주입
@RequestMapping("/todo")
// 해당 컨트롤은 http://localhost:3000 요청 CROS 정책
//@CrossOrigin(origins = "http://192.168.219.113:3000") //리소스를 교차로 지원
public class TodoController {
    @Autowired
    private TodoService todoService;
    @GetMapping("")
    public PageDto get(@RequestParam int page){
        log.info("get page : " + page);
        PageDto todoList = todoService.getTodo(page);
        return todoList;
    }

    @PostMapping("")
    public boolean post(@RequestBody TodoDto dto){
        log.info("controller post" + dto);
        boolean result = todoService.writeTodo(dto);
        return result;
    }


    @PutMapping("")
    public boolean put(@RequestBody TodoDto dto){
        log.info("controller put" + dto);
        boolean result = todoService.updateTodo(dto);
        return result;
    }

    @DeleteMapping("")
    public boolean delete(@RequestParam int tno){
        log.info("controller put" + tno);
        boolean result = todoService.deleteTodo(tno);
        return result;
    }

}
