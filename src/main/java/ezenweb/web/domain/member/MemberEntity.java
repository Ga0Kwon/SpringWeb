package ezenweb.web.domain.member;

import ezenweb.web.domain.BaseTime;
import ezenweb.web.domain.board.BoardEntity;
import ezenweb.web.domain.board.ReplyEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter@Setter@ToString
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

    //게시물 목록 내가 쓴 게시물
    @OneToMany(mappedBy = "memberEntity")
    @Builder.Default
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    // 댓글 목록 = 내가 쓴 댓글
    @OneToMany(mappedBy = "memberEntity")
    @ToString.Exclude
    private List<ReplyEntity> replyEntityList = new ArrayList<>();
    
    //toDto
    public MemberDto toDto() { // 출력용도[userDetails에서 패스워드 등록할대 써야함.]
        return MemberDto.builder()
              .mno(this.mno)
              .memail(this.memail)
              .mpassword(this.mpassword)
              .mname(this.mname)
              .mphone(this.mphone)
              .mrole(this.mrole)
              .cdate(this.cdate)
              .udate(this.udate)
                //userDetails에 권한 관련 정보가 있기때문에 굳이 role안넣어됨
              .build();
    }
}
