import java.util.HashMap;
import java.util.Map;

public class UnidadDeControl {
    private String IC; //Instrucción en Curso
    private String CP; //registro que apunta a una dirección en la memoria

    ////////////////////////////////////// Tabla de Instrucciones ////////////////////////////////////
    private Map<String, String> TablaInstrucciones = new HashMap<>();

    //////////////////////////////////////////// Registros ///////////////////////////////////////////
    Map<String, String> registros = new HashMap<>();

    //////////////////////////////////////////////////////////////////////////////////////////////////
    Memoria memoria;
    UnidadAritmetica ua;

    /*
    Proponemos jugar
    */

    UnidadDeControl(Memoria memoria){
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

        // Entre registros
        TablaInstrucciones.put("Copiar",        "011000000000");
        TablaInstrucciones.put("Sumar",         "011000000001");
        TablaInstrucciones.put("Restar",        "011000000010");
        TablaInstrucciones.put("Mult",          "011000000011");
        TablaInstrucciones.put("Div",           "011000000100");

        // El registro y un número
        TablaInstrucciones.put("SumarNum",         "011000000101");
        TablaInstrucciones.put("RestarNum",        "011000000110");
        TablaInstrucciones.put("MultNum",          "011000000111"); // falta implementar
        TablaInstrucciones.put("DivNum",           "011000001000"); // falta implementar

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
    
    public void Procesar(String M){
        CP = M;
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
        }
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
}
