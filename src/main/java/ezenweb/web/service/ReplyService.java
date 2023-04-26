package ezenweb.web.service;

import ezenweb.web.domain.board.*;
import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import ezenweb.web.domain.todo.PageDto;
import ezenweb.web.domain.todo.TodoDto;
import ezenweb.web.domain.todo.TodoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReplyService {
    @Autowired
    ReplyEntityRepository replyEntityRepository;

    @Autowired
    MemberEntityRepository memberEntityRepository;

    @Autowired
    BoardEntityRepository boardEntityRepository;

    //댓글 등록
    @Transactional
    public int writeReply(ReplyDto replyDto){
        log.info("service replyDto post : " +replyDto );

        Object o =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.equals("anonymousUser")){ //등록 불가
            return 1; //로그인 안했다.
        }

        MemberDto loginDto = (MemberDto) o;
        Optional<MemberEntity> memberEntity = memberEntityRepository.findById(((MemberDto) o).getMno());

        if(memberEntity.isPresent()){
            replyDto.setMemberEntity(memberEntity.get());
            ReplyEntity entity = replyEntityRepository.save(replyDto.toEntity());

            if(entity.getRno() > 0){
                return 0;
            }
        }

        return 2; //등록실패
    }

    //댓글 출력
    @Transactional
    public ReplyPageDto getReply(ReplyPageDto dto){
        List<ReplyDto> replyDtoList = new ArrayList<>();

        System.out.println("댓글 서비스 들어옴!!! GET!!!!  " + dto);
        Pageable pageable = PageRequest.of(dto.getPage()-1, 5, Sort.by(Sort.Direction.DESC, "rno"));

        Page<ReplyEntity> entity = replyEntityRepository.findBySearch(pageable, dto.getBno());

        System.out.println("service reply get :" + entity);

        entity.forEach((e) -> {
            replyDtoList.add(e.toDto());
        });

        System.out.println(replyDtoList);

        return ReplyPageDto.builder()
                .totalCount(entity.getTotalElements())
                .totalPage(entity.getTotalPages())
                .replyDtoList(replyDtoList)
                .bno(dto.getBno())
                .page(dto.getPage())
                .build();
    }

    //댓글 수정
    @Transactional
    public boolean updateReply(ReplyDto replyDto){
        Optional<ReplyEntity> replyEntityOptional = replyEntityRepository.findById(replyDto.getRno());

        if(replyEntityOptional.isPresent()){
            ReplyEntity entity = replyEntityOptional.get();

            entity.setRcontent(replyDto.getRcontent());
            return true;
        }
        return false;
    }
    //댓글 삭제
    @Transactional
    public boolean deleteReply(int rno){
        Optional<ReplyEntity> optionalReply = replyEntityRepository.findById(rno);

        if(optionalReply.isPresent()){
            replyEntityRepository.delete(optionalReply.get());
            return true;
        }
        return false;
    }
}
