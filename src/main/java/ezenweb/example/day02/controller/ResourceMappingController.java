package ezenweb.example.day02.controller;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//reource : html, css, js
@RequestMapping("/gethtml")
@RestController
public class ResourceMappingController {
    @GetMapping("/test1") //GET 방식
    public Resource gettest(){
        return new ClassPathResource("templates/test1.html");
    }

    //Resource : import org.springframework.core.io.Resource;
    // return new ClassPathResource("HTML 경로/파일명.html");
}
