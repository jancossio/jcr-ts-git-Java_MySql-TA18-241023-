import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Ejercicio4 {
	public static void main(String[] args){
		
		String name0 = "Ejercicio4";
		String name1 = "Peliculas";
		String name2 = "Salas";
		
		Connection conexion = mySQLConnection("root","P1gue0N$","");
		if(conexion != null) {
			createDB(name0, conexion);
			createTablePels(name0, name1, conexion);
			createTableSalas(name0, name2, conexion);
			
			insertDataPels(name0, "1", "Deep Impact", "13",conexion);
			insertDataPels(name0, "2", "Armaggedon", "13", conexion);
			insertDataPels(name0, "3", "Toy Story", "3",conexion);
			insertDataPels(name0, "4", "Weird Science", "13",conexion);
			insertDataPels(name0, "5", "Spider Man 2", "7", conexion);
			
			insertDataSalas(name0, "11", "Sala 1", "1", conexion);
			insertDataSalas(name0, "12", "Sala 2", "2", conexion);
			insertDataSalas(name0, "13", "Sala 3", "3", conexion);
			insertDataSalas(name0, "14", "Sala 4", "4", conexion);
			insertDataSalas(name0, "15", "Sala 5", "5", conexion);
			
			closeConnection(conexion);
		}
	}
	
	public static void closeConnection(Connection Conexion){
		try {
			Conexion.close();
			JOptionPane.showInputDialog(null, "Se ha cerrado la conexion con el servidor");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void createDB(String name, Connection conection){
		try {
			String Query = "CREATE DATABASE "+name;
			Statement st = conection.createStatement();
			st.executeUpdate(Query);
			//closeConnection(conection);
			mySQLConnection("root","",name);
			JOptionPane.showInputDialog(null, "Se ha creado la Database en el servidor");
		}catch(SQLException ex) {
			System.out.println("Fallo al crear la base de datos");
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void createTablePels(String db, String name, Connection conection){
		try {
			String QueryDb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(QueryDb);

			String Query = "CREATE TABLE "+name+" (Codigo int, Nombre VARCHAR(100), CalificacionEdad int, PRIMARY KEY(Codigo))";
			Statement st = conection.createStatement();
			stdb.executeUpdate(Query);
			System.out.println("Tabla creada con exito");

		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Error creando tabla.");
		}
	}
	
	public static void createTableSalas(String db, String name, Connection conection){
		try {
			String QueryDb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(QueryDb);

			String Query = "CREATE TABLE "+name+" (Codigo int, Nombre VARCHAR(100), Pelicula int, FOREIGN KEY(Pelicula) references Peliculas(Codigo) on delete cascade on update cascade, PRIMARY KEY(Codigo))";
			Statement st = conection.createStatement();
			stdb.executeUpdate(Query);
			System.out.println("Tabla creada con éxito");

		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Error creando tabla.");
		}
	}
	
	public static Connection mySQLConnection(String user, String password, String name) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+name,"root", "P1gue0N$");
			System.out.println("Server connected");
			JOptionPane.showInputDialog(null, "Se ha creado la conexion con el servidor");
			return conexion;
		}catch(SQLException | ClassNotFoundException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	public static void insertDataPels(String db, String codigo, String nombre, String calificacionEdad, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Peliculas(Codigo, Nombre, CalificacionEdad) VALUE("
					+"\""+codigo+"\", "
					+"\""+nombre+"\", "
					+"\""+calificacionEdad+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Peliculas rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Peliculas");
		}
	}
	
	public static void insertDataSalas(String db, String codigo, String nombre, String pelicula, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Salas(Codigo, Nombre, Pelicula) VALUE("
					+"\""+codigo+"\", "
					+"\""+nombre+"\", "
					+"\""+pelicula+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Salas rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Salas");
		}
	}
}
