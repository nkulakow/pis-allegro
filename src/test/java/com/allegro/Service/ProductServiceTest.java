package com.allegro.Service;

import com.allegro.DTO.ProductDTO;
import com.allegro.Document.MongoProduct;
import com.allegro.Entity.CartItem;
import com.allegro.Entity.Category;
import com.allegro.Entity.PostgresProduct;
import com.allegro.Entity.User;
import com.allegro.Repository.MongoProductRepository;
import com.allegro.Repository.PostgresProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private MongoProductRepository mongoProductRepository;

    @Mock
    private PostgresProductRepository postgresProductRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        postgresProductRepository = mock(PostgresProductRepository.class);
        mongoProductRepository = mock(MongoProductRepository.class);
        UserService userService = mock(UserService.class);
        productService = new ProductService(mongoProductRepository, postgresProductRepository, userService, categoryService);
    }


    @Test
    void addProductTest() {
        String name = "TestProduct";
        Category category = new Category("TestCategory");
        String generatedId = "generatedId";
        int price = 2;
        int quantity = 2;
        String description = "Test desc";
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(category);
        var user = new User("123@gmail.com", "password", "John", "Doe", null, null);


        when(postgresProductRepository.save(any(PostgresProduct.class))).thenReturn(new PostgresProduct(generatedId, name, categories, price, quantity, user));
        when(mongoProductRepository.insert(any(MongoProduct.class))).thenReturn(new MongoProduct(generatedId, description, null));

        try {
            productService.addProduct(user, name, categories , price, quantity, description, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        verify(postgresProductRepository, times(1)).save(any(PostgresProduct.class));
        verify(mongoProductRepository, times(1)).insert(any(MongoProduct.class));
    }

    @Test
    void getMongoProductsTest() {
        List<MongoProduct> expectedMongoProducts = Arrays.asList(new MongoProduct("1",  "Category1", null),
                new MongoProduct("2", "Category2", null));

        when(mongoProductRepository.findAll()).thenReturn(expectedMongoProducts);

        List<MongoProduct> actualMongoProducts = productService.getMongoProducts();

        assertEquals(expectedMongoProducts, actualMongoProducts);
    }

    @Test
    void getProductsTest(){
        Category category = new Category("TestCategory");
        var user = new User("123@gmail.com", "password", "John", "Doe", null, null);
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(category);
        List<MongoProduct> expectedMongoProducts = Arrays.asList(new MongoProduct("1", "desc1", null),
                new MongoProduct("2",  "desc2", null));
        List<PostgresProduct> expectedPostgresProducts = Arrays.asList(new PostgresProduct("1", "Product1", categories, 1,2, user),
                new PostgresProduct("2", "Product2", categories, 4, 4, user));

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
        var user = new User("123@gmail.com", "password", "John", "Doe", null, null);
        categories.add(category);
        String text = "text";
        List<MongoProduct> expectedMongoProducts = Arrays.asList(new MongoProduct("1",  "desc1", null),
                new MongoProduct("2",  "desc2", null));
        List<PostgresProduct> expectedPostgresProducts = Arrays.asList(new PostgresProduct("1", "Product1", categories, 1, 2, user),
                new PostgresProduct("2", "Product2", categories, 4, 4, user));

        when(mongoProductRepository.findAllBy(any(), any())).thenReturn(expectedMongoProducts);
        when(postgresProductRepository.searchProducts(any())).thenReturn(expectedPostgresProducts);
        when(mongoProductRepository.findById("1")).thenReturn(Optional.of(expectedMongoProducts.get(0)));
        when(mongoProductRepository.findById("2")).thenReturn(Optional.of(expectedMongoProducts.get(1)));

        List<ProductDTO> actualProducts = productService.findByText(text);
        verify(mongoProductRepository, times(1)).findAllBy(any(), any());
        verify(postgresProductRepository, times(1)).searchProducts(text);
    }

    @Test
    public void testGetWholeProductByPostgres() {
        var user = new User("123@gmail.com", "password", "John", "Doe", null, null);
        PostgresProduct postgresProduct = new PostgresProduct("123", "name", null, 1, 1, user);
        MongoProduct mongoProduct = new MongoProduct("123", "description", null);
        when(mongoProductRepository.findById("123")).thenReturn(Optional.of(mongoProduct));

        ProductDTO result = productService.getWholeProductByPostgres(postgresProduct);

        assertEquals(postgresProduct, result.getPostgres());
        assertEquals(mongoProduct, result.getMongo());
    }

    @Test
    public void testGetWholeSoldProductsByPostgres() {
        var user = new User("123@gmail.com", "password", "John", "Doe", null, null);
        PostgresProduct soldProduct = new PostgresProduct("123", "name", null, 1, 1, user);
        user.addSoldProduct(soldProduct);
        MongoProduct mongoProduct = new MongoProduct("123", "description", null);;
        when(mongoProductRepository.findById(any())).thenReturn(Optional.of(mongoProduct));

        List<ProductDTO> result = productService.getWholeSoldProductsByPostgres(user);

        assertEquals(1, result.size());
        assertEquals(soldProduct, result.get(0).getPostgres());
        assertEquals(mongoProduct, result.get(0).getMongo());

    }

    @Test
    void updateProductTest() {
        String name = "TestProduct";
        Category category = new Category("TestCategory");
        String generatedId = "generatedId";
        int price = 2;
        int quantity = 2;
        String description = "Test desc";
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(category);
        var user = new User("123@gmail.com", "password", "John", "Doe", null, null);
        var postProd = new PostgresProduct(generatedId, name, categories, price, quantity, user);
        var mongoProd = new MongoProduct(generatedId, description, null);
        var productDTO = new ProductDTO(postProd, mongoProd);
        productDTO.setDescription("new desc");
        var newPostProd = productDTO.getPostgres();
        var newMongoProd = productDTO.getMongo();

        when(postgresProductRepository.save(any(PostgresProduct.class))).thenReturn(newPostProd);
        when(mongoProductRepository.save(any(MongoProduct.class))).thenReturn(newMongoProd);

        try {
            productService.addProduct(user, name, categories , price, quantity, description, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productService.updateProduct(productDTO);

        verify(postgresProductRepository, times(2)).save(any(PostgresProduct.class));
    }

    @Test
    void getWholeProductByPostgreswCartItem() {
        var user = new User("123@gmail.com", "password", "John", "Doe", null, null);
        var postProd = new PostgresProduct("123", "name", null, 1, 1, user);
        var mongoProd = new MongoProduct("123", "description", null);
        CartItem cartItem = new CartItem(user, postProd, 1);

        when(mongoProductRepository.findById("123")).thenReturn(Optional.of(mongoProd));

        ProductDTO result = productService.getWholeProductByPostgres(cartItem);
        assertEquals(postProd, result.getPostgres());
        assertEquals(mongoProd, result.getMongo());
    }

    @Test
    void getWholeProductByPostgreswUser() {
        var user = new User("123@gmail.com", "password", "John", "Doe", null, null);
        var postProd = new PostgresProduct("123", "name", null, 1, 1, user);
        var mongoProd = new MongoProduct("123", "description", null);
        CartItem cartItem = new CartItem(user, postProd, 1);
        user.addCartItem(cartItem);

        when(mongoProductRepository.findById("123")).thenReturn(Optional.of(mongoProd));

        List<ProductDTO> result = productService.getWholeCartItemsByPostgres(user);
        assertEquals(postProd, result.get(0).getPostgres());
        assertEquals(mongoProd, result.get(0).getMongo());
    }
}
