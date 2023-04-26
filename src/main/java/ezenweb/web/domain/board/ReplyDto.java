package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberEntity;
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
}
