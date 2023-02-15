package authors.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryUpdate {

    private UUID id;

    @NotBlank(message = "CountryName is mandatory")
    private String countryName;
}
