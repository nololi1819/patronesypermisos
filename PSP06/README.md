# Patrones y políticas de acceso
Utilización de patrones y políticas de acceso en java.

## Instalación 
Importar proyecto en IDE de Java

Eclipse:  
![ImportaciónEclipse](../doc/1.jpg)

Netbeans:  
![ImportaciónNetbeans](../doc/2.jpg)


## Ejemplo de uso 
Ejecutar el programa como aplicación de java (genera los documentos de lectura automáticamente), en eclipse o consola  por el fichero .jar e introducir los datos que se solicitan:   
![Execute](../doc/3.jpg)  
Comprobar que se han creado los ficheros (log y fichero de lectura) 
![Ficheros creados](../doc/4.jpg)   
 
## Configuración políticas
Compilar GetProps y generar el .jar:   
![GetProps](../doc/5.jpg)  
Generación de claves 
![Generación de claves](../doc/6.jpg)   
Firma del .jar 
![Firma del jar](../doc/7.jpg)
Exportar certificado
![Certificado](../doc/8.jpg)
Importar certificado en proyecto
![Importar certificado](../doc/9.jpg)
Configuración de java.policy en el policytool
![java.policy](../doc/10.jpg)
Agregar políticas
![java.policy](../doc/11.jpg)
Ejecución (he probado a darle diferentes permisos, todos los permisos, cmd como administrador, pero siempre da un error de permisos, diferente según el caso)
![security manager](../doc/12.jpg)
 

## Configuración de desarrollo
Requiere instalación de Java 8, y de un navegador web. 

## META
ue57656@edu.xunta.es
Distributed under the CreativeCommons by-nc license. See https://creativecommons.org/licenses/by-nc/2.0/es/  for more information.
