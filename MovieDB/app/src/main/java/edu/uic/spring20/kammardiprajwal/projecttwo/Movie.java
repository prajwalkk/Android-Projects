package edu.uic.spring20.kammardiprajwal.projecttwo;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int image;
    private String movieName;
    private String year;
    private String IMDBLink;
    private String YTLink;
    private String WikiLink;
    private String Director;
    private String rating_1;
    private String rating_2;
    private String cast;
    private String duration;
    private String releaseDate;

    public Movie(int image, String movieName, String year, String IMDBLink, String YTLink, String wikiLink, String director, String rating_1, String rating_2, String cast, String duration, String releaseDate) {
        this.image = image;
        this.movieName = movieName;
        this.year = year;
        this.IMDBLink = IMDBLink;
        this.YTLink = YTLink;
        WikiLink = wikiLink;
        Director = director;
        this.rating_1 = rating_1;
        this.rating_2 = rating_2;
        this.cast = cast;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }


    protected Movie(Parcel in) {
        image = in.readInt();
        movieName = in.readString();
        year = in.readString();
        IMDBLink = in.readString();
        YTLink = in.readString();
        WikiLink = in.readString();
        Director = in.readString();
        rating_1 = in.readString();
        rating_2 = in.readString();
        cast = in.readString();
        duration = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIMDBLink() {
        return IMDBLink;
    }

    public void setIMDBLink(String IMDBLink) {
        this.IMDBLink = IMDBLink;
    }

    public String getYTLink() {
        return YTLink;
    }

    public void setYTLink(String YTLink) {
        this.YTLink = YTLink;
    }

    public String getWikiLink() {
        return WikiLink;
    }

    public void setWikiLink(String wikiLink) {
        WikiLink = wikiLink;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getRating_1() {
        return rating_1;
    }

    public void setRating_1(String rating_1) {
        this.rating_1 = rating_1;
    }

    public String getRating_2() {
        return rating_2;
    }

    public void setRating_2(String rating_2) {
        this.rating_2 = rating_2;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "image=" + image +
                ", movieName='" + movieName + '\'' +
                ", year='" + year + '\'' +
                ", IMDBLink='" + IMDBLink + '\'' +
                ", YTLink='" + YTLink + '\'' +
                ", WikiLink='" + WikiLink + '\'' +
                ", Director='" + Director + '\'' +
                ", rating_1='" + rating_1 + '\'' +
                ", rating_2='" + rating_2 + '\'' +
                ", cast='" + cast + '\'' +
                ", duration='" + duration + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
        dest.writeString(movieName);
        dest.writeString(year);
        dest.writeString(IMDBLink);
        dest.writeString(YTLink);
        dest.writeString(WikiLink);
        dest.writeString(Director);
        dest.writeString(rating_1);
        dest.writeString(rating_2);
        dest.writeString(cast);
        dest.writeString(duration);
        dest.writeString(releaseDate);
    }
}
