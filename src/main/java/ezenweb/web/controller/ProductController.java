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
    //Body => 객체라는 뜻 => JSON이다. => form 전송이기 때문에 @ReqeustBody타입이 json타입이다.
    //객체로 받을때 -> mulitpart를 지원X
    @PostMapping("")
    private boolean postProduct(ProductDto productDto){
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

/*
 axios

 1. 객체 전송 [post, put]
    axios.post('url' ,object)
           ----> @requestBody
 2. 폼 전송 [post 필수]
    axios.post('url' ,object)
           ----> DTO 받을 때는 어노테이션 생략
           ----> requestParams("form 필드이름") : 폼 내 필드 하나의 데이터
 3. 쿼리스트링 전송 [get, post, put, delete]
    axios.post('url' ,{params : {필드명 : 데이터, 필드명 : 데이터})
    axios.post('url' ,{params : object})
           ----> @RequestParams
 4. 매개변수 전송 [get, post, put, delete]
    axios.post('url/데이터1/데이터2')

*/
