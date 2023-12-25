package com.allegro;

import com.allegro.Service.CategoryService;
import com.allegro.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AllegroApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;

    @Test
    void contextLoads() {
    }

    @Test
    void sayHelloTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                        .param("name", "John")
                        .param("description", "Electronics"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("hello.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("name", "John"))
                .andExpect(MockMvcResultMatchers.model().attribute("des", "Electronics"));
    }

}
