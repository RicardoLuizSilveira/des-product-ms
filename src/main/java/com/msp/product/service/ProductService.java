package com.msp.product.service;

import com.msp.product.entities.Product;
import com.msp.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository repo;

    public Optional<Product> findById(String id){
        return repo.findById(id);
    }

    public Product insert(Product obj) {
        obj.setId(UUID.randomUUID().toString());
        return repo.save(obj);
    }

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Optional<Product> deleteById(String id) {
        Optional<Product> optional = this.findById(id);
        optional.ifPresent(x -> repo.delete(x));
        return optional;
    }

    public Product update(Product obj) {
        Optional<Product> optional = this.findById(obj.getId());
        if (optional.isEmpty()) return null;

        Product newObj = optional.get();
        this.updateData(newObj, obj);
        return repo.save(newObj);
    }

    private void updateData(Product newObj, Product obj) {
        newObj.setName(obj.getName());
        newObj.setDescription(obj.getDescription());
        newObj.setPrice(obj.getPrice());
    }

    public List<Product> search(String query, Double minPrice, Double maxPrice) {
        return repo.search(query, minPrice, maxPrice);
    }
}
