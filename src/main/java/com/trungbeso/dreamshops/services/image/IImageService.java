package com.trungbeso.dreamshops.services.image;

import com.trungbeso.dreamshops.dtos.ImageDto;
import com.trungbeso.dreamshops.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
	Image getImageById(Long id);

	void deleteImageById(Long id);

	List<ImageDto> saveImages(List<MultipartFile> files, Long productId);

	void updateImage(MultipartFile file, Long imageId);
}
