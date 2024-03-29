package com.project.lapstore.core.item.domain.entity;

import static com.project.lapstore.core.common.exception.CommonValidationError.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

import org.springframework.util.Assert;

import com.project.lapstore.core.common.entity.TimeBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "item_table")
public class Item extends TimeBaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "item_id")
	private Long id;

	@Column(name = "item_name", nullable = false, unique = true)
	private String name;

	@Enumerated(STRING)
	@Column(name = "item_brand", nullable = false)
	private ItemBrand brand;

	@Column(name = "item_wish_count", columnDefinition = "integer default 0")
	private int wishCount;

	private Item(String name, ItemBrand brand) {
		validateItem(name, brand);

		this.name = name;
		this.brand = brand;
	}

	private void validateItem(String name, ItemBrand itemBrand) {
		Assert.hasText(name, getNotEmptyMessage("Item", "name"));
		Assert.notNull(itemBrand, getNotNullMessage("Item", "itemBrand"));
	}

	public static Item of(String name, ItemBrand brand) {
		return new Item(name, brand);
	}
}
