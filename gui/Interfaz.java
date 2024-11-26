package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import code.*;

public class Interfaz extends JPanel implements ActionListener {

    JFrame panel;
    JLayeredPane layeredPane;
    ArrayList<Cuadrado> cuadroBG;
    ArrayList<Cuadrado> cuadroIntentos;
    ArrayList<JLabel> etiquetas;
    JTextField campo;
    JButton enviar;
    JLabel mensaje;

    JFrame gameOver;
    JLabel mensajeFinal;
    JLabel palabraElegida;
    JLabel intentosTotales;
    JButton reintentar;

    private Palabra palabra;
    private Lista lista;
    private BancoDePalabras diccionario;
    private int intento = 0;

    //CONSTRUCTOR
    public Interfaz() {
        //Inicializar los valores
        palabra = new Palabra();
        lista = new Lista();
        diccionario = new BancoDePalabras();

        //Panel de juego Principal
        panel = new JFrame("Wordle");
        panel.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setSize(480, 640);
        panel.getContentPane().setBackground(Color.WHITE);

        //Layered Pane se trata de un panel con capas
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setBounds(0, 0, 480, 640);
        panel.add(layeredPane);

        //Fondos de los recuadros
        cuadroBG = new ArrayList<>();
        Cuadrado cuadroTemp;
        for (int i = 0; i < 30; i++) {
            int x = 60 + 70 * (i % 5);
            int y = 5 + 70 * (i / 5);
            cuadroTemp = new Cuadrado(x, y, 60, 60);
            cuadroTemp.setbg();
            cuadroTemp.setBounds(x, y, 60, 60);
            cuadroBG.add(cuadroTemp);
            layeredPane.add(cuadroBG.get(i), JLayeredPane.DEFAULT_LAYER);
        }

        //Cuadros que contienen un caracter y cambian de color
        cuadroIntentos = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            int x = 65 + 70 * (i % 5);
            int y = 10 + 70 * (i / 5);
            cuadroTemp = new Cuadrado(x, y, 50, 50);
            cuadroTemp.setVacio();
            cuadroTemp.setBounds(x, y, 50, 50);
            cuadroIntentos.add(cuadroTemp);
            layeredPane.add(cuadroIntentos.get(i), JLayeredPane.PALETTE_LAYER);
        }

        //Caracteres de todos los intentos posibles
        etiquetas = new ArrayList<>();
        JLabel etiqueta;
        for (int i = 0; i < 30; i++) {
            int x = 75 + 70 * (i % 5);
            int y = 10 + 70 * (i / 5);
            etiqueta = new JLabel("");
            etiqueta.setBounds(x, y, 50, 50);
            etiqueta.setFont(new Font("Arial", Font.BOLD, 40));
            etiqueta.setForeground(Color.BLACK);
            etiqueta.setVisible(true);
            etiquetas.add(etiqueta);
            layeredPane.add(etiquetas.get(i), JLayeredPane.MODAL_LAYER);
        }

