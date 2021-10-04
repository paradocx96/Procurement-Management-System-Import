package com.csse.pms.dal.adapter;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.csse.pms.dal.model.ERole;
import com.csse.pms.dal.model.EmailSender;
import com.csse.pms.dal.model.Role;
import com.csse.pms.dal.model.SupplierModel;
import com.csse.pms.dal.repository.InternelUserRepository;
import com.csse.pms.dal.repository.RoleMongoRepository;
import com.csse.pms.dal.repository.SupplierRepository;
import com.csse.pms.domain.Supplier;
import com.csse.pms.domain.SupplierDataAdapter;
import com.csse.pms.dto.JwtResponseDto;
import com.csse.pms.dto.SupplierMessageResponseDto;
import com.csse.pms.security.jwt.JwtUtils;
import com.csse.pms.util.CommonConstants;



/**
 * 
 * @author Malwatta H.G.
 * 
 * This class handle by the supplier related methods
 *     - register @see #registerSupplier(Supplier)
 *     - login
 *     - add items
 *     - view items
 *     - delete items
 *     - edit items
 *
 */

@Component
public class SupplierAdapterImpl implements SupplierDataAdapter{

	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	RoleMongoRepository roleMongoRepository;
	
	@Autowired
	private InternelUserRepository internelUserRepository;
	
	/**
     * Initialize Logger
     */
    public static final Logger LOGGER = Logger.getLogger(SupplierAdapterImpl.class.getName());

	@Override
	public ResponseEntity<?> registerSupplier(Supplier supplier) {
		
		/**
		 * Check whether user mail is already in the database,
		 * because user mail should be unique 
		 *  
		 */
		if(supplierRepository.existsByEmail(supplier.getEmail())) {
			return ResponseEntity.badRequest().body(new SupplierMessageResponseDto(CommonConstants.SUPPLIER_EMAIL_REGISTRATION_ERROR_MSG));
		}
		
		if(internelUserRepository.existsByEmail(supplier.getEmail())) {
			return ResponseEntity.badRequest().body(new SupplierMessageResponseDto(CommonConstants.SUPPLIER_EMAIL_REGISTRATION_ERROR_MSG));
		}
		
		SupplierModel supplierDetails = new SupplierModel(
				supplier.getName(),
				supplier.getEmail(),
				supplier.getPassword(),
				supplier.getContactNo(),
				supplier.getAddress(),
				supplier.getLocation(),
				supplier.getStatus()
				);
		
		//Create new HashSet to store user Roles
		Set<Role> roles = new HashSet<>();
						
		//If it is true, Add ROLE_USER to that user
		Role userRole = roleMongoRepository.findByName(ERole.ROLE_SUPPLIER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				
		roles.add(userRole);
		
		//set all roles to user object
		supplierDetails.setRoles(roles);
		
		supplierRepository.save(supplierDetails);
		
		/**
		 * Set the values to email sender class and call 
		 * send email method to send the email
		 * 
		 */
		emailSender.setEmail(supplier.getEmail());
		emailSender.setUsername(supplier.getName());
		
		try {
				emailSender.sendEmail();
				
		} catch (UnsupportedEncodingException | MessagingException e) {
			
			 LOGGER.log(Level.SEVERE, e.getMessage());
		}
		
		//return success MSG to frontEnd user is registered successfully
		return ResponseEntity.ok(new SupplierMessageResponseDto(CommonConstants.SUPPLIER_REGISTRATION_SUCCESS_MSG));
	}

	@Override
	public ResponseEntity<?> loginSupplier(Supplier supplier) {
		
		
				//Get user name and password and create new AuthenticationToken 
				Authentication authentication = authenticationManager.authenticate(
								new UsernamePasswordAuthenticationToken(supplier.getEmail(), supplier.getPassword()));

				//Set above assigned user credentials using Authentication object
				SecurityContextHolder.getContext().setAuthentication(authentication);
						
				//After that create new JWT Token for that person
				String jwt = jwtUtils.generateJwtToken(authentication);
						
				//Then get authentication principles and set that UserDetailimpl object 
				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();	
						
				//Get getAuthorities and set to List object
				List<String> roles = userDetails.getAuthorities().stream()
								.map(item -> item.getAuthority())
								.collect(Collectors.toList());
				
				//This is for check the program display correct values or not
				System.out.println(userDetails.getUsername());
				System.out.println(userDetails.getPassword());
				System.out.println(jwt);
				System.out.println(roles.toString());
				
				
				//Return JWT response to FrontEnd
				return ResponseEntity.ok(new JwtResponseDto(jwt, 
															userDetails.getId(), 
															userDetails.getUsername(), 
															userDetails.getName(), 
															roles));
	}
	
}
