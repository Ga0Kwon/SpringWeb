package ezenweb.example.day03;

import lombok.*;

/*@Setter@Getter@ToString*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data //getter, setter, toString 한번에
public class NoteDto { //실제 내용X [옮기기 위해 사용하는]
    private int nno; //노트 번호
    private String ntitle; //노트 제목
    private String ncontent; //노트 내용
    
    //dto 필드들을 전부 소문자여야 인식한다 nTitle 이라고 했더니 null값만 들어감


    // dto ----> entity [서비스에서 사용]
        //this : 현재 클래스내 필드명
    public NoteEntity toEntity() {
        return NoteEntity.builder()
              .nno(this.nno)
              .ntitle(this.ntitle)
              .ncontent(this.ncontent)
              .build();
    }
}
