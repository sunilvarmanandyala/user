package com.sunil.UserMicroservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sunil.UserMicroservice.Entity.JwtUsername;
import com.sunil.UserMicroservice.ServiceImpl.CustomUserDetailsService;
import com.sunil.UserMicroservice.helper.JwtUtil;
import com.sunil.UserMicroservice.model.JwtRequest;
import com.sunil.UserMicroservice.model.JwtResponce;



@RestController
@CrossOrigin
public class JwtController {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	@RequestMapping(value="/token", method = RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception
	{
		System.out.println(jwtRequest);
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
			
		}catch(UsernameNotFoundException e)
		{
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		}catch(BadCredentialsException e)
		{
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		}
	UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
	String token = this.jwtUtil.generateToken(userDetails);
	System.out.println("JWT"+token);
	return ResponseEntity.ok(new JwtResponce(token));
	}
	@PostMapping("/token/get")
    public ResponseEntity<?> getUsername(@RequestBody String token) {
        String username= jwtUtil.extractUsername(token);
        return ResponseEntity.ok(new JwtUsername(username));
   }

}
