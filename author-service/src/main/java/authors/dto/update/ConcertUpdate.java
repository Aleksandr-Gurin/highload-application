package authors.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcertUpdate {

    private UUID id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Performer is mandatory")
    private String performer;

    @NotNull(message = "Performer is mandatory")
    private LocalDateTime concertDate;

    @NotNull(message = "City is mandatory")
    private UUID cityId;
}
