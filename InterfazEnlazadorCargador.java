import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazEnlazadorCargador extends JFrame {

    private EnlazadorCargador enlazadorCargador;

    private JLabel LEC;
    private JPanel PanelEnlazadorCargador;
    private JPanel ContainerTexto;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton BotonCargar;
    private JScrollPane SPanelArea1;
    private JScrollPane SPanelArea2;
    private JButton BotonEnlazar;
    private JPanel PanelBotones;
    private String codigo;

    InterfazEnlazadorCargador(String codigo){
        this.codigo = codigo;
        enlazadorCargador = new EnlazadorCargador(codigo);
        mostrar();
    }

    public void mostrar(){
        setTitle("Ensamblador");
        setSize(800,700);
        setContentPane(PanelEnlazadorCargador);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        LEC.setFont(new Font("Serif", Font.BOLD, 50));

        ContainerTexto.setBorder(new EmptyBorder(10,10,10,10));

        SPanelArea1.setBorder(new LineBorder(Color.BLACK,5,true));
        textArea1.setText(codigo);
        SPanelArea2.setBorder(new LineBorder(Color.BLACK,5,true));

        PanelBotones.setBorder(new EmptyBorder(10,10,10,10));

        BotonEnlazar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea2.setText(enlazadorCargador.Enlazar());
            }
        });

        BotonCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaDialogoCargar dialogoCargar = new VentanaDialogoCargar(InterfazEnlazadorCargador.this, codigo);

                dialogoCargar.setVisible(true);
//                enlazadorCargador.EnlazarCargar();
            }
        });

//        PanelGeneral.setBorder(new EmptyBorder(10,30,0,30));
//
//        PanelDelArea.setBorder(new LineBorder(Color.BLACK,5,true));
//
//        AreaEnsamblador.setBorder(new EmptyBorder(2,2,2,2));
//
//        PanelBoton.setBorder(new EmptyBorder(10,0,18,0));
//
//        BotonEnsamblar.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String codigo = AreaEnsamblador.getText();
//                ensamblador.Ensamblar(codigo);
//            }
//        });

        setVisible(true);
    }
}
