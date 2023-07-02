package com.example.application.views.instances;

import com.example.application.api.client.RestClient;
import com.example.application.api.dto.instance.Instance;
import com.example.application.components.InstanceElement;
import com.example.application.components.AddInstanceDialog;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

@PageTitle("Instance Overview")
@Route(value = "", layout = MainLayout.class)
public class InstancesView extends VerticalLayout {
	HorizontalLayout instanceGroup = new HorizontalLayout();
	AddInstanceDialog addInstanceDialog = new AddInstanceDialog();
	private RestClient instanceService;

	public InstancesView(@Autowired RestClient instanceService) {
		this.instanceService = instanceService;

		addInstanceDialog.setHeaderTitle("Add new instance");

		Icon plusIcon = new Icon("lumo", "plus");
		Button addButton = new Button("Add new instance", plusIcon);
		addButton.addClassName("btn-add-wide");
		addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		addButton.addClickListener(click -> {
			addInstanceDialog.open();
		});
		
		Button mobileAddButton = new Button(plusIcon);
		mobileAddButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		mobileAddButton.addClassName("btn-add");
		mobileAddButton.addClickListener(click -> {
			addInstanceDialog.open();
		});

		instanceGroup.setWidthFull();
		instanceGroup.addClassNames("instance-group", "flex-wrap");
		createInstanceList();

		System.out.println("MainView");

		add(addButton, mobileAddButton, instanceGroup, addInstanceDialog);
	}

	private void createInstanceList() {
		for(int i = 1; i <=5; i++) {
			InstanceElement instance = new InstanceElement();
			instance.setName("IRMA Instance no. " + i);
			instance.setThreatLevel(200);

			instanceGroup.add(instance);
		}
	}
}