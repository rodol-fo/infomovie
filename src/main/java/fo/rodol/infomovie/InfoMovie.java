package fo.rodol.infomovie;

import fo.rodol.infomovie.domain.Movie;
import fo.rodol.infomovie.service.MovieService;
import fo.rodol.infomovie.service.MyApiFilmsService;
import fo.rodol.infomovie.service.OmdbService;
import org.apache.commons.cli.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class InfoMovie {

    private static final String MY_API_FILMS_ROOT = "http://api.myapifilms.com/";

    private static final String MY_API_FILMS_TOKEN = "fc869ce9-fdf2-4c0f-9270-f795d526092f";

    private static final String OMDB_ROOT = "http://www.omdbapi.com/";

    public static void main(String[] args) {

        CommandLine line = null;
        try {

            line = getCommandLineArguments(args);

        } catch (ParseException e) {

            e.printStackTrace();
            System.exit(1);
        }

        if (line.hasOption("api")) {

            MovieService movieService = getMovieService(line.getOptionValue("api"));
            if (movieService == null) {

                printUsageHelp();
                System.exit(1);
            }

            if (line.hasOption("title")) {

                List<Movie> movies = movieService.findMovies(line.getOptionValue("title"));
                printResults(movies);

            } else {

                printUsageHelp();
                System.exit(1);
            }
        }
        else {
            printUsageHelp();
            System.exit(1);
        }
    }

    private static void printUsageHelp() {

        Options cliOptions = new Options();
        cliOptions.addOption("api", true, "Valid values: MyApiFilms, OMDB");
        cliOptions.addOption("title", true, "The title of the movie");

        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("java -jar <InfoMovie.jar> -api <api> -title <title>", cliOptions);
    }

    private static MovieService getMovieService(String api) {

        if (api.equalsIgnoreCase("MyApiFilms")) {

            return new MyApiFilmsService(new RestTemplate(), MY_API_FILMS_ROOT, MY_API_FILMS_TOKEN);
        }
        if (api.equalsIgnoreCase("OMDB")) {

            return new OmdbService(new RestTemplate(), OMDB_ROOT);
        }
        return null;
    }

    private static CommandLine getCommandLineArguments(String[] args) throws ParseException {

        Options cliOptions = new Options();
        cliOptions.addOption("api", true, "Valid values: MyApiFilms, OMDB");
        cliOptions.addOption("title", true, "The title of the movie");

        CommandLineParser cliParser = new BasicParser();

        return cliParser.parse(cliOptions, args);
    }

    private static void printResults(List<Movie> movies) {

        movies.stream()
                .forEach(System.out::println);
    }
}
