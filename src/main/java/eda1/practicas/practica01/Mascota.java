package eda1.practicas.practica01;

import java.util.Iterator;
import java.util.LinkedList;

public class Mascota implements Comparable<Mascota>, Iterable<Cita> {
    private final String nombre;
    private final String especie;
    private final LinkedList<Cita> historial;

    public Mascota(String nombre, String especie) {
    	this.nombre = nombre == null || nombre.trim().isEmpty() ? "sinNombre" : nombre.trim().toLowerCase();
    	this.especie = especie == null || especie.trim().isEmpty() ? "sinEspecie" : especie.trim().toLowerCase();
    	this.historial = new LinkedList<Cita>();
    }	

    public Cita addCita() {
    	Cita c = new Cita(); 
    	this.historial.add(c); 
    	return c; 
    }

    public Cita getCita(int citaId) {
    	if(citaId < 0) return null; 
    	int index = this.historial.indexOf(new Cita(citaId)); 
    	return index < 0 ? null : this.historial.get(index); 
 
    }

    public void clear() {
    	for (Cita c : historial) {
			c.clear();
		}
        this.historial.clear();
    }

    public int size() {
        return this.historial.size();
    }

    @Override
    public String toString() {
    	return this.historial.size() == 1 ? (nombre + " <" + especie + "> [1 cita]") : (nombre + " <" + especie + "> [" + historial.size() + " citas]"); 
    }

    public String toStringExtended() {
        return this.toString() + ": " + this.historial.toString();
    }

    @Override
    public int compareTo(Mascota other) {
    	int cmp = this.nombre.compareTo(other.nombre); 
    	return cmp != 0 ? cmp : this.especie.compareTo(other.especie); 
    }

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (!(other instanceof Mascota)) return false;
        return this.compareTo((Mascota)other) == 0;
    }

    @Override
    public Iterator<Cita> iterator() {
        return this.historial.iterator(); 
    }
}
