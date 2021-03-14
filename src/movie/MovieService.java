package movie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
	
	
	void serializeMovies(List<Movie> movies, String fileName)
	{
		System.out.println("Entered into function");
		File file=new File(fileName);
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		FileOutputStream fos;
		ObjectOutputStream oout;
		try {
			fos = new FileOutputStream(file);
			oout=new ObjectOutputStream(fos);
			oout.writeObject(movies);
			fos.close();
			oout.close();
			return;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	List<Movie> deserializeMovie(String fileName) 
	{
		File file=new File(fileName);
		List<Movie> movielist = null;
		FileInputStream fin;
		try {
			fin = new FileInputStream(file);
			ObjectInputStream oin=new ObjectInputStream(fin);
			movielist=(List<Movie>) oin.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {	
			e.printStackTrace();
		} catch (IOException e) {	
			e.printStackTrace();
		}
		
		return movielist;
	}
	
	
	List<Movie> getMoviesReleasedInYear(int year) throws SQLException
	{
		Connection conn=new DBConnections().getConnection();
		Statement stmt=conn.createStatement();
		ResultSet res=stmt.executeQuery("Select * from MovieBusiness");
		List<Movie> movielist=new ArrayList<>();
		while(res.next())
		{	
			String[] date=res.getDate(5).toString().split("-");
			if(Integer.parseInt(date[0])==year)
			{
				
				String[] actorName=res.getString(6).split(",");
				List<String> casting=new ArrayList<>();
				for(String s:actorName)
					casting.add(s);
				Movie m=new Movie(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getDate(5),casting, res.getDouble(7), res.getDouble(8));
				movielist.add(m);
			}
			
		}
		conn.close();
		return movielist;
	}
	
	List<Movie> getMoviesByActor(String... actorNames) throws SQLException
	{
		Connection conn=new DBConnections().getConnection();
		Statement stmt=conn.createStatement();
		ResultSet res=stmt.executeQuery("Select * from MOVIEBUSINESS");
		ArrayList<Movie> resultmovies=new ArrayList<>();
		while(res.next())
		{
				String[] casting=res.getString(6).split(",");
				List<String> actorlist=new ArrayList<>();
				for(String actor:casting)
					actorlist.add(actor);
				for(String keyactor:actorNames)
				{
					int flag=0;
					for(String actor:actorlist)
					{						
						if(actor.equalsIgnoreCase(keyactor))
						{
							Movie movie=new Movie(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getDate(5),actorlist, res.getDouble(7), res.getDouble(8));
							resultmovies.add(movie);
							flag=1;
							break;				
						}
					}
					if(flag==1)
						break;		
				}		
		}
		conn.close();
		return resultmovies;
	}
	
	void updateRatings(Movie movie, double rating ,List<Movie> movies)
	{
 		
                  for(int i=0;i<movies.size();i++)
                  {
                	  if(movies.get(i).getMovieId()==movie.getMovieId()) 
                	  {
                		  movies.get(i).setRating(rating);
                		  break;
                	  }
                  }
	}
	
	void updateBusiness(Movie movie, double amount,List<Movie> movies){
		for(int i=0;i<movies.size();i++)
        {
      	  if(movies.get(i).getMovieId()==movie.getMovieId())
      	  {
      		  movies.get(i).setTotalBusinessDone(amount);
      		  break;
      	  }

        }

	}
	
	Set<Movie> businessDone(double amount) throws SQLException
	{
		Connection conn=new DBConnections().getConnection();
		Statement stmt=conn.createStatement();
		ResultSet res=stmt.executeQuery("Select * from MOVIEBUSINESS");
		List<Movie> movielist=new ArrayList<>();
		while(res.next())
		{
			if(res.getDouble(8)>amount) {
				String[] temp2=res.getString(6).split(",");
				List<String> l=new ArrayList<>();
				for(String s:temp2)
					l.add(s);
				Movie movie=new Movie(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getDate(5),l, res.getDouble(7), res.getDouble(8));
				movielist.add(movie);
			     }
		}
		Collections.sort(movielist,new MovieByBusinessComparator());
		HashSet<Movie> moviehashSet=new HashSet<>();
		moviehashSet.addAll(movielist);
		conn.close();
		return moviehashSet;	
	}
}
