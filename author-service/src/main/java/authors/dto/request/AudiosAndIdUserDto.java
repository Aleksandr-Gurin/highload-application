package authors.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudiosAndIdUserDto {

    @NotNull(message = "userId is mandatory")
    private UUID userId;

    @NotNull(message = "audiosId is mandatory")
    private List<UUID> audiosId;
}
