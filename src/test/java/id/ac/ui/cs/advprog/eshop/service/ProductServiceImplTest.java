package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreate() {
        Product result = productService.create(product);

        assertEquals(product.getProductId(), result.getProductId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Iterator<Product> iterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertEquals(1, result.size());
        assertEquals(product.getProductId(), result.get(0).getProductId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(productRepository.findById("123")).thenReturn(product);

        Product result = productService.findById("123");

        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        verify(productRepository, times(1)).findById("123");
    }

    @Test
    void testUpdate() {
        productService.update(product);
        verify(productRepository, times(1)).update(product);
    }

    @Test
    void testDelete() {
        productService.delete("123");
        verify(productRepository, times(1)).delete("123");
    }
}