package pro.patrykkrawczyk.zoocrudapi.ui.animal;

import pro.patrykkrawczyk.zoocrudapi.domain.Animal;
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;

import java.util.List;

public interface AnimalEditorHandler {

    void onSave(Animal animal);

    void onDelete(Animal animal);

    List<Species> loadSpeciesData();

    List<Enclosure> loadEnclosureData();
}
