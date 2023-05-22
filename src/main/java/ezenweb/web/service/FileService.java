package ezenweb.web.service;

import ezenweb.web.domain.file.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
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
    // 1. 단순 다운로드, 업로드 시 
    // public String path = "C:\\java\\";
    //default String path = "C:\\java\\"; productService와 같은 패키지임으로 default도 가능
    // 2. 업로드, 다운로드, 리액트 리소스 접근시
    //JS[react] 로컬드라이브[C] 접근 불가능 ---> 리액트 서버에 등록
    //springboot + react 통합
    //spring build --> spring resources --> spring build --> 프로젝트내 build
    //public String path = "C:\\Users\\504\\IdeaProjects\\SpringWeb\\build\\resources\\main\\static\\static\\media\\";

/*    // ***** application.properties 에 버킷 설정값을 가져와서 변수에 저장[서비스에 보완에 관련된 코드 숨기기]
    @Value("${cloud.aws.s3.bucket}") //lombok 아님
    private String bucket; //버킷 명

    // ***** application.properties 에 버킷 설정값을 가져와서 변수에 저장[서비스에 보완에
    @Value("${cloud.aws.s3.bucket.url}") //lombok 아님
    private String defaultURL; //버킷 저장 경로

    //2. S3 업로드 함수 선언
    public void uploadS3(String uuidFile, File file){
        //1) aws s3 전송 객체 생성
        TransferManager transferManager = new TransferManager(amazonS3Client);
        //2) 요청 객체
        // putObjectRequest = new PutObjectRequest("버킷명", 파일명, 업로드할 파일)
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uuidFile, file);

        //3) 업로드할 객체 생성[대기]
        Upload upload = transferManager.upload(putObjectRequest);

        //4) 업로드 객체 업로드 시;ㄹ행
        try{
            upload.waitForCompletion();
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }*/


    //3. AWS S3 리소스 업로드, 다운로드
    // 업로드[복사 - 바이트 개념] => 바이트를 복사해서 이동하는 개념
    // 1) S3 객체 생성
   /* @Autowired => 그레들에 amazon 추가해줘야함
    private AmazonS3Client amazonS3Client

     public FileDto fileupload(MultipartFile multipartFile){
        String s3url = null;

        //1. 첨부파일 존재하는지 확인
        if(!multipartFile.getOriginalFilename().equals("")){

            //* 만약에 다른 이미지인데 파일이 동일하면 중복 발생 [ 색별 불가 ]
            String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
            //2, 경로 + 파일명 조합해서 file 클래스 생성 [ 왜 ??? 파일??? => transferTo()가 File 객체만 받음]
            File file = new File(path + fileName);

            //3. 업로드
            // multipartFile.transferTo(저장할 file 클래스의 객체를 넣어야한다.);
            try {
                multipartFile.transferTo(file);
                //*** S3 업로드 함수 선언
                uploadS3(fileName, file);
                // 업로드 된 S3 리소스 경로
               s3url = defaultUrl + fileName;
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
    }*/

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
            File file = new File(System.getProperty("user.dir") + fileName);
            //System.getProperty("user.dir") : 현재 실행되는 서버에 컴퓨터 루트 경로

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
