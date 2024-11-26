package code;

public class Lista {

    private Palabra[] intentos; //La cantidad de intentos del juego
    private int intento = 0; //Numero de intento actual

    public Lista() {
        intentos = new Palabra[6];
    }

    //Agrega un intento a la lista
    public void agregarIntento(Palabra p) {
        if(intento < 6 && intento >= 0) {
            intentos[intento] = p;
            intento++;
        }
    }

    //Reinicia los intentos
    public void reiniciarIntentos(){
        for(int i = 0; i < intentos.length; i++){
            intentos[i] = null;
        }
        intento = 0;
    }

    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < intentos.length; i++){
            if(intentos[i] != null){
                s += intentos[i].toString() + "\n";
            }
        }
        return s;
    }
}
