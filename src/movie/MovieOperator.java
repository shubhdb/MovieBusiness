package movie;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieOperator {

	public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
		
		Scanner sc=new Scanner(System.in);
		MovieService mvsrv=new MovieService();
		File file=new File("Movie.txt");
		List<Movie> movielist = null;
		
		
		
		int choice;
		do {
			
			System.out.println("Enter your choice\n1.Populate from file to list\n2.Add List to database \n3.Add new movie"
					+ "\n4.Serialize Movie\n5.Deserialize Movie\n6.Search Movies by year\n7.Get movies by actor"
					+ "\n8.Update ratings of movie\n9.Update total business of movie\n10.Get Movies above 150 business");
			choice=sc.nextInt();sc.nextLine();
			switch (choice) {
			case 1:System.out.println("Populating movies from file");
					movielist=mvsrv.populateMovies(file);
					System.out.println(movielist); 
					break;
			case 2:System.out.println("Adding movies to DataBase");
					try {
						mvsrv.allMoviesInDb(movielist);
					} catch (SQLException e) {
						System.out.println(e);
					}
					break;
			case 3: System.out.println("Adding movie object to list");
					ArrayList< String> casting=new ArrayList<>();
					casting.add("Rajkumar");
					casting.add("Nushrat");
					Movie movie=new Movie(5, "Challang", "A","Hindi", Date.valueOf("2020-10-16"), casting, 3.5, 150.0);
					mvsrv.addMovie(movie, movielist);
					try {
						if(mvsrv.allMoviesInDb(movielist))
							System.out.println("Movies added successfully");
						else {
							System.out.println("Failed to add movie");
							}
					} catch (SQLException e) {
						System.out.println(e);
					}
					System.out.println(movielist);
					break;
			case 4:System.out.println("Serializing the movie");
					movielist=mvsrv.populateMovies(file);
					//mvsrv.serializeMovies(movielist, "movie.sr");
					break;
			case 5:System.out.println("Deserializing the movie");
					movielist=mvsrv.populateMovies(file);
					//System.out.println(mvsrv.deserializeMovie("movie.sr"));
					break;
			case 6:
					System.out.println("Enter Year to search movies");
					int year=sc.nextInt();
					System.out.println(mvsrv.getMoviesReleasedInYear(year));
					break;
			case 7:
					System.out.println("Enter actor name");
					String actor=sc.next();
					System.out.println(mvsrv.getMoviesByActor(actor));
					break;
			case 8:System.out.println("Updating Ratings");
					movielist=mvsrv.populateMovies(file);
					mvsrv.updateRatings(movielist.get(1), 2.9, movielist);
					mvsrv.allMoviesInDb(movielist);                                  
					System.out.println(movielist);
					break;
			case 9:System.out.println("Updating business");
					movielist=mvsrv.populateMovies(file);
					mvsrv.updateBusiness(movielist.get(0), 15000,movielist);
					mvsrv.allMoviesInDb(movielist);  
					System.out.println(movielist);
					break;
			case 10:
					movielist=mvsrv.populateMovies(file);
					System.out.println(mvsrv.businessDone(150));
					break;
			default : System.out.println("Enter correct choice.");break;
			}
		}while(choice!=4);

	}

}
