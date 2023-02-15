package authors.mapper;

import authors.dto.request.CountryRequest;
import authors.dto.response.CountryResponse;
import authors.dto.update.CountryUpdate;
import authors.model.Country;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CountryMapper {

    private final ModelMapper mapper;

    public CountryResponse countryToCountryResponse(Country country) {
        return mapper.map(country, CountryResponse.class);
    }

    public Country countryRequestToCountry(CountryRequest request) {
        return mapper.map(request, Country.class);
    }

    public Country countryUpdateToCountry(CountryUpdate update) {
        return mapper.map(update, Country.class);
    }

    public List<CountryResponse> concertToCountryResponseList(List<Country> country) {
        return country
                .stream()
                .map(this::countryToCountryResponse)
                .collect(Collectors.toList());
    }

    public Page<CountryResponse> concertToCountryResponsePage(Page<Country> countryPage) {
        return countryPage
                .map(this::countryToCountryResponse);
    }
}
