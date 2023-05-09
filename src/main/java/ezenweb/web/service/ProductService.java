package ezenweb.web.service;

import ezenweb.web.domain.product.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Transactional
    public List<ProductDto> getProduct(){
        return null;
    }

    @Transactional
    public boolean postProduct(ProductDto productDto){
        return false;
    }

    @Transactional
    public boolean putProduct(ProductDto productDto){
        return false;
    }

    @Transactional
    public boolean deleteProduct(int id){
        return false;
    }
}
