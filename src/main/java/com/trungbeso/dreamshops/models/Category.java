package com.trungbeso.dreamshops.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	//1 category belong to many product
	@JsonIgnore
	@OneToMany(mappedBy = "category")
	private List<Product> products;

	public Category(String name) {
		this.name = name;
	}
}
