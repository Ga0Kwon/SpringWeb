package ezenweb.web.domain.todo;

import ezenweb.web.domain.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "todo")
public class TodoEntity extends BaseTime {
    //할일 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = true) //할일 내용(null값 허용X)
    private String title;

    @Column
    @ColumnDefault("false") //컬럼 기본 값 false(할일 아직 수행X)
    private boolean done;


    //toDto[출력용]
    public TodoDto toDto(){
        return TodoDto.builder()
                .id(this.id)
                .title(this.title)
                .done(this.done)
                .build();
    }
}
