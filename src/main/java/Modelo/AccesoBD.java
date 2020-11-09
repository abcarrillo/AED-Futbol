package Modelo;

import java.sql.*;
import java.util.Scanner;

import com.mysql.cj.xdevapi.Type;

public class AccesoBD {

	private static String urlSQL = "jdbc:mysql://localhost:3306/bdfutbol";
	private static String userSQL = "root";
	private static String passSQL = "";
	private static String urlSQLServer = "jdbc:sqlserver://DESKTOP-OB3TRNJ;DataBaseName=bdFutbol";
	private static String userSQLServer = "sa";
	private static String passSQLServer = "2020informatica";
	

	public static void listarEquiposMySQL() {
		try {
			Connection con = DriverManager.getConnection(urlSQL, userSQL, passSQL);

			String sql = "SELECT * FROM equipos INNER JOIN ligas ON ligas.codLiga = equipos.codLiga";
			PreparedStatement consulta = con.prepareStatement(sql);

			ResultSet resultado = consulta.executeQuery();
			System.out.println("");

			while (resultado.next()) {
				String codEquipo = resultado.getString("codEquipo");
				String nomEquipo = resultado.getString("nomEquipo");
				String nombreLiga = resultado.getString("nomLiga");
				String localidad = resultado.getString("localidad");
				int internacional = resultado.getInt("internacional");

				System.out.println("- " + codEquipo + " | " + nomEquipo + " | " + nombreLiga + " | " + localidad + " | "
						+ internacional);
			}
			System.out.println("");
			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}
	
	
	public static void listarEquiposSqlServer() {
		try {
			Connection con = DriverManager.getConnection(urlSQLServer, userSQLServer, passSQLServer);

			String sql = "SELECT * FROM equipos INNER JOIN ligas ON ligas.codLiga = equipos.codLiga";
			PreparedStatement consulta = con.prepareStatement(sql);

			ResultSet resultado = consulta.executeQuery();
			System.out.println("");

			while (resultado.next()) {
				String codEquipo = resultado.getString("codEquipo");
				String nomEquipo = resultado.getString("nomEquipo");
				String nombreLiga = resultado.getString("nomLiga");
				String localidad = resultado.getString("localidad");
				int internacional = resultado.getInt("internacional");

				System.out.println("- " + codEquipo + " | " + nomEquipo + " | " + nombreLiga + " | " + localidad + " | "
						+ internacional);
			}
			System.out.println("");
			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void modificarEquipoMySQL(String codEquipo) {
		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(urlSQL, userSQL, passSQL);
			String sql = "UPDATE `equipos` SET nomEquipo=?, codLiga=?, localidad=?, internacional=? WHERE codEquipo = "
					+ codEquipo;
			PreparedStatement consulta = con.prepareStatement(sql);

			String sql2 = "SELECT * FROM equipos WHERE codEquipo = " + codEquipo;
			PreparedStatement modificar = con.prepareStatement(sql2);
			ResultSet resultado = modificar.executeQuery();

			if (resultado.next()) {
				System.out.println("Nombre de equipo (Actual: " + resultado.getString("nomEquipo") + " )");
				String nomEquipo = sc.nextLine();
				System.out.println("Codigo de liga (Actual: " + resultado.getString("codLiga") + " )");
				String codLiga = sc.nextLine();
				System.out.println("Localidad (Actual: " + resultado.getString("localidad") + " )");
				String localidad = sc.nextLine();
				System.out.println("Internacional (si o no) (Actual: " + resultado.getString("internacional") + " )");
				String internacionalString = sc.nextLine();
				boolean internacional = false;

				if (internacionalString.toLowerCase().equals("si")) {
					internacional = true;
				}

				consulta.setString(1, nomEquipo);
				consulta.setString(2, codLiga);
				consulta.setString(3, localidad);
				consulta.setBoolean(4, internacional);
			}

			consulta.executeUpdate();
			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	public static void modificarEquipoSQLServer(String codEquipo) {
		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(urlSQLServer, userSQLServer, passSQLServer);
			String sql = "UPDATE equipos SET nomEquipo=?, codLiga=?, localidad=?, internacional=? WHERE codEquipo = "
					+ codEquipo;
			PreparedStatement consulta = con.prepareStatement(sql);

			String sql2 = "SELECT * FROM equipos WHERE codEquipo = " + codEquipo;
			PreparedStatement modificar = con.prepareStatement(sql2);
			ResultSet resultado = modificar.executeQuery();

			if (resultado.next()) {
				System.out.println("Nombre de equipo (Actual: " + resultado.getString("nomEquipo") + " )");
				String nomEquipo = sc.nextLine();
				System.out.println("Codigo de liga (Actual: " + resultado.getString("codLiga") + " )");
				String codLiga = sc.nextLine();
				System.out.println("Localidad (Actual: " + resultado.getString("localidad") + " )");
				String localidad = sc.nextLine();
				System.out.println("Internacional (si o no) (Actual: " + resultado.getString("internacional") + " )");
				String internacionalString = sc.nextLine();
				boolean internacional = false;

				if (internacionalString.toLowerCase().equals("si")) {
					internacional = true;
				}

				consulta.setString(1, nomEquipo);
				consulta.setString(2, codLiga);
				consulta.setString(3, localidad);
				consulta.setBoolean(4, internacional);
			}

			consulta.executeUpdate();
			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void eliminarEquipoMySQL(String codEquipo) {
		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(urlSQL, userSQL, passSQL);

			String sqlEquipo = "SELECT * FROM equipos WHERE codEquipo = " + codEquipo;
			PreparedStatement consultaEquipo = con.prepareStatement(sqlEquipo);
			ResultSet resultadoEquipo = consultaEquipo.executeQuery();

			String sqlContratos = "SELECT * FROM contratos WHERE codEquipo = " + codEquipo;
			PreparedStatement consultaContratos = con.prepareStatement(sqlContratos);
			ResultSet resultadoContratos = consultaContratos.executeQuery();

			boolean borrado = false;
			if (resultadoEquipo.next()) {
				if (resultadoContratos.isBeforeFirst() == false) {
					System.out.println("¿Seguro que quiere borrar este equipo? (si o no)");
					String eleccion = sc.nextLine();

					if (eleccion.toLowerCase().equals("si")) {
						borrado = true;
					}
				} else {
					System.out.println("Si quiere borrar este equipo, debe borrar estos contratos: \n");
					
					while (resultadoContratos.next()) {

						String codContrato = resultadoContratos.getString("codcontrato");
						String coddnionie = resultadoContratos.getString("coddnionie");
						String fechaInicio = resultadoContratos.getString("fechaInicio");
						String fechaFin = resultadoContratos.getString("fechaFin");
						String precioanual = resultadoContratos.getString("precioanual");
						String preciorecision = resultadoContratos.getString("preciorecision");

						System.out.println(" - " + codContrato + " | " + coddnionie + " | " + fechaInicio + " | "
								+ fechaFin + " | " + precioanual + " | " + preciorecision);
					}
					System.out.println("");
					System.out.println("¿Seguro que quiere borrar este equipo y estos contratos? (si o no)");
					String eleccion = sc.nextLine();

					if (eleccion.toLowerCase().equals("si")) {
						borrado = true;
					}
				}

			}

			if (borrado == true) {
				String sqlBorradoContratos = "DELETE FROM contratos WHERE codEquipo = " + codEquipo;
				PreparedStatement borradoContratos = con.prepareStatement(sqlBorradoContratos);
				borradoContratos.executeUpdate();

				String sqlBorradoEquipo = "DELETE FROM equipos WHERE codEquipo = " + codEquipo;
				PreparedStatement borradoEquipo = con.prepareStatement(sqlBorradoEquipo);
				borradoEquipo.executeUpdate();
			}

			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public static void eliminarEquipoSQLServer(String codEquipo) {
		Scanner sc = new Scanner(System.in);
		try {
			
			Connection con = DriverManager.getConnection(urlSQLServer, userSQLServer, passSQLServer);

			String sqlEquipo = "SELECT * FROM equipos WHERE codEquipo = " + codEquipo;
			PreparedStatement consultaEquipo = con.prepareStatement(sqlEquipo);
			ResultSet resultadoEquipo = consultaEquipo.executeQuery();

			String sqlContratos = "SELECT * FROM contratos WHERE codEquipo = " + codEquipo;
			PreparedStatement consultaContratos = con.prepareStatement(sqlContratos);
			ResultSet resultadoContratos = consultaContratos.executeQuery();

			boolean borrado = false;
			if (resultadoEquipo.next()) {
				if (resultadoContratos.isBeforeFirst() == false) {
					System.out.println("¿Seguro que quiere borrar este equipo? (si o no)");
					String eleccion = sc.nextLine();

					if (eleccion.toLowerCase().equals("si")) {
						borrado = true;
					}
				} else {
					System.out.println("Si quiere borrar este equipo, debe borrar estos contratos: \n");
					
					while (resultadoContratos.next()) {

						String codContrato = resultadoContratos.getString("codcontrato");
						String coddnionie = resultadoContratos.getString("coddnionie");
						String fechaInicio = resultadoContratos.getString("fechaInicio");
						String fechaFin = resultadoContratos.getString("fechaFin");
						String precioanual = resultadoContratos.getString("precioanual");
						String preciorecision = resultadoContratos.getString("preciorecision");

						System.out.println(" - " + codContrato + " | " + coddnionie + " | " + fechaInicio + " | "
								+ fechaFin + " | " + precioanual + " | " + preciorecision);
					}
					System.out.println("");
					System.out.println("¿Seguro que quiere borrar este equipo y estos contratos? (si o no)");
					String eleccion = sc.nextLine();

					if (eleccion.toLowerCase().equals("si")) {
						borrado = true;
					}
				}

			}

			if (borrado == true) {
				String sqlBorradoContratos = "DELETE FROM contratos WHERE codEquipo = " + codEquipo;
				PreparedStatement borradoContratos = con.prepareStatement(sqlBorradoContratos);
				borradoContratos.executeUpdate();

				String sqlBorradoEquipo = "DELETE FROM equipos WHERE codEquipo = " + codEquipo;
				PreparedStatement borradoEquipo = con.prepareStatement(sqlBorradoEquipo);
				borradoEquipo.executeUpdate();
			}

			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void insertarEquipoMySQL() {

		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(urlSQL, userSQL, passSQL);
			String sql = "INSERT INTO `equipos`(`nomEquipo`, `codLiga`, `localidad`, `internacional`) VALUES (?,?,?,?)";
			PreparedStatement consulta = con.prepareStatement(sql);

			System.out.println("Nombre de equipo: ");
			String nomEquipo = sc.nextLine();
			System.out.println("Codigo de liga: ");
			String codLiga = sc.nextLine();
			System.out.println("Localidad: ");
			String localidad = sc.nextLine();
			System.out.println("Internacional (si o no): ");
			String internacionalString = sc.nextLine();
			boolean internacional = false;

			if (internacionalString.toLowerCase().equals("si")) {
				internacional = true;
			}

			consulta.setString(1, nomEquipo);
			consulta.setString(2, codLiga);
			consulta.setString(3, localidad);
			consulta.setBoolean(4, internacional);

			consulta.executeUpdate();
			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public static void insertarEquipoSQLServer() {

		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(urlSQLServer, userSQLServer, passSQLServer);
			String sql = "INSERT INTO equipos (nomEquipo, codLiga, localidad, internacional) VALUES (?,?,?,?)";
			PreparedStatement consulta = con.prepareStatement(sql);

			System.out.println("Nombre de equipo: ");
			String nomEquipo = sc.nextLine();
			System.out.println("Codigo de liga: ");
			String codLiga = sc.nextLine();
			System.out.println("Localidad: ");
			String localidad = sc.nextLine();
			System.out.println("Internacional (si o no): ");
			String internacionalString = sc.nextLine();
			boolean internacional = false;

			if (internacionalString.toLowerCase().equals("si")) {
				internacional = true;
			}

			consulta.setString(1, nomEquipo);
			consulta.setString(2, codLiga);
			consulta.setString(3, localidad);
			consulta.setBoolean(4, internacional);

			consulta.executeUpdate();
			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void insertarEquipoProcedimientoMySQL() {

		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(urlSQL, userSQL, passSQL);
			CallableStatement consulta = con.prepareCall("call actividad2(?, ?, ?, ?, ?, ?)");

			System.out.println("Nombre de equipo: ");
			String nomEquipo = sc.nextLine();
			System.out.println("Codigo de liga: ");
			String codLiga = sc.nextLine();
			System.out.println("Localidad: ");
			String localidad = sc.nextLine();
			System.out.println("Internacional (si o no): ");
			String internacionalString = sc.nextLine();
			boolean internacional = false;

			consulta.setString(1, nomEquipo);
			consulta.setString(2, codLiga);
			consulta.setString(3, localidad);
			consulta.setBoolean(4, internacional);
			consulta.registerOutParameter(5, Types.TINYINT);
			consulta.registerOutParameter(6, Types.TINYINT);

			if (internacionalString.toLowerCase().equals("si")) {
				internacional = true;
			}

			consulta.execute();
			System.out.println("");
			System.out.println("La liga insertada existe: " + consulta.getString(5) + " | Estado de inserción: "
					+ consulta.getString(6) + " \n");

			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	public static void insertarEquipoProcedimientoSQLServer() {

		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(urlSQLServer, userSQLServer, passSQLServer);
			CallableStatement consulta = con.prepareCall("exec dbo.InsertaEquipo @nomEquipo = ?, @codLiga = ?, " +
			"@localidad = ?, @internacional = ?, @existeliga = ?,"
			+ "	@insercion = ?");

			System.out.println("Nombre de equipo: ");
			String nomEquipo = sc.nextLine();
			System.out.println("Codigo de liga: ");
			String codLiga = sc.nextLine();
			System.out.println("Localidad: ");
			String localidad = sc.nextLine();
			System.out.println("Internacional (si o no): ");
			String internacionalString = sc.nextLine();
			boolean internacional = false;

			consulta.setString(1, nomEquipo);
			consulta.setString(2, codLiga);
			consulta.setString(3, localidad);
			consulta.setBoolean(4, internacional);
			consulta.registerOutParameter(5, Types.TINYINT);
			consulta.registerOutParameter(6, Types.TINYINT);

			if (internacionalString.toLowerCase().equals("si")) {
				internacional = true;
			}

			consulta.execute();
			System.out.println("");
			System.out.println("La liga insertada existe: " + consulta.getString(5) + " | Estado de inserción: "
					+ consulta.getString(6) + " \n");

			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public static void contratosPorDNIProcedimientoMySQL() {

		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(urlSQL, userSQL, passSQL);
			CallableStatement consulta = con.prepareCall("CALL actividad1(?);");

			System.out.println("DNI del jugador: ");
			String dni = sc.nextLine();
			
			consulta.setString(1, dni);

			ResultSet resultado = consulta.executeQuery();
			System.out.println("");
			while (resultado.next()) {
	            String codcontrato = resultado.getString("codcontrato");
	            String nomEquipo = resultado.getString("nomEquipo");
	            String fechaInicio = resultado.getString("fechaInicio");
	            String fechaFin = resultado.getString("fechaFin");
	            String precioanual = resultado.getString("precioanual");
	            String preciorecision = resultado.getString("preciorecision");    
	            System.out.println("- "+ codcontrato+" | "+nomEquipo+" | "+fechaInicio+" | "+fechaFin+" | "+precioanual+" | "+preciorecision);
	        }

			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public static void contratosPorDNIProcedimientoSQLServer() {

		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(urlSQLServer, userSQLServer, passSQLServer);
			CallableStatement consulta = con.prepareCall("EXEC dbo.ListaContratos @DNINIE = ?");

			System.out.println("DNI del jugador: ");
			String dni = sc.nextLine();
			
			consulta.setString(1, dni);

			ResultSet resultado = consulta.executeQuery();
			System.out.println("");
			while (resultado.next()) {
	            String codcontrato = resultado.getString("codcontrato");
	            String nomEquipo = resultado.getString("nomEquipo");
	            String fechaInicio = resultado.getString("fechaInicio");
	            String fechaFin = resultado.getString("fechaFin");
	            String precioanual = resultado.getString("precioanual");
	            String preciorecision = resultado.getString("preciorecision");    
	            System.out.println("- "+ codcontrato+" | "+nomEquipo+" | "+fechaInicio+" | "+fechaFin+" | "+precioanual+" | "+preciorecision);
	        }

			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	public static void jugadoresActivosProcedimientoMySQL() {

		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(urlSQL, userSQL, passSQL);
			CallableStatement consulta = con.prepareCall(" CALL `actividad3`(?, ?, ?, ?, ?)");
			
			System.out.println("A continuacion, introduzca el codigo de equipo y el precio anual y de recision del contrato.");
			System.out.println("Saldran en pantalla los contratos activos de ese equipos y los MENORES a los precios anuales y de recision introducidos.");

			System.out.println("Codigo de equipo: ");
			String codEquipo = sc.nextLine();
			System.out.println("Precio anual: ");
			String precioanual = sc.nextLine();
			System.out.println("Precio recision: ");
			String preciorecision = sc.nextLine();
			
			consulta.setString(1, codEquipo);
			consulta.setString(2, precioanual);
			consulta.setString(3, preciorecision);

			consulta.execute();
			System.out.println("");
			System.out.println("Hay esta cantidad de contratos activos en el equipo introducido: " + consulta.getString(4) + " | De esos, hay esta cantidad con precios menores a los establecidos: "
					+ consulta.getString(5) + " \n");

			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	public static void jugadoresActivosProcedimientoSQLServer() {

		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(urlSQLServer, userSQLServer, passSQLServer);
			CallableStatement consulta = con.prepareCall("exec dbo.FutbolistasActivoEquipo @codEquipo = ?, @precioAnual = ?, " +
					"@precioRecision = ?, @futbolistasActivos = ?, @futbolistasActivos2 = ?");
			
			System.out.println("A continuacion, introduzca el codigo de equipo y el precio anual y de recision del contrato.");
			System.out.println("Saldran en pantalla los contratos activos de ese equipos y los MENORES a los precios anuales y de recision introducidos.");

			System.out.println("Codigo de equipo: ");
			String codEquipo = sc.nextLine();
			System.out.println("Precio anual: ");
			String precioanual = sc.nextLine();
			System.out.println("Precio recision: ");
			String preciorecision = sc.nextLine();
			
			consulta.setString(1, codEquipo);
			consulta.setString(2, precioanual);
			consulta.setString(3, preciorecision);
			consulta.registerOutParameter(4, Types.INTEGER);
			consulta.registerOutParameter(5, Types.INTEGER);

			consulta.execute();
			System.out.println("");
			System.out.println("Hay esta cantidad de contratos activos en el equipo introducido: " + consulta.getString(4) + " | De esos, hay esta cantidad con precios menores a los establecidos: "
					+ consulta.getString(5) + " \n");

			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public static void mesesTotalesProcedimientoMySQL() {

		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(urlSQL, userSQL, passSQL);

			String sql = "SELECT actividad4(?) as meses";
			PreparedStatement consulta = con.prepareStatement(sql);
			System.out.println("DNI del jugador: ");
			String dni = sc.nextLine();
			
			consulta.setString(1, dni);

			ResultSet resultado = consulta.executeQuery();
			if(resultado.next()) {
				System.out.println("");
				System.out.println("El jugador ha estado en activo un total de " + resultado.getString(1) + " meses");

			}
			
			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	public static void mesesTotalesProcedimientoSQLServer() {

		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(urlSQLServer, userSQLServer, passSQLServer);

			String sql = "SELECT dbo.MesesSegunDNIoNIE(?) as meses";
			PreparedStatement consulta = con.prepareStatement(sql);
			System.out.println("DNI del jugador: ");
			String dni = sc.nextLine();
			
			consulta.setString(1, dni);

			ResultSet resultado = consulta.executeQuery();
			if(resultado.next()) {
				System.out.println("");
				System.out.println("El jugador ha estado en activo un total de " + resultado.getString(1) + " meses");

			}
			
			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
