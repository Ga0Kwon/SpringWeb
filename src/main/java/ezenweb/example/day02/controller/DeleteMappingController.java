package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/delete")
public class DeleteMappingController {
    //get, Delete => @RequestBody가 없다.
   /* @DeleteMapping("/method1")
    public ParamDto method1(@RequestBody ParamDto dto) {
        log.info("method1: {}", dto);
        return dto;
    }*/

    @DeleteMapping("/method1")
    public ParamDto method1(ParamDto dto) {
        log.info("method1: {}", dto);
        return dto;
    }


   /* @DeleteMapping("/method2")
    public Map<String, String> method2(@RequestBody Map<String, String> map) {
        log.info("method2: {}", map);
        return map;
    }*/

    @DeleteMapping("/method2")
    public Map<String, String> method2( @RequestParam Map<String, String> map) {
        log.info("method2: {}", map);
        return map;
    }
}
