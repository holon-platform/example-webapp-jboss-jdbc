package com.holonplatform.example.app.views;

import static com.holonplatform.example.app.model.Product.CATEGORY;
import static com.holonplatform.example.app.model.Product.DESCRIPTION;
import static com.holonplatform.example.app.model.Product.ID;
import static com.holonplatform.example.app.model.Product.PRODUCT;
import static com.holonplatform.example.app.model.Product.SKU;
import static com.holonplatform.example.app.model.Product.TARGET;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.holonplatform.core.Validator;
import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.core.datastore.DefaultWriteOption;
import com.holonplatform.core.exceptions.DataAccessException;
import com.holonplatform.vaadin.components.Components;
import com.holonplatform.vaadin.components.PropertyInputForm;
import com.holonplatform.vaadin.navigator.ViewNavigator;
import com.holonplatform.vaadin.navigator.annotations.OnShow;
import com.holonplatform.vaadin.navigator.annotations.ViewParameter;
import com.holonplatform.vaadin.navigator.annotations.VolatileView;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@VolatileView
@SpringView(name = "manage")
public class Manage extends VerticalLayout implements com.vaadin.navigator.View {

	private static final long serialVersionUID = 1L;

	@ViewParameter
	private Long id;

	@Autowired
	private Datastore datastore;

	private PropertyInputForm form;

	private Button clearButton;

	@PostConstruct
	public void init() {
		Components.configure(this)
				// set margins and size full to view content
				.margin().fullSize()
				.addAndExpandFull(
						// add a form using Product property set
						form = Components.input.form().fullSize().properties(PRODUCT)
								// set id as read-only
								.readOnly(ID)
								// set SKU as required
								.required(SKU)
								// set "DFT" as CATEGORY default value
								.defaultValue(CATEGORY, p -> "DFT")
								// add a validator to check DESCRIPTION with minimum 3 characters
								.withValidator(DESCRIPTION, Validator.min(3))
								// build the form
								.build())
				.add(Components.hl().margin().spacing()
						// SAVE action
						.add(Components.button().caption("Save").styleName(ValoTheme.BUTTON_PRIMARY)
								.onClick(e -> save()).build())
						// CLEAR action
						.add(clearButton = Components.button().caption("Clear")
								// clear the form
								.onClick(e -> form.clear()).build())
						.build());
	}

	@OnShow
	public void load() {
		// if id parameter is not null, we are in edit mode
		if (id != null) {
			// load the product data
			form.setValue(datastore.query().target(TARGET).filter(ID.eq(id)).findOne(PRODUCT)
					// throw an exception if a product with given id was not found
					.orElseThrow(() -> new DataAccessException("Data not found: " + id)));
		}
		// enable the Clear button if not in edit mode
		clearButton.setVisible(id == null);
	}

	@Transactional
	private void save() {
		// check valid and get PropertyBox value
		form.getValueIfValid().ifPresent(value -> {

			// save and notify
			datastore.save(TARGET, value, DefaultWriteOption.BRING_BACK_GENERATED_IDS);
			// notify the saved id
			Notification.show("Saved [" + ((id != null) ? id : value.getValue(ID)) + "]");

			// go back home
			ViewNavigator.require().navigateToDefault();
		});

	}

}
