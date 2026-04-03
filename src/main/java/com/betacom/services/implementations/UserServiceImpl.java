package com.betacom.services.implementations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import com.betacom.dto.request.cart.CartRequest;
import com.betacom.dto.request.login.LoginRequest;
import com.betacom.dto.request.user.UserCreateRequest;
import com.betacom.dto.request.user.UserUpdateRequest;
import com.betacom.dto.response.login.LoginDTO;
import com.betacom.dto.response.user.UserDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.enums.Roles;
import com.betacom.model.User;
import com.betacom.repository.AddressRepository;
import com.betacom.repository.OrderRepository;
import com.betacom.repository.ReviewRepository;
import com.betacom.repository.UserRepository;
import com.betacom.services.interfaces.InterfaceUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements InterfaceUserService{
	
	private final UserRepository userR;
	private final AddressRepository addressR;
	private final OrderRepository orderR;
	private final ReviewRepository reviewR;
	private final CartServiceImpl cartService;
	private final SecurityContextRepository securityContextRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDTO getById(Long id) throws Exception {
		log.debug("getById {}", id);
		
		User user = userR.findById(id)
		        .orElseThrow(() -> new Exception("utente non presente in DB"));

		return DtoResponseMapper.userDTO(user);
	}

	@Override
	public List<UserDTO> list() throws Exception {
		log.debug("list");
		
		List<User> users = userR.findAll();
		
		return users.stream().map(user -> 
			DtoResponseMapper.userDTO(user))
				.collect(Collectors.toList());
	}

	@Override
	public void create(UserCreateRequest request) throws Exception {
		log.debug("create {}", request);
		
		User user = new User();
		user.setName(request.getName());
		user.setLastName(request.getLastName());
		user.setBirthday(request.getBirthday());
		if(request.getCodiceFiscale() != null)
			user.setCodiceFiscale(request.getCodiceFiscale());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setPhone(request.getPhone());
		user.setRole(request.getRole());
		
		User userSaved = userR.save(user);
		cartService.create(new CartRequest(userSaved.getId()));
	}

	@Override
	public void update(UserUpdateRequest request) throws Exception {
		log.debug("update {}", request);

		User user = userR.findById(request.getId())
		        .orElseThrow(() -> new Exception("utente non presente in DB"));

	    if (request.getName() != null) {
	        user.setName(request.getName());
	    }
	    
	    

	    if (request.getLastName() != null) {
	        user.setLastName(request.getLastName());
	    }

	    if (request.getBirthday() != null) {
	        user.setBirthday(request.getBirthday());
	    }

	    if (request.getCodiceFiscale() != null) {
	        user.setCodiceFiscale(request.getCodiceFiscale());
	    }

	    if (request.getEmail() != null) {
	        user.setEmail(request.getEmail());
	    }

	    if (request.getPassword() != null) {
	        user.setPassword(request.getPassword());
	    }

	    if (request.getPhone() != null) {
	        user.setPhone(request.getPhone());
	    }

	    if (request.getRole() != null) {
	        user.setRole(Roles.valueOf(request.getRole()));
	    }

	    userR.save(user);
	}

	@Override
	public void delete(Long id) throws Exception {
		log.debug("delete {}", id);

		User user = userR.findById(id)
		        .orElseThrow(() -> new Exception("utente non presente in DB"));
		
		if(user.getAddresses() != null) {
			user.getAddresses().forEach(address -> address.setUser(null));
			addressR.saveAll(user.getAddresses());
		}
		
		if (user.getOrders() !=null) {
			
			user.getOrders().forEach(order-> order.setUser(null));
			orderR.saveAll(user.getOrders());
		
		}
		
		if(user.getReviews()!= null) {
			user.getReviews().forEach(review -> review.setUser(null));
			reviewR.saveAll(user.getReviews());
			
		}
		
		userR.delete(user);
	}

	@Override
	public LoginDTO login(LoginRequest request, 
            HttpServletRequest httpRequest, 
            HttpServletResponse httpResponse) throws Exception {

	
				User user = userR.findByEmail(request.getEmail())
				  .orElseThrow(() -> new Exception("Credenziali non valide"));
				
				if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
				throw new Exception("Credenziali non valide");
				}
				
	
				UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
				  user.getEmail(), null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
				
	
				SecurityContext context = SecurityContextHolder.createEmptyContext();
				context.setAuthentication(authReq);
				SecurityContextHolder.setContext(context);
				securityContextRepository.saveContext(context, httpRequest, httpResponse);

				return DtoResponseMapper.loginDTO(user);
}

}
