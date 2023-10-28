import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Ejercicio6App {
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
			
			String tab1 = "INSERT INTO Proveedores(Id, Nombre) VALUE(";
			
			String[] arguments0 = {"AAAA", "Prove Piezas"};
			String[] arguments1 = {"AAAB", "Proveedor 2"};
			String[] arguments2 = {"AAAC", "Piezas Anonimas"};
			String[] arguments3 = {"AAAD", "Clavos y Tornillso"};
			String[] arguments4 = {"AAAE", "Despiezados"};
			
			insertData(name0, tab1, arguments0,conexion);
			insertData(name0, tab1, arguments1, conexion);
			insertData(name0, tab1, arguments2,conexion);
			insertData(name0, tab1, arguments3,conexion);
			insertData(name0, tab1, arguments4, conexion);
			
			String tab2 = "INSERT INTO Piezas(Codigo, Nombre) VALUE(";
			
			String[] arguments5 = {"1", "Juntas"};
			String[] arguments6 = {"2", "Clavos"};
			String[] arguments7 = {"3", "Juntas"};
			String[] arguments8 = {"4", "Tornillos"};
			String[] arguments9 = {"5", "Tuercas"};
			
			insertData(name0, tab2, arguments5, conexion);
			insertData(name0, tab2, arguments6, conexion);
			insertData(name0, tab2, arguments7, conexion);
			insertData(name0, tab2, arguments8, conexion);
			insertData(name0, tab2, arguments9, conexion);
			
			String tab3 = "INSERT INTO Suministra(CodigoPieza, IdProveedor, Precio) VALUE(";
			
			String[] arguments10 = {"1", "AAAA", "12"};
			String[] arguments11 = {"2", "AAAB", "15"};
			String[] arguments12 = {"3", "AAAC", "11"};
			String[] arguments13 = {"4", "AAAD", "10"};
			String[] arguments14 = {"5", "AAAE", "9"};
			
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
