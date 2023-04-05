package ezenweb.example.day03;

import lombok.*;

/*@Setter@Getter@ToString*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data //getter, setter, toString 한번에
public class NoteDto {
    private int nno; //노트 번호
    private String ntitle; //노트 제목
    private String ncontent; //노트 내용
    
    //dto 필드들을 전부 소문자여야 인식한다 nTitle 이라고 했더니 null값만 들어감

}
