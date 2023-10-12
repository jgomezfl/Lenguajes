import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnlazadorCargador {
    Memoria memoria;
    String fichero = "resultado_a_redireccionar.txt";

    EnlazadorCargador(Memoria memoria) {
        this.memoria = memoria;
    }

    public void EnlazarCargar(int posicion) {
//        List <String> lineas = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fichero));
            String etiqueta = br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String pos = DecimalToBinarioDireccion(posicion);
                posicion += 1;
//                System.out.println(pos+" -> "+linea);
                memoria.EscribirMemoria(pos, linea);
                if (linea.contains(etiqueta)) {
                    String [] inst = linea.split(" ");

                    String instruccion = inst[0]+DecimalToBinarioDireccion(Integer.parseInt(inst[1].split("\\+")[1]));
                    memoria.EscribirMemoria(pos, instruccion);
                }
            }
            br.close();

//            String
        } catch (IOException e) {
            System.err.println("Hubo un error al leer el archivo: " + e.getMessage());
        }
    }

    public String DecimalToBinarioDireccion(int numero){
        String binario = "";
        while(numero > 1){
            int resto = numero % 2;
            numero /= 2;
            binario = resto+""+binario;
        }
        if(numero == 1){
            binario = "1"+binario;
        }
        while(binario.length() < 10){
            binario = "0"+binario;
        }
        return binario;
    }

}
