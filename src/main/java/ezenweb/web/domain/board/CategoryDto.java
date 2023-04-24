package ezenweb.web.domain.board;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CategoryDto {
    private int cno;
    private String cname;
}
