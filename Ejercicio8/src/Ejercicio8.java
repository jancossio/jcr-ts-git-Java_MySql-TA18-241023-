import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Ejercicio8 {
	public static void main(String[] args){
		
		String name0 = "Ejercicio8";
		String name1 = "Productos";
		String name2 = "Cajeros";
		String name3 = "Maquinas_Registradoras";
		String name4 = "Venta";
		
		Connection conexion = mySQLConnection("root","P1gue0N$","");
		if(conexion != null) {
			createDB(name0, conexion);
			
			String instProd = "(Codigo int, Nombre VARCHAR(100), Precio int, PRIMARY KEY(Codigo))";
			String instCajr = "(Codigo int, NomApels VARCHAR(255), PRIMARY KEY(Codigo))";
			String instMaqs = "(Codigo int, Piso int, PRIMARY KEY(Codigo))";
			String instVent = "(Cajero int, Maquina int, Producto int, FOREIGN KEY(Cajero) references Cajeros(Codigo) on delete cascade on update cascade,"
							+ "	FOREIGN KEY(Maquina) references Maquinas_Registradoras(Codigo) on delete cascade on update cascade,"
							+ "	FOREIGN KEY(Producto) references Productos(Codigo) on delete cascade on update cascade,PRIMARY KEY(Cajero, Maquina, Producto))";
			
			createTable(name0, name1, instProd, conexion);
			createTable(name0, name2, instCajr, conexion);
			createTable(name0, name3, instMaqs, conexion);
			createTable(name0, name4, instVent, conexion);
			
			insertDataProd(name0, "1", "Microondas", "200", conexion);
			insertDataProd(name0, "2", "Camiseta", "16", conexion);
			insertDataProd(name0, "3", "Detergente", "18", conexion);
			insertDataProd(name0, "4", "Zapatos", "45", conexion);
			insertDataProd(name0, "5", "Pantalones", "60", conexion);
			
			insertDataCajr(name0, "101", "Maria Lacama",conexion);
			insertDataCajr(name0, "102", "Olga Niza", conexion);
			insertDataCajr(name0, "103", "Yaiza Latorre",conexion);
			insertDataCajr(name0, "104", "Jaime Bulto",conexion);
			insertDataCajr(name0, "105", "Belen tejas", conexion);
			
			insertDataMaqs(name0, "201", "1",conexion);
			insertDataMaqs(name0, "202", "1", conexion);
			insertDataMaqs(name0, "203", "2",conexion);
			insertDataMaqs(name0, "204", "3",conexion);
			insertDataMaqs(name0, "205", "2", conexion);
			
			insertDataVent(name0, "101", "201", "1", conexion);
			insertDataVent(name0, "102", "202", "2", conexion);
			insertDataVent(name0, "103", "203", "3", conexion);
			insertDataVent(name0, "104", "204", "4", conexion);
			insertDataVent(name0, "105", "205", "5", conexion);
			
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
	
	public static void insertDataProd(String db, String codigo, String nombre, String precio, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Productos(Codigo, Nombre, Precio) VALUE("
					+"\""+codigo+"\", "
					+"\""+nombre+"\", "
					+"\""+precio+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Productos rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Productos");
		}
	}
	
	public static void insertDataCajr(String db, String codigo, String nomApels, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Cajeros(Codigo, NomApels) VALUE("
					+"\""+codigo+"\", "
					+"\""+nomApels+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Cajeros rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Cajeros");
		}
	}
	
	public static void insertDataMaqs(String db, String codigo, String piso, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Maquinas_Registradoras(Codigo, Piso) VALUE("
					+"\""+codigo+"\", "
					+"\""+piso+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Maquinas_r rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Maquinas_r");
		}
	}
	
	public static void insertDataVent(String db, String cajero, String maquina, String producto, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Venta(Cajero, Maquina, Producto) VALUE("
					+"\""+cajero+"\", "
					+"\""+maquina+"\", "
					+"\""+producto+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Venta rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Venta");
		}
	}
}
