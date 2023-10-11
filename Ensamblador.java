import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ensamblador {
    // Memoria memoria = new Memoria();
    // UnidadDeControl uc;
    FileReader fR;
    
    private Map<String, String> TablaInstrucciones = new HashMap<>();
    private Map<String, String> Etiquetas = new HashMap<>();
    private Map<String, String> Registros = new HashMap<>();
    String PrimeraEtiqueta;

    Ensamblador(String ruta){
        try{
            fR = new FileReader(ruta);
            
            TablaInstrucciones.put("Parar",         "0000000000000000");
            TablaInstrucciones.put("Cargar",        "0001");
            TablaInstrucciones.put("CargarValor",   "0010");
            TablaInstrucciones.put("Almacenar",     "0011");
    
            TablaInstrucciones.put("SaltarSiCero",  "010000");
            TablaInstrucciones.put("SaltarSiNeg",   "010001");
            TablaInstrucciones.put("SaltarSiPos",   "010010");
            TablaInstrucciones.put("SaltarSiDes",   "010011");
            TablaInstrucciones.put("Saltar",        "010100");
            TablaInstrucciones.put("AlmacenarNum",  "010101"); //Almacena un número en la dirección de memoria especificada
    
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

            Registros.put("A", "00");
            Registros.put("B", "01");
            Registros.put("C", "10");
            Registros.put("D", "11");
        }catch(IOException e){
            System.err.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    public void Ensamblar(){
        String nameFile = "resultado_a_redireccionar.txt";
        List<String> lineas = new ArrayList<>();

        try {
            FileWriter fWriter = new FileWriter(nameFile);
            BufferedWriter bfWriter = new BufferedWriter(fWriter);
            String respuesta = "";

            BufferedReader bf = new BufferedReader(fR);
            String linea;
            int contLinea = 0;

            while ((linea = bf.readLine()) != null){
                lineas.add(linea);
                linea = linea.replaceAll("\\s+", " ");
                List <String> l = new ArrayList<String>(Arrays.asList(linea.split(" ")));
                l.removeIf(item -> item.equals(""));
                if(l.get(0).contains(":")){
                    // Etiquetas.put(l.get(0).replaceAll(":", ""), contLinea+"");
                    if(Etiquetas.size() == 0){
                        Etiquetas.put(l.get(0).replaceAll(":",""), contLinea+"");
                        PrimeraEtiqueta = l.get(0).replaceAll(":","");
                    }
                    else{
                        Etiquetas.put(l.get(0).replaceAll(":",""), PrimeraEtiqueta+"+"+contLinea);
                    }
                    l.remove(0);
                }
                if(TablaInstrucciones.containsKey(l.get(0))){
                    if(l.get(0).equals("CargarValor") || l.get(0).equals("AlmacenarNum") ||
                    l.get(0).equals("SumarNum") || l.get(0).equals("RestarNum") ||
                    l.get(0).equals("MultNum") || l.get(0).equals("DivtNum")){
                        contLinea++;
                    }
                }
                contLinea ++;
            }
            bf.close();

            for(String i : Etiquetas.keySet()){
                System.out.println(i+" "+Etiquetas.get(i));
            }

            for (String i : lineas){
                linea = i.replaceAll("\\s+", " ");
                List <String> l = new ArrayList<String>(Arrays.asList(linea.split(" ")));
                l.removeIf(item -> item.equals(""));
                if(l.get(0).contains(":")){
                    l.remove(0);
                }
                if(TablaInstrucciones.containsKey(l.get(0))){
                    if(l.get(0).equals("Parar")){
                        respuesta += TablaInstrucciones.get(l.get(0));
                    }
                    if(l.get(0).equals("Cargar")){
                        String [] args = l.get(1).split(",");
                        respuesta += TablaInstrucciones.get(l.get(0))+Registros.get(args[0])+DecimalToBinarioDireccion(Integer.parseInt(args[1]))+"\n";
                    }
                    if(l.get(0).equals("CargarValor")){
                        String [] args = l.get(1).split(",");
                        respuesta += LLenarDeCeros(TablaInstrucciones.get(l.get(0))+Registros.get(args[0]))+"\n";
                        respuesta += DecimalToBinario(Integer.parseInt(args[1]))+"\n";
                        // contLinea++;
                    }
                    if(l.get(0).equals("Almacenar")){
                        String [] args = l.get(1).split(",");
                        respuesta += TablaInstrucciones.get(l.get(0))+Registros.get(args[0])+DecimalToBinarioDireccion(Integer.parseInt(args[1]))+"\n";
                    }
                    if(l.get(0).equals("SaltarSiCero")){
                        respuesta += TablaInstrucciones.get(l.get(0))+" "+Etiquetas.get(l.get(1))+"\n";
                        // System.out.println(l.get(1));
                    }
                    if(l.get(0).equals("SaltarSiNeg")){
                        respuesta += TablaInstrucciones.get(l.get(0))+" "+Etiquetas.get(l.get(1))+"\n";
                        // System.out.println(l.get(1));
                    }
                    if(l.get(0).equals("SaltarSiPos")){
                        respuesta += TablaInstrucciones.get(l.get(0))+" "+Etiquetas.get(l.get(1))+"\n";
                        // System.out.println(l.get(1));
                    }
                    if(l.get(0).equals("SaltarSiDes")){
                        respuesta += TablaInstrucciones.get(l.get(0))+" "+Etiquetas.get(l.get(1))+"\n";
                        // System.out.println(l.get(1));
                    }
                    if(l.get(0).equals("Saltar")){
                        respuesta += TablaInstrucciones.get(l.get(0))+" "+Etiquetas.get(l.get(1))+"\n";
                        // System.out.println(l.get(1));
                    }
                    if(l.get(0).equals("AlmacenarNum")){
                        String [] args = l.get(1).split(",");
                        respuesta += TablaInstrucciones.get(l.get(0))+DecimalToBinarioDireccion(Integer.parseInt(args[0]))+"\n";
                        respuesta += DecimalToBinario(Integer.parseInt(args[1]))+"\n";
                        // contLinea++;
                    }
                    if(l.get(0).equals("Copiar")){
                        String [] args = l.get(1).split(",");
                        respuesta += TablaInstrucciones.get(l.get(0))+Registros.get(args[0])+Registros.get(args[1])+"\n";
                    }
                    if(l.get(0).equals("Sumar")){
                        String [] args = l.get(1).split(",");
                        respuesta += TablaInstrucciones.get(l.get(0))+Registros.get(args[0])+Registros.get(args[1])+"\n";
                    }
                    if(l.get(0).equals("Restar")){
                        String [] args = l.get(1).split(",");
                        respuesta += TablaInstrucciones.get(l.get(0))+Registros.get(args[0])+Registros.get(args[1])+"\n";
                    }
                    if(l.get(0).equals("Mult")){
                        String [] args = l.get(1).split(",");
                        respuesta += TablaInstrucciones.get(l.get(0))+Registros.get(args[0])+Registros.get(args[1])+"\n";
                    }
                    if(l.get(0).equals("Div")){
                        String [] args = l.get(1).split(",");
                        respuesta += TablaInstrucciones.get(l.get(0))+Registros.get(args[0])+Registros.get(args[1])+"\n";
                    }
                    if(l.get(0).equals("SumarNum")){
                        String [] args = l.get(1).split(",");
                        respuesta += LLenarDeCeros(TablaInstrucciones.get(l.get(0))+Registros.get(args[0]))+"\n";
                        respuesta += DecimalToBinario(Integer.parseInt(args[1]))+"\n";
                        // contLinea++;
                    }
                    if(l.get(0).equals("RestarNum")){
                        String [] args = l.get(1).split(",");
                        respuesta += LLenarDeCeros(TablaInstrucciones.get(l.get(0))+Registros.get(args[0]))+"\n";
                        respuesta += DecimalToBinario(Integer.parseInt(args[1]))+"\n";
                        // contLinea++;
                    }
                    if(l.get(0).equals("MultNum")){
                        String [] args = l.get(1).split(",");
                        respuesta += LLenarDeCeros(TablaInstrucciones.get(l.get(0))+Registros.get(args[0]))+"\n";
                        respuesta += DecimalToBinario(Integer.parseInt(args[1]))+"\n";
                        // contLinea++;
                    }
                    if(l.get(0).equals("DivNum")){
                        String [] args = l.get(1).split(",");
                        respuesta += LLenarDeCeros(TablaInstrucciones.get(l.get(0))+Registros.get(args[0]))+"\n";
                        respuesta += DecimalToBinario(Integer.parseInt(args[1]))+"\n";
                        // contLinea++;
                    }
                }
                // contLinea++;
            }
            

            bfWriter.write(respuesta);
            bfWriter.close();
            fWriter.close();
            // bf.close();
        } catch (IOException e) {
            System.err.println("Error al leer el fichero: " + e.getMessage());
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

    public String LLenarDeCeros(String instruccion){
        while(instruccion.length() < 16){
            instruccion += "0";
        }
        return instruccion;
    }
}
