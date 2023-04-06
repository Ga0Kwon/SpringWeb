package ezenweb.example.day04.domain.dto;

import ezenweb.example.day04.domain.entity.proudct.ProductEntity;
import lombok.*;

@Data //getter setter toString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    //Dto은 왔다갔다하는 용도이기 때문에 lombok을 사용
    // 저장X => 그냥 데이터들이 움직일때 사용하는 용도
    private int pno;
    private String pname;
    private String pcontent;

    //ProdcctEnttityRepository의 save()는 dto를 못넣어서
    public ProductEntity toEntity(){
        return ProductEntity.builder()
              .pno(pno)
              .pname(pname)
              .pcontent(pcontent)
              .build();
    }
}
