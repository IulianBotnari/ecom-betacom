package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.wish_list.WishlistCreateRequest;
import com.betacom.dto.request.wish_list.WishlistUpdateRequest;
import com.betacom.dto.response.wish_list.WishListDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.model.WishList;
import com.betacom.repository.WishListRepository;
import com.betacom.repository.UserRepository;
import com.betacom.repository.ProductRepository;
import com.betacom.services.interfaces.InterfaceWhishListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class WishListServiceImpl implements InterfaceWhishListService {

    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // ---- GET BY ID ----
    @Override
    public WishListDTO getById(Long id) throws Exception {
        WishList wish = wishListRepository.findById(id)
                .orElseThrow(() -> new Exception("Prodotto non trovato nella lista"));

        return DtoResponseMapper.wishListDTO(wish);
    }

    // ---- LIST ALL ----
    @Override
    public List<WishListDTO> list() throws Exception {
        return wishListRepository.findAll()
                .stream()
                .map(DtoResponseMapper::wishListDTO)
                .collect(Collectors.toList());
    }

    // ---- CREATE ----
    @Override
    public void create(WishlistCreateRequest request) throws Exception {
        WishList wish = new WishList();

        wish.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("Utente non trovato")));

        wish.setProduct(productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Prodotto non trovato")));

        wishListRepository.save(wish);
    }

    // ---- UPDATE ----
    @Override
    public void update(WishlistUpdateRequest request) throws Exception {
        WishList item = wishListRepository.findById(request.getId())
                .orElseThrow(() -> new Exception("Elemento wishlist non trovato"));

        wishListRepository.save(item);
    }

    // ---- DELETE ----
    @Override
    public void delete(Long id) throws Exception {
        if (!wishListRepository.existsById(id)) {
            throw new Exception("Prodotto non trovato nella lista");
        }

        wishListRepository.deleteById(id);
    }
}