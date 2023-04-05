package ezenweb.example.day03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        //1. 삭제할 식별번호[pk]를 이용한 엔티티 검색 [검색 성공시 : 엔티티 / 검색실패시 : null]
        //findById() : pk값을 가지고 해당 레코드를 찾는다.
        Optional<NoteEntity> optionalEntity =  noteEntityRepository.findById(nno);

        //2. 포장클래스 <엔티티>
        if(optionalEntity.isPresent()){//null이면 false, 객체이면 true
            //포장 클래스 내 엔티티가 들어왔으면
            NoteEntity entity = optionalEntity.get();
            noteEntityRepository.delete(entity); //찾은 entity를 삭제
            return true;
        }

        return false;
    }

    @Transactional //import javax.transaction.Transactional;
    //@Transactional : 해당 메서드 내 엔티티객체 필드의 변화가 있을 경우 실시간으로 commit 처리
    //수정은 필수고 나머지는 넣어도 안넣어도 됨(조회, 삭제, 추가)
    //4. 수정
    public boolean update(NoteDto note) {
        //1. 수정할 해당 pk번호를 이용한 엔티티 검색
        Optional<NoteEntity> optionalEntity =  noteEntityRepository.findById(note.getNno());
        //2. 포장 클래스<엔티티>

        if(optionalEntity.isPresent()) {//null이면 false, 있으면 true
            NoteEntity entity = optionalEntity.get();
            // 자동 매핑이 되어있기 때문에 setter 로 수정 가능
            //3. 객체내 필드변경 => 엔티티 객체 필드 값 변경 => 해당 레코드의 필드 값 변경
            entity.setNcontent(note.getNcontent());
            entity.setNtitle(note.getNtitle());
            return true;
        }
        return false;
    }

}
