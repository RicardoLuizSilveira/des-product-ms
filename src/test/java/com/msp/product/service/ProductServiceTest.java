package com.msp.product.service;

import com.msp.product.entities.Product;
import com.msp.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ProductServiceTest {

    private static final String NAME = "default name";
    private static final String DESCRIPTION = "default description";
    private static final Double PRICE = 50.00;
    private static final String ID = "id1";

    @MockBean
    ProductRepository repo;

    @Autowired
    ProductService service;

    @BeforeEach
    void setUp() {
        BDDMockito.given(repo.findById(Mockito.anyString())).willReturn(Optional.of(new Product(ID, NAME, DESCRIPTION, PRICE)));
    }

    @Test
    void testFindProduct_Success() {
        Optional<Product> p1 = service.findById(ID);
        assertEquals(NAME, p1.get().getName());
    }
}
