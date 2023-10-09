import java.io.BufferedReader;
// import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Por cuestiones de facilidad tomamos la decisión de permitir en este lenguaje el siguiente conjunto de caracteres ascii
// 32 (espacio) | 33, 35 (!, #) | 39 - 43 (', (, ), *, +) | 45 - 47 (-, ., /)
// 48 - 57 (0-9) | 59, 60, 61, 62 (;, <, =, >)
// 65 - 90 (A-Z) | 97 - 122 (a-z)
// 123, 125 ({, })


public class AnalisadorLexico {
    
    FileReader archivo;
    // String [] Tokens = {"Boolean", "Break", "If", "Else", "Int", "Float", "For", "While", "Char"};
    Map<String, String> Tokens = new HashMap<>();
    List<String> identificadores;

    String PatronIdentificador = "^[A-Za-z][a-zA-Z0-9_]*$";
    String PatronNumeroEntero = "^[0-9]+$";
    String PatronNumeroDecimal = "^[0-9]+.[0-9]+$";

    AnalisadorLexico(FileReader archivo){
        this.archivo = archivo;
        
        // Palabras Reservadas
        Tokens.put("Boolean",   "TipoVariable");// Añadirmos los tipos de variables
        Tokens.put("Int",       "TipoVariable");
        Tokens.put("Float",     "TipoVariable");
        Tokens.put("Char",      "TipoVariable");
        Tokens.put("If",        "Estructura");// Añadimos las diferentes estructuras
        Tokens.put("Else",      "Estructura");
        Tokens.put("For",       "Estructura");
        Tokens.put("While",     "Estructura");
        Tokens.put("Print",     "Estructura");
        Tokens.put("Or",        "OpLogico");// Operadores lógicos
        Tokens.put("And",       "OpLogico");
        Tokens.put("True",      "ValorLogico");// Valores Lógicos
        Tokens.put("False",     "ValorLogico");

        // Operadores Aritmeticos
        Tokens.put("+",         "OpAritmetico");
        Tokens.put("-",         "OpAritmetico");
        Tokens.put("*",         "OpAritmetico");
        Tokens.put("/",         "OpAritmetico");
        Tokens.put("//",        "OpAritmetico");
        Tokens.put("++",        "OpAritmetico");

        // Operadores Relacionales
        Tokens.put("<", "OpRelacional");
        Tokens.put(">", "OpRelacional");
        Tokens.put(">=","OpRelacional");
        Tokens.put("<=","OpRelacional");
        Tokens.put("==","OpRelacional");
        Tokens.put("!=","OpRelacional");

        // Operador de Asignación
        Tokens.put("=", "OpAsignacion");

        //Inicializamos la lista de identificadores
        identificadores = new ArrayList<String>();
    }

    public String AnalizarArchivo(){
        try{
            BufferedReader bf = new BufferedReader(archivo);
            String linea;
            int contLinea = 1;
            while ((linea = bf.readLine()) != null){
                // System.out.println("linea "+contLinea);
                linea = linea.replaceAll("\\s+", " ");
                List <String> l = new ArrayList<String>(Arrays.asList(linea.split("[ (){}]")));
                l.removeIf(item -> item.equals(""));
                while(!l.isEmpty()){
                    // System.out.println(l.get(0));
                    boolean isComment = false;
                    for(int j = 0 ; j < l.get(0).length() ; j++){
                        int VAscii = (int) l.get(0).charAt(j);
                        if((VAscii==34) || (VAscii>35 && VAscii<39) || (VAscii==44) || (VAscii==58) ||
                        (VAscii>62 && VAscii<65) || (VAscii>90 && VAscii<97) || (VAscii==124) || (VAscii>125)){
                            String respuesta = "Caracter Invalido por el Lenguaje en la línea "+contLinea+"\n"+linea+"\n";
                            for(int k = 0 ; k < linea.indexOf(l.get(0).charAt(j)) ; k++){
                                respuesta += " ";
                            }
                            respuesta += "^";
                            return respuesta;
                        }
                        // if(VAscii == 39){}
                        if(VAscii == 35){
                            isComment = true;
                            break;
                        }
                    }
                    if(isComment){
                        break;
                    }
                    if(Tokens.containsKey(l.get(0))){
                        // System.out.println(Tokens.get(l.get(0)));
                        if(Tokens.get(l.get(0)).equals("TipoVariable")){
                            if(l.size() > 1){
                                if(Tokens.containsKey(l.get(1))){
                                    String respuesta = "Palabra reservada, Identificador invalido en la linea "+contLinea+"\n"+linea+"\n";
                                    for(int i = 0 ; i < linea.indexOf(l.get(1)) ; i++){
                                        respuesta += " ";
                                    }
                                    respuesta += "^";
                                    return respuesta;
                                }
                                Pattern patron = Pattern.compile(PatronIdentificador);
                                Matcher matcher = patron.matcher(l.get(1));

                                if(!matcher.matches()){
                                    String respuesta = "Identificador invalido en la linea "+contLinea+"\n"+linea+"\n";
                                    for(int i = 0 ; i < linea.indexOf(l.get(1)) ; i++){
                                        respuesta += " ";
                                    }
                                    respuesta += "^";
                                    return respuesta;
                                }

                                identificadores.add(l.get(1));

                                l.remove(0);
                                l.remove(0);
                                continue;
                            }
                            else{
                                String respuesta = "Identificador invalido en la linea "+contLinea+"\n"+linea+"\n";
                                for(int i = 0 ; i < linea.length() ; i++){
                                    respuesta += " ";
                                }
                                respuesta += " ^";
                                return respuesta;
                            }
                        }
                        l.remove(0);
                        continue;
                    }
                    
                    Pattern patron = Pattern.compile(PatronNumeroEntero);
                    Matcher matcher = patron.matcher(l.get(0));
                    if(matcher.matches()){
                        l.remove(0);
                        continue;
                    }

                    patron = Pattern.compile(PatronNumeroDecimal);
                    matcher = patron.matcher(l.get(0));
                    if(matcher.matches()){
                        l.remove(0);
                        continue;
                    }

                    int VAscii = (int) l.get(0).charAt(0);
                    if((l.get(0).length() == 1)  && (VAscii == 32 || VAscii == 33 || VAscii == 35 || (VAscii > 38 && VAscii < 44) ||
                    (VAscii > 44 && VAscii < 58) || (VAscii > 58 && VAscii < 63) || (VAscii > 64 && VAscii < 91) || 
                    (VAscii > 96 && VAscii < 123) || VAscii == 123 || VAscii == 125)){
                        l.remove(0);
                        continue;
                    }

                    if(!identificadores.contains(l.get(0))){
                        // for(String i : identificadores){
                        //     if(i.length() < l.get(0).length()){
                        //         String substring1 = l.get(0).substring(0, i.length());
                        //         String substring2 = l.get(0).substring(i.length(), l.get(0).length());
                        //         if(i.equals(substring1) && Tokens.containsKey(substring2)){
                        //             l.remove(0);
                        //             continue;
                        //         }
                        //     }
                        // }
                        String respuesta = linea+"\n";
                        for(int i = 0 ; i < linea.indexOf(l.get(0)) ; i++){
                            respuesta += " ";
                        }
                        respuesta += "^";
                        return respuesta;
                    }
                    l.remove(0);
                }
                contLinea++;
            }
        }catch(IOException e){
            System.err.println("Error al leer el fichero: " + e.getMessage());
        }
        
        return "";
    }


}
