package pro.patrykkrawczyk.zoocrudapi.ui.enclosure;

import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.repository.EnclosureRepository;
import pro.patrykkrawczyk.zoocrudapi.repository.SpeciesRepository;

import java.util.List;

@SpringComponent
@UIScope
public class EnclosureLayout extends HorizontalLayout implements EnclosureEditorHandler, Button.ClickListener, HasValue.ValueChangeListener<Enclosure> {

    private final Button addButton = new Button("New", VaadinIcons.PLUS);
    private final Grid<Enclosure> grid = new Grid<>();
    private final EnclosureEditor enclosureEditor;
    private final EnclosureRepository enclosureRepository;
    private final SpeciesRepository speciesRepository;

    @Autowired
    public EnclosureLayout(EnclosureEditor enclosureEditor, EnclosureRepository enclosureRepository, SpeciesRepository speciesRepository) {
        this.enclosureEditor = enclosureEditor;
        this.enclosureRepository = enclosureRepository;
        this.speciesRepository = speciesRepository;

        addComponents(grid, addButton, enclosureEditor);

        setSpacing(true);
        setMargin(true);

        grid.setHeight(300, Unit.PIXELS);
        grid.asSingleSelect().addValueChangeListener(this);
        grid.addColumn(Enclosure::getId).setCaption("id");
        grid.addColumn(Enclosure::getName).setCaption("Name");
        grid.addColumn(e -> e.getSpecies().getName()).setCaption("Species");

        addButton.addClickListener(this);

        enclosureEditor.setEditorHandler(this);

        loadData();
    }

    void loadData() {
        List<Enclosure> data = enclosureRepository.findAll();
        data.forEach(e -> e.setAnimals(null));
        grid.setItems(data);
    }

    /**
     * Add Button click handler
     */
    @Override
    public void buttonClick(Button.ClickEvent event) {
        enclosureEditor.edit(new Enclosure());
    }

    /**
     * Grid selection handler
     */
    @Override
    public void valueChange(HasValue.ValueChangeEvent<Enclosure> event) {
        enclosureEditor.edit(event.getValue());
    }

    /**
     * Editor save handler
     */
    @Override
    public void onSave(Enclosure enclosure) {
        enclosureRepository.save(enclosure);

        enclosureEditor.setVisible(false);
        loadData();
    }

    /**
     * Editor save handler
     */
    @Override
    public void onDelete(Enclosure enclosure) {
        enclosureRepository.delete(enclosure);

        enclosureEditor.setVisible(false);
        loadData();
    }

    @Override
    public List<Species> loadSpeciesData() {
        return speciesRepository.findAll();
    }
}
