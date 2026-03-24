package com.betacom.user;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.betacom.dto.request.user.UserCreateRequest;
import com.betacom.dto.request.user.UserUpdateRequest;
import com.betacom.dto.response.user.UserDTO;
import com.betacom.dto_mappers.map_model.ModelMappers;
import com.betacom.enums.Roles;
import com.betacom.model.User;
import com.betacom.repository.AddressRepository;
import com.betacom.repository.OrderRepository;
import com.betacom.repository.ReviewRepository;
import com.betacom.repository.UserRepository;
import com.betacom.services.implementations.CartServiceImpl;
import com.betacom.services.implementations.UserServiceImpl;

class UserServiceImplTest {

    @Mock
    private UserRepository userR;
    @Mock
    private ModelMappers modelM;

    @InjectMocks
    private UserServiceImpl userService;
    
    @Mock
    private CartServiceImpl cartService;
    
    @Mock
    private AddressRepository addressR;
    @Mock
    private OrderRepository orderR;
    @Mock
    private ReviewRepository reviewR;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- 3. TEST CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        UserCreateRequest request = UserCreateRequest.builder()
                .name("Mario")
                .lastName("Rossi")
                .birthday(LocalDate.of(1990, 1, 1))
                .codiceFiscale("RSSMRA90A01H501U")
                .email("mario.rossi@example.com")
                .password("password123")
                .phone("1234567890")
                .role(Roles.USER)
                .build();

        User savedUser = new User();
        savedUser.setId(1L);

        when(userR.save(any())).thenReturn(savedUser);
        doNothing().when(cartService).create(any());

        userService.create(request);

        verify(userR, times(1)).save(any());
        verify(cartService, times(1)).create(any());
    }
    
    @Test
    void testCreate_Fail_SaveException() throws Exception {
        UserCreateRequest request = new UserCreateRequest();

        when(userR.save(any())).thenThrow(new RuntimeException("DB error"));

        try {
            userService.create(request);
        } catch (Exception e) {
            // expected
        }

        verify(userR, times(1)).save(any());
        verify(cartService, times(0)).create(any());
    }
    
    @Test
    void testUpdate_Success() throws Exception {
        UserUpdateRequest request = UserUpdateRequest.builder()
                .id(1L)
                .name("Dario")
                .build();

        User user = new User();
        when(userR.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(userR.save(any())).thenReturn(user);

        userService.update(request);

        verify(userR, times(1)).findById(1L);
        verify(userR, times(1)).save(user);
    }
    
    @Test
    void testUpdate_Fail_UserNotFound() {
        UserUpdateRequest request = UserUpdateRequest.builder()
                .id(10L)
                .name("Dario")
                .build();

        when(userR.findById(10L)).thenReturn(java.util.Optional.empty());

        try {
            userService.update(request);
        } catch (Exception e) {
            // expected
        }

        verify(userR, times(1)).findById(10L);
        verify(userR, times(0)).save(any());
    }
    
    @Test
    void testDelete_Success() throws Exception {
        User user = new User();
        when(userR.findById(1L)).thenReturn(java.util.Optional.of(user));
        doNothing().when(userR).delete(user);
        when(addressR.saveAll(any())).thenReturn(new ArrayList<>());
        when(orderR.saveAll(any())).thenReturn(new ArrayList<>());
        when(reviewR.saveAll(any())).thenReturn(new ArrayList<>());

        userService.delete(1L);

        verify(userR, times(1)).findById(1L);
        verify(userR, times(1)).delete(user);
    }
    
    @Test
    void testDelete_Fail_UserNotFound() {
        when(userR.findById(1L)).thenReturn(java.util.Optional.empty());

        try {
            userService.delete(1L);
        } catch (Exception e) {
            // expected
        }

        verify(userR, times(1)).findById(1L);
        verify(userR, times(0)).delete(any());
    }
    
    @Test
    void testGetById_Success() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setName("Mario");

        when(userR.findById(1L)).thenReturn(java.util.Optional.of(user));

        UserDTO result = userService.getById(1L);

        verify(userR, times(1)).findById(1L);

        // controlli base
        assert result != null;
        assert result.getId().equals(1L);
    }
    
    @Test
    void testGetById_Fail() {

        when(userR.findById(1L)).thenReturn(java.util.Optional.empty());

        try {
            userService.getById(1L);
        } catch (Exception e) {
            assert e.getMessage().equals("utente non presente in DB");
        }

        verify(userR, times(1)).findById(1L);
    }
    
    @Test
    void testList_Success() throws Exception {

        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        when(userR.findAll()).thenReturn(java.util.List.of(user1, user2));

        List<UserDTO> result = userService.list();

        verify(userR, times(1)).findAll();

        assert result != null;
        assert result.size() == 2;
    }
    
    @Test
    void testList_Fail() {

        when(userR.findAll()).thenThrow(new RuntimeException("Errore DB"));

        try {
            userService.list();
        } catch (Exception e) {
            // expected
        }

        verify(userR, times(1)).findAll();
    }

    
}