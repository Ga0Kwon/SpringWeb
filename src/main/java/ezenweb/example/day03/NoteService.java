package ezenweb.example.day03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//비즈니스 로직(실질적인 업무) 담당
@Service //MVC 서비스
@Slf4j
public class NoteService {
    @Autowired
    NoteEntityRepository noteEntityRepository;

    //1. 쓰기
    public boolean write(NoteDto note) {
        log.info("service write in :  " + note);

        NoteEntity noteEntity = noteEntityRepository.save(note.toEntity());

        if(noteEntity.getNno() > 0) {
            return true;
        }
//        NoteDto dto1 = new NoteDto(1, "하이", "호호");
//        dto1.toEntity();
//
//        NoteDto dto2 = new NoteDto(2, "하하", "안녕");
//        dto2.toEntity();

        return false;
    }

    //2. 출력
    public ArrayList<NoteDto> getNotes() {
        log.info("service getNotes");
        //1. 모든 엔티티 호출
        List<NoteEntity> entityList =  noteEntityRepository.findAll();

        //2 모든 엔티티를 형변환 [ 엔티티 -> DTO]
        ArrayList<NoteDto> list = new ArrayList<>();

        entityList.forEach(e -> {
            list.add(e.toDto());
        });

        return list;
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
