import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Ejercicio3App {
	public static void main(String[] args){
		
		String name0 = "Ejercicio3";
		String name1 = "Almacenes";
		String name2 = "Cajas";
		
		Connection conexion = mySQLConnection("root","P1gue0N$","");
		if(conexion != null) {
		createDB(name0, conexion);
		
		String instAlms = "(Codigo int, Lugar VARCHAR(100), Capacidad int, PRIMARY KEY(Codigo))";
		String instCajs = " (NumReferencia VARCHAR(5), Contenido VARCHAR(100), Valor int, Almacen int,"
						+ " FOREIGN KEY(Almacen) references Almacenes(Codigo) on delete cascade on update cascade, PRIMARY KEY(NumReferencia))";
		
		createTable(name0, name1, instAlms, conexion);
		createTable(name0, name2, instCajs, conexion);
		
		String tab1 = "INSERT INTO Almacenes(Codigo, Lugar, Capacidad) VALUE(";
		
		String[] arguments0 = {"1", "Tarragona", "90"};
		String[] arguments1 = {"2", "Barcelona", "80"};
		String[] arguments2 = {"3", "Girona", "88"};
		String[] arguments3 = {"4", "Madrid", "75"};
		String[] arguments4 = {"5", "Lleida", "77"};
		
		insertData(name0, tab1, arguments0, conexion);
		insertData(name0, tab1, arguments1, conexion);
		insertData(name0, tab1, arguments2, conexion);
		insertData(name0, tab1, arguments3,conexion);
		insertData(name0, tab1, arguments4, conexion);
		
		String tab2 = "INSERT INTO Cajas(NumReferencia, Contenido, Valor, Almacen) VALUE(";
		
		String[] arguments5 = {"11AST", "Detergente", "11", "1"};
		String[] arguments6 = {"12LDE", "Latas", "3", "2"};
		String[] arguments7 = {"13HSK", "Cepillo", "6", "3"};
		String[] arguments8 = {"14JIU", "Recogedor", "4", "4"};
		String[] arguments9 = { "15LYG", "Limpiador", "8", "5"};
		
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