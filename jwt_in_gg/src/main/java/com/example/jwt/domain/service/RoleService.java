package com.example.cache_example_with_ehcache.domain.service;

import com.example.cache_example_with_ehcache.appilication.utils.ERole;
import com.example.cache_example_with_ehcache.domain.model.Role;

public interface RoleService {
	Role findByName(ERole name);
}
