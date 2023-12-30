package com.allegro.Service;

import com.allegro.DTO.ProductDTO;
import com.allegro.Document.MongoProduct;
import com.allegro.Entity.Category;
import com.allegro.Entity.PostgresProduct;
import com.allegro.Repository.MongoProductRepository;
import com.allegro.Repository.PostgresProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private MongoProductRepository mongoProductRepository;

    @Mock
    private PostgresProductRepository postgresProductRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        postgresProductRepository = mock(PostgresProductRepository.class);
        mongoProductRepository = mock(MongoProductRepository.class);
        productService = new ProductService(mongoProductRepository, postgresProductRepository);
    }

    @Test
    void addProductTest() {
        String name = "TestProduct";
        Category category = new Category("TestCategory");
        String generatedId = "generatedId";
        float price = 2.3F;
        String description = "Test desc";
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(category);

        when(postgresProductRepository.save(any(PostgresProduct.class))).thenReturn(new PostgresProduct(generatedId, name, categories, price));
        when(mongoProductRepository.insert(any(MongoProduct.class))).thenReturn(new MongoProduct(generatedId, description));

        productService.addProduct(name, categories , price, description);

        verify(postgresProductRepository, times(1)).save(any(PostgresProduct.class));
        verify(mongoProductRepository, times(1)).insert(any(MongoProduct.class));
    }

    @Test
    void getMongoProductsTest() {
        List<MongoProduct> expectedMongoProducts = Arrays.asList(new MongoProduct("1",  "Category1"),
                new MongoProduct("2", "Category2"));

        when(mongoProductRepository.findAll()).thenReturn(expectedMongoProducts);

        List<MongoProduct> actualMongoProducts = productService.getMongoProducts();

        assertEquals(expectedMongoProducts, actualMongoProducts);
    }

    @Test
    void getProductsTest(){
        Category category = new Category("TestCategory");
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(category);
        List<MongoProduct> expectedMongoProducts = Arrays.asList(new MongoProduct("1", "desc1"),
                new MongoProduct("2",  "desc2"));
        List<PostgresProduct> expectedPostgresProducts = Arrays.asList(new PostgresProduct("1", "Product1", categories, 1.3F),
                new PostgresProduct("2", "Product2", categories, 4F));

        when(mongoProductRepository.findAll()).thenReturn(expectedMongoProducts);
        when(postgresProductRepository.findAll()).thenReturn(expectedPostgresProducts);

        ArrayList<ProductDTO> actualProducts = productService.getProducts();

        assertEquals(actualProducts.size(), 2);
        assertEquals(actualProducts.get(0).getName(), "Product1");
    }


    @Test
    void findByTextTest(){
        Category category = new Category("TestCategory");
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(category);
        String text = "text";
        List<MongoProduct> expectedMongoProducts = Arrays.asList(new MongoProduct("1",  "desc1"),
                new MongoProduct("2",  "desc2"));
        List<PostgresProduct> expectedPostgresProducts = Arrays.asList(new PostgresProduct("1", "Product1", categories, 1.3F),
                new PostgresProduct("2", "Product2", categories, 4F));

        when(mongoProductRepository.findAllBy(any(), any())).thenReturn(expectedMongoProducts);
        when(postgresProductRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(expectedPostgresProducts.get(0)));

        List<ProductDTO> actualProducts = productService.findByText(text);

        assertEquals(actualProducts.size(), 2);
        assertEquals(actualProducts.get(0).getName(), "Product1");
    }
}
