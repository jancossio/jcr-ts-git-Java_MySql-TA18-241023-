import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Ejercicio2 {
	public static void main(String[] args){
		
		String name0 = "Ejercicio2";
		String name1 = "Departamentos";
		String name2 = "Empleados";
		
		Connection conexion = mySQLConnection("root","P1gue0N$","");
		createDB(name0, conexion);
		createTableDeps(name0, name1, conexion);
		createTableEmps(name0, name2, conexion);
		
		insertDataDeps(name0, "1", "I+D", "1500", conexion);
		insertDataDeps(name0, "2", "Desarrollo","2500",conexion);
		insertDataDeps(name0, "3", "Produccion","5000",conexion);
		insertDataDeps(name0, "4", "Marketing","2500",conexion);
		insertDataDeps(name0, "5", "Recursos Humanos","3500",conexion);
		
		insertDataEmps(name0, "ASDD11FF", "Alfredo", "Lorente", "1", conexion);
		insertDataEmps(name0, "EGCS12SS", "Ester", "Luengo", "2", conexion);
		insertDataEmps(name0, "LOHG13RR", "Aitor", "Perez", "3", conexion);
		insertDataEmps(name0, "ERFD14TT", "Noel", "Dominguez", "4", conexion);
		insertDataEmps(name0, "FRED15WW", "David", "Alvarez", "5", conexion);
		
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
	
	public static void createTableDeps(String db, String name, Connection conection){
		try {
			String QueryDb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(QueryDb);

			String Query = "CREATE TABLE "+name+" (Codigo int, Nombre VARCHAR(100), Presupuesto int, PRIMARY KEY(Codigo))";
			Statement st = conection.createStatement();
			stdb.executeUpdate(Query);
			System.out.println("Tabla creada con exito");

		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Error creando tabla.");
		}
	}
	
	public static void createTableEmps(String db, String name, Connection conection){
		try {
			String QueryDb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(QueryDb);

			String Query = "CREATE TABLE "+name+" (DNI VARCHAR(8), Nombre VARCHAR(100), Apellidos VARCHAR(255), Departamento int, FOREIGN KEY(Departamento) references Departamentos(Codigo) on delete cascade on update cascade, PRIMARY KEY(DNI))";
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
	
	public static void insertDataDeps(String db, String codigo, String nombre, String presupuesto, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Departamentos(Codigo, Nombre, Presupuesto) VALUE("
					+"\""+codigo+"\", "
					+"\""+nombre+"\", "
					+"\""+presupuesto+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Departamentos rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Departamentos");
		}
	}
	
	public static void insertDataEmps(String db, String dni, String nombre, String apellidos, String departamento, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Empleados(DNI, Nombre, Apellidos, Departamento) VALUE("
					+"\""+dni+"\", "
					+"\""+nombre+"\", "
					+"\""+apellidos+"\", "
					+"\""+departamento+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Empleados rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Empleados");
		}
	}
}
