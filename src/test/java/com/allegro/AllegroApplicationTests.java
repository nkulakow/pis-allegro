package com.allegro;

import com.allegro.DTO.ProductDTO;
import com.allegro.Entity.CartItem;
import com.allegro.Entity.User;
import com.allegro.Service.CategoryService;
import com.allegro.Service.ProductService;
import com.allegro.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
class AllegroApplicationTests {
// Tests for frontend

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private UserService userService;


    @Test
    void contextLoads() {
    }


    @Test
    void getLoginPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login.html"));
    }

    @Test
    void getRegisterPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register.html"));
    }

    @Test
    void getMainPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mainPage"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("main-page.html"));
    }

    @Test
    void getSearchPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fulltext-search"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("fulltext-search.html"));
    }

    @Test
    void getPreviousBuysPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/previous-buys"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("previous-buys.html"));
    }

    @Test
    void getAddProductPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/manage-products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("manage-products.html"));
    }

    @Test
    void getCartPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("cart.html"));
    }

    @Test
    void getAllProductsPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/view-all-products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("view-all-products.html"));
    }


    @Test
    void getProductInfoPageTest() throws Exception {
        String productId = "123";
        mockMvc.perform(MockMvcRequestBuilders.get("/product-info/{productId}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("product-info.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("productId", productId));
    }

    @Test
    void searchForProductsTest() throws Exception {
        // Arrange
        List<ProductDTO> mockedProductList = new ArrayList<>();
        when(productService.findByText(anyString())).thenReturn(mockedProductList);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/products/fulltext-search-results")
                        .param("searchPhrase", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the ProductService method was called with the correct argument
        verify(productService, times(1)).findByText("test");
    }
    @Test
    void addProductTest() throws Exception {
        // Arrange
        when(userService.getUserByEmail(anyString())).thenReturn(new User());
        when(categoryService.getCategoriesByNames(anyList())).thenReturn(new ArrayList<>());

        MockMultipartFile file = new MockMultipartFile("photo", "testPhoto".getBytes());
        doNothing().when(productService).addProduct(any(User.class), anyString(), anyList(), anyFloat(), anyInt(), anyString(), eq(file));
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                        .param("name", "TestProduct")
                        .param("categories", "category1", "category2")
                        .param("price", "10.0")
                        .param("quantity", "5")
                        .param("description", "Test description")
                        .param("photo", "testPhoto")
                        .sessionAttr("login", "testUser"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the ProductService method was called with the correct arguments
        verify(productService, times(1)).addProduct(any(User.class), eq("TestProduct"), anyList(), eq(10.0f), eq(5), eq("Test description"),  eq(null));
    }


    @Test
    void getCategoriesTest() throws Exception {
        // Arrange
        when(categoryService.getAllCategoryNames()).thenReturn(Arrays.asList("category1", "category2"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/get-categories"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[\"category1\", \"category2\"]"));
    }

    @Test
    void getProductInfoTest() throws Exception {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("TestProduct");
        productDTO.setCategories(new ArrayList<>());
        when(productService.getProductById(anyString())).thenReturn(productDTO);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/get-product-info")
                        .param("productId", "testProductId"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"name\":\"TestProduct\"}"));
    }

    @Test
    void insertCategoriesTest() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/add-some-categories"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the CategoryService method was called to add categories
        verify(categoryService, times(7)).addCategory(anyString());
    }

    @Test
    void getUserProductsTest() throws Exception {
        // Arrange
        when(userService.getUserByEmail(anyString())).thenReturn(new User());
        when(productService.getProductByUser(anyString())).thenReturn(new ArrayList<>());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/get-user-products")
                        .sessionAttr("login", "testUser"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void getProductsInCategoriesTest() throws Exception {
        // Arrange
        when(productService.getProductByCategories(anyList())).thenReturn(new ArrayList<>());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/products/get-all-in-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"category1\", \"category2\"]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void editProductTest() throws Exception {
        // Arrange
        when(productService.getProductById(anyString())).thenReturn(new ProductDTO());
        doNothing().when(productService).updateProduct(any(ProductDTO.class));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/products/edit-product")
                        .param("name", "NewProduct")
                        .param("categories", "newCategory")
                        .param("price", "20.0")
                        .param("description", "New description")
                        .param("id", "testProductId")
                        .param("quantity", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the ProductService method was called with the correct arguments
        verify(productService, times(1)).updateProduct(any(ProductDTO.class));
    }

    @Test
    void addToCartTest() throws Exception {
        // Arrange
        when(userService.getUserByEmail(anyString())).thenReturn(new User());
        when(productService.getProductById(anyString())).thenReturn(new ProductDTO());
        doNothing().when(userService).addCartItem(any(User.class), any(CartItem.class));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/products/add-to-cart")
                        .param("productId", "testProductId")
                        .sessionAttr("login", "testUser"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the UserService method was called to add a cart item
        verify(userService, times(1)).addCartItem(any(User.class), any(CartItem.class));
    }

    @Test
    void getCartItemsTest() throws Exception {
        // Arrange
        User user = new User();
        user.setCartItems(new ArrayList<>());
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(userService.getUserByEmail("null")).thenReturn(null);
        when(productService.getProductById(anyString())).thenReturn(new ProductDTO());


        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/get-cart-items")
                        .sessionAttr("login", "testUser"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void loginSuccessTest() throws Exception {
        when(userService.login("testUser@example.com", "password123")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginVerify")
                        .param("email", "testUser@example.com")
                        .param("password", "password123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("main-page"));

        verify(userService, times(1)).login("testUser@example.com", "password123");
    }

    @Test
    void loginFailureTest() throws Exception {
        when(userService.login("invalidUser@example.com", "invalidPassword")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginVerify")
                        .param("email", "invalidUser@example.com")
                        .param("password", "invalidPassword"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.model().attribute("paragraphText", "Invalid login or password"));

        verify(userService, times(1)).login("invalidUser@example.com", "invalidPassword");
    }

    @Test
    void registerSuccessTest() throws Exception {
        when(userService.getUserByEmail("newUser@example.com")).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .param("name", "John")
                        .param("surname", "Doe")
                        .param("email", "newUser@example.com")
                        .param("password", "newPassword"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));

        verify(userService, times(1)).addUser(any());
    }

    @Test
    void registerUserAlreadyExistsTest() throws Exception {
        when(userService.getUserByEmail("existingUser@example.com")).thenReturn(new User());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .param("name", "Jane")
                        .param("surname", "Doe")
                        .param("email", "existingUser@example.com")
                        .param("password", "existingPassword"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.model().attribute("paragraphText", "User already exists"));

        verify(userService, never()).addUser(any());
    }

    @Test
    void buyCartItemsSuccessTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("login", "testUser@example.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/buy-cart-items").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Successfully bought products"));

        verify(userService, times(1)).buyCartItems(any());
    }



}
