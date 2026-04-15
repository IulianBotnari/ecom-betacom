package com.betacom.paymentMethod;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.betacom.dto.request.card.CardRequest;
import com.betacom.dto.request.payment_method.PaymentMethodRequest;
import com.betacom.dto.response.payment_method.PaymentMethodDTO;
import com.betacom.model.Card;
import com.betacom.model.PaymentMethod;
import com.betacom.model.User;
import com.betacom.repository.PaymentMethodRepository;
import com.betacom.repository.UserRepository;
import com.betacom.services.implementations.PaymentMethodServiceImpl;
import com.betacom.services.interfaces.InterfaceCardService;

public class PaymentMethodServiceImplTest {
	@Mock private PaymentMethodRepository pmR;
    @Mock private UserRepository userR;
    @Mock private InterfaceCardService cardService;

    @InjectMocks private PaymentMethodServiceImpl paymentMethodService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    
    
    
    @Test
    void testGetById_Success() throws Exception {
        // 1. Prepariamo lo User (per evitare null su .getUser().getId())
        User mockUser = new User();
        mockUser.setId(10L);

        // 2. Prepariamo la Card (per evitare null su cardDTO(model.getCard()))
        Card mockCard = new Card();
        mockCard.setId(5L);
        mockCard.setCardHolder("Mario Rossi");

        // 3. Prepariamo il PaymentMethod con TUTTI i pezzi richiesti dal Mapper
        PaymentMethod pm = new PaymentMethod();
        pm.setId(1L);
        pm.setDescription("Test Description");
        pm.setUser(mockUser); // Fondamentale!
        pm.setCard(mockCard); // Fondamentale!

        when(pmR.findById(1L)).thenReturn(Optional.of(pm));

        // 4. Esecuzione
        PaymentMethodDTO result = paymentMethodService.getById(1L);

        // 5. Verifiche
        assertThat(result).isNotNull(); 
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUserId()).isEqualTo(10L);
    }

    @Test
    void testGetById_NotFound() {
    	when(pmR.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> paymentMethodService.getById(1L))
            .hasMessage("Metodo di pagamento non presente in DB");
    }

    @Test
    void testList_Success() throws Exception {
    	User mockUser = new User();
        mockUser.setId(10L);

        Card mockCard = new Card();
        mockCard.setId(5L);

        // 2. Prepariamo il PaymentMethod completo
        PaymentMethod pm = new PaymentMethod();
        pm.setId(1L);
        pm.setDescription("Lista Test");
        pm.setUser(mockUser); // Fondamentale per il Mapper
        pm.setCard(mockCard); // Fondamentale per il Mapper

        // 3. Mock: restituiamo la lista con l'oggetto completo
        when(pmR.findAll()).thenReturn(Arrays.asList(pm));

        // 4. Esecuzione
        List<PaymentMethodDTO> result = paymentMethodService.list();

        // 5. Verifica
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDescription()).isEqualTo("Lista Test");
    }
    
    @Test
    void testList_DatabaseError() {
        when(pmR.findAll()).thenThrow(new RuntimeException("DB Error"));
        
        assertThatThrownBy(() -> paymentMethodService.list())
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void testCreate_Success() throws Exception {
        PaymentMethodRequest req = new PaymentMethodRequest();
        req.setUserId(1L);
        req.setCard(new CardRequest());
        req.setDescription("Test PM");

        User user = new User();
        Card card = new Card();

        when(userR.findById(1L)).thenReturn(Optional.of(user));
        when(cardService.create(any(CardRequest.class))).thenReturn(card);

        paymentMethodService.create(req);

        verify(pmR, times(1)).save(any(PaymentMethod.class));
    }
    
    @Test
    void testCreate_DatabaseError() {
        PaymentMethodRequest req = new PaymentMethodRequest();
        req.setUserId(1L);
        when(userR.findById(1L)).thenReturn(Optional.of(new User()));
        when(pmR.save(any(PaymentMethod.class))).thenThrow(new RuntimeException("DB crash"));

        assertThatThrownBy(() -> paymentMethodService.create(req))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void testCreate_UserNotFound() {
        PaymentMethodRequest req = new PaymentMethodRequest();
        req.setUserId(1L);
        when(userR.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentMethodService.create(req))
            .hasMessageContaining("User non trovato in DB");
    }

    @Test
    void testUpdate_Success() throws Exception {
        PaymentMethod existingPm = new PaymentMethod();
        Card existingCard = new Card();
        existingCard.setId(10L);
        existingPm.setCard(existingCard);

        PaymentMethodRequest req = new PaymentMethodRequest();
        req.setId(1L);
        req.setDescription("New Desc");
        req.setUserId(2L);
        req.setCard(new CardRequest());

        when(pmR.findById(1L)).thenReturn(Optional.of(existingPm));
        when(userR.findById(2L)).thenReturn(Optional.of(new User()));

        paymentMethodService.update(req);

        verify(cardService, times(1)).update(any(CardRequest.class));
        verify(pmR, times(1)).save(existingPm);
    }
    
    @Test
    void testUpdate_NotFound() {
        PaymentMethodRequest req = new PaymentMethodRequest();
        req.setId(99L);
        when(pmR.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentMethodService.update(req))
            .hasMessage("Metodo di pagamento non trovato"); 
    }
    
    @Test
    void testUpdate_Partial_NoUserNoCard() throws Exception {
        PaymentMethod existingPm = new PaymentMethod();
        existingPm.setDescription("Old Desc");
        
        PaymentMethodRequest req = new PaymentMethodRequest();
        req.setId(1L);
        req.setDescription("New Desc");
        req.setUserId(null); // Caso ID User null
        req.setCard(null);   // Caso CardRequest null

        when(pmR.findById(1L)).thenReturn(Optional.of(existingPm));

        paymentMethodService.update(req);

        assertThat(existingPm.getDescription()).isEqualTo("New Desc");
        verify(userR, times(0)).findById(anyLong()); // Verifica che non cerchi l'utente
        verify(pmR, times(1)).save(existingPm);
    }

    @Test
    void testDelete_Success() throws Exception {
        PaymentMethod pm = new PaymentMethod();
        when(pmR.findById(1L)).thenReturn(Optional.of(pm));

        paymentMethodService.delete(1L);
        verify(pmR, times(1)).delete(pm);
    }

    @Test
    void testDelete_NotFound() {
        when(pmR.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> paymentMethodService.delete(1L))
            .hasMessage("Metodo pagamento non presente in DB");
    }
}
