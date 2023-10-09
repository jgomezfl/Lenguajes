import java.io.FileReader;
import java.io.IOException;

public class Ensamblador {
    Memoria memoria = new Memoria();
    UnidadDeControl uc;
    FileReader fR;
    AnalisadorLexico al;

    Ensamblador(String ruta){
        try{
            fR = new FileReader(ruta);
            al = new AnalisadorLexico(fR);
        }catch(IOException e){
            System.err.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    public void compilar(){
        System.out.println(al.AnalizarArchivo());
    }
}
