package stocks.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stocks.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
