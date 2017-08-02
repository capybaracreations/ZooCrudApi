package pro.patrykkrawczyk.zoocrudapi;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class VaadinUI extends UI {

    private final Label label;

    @Autowired
    public VaadinUI() {
        this.label = new Label("ZooCrudApi");
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout mainLayout = new VerticalLayout(label);
        setContent(mainLayout);
    }
}
