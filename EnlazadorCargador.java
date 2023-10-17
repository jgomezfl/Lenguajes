import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnlazadorCargador {
    Memoria memoria;
    String fichero = "resultado_a_redireccionar.txt";
    String codigo;

    EnlazadorCargador(String codigo) {
        memoria = new Memoria();
        this.codigo = codigo;
    }

    public String Enlazar(int num_linea){
        String linea;
        String [] l = codigo.split("\n");
        String etiqueta = l[0];
        String respuesta = "";

        for(int i = 1 ; i < l.length ; i++){
            linea = l[i];
//            String pos = DecimalToBinarioDireccion(posicion);
//            posicion += 1;
//                System.out.println(pos+" -> "+linea);
//            memoria.EscribirMemoria(pos, linea);
            if (linea.contains(etiqueta)) {
                String [] inst = linea.split(" ");
                int numSalto = Integer.parseInt(inst[1].split("\\+")[1])+num_linea;

                String instruccion = inst[0]+DecimalToBinarioDireccion(numSalto);
                respuesta += instruccion+"\n";
//                memoria.EscribirMemoria(pos, instruccion);
            }
            else{
                respuesta += linea+"\n";
            }
        }

        return respuesta;
    }

    public void EnlazarCargar(int posicion) {
        String linea;
        String [] l = codigo.split("\n");
        String etiqueta = l[0];
        int num_linea = posicion;
        for(int i = 1 ; i < l.length ; i++){
            linea = l[i];
            String pos = DecimalToBinarioDireccion(posicion);
            posicion += 1;
//                System.out.println(pos+" -> "+linea);
            memoria.EscribirMemoria(pos, linea);
            if (linea.contains(etiqueta)) {
                String [] inst = linea.split(" ");
                int numSalto = Integer.parseInt(inst[1].split("\\+")[1])+num_linea;

                String instruccion = inst[0]+DecimalToBinarioDireccion(numSalto);
                memoria.EscribirMemoria(pos, instruccion);
            }
        }
        // memoria.VerMemoria();
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
