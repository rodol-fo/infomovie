package fo.rodol.infomovie.service;

import fo.rodol.infomovie.domain.Movie;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class MyApiFilmsService implements MovieService {

    RestTemplate restTemplate;

    UriComponents myApiFilmsUriComponents;

    public MyApiFilmsService(RestTemplate restTemplate, String myApiFilmsRoot, String userToken) {

        this.restTemplate = restTemplate;
        myApiFilmsUriComponents = UriComponentsBuilder.fromHttpUrl(myApiFilmsRoot)
                .pathSegment("imdb", "idIMDB")
                .queryParam("token", userToken)
                .queryParam("title", "{title}")
                .queryParam("limit", "10")
                .queryParam("filter", "3")
                .scheme("http")
                .build();
    }

    @Override
    public List<Movie> findMovies(String query) {

        URI uri = myApiFilmsUriComponents.expand(query).toUri();
        MyApiFilmsSearchResponse response = restTemplate.getForObject(uri, MyApiFilmsSearchResponse.class);

        return response.getData().getMovies().stream()
                .map(m -> new Movie(m.getTitle(), m.getYear()))
                .collect(Collectors.toList());
    }

    private static class MyApiFilmsSearchResponse {

        MyApifilmsResponseData data;

        public MyApifilmsResponseData getData() {
            return data;
        }

        public void setData(MyApifilmsResponseData data) {
            this.data = data;
        }
    }

    private static class MyApifilmsResponseData {

        List<MyApiFilmsResponseMovie> movies;

        public List<MyApiFilmsResponseMovie> getMovies() {
            return movies;
        }

        public void setMovies(List<MyApiFilmsResponseMovie> movies) {
            this.movies = movies;
        }
    }

    private static class MyApiFilmsResponseMovie {

        String title;

        String year;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }
}
