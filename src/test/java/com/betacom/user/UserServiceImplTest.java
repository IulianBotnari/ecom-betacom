package com.betacom.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.betacom.dto.request.login.LoginRequest;
import com.betacom.dto.request.user.UserCreateRequest;
import com.betacom.dto.request.user.UserUpdateRequest;
import com.betacom.model.User;
import com.betacom.repository.AddressRepository;
import com.betacom.repository.OrderRepository;
import com.betacom.repository.ReviewRepository;
import com.betacom.repository.UserRepository;
import com.betacom.services.implementations.CartServiceImpl;
import com.betacom.services.implementations.UserServiceImpl;
import com.betacom.services.interfaces.InterfaceUserService;
import com.betacom.dto.response.login.LoginDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.context.SecurityContextRepository;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {

    @Mock private UserRepository userR;
    @Mock private AddressRepository addressR;
    @Mock private OrderRepository orderR;
    @Mock private ReviewRepository reviewR;
    @Mock private CartServiceImpl cartService;
    @Mock private SecurityContextRepository securityContextRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- GET BY ID ---
    @Test
    void testGetById_Success() throws Exception {
        User user = new User();
        when(userR.findById(1L)).thenReturn(Optional.of(user));
        userService.getById(1L);
        verify(userR).findById(1L);
    }

    @Test
    void testGetById_Fail() {
        when(userR.findById(1L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(Exception.class, () -> userService.getById(1L));
        assertEquals("utente non presente in DB", ex.getMessage());
    }

    // --- LIST ---
    @Test
    void testList_Success() throws Exception {
        when(userR.findAll()).thenReturn(new ArrayList<>());
        userService.list();
        verify(userR).findAll();
    }

    @Test
    void testList_Fail() throws Exception {
        when(userR.findAll()).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> userService.list());
    }

    // --- CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        UserCreateRequest req = new UserCreateRequest();
        req.setName("Mario");
        req.setLastName("Rossi");
        req.setEmail("test@test.com");
        req.setPassword("1234");
        req.setBirthday(LocalDate.of(1990, 1, 1));
        req.setPhone("1234567890");

        User savedUser = new User();
        savedUser.setId(1L);

        when(userR.save(any())).thenReturn(savedUser);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        userService.create(req);

        verify(userR).save(any());
        verify(cartService).create(any());
    }

    // --- UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        User user = new User();
        user.setId(1L);
        when(userR.findById(1L)).thenReturn(Optional.of(user));

        UserUpdateRequest req = new UserUpdateRequest();
        req.setId(1L);
        req.setName("Luigi");

        userService.update(req);
        verify(userR).save(any());
    }

    @Test
    void testUpdate_Fail_UserNotFound() {
        when(userR.findById(1L)).thenReturn(Optional.empty());
        UserUpdateRequest req = new UserUpdateRequest();
        req.setId(1L);
        Exception ex = assertThrows(Exception.class, () -> userService.update(req));
        assertEquals("utente non presente in DB", ex.getMessage());
    }

    // --- DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        User user = new User();
        when(userR.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userR).delete(user);
        userService.delete(1L);
        verify(userR).delete(user);
    }

    @Test
    void testDelete_Fail() {
        when(userR.findById(1L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(Exception.class, () -> userService.delete(1L));
        assertEquals("utente non presente in DB", ex.getMessage());
    }

    // --- LOGIN ---
    @Test
    void testLogin_Success() throws Exception {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword");

        LoginRequest req = new LoginRequest();
        req.setEmail("test@test.com");
        req.setPassword("1234");

        when(userR.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "encodedPassword")).thenReturn(true);

        HttpServletRequest httpReq = mock(HttpServletRequest.class);
        HttpServletResponse httpRes = mock(HttpServletResponse.class);

        userService.login(req, httpReq, httpRes);

        verify(userR).findByEmail("test@test.com");
        verify(securityContextRepository).saveContext(any(), any(), any());
    }

    @Test
    void testLogin_Fail_InvalidCredentials() throws Exception {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword");

        LoginRequest req = new LoginRequest();
        req.setEmail("test@test.com");
        req.setPassword("wrongPassword");

        when(userR.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        HttpServletRequest httpReq = mock(HttpServletRequest.class);
        HttpServletResponse httpRes = mock(HttpServletResponse.class);

        Exception ex = assertThrows(Exception.class, () -> userService.login(req, httpReq, httpRes));
        assertEquals("Credenziali non valide", ex.getMessage());
    }
}