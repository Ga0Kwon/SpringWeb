package ezenweb.web.service;

import ezenweb.example.day06.객체관계.Board;
import ezenweb.example.day06.객체관계.Member;
import ezenweb.web.domain.board.*;
import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;


@Service
@Slf4j
public class BoardService {
    @Autowired
    ReplyEntityRepository replyEntityRepository;

    @Autowired
    BoardEntityRepository boardEntityRepository;

    @Autowired
    CategoryEntityRepository categoryEntityRepository;

    @Autowired
    MemberEntityRepository memberEntityRepository;

    //1. 카테고리 등록
    @Transactional
    public boolean categoryWrite( BoardDto boardDto) {
        log.info("service category" + boardDto);
        //1) 입력받은 cname을 Dto에서 카테고리 entity 형변환해서 save
        CategoryEntity entity = categoryEntityRepository.save(boardDto.toCategoryEntity());
        //2) 만약에 생성된 엔티티의 PK가 1보다 크면 save
        if(entity.getCno() > 0){
            return true;
        }
        return false;
    }

    @Transactional //setter을 쓰면 필수
    //2. 게시물 쓰기
    public byte write( BoardDto boardDto){
        //1) 선택된 카테고리 번호를 이용한 카테고리 엔티티 찾기
        Optional<CategoryEntity>  categoryEntity = categoryEntityRepository.findById(boardDto.getCno());

        //2) 만약에 선택된 카테고리가 존재하지 않으면
        if(!categoryEntity.isPresent()){
            return 1; //카테고리 없음
        }

        //로그인된 회원의 엔티티 찾기
        //1) 인증된 인증정보 찾기
        Object o =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.equals("anonymousUser")){ //등록 불가
            return 2; //로그인 안했다.
        }

        //2)
        MemberDto loginDto = (MemberDto) o;
        //3) 회원 엔티티 찾기
        Optional<MemberEntity> memberEntity =  memberEntityRepository.findByMemail(loginDto.getMemail());


        //3) 게시물 쓰기
        BoardEntity boardEntity = boardEntityRepository.save(boardDto.toBoardEntity());

        if(boardEntity.getBno() < 0){
            return 3; //등록에 실패했다.
        }
        
        //4) 양방향 관계 [카테고리안에 게시물 힙[주소] 대입, 게시물안에 카테고리 힙[주소] 대입]
            //1> 카테고리 Entity에 생성된 게시물 등록
        categoryEntity.get().getBoardEntityList().add(boardEntity);
            //2> 생성된 게시물에 카테고리 Entity 등록
        boardEntity.setCategoryEntity(categoryEntity.get());

        //공지사항 게시물 정보 확인
        Optional<CategoryEntity> optionalCategoryEntity = categoryEntityRepository.findById(boardDto.getCno());

        log.info(boardEntity.toString());// 부모를 출력하면 자식의 정보도 알 수 있음.

       //5. 양방향 관계
            //1) 생성된 게시물 엔티티에 로그인된 회원 등록
        boardEntity.setMemberEntity(memberEntity.get());
            //2) 로그인된 회원 엔티티에 생성된 게시물 엔티티 등록
        memberEntity.get().getBoardEntityList().add(boardEntity);

        /* ------------------------- --------------------------- */
        log.info(boardEntity.toString());

