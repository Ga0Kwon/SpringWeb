package ezenweb.web.domain.product;

import ezenweb.web.domain.BaseTime;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name ="product")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity extends BaseTime {
    //제품 번호 [JPA는 1개 이상의 ID 필요! => iD = PK가 필요]
    @Id
    private int id; // 오토키 x -> 오늘날짜/밀리초

    //제품명 [JPA로 DB 필드 선언시 _ : X]
    @Column(nullable = false)
    private String pname;

    //제품가격
    @Column(nullable = false)
    private int pprice;

    //제품 카테고리
    @Column(nullable = false)
    private String pcategory;

    //제품 설명
    @Column(nullable = false, columnDefinition = "TEXT") //sql 자료형을 넣어줄 수 있다.
    private String pcomment;
    
    //제조사
    @Column(nullable = false, length = 100) //기본 길이가 255인데 지정할 수 있다.
    private String pmanufacturer;

    //제품 상태 [ 0 : 판매중, 1: 판매 중지, 2: 재고 없음]
    @ColumnDefault("0")
    @Column(nullable = false)
    private byte pstatus;

    //제품 재고/수량
    @ColumnDefault("0")
    @Column(nullable = false)
    private int pstock;

    //제품 이미지 [ 1 : 다 ] 연관성
    //@ManyToOne
    //구매 내경[ 1: 다] 연관성
    
    //1. 출력용 [ 관리자 페이지 - 관리자가 보는 입장]
    public ProductDto toAdminProductDto(){
        return ProductDto.builder()
                .id(this.id)
                .pname(this.pname)
                .pcomment(this.pcomment)
                .pmanufacturer(this.pmanufacturer)
                .pcategory(this.pcategory)
                .pstock(this.pstock)
                .pprice(this.pprice)
                .udate(this.udate.toString())
                .cdate(this.cdate.toString())
                .pstatus(this.pstatus)
                .build();
    }

    //2. 출력용 [ 메인 페이지 - 사용자가 보는 입장]
    //public ProductDto toMainProductDto(){}
}
