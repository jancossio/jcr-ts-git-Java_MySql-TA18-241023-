import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Ejercicio6 {
	public static void main(String[] args){
		
		String name0 = "Ejercicio6";
		String name1 = "Proveedores";
		String name2 = "Piezas";
		String name3 = "Suministra";
		
		Connection conexion = mySQLConnection("root","P1gue0N$","");
		if(conexion != null) {
			createDB(name0, conexion);
			
			String instProv = "(Id VARCHAR(4), Nombre  VARCHAR(100), PRIMARY KEY(Id))";
			String instPiez = "(Codigo int, Nombre VARCHAR(100), PRIMARY KEY(Codigo))";
			String instSumn = "(CodigoPieza int, IdProveedor VARCHAR(4), Precio int,FOREIGN KEY(CodigoPieza) references Piezas(Codigo) on delete cascade on update cascade,"
							+ "FOREIGN KEY(IdProveedor) references Proveedores(Id) on delete cascade on update cascade, PRIMARY KEY(CodigoPieza,IdProveedor))";
			
			createTable(name0, name1, instProv, conexion);
			createTable(name0, name2, instPiez, conexion);
			createTable(name0, name3, instSumn, conexion);
			
			insertDataProv(name0, "AAAA", "Prove Piezas",conexion);
			insertDataProv(name0, "AAAB", "Proveedor 2", conexion);
			insertDataProv(name0, "AAAC", "Piezas Anonimas",conexion);
			insertDataProv(name0, "AAAD", "Clavos y Tornillso",conexion);
			insertDataProv(name0, "AAAE", "Despiezados", conexion);
			
			insertDataPiez(name0, "1", "Juntas", conexion);
			insertDataPiez(name0, "2", "Clavos", conexion);
			insertDataPiez(name0, "3", "Juntas", conexion);
			insertDataPiez(name0, "4", "Tornillos", conexion);
			insertDataPiez(name0, "5", "Tuercas", conexion);
			
			insertDataSumn(name0, "1", "AAAA", "12",conexion);
			insertDataSumn(name0, "2", "AAAB", "15", conexion);
			insertDataSumn(name0, "3", "AAAC", "11",conexion);
			insertDataSumn(name0, "4", "AAAD", "10",conexion);
			insertDataSumn(name0, "5", "AAAE", "9", conexion);
			
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
	
	public static void insertDataProv(String db, String id, String nombre, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Proveedores(Id, Nombre) VALUE("
					+"\""+id+"\", "
					+"\""+nombre+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Proveedores rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Proveedores");
		}
	}
	
	public static void insertDataPiez(String db, String codigo, String nombre, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Piezas(Codigo, Nombre) VALUE("
					+"\""+codigo+"\", "
					+"\""+nombre+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Piezas rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Piezas");
		}
	}
	
	public static void insertDataSumn(String db, String codigoPieza, String idProveedor, String Precio, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Suministra(CodigoPieza, IdProveedor, Precio) VALUE("
					+"\""+codigoPieza+"\", "
					+"\""+idProveedor+"\", "
					+"\""+Precio+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Suministra rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Suministra");
		}
	}
}
