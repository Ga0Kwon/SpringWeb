package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.todo.TodoEntity;
import lombok.*;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDto {
    private int rno;

    private String rcontent;

    private BoardEntity boardEntity; //게시물 fk

    private MemberEntity memberEntity; //작성자  fk

    //toEntity[저장용]
    public ReplyEntity toEntity() {
        return ReplyEntity.builder()
                .rno(this.rno)
                .rcontent(this.rcontent)
                .memberEntity(this.memberEntity)
                .boardEntity(this.boardEntity)
                .build();
    }

}
