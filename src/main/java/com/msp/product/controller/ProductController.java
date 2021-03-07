package com.msp.product.controller;

import com.msp.product.dto.ProductDTO;
import com.msp.product.entities.Product;
import com.msp.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto){
        Product obj = Product.builder(dto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProductDTO(obj));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable(value = "id") String id, @Valid @RequestBody ProductDTO dto){
        Product obj = Product.builder(dto);
        obj.setId(id);
        obj = service.update(obj);

        if (obj == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(new ProductDTO(obj));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<Product> list = service.findAll();
        List<ProductDTO> listDto = list.stream().map(ProductDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(listDto);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<ProductDTO>> search(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "min_price", required = false) Double minPrice,
            @RequestParam(value = "max_price", required = false) Double maxPrice
    ) {
        List<Product> list = service.search(query, minPrice, maxPrice);
        List<ProductDTO> listDto = list.stream().map(ProductDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable(value = "id") String id) {
        Optional<Product> obj = service.findById(id);
        if (obj.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new ProductDTO(obj.get()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(value = "id") String id) {
        Optional<Product> obj = service.deleteById(id);

        if (obj.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }

}
