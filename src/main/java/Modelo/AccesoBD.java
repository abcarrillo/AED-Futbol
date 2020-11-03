package Modelo;

import java.sql.*;
import java.util.Scanner;

public class AccesoBD {

	private static String url = "jdbc:mysql://localhost:3306/bdfutbol";
	private static String user = "root";
	private static String pass = "";

	public static void listarEquiposMySQL() {
		try {
			Connection con = DriverManager.getConnection(url, user, pass);

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
			Connection con = DriverManager.getConnection(url, user, pass);
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
			sc.close();
			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void eliminarEquipoMySQL() {
		try {
			Connection con = DriverManager.getConnection(url, user, pass);

			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void insertarEquipoMySQL() {

		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(url, user, pass);
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
			sc.close();
			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void insertarEquipoProcedimientoMySQL() {

		Scanner sc = new Scanner(System.in);
		try {
			Connection con = DriverManager.getConnection(url, user, pass);
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

			sc.close();
			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
