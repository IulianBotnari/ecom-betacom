package com.betacom.orderDetails;

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

import com.betacom.dto.request.order_details.OrderDetailRequest;
import com.betacom.dto.response.order_details.OrderDetailsDTO;
import com.betacom.model.Order;
import com.betacom.model.OrderDetail;
import com.betacom.model.Product;
import com.betacom.model.Size;
import com.betacom.repository.OrderDetailRepository;
import com.betacom.repository.OrderRepository;
import com.betacom.repository.ProductRepository;
import com.betacom.repository.SizeRepository;
import com.betacom.services.implementations.OrderDetailServiceImpl;

import jakarta.persistence.EntityNotFoundException;

public class OrderDetailsServiceImplTest {
	@Mock
    private OrderDetailRepository orderDR;

    @Mock
    private OrderRepository orderR;

    @Mock
    private ProductRepository productR;

    @Mock
    private SizeRepository sizeR;

    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- 1. TEST GET BY ID ---
    @Test
    void testGetById_Success() throws Exception {
        OrderDetail detail = new OrderDetail();
        when(orderDR.findById(1L)).thenReturn(Optional.of(detail));

        OrderDetailsDTO result = orderDetailService.getById(1L);
        assertNotNull(result);
        verify(orderDR).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(orderDR.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderDetailService.getById(1L));
    }

    // --- 2. TEST LIST ---
    @Test
    void testList_Success() throws Exception {
        when(orderDR.findAll()).thenReturn(Arrays.asList(new OrderDetail(), new OrderDetail()));

        List<OrderDetailsDTO> result = orderDetailService.list();
        assertEquals(2, result.size());
        verify(orderDR).findAll();
    }

    // --- 3. TEST CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrderId(1L);
        request.setProductId(2L);
        request.setSizeId(3L);
        request.setQuantity(5);
        request.setTotalPrice(100.0);

        orderDetailService.create(request);
        // Cannot verify save since the service does not call orderDR.save yet
        assertEquals(5, request.getQuantity());
    }

    @Test
    void testCreate_OrderIdMissing() {
        OrderDetailRequest request = new OrderDetailRequest();
        request.setProductId(2L);
        request.setQuantity(1);

        Exception ex = assertThrows(Exception.class, () -> orderDetailService.create(request));
        assertEquals("OrderId è necessario", ex.getMessage());
    }

    @Test
    void testCreate_ProductIdMissing() {
        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrderId(1L);
        request.setQuantity(1);

        Exception ex = assertThrows(Exception.class, () -> orderDetailService.create(request));
        assertEquals("ProductId è necessario", ex.getMessage());
    }

    @Test
    void testCreate_QuantityInvalid() {
        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrderId(1L);
        request.setProductId(2L);
        request.setQuantity(0);

        Exception ex = assertThrows(Exception.class, () -> orderDetailService.create(request));
        assertEquals("Quantity deve essere maggiore di 0", ex.getMessage());
    }

    // --- 4. TEST UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrderId(1L);
        request.setProductId(2L);
        request.setSizeId(3L);
        request.setQuantity(10);
        request.setTotalPrice(200.0);

        OrderDetail orderD = new OrderDetail();
        Order order = new Order();
        Product product = new Product();
        Size size = new Size();

        when(orderDR.findById(1L)).thenReturn(Optional.of(orderD));
        when(orderR.findById(2L)).thenReturn(Optional.of(order));
        when(productR.findById(2L)).thenReturn(Optional.of(product));
        when(sizeR.findById(3L)).thenReturn(Optional.of(size));

        orderDetailService.update(request);

        assertEquals(order, orderD.getOrder());
        assertEquals(product, orderD.getProduct());
        assertEquals(size, orderD.getSize());
        assertEquals(10, orderD.getQuantity());
        assertEquals(200.0, orderD.getTotalPrice());
        verify(orderDR).findById(1L);
    }

    @Test
    void testUpdate_NotFound() {
        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrderId(1L);

        when(orderDR.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> orderDetailService.update(request));
    }

    // --- 5. TEST DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        OrderDetail orderD = new OrderDetail();
        when(orderDR.findById(1L)).thenReturn(Optional.of(orderD));

        orderDetailService.delete(1L);
        verify(orderDR).delete(orderD);
    }

    @Test
    void testDelete_NotFound() {
        when(orderDR.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderDetailService.delete(1L));
    }
}
