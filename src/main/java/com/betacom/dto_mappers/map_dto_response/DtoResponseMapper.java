	package com.betacom.dto_mappers.map_dto_response;
	
	import com.betacom.model.Address;
	import com.betacom.model.Card;
	import com.betacom.model.Cart;
	import com.betacom.model.CartItem;
	import com.betacom.model.Category;
	import com.betacom.model.Order;
	import com.betacom.model.OrderedItemsDetails;
	import com.betacom.model.PaymentMethod;
	import com.betacom.model.Product;
	import com.betacom.model.Review;
	import com.betacom.model.Size;
	import com.betacom.model.User;
	import com.betacom.model.WishList;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
	
	import com.betacom.dto.response.address.*;
	import com.betacom.dto.response.card.*;
	import com.betacom.dto.response.cart.CartDTO;
	import com.betacom.dto.response.cart_item.*;
	import com.betacom.dto.response.category.*;
	import com.betacom.dto.response.order.*;
	import com.betacom.dto.response.ordered_items_details.*;
	import com.betacom.dto.response.payment_method.*;
	import com.betacom.dto.response.product.ProductsDTO;
	import com.betacom.dto.response.review.ReviewDTO;
	import com.betacom.dto.response.size.SizeDTO;
	import com.betacom.dto.response.user.UserDTO;
	import com.betacom.dto.response.wish_list.WishListDTO;
	
	@Component
	public class DtoResponseMapper {
	
		
		public static AddressDTO addressDTO(Address model) {
			
			return AddressDTO.builder()
					.id(model.getId())
					.city(model.getCity())
					.street(model.getStreet())
					.civic(model.getCivic())
					.staircase(model.getStaircase())
					.province(model.getProvince())
					.cap(model.getCap())
					.country(model.getCountry())
					.user(model.getUser())
					.residence(model.isResidence())
					.domicile(model.isDomicile())
					.defaulAddress(model.isDefaulAddress())
					.build();
		}
		
		
		public static CardDTO cardDTO(Card model) {
			
			return CardDTO.builder()
					.id(model.getId())
					.cardNumber(model.getCardNumber())
					.expiryDate(model.getExpiryDate())
					.cvv(model.getCvv())
					.cardHolder(model.getCardHolder())
					.build();
		}
		
		public static CartDTO cartDTO(Cart model) {
			
			return CartDTO.builder()
					.id(model.getId())
					.user(model.getUser())
					.cartItems(model.getCartItems())
					.createDate(model.getCreateDate())
					.build();
		}
		
		public static CartItemDTO cartItemDTO(CartItem model) {
			
			return CartItemDTO.builder()
					.id(model.getId())
					.quantity(model.getQuantity())
					.cart(model.getCart())
					.product(model.getProduct())
					.build();
		}
		
		public static CategoryDTO categoryDTO(Category model) {
			
			return CategoryDTO.builder()
					.id(model.getId())
					.category(model.getCategory())
					.products(model.getProducts())
					.build();
		}
		
		public static OrderDTO orderDTO(Order model) {
			return OrderDTO.builder()
					.id(model.getId())
					.user(model.getUser())
					.date(model.getDate())
					.status(model.getStatus())
					.orderPrice(model.getOrderPrice())
					.shippingAddress(model.getShippingAddress())
					.details(model.getDetails())
					.build();
		}
		
		public static OrderedItemsDetailsDTO orderDetailsDTO(OrderedItemsDetails model) {
			return OrderedItemsDetailsDTO.builder()
					.id(model.getId())
					.order(model.getOrder())
					.product(model.getProduct())
					.size(model.getSize())
					.quantity(model.getQuantity())
					.totalPrice(model.getTotalPrice())
					.build();
		}
		
		public static PaymentMethodDTO paymentMethodDTO(PaymentMethod model) {
			return PaymentMethodDTO.builder()
					.id(model.getId())
					.description(model.getDescription())
					.userId(model.getUser() != null ? model.getUser().getId() : null)
					.card(model.getCard() != null ? cardDTO(model.getCard()) : null)
					.build();
		}
		
		public static ProductsDTO productsDTO(Product model) {
			return ProductsDTO.builder()
					.id(model.getId())
					.name(model.getName())
					.image(model.getImage())
					.description(model.getDescription())
					.category(categoryDTO(model.getCategory()) )
					.gender(model.getGender())
					.material(model.getMaterial())
					.price(model.getPrice())
					.discount(model.getDiscount())
					.discountPercentage(model.getDiscountPercentage())
					.sizes(model.getSizes().stream().map(size -> sizeDTO(size)).collect(Collectors.toList()))
					.reviews(model.getReviews().stream().map(review -> reviewDTO(review)).collect(Collectors.toList()))
					.build();
		}
		
		public static ReviewDTO reviewDTO(Review model) {
			return ReviewDTO.builder()
					.id(model.getId())
					.user(model.getUser())
					.product(model.getProduct())
					.rating(model.getRating())
					.review(model.getReview())
					.date(model.getDate())
					.build();
		}
		
		public static SizeDTO sizeDTO(Size model) {
			
			return SizeDTO.builder()
					.id(model.getId())
					.product(model.getProduct())
					.size(model.getSize())
					.quantity(model.getQuantity())
					.build();
		}
		
		public static UserDTO userDTO(User model) {
			
			return UserDTO.builder()
					.id(model.getId())
					.birthday(model.getBirthday())
					.createDate(model.getCreateDate())
					.codiceFiscale(model.getCodiceFiscale())
					.email(model.getEmail())
					.lastName(model.getLastName())
					.name(model.getName())
					.password(model.getPassword())
					.phone(model.getPhone())
					.role(model.getRole())
					.build();
			
		}
		
		public static WishListDTO wishListDTO(WishList model) {
			return WishListDTO.builder()
					.id(model.getId())
					.user(model.getUser())
					.product(model.getProduct())
					.createDate(model.getCreateDate())
					.build();
		}
	}
