package fo.rodol.infomovie

import fo.rodol.infomovie.service.MyApiFilmsService
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.client.MockRestServiceServer.createServer
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

class TestMyApiFilmsService extends Specification {

    def omdbApiRoot = 'http://myapifilmsroot'

    def resultsQuery= 'IndianaJones'

    def noResultsQuery= 'noresults'

    def userToken = 'usertoken'

    def restTemplate = new RestTemplate()

    def myApiFilmsService = new MyApiFilmsService(restTemplate, omdbApiRoot, userToken)

    void 'should get an empty list of movies from My API Films when the API returns no content'() {

        given:
        initMockMyApiFilmsNoResults()

        when:
        def movies = myApiFilmsService.findMovies(noResultsQuery)

        then:
        movies.isEmpty()
    }

    void 'should get a list of movies from My API Films when the API returns content'() {

        given:
        initMockMyApiFilmsWithResults()

        when:
        def movies = myApiFilmsService.findMovies(resultsQuery)

        then:
        movies.size() == 4
    }

    def initMockMyApiFilmsNoResults() {

        createServer(restTemplate)
                .expect(requestTo("$omdbApiRoot/imdb/idIMDB?token=$userToken&title=$noResultsQuery&limit=10&filter=3"))
                .andRespond(withSuccess("""
{
   "about" : {
      "version" : "2.16.0"
   },
   "data" : {
      "movies" : []
   }
}
""", APPLICATION_JSON))
    }

