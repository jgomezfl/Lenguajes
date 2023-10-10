import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class InterfazUsuario extends JFrame{
    private static final Memoria MEMORIA = new Memoria();
    private JPanel Computador;
    private JLabel valueD;
    private JLabel dValue;
    private JButton ejecucionCompletaButton;
    private JButton ejecucionPasoAPasoButton;
    private JLabel D;
    private JLabel valueC;
    private JLabel C;
    private JLabel valueB;
    private JLabel B;
    private JLabel valueA;
    private JLabel A;
    private JLabel cValue;
    private JLabel c;
    private JLabel d;
    private JLabel nValue;
    private JLabel n;
    private JLabel pValue;
    private JLabel p;
    private JTable Memoria;

    public InterfazUsuario(){

        setTitle("Computador");
        setSize(700, 800);
        setContentPane(Computador);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String[] memoriaCompleta = MEMORIA.TraerMemoria();

        MemoriaTableModel memoriaTableModel = new MemoriaTableModel(memoriaCompleta);

        Memoria.setModel(memoriaTableModel);

        setVisible(true);

    }

    private static class MemoriaTableModel extends AbstractTableModel {

        private final String[] COLUMNS = {"Dirección", "Instrucción Máquina"};
        private String[] memoriaCompleta;

        public MemoriaTableModel(String[] memoriaCompleta) {
            this.memoriaCompleta = memoriaCompleta;
        }

        @Override
        public int getRowCount() {
            return memoriaCompleta.length;
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex){
                case 0 -> rowIndex;
                case 1 -> memoriaCompleta[rowIndex];
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int column){
            return COLUMNS[column];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex){
            return columnIndex == 1;
        }
    }
}
