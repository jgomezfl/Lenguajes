import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Computador{
    public static void main(String[] args) {

        ////////////////////////////////////////// Memoria //////////////////////////////////////////////
        Memoria memoria = new Memoria();

        // memoria.EscribirMemoria("0000000000", "0101010000000010");
        // memoria.EscribirMemoria("0000000001", "0000000100001010");

//        Ensamblador ensamblador = new Ensamblador();
//        ensamblador.Ensamblar();
        // System.out.println(ensamblador.DecimalToBinario(45));

        ////////////////////////////////// Escribimos un programa mcd ///////////////////////////////////
        // memoria.EscribirMemoria("0100000000", "0001001000000000"); //mcd
        // memoria.EscribirMemoria("0100000001", "0001011000000001");
        // memoria.EscribirMemoria("0100000010", "0110000000000010"); //bucle
        // memoria.EscribirMemoria("0100000011", "0110000000101001");
        // memoria.EscribirMemoria("0100000100", "0100000100001010");
        // memoria.EscribirMemoria("0100000101", "0100010100001000");
        // memoria.EscribirMemoria("0100000110", "0110000000100001");
        // memoria.EscribirMemoria("0100000111", "0101000100000010");
        // memoria.EscribirMemoria("0100001000", "0110000000100100"); //menor
        // memoria.EscribirMemoria("0100001001", "0101000100000010");
        // memoria.EscribirMemoria("0100001010", "0011001000000010"); //fin
        // memoria.EscribirMemoria("0100001011", "0000000000000000");
        ///////////////// Le damos un número a a y b
        // memoria.EscribirMemoria("1000000000", "0000000000001100"); //12
        // memoria.EscribirMemoria("1000000001", "0000000000011000"); //24

        // memoria.EscribirMemoria("1000000000", "0000000001000000"); //64
        // memoria.EscribirMemoria("1000000001", "0000000000100010"); //34

        // memoria.EscribirMemoria("1000000000", "0000000011111000"); //248
        // memoria.EscribirMemoria("1000000001", "0000000101010101"); //341

//        //////////////////////////// Escribimos un programa factorial de 5 //////////////////////////////
//         memoria.EscribirMemoria("0000000000", "0010000000000000"); //Guardamos en el registro "00" el siguiente valor
//         memoria.EscribirMemoria("0000000001", "0000000000000001"); //1
//         memoria.EscribirMemoria("0000000010", "0010010000000000"); //Guardamos en el registro "01" el siguiente valor
//         memoria.EscribirMemoria("0000000011", "0000000000000101"); //5
//        //Inicializamos el for
//         memoria.EscribirMemoria("0000000100", "0010100000000000"); //Guardamos en el registro "10" el siguiente valor
//         memoria.EscribirMemoria("0000000101", "0000000000000001"); //El valor a guardar
//        //Aqui inicia el for
//         memoria.EscribirMemoria("0000000110", "0110000000110010"); //Multiplicamos los registros "00" y "10"
//         memoria.EscribirMemoria("0000000111", "0110000000001011"); //Copíamos el valor del registro "10" al registro "11"
//         memoria.EscribirMemoria("0000001000", "0110000001101100"); //Restamos al registro 11 el siguiente valor
//         memoria.EscribirMemoria("0000001001", "0000000000000101"); //5
//         memoria.EscribirMemoria("0000001010", "0100000000001110"); //Saltar si cero
//         memoria.EscribirMemoria("0000001011", "0110000001011000"); //Sumamos al registro "10" el siguiente valor
//         memoria.EscribirMemoria("0000001100", "0000000000000001"); //1
//         memoria.EscribirMemoria("0000001101", "0101000000000110"); //Saltar a la dirección "0000000110"
//        // Aqui termina el for
//         memoria.EscribirMemoria("0000001110", "0011001100000000"); //Guardamos el valor del registro "00" a la dirección "1100000000"
//         memoria.EscribirMemoria("0000001111", "0000000000000000"); //Aquí termina el código

        //////////////////////////////////////////// Unidad de Control //////////////////////////////////
//        UnidadDeControl uc = new UnidadDeControl(memoria);
        
//        uc.Procesar("0000000000");
//        System.out.println(uc.registros.get("00"));
//        System.out.println(uc.registros.get("01"));
//        System.out.println(uc.registros.get("10"));
//        System.out.println(uc.registros.get("11"));
//        System.out.println(memoria.LeerMemoria("1100000000"));
        // memoria.VerMemoria();

        ///////////////////////////////////////// Interfaz /////////////////////////////////////////////

//        new InterfazUsuario(memoria, uc, new Ensamblador());
        new InterfazEnsamblador();
//        new InterfazEnlazadorCargador();

//        Pattern patron = Pattern.compile("^\"[a-zA-Z0-9]\"");
//        Matcher matcher = patron.matcher("\"c\" ewoidwedjweiojd");
//
//        if(matcher.find()){
//            System.out.println(matcher.group());
//        }

        Compilador comp = new Compilador("PrimerEjemplo");
        comp.compilar();



//        AnalisadorLexico an = new AnalisadorLexico();
//
//        System.out.println(an.AnalizarArchivo());
    }

}