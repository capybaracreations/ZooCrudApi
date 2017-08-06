package pro.patrykkrawczyk.zoocrudapi.ui.species;

import pro.patrykkrawczyk.zoocrudapi.domain.Species;

public interface SpeciesEditorHandler {

    void onSave(Species species);

    void onDelete(Species species);
}
