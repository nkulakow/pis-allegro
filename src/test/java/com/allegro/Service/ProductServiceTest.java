package com.allegro.Service;

import com.allegro.Entity.MongoProduct;
import com.allegro.Entity.PostgresProduct;
import com.allegro.Repository.MongoProductRepository;
import com.allegro.Repository.PostgresProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        String category = "TestCategory";
        String generatedId = "generatedId";

        when(postgresProductRepository.save(any(PostgresProduct.class))).thenReturn(new PostgresProduct(generatedId, name, category));
        when(mongoProductRepository.insert(any(MongoProduct.class))).thenReturn(new MongoProduct(generatedId, name, category));

        productService.addProduct(name, category);

        verify(postgresProductRepository, times(1)).save(any(PostgresProduct.class));
        verify(mongoProductRepository, times(1)).insert(any(MongoProduct.class));
    }

    @Test
    void getMongoProductsTest() {
        List<MongoProduct> expectedMongoProducts = Arrays.asList(new MongoProduct("1", "Product1", "Category1"),
                new MongoProduct("2", "Product2", "Category2"));

        when(mongoProductRepository.findAll()).thenReturn(expectedMongoProducts);

        List<MongoProduct> actualMongoProducts = productService.getMongoProducts();

        assertEquals(expectedMongoProducts, actualMongoProducts);
    }
}
