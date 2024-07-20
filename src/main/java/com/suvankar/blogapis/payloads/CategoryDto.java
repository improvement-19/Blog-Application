package com.suvankar.blogapis.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class CategoryDto {
	private Integer categoryId;
	@NotBlank
	@Size(min=4,message="Min size of category should be 4 ! ")
	private String categoryTitle;
	@NotBlank
	@Size(min=10,message="Min size of category description should be 10 ")
	private String categoryDescription;
	
}
