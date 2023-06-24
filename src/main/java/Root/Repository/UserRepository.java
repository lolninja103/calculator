package Root.Repository;

import Root.Model.Client;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Client, Long> {
public boolean existsByUsername(String username);
public Client findByUsername (String username);
}
