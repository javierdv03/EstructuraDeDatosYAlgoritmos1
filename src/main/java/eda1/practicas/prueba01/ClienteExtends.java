package eda1.practicas.prueba01;

import java.util.ArrayList;

import eda1.practicas.auxiliar.AVLTree;
import eda1.practicas.auxiliar.Format;
import eda1.practicas.auxiliar.Par;
import eda1.practicas.practica02.parte02.Cliente;
import eda1.practicas.practica02.parte02.Mascota;

public class ClienteExtends extends Cliente{

	public ClienteExtends(String nombre) {
		super(nombre);
	}
     
	/**Metodo resume. Declaramos primeramente el arbol de pares que pasaremos como resultado.
	 * Inicializamos un contador a 0 que sera el resultado de la valoracion maxima. Seguidamente,
	 * iteramos sobre el data de la clase Cliente y de cada par, iteramos sobre sus citas.
	 * De cada cita, sacamos su valoracion maxima comparandola en cada iteracion con cont. Finalmente,
	 * lo anadimos al arbol resultado cada par con la maxima valoracion y el nombre y especie de la mascota (getKey)
	 * 
	 * @return toString() del arbol result
	 */
	public String resume() {
        AVLTree<Par<Mascota,Integer>> result = new AVLTree<>(); 
        int cont = 0; 
        for(Par<Mascota, ArrayList<ArrayList<String>>> it : this.data) { 
        	for(ArrayList<String> it2 : it.getValue()) { 
        		cont = 0;
        		if(cont < it2.size()) cont += it2.size(); 
        	}
        	result.add(new Par(it.getKey(),cont));
        }
		return result.toString();
	}
	
	public static void main(String[] args) {
		ClienteExtends cliente = new ClienteExtends("pepe");

		cliente.addMascota("mascota01", "especie");
		cliente.addMascota("mascota02", "especie");
		cliente.addMascota("mascota03", "especie");
		
		for (int i=0; i<50; i++) {
			cliente.addCita("mascota01", "especie");
			for (int j=0; j<10; j++) {
				cliente.addValoracion("mascota01", "especie", i, "valoración" + Format.formatInt(j));
			}
			cliente.addCita("mascota02", "especie");
			for (int j=0; j<20; j++) {
				cliente.addValoracion("mascota02", "especie", i, "valoración" + Format.formatInt(j));
			}
			cliente.addCita("mascota03", "especie");
			for (int j=0; j<30; j++) {
				cliente.addValoracion("mascota03", "especie", i, "valoración" + Format.formatInt(j));
			}
		}

		String salidaEsperada = "[mascota01-especie <10>, mascota02-especie <20>, mascota03-especie <30>]";
        System.out.println(cliente.resume());		
		System.out.println(salidaEsperada.equals(cliente.resume()) ? "¡¡¡OK!!!" : "¡¡¡Error!!!");
	}
}
