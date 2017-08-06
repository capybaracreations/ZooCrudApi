package pro.patrykkrawczyk.zoocrudapi.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import pro.patrykkrawczyk.zoocrudapi.ui.species.SpeciesLayout;

@SpringUI
public class VaadinUI extends UI {

    private final SpeciesLayout speciesLayout;
    private final VerticalLayout mainLayout;
    private final TabSheet tabSheet;

    @Autowired
    public VaadinUI(SpeciesLayout speciesLayout) {
        this.speciesLayout = speciesLayout;

        this.tabSheet = new TabSheet();
        this.mainLayout = new VerticalLayout();
    }

    @Override
    protected void init(VaadinRequest request) {
        tabSheet.setSizeFull();
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

        mainLayout.addComponents(tabSheet);
        setContent(mainLayout);

        tabSheet.addTab(speciesLayout, "Species");
    }
}
