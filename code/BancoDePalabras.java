package code;

import java.io.*;
import java.util.*;

public class BancoDePalabras {

    public BancoDePalabras() {}


    //Lee un diccionario y regresa una palabra aleatoria de alli
    public String getPalabraRandom(){
        Random random = new Random();
        List<String> diccionario = leerConfiguracion();
        int palabraRandom = random.nextInt(diccionario.size());
        return diccionario.get(palabraRandom).toUpperCase();
    }

    //Verifica si una palabra esta en el diccionario
    public boolean isPalabraInDiccionario(String palabra){
        List<String> diccionario = leerConfiguracion();
        for(int i = 0; i < diccionario.size(); i++){
            if(diccionario.get(i).equalsIgnoreCase(palabra)){
                return true;
            }
        }
        return false;
    }

    // Metodo para leer desde un archivo de texto
    public static List<String> leerConfiguracion() {
        String nombreArchivo = "code/Diccionario";
        List<String> configuracion = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nombreArchivo));
            String linea;
            while ((linea = reader.readLine()) != null) {
                configuracion.add(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el archivo: " + e.getMessage());
                }
            }
        }
        return configuracion;
    }

}
