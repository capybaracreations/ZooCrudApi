package pro.patrykkrawczyk.zoocrudapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesDTO {

    private Long id;
    @NotNull
    private String name;
}
