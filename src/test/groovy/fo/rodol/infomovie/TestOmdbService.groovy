package fo.rodol.infomovie

import fo.rodol.infomovie.service.OmdbService
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.client.MockRestServiceServer.createServer
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

class TestOmdbService extends Specification {

    def omdbApiRoot = 'http://omdbRoot'

    def resultsQuery= 'IndianaJones'

    def noResultsQuery= 'noresults'

    def restTemplate = new RestTemplate()

    def omdbService = new OmdbService(restTemplate, omdbApiRoot)

    void 'should get an empty list of movies from omdb when the API returns no content'() {

        given:
        initMockOmdbNoResults()

        when:
        def movies = omdbService.findMovies(noResultsQuery)

        then:
        movies.isEmpty()
    }

    void 'should get a list of movies from omdb when the API returns content'() {

        given:
        initMockOmdbWithResults()

        when:
        def movies = omdbService.findMovies(resultsQuery)

        then:
        movies.size() == 4
    }

    def initMockOmdbNoResults() {

        createServer(restTemplate)
                .expect(requestTo("$omdbApiRoot?type=movie&s=$noResultsQuery"))
                .andRespond(withSuccess("""
{
   "Response" : "False",
   "Error" : "Movie not found!"
}
""", APPLICATION_JSON))
    }

    def initMockOmdbWithResults() {

        createServer(restTemplate)
                .expect(requestTo("$omdbApiRoot?type=movie&s=$resultsQuery"))
                .andRespond(withSuccess("""
{
   "totalResults" : "4",
   "Response" : "True",
   "Search" : [
      {
         "Title" : "Indiana Jones and the Last Crusade",
         "Poster" : "http://ia.media-imdb.com/images/M/MV5BMTQxMTUyODg2OF5BMl5BanBnXkFtZTcwNDM2MjAxNA@@._V1_SX300.jpg",
         "Year" : "1989",
         "Type" : "movie",
         "imdbID" : "tt0097576"
      },
      {
         "imdbID" : "tt0367882",
         "Year" : "2008",
         "Type" : "movie",
         "Poster" : "http://ia.media-imdb.com/images/M/MV5BMTIxNDUxNzcyMl5BMl5BanBnXkFtZTcwNTgwOTI3MQ@@._V1_SX300.jpg",
         "Title" : "Indiana Jones and the Kingdom of the Crystal Skull"
      },
      {
         "imdbID" : "tt0087469",
         "Type" : "movie",
         "Year" : "1984",
         "Poster" : "http://ia.media-imdb.com/images/M/MV5BMTMyNzI4OTA5OV5BMl5BanBnXkFtZTcwMDQ2MjAxNA@@._V1_SX300.jpg",
         "Title" : "Indiana Jones and the Temple of Doom"
      },
      {
         "Title" : "Indiana Jones and the Temple of the Forbidden Eye",
         "Poster" : "N/A",
         "Type" : "movie",
         "Year" : "1995",
         "imdbID" : "tt0764648"
      }
   ]
}
""", APPLICATION_JSON))
    }
}
