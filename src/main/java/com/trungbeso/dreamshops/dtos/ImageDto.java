package com.trungbeso.dreamshops.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageDto {
	Long id;
	String fileName;
	String downloadUrl;

}
