package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyPageDto {
    //1. 전체 reply 수
    private long totalCount;
    //2. 전체 reply 페이지 수
    private int totalPage;
    //3. 현재 페이지의 게시물 dto들
    List<ReplyDto> replyDtoList;
    //4. 현재 reply 페이지 수
    private int page;

    private int bno; //어떤 게시물인지.


}
