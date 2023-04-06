package ezenweb.example.day04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //감시하는 기능을 키겠다! (EntityListeners사용하려면 써야함)
public class AppStart { //시작점
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class, args);
    }
}
