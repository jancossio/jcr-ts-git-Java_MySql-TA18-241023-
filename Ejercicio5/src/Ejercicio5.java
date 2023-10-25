import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Ejercicio5 {

		public static void main(String[] args){
			
			String name0 = "Ejercicio5";
			String name1 = "Despachos";
			String name2 = "Directores";
			
			Connection conexion = mySQLConnection("root","P1gue0N$","");
			if(conexion != null) {
				createDB(name0, conexion);
				
				String instDesp = "(Numero int, Capacidad int, PRIMARY KEY(Numero))";
				String instDirs = " (DNI VARCHAR(8), NomApels VARCHAR(255), DNI_Jefe VARCHAR(8), Despacho int,"
						   		+"FOREIGN KEY(Despacho) references Despachos(Numero) on delete cascade on update cascade, "
						   		+"FOREIGN KEY(DNI_Jefe) references Directores(DNI) on delete cascade on update cascade, PRIMARY KEY(DNI))";
				
				createTable(name0, name1, instDesp, conexion);
				createTable(name0, name2, instDirs, conexion);
				
				insertDataDesp(name0, "1", "3",conexion);
				insertDataDesp(name0, "2", "4", conexion);
				insertDataDesp(name0, "3", "3",conexion);
				insertDataDesp(name0, "4", "4",conexion);
				insertDataDesp(name0, "5", "2", conexion);
				
				insertDataDirs(name0, "27832FG8", "Maria Lorente", "27832FG8", "1", conexion);
				insertDataDirs(name0, "92742YU6", "Raul Torres", "27832FG8", "2", conexion);
				insertDataDirs(name0, "91342RD7", "Jaime Salvado", "92742YU6", "3", conexion);
				insertDataDirs(name0, "98981RD2", "Antonio Perez", "91342RD7", "4", conexion);
				insertDataDirs(name0, "73828GY7", "Teresa Martin", "98981RD2", "5", conexion);
				
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
		
		public static void insertDataDesp(String db, String numero, String capacidad, Connection conection) {
			try {
				String Querydb = "USE "+db+";";
				Statement stdb = conection.createStatement();
				stdb.executeUpdate(Querydb);
				
				String query = "INSERT INTO Despachos(Numero, Capacidad) VALUE("
						+"\""+numero+"\", "
						+"\""+capacidad+"\"); ";
				
				Statement st = conection.createStatement();
				st.executeUpdate(query);
				System.out.println("Tabla Departamentos rellenada con exito");
			}catch(SQLException ex) {
				//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
				System.out.println("Error al rellenar la tabla Departamentos");
			}
		}
		
		public static void insertDataDirs(String db, String dni, String nomApels, String dniJefe, String despacho, Connection conection) {
			try {
				String Querydb = "USE "+db+";";
				Statement stdb = conection.createStatement();
				stdb.executeUpdate(Querydb);
				
				String query = "INSERT INTO Directores(DNI, NomApels, DNI_Jefe, Despacho) VALUE("
						+"\""+dni+"\", "
						+"\""+nomApels+"\", "
						+"\""+dniJefe+"\", "
						+"\""+despacho+"\"); ";
				
				Statement st = conection.createStatement();
				st.executeUpdate(query);
				System.out.println("Tabla Directores rellenada con exito");
			}catch(SQLException ex) {
				//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
				System.out.println("Error al rellenar la tabla Directores");
			}
	}
}
