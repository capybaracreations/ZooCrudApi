package pro.patrykkrawczyk.zoocrudapi.ui.animal;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import pro.patrykkrawczyk.zoocrudapi.domain.Animal;
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.repository.AnimalRepository;

@SpringComponent
@UIScope
class AnimalEditor extends VerticalLayout {

    private final AnimalRepository repository;

    private final Button saveButton = new Button("Save", VaadinIcons.CHECK);
    private final Button cancelButton = new Button("Cancel", VaadinIcons.REFRESH);
    private final Button deleteButton = new Button("Delete", VaadinIcons.TRASH);

    private final TextField name = new TextField("Name");

    private final HorizontalLayout actionsLayout = new HorizontalLayout();
    private final Binder<Animal> binder = new Binder<>(Animal.class);
    private final ComboBox<Species> species = new ComboBox<>("Species");
    private final ComboBox<Enclosure> enclosure = new ComboBox<>("Enclosure");
    /**
     * The currently edited animal
     */
    private Animal animal;
    private AnimalEditorHandler animalEditorHandler;

    @Autowired
    public AnimalEditor(AnimalRepository repository) {
        this.repository = repository;

        binder.bindInstanceFields(this);

        actionsLayout.addComponents(saveButton, deleteButton, cancelButton);

        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        saveButton.addClickListener(e -> animalEditorHandler.onSave(animal));

        deleteButton.addClickListener(e -> animalEditorHandler.onDelete(animal));
        cancelButton.addClickListener(e -> edit(animal));

        species.setItemCaptionGenerator(Species::getName);
        enclosure.setItemCaptionGenerator(Enclosure::getName);

        setSpacing(true);
        setVisible(false);
        addComponents(name, species, enclosure, actionsLayout);
    }

    public final void edit(Animal animal) {
        species.setItems(animalEditorHandler.loadSpeciesData());
        enclosure.setItems(animalEditorHandler.loadEnclosureData());

        if (animal == null) {
            setVisible(false);
            return;
        }

        final boolean persisted = animal.getId() != null;

        if (persisted) {
            // Find fresh entity for editing
            this.animal = repository.findOne(animal.getId());
        } else {
            this.animal = animal;
        }

        deleteButton.setVisible(persisted);
        cancelButton.setVisible(persisted);

        // Bind animal properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        if (this.animal.getEnclosure() != null) {
            this.animal.getEnclosure().setAnimals(null);
        }

        binder.setBean(this.animal);

        setVisible(true);

        // A hack to ensure the whole form is visible
        saveButton.focus();
        // Select all text in name field automatically
        name.selectAll();
    }

    public void setEditorHandler(AnimalEditorHandler animalEditorHandler) {
        this.animalEditorHandler = animalEditorHandler;
    }


}
