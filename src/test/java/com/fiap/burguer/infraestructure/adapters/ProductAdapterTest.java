package com.fiap.burguer.infraestructure.adapters;

import com.fiap.burguer.core.domain.Product;
import com.fiap.burguer.infraestructure.entities.ProductEntity;
import com.fiap.burguer.infraestructure.mappers.ProductMapper;
import com.fiap.burguer.infraestructure.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductAdapterTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductAdapter productAdapter;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
@Test
    void testSave() {
        Product product = new Product();
        ProductEntity productEntity = new ProductEntity();
        ProductEntity savedEntity = new ProductEntity();
        Product savedProduct = new Product();
        when(ProductMapper.toEntity(product)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(savedEntity);
        when(ProductMapper.toDomain(savedEntity)).thenReturn(savedProduct);
        Product result = productAdapter.save(product);
        assertNotNull(result);
        verify(productRepository, times(1)).save(productEntity);
        verifyNoMoreInteractions(productRepository);
        ProductMapper.toEntity(product);
        ProductMapper.toDomain(savedEntity);
    }


    @Test
    void testFindById() {
        int id = 1;
        ProductEntity productEntity = new ProductEntity();
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(productEntity);
        when(ProductMapper.toDomain(productEntity)).thenReturn(product);
        Product result = productAdapter.findById(id);
        assertNotNull(result);
        verify(productRepository, times(1)).findById(id);
        ProductMapper.toDomain(productEntity);
    }

    @Test
    void testFindAll() {
        List<ProductEntity> entities = Arrays.asList(new ProductEntity(), new ProductEntity());
        List<Product> products = Arrays.asList(new Product(), new Product());
        mockStatic(ProductMapper.class);
        when(productRepository.findAll()).thenReturn(entities);
        when(ProductMapper.toDomain(entities)).thenReturn(products);
        List<Product> result = productAdapter.findAll();
        assertEquals(products.size(), result.size());
        verify(productRepository, times(1)).findAll();
        ProductMapper.toDomain(entities);
    }

    @Test
    void testDeleteById() {
        int id = 1;
        doNothing().when(productRepository).deleteById(id);
        productAdapter.deleteById(id);
        verify(productRepository, times(1)).deleteById(id);
    }
}
