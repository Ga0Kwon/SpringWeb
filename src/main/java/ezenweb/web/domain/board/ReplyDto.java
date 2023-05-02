package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDto {
    private int rno;

    private String rcontent;

    private String rdate; //작성일

    private int rindex; //부모의 댓글 번호

    private int bno; //현재 댓글이 속해있는 게시물 번호

    private MemberDto memberDto;

    //답글 리스트
    //빌더에 명시를 안해도 나타낼 수 있다.
    @Builder.Default //빌더를 이용한 객체 생성시 현재 필드 정보 기본값으로 사용
    private List<ReplyDto> rereplyDtoList = new ArrayList<ReplyDto>();


    private int mno;
    private String mname;

    //toEntity[저장용]
    public ReplyEntity toEntity() { //입력받는건 댓글 내용뿐
        return ReplyEntity.builder()
                .rindex(this.rindex)
                .rcontent(this.rcontent)
                .build();
    }

}
