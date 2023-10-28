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
			
			String tab1 = "INSERT INTO Cientificos(DNI, NomApels) VALUE(";
			
			String[] arguments0 = {"AA12HHAA", "Maria Uncafe"};
			String[] arguments1 = {"AA64RGAB", "Olga Perez"};
			String[] arguments2 = {"AA09HYAC", "Dolores Anonimas"};
			String[] arguments3 = {"AA94JJAD", "David Alvaro"};
			String[] arguments4 = {"AAPK23AE", "Fatima Calderon"};
			
			insertData(name0, tab1, arguments0,conexion);
			insertData(name0, tab1, arguments1, conexion);
			insertData(name0, tab1, arguments2,conexion);
			insertData(name0, tab1, arguments3,conexion);
			insertData(name0, tab1, arguments4, conexion);
			
			String tab2 = "INSERT INTO Proyectos(Id, Nombre, Horas) VALUE(";
			
			String[] arguments5 = {"AAAA", "Proyecto de Quimica", "200"};
			String[] arguments6 = {"AAAB", "Proyecto de Geologia", "168"};
			String[] arguments7 = {"AAAC", "Proyecto de Fisica", "250"};
			String[] arguments8 = {"AAAD", "Proyecto de Entomologia", "210"};
			String[] arguments9 = {"AAAE", "Proyecto de Entomologia", "240"};
			
			insertData(name0, tab2, arguments5, conexion);
			insertData(name0, tab2, arguments6, conexion);
			insertData(name0, tab2, arguments7, conexion);
			insertData(name0, tab2, arguments8, conexion);
			insertData(name0, tab2, arguments9, conexion);
			
			String tab3 = "INSERT INTO Asignado_A(Cientifico, Proyecto) VALUE(";
			
			String[] arguments10 = {"AA12HHAA", "AAAA"};
			String[] arguments11 = {"AA64RGAB", "AAAB"};
			String[] arguments12 = {"AA09HYAC", "AAAC"};
			String[] arguments13 = {"AA94JJAD", "AAAD"};
			String[] arguments14 = {"AAPK23AE", "AAAE"};
			
			insertData(name0, tab3, arguments10,conexion);
			insertData(name0, tab3, arguments11, conexion);
			insertData(name0, tab3, arguments12,conexion);
			insertData(name0, tab3, arguments13,conexion);
			insertData(name0, tab3, arguments14, conexion);
			
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
			System.out.println("Fallo al crear la base de datos: "+ex);
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
