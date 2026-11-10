package eda1.practicas.practica02.parte02;

import java.util.ArrayList;
import java.util.Iterator;

import eda1.practicas.auxiliar.AVLTree;
import eda1.practicas.auxiliar.Par;

public class GestionClientes implements Iterable<Par<String, ArrayList<Par<String, Integer>>>> {
	
	private AVLTree<Cliente> info;
	
	public GestionClientes() {
		this.info = new AVLTree<>();
	}

	public void addCliente(String clienteId) {
		this.info.add(new Cliente(clienteId));
	}

	public boolean addMascota(String clienteId, String mascotaId, String especieId) {
		Cliente cl = this.info.find(new Cliente(clienteId));
		if(cl == null) return false;
		return cl.addMascota(mascotaId, especieId); 
	}

	public Integer addCita(String clienteId, String mascotaId, String especieId) {
		Cliente cl = this.info.find(new Cliente(clienteId));
		if(cl == null) return null;
		return cl.addCita(mascotaId, especieId);
	}

	public boolean addValoracion(String clienteId, String mascotaId, String especieId, int citaId, String valoracion) {
		Cliente cl = this.info.find(new Cliente(clienteId));
		if(cl == null) return false;
		return cl.addValoracion(mascotaId, especieId, citaId, valoracion);
	}

	public int size() {
		return this.info.size();
	}

	public void clear() {
		this.info.clear();
	}

	@Override
	public Iterator<Par<String, ArrayList<Par<String, Integer>>>> iterator() {
		AVLTree<Par<String, ArrayList<Par<String, Integer>>>> arbol = new AVLTree<>();
		for(Cliente cl : this.info) { 
			Par<String, ArrayList<Par<String, Integer>>> par = new Par<>(cl.getNombre(), new ArrayList<>());
			arbol.add(par); 
			for(Par<Mascota, ArrayList<ArrayList<String>>> mascota : cl){ 
				par.getValue().add(new Par<>(mascota.getKey().toString(), mascota.getValue().size()));
			}
		}
		return arbol.iterator();
	}

}
