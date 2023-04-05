package ezenweb.example.day03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

//비즈니스 로직(실질적인 업무) 담당
@Service //MVC 서비스
@Slf4j
public class NoteService {
    //1. 쓰기
    public boolean write(NoteDto note) {
        log.info("service write in :  " + note);
        return true;
    }

    //2. 출력
    public ArrayList<NoteDto> getNotes() {
        log.info("service getNotes");
        return null;
    }

    //3.삭제
    public boolean delete(int nno) {
        log.info("service delete in :  " + nno);
        return true;
    }
    
    //4. 수정
    public boolean update(NoteDto note) {
        log.info("service update in :  " + note);
        return true;
    }

}
