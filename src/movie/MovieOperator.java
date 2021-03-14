package movie;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieOperator {

	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		MovieService mvsrv=new MovieService();
		File file=new File("Movie.txt");
		List<Movie> movielist = null;
		
		
		
		int choice;
		do {
			
			System.out.println("Enter your choice\n1.Populate from file to list\n2.Add List to database \n3.Add new movie");
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
			case 4: System.out.println("Thank you");break;
			default : System.out.println("Enter correct choice.");break;
			}
		}while(choice!=4);

	}

}