        return 0; //성공
    }

    //3. 내가 쓴 게시물 출력
    @Transactional
    public List<BoardDto> myboards(){
        // 1. 로그인 인증 세션 호출 ---> Dto 강제 형변환
        MemberDto memberDto = (MemberDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // 일반 회원 : 모든 정보 | oauth회원 : email, mname, rol만 있음
        //2. 회원 엔티티 찾기
        MemberEntity entity = memberEntityRepository.findByMemail(memberDto.getMemail()).get();

        //3.
        List<BoardDto> list = new ArrayList<>();
        entity.getBoardEntityList().forEach((e) -> {
            list.add(e.toDto());
        });
        
        return list;
    }

    //4. 카테고리 출력
    @Transactional
    public List<CategoryDto> categoryList(){
        List<CategoryEntity> categoryEntities = categoryEntityRepository.findAll();

        //1. Dto 또는 Map으로 형변환
        // List<엔티티> ---------> Map
        //Map<Integer, String> map = new HashMap<>();
        List<CategoryDto> list = new ArrayList<>();
        categoryEntities.forEach((e) -> {
            list.add(new CategoryDto(e.getCno(), e.getCname()));
        });

        return list;
    }
    @Transactional
    //5. 카테고리별 게시물 출력 + 전체 출력
    public PageDto list(PageDto pageDto){
        //1. pageable 인터페이스 [ 페이지 관련 인터페이스 ] => domain꺼 쓰기!

        Pageable pageable = PageRequest.of(pageDto.getPage()-1, 5, Sort.by(Sort.Direction.DESC, "bno") );
        // PageRequest.of(페이지번호[0시작]) -1을 해줘야한다 0부터 시작하니까
        //size : 총 몇페이지씩
        //Sort.by(Sort.Direction.DESC, "정렬기준 필드") : 정렬기준 필드를 기준으로 내림차순 정렬
        Page<BoardEntity> pageEntity = boardEntityRepository.findBySearch(pageable, pageDto.getCno(), pageDto.getKey(), pageDto.getKeyword());
        /*if(cno == 0){ //전체 보기일 경우
            pageEntity = boardEntityRepository.findAll(pageable);
        }else{ //카테고리 선택일 경우
            pageEntity = boardEntityRepository.findBySearch(pageable, cno);
        }
*/
        List<BoardDto> boardDtoList = new ArrayList<BoardDto>();

        pageEntity.forEach((b) -> {
            boardDtoList.add(b.toDto());

        });

        pageDto.setBoardDtoList(boardDtoList);
        pageDto.setTotalPage(pageEntity.getTotalPages());
        pageDto.setTotalCount(pageEntity.getTotalElements());

        return pageDto;

    }

    //게시물 개별 조회/출력 + 댓글 출력
    // => 댓글 출력은 게시물 조회에서만 하니까 toDto가 아닌 여기서 처리한다. or toDto()를 따로 만드는것
    @Transactional
    public BoardDto getDetailBoard(int bno){
        //Page<ReplyEntity> replyEntityPage = replyEntityRepository.findBySearch(pageable, pageDto)
       Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById(bno);

       if(optionalBoardEntity.isPresent()){
           BoardEntity boardEntity = optionalBoardEntity.get();

           List<ReplyDto> replyDtoList = new ArrayList<ReplyDto>();
           //댓글 같이 형변환[toDto vs. 서비스]
           boardEntity.getReplyEntityList().forEach((r) -> {
               if(r.getRindex() == 0){ //상위 댓글
                   replyDtoList.add(r.toDto());
               }
               
               //하위 댓글
               boardEntity.getReplyEntityList().forEach((rr) -> {
                   if(r.getRno() == rr.getRindex()){
                       replyDtoList.get(replyDtoList.size() -1).getRereplyDtoList().add(rr.toDto());
                        // 리스트 길이 - 1 : 최근 리스트에 등록한 인덱스 번호 = 마지막 인덱스
                   }
               });


           });

           BoardDto boardDto = boardEntity.toDto();
           boardDto.setReplyDtoList(replyDtoList); //댓글 목록을 담아서

           return boardDto;
       }

        return null;

    }
    
    //멤버 정보 받아오기
    public MemberDto retrunMemberDto(int mno){
        return memberEntityRepository.findById(mno).get().toDto();
    }

    @Transactional
    public int deleteBoard(int bno){
        MemberDto memberDto = (MemberDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<BoardEntity> optionalBoard = boardEntityRepository.findById(bno);
        System.out.println("서비스에 삭제 board_bno 들어옴 : " + bno);
        if(optionalBoard.isPresent()){ //포장안의 정보가 들어 있고,
            if(memberDto.getMemail().equals(optionalBoard.get().getMemberEntity().getMemail())){
                boardEntityRepository.delete(optionalBoard.get()); //삭제하려는 entity를 삭제
                return 0;
            }else{
                return 2; //삭제할 권한이 없을 경우
            }
        }
        return 1; //해당 게시물 정보가 없을 경우
    }
    
    //게시물 수정
    @Transactional
    public boolean updateBoard(BoardDto boardDto){
        Optional<BoardEntity> optionalBoardentity = boardEntityRepository.findById(boardDto.getBno());

        Optional<CategoryEntity> optionalCategoryEntity = categoryEntityRepository.findById(boardDto.getCno());

        if(optionalBoardentity.isPresent() && optionalCategoryEntity.isPresent()){
            BoardEntity entity = optionalBoardentity.get();

            entity.setBtitle(boardDto.getBtitle());
            entity.setBcontent(boardDto.getBcontent());
            entity.setCategoryEntity(optionalCategoryEntity.get());

            return true;
        }

        return false;
    }
    
    //댓글작성
    @Transactional
    public int postReply(@RequestBody ReplyDto replyDto){

        //0. 로그인 했는지 [댓글 작성자]
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(o.equals("anonymousUser")){ //등록 불가
            return 1; //로그인 안했다.
        }

        //0. 댓글 작성한 게시물 호출
        Optional<BoardEntity>  optionalBoardEntity = boardEntityRepository.findById(replyDto.getBno());

        if(!optionalBoardEntity.isPresent()){ //댓글 작성한 게시물이 없으면
            return 3;
        }

        MemberDto memberDto = (MemberDto) o; //위에서 한꺼번에하면 에러 발생함.

        //로그인한 회원 정보 가져오기
        Optional<MemberEntity> memberEntity = memberEntityRepository.findById(memberDto.getMno());

        //1. 댓글 작성한다.
        ReplyEntity replyEntity = replyEntityRepository.save(replyDto.toEntity());

        if(replyEntity.getRno() < 1){ //1보다 작으면 저장X
            return 2;
        }

        //2. 댓글과 회원의 양방향 관계 [ 댓글 -> 회원 / 회원 -> 댓글 == 양방향, 댓글 -> 회원 == 단방향]
        replyEntity.setMemberEntity(memberEntity.get());
        memberEntity.get().getReplyEntityList().add(replyEntity); //member에 댓글 작성한거 추가 넣기

        //3. 댓글과 게시물의 양방향 관계
        replyEntity.setBoardEntity(optionalBoardEntity.get());
        optionalBoardEntity.get().getReplyEntityList().add(replyEntity);

        return 0;
    }

    // 댓글수정 [U]
    @Transactional
    public boolean putReply(@RequestBody ReplyDto replyDto){ // {rno : "수정할 번호", rcontent : "수정할 내용"
        Optional<ReplyEntity> replyEntityOptional = replyEntityRepository.findById(replyDto.getRno());

        if(replyEntityOptional.isPresent()){
            replyEntityOptional.get().setRcontent(replyDto.getRcontent());
            return true;
        }
        return false;
    }
    // 댓글 삭제 [D]
    @Transactional
    public boolean deleteReply(@RequestParam int rno){ // {rno : "삭제할 번호"}
        Optional<ReplyEntity> replyEntityOptional = replyEntityRepository.findById(rno);

        if(replyEntityOptional.isPresent()){
            replyEntityRepository.delete(replyEntityOptional.get());
            return true;
        }
        return false;
    }
}
