
package ezenweb.web.controller;

import ezenweb.web.domain.board.ReplyDto;
import ezenweb.web.domain.board.ReplyPageDto;
import ezenweb.web.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/onewayReply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @GetMapping("")
    public ReplyPageDto get(ReplyPageDto pageDto) {
        log.info("get page : " + pageDto.toString());
        ReplyPageDto replyPageDto = replyService.getReply(pageDto);
        return replyPageDto;
    }

    @PostMapping("")
    public int post(@RequestBody ReplyDto dto){
        log.info("controller post" + dto);

        int result = replyService.writeReply(dto);
        return result;
    }


    @PutMapping("")
    public boolean put(@RequestBody ReplyDto dto){
        log.info("controller put" + dto);
        boolean result = replyService.updateReply(dto);
        return result;
    }

    @DeleteMapping("")
    public boolean delete(@RequestParam int rno){
        log.info("controller put" + rno);
        boolean result = replyService.deleteReply(rno);
        return result;
    }


}

