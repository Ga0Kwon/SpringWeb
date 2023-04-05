package ezenweb.example.day03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//처음 켰을때 컴포넌트를 스캔함
@SpringBootApplication //스트링 부트 시작 [!! 컴포넌트를 스캔해서 빈에 등록]
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class, args);
    }
}
