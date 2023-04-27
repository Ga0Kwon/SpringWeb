package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDto {
    private int rno;

    private String rcontent;

    private String rdate; //작성일

    private int bno; //현재 댓글이 속해있는 게시물 번호

    private MemberDto memberDto;
    
    //toEntity[저장용]
    public ReplyEntity toEntity() { //입력받는건 댓글 내용뿐
        return ReplyEntity.builder()
                .rcontent(this.rcontent)
                .build();
    }

}
