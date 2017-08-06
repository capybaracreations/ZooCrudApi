package pro.patrykkrawczyk.zoocrudapi.ui.animal;

import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import pro.patrykkrawczyk.zoocrudapi.domain.Animal;
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.repository.AnimalRepository;
import pro.patrykkrawczyk.zoocrudapi.repository.EnclosureRepository;
import pro.patrykkrawczyk.zoocrudapi.repository.SpeciesRepository;

import java.util.List;

@SpringComponent
@UIScope
public class AnimalLayout extends HorizontalLayout implements AnimalEditorHandler, Button.ClickListener, HasValue.ValueChangeListener<Animal> {

    private final Button addButton = new Button("New", VaadinIcons.PLUS);
    private final Grid<Animal> grid = new Grid<>();
    private final AnimalEditor animalEditor;
    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;
    private final SpeciesRepository speciesRepository;

    @Autowired
    public AnimalLayout(AnimalEditor animalEditor, AnimalRepository animalRepository,
                        EnclosureRepository enclosureRepository, SpeciesRepository speciesRepository) {

        this.animalEditor = animalEditor;
        this.animalRepository = animalRepository;
        this.enclosureRepository = enclosureRepository;
        this.speciesRepository = speciesRepository;

        addComponents(grid, addButton, animalEditor);

        setSpacing(true);
        setMargin(true);

        grid.setHeight(300, Unit.PIXELS);
        grid.addColumn(Animal::getId).setCaption("id");
        grid.addColumn(Animal::getName).setCaption("Name");
        grid.addColumn(a -> a.getEnclosure().getName()).setCaption("Enclosure");
        grid.addColumn(a -> a.getSpecies().getName()).setCaption("Species");
        grid.asSingleSelect().addValueChangeListener(this);

        addButton.addClickListener(this);

        animalEditor.setEditorHandler(this);

        loadData();
    }

    void loadData() {
        List<Animal> data = animalRepository.findAll();
        data.forEach(a -> a.getEnclosure().setAnimals(null));
        grid.setItems(data);
    }

    /**
     * Add Button click handler
     */
    @Override
    public void buttonClick(Button.ClickEvent event) {
        animalEditor.edit(new Animal());
    }

    /**
     * Grid selection handler
     */
    @Override
    public void valueChange(HasValue.ValueChangeEvent<Animal> event) {
        animalEditor.edit(event.getValue());
    }

    /**
     * Editor save handler
     */
    @Override
    public void onSave(Animal animal) {
        animalRepository.save(animal);

        animalEditor.setVisible(false);
        loadData();
    }

    /**
     * Editor save handler
     */
    @Override
    public void onDelete(Animal animal) {
        animalRepository.delete(animal);

        animalEditor.setVisible(false);
        loadData();
    }

    @Override
    public List<Species> loadSpeciesData() {
        return speciesRepository.findAll();
    }

    @Override
    public List<Enclosure> loadEnclosureData() {
        List<Enclosure> data = enclosureRepository.findAll();
        data.forEach(e -> e.setAnimals(null));
        return data;
    }
}
