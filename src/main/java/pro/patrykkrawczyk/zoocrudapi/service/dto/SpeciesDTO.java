package pro.patrykkrawczyk.zoocrudapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesDTO implements Serializable {

    private Long id;
    @NotNull
    private String name;
}
