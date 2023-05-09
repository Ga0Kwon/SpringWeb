package ezenweb.web.domain.product;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private int id; // 오토키 x -> 오늘날짜/밀리초

    //제품명 [JPA로 DB 필드 선언시 _ : X]
    private String pname;

    //제품가격
    private int pprice;

    //제품 카테고리
    private String pcategory;

    //제품 설명
    private String pcomment;

    //제조사
    private String pmanufacturer;

    //제품 상태 [ 0 : 판매중, 1: 판매 중지, 2: 재고 없음]
    private byte pstatus;

    //제품 재고/수량
    private int pstock;

    //관리자용
    private String cdate; //생성 날짜/시간
    private String udate; //수정 날짜/시간

    //1. 저장용 [ 관리자 페이지 ]
    public ProductEntity toSaveEntity() {
        return ProductEntity.builder()
              .pname(this.pname)
              .pprice(this.pprice)
              .pcategory(this.pcategory)
              .pcomment(this.pcomment)
              .pmanufacturer(this.pmanufacturer)
              .pstatus(this.pstatus)
              .pstock(this.pstock)
              .build();
    }
}
