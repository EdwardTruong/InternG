package com.example.jwt.domain.service;

import com.example.jwt.appilication.utils.ERole;
import com.example.jwt.domain.model.Role;

public interface RoleService {
	Role findByName(ERole name);
}
