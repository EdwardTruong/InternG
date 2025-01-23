package com.example.jwt.domain.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.jwt.appilication.constant.ContantUnit.MESSENGER_NOT_FOUND;
import com.example.jwt.appilication.utils.ERole;
import com.example.jwt.domain.model.Role;
import com.example.jwt.domain.repository.RoleRepository;
import com.example.jwt.domain.service.RoleService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {

	RoleRepository roleRepository;

	@Override
	public Role findByName(ERole name) {
		Optional<Role> result = roleRepository.findByName(name);
		return result.orElseThrow(() -> new RuntimeException(MESSENGER_NOT_FOUND.ROLE_NOT_EXIST));
	}

}
