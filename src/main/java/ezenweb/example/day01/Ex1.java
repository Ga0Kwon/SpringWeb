package ezenweb.example.day01;

public class Ex1 {
    public static void main(String[] args) {

        //일반 DTO
        UseDTO dto1 = new UseDTO(1, "qwe", "qwe", 100, "010-3333-3333");

        System.out.println("dto1 : " + dto1.toString()); //toString()안쓰면 주소값이 나옴(dto1의)

        //Lombok [@NoArgsConstructor : 빈생성자]
        LombokDto lombokDto1 = new LombokDto();

        //Lombok [@AllArgsConstructor : 풀생성자]
        LombokDto lombokDto2 = new LombokDto(1, "asd", "asd", 123, "010-9999-9999");

        //Lombok [@Setter@Getter]
        System.out.println("Getter : " + lombokDto2.getMid());
        lombokDto2.setMid("asd123");
        System.out.println("Getter after setter : " + lombokDto2.getMid());

        //Lombok [@ToString]
        System.out.println("toString() : " + lombokDto2.toString());

        //Lombok [@Builder] ******
        LombokDto lombokDto3 = LombokDto.builder()
                .mno(1)
                .mid("zxc")
                .mpw("zxc")
                .mpoint(110)
                .phone("010-1111-1111")
                .build();

        System.out.println("lombokDto3 : " + lombokDto3.toString());

        //생성자 안쓰고 아이디와 패스워드가 저장된 객체 생성
        //객체 생성시 매개변수 개수 상관X 순서 상관X
        LombokDto lombokDto4 = LombokDto.builder()
                .mid("zxc")
                .mpw("zxc")
                .build();

        System.out.println("lombokDto4 : " + lombokDto4.toString());
    }
}
