package ezenweb.web.domain.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {
    private String originalFilename; //실제 파일 이름
    private String uuidFilename; //uuid가 포함된 파일 이름(실질적으로 파일이 저장된 이름) => 식별이 포함된
    private String sizeKb; // 용량 Kb

}
