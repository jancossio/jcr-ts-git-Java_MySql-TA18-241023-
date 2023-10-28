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
				
				String tab1 = "INSERT INTO Despachos(Numero, Capacidad) VALUE(";
				
				String[] arguments0 = {"1", "3"};
				String[] arguments1 = {"2", "4"};
				String[] arguments2 = {"3", "3"};
				String[] arguments3 = {"4", "4"};
				String[] arguments4 = {"5", "2"};
				
				insertData(name0, tab1, arguments0,conexion);
				insertData(name0, tab1, arguments1, conexion);
				insertData(name0, tab1, arguments2,conexion);
				insertData(name0, tab1, arguments3,conexion);
				insertData(name0, tab1, arguments4, conexion);
				
				String tab2 = "INSERT INTO Directores(DNI, NomApels, DNI_Jefe, Despacho) VALUE(";
				
				String[] arguments5 = {"27832FG8", "Maria Lorente", "27832FG8", "1"};
				String[] arguments6 = {"92742YU6", "Raul Torres", "27832FG8", "2"};
				String[] arguments7 = {"91342RD7", "Jaime Salvado", "92742YU6", "3"};
				String[] arguments8 = {"98981RD2", "Antonio Perez", "91342RD7", "4"};
				String[] arguments9 = {"73828GY7", "Teresa Martin", "98981RD2", "5"};
				
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
