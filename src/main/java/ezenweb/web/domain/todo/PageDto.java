package ezenweb.web.domain.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDto {
    //1. 전체 Todo 수
    private long totalCount;
    //2. 전체 Todo 페이지 수
    private int totalPage;
    //3. 현재 페이지의 게시물 dto들
    List<TodoDto> todoList;
    //4. 현재 Todo 페이지 수
    private int page;
}
