package com.holonplatform.example.app.views;

import static com.holonplatform.example.app.model.Product.ID;
import static com.holonplatform.example.app.model.Product.PRODUCT;
import static com.holonplatform.example.app.model.Product.TARGET;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.vaadin.components.Components;
import com.holonplatform.vaadin.components.PropertyListing;
import com.holonplatform.vaadin.navigator.ViewNavigator;
import com.holonplatform.vaadin.navigator.annotations.OnShow;
import com.holonplatform.vaadin.spring.DefaultView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@DefaultView
@UIScope
@SpringView(name = "home")
public class Home extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	@Autowired
	private Datastore datastore;

	private PropertyListing listing;

	@PostConstruct
	public void init() {
		Components.configure(this)
				// set full to view content
				.fullSize().spacing()
				.add(Components.button().caption("Add new").styleName(ValoTheme.BUTTON_PRIMARY)
						// navigate to "manage" view
						.onClick(e -> ViewNavigator.require().toView("manage").navigate()).build())
				// build and add listing
				.addAndExpandFull(listing = Components.listing.properties(PRODUCT)
						// setup data source using Datastore with 'products' table name target and product ID as pk
						.dataSource(datastore, TARGET, ID)
						// froze the ID column
						.frozenColumns(1)
						// set the ID column width and style
						.width(ID, 120).style(ID, "id-column")
						// when user clicks on a row, open the 'view' named View providing product id parameter
						.withItemClickListener((i, p, e) -> ViewNavigator.require().toView("view")
								.withParameter("id", i.getValue(ID)).navigate())
						// set full size and build
						.fullSize().build());
	}

	@OnShow
	public void onShow() {
		// refresh listing at view display
		listing.refresh();
	}

}
