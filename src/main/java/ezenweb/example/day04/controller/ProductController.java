package ezenweb.example.day04.controller;

import ezenweb.example.day04.domain.dto.ProductDto;
import ezenweb.example.day04.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

//컨트롤러
@RestController
@RequestMapping("/item")
public class ProductController {
    //컨테이너에 등록된 빈 자동 주입
    @Autowired
    private ProductService service;
    //1. [REACT.JS] 사용하기 전 HTML 반환
    @GetMapping("")
    public Resource index(){
        return new ClassPathResource("templates/item.html");
    }
    //2. Restful API

    // 1.저장
    @PostMapping("/write")
    public boolean write(@RequestBody ProductDto product){
        System.out.println(product);
        return service.write(product);
    }

    //2. 수정
    @PutMapping("/update")
    public boolean update(@RequestBody ProductDto product){
        return service.update(product);
    }

    
    //3. 삭제
    @DeleteMapping("/delete")
    public boolean delete(@RequestParam int pno){
        System.out.println(pno);
        return service.delete(pno);
    }
    
    //4. 조회
    @GetMapping("/get")
    public ArrayList<ProductDto> read(){
        return service.getProduct();
    }

    //3. 유효성 검사
}
