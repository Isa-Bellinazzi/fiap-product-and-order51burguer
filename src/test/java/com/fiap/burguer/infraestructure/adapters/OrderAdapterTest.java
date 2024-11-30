package com.fiap.burguer.infraestructure.adapters;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.infraestructure.entities.OrderEntity;
import com.fiap.burguer.infraestructure.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderAdapterTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderAdapter orderAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Order order = new Order(); // Configure o objeto conforme necessário
        OrderEntity orderEntity = new OrderEntity();
        OrderEntity savedEntity = new OrderEntity();
        Order savedOrder = new Order();

        when(modelMapper.map(order, OrderEntity.class)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, Order.class)).thenReturn(savedOrder);

        Order result = orderAdapter.save(order);

        assertNotNull(result);
        verify(orderRepository, times(1)).save(orderEntity);
        verify(modelMapper, times(1)).map(order, OrderEntity.class);
        verify(modelMapper, times(1)).map(savedEntity, Order.class);
    }

    @Test
    void testFindById() {
        int id = 1;
        OrderEntity orderEntity = new OrderEntity();
        Order order = new Order();

        when(orderRepository.findById(id)).thenReturn(orderEntity);
        when(modelMapper.map(orderEntity, Order.class)).thenReturn(order);

        Order result = orderAdapter.findById(id);

        assertNotNull(result);
        verify(orderRepository, times(1)).findById(id);
        verify(modelMapper, times(1)).map(orderEntity, Order.class);
    }

    @Test
    void testFindAll() {
        List<OrderEntity> entities = Arrays.asList(new OrderEntity(), new OrderEntity());
        List<Order> orders = Arrays.asList(new Order(), new Order());

        when(orderRepository.findAllOrders()).thenReturn(entities);
        when(modelMapper.map(any(OrderEntity.class), eq(Order.class)))
                .thenAnswer(invocation -> new Order());

        List<Order> result = orderAdapter.findAll();

        assertEquals(orders.size(), result.size());
        verify(orderRepository, times(1)).findAllOrders();
    }

    @Test
    void testFindByStatus() {
        StatusOrder status = StatusOrder.RECEIVED;
        List<OrderEntity> entities = Arrays.asList(new OrderEntity());
        List<Order> orders = Arrays.asList(new Order());

        when(orderRepository.findByStatus(status)).thenReturn(entities);
        when(modelMapper.map(any(OrderEntity.class), eq(Order.class)))
                .thenAnswer(invocation -> new Order());

        List<Order> result = orderAdapter.findByStatus(status);

        assertEquals(orders.size(), result.size());
        verify(orderRepository, times(1)).findByStatus(status);
    }

    @Test
    void testDeleteById() {
        int id = 1;

        doNothing().when(orderRepository).deleteById(id);

        orderAdapter.deleteById(id);

        verify(orderRepository, times(1)).deleteById(id);
    }
}
