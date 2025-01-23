package com.trungbeso.dreamshops.repositories;

import com.trungbeso.dreamshops.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IImageRepository extends JpaRepository<Image, Long> {
	List<Image> findByProductId(Long id);
}
