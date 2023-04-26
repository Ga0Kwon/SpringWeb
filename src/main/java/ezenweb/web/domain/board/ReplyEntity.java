package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.todo.TodoDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reply")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;

    @Column
    private String rcontent;

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
                .memberEntity(this.memberEntity)
                .boardEntity(this.boardEntity)
                .build();
    }
}
