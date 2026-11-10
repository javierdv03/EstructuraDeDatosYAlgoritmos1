package eda1.practicas.prueba01;

import java.util.ArrayList;
import eda1.practicas.auxiliar.AVLTree;
import eda1.practicas.auxiliar.Par;

public class ArrayListPar_vs_TreePar {
	
	private ArrayList<Par<String, AVLTree<Integer>>> array;
	private AVLTree<Par<String, ArrayList<Integer>>> arbol;
	
	public ArrayListPar_vs_TreePar() {
		this.array = new ArrayList<>();
		this.arbol = new AVLTree<>();
	}
	
	public void addArbol(String clave, Integer...datos) {
		Par<String, ArrayList<Integer>> current = this.arbol.find(new Par<>(clave, null));
		if (current == null) {
			this.arbol.add(current = new Par<>(clave, new ArrayList<>()));
		}
		for (Integer dato: datos) {
			if (current.getValue().contains(dato)) continue;
			current.getValue().add(dato);
		}
		current.getValue().sort(null);
	}
	/** Metodo addArray. Primeramente, buscamos en la lista si existe algun par con la clave
	 *  que nos proporcionan por parametro mediante el metodo "indexOf" de la clase ArrayList.
	 *  Una vez hacemos esta comprobación, si el par esta dentro de la lista simplemente lo guardamos 
	 *  en la variable "current" y si no esta lo añadimos (guardandolo previamente tambien en la variable
	 *  curr) con un nuevo arbol AVL vacio. Seguidamente, iteramos por el array de datos y si el arbol contiene
	 *  dicho dato saltamos a la siguiente iteracion mientras que si no esta lo añadimos. Por ultimo, ordenamos la
	 *  lista en orden ascendente (naturalOrder).
	 * @param clave 
	 * @param datos
	 */
	public void addArray(String clave, Integer...datos) {
		int index = this.array.indexOf(new Par<>(clave, null));
		Par<String, AVLTree<Integer>> current;
		if (index == -1) {
			current=new Par<String, AVLTree<Integer>>(clave,new AVLTree<>());
			this.array.add(current); 
		} else {
			current = array.get(index);
		}
		for (Integer dato: datos) { 
			if (current.getValue().contains(dato)) continue;
			current.getValue().add(dato);
		}
		this.array.sort(null);
	}
	
	public String arbolToString() {
		return this.arbol.toString();
	}
	
	public String arrayToString() {
		return this.array.toString();
	}
	
	public static void main(String[] args) {
		ArrayListPar_vs_TreePar prueba = new ArrayListPar_vs_TreePar();
		
		//Insertamos elementos en el árbol
		prueba.addArbol("clave02", 0, 5, 2, 3, 8, 4, 4, 5);
		prueba.addArbol("clave01", 3, 1, 2, 4, 2, 4);
		prueba.addArbol("clave02", 0, 8, 6, 7, 8);
		prueba.addArbol("clave01", 1, 5, 3, 5, 8);
		//Igual pero con el array
		prueba.addArray("clave02", 0, 5, 2, 3, 8, 4, 4, 5);
		prueba.addArray("clave01", 3, 1, 2, 4, 2, 4);
		prueba.addArray("clave02", 0, 8, 6, 7, 8);
		prueba.addArray("clave01", 1, 5, 3, 5, 8);
	
		System.out.println(prueba.arrayToString());
		//Aquí puedes mostrar por consola prueba.arbolToString() y prueba.arrayToString()
	
		System.out.println(prueba.arbolToString().equals(prueba.arrayToString()) ? "¡¡¡OK!!!" : "¡¡¡Error!!!");
	}
}

