package fo.rodol.infomovie.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import fo.rodol.infomovie.domain.Movie;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OmdbService implements MovieService {

    RestTemplate restTemplate;

    UriComponents omdbUriComponents;

    public OmdbService(RestTemplate restTemplate, String omdbApiRoot) {

        this.restTemplate = restTemplate;

        this.omdbUriComponents = UriComponentsBuilder.fromHttpUrl(omdbApiRoot)
                .queryParam("type", "movie")
                .queryParam("s", "{s}")
                .scheme("http")
                .build();
    }

    @Override
    public List<Movie> findMovies(String query) {

        URI uri = omdbUriComponents.expand(query).toUri();
        OmdbSearchResponse response = restTemplate.getForObject(uri, OmdbSearchResponse.class);

        if (response.getResponse().equals("False")) {
            return new ArrayList<>();
        }
        if (response.getResponse().equals("True")) {
            return response.getSearch()
                    .stream()
                    .map(r -> new Movie(r.getTitle(), r.getYear()))
                    .collect(Collectors.toList());
        }

        return null;
    }

    private static class OmdbSearchResponse {

        @JsonProperty("Response")
        private String response;

        @JsonProperty("Error")
        private String error;

        @JsonProperty("Search")
        private List<OmdbResponseMovie> search;

        private String totalResults;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(String totalResults) {
            this.totalResults = totalResults;
        }

        public List<OmdbResponseMovie> getSearch() {
            return search;
        }

        public void setSearch(List<OmdbResponseMovie> search) {
            this.search = search;
        }
    }

    private static class OmdbResponseMovie {

        @JsonProperty("Title")
        private String Title;

        private String type;

        private String imdbID;

        @JsonProperty("Year")
        private String year;

        private String poster;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImdbID() {
            return imdbID;
        }

        public void setImdbID(String imdbID) {
            this.imdbID = imdbID;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

    }
}
