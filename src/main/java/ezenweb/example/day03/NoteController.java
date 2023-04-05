package ezenweb.example.day03;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController //MVC 컨트롤러에서 Controller
@Slf4j //로그
@RequestMapping("/note")
public class NoteController {
    @Autowired //자동 주입 => new를 안써도 가능, 생성자 자동 주입 [ *단 스프링컨테이너에 등록이 되어있는 경우]
    NoteService noteService; //서비스 객체

    /* ------------------------------- HTML 반환 --------------------------------*/
    //  org.springframework.core.io.꺼로 해야함!!
    @GetMapping("") //http://localhost:8080/note
    public Resource index(){
        return new ClassPathResource("templates/note.html");
    }

    /* -------------------------------- Restful API -------------------------------- */
    //1. 쓰기
    @PostMapping("/write")
    public boolean write(@RequestBody NoteDto dto) {
        log.info("write in : " + dto.toString());
        boolean result = noteService.write(dto);
        return result;
    }

    //2. 출력
    @GetMapping("/get")
    public ArrayList<NoteDto> getNote(){
        log.info("get in");
        ArrayList<NoteDto> noteList = noteService.getNotes();
        return noteList;
    }

    //3. 삭제
    @DeleteMapping("/delete")
    public boolean delete(@RequestParam int nno) {
        log.info("delete in : " + nno);
        boolean result = noteService.delete(nno);
        return result;
    }

    //4. 수정
    @PutMapping("/update")
    public boolean update(@RequestBody NoteDto dto) {
        log.info("update in : " + dto.toString());
        boolean result = noteService.update(dto);
        return result;
    }
}
