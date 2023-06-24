package Root.Repository;

import Root.Model.Card;
import Root.Model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History,Integer> {
public List<History> findByCardTakeoff(Card card);
public List<History> findByCardDeposit(Card card);
}

