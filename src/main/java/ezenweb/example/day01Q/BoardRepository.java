package ezenweb.example.day01Q;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoradEntity, Integer> {
}
