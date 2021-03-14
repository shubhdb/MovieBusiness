package movie;

import java.sql.Date;
import java.util.List;

public class Movie {
	private int movieId; 
	private String movieName;
	
	private String movieType;
	//Category movieType;
	
	private String language;
	//Language language, 
	
	private Date releaseDate; 
	List<String> casting;
	double rating;
	double totalBusinessDone;
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieType() {
		return movieType;
	}
	public void setMovieType(String movieType) {
		this.movieType = movieType;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public List<String> getCasting() {
		return casting;
	}
	public void setCasting(List<String> casting) {
		this.casting = casting;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public double getTotalBusinessDone() {
		return totalBusinessDone;
	}
	public void setTotalBusinessDone(double totalBusinessDone) {
		this.totalBusinessDone = totalBusinessDone;
	}
	
	@Override
	public String toString() {
		return "Movie [movieId=" + movieId + ", movieName=" + movieName + ", movieType=" + movieType + ", language="
				+ language + ", releaseDate=" + releaseDate + ", casting=" + casting + ", rating=" + rating
				+ ", totalBusinessDone=" + totalBusinessDone + "]";
	}
	
	

}
