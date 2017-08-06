package pro.patrykkrawczyk.zoocrudapi.ui.species;

import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.repository.SpeciesRepository;

import java.util.List;

@SpringComponent
@UIScope
public class SpeciesLayout extends HorizontalLayout implements SpeciesEditorHandler, Button.ClickListener, HasValue.ValueChangeListener<Species> {

    private final Button addButton;
    private final Grid<Species> grid;
    private final SpeciesEditor speciesEditor;
    private final SpeciesRepository speciesRepository;

    @Autowired
    public SpeciesLayout(SpeciesEditor speciesEditor, SpeciesRepository speciesRepository) {
        this.speciesEditor = speciesEditor;
        this.speciesRepository = speciesRepository;
        this.addButton = new Button("New", VaadinIcons.PLUS);
        this.grid = new Grid<>(Species.class);

        addComponents(grid, addButton, speciesEditor);

        setSpacing(true);
        setMargin(true);

        grid.setHeight(300, Unit.PIXELS);
        grid.setColumns("id", "name");
        grid.asSingleSelect().addValueChangeListener(this);

        addButton.addClickListener(this);

        speciesEditor.setEditorHandler(this);

        loadData();
    }

    void loadData() {
        List<Species> data = speciesRepository.findAll();
        grid.setItems(data);
    }

    /**
     * Add Button click handler
     */
    @Override
    public void buttonClick(Button.ClickEvent event) {
        speciesEditor.edit(new Species());
    }

    /**
     * Grid selection handler
     */
    @Override
    public void valueChange(HasValue.ValueChangeEvent<Species> event) {
        speciesEditor.edit(event.getValue());
    }

    /**
     * Editor save handler
     */
    @Override
    public void onSave(Species species) {
        speciesRepository.save(species);

        speciesEditor.setVisible(false);
        loadData();
    }

    /**
     * Editor save handler
     */
    @Override
    public void onDelete(Species species) {
        speciesRepository.delete(species);

        speciesEditor.setVisible(false);
        loadData();
    }
}
