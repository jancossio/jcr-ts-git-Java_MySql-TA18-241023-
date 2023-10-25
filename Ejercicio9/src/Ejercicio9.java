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
			String instResr = "(Cajero int, Maquina int, Producto int, FOREIGN KEY(Cajero) references Cajeros(Codigo) on delete cascade on update cascade,"
							+ "	FOREIGN KEY(Maquina) references Maquinas_Registradoras(Codigo) on delete cascade on update cascade,"
							+ "	FOREIGN KEY(Producto) references Productos(Codigo) on delete cascade on update cascade,PRIMARY KEY(Cajero, Maquina, Producto))";
			
			
			createTable(name0, name1, instFacl, conexion);
			createTable(name0, name2, instInvs, conexion);
			createTable(name0, name3, instEqps, conexion);
			createTable(name0, name4, instResr, conexion);
			
			insertDataFacl(name0, "1", "Cervantes", conexion);
			insertDataFacl(name0, "2", "Companys", conexion);
			insertDataFacl(name0, "3", "Lope de Vega", conexion);
			insertDataFacl(name0, "4", "Shakesphere", conexion);
			insertDataFacl(name0, "5", "Neruda", conexion);
			
			insertDataInvs(name0, "ASDF56TH", "Alberto Umberto", "1", conexion);
			insertDataInvs(name0, "IKOL78UJ", "Leire Marques", "2", conexion);
			insertDataInvs(name0, "ERFD73CD", "Carmen Perez", "3", conexion);
			insertDataInvs(name0, "TGHY22JU", "Africa Palomo", "4", conexion);
			insertDataInvs(name0, "MNBV87HH", "Abril Lopez", "5", conexion);
			
			insertDataEqps(name0, "AAAA", "Equipo_A", "1", conexion);
			insertDataEqps(name0, "AAAB", "Equipo_B", "2", conexion);
			insertDataEqps(name0, "AAAC", "Equipo_C", "3", conexion);
			insertDataEqps(name0, "AAAD", "Equipo_D", "4", conexion);
			insertDataEqps(name0, "AAAE", "Equipo_E", "5", conexion);
			
			insertDataResr(name0, "ASDF56TH", "AAAA", "2023-10-20", "2023-10-20", conexion);
			insertDataResr(name0, "IKOL78UJ", "AAAB", "2023-10-20", "2023-10-20", conexion);
			insertDataResr(name0, "ERFD73CD", "AAAC", "2023-10-20", "2023-10-20", conexion);
			insertDataResr(name0, "TGHY22JU", "AAAD", "2023-10-20", "2023-10-20", conexion);
			insertDataResr(name0, "MNBV87HH", "AAAE", "2023-10-20", "2023-10-20", conexion);
			
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
	
	public static void insertDataFacl(String db, String codigo, String nombre, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Facultad(Codigo, Nombre) VALUE("
					+"\""+codigo+"\", "
					+"\""+nombre+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Facultad rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Facultad");
		}
	}
	
	public static void insertDataInvs(String db, String dni, String nomApels, String facultad, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Investigadores(DNI, NomApels, Facultad) VALUE("
					+"\""+dni+"\", "
					+"\""+nomApels+"\", "
					+"\""+facultad+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Investigadores rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Investigadores");
		}
	}
	
	public static void insertDataEqps(String db, String numSerie, String nombre, String facultad, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Equipos(NumSerie, Nombre ,Facultad) VALUE("
					+"\""+numSerie+"\", "
					+"\""+nombre+"\", "
					+"\""+facultad+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Equipos rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Equipos");
		}
	}
	
	public static void insertDataResr(String db, String dni, String numSerie, String comienzo, String fin, Connection conection) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb = conection.createStatement();
			stdb.executeUpdate(Querydb);
			
			String query = "INSERT INTO Reserva(DNI, NumSerie, Comienzo, Fin) VALUE("
					+"\""+dni+"\", "
					+"\""+numSerie+"\", "
					+"\""+comienzo+"\", "
					+"\""+fin+"\"); ";
			
			Statement st = conection.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla Reserva rellenada con exito");
		}catch(SQLException ex) {
			//Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error al rellenar la tabla Reserva");
		}
	}
}
