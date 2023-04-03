package ezenweb.example.day01Q;

import lombok.*;

import javax.persistence.*;

@Entity // DB 테이블과 해당 클래스와 매필[연결]
@Table(name = "testboard") // DB 테이블명

// 룸북에서 제공
@Setter@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder // 빌더 패턴
public class BoradEntity {
    @Id //pk 선언 생략시 오류 발생[JPA 엔티티/테이블 당 PK 하나 이상 무조건 선언]
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto key
    private int bno;
    @Column
    private String btitle;
    @Column
    private String bcontent;
}
