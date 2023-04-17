package ezenweb.web.controller;

import ezenweb.web.domain.board.BoardDto;
import ezenweb.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    //1. 카테고리 등록 
    @PostMapping("/category/write")
    public boolean categoryWrite(@RequestBody BoardDto boardDto) {
        log.info("categoryWrite" + boardDto);
        boolean result = boardService.categoryWrite(boardDto);
        return result;
    }

    //2. 게시물 쓰기
    @PostMapping("/write")
    public boolean write(@RequestBody BoardDto boardDto){
        log.info("BoardDto write" + boardDto);
        boolean result = boardService.write(boardDto);
        return result;
    }
    
    //3. 내가 쓴 게시물 출력
    @GetMapping("/myboards")
    public List<BoardDto> myboards(){
        List<BoardDto> boardDtoList = boardService.myboards();
        return boardDtoList;
    }
}
