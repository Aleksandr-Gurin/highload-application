package authors.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityRequest {

    private UUID id;

    @NotBlank(message = "CityName is mandatory")
    private String cityName;

    @NotNull(message = "Country is mandatory")
    private UUID countryId;
}
