package com.example.application.views.instances;

import com.example.application.api.client.RestClient;
import com.example.application.api.dto.instance.Instance;
import com.example.application.components.InstanceElement;
import com.example.application.components.AddInstanceDialog;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import io.netty.handler.codec.http.HttpScheme;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

@PageTitle("Instance Overview")
@Route(value = "", layout = MainLayout.class)
public class InstancesView extends VerticalLayout {
	HorizontalLayout instanceGroup = new HorizontalLayout();
	AddInstanceDialog addInstanceDialog;
	RestClient instanceService;
	List<Instance> instanceList = new ArrayList<Instance>();

	public InstancesView(@Autowired RestClient instanceService) {
		this.instanceService = instanceService;

		addInstanceDialog = new AddInstanceDialog((instance) -> {
			instanceList.add((Instance) instance);
			createInstanceList();
			addInstanceDialog.close();
		});
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
		createInstances();
		createInstanceList();

		System.out.println("MainView");

		add(addButton, mobileAddButton, instanceGroup, addInstanceDialog);
	}
	
	private void createInstanceList() {
		instanceGroup.removeAll();
		List<Component> instanceElements = new ArrayList<>();
		instanceList.forEach(instance -> {
			InstanceElement instanceElement = new InstanceElement();
			instanceElement.setName(instance.getTitle());
			instanceElement.setUrl("http://" + instance.getWebURL() + ":" + instance.getPort());
			instanceElement.setThreatLevel(200);
			
			instanceElements.add(instanceElement);
		});
		
		instanceGroup.add(instanceElements);
	}

	private void createInstances() {
		for(int i = 1; i <=5; i++) {
			Instance instance = new Instance();
			instance.setTitle("IRMA Instance no. " + i);
			instance.setPort(3000);
			instance.setWebURL("127.0.0.1");

			instanceList.add(instance);
		}
	}
}