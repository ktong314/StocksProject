package stocks.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stocks.domain.StockObject;

@Repository
public interface StockRepository extends JpaRepository<StockObject, Long>{


}
