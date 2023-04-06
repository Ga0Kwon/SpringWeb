package ezenweb.example.day04.domain.entity.proudct;

import ezenweb.example.day04.domain.dto.ProductDto;
import ezenweb.example.day04.domain.entity.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity //엔티티 = DB 매핑
@Table(name = "item")
@NoArgsConstructor@AllArgsConstructor@Data@Builder
public class ProductEntity extends BaseTime {
    //앤티티는 component는 아님
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pno;

    @Column
    private String pname;
    @Column
    private String pcontent;

    //entity ----> dto [서비스에서 사용하기 위함]
    public ProductDto toDto() {
        return ProductDto.builder()
              .pno(this.pno)
              .pname(this.pname)
              .pcontent(this.pcontent)
              .build();
    }
}
