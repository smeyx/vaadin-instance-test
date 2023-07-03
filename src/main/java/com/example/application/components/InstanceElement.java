package com.example.application.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Tag("div")
public class InstanceElement extends Composite<Div> {
	H3 name = new H3();
	Span threatLevel = new Span();
	Paragraph url = new Paragraph();

	public InstanceElement() {
		getContent().addClassName("instance-wrapper");
		
		threatLevel.addClassName(LumoUtility.FontSize.XXLARGE);
		Component headerComponent = createHeader();
		Component informationComponent = createInformationWrapper();
		getContent().add(
			headerComponent,
			informationComponent
		);
	}

	private Component createHeader() {
		Div circle = new Div();
		circle.addClassNames("is-circle", "green-threat", "center-content", "threatlevel");
		circle.add(threatLevel);

		return circle;
	}
	
	private Component createInformationWrapper() {
		Div wrapperDiv = new Div();
		wrapperDiv.addClassNames("content-wrapper");
		wrapperDiv.add(name);
		wrapperDiv.add(url);
		
		return wrapperDiv;
	}

	public String getName() {
		return this.name.getText();
	}

	public void setName(String name) {
		this.name.setText(name);
	}

	public String getUrl() {
		return this.url.getText();
	}

	public void setUrl(String url) {
		this.url.setText(url);
	}

	public Integer getThreatLevel() {
		return Integer.parseInt(this.threatLevel.getText());
	}

	public void setThreatLevel(Integer threatLevel) {
		this.threatLevel.setText(threatLevel.toString());
	}
}