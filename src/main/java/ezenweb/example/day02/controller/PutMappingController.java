package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/put")
public class PutMappingController {
    //post, put => body
    @PutMapping("/method1")
    public ParamDto method1(@RequestBody ParamDto dto) {
        log.info("method1: {}", dto);
        return dto;
    }

    @PutMapping("/method2")
    public Map<String, String> method2(@RequestBody Map<String, String> map) {
        log.info("method2: {}", map);
        return map;
    }
    
    //안됨 => RequestBody 아니라서
 /*   @PutMapping("/method3")
    public ParamDto method3(ParamDto dto) {
        log.info("method3: {}", dto);
        return dto;
    }*/
}
