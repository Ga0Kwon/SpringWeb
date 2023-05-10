package ezenweb.web.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProductImgRepository extends JpaRepository<ProductImgEntity, String> {
}
