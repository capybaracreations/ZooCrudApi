package pro.patrykkrawczyk.zoocrudapi.ui.enclosure;

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
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.repository.EnclosureRepository;

@SpringComponent
@UIScope
class EnclosureEditor extends VerticalLayout {

    private final EnclosureRepository repository;

    private final Button saveButton = new Button("Save", VaadinIcons.CHECK);
    private final Button cancelButton = new Button("Cancel", VaadinIcons.REFRESH);
    private final Button deleteButton = new Button("Delete", VaadinIcons.TRASH);

    private final TextField name = new TextField("Name");
    private final ComboBox<Species> species = new ComboBox<>("Species");

    private final HorizontalLayout actionsLayout = new HorizontalLayout();
    private final Binder<Enclosure> binder = new Binder<>(Enclosure.class);

    /**
     * The currently edited enclosure
     */
    private Enclosure enclosure;
    private EnclosureEditorHandler enclosureEditorHandler;

    @Autowired
    public EnclosureEditor(EnclosureRepository repository) {
        this.repository = repository;

        binder.bindInstanceFields(this);

        actionsLayout.addComponents(saveButton, deleteButton, cancelButton);

        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        saveButton.addClickListener(e -> enclosureEditorHandler.onSave(enclosure));

        deleteButton.addClickListener(e -> enclosureEditorHandler.onDelete(enclosure));
        cancelButton.addClickListener(e -> edit(enclosure));

        species.setItemCaptionGenerator(Species::getName);

        setSpacing(true);
        setVisible(false);

        addComponents(name, species, actionsLayout);
    }

    public final void edit(Enclosure enclosure) {
        species.setItems(enclosureEditorHandler.loadSpeciesData());

        if (enclosure == null) {
            setVisible(false);
            return;
        }

        final boolean persisted = enclosure.getId() != null;

        if (persisted) {
            // Find fresh entity for editing
            this.enclosure = repository.findOne(enclosure.getId());
        } else {
            this.enclosure = enclosure;
        }

        deleteButton.setVisible(persisted);
        cancelButton.setVisible(persisted);

        // Bind enclosure properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(this.enclosure);

        setVisible(true);

        // A hack to ensure the whole form is visible
        saveButton.focus();
        // Select all text in name field automatically
        name.selectAll();
    }

    public void setEditorHandler(EnclosureEditorHandler enclosureEditorHandler) {
        this.enclosureEditorHandler = enclosureEditorHandler;
    }


}
