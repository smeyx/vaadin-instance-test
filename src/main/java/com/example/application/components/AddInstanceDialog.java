package com.example.application.components;

import com.example.application.api.client.RestClient;
import com.example.application.api.dto.instance.Instance;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import reactor.core.publisher.Mono;


public class AddInstanceDialog extends Dialog {
    RestClient service = new RestClient();
    TextField ipAddress;
    TextField title;
    public AddInstanceDialog() {
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
                e -> Notification.show("Saved")
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

        title = new TextField("Title");
        title.setPlaceholder("The title for this IRMA instance.");
        ipAddress = new TextField("IP-address");
        ipAddress.setPlaceholder("The IP of this IRMA instance.");
        ipAddress.setWidthFull();
        ipAddress.addValueChangeListener(e -> {
            System.out.println("Value changed");
            service.fetchInstance("http://" + e.getValue() + "/api/v1/instance")
                    .subscribe(
                            instance -> {
                                if (!instance.getTitle().isEmpty()) {
                                    UI ui = getUI().orElseThrow();
                                    ui.access(() -> {
                                        ipAddress.getStyle().set("border-color", "var(--lumo-success-text-color)");
                                    });
                                }
                            },
                            System.err::println
                    );
        });

        layout.add(title, ipAddress);
        add(layout);
    }
}
