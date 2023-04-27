package ezenweb.web.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import ezenweb.web.domain.BaseTime;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.todo.TodoDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "reply")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;

    @Column
    private String rcontent;

    @Column
    @ColumnDefault("0")
    private int rindex; //상위 댓글 번호 = 0

    @ManyToOne
    @JoinColumn(name = "bno")
    @ToString.Exclude
    private BoardEntity boardEntity; //게시물 fk

    @ManyToOne
    @JoinColumn(name = "mno")
    @ToString.Exclude
    private MemberEntity memberEntity; //작성자  fk

    //toDto[출력용]
    public ReplyDto toDto(){
        return ReplyDto.builder()
                .rno(this.rno)
                .rcontent(this.rcontent)
                .rdate(this.cdate.toLocalDate().toString())
                //cdate[LocalDateTime] rdate[String] => objectMapper이 LocalDateTime 지원을 안해서 toString()
                .build();
    }
}
