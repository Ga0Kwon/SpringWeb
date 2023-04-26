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
public class PageDto {//페이지와 관련된 정보
    //1. 전체 게시물 수
    private long totalCount;
    //2. 전체 페이지수
    private int totalPage;
    //3. 현재 페이지의 게시물 dto들
    List<BoardDto> boardDtoList;
    //4. 현재 페이지번호
    private int page;
    //5. 현재 카테고리 번호
    private int cno;

    //6. 선택한 key
    private String key;
    //7. 검색한 keyword
    private String keyword;
}
