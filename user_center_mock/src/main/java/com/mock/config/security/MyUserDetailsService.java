package com.mock.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {
	
	public static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
	
//	 @Autowired
//	 private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 logger.info("用户的用户名: {}", username);
		 PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	     String password = passwordEncoder.encode("123456");
	     logger.info("password: {}", password);
	     // 参数分别是：用户名，密码，用户权限
	     User user = new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMI"));
	     return user;
	}

}