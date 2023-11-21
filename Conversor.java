import java.util.*;

public class Conversor {
    public List<String> Tokens;
    public Map<String, Integer> idsInt;
    public Map<String, Float> idsFloat;
    public Map<String, String> idsString;

    public Conversor(String tokens){
        this.Tokens = new ArrayList<>(Arrays.asList(tokens.split("\n")));
        idsInt = new HashMap<>();
        idsFloat = new HashMap<>();
        idsString = new HashMap<>();
    }
    
    public void convertir(){

        while(!Tokens.isEmpty()){

            // Identifica las palabras reservadas
            if(Tokens.get(0).charAt(1) == '1'){
                // Declaración entera
                if(Tokens.get(0).charAt(3) == '0'){
                    List<String> aux = Separación("<6,0>");

                    if(aux.size() > 1){
                        // Falta Implementar
                    }else{
                        idsInt.put(SepararId(aux.get(0)),0);
                    }
                }
                // Declaración flotante
                if(Tokens.get(0).charAt(3) == '1'){
                    List<String> aux = Separación("<6,0>");

                    if(aux.size() > 1){
                        // Falta Implementar
                    }else{
                        idsFloat.put(SepararId(aux.get(0)), Float.valueOf(0));
                    }
                }
                // Declaración Cadenas
                if(Tokens.get(0).charAt(3) == '2'){
                    List<String> aux = Separación("<6,0>");

                    if(aux.size() > 1){
                        // Falta Implementar
                    }else{
                        idsString.put(SepararId(aux.get(0)), "");
                    }
                }
            }
            if(Tokens.get(0).charAt(1) == '4'){
//                List<String> aux = new ArrayList<String>();
//                 aux.add(Separación("<6,0>"));
//                if(aux.size() > 1){
//
//                }
                break;
            }

        }
    }

    public List<String> Separación(String separador) {
        Tokens.remove(0);
        int cont = 0;
        List<String> respuesta = new ArrayList<String>();
        while (!Tokens.get(cont).equals(separador)){
            respuesta.add(Tokens.get(cont));
            Tokens.remove(cont);
        }
        Tokens.remove(cont);
        return respuesta;
    }

    public String SepararId(String cad){
        int cont = 0;
        for(int i = 4 ; i < cad.length() ; i++){
            if(cad.charAt(i) == '\''){
                cont = i;
                break;
            }
        }
        return cad.substring(4,cont);
    }

}
