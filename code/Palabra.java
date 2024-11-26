package code;

public class Palabra {

    private char[] palabra; //Almacenara la palabra en un array de 5 caracteres

    public Palabra() {
        palabra = new char[5];
    }

    public Palabra(char[] palabra) {
        this.palabra = new char[5];
        setPalabra(palabra);
    }

    //Mandara la palabra completa
    public char[] getPalabra() {
        return palabra;
    }

    //Recibira una palabra completa
    public void setPalabra(char[] palabra) {
        for(int i = 0; i < this.palabra.length; i++){
            this.palabra[i] = palabra[i];
        }
    }

    //Solo regresara el caracter de una ID especifica
    public char getCaracter(int id){
        return palabra[id];
    }

    //Cambiara el caracter de una ID especifica
    public void setCaracter(int id, char caracter){
        palabra[id] = caracter;
    }

    //Añadira un caracter a la ultima posicion
    public void addCaracter(char caracter){
        for(int i = 0; i < palabra.length; i++){
            if(palabra[i] == '\u0000'){
                palabra[i] = caracter;
                i = palabra.length;
            }
        }
    }

    //Compara 2 palabras y regresa una matriz de booleanos
    public boolean[][] compararPalabras(Palabra palabra) {
        boolean[][] igualdades = new boolean[2][5]; //La primera Fila indicara caracteres iguales
                                                    //La segunda Fila indicara caracteres presentes
        boolean[] marcadas = new boolean[5];        //Marca los caracteres que ya han sido verificados

        //Se encarga de verificar solo los caracteres que estan en su lugar
        for (int i = 0; i < this.palabra.length; i++) {
            igualdades[0][i] = this.palabra[i] == palabra.getCaracter(i);
            if (igualdades[0][i]) {
                marcadas[i] = true;
            }
        }

        //Se encarga de verificar los caracteres que esten presentes
        for (int i = 0; i < this.palabra.length; i++) {
            if (!igualdades[0][i]) {
                for (int j = 0; j < this.palabra.length; j++) {
                    if (!igualdades[0][j] && !marcadas[j] && this.palabra[j] == palabra.getCaracter(i)) {
                        igualdades[1][i] = true;
                        marcadas[j] = true;
                        break;
                    }
                }
            }
        }

        return igualdades;
    }

    //Compara 2 palabras y regresa una matriz de booleanos
    public boolean compararPalabrasBooleano(Palabra palabra) {
        boolean[] igualdades = new boolean[5]; //La primera Fila indicara caracteres iguales
        //La segunda Fila indicara caracteres presentes
        int iguales = 0;        //Marca los caracteres que ya han sido verificados

        //Se encarga de verificar solo los caracteres que estan en su lugar
        for (int i = 0; i < this.palabra.length; i++) {
            igualdades[i] = this.palabra[i] == palabra.getCaracter(i);
            if (igualdades[i]) {
                iguales ++;
            }
        }

        return iguales == this.palabra.length;
    }

    @Override
    public String toString() {
        return new String(palabra);
    }

}