    def initMockMyApiFilmsWithResults() {

        createServer(restTemplate)
                .expect(requestTo("$omdbApiRoot/imdb/idIMDB?token=$userToken&title=$resultsQuery&limit=10&filter=3"))
                .andRespond(withSuccess("""
{
   "about" : {
      "version" : "2.16.0"
   },
   "data" : {
      "movies" : [
         {
            "plot" : "During the Cold War, Soviet agents watch Professor Henry Jones when a young man brings him a coded message from an aged, demented colleague, Harold Oxley. Led by the brilliant Irina Spalko, the Soviets tail Jones and the young man, Mutt, to Peru. With Oxley's code, they find a legendary skull made of a single piece of quartz. If Jones can deliver the skull to its rightful place, all may be well; but if Irina takes it to its origin, she'll gain powers that could endanger the West. Aging professor and young buck join forces with a woman from Jones' past to face the dangers of the jungle, Russia, and the supernatural.",
            "simplePlot" : "Famed archaeologist/adventurer Dr. Henry \\"Indiana\\" Jones is called back into action when he becomes entangled in a Soviet plot to uncover the secret behind mysterious artifacts known as the Crystal Skulls.",
            "releaseDate" : "20080522",
            "filmingLocations" : [
               "Bridgeport",
               "Connecticut",
               "USA"
            ],
            "type" : "Movie",
            "runtime" : "122 min",
            "urlPoster" : "http://ia.media-imdb.com/images/M/MV5BMTIxNDUxNzcyMl5BMl5BanBnXkFtZTcwNTgwOTI3MQ@@._V1_UX182_CR0,0,182,268_AL_.jpg",
            "rated" : "PG-13",
            "metascore" : "65",
            "title" : "Indiana Jones and the Kingdom of the Crystal Skull",
            "directors" : [
               {
                  "id" : "nm0000229",
                  "name" : "Steven Spielberg"
               }
            ],
            "idIMDB" : "tt0367882",
            "originalTitle" : "",
            "writers" : [
               {
                  "name" : "David Koepp",
                  "id" : "nm0462895"
               },
               {
                  "name" : "George Lucas",
                  "id" : "nm0000184"
               }
            ],
            "countries" : [
               "USA"
            ],
            "rating" : "6.2",
            "genres" : [
               "Action",
               "Adventure",
               "Fantasy"
            ],
            "year" : "2008",
            "languages" : [
               "English",
               "German",
               "Russian"
            ],
            "votes" : "322,508",
            "urlIMDB" : "http://www.imdb.com/title/tt0367882"
         },
         {
            "votes" : "8",
            "languages" : [
               "Thai"
            ],
            "urlIMDB" : "http://www.imdb.com/title/tt0442272",
            "rating" : "2.8",
            "year" : "2003",
            "genres" : [
               "Adventure"
            ],
            "countries" : [
               "Thailand"
            ],
            "writers" : [
               {
                  "name" : "Sarawooth Rakpradith",
                  "id" : "nm1834229"
               }
            ],
            "directors" : [
               {
                  "name" : "Sarawooth Rakpradith",
                  "id" : "nm1834229"
               }
            ],
            "idIMDB" : "tt0442272",
            "originalTitle" : "",
            "rated" : "",
            "metascore" : "",
            "title" : "Indiana Joai: Elephant Cemetery",
            "urlPoster" : "",
            "runtime" : "87 min",
            "type" : "Movie",
            "simplePlot" : "Very loosely based on a true story of Thai adventurer/photographer Joai Bangchark. After fleeing animal poachers, Joai receives a necklace made of elephant tusk from a dying man in the ... ",
            "plot" : "Very loosely based on a true story of Thai adventurer/photographer Joai Bangchark. After fleeing animal poachers, Joai receives a necklace made of elephant tusk from a dying man in the forest. The necklace can apparently control the actions of elephants. Using the necklace, he must thwart a band of evil animal poachers and save a local village, with the help of the ghost of a baby elephant.",
            "filmingLocations" : [],
            "releaseDate" : "20031217"
         },
         {
            "plot" : "Indiana Jones, famed adventurer and archaeologist acquires a diary that holds clues and a map with no names to find the mysterious Holy Grail- which was sent from his father, Dr. Henry Jones, in Italy. Upon hearing from a private collector, Walter Donavan, that the mission for the Holy Grail went astray with the disappearance of his father, Indiana Jones and museum curator Marcus Brody venture to Italy in search of Indy's father. However, upon retrieving Dr. Henry Jones in Nazi territory, the rescue mission turns into a race to find the Holy Grail before the Nazis do- who plan to use it for complete world domination for their super-race. With the diary as a vital key and the map with no names as a guide, Indiana Jones once again finds himself in another death defying adventure of pure excitement.",
            "simplePlot" : "When Dr. Henry Jones Sr. suddenly goes missing while pursuing the Holy Grail, eminent archaeologist Indiana Jones must follow in his father's footsteps and stop the Nazis.",
            "releaseDate" : "19890524",
            "filmingLocations" : [
               "Palazzi Barbaro",
               "Grand Canal",
               "Venice",
               "Veneto",
               "Italy"
            ],
            "type" : "Movie",
            "runtime" : "127 min",
            "urlPoster" : "http://ia.media-imdb.com/images/M/MV5BMTQxMTUyODg2OF5BMl5BanBnXkFtZTcwNDM2MjAxNA@@._V1_UX182_CR0,0,182,268_AL_.jpg",
            "rated" : "PG-13",
            "title" : "Indiana Jones and the Last Crusade",
            "metascore" : "65",
            "directors" : [
               {
                  "id" : "nm0000229",
                  "name" : "Steven Spielberg"
               }
            ],
            "idIMDB" : "tt0097576",
            "originalTitle" : "",
            "writers" : [
               {
                  "id" : "nm0090151",
                  "name" : "Jeffrey Boam"
               },
               {
                  "name" : "George Lucas",
                  "id" : "nm0000184"
               }
            ],
            "countries" : [
               "USA"
            ],
            "rating" : "8.3",
            "genres" : [
               "Action",
               "Adventure",
               "Fantasy"
            ],
            "year" : "1989",
            "languages" : [
               "English",
               "German",
               "Greek"
            ],
            "votes" : "493,848",
            "urlIMDB" : "http://www.imdb.com/title/tt0097576"
         },
         {
            "directors" : [
               {
                  "id" : "nm0000229",
                  "name" : "Steven Spielberg"
               }
            ],
            "idIMDB" : "tt0087469",
            "originalTitle" : "",
            "title" : "Indiana Jones and the Temple of Doom",
            "rated" : "PG",
            "metascore" : "57",
            "urlPoster" : "http://ia.media-imdb.com/images/M/MV5BMTMyNzI4OTA5OV5BMl5BanBnXkFtZTcwMDQ2MjAxNA@@._V1_UX182_CR0,0,182,268_AL_.jpg",
            "runtime" : "118 min",
            "type" : "Movie",
            "simplePlot" : "After arriving in India, Indiana Jones is asked by a desperate village to find a mystical stone. He agrees, and stumbles upon a secret cult plotting a terrible plan in the catacombs of an ancient palace.",
            "plot" : "Set in 1935, a professor, archaeologist, and legendary hero by the name of Indiana Jones is back in action in his newest adventure. But this time he teams up with a night club singer named Wilhelmina \\"Willie\\" Scott and a twelve-year-old boy named Short Round. They end up in an Indian small distressed village, where the people believe that evil spirits have taken all their children away after a sacred precious stone was stolen! They also discovered the great mysterious terror surrounding a booby-trapped temple known as the Temple of Doom! Thuggee is beginning to attempt to rise once more, believing that with the power of all five Sankara stones they can rule the world! Now, it's all up to Indiana to put an end to the Thuggee campaign, rescue the lost children, win the girl and conquer the Temple of Doom.",
            "releaseDate" : "19840523",
            "filmingLocations" : [
               "University of the Pacific",
               "Stockton",
               "California",
               "USA"
            ],
            "votes" : "315,754",
            "languages" : [
               "English",
               "Sinhalese"
            ],
            "urlIMDB" : "http://www.imdb.com/title/tt0087469",
            "rating" : "7.6",
            "genres" : [
               "Action",
               "Adventure"
            ],
            "year" : "1984",
            "countries" : [
               "USA"
            ],
            "writers" : [
               {
                  "id" : "nm0404754",
                  "name" : "Willard Huyck"
               },
               {
                  "name" : "Gloria Katz",
                  "id" : "nm0441718"
               }
            ]
         }
      ]
   }
}
""", APPLICATION_JSON))
    }
}
