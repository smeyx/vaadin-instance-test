package com.example.application.components;

import org.jsoup.helper.W3CDom;

import com.example.application.api.client.RestClient;
import com.example.application.api.dto.instance.Instance;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.progressbar.ProgressBarVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import reactor.core.publisher.Mono;


public class AddInstanceDialog extends Dialog {
    RestClient service = new RestClient();
    TextField ipAddress = new TextField("IP-address");
    TextField title = new TextField("Title");
    NumberField port = new NumberField("Port");
    ProgressBar progressBar = new ProgressBar();
    Span progressBarLabel = new Span();
    saveInstanceCallback<Instance> callback;

    public AddInstanceDialog(saveInstanceCallback<Instance> callback) {
    	this.callback = callback;
        createLayout();
        createFooter();
    }

    private void createFooter() {
        Button cancelButton = new Button(
                "Cancel",
                e -> close()
        );

        Button saveButton = new Button(
                "Save",
                e -> saveInstance(callback)
        );
       saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
       saveButton.addClickShortcut(Key.ENTER);

        getFooter().add(cancelButton, saveButton);
    }

    private void createLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        layout.getStyle().set("max-width", "100%").set("width", "300px");

        title.setPlaceholder("The title for this IRMA instance.");

        ipAddress.setPlaceholder("The IP of this IRMA instance.");
        ipAddress.addValueChangeListener(e -> {
        	pingInstance();
        });
        
        port.addValueChangeListener(e -> {
        	pingInstance();
        });
        
        port.setPlaceholder("The port of this IRMA instance.");
        progressBar.setVisible(false);
        progressBarLabel.getStyle().set("align-self", "center");
        
        layout.add(title, ipAddress, port, progressBar, progressBarLabel);
        add(layout);
    }
    
    private void resetProgressBar() {
    	progressBar.setIndeterminate(true);
    	progressBar.setVisible(true);
    	progressBarLabel.getElement().getThemeList().clear();
    }
    
    private void pingInstance() {
    	if(ipAddress.isEmpty() || port.isEmpty()) return;
    	resetProgressBar();

    	service.fetchInstance("http://" + ipAddress.getValue() + ":" + port.getValue().intValue() + "/api/v1/instance")
    	.subscribe(
    			instance -> {
    				if (!instance.getTitle().isEmpty()) {
    					changeUi(() -> {
    						progressBar.setVisible(false);
    						progressBarLabel.setText("Valid instance");
    						progressBarLabel.getElement().getThemeList().add("badge success");
    					});
    				}
    			},
    			error -> {
    				changeUi(() -> {
    					progressBar.setVisible(false);
    					progressBarLabel.setText("Instance not found.");
    					progressBarLabel.getElement().getThemeList().add("badge error");
    				});
    			}
    	);
    }
    
    public static interface saveInstanceCallback<T> {
    	void save(T result);
    }
    
    private void saveInstance(saveInstanceCallback<Instance> callback) {
    	Instance instance = new Instance();
    	instance.setTitle(title.getValue());
    	instance.setWebURL(ipAddress.getValue());
    	instance.setPort(port.getValue().intValue());
    	callback.save(instance);
    }
    
    private static interface changeUiCallback {
    	void changeUi();
    }
    
    private void changeUi(changeUiCallback callback) {
    	UI ui = getUI().orElseThrow();
    	ui.access(() -> {
    		callback.changeUi();
    	});
    }
}
