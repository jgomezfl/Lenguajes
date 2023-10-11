import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazEnsamblador extends JFrame {

    Ensamblador ensamblador;
    private JPanel VentanaEnsamblador;
    private JLabel TituloEnsamblador;
    private JTextArea AreaEnsamblador;
    private JScrollPane PanelDelArea;
    private JButton BotonEnsamblar;
    private JPanel PanelGeneral;
    private JPanel PanelBoton;

    public InterfazEnsamblador(){
        ensamblador = new Ensamblador();

        mostrar();
    }

    public void mostrar(){
        setTitle("Ensamblador");
        setSize(600,700);
        setContentPane(VentanaEnsamblador);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        TituloEnsamblador.setFont(new Font("Serif", Font.BOLD, 50));
        TituloEnsamblador.setBorder(new EmptyBorder(0,30,0,0));

        PanelGeneral.setBorder(new EmptyBorder(10,30,0,30));

        PanelDelArea.setBorder(new LineBorder(Color.BLACK,5,true));

        AreaEnsamblador.setBorder(new EmptyBorder(2,2,2,2));

        PanelBoton.setBorder(new EmptyBorder(10,0,18,0));

        BotonEnsamblar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = AreaEnsamblador.getText();
                ensamblador.Ensamblar(codigo);
            }
        });

        setVisible(true);
    }


}
