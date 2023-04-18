package ezenweb.web.controller;

import ezenweb.example.day06.객체관계.Board;
import ezenweb.web.domain.board.BoardDto;
import ezenweb.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    /* -------------------------- View 반환 -------------------------- */
    @GetMapping("")
    public Resource index(){
        return new ClassPathResource("templates/board/list.html");
    }
    /* -------------------------- Model 반환 -------------------------- */

    //1. 카테고리 등록 
    @PostMapping("/category/write")
    public boolean categoryWrite(@RequestBody BoardDto boardDto) {
        log.info("categoryWrite" + boardDto);
        boolean result = boardService.categoryWrite(boardDto);
        return result;
    }

    //2. 카테고리 출력 [반환타입 : {cno : cname, cno : cname}
        //List {값, 값, 값,값}
        //Map{키 : 값, 키 : 값, 키 : 값}
    @GetMapping("/category/list")
    public Map<Integer, String> categoryList(){
        Map<Integer, String> result = boardService.categoryList();
        return result;
    }

    //2. 게시물 쓰기
    @PostMapping("/write")
    public byte write(@RequestBody BoardDto boardDto){
        log.info("BoardDto write" + boardDto);
        byte result = boardService.write(boardDto);
        return result;
    }


    //3. 내가 쓴 게시물 출력
    @GetMapping("/myboards")
    public List<BoardDto> myboards(){
        List<BoardDto> boardDtoList = boardService.myboards();
        return boardDtoList;
    }

    //4. 카테고리별 게시물 출력
    @GetMapping("/list")
    public List<BoardDto> list(@RequestParam int cno){
        System.out.println("board list cno : " + cno);
        List<BoardDto> result = boardService.list(cno);
        return result;
    }

}
