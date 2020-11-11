package App;

import java.util.Scanner;

import Modelo.AccesoBD;

public class App {
	private static boolean exit = false;
	// private static String eleccion = "";
	private static Scanner sc = new Scanner(System.in);

	private static void mostrarMenu() {
		try {

			System.out.println("-------------------------");
			System.out.println("Bienvenido a la gestión de 'bdfutbol'.");
			System.out.println("¿Qué quiere hacer? \n");
			System.out.println("1: Listar equipos");
			System.out.println("2: Insertar un equipo");
			System.out.println("3: Borrar un equipo");
			System.out.println("4: Modificar un equipo");
			System.out.println("5: Utilizar procedimientos");
			System.out.println("6: Cerrar aplicación");
			System.out.println("");
			String eleccion = "";
			eleccion = sc.nextLine();

			switch (eleccion) {
			case "1":
				mostrarSubmenu("listado");
				break;
			case "2":
				mostrarSubmenu("insercion");
				break;
			case "3":
				mostrarSubmenu("borrado");
				break;
			case "4":
				mostrarSubmenu("modificacion");
				break;
			case "5":
				mostrarSubmenuProcedimientos();
				break;
			case "6": {
				exit = true;
				System.out.println("GRACIAS");
				break;
			}
			default:
				exit = true;
				System.out.println("GRACIAS");
				break;
			}

		} catch (Exception ex) {
			System.out.println("Volvio a ocurrir");
		}

	}

	private static void mostrarSubmenuProcedimientos() {

		System.out.println("");
		System.out.println("Procedimientos disponibles: ");
		System.out.println("1: Insertar un equipo");
		System.out.println("2: Listar contratos según DNI");
		System.out.println("3: Futbolistas activos en un equipo");
		System.out.println("4: Cantidad de meses jugados totales por un jugador (DNI/NIE)");
		System.out.println("5: Cancelar operación");
		String eleccion = sc.nextLine();

		switch (eleccion) {
		case "1": {
			mostrarSubmenu("procedimiento_insertar");
			break;
		}
		case "2": {
			mostrarSubmenu("procedimiento_contratos_dni");
			break;
		}
		case "3": {
			mostrarSubmenu("procedimiento_contratos_activos");
			break;
		}
		case "4": {
			mostrarSubmenu("procedimiento_meses_totales");
			break;
		}
		case "5": {
			break;
		}
		default:
			break;
		}

	}

	private static void mostrarSubmenu(String operacion) {

		System.out.println("");
		System.out.println("¿Que base de datos?");
		System.out.println("1: MySQL");
		if(!operacion.contains("procedimiento")) {
			System.out.println("2: Access");	
		}
		System.out.println("3: SQLServer");
		System.out.println("4: Cancelar operación");
		String eleccion = sc.nextLine();

		switch (eleccion) {
		case "1": {
			switch (operacion) {
				case "listado": {
					AccesoBD.listarEquiposMySQL();
					break;
				}
				case "insercion": {
					AccesoBD.insertarEquipoMySQL();
					break;
				}
				case "borrado": {
					AccesoBD.listarEquiposMySQL();
					System.out.println("¿Qué equipo quieres borrar? (0 para cancelar)");
					String cod = sc.nextLine();
					AccesoBD.eliminarEquipoMySQL(cod);
					break;
				}
				case "modificacion": {
					AccesoBD.listarEquiposMySQL();
					System.out.println("¿Qué equipo quieres modificar? \n");
					String cod = sc.nextLine();
					AccesoBD.modificarEquipoMySQL(cod);
					break;
				}
				case "procedimiento_insertar": {
					AccesoBD.insertarEquipoProcedimientoMySQL();
					break;
				}
	
				case "procedimiento_contratos_dni": {
					AccesoBD.contratosPorDNIProcedimientoMySQL();
					break;
				}
				case "procedimiento_contratos_activos":{
					AccesoBD.jugadoresActivosProcedimientoMySQL();
					break;
				}
				case "procedimiento_meses_totales":{
					AccesoBD.mesesTotalesProcedimientoMySQL();
				}
			}
			break;
		}
		case "2": {
			switch (operacion) {
			case "listado": {
				AccesoBD.listarEquiposAccess();
				break;
			}
			case "insercion": {
				AccesoBD.insertarEquipoAccess();
				break;
			}
			case "borrado": {
				AccesoBD.listarEquiposAccess();
				System.out.println("¿Qué equipo quieres borrar? (0 para cancelar)");
				String cod = sc.nextLine();
				AccesoBD.eliminarEquipoAccess(cod);;
				break;
			}
			case "modificacion": {
				AccesoBD.listarEquiposAccess();
				System.out.println("¿Qué equipo quieres modificar? \n");
				String cod = sc.nextLine();
				AccesoBD.modificarEquipoAccess(cod);
				break;
			}
			default:
				break;
			}
			break;
		}
		case "3": {
			switch (operacion) {
				case "listado": {
					AccesoBD.listarEquiposSqlServer();
					break;
				}
				case "insercion": {
					AccesoBD.insertarEquipoSQLServer();
					break;
				}
				case "borrado": {
					AccesoBD.listarEquiposSqlServer();
					System.out.println("¿Qué equipo quieres borrar? (0 para cancelar)");
					String cod = sc.nextLine();
					AccesoBD.eliminarEquipoSQLServer(cod);
					break;
				}
				case "modificacion": {
					AccesoBD.listarEquiposSqlServer();
					System.out.println("¿Qué equipo quieres modificar? \n");
					String cod = sc.nextLine();
					AccesoBD.modificarEquipoSQLServer(cod);
					break;
				}
				case "procedimiento_insertar": {
					AccesoBD.insertarEquipoProcedimientoSQLServer();
					break;
				}
				
				case "procedimiento_contratos_dni":{
					AccesoBD.contratosPorDNIProcedimientoSQLServer();
					break;
				}	
				case "procedimiento_contratos_activos":{
					AccesoBD.jugadoresActivosProcedimientoSQLServer();
					break;
				}
				case "procedimiento_meses_totales":{
					AccesoBD.mesesTotalesProcedimientoSQLServer();
				}
			}
			break;
		}
		case "4": {
			break;
		}
		default:
			break;
		}
	}

	public static void main(String[] args) {

		while (exit == false) {
			mostrarMenu();
		}

	}
}
