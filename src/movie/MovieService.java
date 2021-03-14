package movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class MovieService {
	
	List<Movie> populateMovies(File file){
		List<Movie> list=new ArrayList<Movie>();
		try {
			Scanner filesc=new Scanner(file);	
			while(filesc.hasNext())
			{
				String[] str=filesc.next().split(",");
				List<String> casting=new ArrayList<String>();
				for(int i=5;i<str.length-2;i++)
				{
					casting.add(str[i]);
				}
				Movie movie=new Movie(Integer.parseInt(str[0]), str[1], str[2], str[3],Date.valueOf(str[4]), casting, Double.parseDouble(str[str.length-2]),Double.parseDouble( str[str.length-1]));
			   list.add(movie);
			   }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	boolean allMoviesInDb(List<Movie> movies) throws SQLException {
		Connection con=new DBConnections().getConnection();
		try {		
			for(Movie m:movies) 
				{
					String casting="";
					List<String> temp=m.getCasting();
					for(String s:temp)
						casting=casting+s+",";
					casting=casting.trim();
				PreparedStatement pstmt=con.prepareStatement("insert into MovieBusiness values(?,?,?,?,?,?,?,?)");
				pstmt.setInt(1, m.getMovieId());
				pstmt.setString(2, m.getMovieName());
				pstmt.setString(3, m.getMovieType());
				pstmt.setString(4, m.getLanguage());
				pstmt.setDate(5, m.getReleaseDate());
				pstmt.setString(6,casting);
				pstmt.setDouble(7, m.getRating());
				pstmt.setDouble(8, m.getTotalBusinessDone());
				pstmt.execute();
			}
		}catch (Exception e) {
			con.close();
			return false;
		}
		con.close();
		return true;
	}
	
	void addMovie(Movie movie,List<Movie> movies)
	{
		movies.add(movie);
	}
}
