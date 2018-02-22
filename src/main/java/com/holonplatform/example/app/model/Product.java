package com.holonplatform.example.app.model;

import com.holonplatform.core.Validator;
import com.holonplatform.core.datastore.DataTarget;
import com.holonplatform.core.property.PathProperty;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.property.PropertyValueConverter;

public interface Product {

	public static final PathProperty<Long> ID = PathProperty.create("id", Long.class).message("Product ID");

	public static final PathProperty<String> SKU = PathProperty.create("sku", String.class).message("SKU")
			// not null validator
			.validator(Validator.notNull());

	public static final PathProperty<String> DESCRIPTION = PathProperty.create("description", String.class)
			.message("Description");

	public static final PathProperty<String> CATEGORY = PathProperty.create("category", String.class)
			.message("Category");

	public static final PathProperty<Double> UNIT_PRICE = PathProperty.create("price", Double.class).message("Price")
			// not negative value validator
			.validator(Validator.notNegative());

	public static final PathProperty<Boolean> WITHDRAWN = PathProperty.create("withdrawn", Boolean.class)
			.message("Withdrawn")
			// set a property value converter from Integer model type to Boolean
			.converter(PropertyValueConverter.numericBoolean(Integer.class));

	// Product property set
	public static final PropertySet<?> PRODUCT = PropertySet.of(ID, SKU, DESCRIPTION, CATEGORY, UNIT_PRICE, WITHDRAWN);

	// "products" DataTarget
	public static final DataTarget<?> TARGET = DataTarget.named("products");

}
