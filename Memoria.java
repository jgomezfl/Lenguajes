import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Memoria {
    String [] memoria = new String[1024];

    Memoria(){
        IniciarMemoria();
    }

    public void IniciarMemoria(){
        for(int i = 0 ; i < memoria.length ; i++){
            memoria[i] = "0000000000000000";
        }
    }

    public void VerMemoria(){
        for(int i = 0 ; i < memoria.length ; i++){
            System.out.println(memoria[i]);
        }
    }

    public String LeerMemoria(String posicion){
        if(BinarioToDecimalPosicion(posicion) == 1024){
            return "Problemas con las posiciones de la memoria";
        }
        return memoria[BinarioToDecimalPosicion(posicion)];
    }

    public String[] EscribirMemoria(String posicion, String numero){
        if(BinarioToDecimalPosicion(posicion) == 1024){
            System.out.println("Problemas con las posiciones de la memoria");
        }
        else{
            if((numero.length() == 16) && (numero.matches("[01]+"))){
                memoria[BinarioToDecimalPosicion(posicion)] = numero;
            }
        }
        return memoria;
    }

    public static int BinarioToDecimalPosicion(String posicion){
        String regex = "^[01]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(posicion);

        int [] bin10 = {512,256,128,64,32,16,8,4,2,1};
        // {1,2,4,8,16,32,64,128,256,512}
        int num = 0;

        if(matcher.matches() && (posicion.length() == 10)){
            for(int i = 0 ; i < posicion.length() ; i++){
                if(posicion.charAt(i) == '1'){
                    num += bin10[i];
                }
            }
        }
        else if(!matcher.matches()){
            System.out.println("No es un número binario");
            return 1024;
        }
        else if(posicion.length() != 10){
            System.out.println("No es una posición valida en la memoria");
            return 1024;
        }
        return num;
    }

    public static String DecimalToBinario(int numero){
        if(numero > 32767 || numero < -32768){
            return "Número fuera de rango";
        }
        if(numero == -32768){
            return "1000000000000000";
        }
        String binario = "";
        boolean negativo = false;
        if(numero < 0){
            negativo = true;
            numero *= -1;
        }
        while(numero > 1){
            int resto = numero % 2;
            numero /= 2;
            binario = resto+""+binario;
        }
        if(numero == 1){
            binario = "1"+binario;
        }

        while(binario.length() < 15){
            binario = "0"+binario;
        }

        if(negativo){
            binario = "1" + binario;
        }
        else{
            binario = "0" + binario;
            return binario;
        }

        //Ahora hallamos complemento a 2 de ese número
        String respuesta = "";
        boolean aux = false;
        for(int i = binario.length()-1 ; i > 0 ; i--){
            if((!aux) && (binario.charAt(i) == '1')){
                respuesta = binario.charAt(i)+respuesta;
                aux = !aux;
                continue;
            }
            if((!aux) && (binario.charAt(i) == '0')){
                respuesta = binario.charAt(i)+respuesta;
                continue;
            }
            if((aux) &&  (binario.charAt(i) == '1')){
                respuesta = "0"+respuesta;
                continue;
            }
            if((aux) &&  (binario.charAt(i) == '0')){
                respuesta = "1"+respuesta;
                continue;
            }
            System.out.println(i);
        }
        respuesta = binario.charAt(0)+respuesta;
        return respuesta;
    }
        
}
