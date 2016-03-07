package fo.rodol.infomovie.domain;

public class Movie {

    private String title;

    private String year;

    public Movie(String title, String year) {

        this.title = title;
        this.year = year;
    }

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

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
