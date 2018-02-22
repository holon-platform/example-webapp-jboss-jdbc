package com.holonplatform.example.app;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;

@SpringUI
@SpringViewDisplay
@Theme("test")
public class UI extends com.vaadin.ui.UI {

	private static final long serialVersionUID = -2791341023009988674L;

	@Override
	protected void init(VaadinRequest request) {
	}

}
