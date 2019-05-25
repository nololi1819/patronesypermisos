import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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
			String rutaLog = "C:\\datos\\psp06.txt";
			File log = new File(rutaLog); //ruta tiene que estar creada
			if(!log.exists() &&  !log.createNewFile()) {//si no se puede crear el log
				System.out.println("no se ha podido crear el log");
			}
			
			fh = new FileHandler(rutaLog, true);//creo el handler para el log
			
			//asigno un formato de texto al handler
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			
			logger.addHandler(fh);//asigno el handler para poder recibir los mensajes de log
			logger.setUseParentHandlers(true); //  visualizar por pantalla
			logger.setLevel(Level.ALL);// guardar todos los logs
			logger.log(Level.INFO, new Date() + " | Programa iniciado");//primer mensaje de log			
		} catch (SecurityException | IOException e) {
			logger.log(Level.WARNING, e.getMessage());			
		}
	}

	/*
	 * Solicita el nombre del usuario que va a utilizar la aplicaci�n. El login
	 * tiene una longitud de 8 caracteres y est� compuesto �nicamente por letras
	 * min�sculas.
	 */
	private static void pedirLogin() {
		Pattern loginPattern = Pattern.compile("[a-z]{8}");
		String usuarioLogin;
		
		while (true) {//lo pido hasta que cumpla el patr�n
			System.out.println("Introduzca su nombre de usuario");
			 usuarioLogin = sc.nextLine();
			 
			if (loginPattern.matcher(usuarioLogin).matches())//si cumple patr�n salgo
				break;
			
			// si no he salido, no cumple el patr�n, pido de nuevo el nombre de usuario
			System.out.println("El nombre de usuario debe de tener 8 caracteres en min�sculas");
			logger.log(Level.WARNING, new Date() + " | nombre de usuario incorrecto.  | Introducido  "   + usuarioLogin );			
		}
		logger.log(Level.INFO, new Date() + " | Login de usuario "  + usuarioLogin );			
	}

	/*
	 * Solicita al usuario el nombre de un fichero que quiere mostrar. El nombre del
	 * fichero es como m�ximo de 8 caracteres y tiene una extensi�n de 3 caracteres.
	 */
	private static void pedirFichero() {
		// Explicaci�n del patr�n
		// [a-zA-Z]{8} : hasta ocho caracteres en may�sculas o min�sculas
		// [\\.] : un punto
		// [a-z]{3} : 3 caracteres min�sculas
		Pattern filePattern = Pattern.compile("[a-zA-Z]{1,8}[\\.][a-z]{3}");

		while (true) { // mientras no cumpla el patr�n, vuelvo a pedir el nombre del fichero y
						// extensi�n
			System.out.println("Introduzca el fichero que quiere mostrar");
			ficheroBuscar = sc.nextLine();
			
			if (filePattern.matcher(ficheroBuscar).matches())//si cumple el patr�n salgo
				break;
			
			System.out.println(//si no cumple el patr�n muestro aviso y vuelvo a pedirlo
					"El nombre del fichero tiene que tener un nombre de hasta 8 caracteres y extensi�n de 3 caracteres");
			logger.log(Level.WARNING, new Date() + " | fichero incorrecto.  | Introducido "  + ficheroBuscar );	
		}
	}

	/*
	 * Visualiza en pantalla el contenido del fichero.
	 */
	private static void visualizarContenidoFichero() {

		// si el fichero cumple los requisitos
		BufferedReader br;
		try {
			//buffer y reader para leer del fichero
			br = new BufferedReader(new FileReader(directory + File.separator + ficheroBuscar));
			String linea;
			
			logger.log(Level.INFO, new Date() + " | leyendo fichero  "  + ficheroBuscar );	//log
			
			while ((linea = br.readLine()) != null)//leo el fichero l�nea a l�nea
				System.out.println(linea);			
			
		} catch (FileNotFoundException e1) {//si fichero no encontrado
			logger.log(Level.WARNING, new Date() + " |   "  + ficheroBuscar  + " no encontrado");	
			logger.log(Level.WARNING, new Date() + " |   "  +e1.getMessage());	
		} catch (IOException e) {//si error de entrada y salida 
			logger.log(Level.WARNING, new Date() + " | error al leer el fichero  "  + ficheroBuscar );
			logger.log(Level.WARNING, new Date() + " |   "  +e.getMessage());
		}

	}

	private static void crearFicherosLectura() {
		// pongo un par de ficheros en el directorio C:/datos
		String contenido = "En un lugar de la Mancha,\rde cuyo nombre no quiero acordarme,\r"
				+ "no ha mucho tiempo que viv�a un hidalgo de los de lanza en astillero,\r"
				+ "adarga antigua, roc�n flaco y galgo corredor.";

		if (!directory.exists()) { // si el directorio no existe intento crearlo
			if (!directory.mkdirs()) {
				logger.log(Level.SEVERE, new Date() + " | No se ha podido crear el directorio  "  + directory );
				return;
			}
		}

		BufferedWriter bw = null;
		String nuevoFicheroNombre = "fichero"; // empiezo por un nombre de 7 caracteres, para tener 3 ficheros con
												// nombre de 7,8,9 caracteres
		for (int i = 0; i < 3; i++) {
			File ficheroCrear = new File(directory + File.separator + nuevoFicheroNombre + ".txt");
			nuevoFicheroNombre += i;//para el siguiente fichero nombre + 0/1/2
			if (ficheroCrear.exists()) {//si fichero existe pruebo con el siguiente nombre
				logger.log(Level.INFO, new Date() + " | el fichero ya existe  "  + ficheroCrear );
				continue; // si el fichero ya existe contin�o
			}
				
			try {// creo el buffer con el contenido, y guardo los ficheros
				bw = new BufferedWriter(new FileWriter(ficheroCrear));
				bw.write(contenido);//el contenido es id�ntico
				bw.flush();				
			} catch (IOException e) {//logs con excepciones de entrada/salida
				logger.log(Level.SEVERE, new Date() + " | Error al crear/escribir en  el fichero  "  + ficheroCrear );
				logger.log(Level.WARNING, new Date() + " |   "  +e.getMessage());
			}

		}

		if (bw != null) {//cierro el bufferWriter 
			try {
				bw.close();
			} catch (IOException e) {//logs
				logger.log(Level.SEVERE, new Date() + " | Error al cerrar el  buffer  "  );		
				logger.log(Level.WARNING, new Date() + " |   "  +e.getMessage());
			}
		}
	}
	
	/*
	 * Utilizo este m�todo para eliminar el lock del fichero
	 */
	private static void cerrarLog() {	
		//hago esperar unos segundos al m�todo, sino se cierra el log antes de terminar de escribir el contenido del fichero
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			logger.log(Level.INFO, new Date() + " | no se ha podido esperar a cerrar el log " );
		}
		logger.log(Level.INFO, new Date() + " | app finalizada  " );
		fh.close();
		logger.removeHandler(fh);
	}

	public static void main(String[] args) {
		//ejecuci�n de los m�todos de la aplicaci�n
		app.iniciarLog();
		app.crearFicherosLectura();
		app.pedirLogin();
		app.pedirFichero();
		app.visualizarContenidoFichero();
		app.cerrarLog();

	}

}
