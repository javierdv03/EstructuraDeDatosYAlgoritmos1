package eda1.ejercicios.ejercicio01;

import java.util.ArrayList;

public class Estilo {
	public static void main(String[] args) {
		ArrayList<String> arr = new ArrayList<String>();
//		int numNeg = 0;
		//problema 1
		for(int i = 1; i <= 50; i++) {
			arr.add(Math.random() < .5 ? String.valueOf(i) : String.valueOf(-i)); 
			//operador ternario para minimizar el código
		}
		System.out.println(arr.toString());
		//problema 2
//		for (String s : arr) {
//			numNeg = Integer.parseInt(s) < 0 ? numNeg+1 : numNeg;
			//En el video, en el método de foreach se ha utilizado valueOf para obtener 
			//cada valor del arraylist. Yo he optado por parsear cada String en un entero
			//que a fin de cuentas es el mismo resultado
//		}
//		System.out.println("Numeros negativos: " + numNeg);
//		System.out.println("antes de modificar la estructura " + arr.toString());
		
		//problema 3
//		for (int i = 0; i < arr.size(); i++) {
//			
//			if(Integer.valueOf(arr.get(i)) < 0) arr.set(i, String.valueOf(-Integer.valueOf(arr.get(i))));
//		}
//		
//		System.out.println("despues de modificar la estructura " + arr.toString());
		//problema 4
		int i = 0;
		while(true) {
			if(i == arr.size()) {
				System.out.println("No se ha encontrado ningun valor negativo");
				break;
			}
			//Comprobamos en este if si es mayor que 0 y no igual, ya que el arraylist
			//contiene valores del -50 al 50 sin contar el 0 (i inicializada a 1)
			if(Integer.parseInt(arr.get(i)) > 0) {
				i++; continue;
			}
			System.out.println("He encontrado el primer valor negativo: " + arr.get(i));
			break;
		}
		System.out.println("CORRECTO");
		

		
	}
}
