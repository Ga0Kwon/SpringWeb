package ezenweb.example.day01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppStart {
    @Autowired
    private static TestMember2Repository testMember2Repository;

    public static void main(String[] args) {
        SpringApplication.run(AppStart.class); //현재클래스명.class

        LombokDto dto = LombokDto.builder()
                .mid("qweqwe")
                .mpw("qweqwe1")
                .build();

        testMember2Repository.save(dto.toEntity());
    }
}

/*
    JPA <---------------> DB 연동 실패
        1) application.properties 작성된 URL, 정보 오타 있거나 실제로 DB가 없거나

   [ERROR]
   Description:

    Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.

    Reason: Failed to determine a suitable driver class
*/