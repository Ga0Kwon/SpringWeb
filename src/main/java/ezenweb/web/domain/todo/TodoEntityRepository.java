package ezenweb.web.domain.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoEntityRepository extends JpaRepository<TodoEntity, Integer>{

}
