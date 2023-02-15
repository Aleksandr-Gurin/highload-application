package authors.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryRequest {

    private UUID id;

    @NotBlank(message = "CountryName is mandatory")
    private String countryName;
}
