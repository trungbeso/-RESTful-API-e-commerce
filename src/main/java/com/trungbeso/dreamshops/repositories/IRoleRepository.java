package com.trungbeso.dreamshops.repositories;

import com.trungbeso.dreamshops.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
}
