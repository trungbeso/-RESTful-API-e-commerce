package com.trungbeso.dreamshops.services.image;

import com.trungbeso.dreamshops.dtos.ImageDto;
import com.trungbeso.dreamshops.exception.ResourceNotFoundException;
import com.trungbeso.dreamshops.models.Image;
import com.trungbeso.dreamshops.models.Product;
import com.trungbeso.dreamshops.repositories.IImageRepository;
import com.trungbeso.dreamshops.services.product.IProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ImageService implements IImageService{

	IImageRepository imageRepository;
	IProductService productService;

	@Override
	public Image getImageById(Long id) {
		return imageRepository.findById(id)
			  .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
	}

	@Override
	public void deleteImageById(Long id) {
		imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
			throw new ResourceNotFoundException("No image found with id: " + id);
		});
	}

	@Override
	public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
		Product product = productService.getProductById(productId);

		List<ImageDto> savedImageDto = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				Image image = new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileType(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));
				image.setProduct(product);

				String buildDownloadUrl = "/api/v1/images/image/download/";

				String downloadUrl = buildDownloadUrl + image.getId();
				image.setDownloadUrl(downloadUrl);
				Image savedImage = imageRepository.save(image);

				// save correct id of savedImage
				savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
				imageRepository.save(savedImage);

				ImageDto imageDto = new ImageDto();
				imageDto.setId(savedImage.getId());
				imageDto.setFileName(savedImage.getFileName());
				imageDto.setDownloadUrl(savedImage.getDownloadUrl());

				savedImageDto.add(imageDto);

			} catch (IOException | SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return savedImageDto;
	}

	@Override
	public void updateImage(MultipartFile file, Long imageId) {
		Image image = getImageById(imageId);
		try {
			image.setFileName(file.getOriginalFilename());
			image.setFileType(file.getContentType());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepository.save(image);
		} catch (IOException | SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
