package ezenweb.web.service;

import ezenweb.example.day06.객체관계.Board;
import ezenweb.web.domain.board.*;
import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
        log.info("service myboards");
        return null;
    }

    //4. 카테고리 출력
    @Transactional
    public Map<Integer, String> categoryList(){
        List<CategoryEntity> categoryEntities = categoryEntityRepository.findAll();

        //1. Dto 또는 Map으로 형변환
        // List<엔티티> ---------> Map
        Map<Integer, String> map = new HashMap<>();

        categoryEntities.forEach((e) -> {
            map.put(e.getCno(), e.getCname());
        });

        return map;
    }
    @Transactional
    //5. 카테고리별 게시물 출력 + 전체 출력
    public List<BoardDto> list(int cno){
        log.info("service list" + cno);

        List<BoardDto> list = new ArrayList<>();
        if(cno == 0){//전체 보기
            List<BoardEntity> boardEntityList =  boardEntityRepository.findAll();

            boardEntityList.forEach((e) -> { // 엔티티[레코드] 하나씩 반복문
                list.add(e.toDto()); //엔티티[레코드] 하나씩 Dto 변환후 리스트 담기
            });
            return list; //리스트 반환

        }else{//카테고리별 게시물 출력
            Optional<CategoryEntity> optionalCategoryEntity = categoryEntityRepository.findById(cno);
            if(optionalCategoryEntity.isPresent()){
                optionalCategoryEntity.get().getBoardEntityList().forEach((e) -> {
                    list.add(e.toDto());
                });
            }
            return list;
        }
    }

}
