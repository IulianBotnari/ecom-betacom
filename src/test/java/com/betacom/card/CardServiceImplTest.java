package com.betacom.card;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.betacom.dto.request.card.CardRequest;
import com.betacom.model.Card;
import com.betacom.repository.CardRepository;
import com.betacom.services.implementations.CardServiceImpl;

public class CardServiceImplTest {
	
	@Mock
    private CardRepository cardR; // Mockiamo il repository

    @InjectMocks
    private CardServiceImpl cardService; // Il service da testare con i mock iniettati

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_Success() throws Exception {
        // 1. PREPARAZIONE DATI (Input)
        CardRequest request = new CardRequest();
        request.setCardNumber("1234567812345678");
        request.setExpiryDate("12/28");
        request.setCvv("123");
        request.setCardHolder("Mario Rossi");

        // 2. MOCK DEL COMPORTAMENTO (Cosa deve tornare il DB)
        Card savedCard = new Card();
        savedCard.setId(1L); // Simuliamo che il DB assegni un ID
        savedCard.setCardNumber(request.getCardNumber());
        savedCard.setCardHolder(request.getCardHolder());

        // Quando il repository salva QUALSIASI Card, ritorna la nostra savedCard
        when(cardR.save(any(Card.class))).thenReturn(savedCard);

        // 3. ESECUZIONE
        Card result = cardService.create(request);

        // 4. VERIFICA (Asserzioni)
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCardHolder()).isEqualTo("Mario Rossi");

