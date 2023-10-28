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
			
			String tab1 = "INSERT INTO Productos(Codigo, Nombre, Precio) VALUE(";
			
			String[] arguments0 = {"1", "Microondas", "200"};
			String[] arguments1 = {"2", "Camiseta", "16"};
			String[] arguments2 = {"3", "Detergente", "18"};
			String[] arguments3 = {"4", "Zapatos", "45"};
			String[] arguments4 = {"5", "Pantalones", "60"};
			
			insertData(name0, tab1, arguments0, conexion);
			insertData(name0, tab1, arguments1, conexion);
			insertData(name0, tab1, arguments2, conexion);
			insertData(name0, tab1, arguments3, conexion);
			insertData(name0, tab1, arguments4, conexion);
			
			String tab2 = "INSERT INTO Cajeros(Codigo, NomApels) VALUE(";
			
			String[] arguments5 = {"101", "Maria Lacama"};
			String[] arguments6 = {"102", "Olga Niza"};
			String[] arguments7 = {"103", "Yaiza Latorre"};
			String[] arguments8 = {"104", "Jaime Bulto"};
			String[] arguments9 = {"105", "Belen tejas"};
			
			insertData(name0, tab2, arguments5,conexion);
			insertData(name0, tab2, arguments6, conexion);
			insertData(name0, tab2, arguments7,conexion);
			insertData(name0, tab2, arguments8,conexion);
			insertData(name0, tab2, arguments9, conexion);
			
			String tab3 = "INSERT INTO Maquinas_Registradoras(Codigo, Piso) VALUE(";
			
			String[] arguments10 = {"201", "1"};
			String[] arguments11 = {"202", "1"};
			String[] arguments12 = {"203", "2"};
			String[] arguments13 = {"204", "3"};
			String[] arguments14 = {"205", "2"};
			
			insertData(name0, tab3, arguments10,conexion);
			insertData(name0, tab3, arguments11, conexion);
			insertData(name0, tab3, arguments12,conexion);
			insertData(name0, tab3, arguments13,conexion);
			insertData(name0, tab3, arguments14, conexion);
			
			String tab4 = "INSERT INTO Venta(Cajero, Maquina, Producto) VALUE(";
			
			String[] arguments15 = {"101", "201", "1"};
			String[] arguments16 = {"102", "202", "2"};
			String[] arguments17 = {"103", "203", "3"};
			String[] arguments18 = {"104", "204", "4"};
			String[] arguments19 = {"105", "205", "5"};
			
			insertData(name0, tab4, arguments15, conexion);
			insertData(name0, tab4, arguments16, conexion);
			insertData(name0, tab4, arguments17, conexion);
			insertData(name0, tab4, arguments18, conexion);
			insertData(name0, tab4, arguments19, conexion);
			
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
