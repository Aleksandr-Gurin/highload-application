package authors.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "concerts")
@Setter
@Getter
@NoArgsConstructor
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "title")
    private String name;

    @NotBlank(message = "Performer is mandatory")
    @Column(name = "performer")
    private String performer;

    @NotNull(message = "Performer is mandatory")
    @Column(name = "concert_date")
    private LocalDateTime concertDate;

    @NotNull(message = "City is mandatory")
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Override
    public String toString() {
        return "Concert{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", performer='" + performer + '\'' +
                ", concertDate=" + concertDate +
                ", city=" + city.getId() +
                '}';
    }
}
