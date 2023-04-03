package ezenweb.example.day01;

public class Ex2 {
    public static void main(String[] args) {
        
        // 1. builder 패턴을 이용한 객체 생성
        LombokDto dto = LombokDto.builder()
                .mno(1)
                .mid("qwe123")
                .mpw("qwe1234")
                .phone("010-1111-1111")
                .mpoint(123)
                .build();

        //2. @toString
        System.out.println("lombokDto : " + dto.toString());

        //3. Dao를 이용한 DB처리
        Dao dao = new Dao();
        
        //4. DB에 저장하기
        boolean result = dao.setmember(dto);
        
        if(result){
            System.out.println("DB 저장 완료");
        }else{
            System.out.println("DB 저장 실패");
        }
        
        //5. JPA 이용한 DB처리

    }
}
