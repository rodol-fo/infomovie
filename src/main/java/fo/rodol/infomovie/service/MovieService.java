package fo.rodol.infomovie.service;

import fo.rodol.infomovie.domain.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> findMovies(String query);
}
