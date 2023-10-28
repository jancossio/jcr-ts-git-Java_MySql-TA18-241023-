import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Ejercicio1App {
	public static void main(String[] args){
		
		String name0 = "Ejercicio1";
		String name1 = "Fabricantes";
		String name2 = "Articulos";
		
		Connection conexion = mySQLConnection("root","P1gue0N$","");
		if(conexion != null) {
			
			String InstFabs = " (Codigo int, Nombre VARCHAR(100), PRIMARY KEY(Codigo))";
			String InstArts = "(Codigo int, Nombre VARCHAR(100), Precio int, Fabricante int, "
						+ "FOREIGN KEY(Fabricante) references Fabricantes(Codigo) on delete cascade on update cascade, PRIMARY KEY(Codigo))";
			
			createDB(name0, conexion);
			createTable(name0, name1, InstFabs, conexion);
			createTable(name0, name2, InstArts, conexion);
			
			String[] arguments0 = {"1", "Articulos.sa"};
			String[] arguments1 = {"2", "Fabricantes.sl"};
			String[] arguments2 = {"3", "Articulos sin fronteras"};
			String[] arguments3 = {"4", "Cachos sueltos"};
			String[] arguments4 = {"5", "Componentes"};
			
			String tab1 = "INSERT INTO Fabricantes(Codigo, Nombre) VALUE(";
			
			insertData(name0, tab1, arguments0, conexion);
			insertData(name0, tab1, arguments1, conexion);
			insertData(name0, tab1, arguments2, conexion);
			insertData(name0, tab1, arguments3, conexion);
			insertData(name0, tab1, arguments4, conexion);
			
			String[] arguments6 = {"11", "Camisa", "21", "1"};
			String[] arguments7 = {"12", "Nevera", "799", "2"};
			String[] arguments8 = {"13", "Pelota", "12", "3"};
			String[] arguments9 = {"14", "Abrigo", "29", "4"};
			String[] arguments10 = {"15", "Zapatos", "41", "5"};
			
			String tab2 = "INSERT INTO Articulos(Codigo, Nombre, Precio, Fabricante) VALUE(";
			
			insertData(name0, tab2, arguments6, conexion);
			insertData(name0, tab2,arguments7, conexion);
			insertData(name0, tab2,arguments8, conexion);
			insertData(name0, tab2,arguments9, conexion);
			insertData(name0, tab2,arguments10, conexion);
			
			closeConnection(conexion);
		}else {
			System.out.println("Conexion fallida!");
		}
	}
	
	public static void closeConnection(Connection Conexion){
		try {
			Conexion.close();
			JOptionPane.showMessageDialog(null, "Se ha cerrado la conexion con el servidor");
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
			//mySQLConnection("root","",name);
			JOptionPane.showMessageDialog(null, "Se ha creado la Database "+name+" en el servidor");
		}catch(SQLException ex) {
			System.out.println("Fallo al crear la base de datos");
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void createTable(String db, String name, String instructions, Connection conection){
		try {
			String QueryDb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(QueryDb);

			String Query = "CREATE TABLE "+name+instructions;
			Statement st = conection.createStatement();
			stdb.executeUpdate(Query);
			System.out.println("Tabla "+name+" creada con exito");

		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Error creando tabla "+name);
		}
	}
	
	public static Connection mySQLConnection(String user, String password, String name) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+name,user, password);
			System.out.println("Server connected");
			JOptionPane.showMessageDialog(null, "Se ha creado la conexion con el servidor");
			return conexion;
		}catch(SQLException | ClassNotFoundException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	public static void insertData(String db, String table, String[] vars, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = table;
			for(int i=0; i<vars.length; i++) {
				if(i==(vars.length-1)) {
					query += ("\""+vars[i]+"\"); ");
				}else {
					query += ("\""+vars[i]+"\", ");
				}
			};
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla");
		}
	}
}
