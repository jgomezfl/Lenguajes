import java.io.BufferedReader;
// import java.io.File;
import java.io.File;
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
    Map<Integer, Object> Diccionario = new HashMap<>();
    List<String> identificadores;

    String PatronIdentificador = "^[A-Za-z][a-zA-Z0-9_]*$";
    String PatronNumeroEntero = "^[0-9]+$";
    String PatronNumeroDecimal = "^[0-9]+.[0-9]+$";

    AnalisadorLexico(){
        // La primer categoría léxica son las palabras reservadas
        List<String> PalabrasReservadas =
                List.of("Boolean","Integer","Float", "Char", // Tipos de Variable (1-4)
                        "If", "Else", "For", "While", "Print", // Estructuras (5-9)
                        "Or", "And", // Operadores Lógicos (10-11)
                        "True", "False"); // Literalos Booleanos (12-13)
        Diccionario.put(1, PalabrasReservadas);

        // La segunda categoría Léxica son los identificadores
        Diccionario.put(2, "^[A-Za-z][a-zA-Z0-9_]*"); // Almacenamos el patrón a identificar

        // La tercer categoría Léxica son los operadores
        List<String> Operadores =
                List.of("+","-","*","/","//","++", //Operadores Aritmeticos  (1-6)
                        "<",">","<=",">=","==","!=", //Operador Relacional (7-12)
                        "="); // Operador de Asignación (13)
        Diccionario.put(3, Operadores);

        // La cuarta categoría Léxica son los literales
        List<String> PatronesLiterales =
                List.of("^[0-9]+.[0-9]+", // Números decimales (0)
                        "^[0-9]+", // Números enteros (1)
                        "^\"[a-zA-Z0-9]\""); // Caracter, solo admite caracteres alfa númericos (2)
        Diccionario.put(4, PatronesLiterales);

        // La quinta categoría son los delimitadores
        List<String> Delimitadores =
                List.of("(",")", // Paréntesis 1 y 2 respectivamente
                        "{","}"); // Llaves 1 y 2 respectivamente
        Diccionario.put(5, Delimitadores);

        // La sexta categoría son los signos de puntuación
        List<String> SignosDePuntuacion =
                List.of("#", // Comentario de una línea, 1
                        "/*", "*/", // Comentario de 2 líneas, 2 y 3 respectivamente
                        ";"); // Mayormente usado en la sintaxis de ciertas estructuras, 4
        Diccionario.put(6, SignosDePuntuacion);

        // Definimos en el diccionario una categoría especial, el espacio en blanco
        Diccionario.put(7, "^\\s+"); //\\s+

        //Inicializamos la lista de identificadores
        identificadores = new ArrayList<String>();
    }

    public String AnalizarArchivo(){
        File archivo = new File("DocumentoAnalizador.txt");
        String respuesta = "";
        try{
            FileReader reader = new FileReader(archivo);
            BufferedReader bfreader = new BufferedReader(reader);

            String linea;
            int cont = 0;
            while ((linea = bfreader.readLine()) != null){
                cont += 1;
                String lAux = linea;
//                System.out.println(lAux);
                while(lAux.length() > 0){
                    String aux = (String) Diccionario.get(7);
                    Pattern p = Pattern.compile(aux);
                    Matcher m = p.matcher(lAux);

                    if(m.find()){
                        respuesta += "<"+7+",'"+m.group()+"'>\n";
                        lAux = lAux.substring(m.group().length(),lAux.length());
                    }

                    boolean SalirDelBucle = false;
                    for(int i = 1 ; i < 8 ; i++){
                        switch (i){
                            // Primero revisamos si es una palabra reservada
                            case 1:
                                List<String> PalabrasReservadas = (List<String>)Diccionario.get(i);
                                for(String j : PalabrasReservadas){
                                    if(lAux.length() < j.length()){
                                        continue;
                                    }
                                    if(j.equals(lAux.substring(0,j.length()))){
                                        respuesta += "<"+i+","+PalabrasReservadas.indexOf(j)+">\n";
                                        lAux = lAux.substring(j.length(),lAux.length());
                                        SalirDelBucle = true;
                                        break;
                                    }
                                }
                                break;
                            case 2:
                                Pattern patron = Pattern.compile((String)Diccionario.get(i));
                                Matcher matcher = patron.matcher(lAux);

                                if (matcher.find()) {
                                    respuesta += "<"+i+",'"+matcher.group()+"'>\n";
                                    lAux = lAux.substring(matcher.group().length(),lAux.length());
                                    SalirDelBucle = true;
                                }
                                break;
                            case 3:
                                List<String> Operadores = (List<String>)Diccionario.get(i);
                                for(String j : Operadores){
                                    if(lAux.length() < j.length()){
                                        continue;
                                    }
                                    if(j.equals(lAux.substring(0,j.length()))){
                                        respuesta += "<"+i+","+Operadores.indexOf(j)+">\n";
                                        lAux = lAux.substring(j.length(),lAux.length());
                                        SalirDelBucle = true;
                                        break;
                                    }
                                }
                                break;
                            case 4:
                                List<String> PatronesLiterales = (List<String>)Diccionario.get(i);
                                for(String j : PatronesLiterales){
                                    Pattern patronLi = Pattern.compile(j);
                                    Matcher matcherLi = patronLi.matcher(lAux);

                                    if (matcherLi.find()) {
                                        respuesta += "<"+i+",'"+matcherLi.group()+"'>\n";
                                        lAux = lAux.substring(matcherLi.group().length(),lAux.length());
                                        SalirDelBucle = true;
                                        break;
                                    }
                                }
                                break;
                            case 5:
                                List<String> Delimitadores = (List<String>)Diccionario.get(i);
                                for(String j : Delimitadores){
                                    if(lAux.length() < j.length()){
                                        continue;
                                    }
                                    if(j.equals(lAux.substring(0,j.length()))){
                                        respuesta += "<"+i+","+Delimitadores.indexOf(j)+">\n";
                                        lAux = lAux.substring(j.length(),lAux.length());
                                        SalirDelBucle = true;
                                        break;
                                    }
                                }
                                break;
                            case 6:
                                List<String> SignosDePuntuacion = (List<String>)Diccionario.get(i);
                                for(String j : SignosDePuntuacion){
                                    if(lAux.length() < j.length()){
                                        continue;
                                    }
                                    if(j.equals(lAux.substring(0,j.length()))){
                                        respuesta += "<"+i+","+SignosDePuntuacion.indexOf(j)+">\n";
                                        lAux = lAux.substring(j.length(),lAux.length());
                                        SalirDelBucle = true;
                                        break;
                                    }
                                }
                                break;
                            case 7:
                                respuesta = linea+"\n";
                                for(int k = 0 ; k < linea.indexOf(lAux) ; k++){
                                    respuesta += " ";
                                }
                                respuesta += "^\nCaracter Inválivo en la línea "+cont;
                                return respuesta;
                        }
                        if(SalirDelBucle){
                            break;
                        }
                    }
//                    break;
                }
//                break;
            }

            bfreader.close();
            reader.close();

            return respuesta;

        }catch(IOException e){
            return("Error al leer el fichero: " + e.getMessage());
        }
    }


}
