package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class BoardDto {
    private int bno;
    private String btitle;
    private String bcontent;
    private int cno;
    private String cname;

    //추가
    private int mno;
    private String memail;

    //작성일
    private String bdate;
    //조회수
    private int bview;

    //댓글 목록
    List<ReplyDto> replyDtoList = new ArrayList<>();

    //저장용  Entity 변환 메소드
    //1) toCategoryEntity
    public CategoryEntity toCategoryEntity() {
        return CategoryEntity.builder()
                .cname(this.cname)
                .build();
    }

    //2) toBoardEntity
    public BoardEntity toBoardEntity() {
        return BoardEntity.builder()
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .build();
    }



}
