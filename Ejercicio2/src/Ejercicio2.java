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
		if(conexion != null) {
			createDB(name0, conexion);
			
			String instDeps = " (Codigo int, Nombre VARCHAR(100), Presupuesto int, PRIMARY KEY(Codigo))";
			String instEmps = (" (DNI VARCHAR(8), Nombre VARCHAR(100), Apellidos VARCHAR(255), Departamento int, "
							+ "FOREIGN KEY(Departamento) references Departamentos(Codigo) on delete cascade on update cascade, PRIMARY KEY(DNI))");

			createTable(name0, name1, instDeps, conexion);
			createTable(name0, name2, instEmps, conexion);
			
			String tab1 = "INSERT INTO Departamentos(Codigo, Nombre, Presupuesto) VALUE(";
			
			String[] arguments0 = {"1", "I+D", "1500"};
			String[] arguments1 = {"2", "Desarrollo","2500"};
			String[] arguments2 = {"3", "Produccion","5000"};
			String[] arguments3 = {"4", "Marketing","2500"};
			String[] arguments4 = {"5", "Recursos Humanos","3500"};
			
			insertData(name0, tab1, arguments0, conexion);
			insertData(name0, tab1, arguments1,conexion);
			insertData(name0, tab1, arguments2,conexion);
			insertData(name0, tab1, arguments3,conexion);
			insertData(name0, tab1, arguments4,conexion);
			
			String tab2 = "INSERT INTO Empleados(DNI, Nombre, Apellidos, Departamento) VALUE(";
			
			String[] arguments5 = {"ASDD11FF", "Alfredo", "Lorente", "1"};
			String[] arguments6 = {"EGCS12SS", "Ester", "Luengo", "2"};
			String[] arguments7 = {"LOHG13RR", "Aitor", "Perez", "3"};
			String[] arguments8 = {"ERFD14TT", "Noel", "Dominguez", "4"};
			String[] arguments9 = {"FRED15WW", "David", "Alvarez", "5"};
			
			insertData(name0, tab2, arguments5, conexion);
			insertData(name0, tab2, arguments6, conexion);
			insertData(name0, tab2, arguments7, conexion);
			insertData(name0, tab2, arguments8, conexion);
			insertData(name0, tab2, arguments9, conexion);
			
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
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+name,user, password);
			System.out.println("Server connected");
			JOptionPane.showInputDialog(null, "Se ha creado la conexion con el servidor");
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
