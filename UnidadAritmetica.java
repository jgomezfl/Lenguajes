public class UnidadAritmetica {
    String [] registros = new String[2];

	String C;
	String P;
	String N;
	String D;

	UnidadAritmetica(String num1, String num2){
		registros[0] = num1;
		registros[1] = num2;

		C = "0";
		P = "0";
		N = "0";
		D = "0";
	}

	public String suma(){
		String resultado = "";
        //0+0=0 ; 0+1=0 ; 1+0=0 ; 1+1=10
        String [] reglas = {"0","1","1","10"};

		// System.out.println(registros[0]);
		// System.out.println(registros[1]);

        String acarreo = "0";
        for(int i = 15 ; i >= 0 ; i--){
            String operAux = registros[0].charAt(i)+"+"+registros[1].charAt(i);
			// System.out.print(i+". acarreo: "+acarreo+" "+operAux+" ");
            if(acarreo.equals("0")){
                if(operAux.equals("0+0")){
                    resultado = reglas[0]+resultado;
					// System.out.println(resultado.charAt(0));
                }
                if(operAux.equals("0+1")){
                    resultado = reglas[1]+resultado;
					// System.out.println(resultado.charAt(0));
                }
                if(operAux.equals("1+0")){
                    resultado = reglas[2]+resultado;
					// System.out.println(resultado.charAt(0));
                }
                if(operAux.equals("1+1")){
                    resultado = reglas[3].charAt(1)+resultado;
                    acarreo = reglas[3].charAt(0)+"";
					// System.out.println(resultado.charAt(0));
                }
            }else{
                if(operAux.equals("0+0")){
                    resultado = acarreo+resultado;
                    acarreo = "0";
					// System.out.println(resultado.charAt(0));
                }
                if(operAux.equals("0+1")){
                    resultado = "0"+resultado;
					// System.out.println(resultado.charAt(0));
                }
                if(operAux.equals("1+0")){
                    resultado = "0"+resultado;
					// System.out.println(resultado.charAt(0));
                }
                if(operAux.equals("1+1")){
                    resultado = "1"+resultado;
					// System.out.println(resultado.charAt(0));
                }
            }
        }
        if(registros[0].charAt(0) == '0' && registros[1].charAt(0) == '0' && resultado.charAt(0) == '1'){
            registros[0] = "Sobrecarga";
			D = "1";
			return registros[0];
        }
        if(registros[0].charAt(0) == '1' && registros[1].charAt(0) == '1' && resultado.charAt(0) == '0'){
			registros[0] = "Sobrecarga";
			D = "1";
			return registros[0];
        }
		registros[0] = resultado;
		if(registros[0].equals("0000000000000000")){
			C = "1";
		}
		else if(registros[0].charAt(0) == '0'){
			P = "1";
		}
		else{
			N = "1";
		}
		return registros[0];
	}

	public String resta(){
		String numero = registros[1];
		if(registros[1].charAt(0) == '1'){
			registros[1] = valorAbsoluto(registros[1]);
		}
		else{
			registros[1] = ConvertirNegativo(registros[1]);
		}

		// System.out.println(registros[1]);
		String respuesta = "";
		
		respuesta = suma();
		registros[1] = numero;
		registros[0] = respuesta;
		return registros[0];
	}

	public String multiplicacion(){
		// Ley de signos
		int signo = 0; // 0 es positivo y 1 es negativo
		if((registros[0].charAt(0) == '0' && registros[1].charAt(0) == '1') || 
		    registros[1].charAt(0) == '0' && registros[0].charAt(0) == '1'){
				signo = 1;
		}

		String num1 = valorAbsoluto(registros[0]);
		String num2 = valorAbsoluto(registros[1]);

		// System.out.println(num1);
		// System.out.println(num2);
		// String [] reglas = {"0","0","0","1"};
		String [] sumas;

		int cont = 0;
		while(num2.charAt(cont) != '1') cont++;
		sumas = new String[16-cont];

		cont = 0;
		while(num1.charAt(cont) != '1') cont++;
		// System.out.println(cont);

		int aux = 0;
		for(int i = 15 ; i > (15 - sumas.length) ; i--){
			// System.out.print(num2.charAt(i)+": ");
			String respuesta = "";
			for(int j = 15 ; j >= cont ; j--){
				// System.out.println(num1.charAt(j));
				if(num2.charAt(i) == '1' && num1.charAt(j) == '1'){
					respuesta = "1"+respuesta;
					continue;
				}
				respuesta = "0"+respuesta;
			}
			// System.out.print(respuesta+" ");
			sumas[aux] = respuesta;
			// System.out.println(sumas[aux]+" ");
			aux ++;
		}

		for(int i = 0 ; i < sumas.length ; i++){
			for(int j = 0 ; j < i ; j++){
				sumas[i] += "0";
			}
			// for(int j = 1 ; j < sumas.length - i ; j++){
			// 	sumas[i] = "0"+sumas[i];
			// }
			// System.out.println(sumas[i]);
		}

		String aux1 = sumas[0];

		for(int i = 1 ; i < sumas.length ; i++){
			aux1 = SumaAuxiliar(aux1, sumas[i]);
			if(aux1.equals("SobreCarga")){
				registros[0] = "sobre carga";
				D = "1";
				return registros[0];
			}
		}
		
		aux = 16-aux1.length();
		for(int i = 0 ; i < aux ; i++){
			aux1 = "0" + aux1;
		}
		// System.out.println(aux1);

		if(signo == 1){
			aux1 = ConvertirNegativo(aux1);
		}

		registros[0] = aux1;
		if(registros[0].equals("0000000000000000")){
			C = "1";
		}
		else if(registros[0].charAt(0) == '0'){
			P = "1";
		}
		else{
			N = "1";
		}
		return registros[0];
	}

	public String division(){

		int signo = 0; // 0 es positivo y 1 es negativo
		if((registros[0].charAt(0) == '0' && registros[1].charAt(0) == '1') || 
		    registros[1].charAt(0) == '0' && registros[0].charAt(0) == '1'){
				signo = 1;
		}

		String num1 = valorAbsoluto(registros[0]);
		String num2 = valorAbsoluto(registros[1]);

		if(num2.equals("0000000000000001")){
			if(registros[0].equals("0000000000000000")){
				C = "1";
			}
			else if(registros[0].charAt(0) == '0'){
				P = "1";
			}
			else{
				N = "1";
			}
			return registros[0];
		}
		if(num2.equals("0000000000000000")){
			registros[0] = "Indeterminación";
			return registros[0];
		}
		if(num1.equals("0000000000000000")){
			registros[0] = "0000000000000000";
			C = "1";
			return registros[0];
		}

		int comparacion = num1.compareTo(num2);
		if (comparacion < 0) {
			registros[0] = "0000000000000000";
			C = "1";
			return registros[0];
        }

		num1 = registros[0];
		num2 = registros[1];

		registros[0] = valorAbsoluto(registros[0]);
		registros[1] = valorAbsoluto(registros[1]);
		
		int cont = 0;
		while(comparacion >= 0){
			// System.out.print(registros[0]+"-"+registros[1]+"(");
			registros[1] = ConvertirNegativo(registros[1]);
			// System.out.print(registros[1]+"): ");
			registros[0] = suma();
			// System.out.println(registros[0]);
			cont++;
			registros[1] = valorAbsoluto(registros[1]);
			comparacion = registros[0].compareTo(registros[1]);
		}
		if (signo == 1) {
			cont *= -1;
		}

		registros[0] = num1;
		registros[1] = num2;
		
		registros[0] = DecimalToBinario(cont);
		if(registros[0].equals("0000000000000000")){
			C = "1";
		}
		else if(registros[0].charAt(0) == '0'){
			P = "1";
		}
		else{
			N = "1";
		}
		return registros[0];
        
	}

	public String getC() {
		return C;
	}

	public String getP() {
		return P;
	}

	public String getN() {
		return N;
	}

	public String getD() {
		return D;
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

	public String SumaAuxiliar(String num1, String num2){
		String respuesta = "";
		if(num1.length() > num2.length()){
			for(int i = 0 ; i < (num1.length() - num2.length()) ; i++){
				num2 = "0"+num2;
			}
		}
		else{
			for(int i = 0 ; i < (num2.length() - num1.length()) ; i++){
				num1 = "0"+num1;
			}
		}
		
		String carry = "0";
		for(int i = num1.length()-1 ; i >= 0 ; i--){
			if(carry.equals("0")){
				if((num1.charAt(i) == '0' && num2.charAt(i) == '1') ||
				(num1.charAt(i) == '1' && num2.charAt(i) == '0')){
					respuesta = "1" + respuesta;
					continue;
				}
				respuesta = "0" + respuesta;
				if(num1.charAt(i) == '1' && num2.charAt(i) == '1'){
					carry = "1";
				}
				continue;
			}
			if(num1.charAt(i) == '0' && num2.charAt(i) == '0'){
				carry = "0";
				respuesta = "1" + respuesta;
				continue;
			}
			if(num1.charAt(i) == '1' && num2.charAt(i) == '1'){
				respuesta = "1" + respuesta;
				continue;
			}
			respuesta = "0" + respuesta;
		}
		if(carry.equals("1")){
			respuesta = "1" + respuesta;
		}
		if(respuesta.length() > 15){
			return "SobreCarga";
		}

		return respuesta;
	}

	public String valorAbsoluto(String num){
		if(num.charAt(0) == '0'){
			return num;
		}
        String respuesta = "";
		
		boolean first = false;
		for(int i = 15 ; i > 0 ; i--){
			if(!first && (num.charAt(i) == '1')){
				first = !first;
				respuesta = num.charAt(i)+respuesta;
				continue;
			}
			if(!first){
				respuesta = num.charAt(i)+respuesta;
				continue;
			}
			if(num.charAt(i) == '1'){
				respuesta = '0'+respuesta;
				continue;
			}
			respuesta = '1'+respuesta;
		}
		respuesta = '0'+respuesta;
		return respuesta;
	}

	public String ConvertirNegativo(String num){
		String respuesta = "";

		boolean first = false;
		for(int i = 15 ; i > 0 ; i--){
			if(!first && (num.charAt(i) == '1')){
				first = !first;
				respuesta = num.charAt(i)+respuesta;
				continue;
			}
			if(!first){
				respuesta = num.charAt(i)+respuesta;
				continue;
			}
			if(num.charAt(i) == '1'){
				respuesta = '0'+respuesta;
				continue;
			}
			respuesta = '1'+respuesta;
		}
		respuesta = '1'+respuesta;
		return respuesta;
	}
    
}
