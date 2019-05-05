import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

public class app {
	private static Scanner sc = new Scanner(System.in); // clase para pedir datos por teclado
	private static String ficheroBuscar; //fichero que quiero buscar
	private static File directory = new File("C:\\datos"); //directorio datos

	private static Logger logger;
	private static FileHandler fh;

	/*
	 * Inicio el log
	 */
	private static void iniciarLog() {
		logger = Logger.getLogger("app_PSP06_logger");
		try {
			fh = new FileHandler("c:\\logs\\psp06.log", true);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			logger.addHandler(fh);
			logger.setUseParentHandlers(false); // no visualizar por pantalla
			logger.setLevel(Level.ALL);// guardar todo
			logger.log(Level.INFO, new Date() + " | Programa iniciado");			
		} catch (SecurityException | IOException e) {
			logger.log(Level.WARNING, e.getMessage());			
		}
	}

	/*
	 * Solicita el nombre del usuario que va a utilizar la aplicación. El login
	 * tiene una longitud de 8 caracteres y está compuesto únicamente por letras
	 * minúsculas.
	 */
	private static void pedirLogin() {
		Pattern loginPattern = Pattern.compile("[a-z]{8}");
		String usuarioLogin;
		
		while (true) {
			System.out.println("Introduzca su nombre de usuario");
			 usuarioLogin = sc.nextLine();
			if (loginPattern.matcher(usuarioLogin).matches())
				break;
			// si no he salido, no cumple el patrón, pido de nuevo el nombre de usuario
			System.out.println("El nombre de usuario debe de tener 8 caracteres en minúsculas");
			logger.log(Level.WARNING, new Date() + " | nombre de usuario incorrecto.  | Introducido "   + usuarioLogin );			
		}
		logger.log(Level.INFO, new Date() + " | Login de usuario "  + usuarioLogin );			
	}

	/*
	 * Solicita al usuario el nombre de un fichero que quiere mostrar. El nombre del
	 * fichero es como máximo de 8 caracteres y tiene una extensión de 3 caracteres.
	 */
	private static void pedirFichero() {
		// Explicación del patrón
		// [a-zA-Z]{8} : hasta ocho caracteres en mayúsculas o minúsculas
		// [\\.] : un punto
		// [a-z]{3} : 3 caracteres minúsculas
		Pattern filePattern = Pattern.compile("[a-zA-Z]{1,8}[\\.][a-z]{3}");

		while (true) { // mientras no cumpla el patrón, vuelvo a pedir el nombre del fichero y
						// extensión
			System.out.println("Introduzca el fichero que quiere mostrar");
			ficheroBuscar = sc.nextLine();
			if (filePattern.matcher(ficheroBuscar).matches())
				break;
			System.out.println(
					"El nombre del fichero tiene que tener un nombre de hasta 8 caracteres y extensión de 3 caracteres");
			logger.log(Level.WARNING, new Date() + " | fichero incorrecto.  | Introducido"  + ficheroBuscar );	
		}
	}

	/*
	 * Visualiza en pantalla el contenido del fichero.
	 */
	private static void visualizarContenidoFichero() {

		// si el fichero cumple los requisitos
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(directory + File.separator + ficheroBuscar));
			String linea;
			logger.log(Level.INFO, new Date() + " | leyendo fichero  "  + ficheroBuscar );	
			while ((linea = br.readLine()) != null)
				System.out.println(linea);			
		} catch (FileNotFoundException e1) {//si fichero no encontrado
			logger.log(Level.WARNING, new Date() + " |   "  + ficheroBuscar  + " no encontrado");	
			logger.log(Level.WARNING, new Date() + " |   "  +e1.getMessage());	
		} catch (IOException e) {
			logger.log(Level.WARNING, new Date() + " | error al leer el fichero  "  + ficheroBuscar );
			logger.log(Level.WARNING, new Date() + " |   "  +e.getMessage());
		}

	}

	private static void crearFicherosLectura() {
		// pongo un par de ficheros en el directorio C:/datos
		String contenido = "En un lugar de la Mancha,\rde cuyo nombre no quiero acordarme,\r"
				+ "no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero,\r"
				+ "adarga antigua, rocín flaco y galgo corredor.";

		if (!directory.exists()) { // si el directorio no existe intento crearlo
			if (!directory.mkdirs()) {
				logger.log(Level.SEVERE, new Date() + " | No se ha podido crear el directorio  "  + directory );
				return;
			}
		}

		BufferedWriter bw = null;
		String nuevoFicheroNombre = "ficheros"; // empiezo por un nombre de 7 caracteres, para tener 3 ficheros con
												// nombre de 7,8,9 caracteres
		for (int i = 0; i < 3; i++) {
			File ficheroCrear = new File(directory + File.separator + nuevoFicheroNombre + ".txt");
			nuevoFicheroNombre += i;
			if (ficheroCrear.exists()) {
				logger.log(Level.INFO, new Date() + " | el fichero ya existe  "  + ficheroCrear );
				continue; // si el fichero ya existe continúo
			}
				
			try {// creo el buffer con el contenido, y guardo
				bw = new BufferedWriter(new FileWriter(ficheroCrear));
				bw.write(contenido);
				bw.flush();				
			} catch (IOException e) {
				logger.log(Level.SEVERE, new Date() + " | Error al crear/escribir en  el fichero  "  + ficheroCrear );
				logger.log(Level.WARNING, new Date() + " |   "  +e.getMessage());
			}

		}

		if (bw != null) {
			try {
				bw.close();
			} catch (IOException e) {
				logger.log(Level.SEVERE, new Date() + " | Error al cerrar el  buffer  "  );		
				logger.log(Level.WARNING, new Date() + " |   "  +e.getMessage());
			}
		}
	}
	
	/*
	 * Utilizo este método para eliminar el lock del fichero
	 */
	private static void cerrarLog() {		
		logger.removeHandler(fh);
		fh.close();
		logger.log(Level.INFO, new Date() + " | app finalizada  " );
	}

	public static void main(String[] args) {
		app.iniciarLog();
		app.crearFicherosLectura();
		app.pedirLogin();
		app.pedirFichero();
		app.visualizarContenidoFichero();
		app.cerrarLog();

	}

}
