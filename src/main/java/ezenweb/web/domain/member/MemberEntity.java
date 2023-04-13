package ezenweb.web.domain.member;

import ezenweb.web.domain.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class MemberEntity extends BaseTime {
    //1. 회원번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;
    //2. 회원 아이디 [이메일]
    @Column(nullable = false) // null값 허용X
    private String memail;
    //3. 회원 비밀번호
    @Column
    private String mpassword;
    //4. 회원 이름
    @Column
    private String mname;
    //5. 회원 전화번호
    @Column
    private String mphone;
    //6. 회원 등급 / 권한 명
    @Column
    private String mrole;

    //toDto
    public MemberDto toDto() { // 출력용도
        return MemberDto.builder()
              .mno(this.mno)
              .memail(this.memail)
              .mpassword(this.mpassword)
              .mname(this.mname)
              .mphone(this.mphone)
              .mrole(this.mrole)
              .cdate(this.cdate)
              .udate(this.udate)
              .build();
    }
}
