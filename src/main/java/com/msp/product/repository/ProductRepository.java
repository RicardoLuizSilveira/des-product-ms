package com.msp.product.repository;

import com.msp.product.entities.Product;
import com.msp.product.utils.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    default List<Product> search(String query, Double minPrice, Double maxPrice){
        return searchUpperCaseText(StringUtils.toUpperCase(query), minPrice, maxPrice);
    }

    @Query("SELECT DISTINCT obj FROM Product obj " +
            "WHERE ( " +
            "(:query is null or UPPER(obj.name) LIKE %:query% or UPPER(obj.description) LIKE %:query%) and " +
            "(:minPrice is null or obj.price >= :minPrice) and " +
            "(:maxPrice is null or obj.price <= :maxPrice) " +
            ")")
    List<Product> searchUpperCaseText(@Param("query") String query,
                         @Param("minPrice") Double minPrice,
                         @Param("maxPrice") Double maxPrice);

}
