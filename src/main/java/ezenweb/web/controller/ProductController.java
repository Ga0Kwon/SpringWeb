package ezenweb.web.controller;

import ezenweb.web.domain.product.ProductDto;
import ezenweb.web.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController { //리액트와 통신 역할[매핑]

    @Autowired
    private ProductService productService;

    @GetMapping("")
    private List<ProductDto> getProduct(){
        return productService.getProduct();
    }
    //Body => 객체라는 뜻 => JSON이다.
    @PostMapping("")
    private boolean postProduct(@RequestBody ProductDto productDto){
        return productService.postProduct(productDto);
    }

    @PutMapping("")
    private boolean putProduct(@RequestBody ProductDto productDto){
        return productService.putProduct(productDto);
    }

    @DeleteMapping("")
    private boolean deleteProduct(@RequestParam String id){
        return productService.deleteProduct(id);
    }
}
