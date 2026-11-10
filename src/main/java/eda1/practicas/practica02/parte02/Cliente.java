package eda1.practicas.practica02.parte02;

import eda1.practicas.auxiliar.AVLTree;
import eda1.practicas.auxiliar.Par;

import java.util.ArrayList;
import java.util.Iterator;

public class Cliente implements Comparable<Cliente>, Iterable<Par<Mascota, ArrayList<ArrayList<String>>>> {
    private final String nombre;
    private final AVLTree<Par<Mascota, ArrayList<ArrayList<String>>>> data;

    public Cliente(String nombre) {
        this.nombre = nombre.trim().toLowerCase();
        this.data = new AVLTree<>();
    }

    public boolean addMascota(String nombre, String especie) {
        Par<Mascota, ArrayList<ArrayList<String>>> mascotaAux = new Par<Mascota, ArrayList<ArrayList<String>>>(new Mascota(nombre,especie), null);
        Par<Mascota, ArrayList<ArrayList<String>>> mascotaCurr = this.data.find(mascotaAux);
        if(mascotaCurr != null) return false;
        	mascotaAux.setValue(new ArrayList<>());
        	return this.data.add(mascotaAux);
    }

    public Integer addCita(String nombre, String especie) {
        Par<Mascota, ArrayList<ArrayList<String>>> mascotaCurr = this.data.find(new Par<Mascota, ArrayList<ArrayList<String>>>(new Mascota(nombre,especie), null));
        if(mascotaCurr == null) return null;
    	mascotaCurr.getValue().add(new ArrayList<>());
    	return mascotaCurr.getValue().size()-1;
    }

    public boolean addValoracion(String nombre, String especie, int citaId, String valoracion) {
    	if(citaId < 0 || valoracion == null || valoracion.isBlank()) return false;
        Par<Mascota, ArrayList<ArrayList<String>>> mascotaCurr = this.data.find(new Par<>(new Mascota(nombre,especie), null));
        if(mascotaCurr == null) return false;
        if(citaId >= mascotaCurr.getValue().size()) return false; 
        return mascotaCurr.getValue().get(citaId).add(valoracion); 
        
        
    }

    public ArrayList<String> getValoraciones(String nombre, String especie, int citaId) {
        if (citaId < 0) return null;
        Par<Mascota, ArrayList<ArrayList<String>>> mascotaCurr = this.data.find(new Par<>(new Mascota(nombre,especie), null));
        if(mascotaCurr == null || citaId >= mascotaCurr.getValue().size()) return null;
        return mascotaCurr.getValue().get(citaId);
    }
    
    public void clear() {
    	for (Par<Mascota, ArrayList<ArrayList<String>>> aux : data) {
			for (ArrayList<String> aux2 : aux.getValue()) {
				aux2.clear();
			}
			aux.getValue().clear();
		}
    	this.data.clear();
    }

    public String getNombre() {
    	return this.nombre;
    }
    
    public int size() {
        return this.data.size();
    }

    @Override
    public String toString() {
        return this.nombre + " -> " + this.data.isEmpty() == null ? "<>" : this.data.toString();
    }

	@Override
	public int compareTo(Cliente other) {
		return this.nombre.compareTo(other.nombre);
	}

	@Override
	public Iterator<Par<Mascota, ArrayList<ArrayList<String>>>> iterator() {
		return this.data.iterator();
	}
}
