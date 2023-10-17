import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaDialogoCargar extends JDialog {
    private JTextField campoDeEntrada;
    private JButton botonAceptar;
    EnlazadorCargador enlazadorCargador;
    private String CodigoEnlazado;

    public String getCodigoEnlazado() {
        return CodigoEnlazado;
    }

    public VentanaDialogoCargar(Frame frame, String codigo, int code) {
        super(frame, "Posición en la Memoria", true);
        enlazadorCargador = new EnlazadorCargador(codigo);

        campoDeEntrada = new JTextField(20);

        if(code == 1){
            botonAceptar = new JButton("Enlazar");

            botonAceptar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Obtener el texto ingresado en el campo de entrada
                    String textoIngresado = campoDeEntrada.getText();
                    CodigoEnlazado = (enlazadorCargador.Enlazar(Integer.parseInt(textoIngresado)));
                    // Cerrar el diálogo
                    dispose();
                }
            });
        }
        if(code == 2){
            botonAceptar = new JButton("Cargar");
            botonAceptar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Obtener el texto ingresado en el campo de entrada
                    String textoIngresado = campoDeEntrada.getText();
                    enlazadorCargador.EnlazarCargar(Integer.parseInt(textoIngresado));
                    new InterfazUsuario(enlazadorCargador.memoria, new UnidadDeControl(enlazadorCargador.memoria), new Ensamblador()).setEspacioMemoria(Integer.parseInt(textoIngresado));
                    // Cerrar el diálogo
                    dispose();
                }
            });
        }

        JPanel panel = new JPanel();
        panel.add(new JLabel("Posición: "));
        panel.add(campoDeEntrada);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(botonAceptar, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(frame);
    }
}
