package ezenweb.web.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailBoardDto {
    private long totalReplyCount; //게시물별 댓글 수
    private int totalReplyPage; //게시물별 댓글 페이지 수
    private BoardDto boarDto; // 개별 게시물 정보
    private List<ReplyDto> replyDtoList; //댓글 리스트
    
}
