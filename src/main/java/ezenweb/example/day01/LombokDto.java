package ezenweb.example.day01;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LombokDto {
    private int mno;
    private String mid;
    private String mpw;
    private long mpoint;
    private String phone;
    
    //dto -> entity로 변환
    public TestMember2 toEntity(){
        return TestMember2.builder()
                .mno(this.mno)
                .mid(this.mid)
                .mpw(this.mpw)
                .mpoint(this.mpoint)
                .mphone(this.phone)
                .build();
    }

}
