import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UnidadDeControl {
    private String IC; //Instrucción en Curso
    private String CP; //registro que apunta a una dirección en la memoria
    private String DirInicial;

    public void setDirInicial(String dirInicial) {
        DirInicial = dirInicial;
    }

    ////////////////////////////////////// Tabla de Instrucciones ////////////////////////////////////
    private final Map<String, String> TablaInstrucciones = new HashMap<>();

    //////////////////////////////////////////// Registros ///////////////////////////////////////////
    Map<String, String> registros = new HashMap<>();

    //////////////////////////////////////////////////////////////////////////////////////////////////
    Memoria memoria;
    UnidadAritmetica ua;

    /*
    Proponemos jugar
    */

    UnidadDeControl(Memoria memoria){
        ua = new UnidadAritmetica("","");

        IC = "0000000000000000";
        CP = "0000000000";

        registros.put("00", "0000000000000000"); //A
        registros.put("01", "0000000000000000"); //B
        registros.put("10", "0000000000000000"); //C
        registros.put("11", "0000000000000000"); //D

        //Inicializamos la tabla de instrucciones
        // TablaInstrucciones.put("Parar",         "0000"); // Esto no era necesario

        TablaInstrucciones.put("Cargar",        "0001");
        TablaInstrucciones.put("CargarValor",   "0010");
        TablaInstrucciones.put("Almacenar",     "0011");

        TablaInstrucciones.put("SaltarSiCero",  "010000");
        TablaInstrucciones.put("SaltarSiNeg",   "010001");
        TablaInstrucciones.put("SaltarSiPos",   "010010");
        TablaInstrucciones.put("SaltarSiDes",   "010011");
        TablaInstrucciones.put("Saltar",        "010100");
        TablaInstrucciones.put("AlmacenarNum",  "010101"); //Almacena un número en la dirección de memoria especificada
        TablaInstrucciones.put("WriteNum",      "010110"); //Escribir Numero
        TablaInstrucciones.put("ReadNum",       "010111"); //Leer Numero

        // Entre registros
        TablaInstrucciones.put("Copiar",        "011000000000");
        TablaInstrucciones.put("Sumar",         "011000000001");
        TablaInstrucciones.put("Restar",        "011000000010");
        TablaInstrucciones.put("Mult",          "011000000011");
        TablaInstrucciones.put("Div",           "011000000100");

        // El registro y un número
        TablaInstrucciones.put("SumarNum",      "011000000101");
        TablaInstrucciones.put("RestarNum",     "011000000110");
        TablaInstrucciones.put("MultNum",       "011000000111");
        TablaInstrucciones.put("DivNum",        "011000001000");

        this.memoria = memoria;
    }

    public void ExtraerMemoria(){
        IC = memoria.LeerMemoria(CP);
    }

    void Cargar(String R, String M){
        if(registros.containsKey(R) && ((M.length() == 10) && (M.matches("[01]+")))){
            registros.put(R, memoria.LeerMemoria(M));
        }
    }
    
    void CargarValor(String R){
        if(registros.containsKey(R)){
            Sumar1Direccion();
            ExtraerMemoria();
            registros.put(R, IC);
        }
    }

    void Almacenar(String R, String M){
        if(registros.containsKey(R) && ((M.length() == 10) && (M.matches("[01]+")))){
            memoria.EscribirMemoria(M, registros.get(R));
        }
    }

    boolean SaltarSiCero(String M){
        if((M.length() == 10) && (M.matches("[01]+"))){
            if(ua.C.equals("1")){
                CP = M;
                return true;
            }
        }
        return false;
    }

    boolean SaltarSiNeg(String M){
        if((M.length() == 10) && (M.matches("[01]+"))){
            if(ua.N.equals("1")){
                CP = M;
                return true;
            }
        }
        return false;
    }

    boolean SaltarSiPos(String M){
        if((M.length() == 10) && (M.matches("[01]+"))){
            if(ua.P.equals("1")){
                CP = M;
                return true;
            }
        }
        return false;
    }

    boolean SaltarSiDes(String M){
        if((M.length() == 10) && (M.matches("[01]+"))){
            if(ua.D.equals("1")){
                CP = M;
                return true;
            }
        }
        return false;
    }

    void Saltar(String M){
        if((M.length() == 10) && (M.matches("[01]+"))){
            CP = M;
        }
    }

    void AlmacenarNum(String M){
        Sumar1Direccion();
        ExtraerMemoria();
        memoria.EscribirMemoria(M, IC);
    }

    void WriteNum(String M){
        int num;

        Scanner sc = new Scanner(System.in);
        num = sc.nextInt();

        memoria.EscribirMemoria(M, DecimalToBinario(num));
    }

    void ReadNum(String M){
        System.out.println(binarioADecimal(memoria.LeerMemoria(M)));
    }

    void Copiar(String R, String R1){
        if(registros.containsKey(R) && registros.containsKey(R1)){
            registros.put(R1, registros.get(R));
        }
    }

    void Sumar(String R, String R1){
        ua = new UnidadAritmetica(registros.get(R), registros.get(R1));
        registros.put(R, ua.suma());
    }

    void Restar(String R, String R1){
        ua = new UnidadAritmetica(registros.get(R), registros.get(R1));
        registros.put(R, ua.resta());
    }

    void Mult(String R, String R1){
        ua = new UnidadAritmetica(registros.get(R), registros.get(R1));
        registros.put(R, ua.multiplicacion());
    }

    void Div(String R, String R1){
        ua = new UnidadAritmetica(registros.get(R), registros.get(R1));
        registros.put(R, ua.division());
    }

    void SumarNum(String R){
        if(registros.containsKey(R)){
            Sumar1Direccion();
            ExtraerMemoria();
            ua = new UnidadAritmetica(registros.get(R), IC);
            registros.put(R, ua.suma());
        }
    }

    void RestarNum(String R){
        if(registros.containsKey(R)){
            Sumar1Direccion();
            ExtraerMemoria();
            ua = new UnidadAritmetica(registros.get(R), IC);
            registros.put(R, ua.resta());
        }
    }

    void MultNum(String R){
        if(registros.containsKey(R)){
            Sumar1Direccion();
            ExtraerMemoria();
            ua = new UnidadAritmetica(registros.get(R), IC);
            registros.put(R, ua.multiplicacion());
        }
    }

    void DivNum(String R){
        if(registros.containsKey(R)){
            Sumar1Direccion();
            ExtraerMemoria();
            ua = new UnidadAritmetica(registros.get(R), IC);
            registros.put(R, ua.division());
        }
    }
    
    public void Procesar(){
        CP = DirInicial;
        if((CP.length() == 10) && (CP.matches("[01]+"))){
            IC = memoria.LeerMemoria(CP);
        }
        else{
            return;
        }

        while(true){
            // System.out.println(CP);
            // System.out.println(IC);
            if(IC.equals("0000000000000000")){
                break;
            }
            TablaInstrucciones();
            ExtraerMemoria();
            // System.out.println("");
            DirInicial = CP;
        }
    }

    public void ProcesarPaso(){
        CP = DirInicial;
        ExtraerMemoria();
        TablaInstrucciones();
        DirInicial = CP;
    }

    void TablaInstrucciones(){
        for(Map.Entry<String,String> i : TablaInstrucciones.entrySet()){
            String j = IC.substring(0, i.getValue().length());
            if(i.getValue().equals(j)){
                if(i.getKey().equals("Cargar")){
                    // System.out.println("Carga de "+IC.substring(6, 16)+" al registro "+IC.substring(4, 6));
                    Cargar(IC.substring(4, 6), IC.substring(6, 16));
                    // System.out.println(registros.get(IC.substring(4, 6)));
                    break;
                }
                //Cargar valor no tiene ningún puto sentido
                if(i.getKey().equals("CargarValor")){
                    CargarValor(IC.substring(4, 6));
                    break;
                }
                if(i.getKey().equals("Almacenar")){
                    Almacenar(IC.substring(4, 6), IC.substring(6, 16));
                    break;
                }
                if(i.getKey().equals("SaltarSiCero")){
                    if(SaltarSiCero(IC.substring(6, 16))){
                        return;
                    } else {
                        break;
                    }
                }
                if(i.getKey().equals("SaltarSiNeg")){
                    if(SaltarSiNeg(IC.substring(6, 16))){
                        return;
                    } else {
                        break;
                    }
                }
                if(i.getKey().equals("SaltarSiPos")){
                    if(SaltarSiPos(IC.substring(6, 16))){
                        return;
                    } else {
                        break;
                    }
                }
                if(i.getKey().equals("SaltarSiDes")){
                    if(SaltarSiDes(IC.substring(6, 16))){
                        return;
                    } else {
                        break;
                    }
                }
                if(i.getKey().equals("Saltar")){
                    Saltar(IC.substring(6, 16));
                    return;
                }
                if(i.getKey().equals("AlmacenarNum")){
                    AlmacenarNum(IC.substring(6, 16));
                    break;
                }
                if(i.getKey().equals("WriteNum")){
                    WriteNum(IC.substring(6, 16));
                    break;
                }
                if(i.getKey().equals("ReadNum")){
                    ReadNum(IC.substring(6, 16));
                    break;
                }
                if(i.getKey().equals("Copiar")){
                    // System.out.println("Copia del registro "+IC.substring(12, 14)+" al registro "+IC.substring(14, 16));
                    Copiar(IC.substring(12, 14), IC.substring(14, 16));
                    // System.out.println("Registro "+IC.substring(12, 14)+" tiene el valor "+registros.get(IC.substring(14, 16)));
                    break;
                }
                if(i.getKey().equals("Sumar")){
                    Sumar(IC.substring(12, 14), IC.substring(14, 16));
                    break;
                }
                if(i.getKey().equals("Restar")){
                    // System.out.println(IC.substring(12, 14)+" - "+IC.substring(14, 16));
                    Restar(IC.substring(12, 14), IC.substring(14, 16));
                    // System.out.println("Resultado: "+IC.substring(12, 14));
                    break;
                }
                if(i.getKey().equals("Mult")){
                    Mult(IC.substring(12, 14), IC.substring(14, 16));
                    break;
                }
                if(i.getKey().equals("Div")){
                    Div(IC.substring(12, 14), IC.substring(14, 16));
                    break;
                }
                if(i.getKey().equals("SumarNum")){
                    SumarNum(IC.substring(12, 14));
                    break;
                }
                if(i.getKey().equals("RestarNum")){
                    RestarNum(IC.substring(12, 14));
                    break;
                }
                if(i.getKey().equals("MultNum")){
                    MultNum(IC.substring(12, 14));
                    break;
                }
                if(i.getKey().equals("DivNum")){
                    DivNum(IC.substring(12, 14));
                    break;
                }
            }
        }
        Sumar1Direccion();
    }

    public static int binarioADecimal(String binario) {
        int resultado = 0;

        for (int i = binario.length() - 1; i >= 0; i--) {
            resultado += (binario.charAt(i) - '0') * Math.pow(2, binario.length() - 1 - i);
        }

        return resultado;
    }

    void Sumar1Direccion(){
        String resp = "";
        String carry = "1";
        for(int i = CP.length()-1 ; i >= 0 ; i--){
            if(carry.equals("1") && CP.charAt(i) == '1'){
                resp = "0"+resp;
                continue;
            }
            if(carry.equals("1") && CP.charAt(i) == '0'){
                resp = "1"+resp;
                carry = "0";
                continue;
            }
            resp = CP.charAt(i)+resp;
        }
        // System.out.println(resp);
        CP = resp;
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
    public String getIndC(){
        return ua.getC();
    }

    public String getIndP(){
        return ua.getP();
    }

    public String getIndN(){
        return ua.getN();
    }

    public String getIndD(){
        return ua.getD();
    }

    public String getIC() {
        return IC;
    }

    public String getCP() {
        return CP;
    }
}
