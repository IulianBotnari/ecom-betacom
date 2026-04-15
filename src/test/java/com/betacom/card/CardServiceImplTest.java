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
    private CardRepository cardR; 

    @InjectMocks
    private CardServiceImpl cardService; 

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_Success() throws Exception {
       
        CardRequest request = new CardRequest();
        request.setCardNumber("1234567812345678");
        request.setExpiryDate("12/28");
        request.setCvv("123");
        request.setCardHolder("Mario Rossi");

       
        Card savedCard = new Card();
        savedCard.setId(1L); 
        savedCard.setCardNumber(request.getCardNumber());
        savedCard.setCardHolder(request.getCardHolder());

        
        when(cardR.save(any(Card.class))).thenReturn(savedCard);

        
        Card result = cardService.create(request);

        
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCardHolder()).isEqualTo("Mario Rossi");

        
        verify(cardR, times(1)).save(any(Card.class));
    }
    
    @Test
    void testCreate_Service_DatabaseError() {
        
        CardRequest request = new CardRequest();
        request.setCardNumber("1234567812345678");

       
        when(cardR.save(any(Card.class))).thenThrow(new RuntimeException("Database error"));

       
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
        assertThat(existingCard.getCardNumber()).isEqualTo("1111222233334444"); 
        assertThat(existingCard.getCvv()).isEqualTo("999"); 
        
        verify(cardR, times(1)).save(existingCard);
    }

    @Test
    void testUpdate_NotFound() {
        
        when(cardR.findById(anyLong())).thenReturn(Optional.empty());

        CardRequest request = new CardRequest();
        request.setId(99L);

        
        assertThatThrownBy(() -> cardService.update(request))
                .isInstanceOf(Exception.class)
                .hasMessage("card non presente in DB");
    }
    
    
    @Test
    void testUpdateService_Save_Failure() {
       
        Card existingCard = new Card();
        existingCard.setId(1L);
        
        CardRequest request = new CardRequest();
        request.setId(1L);
        request.setCardHolder("Nuovo Nome");

        when(cardR.findById(1L)).thenReturn(Optional.of(existingCard));

        
        when(cardR.save(any(Card.class))).thenThrow(new RuntimeException("Errore persistenza database"));

        
        assertThatThrownBy(() -> cardService.update(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Errore persistenza database");

        
        verify(cardR, times(1)).save(any(Card.class));
    }
    
    
    
    @Test
    void testDeleteService_Success() throws Exception {
        
        Card card = new Card();
        card.setId(1L);
       
        when(cardR.findById(1L)).thenReturn(Optional.of(card));

        
        cardService.delete(1L);

       
        verify(cardR, times(1)).delete(card);
    }

    @Test
    void testDeleteService_NotFound() {
     
        when(cardR.findById(anyLong())).thenReturn(Optional.empty());

       
        assertThatThrownBy(() -> cardService.delete(99L))
                .isInstanceOf(Exception.class)
                .hasMessage("card non presente in DB");
    }
    
    @Test
    void testListService_Success() throws Exception {
        
        Card c1 = new Card();
        c1.setId(1L);
        c1.setCardHolder("Mario");
        
        java.util.List<Card> entityList = java.util.Arrays.asList(c1);

        
        when(cardR.findAll()).thenReturn(entityList);

       
        java.util.List<com.betacom.dto.response.card.CardDTO> result = cardService.list();

       
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        
        verify(cardR, times(1)).findAll();
    }
    
    @Test
    void testListService_Error() {
        
        when(cardR.findAll()).thenThrow(new RuntimeException("Database offline"));

       
        assertThatThrownBy(() -> cardService.list())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database offline");

        
        verify(cardR, times(1)).findAll();
    }
    
    @Test
    void testGetByIdService_Success() throws Exception {
       
        Card card = new Card();
        card.setId(1L);
        card.setCardHolder("Mario Rossi");
        
        when(cardR.findById(1L)).thenReturn(Optional.of(card));

        
        com.betacom.dto.response.card.CardDTO result = cardService.getById(1L);

        
        assertThat(result).isNotNull();
        verify(cardR, times(1)).findById(1L);
    }

    
    @Test
    void testGetByIdService_NotFound() {
       
        when(cardR.findById(anyLong())).thenReturn(Optional.empty());

        
        assertThatThrownBy(() -> cardService.getById(99L))
                .isInstanceOf(Exception.class)
                .hasMessage("carta non presente in DB");
    }
    
}
    

