package ezenweb.web.service;

import ezenweb.web.domain.file.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    // * 첨부파일 저장 할 경로 [ 1. 배포 전, 2. 배포 후]
    public String path = "C:\\java\\";
    //default String path = "C:\\java\\"; productService와 같은 패키지임으로 default도 가능

    public FileDto fileupload(MultipartFile multipartFile){ //첨부파일 1개만 / 여러개일 경우 List<MultipartFile> 라고하면 된다.

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
    @Autowired
    private HttpServletResponse httpServletResponse; //http 응답 객체
    
    
    public void filedownload( String uuidFile){ //spring 다운로드 관한 API 없음
        String pathFile = path + uuidFile; //경로+uuid 파일명 : 실제 파일이 존재한는 위치
        
        try{
            //1, 다운로드 형식 구성
            httpServletResponse.setHeader(
                    "Content-Disposition", //헤더 구성[브라우저의 다운로드 형식]
                    "attachment;filename = " + URLEncoder.encode(uuidFile.split("_")[1],"UTF-8"));// 다운로드시 표시될 이름);
        
            //2. 다운로드 스트림 구성
            File file = new File(pathFile); // 다운로드할 파일의 경로에서 파일 객체화

            //3. 입력 스트림[서버가 먼저 다운로드 할 파일의 바이트 읽어오기 : 클라이언트가 요청한 형식]
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[(int)file.length()]; //file.length() : long으로 나옴
            fin.read(bytes); //읽어온 바이트들을 bytes배열에 저장


            //4. 출력 스트림 [ 서버가 읽어온 바이트를 출력할 스트림 : 현재 서비스 요청한 클라이언트에게]
            BufferedOutputStream fout = new BufferedOutputStream(httpServletResponse.getOutputStream());

            fout.write(bytes); //입력 스트림에서 읽어온 바이트 배열을 내보내기
            fout.flush(); //스트림 메모리 초기화
            fout.close(); //스트림 닫기
            fin.close();//스트림 닫기
            
        }catch (Exception e){
            log.info("첨부파일 다운로드 에러" + e.getMessage());
        }

    }
}
