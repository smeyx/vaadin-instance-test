package com.example.application.views.main;

import com.example.application.components.InstanceElement;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

@PageTitle("Instance Overview")
@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {
	HorizontalLayout instanceGroup = new HorizontalLayout();

	public MainView() {
		Icon plusIcon = new Icon("lumo", "plus");
		plusIcon.setSize("2rem");

		Button addButton = new Button(plusIcon);
		addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		addButton.addClassNames("btn-add");
		addButton.addClickListener(click -> {
			Notification.show("Add instance");
		});
		
		instanceGroup.setWidthFull();
		instanceGroup.addClassNames("instance-group", "flex-wrap");
		createInstanceList();

		add(addButton, instanceGroup);
	}
	
	private void createInstanceList() {
		for(Integer i = 1; i <=5; i++) {
			InstanceElement instance = new InstanceElement();
			instance.setName("Instance no. 1");
			instance.setThreatLevel(200);

			instanceGroup.add(instance);
		}
	}
}