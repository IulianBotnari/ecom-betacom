package com.betacom.address;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.betacom.dto.request.address.AddressCreateRequest;
import com.betacom.dto.request.address.AddressUpdateRequest;
import com.betacom.dto.response.address.AddressDTO;
import com.betacom.model.Address;
import com.betacom.model.User;
import com.betacom.repository.AddressRepository;
import com.betacom.repository.UserRepository;
import com.betacom.services.implementations.AddressServiceImpl;

public class AddressServiceImplTest {

    @Mock
    private AddressRepository addressR;

    @Mock
    private UserRepository userR;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- GET BY ID ---
    @Test
    void testGetById_Success() throws Exception {
        Address address = new Address();
        address.setId(1L);

        when(addressR.findById(1L)).thenReturn(java.util.Optional.of(address));

        AddressDTO result = addressService.getById(1L);

        verify(addressR).findById(1L);
        assert result != null;
    }

    @Test
    void testGetById_Fail() {
        when(addressR.findById(1L)).thenReturn(java.util.Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> {
            addressService.getById(1L);
        });

        assertEquals("Indirizzo non presente in DB", ex.getMessage());
        verify(addressR).findById(1L);
    }

    // --- LIST ---
    @Test
    void testList_Success() throws Exception {
        when(addressR.findAll()).thenReturn(List.of(new Address(), new Address()));

        List<AddressDTO> result = addressService.list();

        verify(addressR).findAll();
        assert result.size() == 2;
    }

    @Test
    void testList_Fail() {
        when(addressR.findAll()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            addressService.list();
        });

        verify(addressR).findAll();
    }

    // --- CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        AddressCreateRequest request = AddressCreateRequest.builder()
                .city("Padova")
                .street("Via Roma")
                .civic("10")
                .province("PD")
                .cap("35100")
                .country("Italia")
                .residence(true)
                .domicile(false)
                .userId(1L)
                .build();

        User user = new User();
        user.setId(1L);

        when(userR.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(addressR.save(any())).thenReturn(new Address());

        addressService.create(request);

        verify(userR).findById(1L);
        verify(addressR).save(any());
    }

    @Test
    void testCreate_Fail_UserNotFound() {
        AddressCreateRequest request = AddressCreateRequest.builder()
                .city("Padova")
                .street("Via Roma")
                .civic("10")
                .province("PD")
                .cap("35100")
                .country("Italia")
                .residence(true)
                .domicile(false)
                .userId(1L)
                .build();

        when(userR.findById(1L)).thenReturn(java.util.Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> {
            addressService.create(request);
        });

        assertEquals("Utente di riferimento non trovato", ex.getMessage());
        verify(addressR, times(0)).save(any());
    }

    // --- UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        AddressUpdateRequest request = AddressUpdateRequest.builder()
                .id(1L)
                .city("Milano")
                .userId(1L)
                .build();

        Address address = new Address();
        User user = new User();

        when(addressR.findById(1L)).thenReturn(java.util.Optional.of(address));
        when(userR.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(addressR.save(any())).thenReturn(new Address());

        addressService.update(request);

        verify(addressR).findById(1L);
        verify(addressR).save(any());
    }

    @Test
    void testUpdate_Fail_AddressNotFound() {
        AddressUpdateRequest request = AddressUpdateRequest.builder().id(1L).build();

        when(addressR.findById(1L)).thenReturn(java.util.Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> {
            addressService.update(request);
        });

        assertEquals("Indirizzo non presente in DB", ex.getMessage());
        verify(addressR).findById(1L);
        verify(addressR, times(0)).save(any());
    }

    @Test
    void testUpdate_Fail_UserNotFound() {
        AddressUpdateRequest request = AddressUpdateRequest.builder()
                .id(1L)
                .userId(1L)
                .build();

        when(addressR.findById(1L)).thenReturn(java.util.Optional.of(new Address()));
        when(userR.findById(1L)).thenReturn(java.util.Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> {
            addressService.update(request);
        });

        assertEquals("Utente di riferimento non trovato", ex.getMessage());
        verify(addressR, times(0)).save(any());
    }

    // --- DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        Address address = new Address();

        when(addressR.findById(1L)).thenReturn(java.util.Optional.of(address));
        doNothing().when(addressR).delete(address);

        addressService.delete(1L);

        verify(addressR).findById(1L);
        verify(addressR).delete(address);
    }

    @Test
    void testDelete_Fail() {
        when(addressR.findById(1L)).thenReturn(java.util.Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> {
            addressService.delete(1L);
        });

        assertEquals("Indirizzo non presente in DB", ex.getMessage());
        verify(addressR).findById(1L);
        verify(addressR, times(0)).delete(any());
    }
}