        // Verifichiamo che il metodo save del repository sia stato chiamato esattamente 1 volta
        verify(cardR, times(1)).save(any(Card.class));
    }
    
    @Test
    void testCreate_Service_DatabaseError() {
        // 1. Preparazione
        CardRequest request = new CardRequest();
        request.setCardNumber("1234567812345678");

        // 2. Mock: il repository lancia un'eccezione imprevista (es. Database offline)
        when(cardR.save(any(Card.class))).thenThrow(new RuntimeException("Database error"));

        // 3. Verifica
        assertThatThrownBy(() -> cardService.create(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database error");

        verify(cardR, times(1)).save(any(Card.class));
    }
    
    
    
    @Test
    void testUpdateService_Success() throws Exception {
        
    	Card existingCard = new Card();
        existingCard.setId(1L);
        existingCard.setCardHolder("Vecchio Nome");
        existingCard.setCardNumber("1111222233334444");
        existingCard.setCvv("123");
        existingCard.setExpiryDate("01/25");

        
        CardRequest request = new CardRequest();
        request.setId(1L); // <--- DEVE ESSERE 1L
        request.setCardHolder("Nuovo Nome"); 
        request.setCardNumber("1111222233334444"); 
        request.setCvv("999");               
        request.setExpiryDate("12/30");      
   
        when(cardR.findById(1L)).thenReturn(Optional.of(existingCard));
   
        cardService.update(request);

        assertThat(existingCard.getCardHolder()).isEqualTo("Nuovo Nome"); 
        assertThat(existingCard.getCardNumber()).isEqualTo("1111222233334444"); // Deve essere rimasto il vecchio!
        assertThat(existingCard.getCvv()).isEqualTo("999"); 
        
        verify(cardR, times(1)).save(existingCard);
    }

    @Test
    void testUpdate_NotFound() {
        // Mock: il repository non trova nulla
        when(cardR.findById(anyLong())).thenReturn(Optional.empty());

        CardRequest request = new CardRequest();
        request.setId(99L);

        // Verifica
        assertThatThrownBy(() -> cardService.update(request))
                .isInstanceOf(Exception.class)
                .hasMessage("card non presente in DB");
    }
    
    
    @Test
    void testUpdateService_Save_Failure() {
        // 1. Preparazione: La carta esiste nel DB
        Card existingCard = new Card();
        existingCard.setId(1L);
        
        CardRequest request = new CardRequest();
        request.setId(1L);
        request.setCardHolder("Nuovo Nome");

        when(cardR.findById(1L)).thenReturn(Optional.of(existingCard));

        // 2. Mock: Il findById funziona, ma il SAVE lancia un errore (es. Database offline)
        // Usiamo RuntimeException perché i repository JPA solitamente lanciano quelle
        when(cardR.save(any(Card.class))).thenThrow(new RuntimeException("Errore persistenza database"));

        // 3. Verifica: Il service deve lasciar passare l'eccezione verso il controller
        assertThatThrownBy(() -> cardService.update(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Errore persistenza database");

        // Verifichiamo che il save sia stato effettivamente tentato
        verify(cardR, times(1)).save(any(Card.class));
    }
    
    
    
    @Test
    void testDeleteService_Success() throws Exception {
        // 1. Preparazione
        Card card = new Card();
        card.setId(1L);
        // Quando cerco l'ID 1, il repository deve "trovare" la card
        when(cardR.findById(1L)).thenReturn(Optional.of(card));

        // 2. Esecuzione
        cardService.delete(1L);

        // 3. Verifica: controlliamo che sia stato chiamato il delete sul repository
        verify(cardR, times(1)).delete(card);
    }

    @Test
    void testDeleteService_NotFound() {
        // Mock: il repository non trova nulla
        when(cardR.findById(anyLong())).thenReturn(Optional.empty());

        // Verifica che il service lanci l'eccezione col tuo messaggio specifico
        assertThatThrownBy(() -> cardService.delete(99L))
                .isInstanceOf(Exception.class)
                .hasMessage("card non presente in DB");
    }
    
    @Test
    void testListService_Success() throws Exception {
        // 1. Preparazione: creiamo una lista di Card (Entity)
        Card c1 = new Card();
        c1.setId(1L);
        c1.setCardHolder("Mario");
        
        java.util.List<Card> entityList = java.util.Arrays.asList(c1);

        // 2. Mock: quando il repository chiama findAll, restituisce la nostra lista
        when(cardR.findAll()).thenReturn(entityList);

        // 3. Esecuzione
        java.util.List<com.betacom.dto.response.card.CardDTO> result = cardService.list();

        // 4. Verifica
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        // Verifichiamo che il repository sia stato interrogato
        verify(cardR, times(1)).findAll();
    }
    
    @Test
    void testListService_Error() {
        // 1. Simula un errore fatale del database (es. connessione persa)
        // Usiamo RuntimeException perché findAll() di JpaRepository non lancia Exception checked
        when(cardR.findAll()).thenThrow(new RuntimeException("Database offline"));

        // 2. Verifica che il service propaghi l'eccezione
        // Usiamo assertThatThrownBy per intercettare l'errore
        assertThatThrownBy(() -> cardService.list())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database offline");

        // 3. Verifica che il repository sia stato effettivamente chiamato
        verify(cardR, times(1)).findAll();
    }
    
    @Test
    void testGetByIdService_Success() throws Exception {
        // 1. Preparazione
        Card card = new Card();
        card.setId(1L);
        card.setCardHolder("Mario Rossi");
        
        when(cardR.findById(1L)).thenReturn(Optional.of(card));

        // 2. Esecuzione
        com.betacom.dto.response.card.CardDTO result = cardService.getById(1L);

        // 3. Verifica
        assertThat(result).isNotNull();
        verify(cardR, times(1)).findById(1L);
    }

    // --- TEST GET BY ID NOT FOUND ---
    @Test
    void testGetByIdService_NotFound() {
        // Mock: il repository restituisce vuoto
        when(cardR.findById(anyLong())).thenReturn(Optional.empty());

        // Verifica che lanci l'eccezione con il TUO messaggio specifico
        assertThatThrownBy(() -> cardService.getById(99L))
                .isInstanceOf(Exception.class)
                .hasMessage("carta non presente in DB");
    }
    
}
    

