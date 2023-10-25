import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Ejercicio3 {
	public static void main(String[] args){
		
		String name0 = "Ejercicio3";
		String name1 = "Almacenes";
		String name2 = "Cajas";
		
		Connection conexion = mySQLConnection("root","P1gue0N$","");
		createDB(name0, conexion);
		createTableAlms(name0, name1, conexion);
		createTableCajas(name0, name2, conexion);
		
		insertDataAlms(name0, "1", "Tarragona", "90",conexion);
		insertDataAlms(name0, "2", "Barcelona", "80", conexion);
		insertDataAlms(name0, "3", "Girona", "88",conexion);
		insertDataAlms(name0, "4", "Madrid", "75",conexion);
		insertDataAlms(name0, "5", "Lleida", "77", conexion);
		
		insertDataCajas(name0, "11AST", "Detergente", "11", "1", conexion);
		insertDataCajas(name0, "12LDE", "Latas", "3", "2", conexion);
		insertDataCajas(name0, "13HSK", "Cepillo", "6", "3", conexion);
		insertDataCajas(name0, "14JIU", "Recogedor", "4", "4", conexion);
		insertDataCajas(name0, "15LYG", "Limpiador", "8", "5", conexion);
		
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
	
	public static void createTableAlms(String db, String name, Connection conection){
		try {
			String QueryDb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(QueryDb);

			String Query = "CREATE TABLE "+name+" (Codigo int, Lugar VARCHAR(100), Capacidad int, PRIMARY KEY(Codigo))";
			Statement st = conection.createStatement();
			stdb.executeUpdate(Query);
			System.out.println("Tabla creada con exito");

		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Error creando tabla.");
		}
	}
	
	public static void createTableCajas(String db, String name, Connection conection){
		try {
			String QueryDb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(QueryDb);

			String Query = "CREATE TABLE "+name+" (NumReferencia VARCHAR(5), Contenido VARCHAR(100), Valor int, Almacen int, FOREIGN KEY(Almacen) references Almacenes(Codigo) on delete cascade on update cascade, PRIMARY KEY(NumReferencia))";
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
	
	public static void insertDataAlms(String db, String codigo, String lugar, String capacidad, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Almacenes(Codigo, Lugar, Capacidad) VALUE("
					+"\""+codigo+"\", "
					+"\""+lugar+"\", "
					+"\""+capacidad+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Almacenes rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Almacenes");
		}
	}
	
	public static void insertDataCajas(String db, String numReferencia, String contenido, String valor, String almacen, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Cajas(NumReferencia, Contenido, Valor, Almacen) VALUE("
					+"\""+numReferencia+"\", "
					+"\""+contenido+"\", "
					+"\""+valor+"\", "
					+"\""+almacen+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Cajas rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Cajas");
		}
	}
}