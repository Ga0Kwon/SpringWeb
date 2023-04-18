package ezenweb.web.domain.board;

import ezenweb.example.day06.객체관계.Board;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;

    @Column( name = "cname" , nullable = true )
    private String cname;

    // 양방향
    // 카테고리[Pk] <------> 게시물[FK]
    //PK 테이블에는 FK 흔적 남긴적이 없다. [ 필드 존재 X 객체 존재 O]
    @OneToMany(mappedBy = "categoryEntity") //하나가 다수에게 [PK --> FK]
    @Builder.Default //.builder 사용시 현재 필드 기본값으로 설정
    private List<BoardEntity> boardEntityList = new ArrayList<>();
}
