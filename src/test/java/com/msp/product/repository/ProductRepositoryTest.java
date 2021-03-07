package com.msp.product.repository;

import com.msp.product.entities.Product;
import com.msp.product.utils.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

    private static final String ID = "id1";
    public static final String NAME_INSIDE_TEST = "name inside test";
    private static final String NAME = "default name";
    private static final String DESCRIPTION = "default description";
    private static final Double PRICE = 50.00;

    @Autowired
    private ProductRepository repo;

    @BeforeEach
    void setUp() {
        Product p1 = new Product(ID, NAME, DESCRIPTION, PRICE);
        Product p2 = new Product("id2", "name 2", "description 2", 25.0);
        Product p3 = new Product("id3", "name 3", "description 3", 100.0);
        Product p4 = new Product("id4", "name 4", "description 4", 75.0);

        repo.saveAll(Arrays.asList(p1, p2, p3, p4));
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
    }

    @Test
    public void testSaveProduct_Success(){
        Product p = new Product(ID, NAME, DESCRIPTION, PRICE);
        Product response = repo.save(p);
        assertNotNull(response);
    }

    @Test
    public void testUpdateProduct_Success(){
        Product p = new Product(ID, NAME, DESCRIPTION, PRICE);
        Product response = repo.save(p);

        response.setName(NAME_INSIDE_TEST);
        Product response2 = repo.save(response);

        assertEquals(NAME_INSIDE_TEST, response2.getName());
    }

    @Test
    public void testUpdateProduct_NameNull_ThrowException(){
        Product p = new Product(ID, NAME, DESCRIPTION, PRICE);
        Product response = repo.save(p);
        response.setName(null);

        assertThrows(DataIntegrityViolationException.class, () -> repo.save(response));
    }

    @Test
    public void testUpdateProduct_DescriptionNull_ThrowException(){
        Product p = new Product(ID, NAME, DESCRIPTION, PRICE);
        Product response = repo.save(p);
        response.setDescription(null);

        assertThrows(DataIntegrityViolationException.class, () -> repo.save(response));
    }

    @Test
    public void testFindProductById_Success(){
        Product p = new Product(ID, NAME, DESCRIPTION, PRICE);
        Product response = repo.save(p);
        Optional<Product> optional = repo.findById(response.getId());
        assertTrue(optional.isPresent());
        assertEquals(response.getId(), optional.get().getId());
    }

    @Test
    public void testDeleteProductById_Success(){
        Product p = new Product(ID, NAME, DESCRIPTION, PRICE);
        Product response = repo.save(p);
        String id = response.getId();

        repo.delete(response);
        Optional<Product> optional = repo.findById(id);
        assertTrue(optional.isEmpty());
    }

    @Test
    public void testSearchProduct_JustByPartialName_ContentOnResult(){
        List<Product> list1 = repo.search("name", null, null);
        assertEquals(4, list1.size());

        List<Product> list2 = repo.search("default", null, null);
        assertEquals(1, list2.size());
    }

    @Test
    public void testSearchProduct_JustByPartialName_EmptyResult(){
        List<Product> list1 = repo.search("j@@lkjh", null, null);
        assertTrue(list1.isEmpty());
    }

    @Test
    public void testSearchProduct_JustByPartialDescription_ContentOnResult(){
        List<Product> list1 = repo.search("description", null, null);
        assertEquals(4, list1.size());
    }

    @Test
    public void testSearchProduct_JustByMinPrice_ContentOnResult(){
        List<Product> list1 = repo.search( null, 0.0, null);
        assertEquals(4, list1.size(), "Should return all products, all of them have price over zero");

        List<Product> list2 = repo.search( null, 51.0, null);
        assertEquals(2, list2.size(), "Should return two products because of minimum price filter");

        List<Product> list3 = repo.search( null, 50.0, null);
        assertEquals(3, list3.size(), "Edge test, should return three products because of minimum price filter");
    }

    @Test
    public void testSearchProduct_JustByMinPrice_EmptyResult(){
        List<Product> list1 = repo.search( null, 500.0, null);
        assertTrue(list1.isEmpty(), "List should be empty, minimum price too high");
    }


    @Test
    public void testSearchProduct_JustByMaxPrice_ContentOnResult(){
        List<Product> list1 = repo.search( null, null, 101.0);
        assertEquals(4, list1.size(), "Should return all products, maximum price is very high");

        List<Product> list2 = repo.search( null, null, 48.0);
        assertEquals(1, list2.size(), "Should return one products because of minimum price filter");

        List<Product> list3 = repo.search( null, null, 50.0);
        assertEquals(2, list3.size(), "Edge test, should return two products because of maximum price filter");
    }

    @Test
    public void testSearchProduct_JustByMaxPrice_EmptyResult(){
        List<Product> list1 = repo.search( null, null, 5.0);
        assertTrue(list1.isEmpty(), "List should be empty, minimum price too low");
    }

    @Test
    public void testSearchProduct_AllFilters_ContentOnResult(){

        Product p = new Product(ID, "Caneca", "caneca", 7.0);
        repo.save(p);

        List<Product> list1 = repo.search( "caneca", 5.0, 10.0);
        assertEquals(1, list1.size(), "Should return one, just one product satisfies the filters");
    }

    @Test
    public void testSearchProduct_AllFiltersIgnoreCase_ContentOnResult(){

        Product p = new Product(ID, "CaNeCa", "cAnEcA", 7.0);
        repo.save(p);

        List<Product> list1 = repo.search( "caneca", 5.0, 10.0);
        assertEquals(1, list1.size(), "Should return one, just one product satisfies the filters");
    }

    @Test
    public void testSearchProduct_AllNull_FindAll(){
        List<Product> list1 = repo.search( null, null, null);
        assertFalse(list1.isEmpty());
        assertEquals(4, list1.size());
    }

}
