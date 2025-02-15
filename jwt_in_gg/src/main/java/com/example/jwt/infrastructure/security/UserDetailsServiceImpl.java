package com.example.jwt.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jwt.appilication.constant.ContantUnit.MESSENGER_NOT_FOUND;
import com.example.jwt.appilication.constant.ContantUnit.STATUS;
import com.example.jwt.domain.model.UserEntity;
import com.example.jwt.domain.repository.UserRepository;
import com.example.jwt.infrastructure.exception.UserNotFoundException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/*
 * 	UserDetailsService have only one method loadUserByUsername. 
 * 	When user login, user's information saved in UserDetailsCustom;
 */
@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService {

	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUsernameAndStatus(username, STATUS.ACTIVE)
				.orElseThrow(() -> new UserNotFoundException(MESSENGER_NOT_FOUND.USER_NOT_FOUND_EMAIL + username));
		UserDetailsCustom userloginSuccess = UserDetailsCustom.build(userEntity);
		log.info("User login and save user's infomations into UserDetailsCustom. with role size {}",
				userloginSuccess.getAuthorities().size());

		return userloginSuccess;
	}

}
