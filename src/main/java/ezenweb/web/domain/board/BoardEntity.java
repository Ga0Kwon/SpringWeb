package ezenweb.web.domain.board;

import ezenweb.web.domain.BaseTime;
import ezenweb.web.domain.member.MemberEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity@Table(name = "board")
@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class BoardEntity extends BaseTime {
    // 작성일, 수정일 BaseTime 클래스를 상속받아 사용.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;

    @Column(nullable = false)
    private String btitle;

    @Column(columnDefinition = "longtext") //긴글 텍스트
    private String bcontent; //mysql longtext 자료형 선택

    @Column
    @ColumnDefault("0")
    private int bview; //조회수

    //FK = 외래키 [ 카테고리 번호 = cno, 회원번호 = mno ]
    //카테고리 번호
    @ManyToOne // 다수가 하나에게 [fk ---> pk]
    @JoinColumn(name = "cno") //pk 이름 정하기
    @ToString.Exclude // 해당 필드는 @ToString 제외 필드[ * 양방향 필수]
    private CategoryEntity categoryEntity;

    //PK = 양방향 [해당 댓글 엔티티의 FK 필드와 매핑]
    //회원번호 [작성자]
    @ManyToOne
    @JoinColumn(name = "mno")
    @ToString.Exclude
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "boardEntity")
    @Builder.Default
    private List<ReplyEntity> replyEntityList = new ArrayList<>();

    //출력용 Entity ===> DTO
    public BoardDto toDto(){
        return BoardDto.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .cno(this.getCategoryEntity().getCno())
                .cname(this.getCategoryEntity().getCname())
                .mno(this.getMemberEntity().getMno())
                .memail(this.getMemberEntity().getMemail())
                .bview(this.bview)
                .bdate((this.cdate.toLocalDate().toString()).equals(LocalDateTime.now().toLocalDate().toString()) ?
                        this.cdate.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) :
                        this.cdate.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }
    
    /*
        cdate [LocalDateTime]
            LocaldateTime() : 날짜/시간 클래스
            LocalDateTime.now() : 현재[시스템] 날짜/시간 추출
            1. toLoacalDaet() : 날짜만 출력
            2. toLocalTime() : 시간만 출력
    */
}
