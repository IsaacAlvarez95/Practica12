package code;

import java.util.Arrays;
import java.util.Scanner;

public class Console {

    Palabra palabra;
    Teclado teclado;
    BancoDePalabras diccionario;
    Lista lista;

    public Console() {
        palabra = new Palabra();
        teclado = new Teclado();
        diccionario = new BancoDePalabras();
        lista = new Lista();
    }

    public void iniciarJuego() {
        palabra.setPalabra(diccionario.getPalabraRandom().toCharArray());
        loop();
    }

    public void loop(){
        int i = 0;
        int k = 0;
        int opciones = 0;
        boolean continuar = false;
        Scanner sc = new Scanner(System.in);

        do {
            do {
                continuar = false;
                System.out.println("Intentos: \n" +
                        lista + "\n");
                System.out.println("Teclado: \n" +
                        teclado + "\n");

                do {
                    System.out.println("1) agregar caracter");
                    System.out.println("2) modificar caracter");
                    System.out.println("3) eliminar caracter");
                    System.out.println("4) enviar");

                    opciones = sc.nextInt();

                    if (opciones <= 0 || opciones > 4) {
                        System.out.println("Opcion invalida");
                    }
                } while (opciones <= 0 || opciones > 4);
                switch (opciones) {
                    case 1:
                        char c = sc.next().charAt(0);
                        teclado.addCaracter(c);

                        i++;
                        break;

                    case 2:
                        int j = 0;
                        do {
                            j = sc.nextInt();
                            if (j < 0 && j > i) {
                                System.out.println("Opcion invalida");
                            }
                        } while (j < 0 && j > i);
                        char c2 = sc.next().charAt(0);
                        teclado.addCaracter(j, c2);

                        i++;
                        break;

                    case 3:
                        if (i > 0) {
                            teclado.deleteCaracter();
                            i--;
                        }
                        break;

                    case 4:
                        if (i >= 4 && diccionario.isPalabraInDiccionario(teclado.getPalabra().toString())) {
                            if (palabra.compararPalabrasBooleano(teclado.getPalabra())) {
                                k = 6;
                                System.out.println("GANASTE");
                            }else{
                                System.out.println("No era la palabra");
                                k++;
                                System.out.println(Arrays.deepToString(teclado.getPalabra().compararPalabras(palabra)));
                                if(k == 6){
                                    System.out.println("La palabra era " + palabra.getPalabra());
                                }
                            }
                            continuar = true;
                            lista.agregarIntento(teclado.enviarPalabra());
                        }else{
                            System.out.println("La palabra no esta en el banco");
                        }


                        break;
                }

                if (i > 4) {
                    i = 4;
                }

            } while (continuar == false);
        }while(k >= 0 && k <= 5);
        finalizar();
    }

    public void finalizar(){
        palabra.setPalabra(diccionario.getPalabraRandom().toCharArray());
        lista.reiniciarIntentos();
        iniciarJuego();
    }

}
