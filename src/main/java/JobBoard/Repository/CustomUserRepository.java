package JobBoard.Repository;

import JobBoard.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserRepository extends JpaRepository<CustomUser, Integer> {
    CustomUser findByUsername(String username);
}
