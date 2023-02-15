package authors.mapper;

import authors.dto.request.CityRequest;
import authors.dto.response.CityResponse;
import authors.dto.update.CityUpdate;
import authors.model.City;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CityMapper {

    private final ModelMapper mapper;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(City.class, CityResponse.class)
                .addMappings(m -> m.skip(CityResponse::setCountry)).setPostConverter(toCityResponseConverter());
    }

    private Converter<City, CityResponse> toCityResponseConverter() {
        return context -> {
            City source = context.getSource();
            CityResponse destination = context.getDestination();
            destination.setCountry(source.getCountry().getId());
            return context.getDestination();
        };
    }

    public CityResponse cityToCityResponse(City city) {
        return mapper.map(city, CityResponse.class);
    }

    public City cityRequestToCity(CityRequest request) {
        return mapper.map(request, City.class);
    }

    public City cityUpdateToCity(CityUpdate update) {
        return mapper.map(update, City.class);
    }

    public List<CityResponse> cityToCityResponseList(List<City> city) {
        return city
                .stream()
                .map(this::cityToCityResponse)
                .collect(Collectors.toList());
    }

    public Page<CityResponse> cityToCityResponsePage(Page<City> cityPage) {
        return cityPage
                .map(this::cityToCityResponse);
    }
}
