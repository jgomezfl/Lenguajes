import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Compilador {
    private String NameProgram;
    private AnalisadorLexico lex;

    public Compilador(String NameProgram){
        this.NameProgram = NameProgram;

        lex = new AnalisadorLexico();
    }

    public void compilar(){
        //Primero Corre el analizador Lexico
        System.out.println(lex.AnalizarArchivo());


        // Despues corre el sintactico
        try {
            String comando = "type Eje1.txt | .\\Taller2.exe";
            String resultado = ejecutarComando(comando);
            System.out.println(resultado);
            if(resultado.equals("")){
                Conversor conv = new Conversor(lex.AnalizarArchivo());
                conv.convertir();
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String ejecutarComando(String comando) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", comando);
        processBuilder.redirectErrorStream(true);

        Process proceso = processBuilder.start();

        // Lee la salida del proceso
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
            StringBuilder resultado = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                resultado.append(linea).append("\n");
            }
            return resultado.toString();
        }
    }
}
