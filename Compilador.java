import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Compilador{
    private String NameProgram;

    public Compilador(String NameProgram){
        this.NameProgram = NameProgram;

        try {
            String comando = "type Eje1.txt | .\\Taller2.exe";
            String resultado = ejecutarComando(comando);
            System.out.println("Resultado del comando:\n" + resultado);
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