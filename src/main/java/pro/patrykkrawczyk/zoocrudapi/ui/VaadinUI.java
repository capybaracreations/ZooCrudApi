package pro.patrykkrawczyk.zoocrudapi.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import pro.patrykkrawczyk.zoocrudapi.ui.animal.AnimalLayout;
import pro.patrykkrawczyk.zoocrudapi.ui.enclosure.EnclosureLayout;
import pro.patrykkrawczyk.zoocrudapi.ui.species.SpeciesLayout;

@SpringUI
public class VaadinUI extends UI {

    private final SpeciesLayout speciesLayout;
    private final AnimalLayout animalLayout;
    private final EnclosureLayout enclosureLayout;
    private final VerticalLayout mainLayout;
    private final TabSheet tabSheet;
    private final Label author = new Label("patryk.krawczyk93@gmail.com");

    @Autowired
    public VaadinUI(SpeciesLayout speciesLayout, AnimalLayout animalLayout, EnclosureLayout enclosureLayout) {
        this.speciesLayout = speciesLayout;
        this.animalLayout = animalLayout;
        this.enclosureLayout = enclosureLayout;

        this.tabSheet = new TabSheet();
        this.mainLayout = new VerticalLayout();
    }

    @Override
    protected void init(VaadinRequest request) {
        tabSheet.setSizeFull();
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

        mainLayout.addComponents(tabSheet, author);
        mainLayout.setComponentAlignment(author, Alignment.BOTTOM_RIGHT);
        setContent(mainLayout);

        tabSheet.addTab(speciesLayout, "Species");
        tabSheet.addTab(animalLayout, "Animals");
        tabSheet.addTab(enclosureLayout, "Enclosure");
    }
}
