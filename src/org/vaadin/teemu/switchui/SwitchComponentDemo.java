package org.vaadin.teemu.switchui;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class SwitchComponentDemo extends Application implements
        Property.ValueChangeListener {

    private Window mainWindow;
    private CheckBox checkBox;
    private List<Switch> allSwitches = new ArrayList<Switch>(3);
    private Panel mainPanel;

    @Override
    public void init() {
        initWindowAndDescription();
        initDemoPanel();
    }

    private void initWindowAndDescription() {
        mainWindow = new Window("Switch Component Demo");
        setMainWindow(mainWindow);

        VerticalLayout centerLayout = new VerticalLayout();
        centerLayout.setMargin(true);

        mainPanel = new Panel();
        mainPanel.setWidth("750px");
        centerLayout.addComponent(mainPanel);
        centerLayout.setComponentAlignment(mainPanel, Alignment.TOP_CENTER);
        mainWindow.setContent(centerLayout);

        StringBuilder descriptionXhtml = new StringBuilder();
        descriptionXhtml.append("<h1>Switch Component Demo</h1>");
        descriptionXhtml
                .append("<p>Switch is a decorated checkbox inspired by the iPhone.</p>");
        descriptionXhtml
                .append("<p>Download and rate this component at <a href=\"http://vaadin.com/directory#addon/9\">Vaadin Directory</a>.</p>");
        descriptionXhtml.append("<p>The value can be changed by:</p>");
        descriptionXhtml.append("<ul>");
        descriptionXhtml.append("<li>Clicking the Switch</li>");
        descriptionXhtml.append("<li>Dragging the Switch with mouse</li>");
        descriptionXhtml
                .append("<li>Focusing the Switch with <i>tab</i> key and pressing <i>space</i></li>");
        descriptionXhtml
                .append("<li>Dragging or tapping the Switch with your finger (Mobile Safari)</li>");
        descriptionXhtml.append("</ul>");

        Label description = new Label(descriptionXhtml.toString(),
                Label.CONTENT_XHTML);
        mainPanel.addComponent(description);
    }

    private void initDemoPanel() {
        Panel demoPanel = new Panel("Demonstration");
        mainPanel.addComponent(demoPanel);

        checkBox = new CheckBox("Animated?", true);
        checkBox.addListener(this);
        checkBox.setImmediate(true);
        checkBox.setHeight("35px"); // add some spacing
        demoPanel.addComponent(checkBox);

        HorizontalLayout switchesLayout = new HorizontalLayout();
        switchesLayout.setSpacing(true);
        demoPanel.addComponent(switchesLayout);

        Switch plainSwitch = createSwitch("Switch 1", true);
        switchesLayout.addComponent(plainSwitch);

        Switch plainSwitch2 = createSwitch("Switch 2", false);
        switchesLayout.addComponent(plainSwitch2);

        Switch disabledSwitch = createSwitch("Disabled", true);
        disabledSwitch.setEnabled(false);
        switchesLayout.addComponent(disabledSwitch);

        Switch readOnlySwitch = createSwitch("Read-only", false);
        readOnlySwitch.setReadOnly(true);
        switchesLayout.addComponent(readOnlySwitch);

        Switch validatorSwitch = createSwitch("Validator", true);
        validatorSwitch
                .addValidator(new AbstractValidator("Only ON is valid!") {
                    public boolean isValid(Object value) {
                        return (Boolean) value;
                    }
                });
        switchesLayout.addComponent(validatorSwitch);
    }

    private Switch createSwitch(String caption, boolean initialState) {
        Switch switchComponent = new Switch(caption, initialState);
        switchComponent.addListener(this);
        switchComponent.setImmediate(true);
        allSwitches.add(switchComponent);
        return switchComponent;
    }

    public void valueChange(ValueChangeEvent event) {
        if (event.getProperty() == checkBox) {
            for (Switch s : allSwitches) {
                s.setAnimationEnabled(checkBox.booleanValue());
            }
        } else if (event.getProperty() instanceof Switch) {
            mainWindow.showNotification(((Switch) event.getProperty())
                    .getCaption()
                    + ": " + event.getProperty().getValue());
        }
    }

}
