package eda1.practicas.practica01;

import eda1.practicas.auxiliar.*;

import java.util.ArrayList;

public class Cita {
    private static int numCitas = 0; 
    private final int citaId; 
    private final ArrayList<String> valoraciones; 

    public static void inicializaNumCitas() {	
    	numCitas = 0; 
    }

    public Cita() {
        this.citaId = ++numCitas;
        this.valoraciones = new ArrayList<String>();
    }

    public Cita(int citaId) {
        this.citaId = citaId;
        this.valoraciones = null; 	
    }	

    public int getCitaId() {
        return this.citaId;
    }

    public boolean addValoracion(String valoracion) {
    	if(valoracion == null || valoracion.trim().isEmpty()) return false;
    	return this.valoraciones.add(valoracion.trim().toLowerCase());
    }

    public boolean contienePalabra(String palabra){
    	if(palabra == null || palabra.trim().isEmpty()) return false;
        for (String v : valoraciones) {
			if(v.contains(palabra.trim().toLowerCase())) return true;
		}
        return false;
    }

    public void clear() {
        this.valoraciones.clear();
    }

    @Override
    public String toString() {
    	return "Cita #" + Format.formatInt(citaId, 4) + " -> " + (this.valoraciones.isEmpty() ? "<sin texto>" : this.valoraciones.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; 
        if (!(o instanceof  Cita)) return false; 
        return this.citaId == ((Cita) o).citaId;
    }
}
