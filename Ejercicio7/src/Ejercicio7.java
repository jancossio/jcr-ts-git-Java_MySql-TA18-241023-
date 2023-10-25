import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Ejercicio7 {
	public static void main(String[] args){
		
		String name0 = "Ejercicio7";
		String name1 = "Cientificos";
		String name2 = "Proyectos";
		String name3 = "Asignado_A";
		
		Connection conexion = mySQLConnection("root","P1gue0N$","");
		if(conexion != null) {
			createDB(name0, conexion);
			
			String instCint = "(DNI VARCHAR(8), NomApels  VARCHAR(255), PRIMARY KEY(DNI))";
			String instProy = "(Id VARCHAR(4), Nombre VARCHAR(255), Horas int, PRIMARY KEY(Id))";
			String instAsig = "(Cientifico VARCHAR(8), Proyecto VARCHAR(4),FOREIGN KEY(Cientifico) references Cientificos(DNI) on delete cascade on update cascade,"
					+ " FOREIGN KEY(Proyecto) references Proyectos(Id) on delete cascade on update cascade, PRIMARY KEY(Cientifico,Proyecto))";
			
			createTable(name0, name1, instCint, conexion);
			createTable(name0, name2, instProy, conexion);
			createTable(name0, name3, instAsig, conexion);
			
			insertDataCint(name0, "AA12HHAA", "Maria Uncafe",conexion);
			insertDataCint(name0, "AA64RGAB", "Olga Perez", conexion);
			insertDataCint(name0, "AA09HYAC", "Dolores Anonimas",conexion);
			insertDataCint(name0, "AA94JJAD", "David Alvaro",conexion);
			insertDataCint(name0, "AAPK23AE", "Fatima Calderon", conexion);
			
			insertDataProy(name0, "AAAA", "Proyecto de Quimica", "200", conexion);
			insertDataProy(name0, "AAAB", "Proyecto de Geologia", "168", conexion);
			insertDataProy(name0, "AAAC", "Proyecto de Fisica", "250", conexion);
			insertDataProy(name0, "AAAD", "Proyecto de Entomologia", "210", conexion);
			insertDataProy(name0, "AAAE", "Proyecto de Entomologia", "240", conexion);
			
			insertDataAsig(name0, "AA12HHAA", "AAAA",conexion);
			insertDataAsig(name0, "AA64RGAB", "AAAB", conexion);
			insertDataAsig(name0, "AA09HYAC", "AAAC",conexion);
			insertDataAsig(name0, "AA94JJAD", "AAAD",conexion);
			insertDataAsig(name0, "AAPK23AE", "AAAE", conexion);
			
			closeConnection(conexion);
		}else {
			System.out.println("Conexion fallida!");
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
			//mySQLConnection("root","",name);
			JOptionPane.showInputDialog(null, "Se ha creado la Database en el servidor");
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
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+name, user, password);
			System.out.println("Server connected");
			JOptionPane.showInputDialog(null, "Se ha creado la conexion con el servidor");
			return conexion;
		}catch(SQLException | ClassNotFoundException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	public static void insertDataCint(String db, String dni, String nomApels, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Cientificos(DNI, NomApels) VALUE("
					+"\""+dni+"\", "
					+"\""+nomApels+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Cientificos rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Cientificos");
		}
	}
	
	public static void insertDataProy(String db, String id, String nombre, String horas, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Proyectos(Id, Nombre, Horas) VALUE("
					+"\""+id+"\", "
					+"\""+nombre+"\", "
					+"\""+horas+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Proyecto rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Proyecto");
		}
	}
	
	public static void insertDataAsig(String db, String cientifico, String proyecto, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Asignado_A(Cientifico, Proyecto) VALUE("
					+"\""+cientifico+"\", "
					+"\""+proyecto+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Asignado_A rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Asignado_A");
		}
	}
}
