package ezenweb.web.controller;

import ezenweb.example.day06.객체관계.Board;
import ezenweb.web.domain.board.BoardDto;
import ezenweb.web.domain.board.CategoryDto;
import ezenweb.web.domain.board.PageDto;
import ezenweb.web.domain.board.ReplyDto;
import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.service.BoardService;
import ezenweb.web.service.MemberService;
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
/*@CrossOrigin(origins = "http://localhost:3000") //리소스를 교차로 지원*/
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    MemberService memberService;

    /* -------------------------- View 반환 -------------------------- */
    //서버 사이드 라우팅 : 클라이언트가 서버에게 html 요청하는 방식 [ 리액트 통합 개발일경우 사용안함. ]
/*    @GetMapping("")
    public Resource index(){
        return new ClassPathResource("templates/board/list.html");
    }*/
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
    public List<CategoryDto> categoryList(){
        List<CategoryDto> result = boardService.categoryList();
        return result;
    }

    //2. 게시물 쓰기
    @PostMapping("")
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
    @GetMapping("")
    public PageDto list(PageDto pagedto){
        log.info(pagedto.toString());
        PageDto result = boardService.list(pagedto);
        return result;
    }

    //게시물 개별 출력
    @GetMapping("/getBoard")
    public BoardDto getDetailBoard(@RequestParam int bno){
        System.out.println("getDetailBoard bno : " + bno);
        BoardDto result = boardService.getDetailBoard(bno);
        System.out.println("게시물 정보 다 출력한다!!!!! " + result);
        return result;
    }
    //수정
    @PutMapping("")
    public boolean update(@RequestBody BoardDto dto){
        System.out.println(dto.toString());
        boolean result = boardService.updateBoard(dto);
        return result;
    }

    @DeleteMapping("")
    public int deleteBoard(@RequestParam int bno){
        System.out.println("getDetailBoard bno : " + bno);
        int result = boardService.deleteBoard(bno);
        return result;
    }
    
    //댓글 작성[C]
    @PostMapping("/reply")
    public int postReply(@RequestBody ReplyDto replyDto){
        log.info("postReply : " + replyDto.toString());
        return boardService.postReply(replyDto);
    }
    // 댓글 출력[R] => 할필요 X => 게시물과 같이 갈거니까
    @GetMapping("/reply")
    public boolean getReply(){
        log.info("getReply : ");
        return true;
    }
    // 댓글수정 [U]
    @PutMapping("/reply")
    public boolean putReply(@RequestBody ReplyDto replyDto){
        log.info("putReply : " + replyDto.toString());
        return boardService.putReply(replyDto);
    }
    // 댓글 삭제 [D]
    @DeleteMapping("/reply")
    public boolean deleteReply(@RequestParam int rno){
        log.info("deleteReply : " + rno);
        return boardService.deleteReply(rno);
    }
}
