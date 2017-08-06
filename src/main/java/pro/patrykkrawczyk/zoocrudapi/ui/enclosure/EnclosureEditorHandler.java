package pro.patrykkrawczyk.zoocrudapi.ui.enclosure;

import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;

import java.util.List;

public interface EnclosureEditorHandler {

    void onSave(Enclosure enclosure);

    void onDelete(Enclosure enclosure);

    List<Species> loadSpeciesData();
}
