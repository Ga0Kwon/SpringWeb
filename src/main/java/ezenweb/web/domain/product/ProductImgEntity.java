package ezenweb.web.domain.product;

import ezenweb.web.domain.file.FileDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="productimg")
public class ProductImgEntity {
    //1. 이미지 식별 이름
    @Id
    private String uuidFile;

    //2. 이미지 이름
    @Column
    private String originalFileName;

    //3. 제품 객체 [fk]
    @ManyToOne // fk 필드 선언시
    @JoinColumn(name="id") //일치하도록 product(DB테이블에 표시될 FK 필드명)
    @ToString.Exclude //무한 루프 돌지 않도록 -> 순환참조 방지(양방향 필수!!)
    private ProductEntity productEntity;

    public FileDto toFileDto(){
        return FileDto.builder()
                .uuidFilename(this.uuidFile)
                .originalFilename(this.originalFileName)
                .build();
    }
}
