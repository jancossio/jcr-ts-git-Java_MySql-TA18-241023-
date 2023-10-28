import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Ejercicio9 {
	public static void main(String[] args){
		
		String name0 = "Ejercicio9";
		String name1 = "Facultad";
		String name2 = "Investigadores";
		String name3 = "Equipos";
		String name4 = "Reserva";
		
		Connection conexion = mySQLConnection("root","P1gue0N$","");
		if(conexion != null) {
			createDB(name0, conexion);
			
			String instFacl = "(Codigo int, Nombre VARCHAR(100), PRIMARY KEY(Codigo))";
			String instInvs = " (DNI VARCHAR(8), NomApels VARCHAR(255), Facultad int, "
							+ "FOREIGN KEY(Facultad) references Facultad(Codigo) on delete cascade on update cascade, PRIMARY KEY(DNI))";
			String instEqps = "(NumSerie VARCHAR(4), Nombre VARCHAR(100), Facultad int, "
							+ "FOREIGN KEY(Facultad) references Facultad(Codigo) on delete cascade on update cascade, PRIMARY KEY(NumSerie))";
			String instResr = "(DNI VARCHAR(8), NumSerie VARCHAR(4), Comienzo date, Fin date, FOREIGN KEY(DNI) references Investigadores(DNI) on delete cascade on update cascade,"
							+ "	FOREIGN KEY(NumSerie) references Equipos(NumSerie) on delete cascade on update cascade,"
							+ "	PRIMARY KEY(DNI, NumSerie))";
			
			
			createTable(name0, name1, instFacl, conexion);
			createTable(name0, name2, instInvs, conexion);
			createTable(name0, name3, instEqps, conexion);
			createTable(name0, name4, instResr, conexion);
			
			String tab1 = "INSERT INTO Facultad(Codigo, Nombre) VALUE(";
			
			String[] arguments0 = {"1", "Cervantes"};
			String[] arguments1 = {"2", "Companys"};
			String[] arguments2 = {"3", "Lope de Vega"};
			String[] arguments3 = {"4", "Shakesphere"};
			String[] arguments4 = {"5", "Neruda"};
			
			insertData(name0, tab1, arguments0, conexion);
			insertData(name0, tab1, arguments1, conexion);
			insertData(name0, tab1, arguments2, conexion);
			insertData(name0, tab1, arguments3, conexion);
			insertData(name0, tab1, arguments4, conexion);
			
			String tab2 = "INSERT INTO Investigadores(DNI, NomApels, Facultad) VALUE(";
			
			String[] arguments5 = {"ASDF56TH", "Alberto Umberto", "1"};
			String[] arguments6 = {"IKOL78UJ", "Leire Marques", "2"};
			String[] arguments7 = {"ERFD73CD", "Carmen Perez", "3"};
			String[] arguments8 = {"TGHY22JU", "Africa Palomo", "4"};
			String[] arguments9 = {"MNBV87HH", "Abril Lopez", "5"};
			
			insertData(name0, tab2, arguments5, conexion);
			insertData(name0, tab2, arguments6, conexion);
			insertData(name0, tab2, arguments7, conexion);
			insertData(name0, tab2, arguments8, conexion);
			insertData(name0, tab2, arguments9, conexion);
			
			String tab3 = "INSERT INTO Equipos(NumSerie, Nombre ,Facultad) VALUE(";
			
			String[] arguments10 = {"AAAA", "Equipo_A", "1"};
			String[] arguments11 = {"AAAB", "Equipo_B", "2"};
			String[] arguments12 = {"AAAC", "Equipo_C", "3"};
			String[] arguments13 = {"AAAD", "Equipo_D", "4"};
			String[] arguments14 = {"AAAE", "Equipo_E", "5"};
			
			insertData(name0, tab3, arguments10, conexion);
			insertData(name0, tab3, arguments11, conexion);
			insertData(name0, tab3, arguments12, conexion);
			insertData(name0, tab3, arguments13, conexion);
			insertData(name0, tab3, arguments14, conexion);
			
			String tab4 = "INSERT INTO Reserva(DNI, NumSerie, Comienzo, Fin) VALUE(";
			
			String[] arguments15 = {"ASDF56TH", "AAAA", "2023-10-20", "2023-10-20"};
			String[] arguments16 = {"IKOL78UJ", "AAAB", "2023-10-20", "2023-10-20"};
			String[] arguments17 = {"ERFD73CD", "AAAC", "2023-10-20", "2023-10-20"};
			String[] arguments18 = {"TGHY22JU", "AAAD", "2023-10-20", "2023-10-20"};
			String[] arguments19 = {"MNBV87HH", "AAAE", "2023-10-20", "2023-10-20"};
			
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
