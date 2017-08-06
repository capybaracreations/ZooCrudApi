package pro.patrykkrawczyk.zoocrudapi.ui.species;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.repository.SpeciesRepository;

@SpringComponent
@UIScope
class SpeciesEditor extends VerticalLayout {

    private final SpeciesRepository repository;

    private final Button saveButton = new Button("Save", VaadinIcons.CHECK);
    private final Button cancelButton = new Button("Cancel", VaadinIcons.REFRESH);
    private final Button deleteButton = new Button("Delete", VaadinIcons.TRASH);

    private final TextField name = new TextField("Name");

    private final HorizontalLayout actionsLayout = new HorizontalLayout();
    private final Binder<Species> binder = new Binder<>(Species.class);

    /**
     * The currently edited species
     */
    private Species species;
    private SpeciesEditorHandler speciesEditorHandler;

    @Autowired
    public SpeciesEditor(SpeciesRepository repository) {
        this.repository = repository;

        binder.bindInstanceFields(this);

        actionsLayout.addComponents(saveButton, deleteButton, cancelButton);

        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        saveButton.addClickListener(e -> speciesEditorHandler.onSave(species));

        deleteButton.addClickListener(e -> speciesEditorHandler.onDelete(species));
        cancelButton.addClickListener(e -> editSpecies(species));

        setSpacing(true);
        setVisible(false);
        addComponents(name, actionsLayout);
    }

    public final void editSpecies(Species species) {
        if (species == null) {
            setVisible(false);
            return;
        }

        final boolean persisted = species.getId() != null;

        if (persisted) {
            // Find fresh entity for editing
            this.species = repository.findOne(species.getId());
        } else {
            this.species = species;
        }

        deleteButton.setVisible(persisted);
        cancelButton.setVisible(persisted);

        // Bind species properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(this.species);

        setVisible(true);

        // A hack to ensure the whole form is visible
        saveButton.focus();
        // Select all text in name field automatically
        name.selectAll();
    }

    public void setEditorHandler(SpeciesEditorHandler speciesEditorHandler) {
        this.speciesEditorHandler = speciesEditorHandler;
    }


}
