package Root.Repository;

import Root.Model.Card;
import Root.Model.Client;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
public List<Card> findByClient(Client client);
    public Card findByNumber(String number);
}

