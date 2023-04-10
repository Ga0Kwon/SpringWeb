package ezenweb.web.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    //1. 회원번호
    private int mno;
    //2. 회원 아이디 [이메일]
    private String memail;
    //3. 회원 비밀번호
    private String mpassword;
    //4. 회원 이름
    private String mname;
    //5. 회원 전화번호
    private String mphone;
    //6. 회원 등급
    private String mrole;

    private LocalDateTime cdate; //등록 날짜/시간
    private LocalDateTime udate; //수정 날짜/시간

    //toEntity
    public MemberEntity toEntity(){ //저장용도
        return MemberEntity.builder()
              .mno(mno)
              .memail(memail)
              .mpassword(mpassword)
              .mname(mname)
              .mphone(mphone)
              .mrole(mrole)
              .build();
    }
}
