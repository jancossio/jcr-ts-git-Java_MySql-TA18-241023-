import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Ejercicio1 {
	public static void main(String[] args){
		
		String name0 = "Ejercicio1";
		String name1 = "Fabricantes";
		String name2 = "Articulos";
		
		Connection conexion = mySQLConnection("root","P1gue0N$","");
		createDB(name0, conexion);
		createTableFab(name0, name1, conexion);
		createTableArt(name0, name2, conexion);
		
		insertDataFabs(name0, "1", "Articulos.sa",conexion);
		insertDataFabs(name0, "2", "Fabricantes.sl",conexion);
		insertDataFabs(name0, "3", "Articulos sin fronteras",conexion);
		insertDataFabs(name0, "4", "Cachos sueltos",conexion);
		insertDataFabs(name0, "5", "Componentes",conexion);
		
		insertDataArts(name0, "11", "Camisa", "21", "1", conexion);
		insertDataArts(name0, "12", "Nevera", "799", "2", conexion);
		insertDataArts(name0, "13", "Pelota", "12", "3", conexion);
		insertDataArts(name0, "14", "Abrigo", "29", "4", conexion);
		insertDataArts(name0, "15", "Zapatos", "41", "5", conexion);
		
		closeConnection(conexion);
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
	
	public static void createTableFab(String db, String name, Connection conection){
		try {
			String QueryDb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(QueryDb);

			String Query = "CREATE TABLE "+name+" (Codigo int, Nombre VARCHAR(100), PRIMARY KEY(Codigo))";
			Statement st = conection.createStatement();
			stdb.executeUpdate(Query);
			System.out.println("Tabla creada con exito");

		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Error creando tabla.");
		}
	}
	
	public static void createTableArt(String db, String name, Connection conection){
		try {
			String QueryDb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(QueryDb);

			String Query = "CREATE TABLE "+name+" (Codigo int, Nombre VARCHAR(100), Precio int, Fabricante int, FOREIGN KEY(Fabricante) references Fabricantes(Codigo) on delete cascade on update cascade, PRIMARY KEY(Codigo))";
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
	
	public static void insertDataFabs(String db, String codigo, String nombre, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Fabricantes(Codigo, Nombre) VALUE("
					+"\""+codigo+"\", "
					+"\""+nombre+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Fabricantes rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Fabricantes");
		}
	}
	
	public static void insertDataArts(String db, String codigo, String nombre, String precio, String fabricante, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Articulos(Codigo, Nombre, Precio, Fabricante) VALUE("
					+"\""+codigo+"\", "
					+"\""+nombre+"\", "
					+"\""+precio+"\", "
					+"\""+fabricante+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Articulos rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Articulos");
		}
	}
}
