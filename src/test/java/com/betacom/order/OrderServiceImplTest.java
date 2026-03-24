package com.betacom.order;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.betacom.dto.request.order.OrderRequest;
import com.betacom.dto.response.order.OrderDTO;
import com.betacom.enums.OrderStatus;
import com.betacom.model.Address;
import com.betacom.model.Order;
import com.betacom.model.User;
import com.betacom.repository.AddressRepository;
import com.betacom.repository.OrderRepository;
import com.betacom.repository.UserRepository;
import com.betacom.services.implementations.OrderServiceImpl;

import jakarta.persistence.EntityNotFoundException;

public class OrderServiceImplTest {
	@Mock
    private OrderRepository orderR;

    @Mock
    private UserRepository userR;

    @Mock
    private AddressRepository addressR;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- 1. TEST GET BY ID ---
    @Test
    void testGetById_Success() throws Exception {
        Order order = new Order();
        when(orderR.findById(1L)).thenReturn(Optional.of(order));

        OrderDTO result = orderService.getById(1L);
        assertNotNull(result);
        verify(orderR).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(orderR.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> orderService.getById(1L));
    }

    // --- 2. TEST LIST ---
    @Test
    void testList_Success() throws Exception {
        when(orderR.findAll()).thenReturn(Arrays.asList(new Order(), new Order()));

        List<OrderDTO> result = orderService.list();
        assertEquals(2, result.size());
        verify(orderR).findAll();
    }

    // --- 3. TEST CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setUserId(1L);
        request.setStatus("CREATED");
        request.setTotal(100.0);

        User user = new User();
        when(userR.findById(1L)).thenReturn(Optional.of(user));

        orderService.create(request);
        verify(orderR).save(any(Order.class));
    }

    @Test
    void testCreate_UserNotFound() {
        OrderRequest request = new OrderRequest();
        request.setUserId(1L);

        when(userR.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> orderService.create(request));
    }

    // --- 4. TEST UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setId(1L);
        request.setUserId(2L);
        request.setShippingAddress(3L);
        request.setStatus("PAGATO");
        request.setTotal(200.0);

        Order order = new Order();
        User user = new User();
        Address address = new Address();

        when(orderR.findById(1L)).thenReturn(Optional.of(order));
        when(userR.findById(2L)).thenReturn(Optional.of(user));
        when(addressR.findById(3L)).thenReturn(Optional.of(address));

        orderService.update(request);

        assertEquals(user, order.getUser());
        assertEquals(address, order.getShippingAddress());
        assertEquals(OrderStatus.PAGATO, order.getStatus());
        assertEquals(200.0, order.getTotal());
        verify(orderR).save(order);
    }

    @Test
    void testUpdate_OrderNotFound() {
        OrderRequest request = new OrderRequest();
        request.setId(1L);

        when(orderR.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> orderService.update(request));
    }

    // --- 5. TEST DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        Order order = new Order();
        when(orderR.findById(1L)).thenReturn(Optional.of(order));

        orderService.delete(1L);
        verify(orderR).delete(order);
    }

    @Test
    void testDelete_NotFound() {
        when(orderR.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.delete(1L));
    }
}
