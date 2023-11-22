import java.util.*;

public class Conversor {
    public List<String> Tokens;
    public List<String> idsInt;
    public List<String> instrInt;
    public List<String> idsFloat;
    public List<String> idsString;
    public List<String> instrucciones;
    public Map<Integer, String> posIds;
    int posicionVar;

    public Conversor(String tokens){
        this.Tokens = new ArrayList<>(Arrays.asList(tokens.split("\n")));

        idsInt = new ArrayList<>();
        idsFloat = new ArrayList<>();
        idsString = new ArrayList<>();
        instrInt = new ArrayList<>();

        instrucciones = new ArrayList<>();

        posicionVar = 1023;
    }
    
    public String convertir(){
        while(!Tokens.isEmpty()){

            // Identifica las palabras reservadas
            if(Tokens.get(0).charAt(1) == '1') {
                // Declaración entera
                if (Tokens.get(0).charAt(3) == '0') {
                    List<String> aux = Separacion("<6,0>");

                    if (aux.size() > 1) {
                        String id = SepararId(aux.get(0));
                        String num = SepararId(aux.get(2));

                        for(String i : idsInt){
                            if(i.contains(id)){
                                return "Variable previamente definida";
                            }
                        }

                        if(aux.size() > 3){
                            instrucciones.add("CargarValor A,"+SepararId(aux.get(2)));
                            instrucciones.add("CargarValor B,"+SepararId(aux.get(4)));

                            if(aux.get(3).equals("<3,0>")){
                                instrucciones.add("Sumar A,B");
                            }
                            if(aux.get(3).equals("<3,1>")){
                                instrucciones.add("Restar A,B");
                            }
                            if(aux.get(3).equals("<3,2>")){
                                instrucciones.add("Mult A,B");
                            }
                            if(aux.get(3).equals("<3,3>")){
                                instrucciones.add("Div A,B");
                            }
                            instrucciones.add("Almacenar A,");

                            idsInt.add(id);
                            instrInt.add(id);

                            continue;
                        }

                        idsInt.add(id);
                        instrInt.add(id+" "+num);
                        instrucciones.add("AlmacenarNum "+id);
                    } else {
                        idsInt.add(SepararId(aux.get(0)));
                        instrInt.add(SepararId(aux.get(0))+" 0");

                        instrucciones.add("AlmacenarNum "+SepararId(aux.get(0)));
                        continue;
                    }
                }
                // If
                if (Tokens.get(0).charAt(3) == '5'){
                    Tokens.remove(0);
                    List<String> aux = Separacion("<5,1>");
                    if(aux.size() == 0){
                        borrarCorchetes();
                        continue;
                    }
                }
                //Write
                if (Tokens.get(0).charAt(3) == '6'){
                    List<String> aux = Separacion("<6,0>");
                    instrInt.add(SepararId(aux.get(0))+" write");

                    instrucciones.add("WriteNum "+SepararId(aux.get(0)));
                }
                //Read
                if (Tokens.get(0).charAt(3) == '7'){
                    List<String> aux = Separacion("<6,0>");
                    instrInt.add(SepararId(aux.get(0))+" read");

                    instrucciones.add("ReadNum "+SepararId(aux.get(0)));
                }
            }
            if(Tokens.get(0).charAt(1) == '2') {
                String id = SepararId(Tokens.get(0));
                List<String> aux = Separacion("<6,0>");

                if (aux.size() > 1) {

                    boolean exists = false;
                    for(String i : idsInt){
                        if(i.contains(id)){
                            exists = true;
                        }
                    }
                    if(!exists){
                        return "Variable No inicializada";
                    }

                    if(aux.size() > 3){
                        instrucciones.add("CargarValor A,"+SepararId(aux.get(1)));
                        instrucciones.add("CargarValor B,"+SepararId(aux.get(3)));

                        if(aux.get(2).equals("<3,0>")){
                            instrucciones.add("Sumar A,B");
                        }
                        if(aux.get(2).equals("<3,1>")){
                            instrucciones.add("Restar A,B");
                        }
                        if(aux.get(2).equals("<3,2>")){
                            instrucciones.add("Mult A,B");
                        }
                        if(aux.get(2).equals("<3,3>")){
                            instrucciones.add("Div A,B");
                        }
                        instrucciones.add("Almacenar A,");

                        instrInt.add(id);

                        continue;
                    }
                    String num = SepararId(aux.get(1));

                    if(num.matches("[0-9]+")){
                        instrInt.add(id+" "+num);
                        instrucciones.add("AlmacenarNum "+id);
                    }
                    else{
                        instrucciones.add("Cargar A,"+num);
                        instrucciones.add("Almacenar A,"+id);
                        instrInt.add(id+" "+num);
                    }
                }

            }

        }

        System.out.println(instrucciones);
        System.out.println(instrInt);
        System.out.println(idsInt);
        for(String i : idsInt){
            for(String j : instrInt){
                if(i.equals(j.substring(0,i.length()))){
                    if(j.contains(" ")){
                        String var = j.split(" ")[0];
                        String val = j.split(" ")[1];

                        if(val.matches("[0-9]+")){
                            instrucciones.set(instrucciones.indexOf("AlmacenarNum "+var), "AlmacenarNum "+(posicionVar-idsInt.indexOf(i))+","+val);
                        }
                        else if(val.equals("write")){
                            instrucciones.set(instrucciones.indexOf("WriteNum "+var), "WriteNum "+(posicionVar-idsInt.indexOf(i)));
                        }
                        else if(val.equals("read")){
                            instrucciones.set(instrucciones.indexOf("ReadNum "+var), "ReadNum "+(posicionVar-idsInt.indexOf(i)));
                        }
                        else{
                            instrucciones.set(instrucciones.indexOf("Cargar A,"+val), "Cargar A,"+(posicionVar-idsInt.indexOf(val)));
                            instrucciones.set(instrucciones.indexOf("Almacenar A,"+i), "Almacenar A,"+(posicionVar-idsInt.indexOf(i)));
                        }

                    }else{
                        instrucciones.set(instrucciones.indexOf("Almacenar A,"), "Almacenar A,"+(posicionVar-idsInt.indexOf(i)));
                    }
                }
            }
        }
        String respuestas = "";
        for(String i : instrucciones){
            respuestas += i+"\n";
        }
        return respuestas;

    }


    public List<String> Separacion(String separador) {
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

    public void borrarCorchetes(){
        Tokens.remove(0);
        Tokens.remove("<5,3>");
    }

}
