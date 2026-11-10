package eda1.practicas.prueba01;

import java.util.ArrayList;

import eda1.practicas.auxiliar.AVLTree;
import eda1.practicas.auxiliar.Format;
import eda1.practicas.auxiliar.Par;

public class TengoErrores {
	
	private AVLTree<Par<String, AVLTree<Par<String, ArrayList<String>>>>> datos;  
	
	public TengoErrores() {
		this.datos = new AVLTree<>();
	}
    /**Metodo add. El metodo nos pasa por parametro dos claves, la clave01 que corresponde con el
     * estudiante y la clave02 que corresponde con la asignatura. Primeramente, buscamos en el arbol 
     * de datos si existe el estudiante. Si no existe, lo anadimos con un nuevo arbol AVL vacio. Luego,
     * pasamos a la comprobacion de la asignatura. Al igual que con el estudiante, comprobamos si dentro de este segundo
     * arbol se encuentra la asignatura, y si no se encuentra la añadimos con una lista de caracteres vacia.
     * Por ultimo, iteramos sobre valoraciones y sacamos cada valoracion, anadiendola a la lista mediante un formateo
     * de Double a tipo String, previamente habiendo comprobado que valoracion no es nula.
     * @param clave01, correspondiente con el estudiante
     * @param clave02, correspondiente con la asignatura
     * @param valoraciones
     */
	public void add(String clave01, String clave02, Double...valoraciones) { 
		Par<String, AVLTree<Par<String, ArrayList<String>>>> par1 = this.datos.find(new Par<>(clave01, null)); 
		if (par1 == null) { 
			this.datos.add(par1 = new Par<>(clave01, new AVLTree<Par<String, ArrayList<String>>>())); 
		}
		Par<String, ArrayList<String>> par2 = par1.getValue().find(new Par<>(clave02, null)); 
		if (par2 == null) { 
			par1.getValue().add(par2 = new Par<>(clave02, new ArrayList<String>()));
		} 
		for (Double valoracion: valoraciones) {
			if(valoracion == null) continue; 
			par2.getValue().add(Format.formatDouble(valoracion,3)); 
		}
	}
	
	@Override
	public String toString() {
		return this.datos.toString();
	}
	
	public static void main(String[] args) {
		TengoErrores arreglame = new TengoErrores();
		arreglame.add("estudiante02", "asignatura01", 1., 9.);
		arreglame.add("estudiante02", "asignatura02", 10., 6.);
		arreglame.add("estudiante01", "asignatura01", 8., null, 8.);
		arreglame.add("estudiante01", "asignatura02", 8., 4.);
		
		String salidaEsperada = "[estudiante01 <[asignatura01 <[8.000, 8.000]>, asignatura02 <[8.000, 4.000]>]>, estudiante02 <[asignatura01 <[1.000, 9.000]>, asignatura02 <[10.000, 6.000]>]>]";
		
		System.out.println(arreglame.toString());

		System.out.println(salidaEsperada.equals(arreglame.toString()) ? "¡¡¡OK!!!" : "Error");
	}

}