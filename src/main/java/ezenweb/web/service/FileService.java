package ezenweb.web.service;

import ezenweb.web.domain.file.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    public FileDto fileupload(MultipartFile multipartFile){ //첨부파일 1개만 / 여러개일 경우 List<MultipartFile> 라고하면 된다.

        // * 첨부파일 저장 할 경로 [ 1. 배포 전, 2. 배포 후]
        String path = "C:\\java\\";

        log.info("file upload : " + multipartFile);
        log.info("file upload : " + multipartFile.getOriginalFilename()); // 실제 첨부파일 파일명
        log.info("file upload : " + multipartFile.getName()); //input name
        log.info("file upload : " + multipartFile.getContentType()); //첨부파일 확장자
        log.info("file upload : " + multipartFile.getSize()); //파일 사이즈(바이트)

        //1. 첨부파일 존재하는지 확인
        if(!multipartFile.getOriginalFilename().equals("")){ //첨부파일이 존재하면

            //* 만약에 다른 이미지인데 파일이 동일하면 중복 발생 [ 색별 불가 ]
            String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
            //2, 경로 + 파일명 조합해서 file 클래스 생성 [ 왜 ??? 파일??? => transferTo()가 File 객체만 받음]
            File file = new File(path + fileName);

            //3. 업로드
            // multipartFile.transferTo(저장할 file 클래스의 객체를 넣어야한다.);
            try {
                multipartFile.transferTo(file);

            } catch (IOException e) {

                log.info("file upload error : " + e.getMessage());
            }

            //4. 반환
            return FileDto.builder()
                    .originalFilename(multipartFile.getOriginalFilename())
                    .uuidFilename(fileName)
                    .sizeKb(multipartFile.getSize()/1024 + "KB")
                    .build();
        }
        return null;
    }

    public void filedownload( String fiulepath){

    }
}
