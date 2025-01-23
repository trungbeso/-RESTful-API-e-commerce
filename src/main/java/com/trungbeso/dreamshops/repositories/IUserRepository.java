package com.trungbeso.dreamshops.repositories;

import com.trungbeso.dreamshops.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);
}
