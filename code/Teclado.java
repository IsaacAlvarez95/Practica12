package code;

public class Teclado {

    private Palabra palabra; //Palabra del intento actual
    int posicion = 0; //Posicion actual

    public Teclado() {
        palabra = new Palabra();
    }

    //Agrega un caracter
    public void addCaracter(char c) {
        if(posicion >= 0 && posicion < 5){
            palabra.addCaracter(c);
            posicion++;
        }
    }

    public void addCaracter(int id , char c) {
        if(id >= 0 && id < 5){
            palabra.setCaracter(id, c);
        }
    }

    //Elimina el ultimo caracter
    public void deleteCaracter() {
        if(posicion > 0){
            palabra.setCaracter(posicion - 1, '\u0000');
            posicion--;
        }
    }

    //Envia una palabra
    public Palabra enviarPalabra() {
        Palabra p = palabra;
        palabra = new Palabra();
        posicion = 0;
        return p;
    }

    //Regresa la palabra
    public Palabra getPalabra(){
        return palabra;
    }

    //Regresa la posicion
    public int getPosicion() {
        return posicion;
    }

     @Override
    public String toString() {
        return palabra.toString();
     }

}