        //Campo de texto que admite 5 caracteres en mayuscula
        campo = new JTextField(5);
        campo.setBounds(140, 490, 80, 30);
        campo.setFont(new Font("Arial", Font.BOLD, 20));
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                campo.setText(campo.getText().toUpperCase());
                campo.setText(campo.getText().replaceAll("[^A-ZÑ]", ""));
                if (campo.getText().length() > 5)
                    campo.setText(campo.getText().substring(0, 5));
            }
        });
        layeredPane.add(campo, JLayeredPane.DRAG_LAYER);

        //Boton de enviar el intento a la lista
        enviar = new JButton("Enviar");
        enviar.setBounds(240, 480, 80, 50);
        enviar.setFont(new Font("Arial", Font.BOLD, 14));
        enviar.addActionListener(this);
        layeredPane.add(enviar, JLayeredPane.DRAG_LAYER);

        //Etiqueta que muestra detalles de la palabra actual
        mensaje = new JLabel("...");
        mensaje.setBounds(40, 540, 400, 50);
        mensaje.setFont(new Font("Arial", Font.BOLD, 14));
        layeredPane.add(mensaje, JLayeredPane.DRAG_LAYER);

        //Muestra el panel
        panel.setVisible(true);
        panel.setResizable(false);

        //Ventana de Game Over
        gameOver = new JFrame("Wordle : Game Over");
        gameOver.setLayout(null);
        gameOver.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameOver.setSize(480, 640);
        gameOver.getContentPane().setBackground(Color.WHITE);

        //Mensaje que indica si acerto o no
        mensajeFinal = new JLabel("FALLASTE!");
        mensajeFinal.setBounds(130, 100, 400, 50);
        mensajeFinal.setFont(new Font("Arial", Font.BOLD, 32));
        gameOver.add(mensajeFinal);

        //Etiqueta que muestra los intentos totales
        intentosTotales = new JLabel("Intentos : "+Integer.toString(intento));
        intentosTotales.setBounds(170, 180, 400, 50);
        intentosTotales.setFont(new Font("Arial", Font.BOLD, 20));
        gameOver.add(intentosTotales);

        //Muestra la palabra que era
        palabraElegida = new JLabel("La palabra es "+palabra);
        palabraElegida.setBounds(130, 240, 400, 50);
        palabraElegida.setFont(new Font("Arial", Font.BOLD, 20));
        gameOver.add(palabraElegida);

        //Boton que reinicia el juego
        reintentar = new JButton("Volver a Jugar");
        reintentar.setBounds(130, 320, 200, 80);
        reintentar.setFont(new Font("Arial", Font.BOLD, 14));
        reintentar.addActionListener(this);
        gameOver.add(reintentar);

        //Inicia el juego
        iniciarJuego();

    }

    /*
    Acciones de los botones
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //Boton de enviar
        if (e.getActionCommand().equals("Enviar")) {
            Palabra campoDeTexto = new Palabra();

            //Solo acepta palabras de 5 caracteres
            if (campo.getText().length() > 4) {

                //Solo acepta palabras del diccionario
                if(diccionario.isPalabraInDiccionario(campo.getText())){
                    campoDeTexto.setPalabra(campo.getText().toCharArray());

                    //Verifica si el intento es igual a la palabra incognita
                    if (palabra.compararPalabrasBooleano(campoDeTexto)) {
                        mensaje.setText("...");
                        boolean[][] b = new boolean[2][5];
                        b = palabra.compararPalabras(campoDeTexto);
                        changeCuadrados(b, campoDeTexto);
                        intento++;

                        gameIsOver(true);

                    } else {
                        mensaje.setText("No era la palabra.");
                        boolean[][] b = new boolean[2][5];
                        b = palabra.compararPalabras(campoDeTexto);
                        changeCuadrados(b, campoDeTexto);
                        intento++;
                        if(intento > 5){
                            gameIsOver(false);
                        }
                    }
                    lista.agregarIntento(campoDeTexto);
                    campo.setText("");
                }else{
                    mensaje.setText("La palabra no existe o no esta en nuestro diccionario.");
                }
            } else {
                mensaje.setText("La palabra tiene menos de 5 Caracteres.");
            }
        }

        //Boton de reintentar
        if(e.getActionCommand().equals("Volver a Jugar")){
            iniciarJuego();
            gameOver.setVisible(false);
            panel.setBounds(gameOver.getX(),gameOver.getY(),480,640);
            panel.setVisible(true);
        }

    }

    /*
    Cambia el color de una fila en base si los caracteres estan en su lugar
     */
    public void changeCuadrados(boolean[][] b, Palabra p) {
        int contador = 0;
        for (int i = 5 * intento; i < 5 * intento + 5; i++) {
            if (b[0][contador] == true) {
                cuadroIntentos.get(i).letraCorrecta();
            } else if (b[1][contador] == true) {
                cuadroIntentos.get(i).letraPresente();
            } else {
                cuadroIntentos.get(i).letraIncorrecta();
            }
            etiquetas.get(i).setText(p.getCaracter(contador) + "");
            etiquetas.get(i).setVisible(true);
            contador++;
        }
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    /*
    Termina el juego y muestra las estadisticas
     */
    public void gameIsOver(boolean ganado){

        intentosTotales.setText("Intentos : "+Integer.toString(intento));
        palabraElegida.setText("La palabra es "+palabra);

        if(ganado){
            mensajeFinal.setText("ACERTASTE!");
            panel.setVisible(false);
            gameOver.setBounds(panel.getX(),panel.getY(),480,640);
            gameOver.setVisible(true);
        }else{
            mensajeFinal.setText("FALLASTE!");
            panel.setVisible(false);
            gameOver.setBounds(panel.getX(),panel.getY(),480,640);
            gameOver.setVisible(true);
        }

    }

    /*
    Inicializa el juego
     */
    public void iniciarJuego(){
        palabra.setPalabra(diccionario.getPalabraRandom().toCharArray());
        mensaje.setText("...");
        intento = 0;

        for(Cuadrado c: cuadroIntentos){
            c.setVacio();
        }

        for(JLabel l: etiquetas){
            l.setText("");
        }
        lista.reiniciarIntentos();

    }

}
