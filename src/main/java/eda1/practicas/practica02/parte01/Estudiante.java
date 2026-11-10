package eda1.practicas.practica02.parte01;

import java.util.*;

import eda1.practicas.auxiliar.Format;

public class Estudiante implements Comparable<Estudiante>, Iterable<AsignaturaNotas>{
    private final String alumnoId; 
    private final ArrayList<AsignaturaNotas> matricula; 

    public Estudiante(String alumnoId) {
    	this.alumnoId = alumnoId.toLowerCase();
    	this.matricula = new ArrayList<>();
    }

    public void addAsignaturas(Asignatura... asignaturas) {
    	for (Asignatura as : asignaturas) {
    		AsignaturaNotas an = new AsignaturaNotas(as);
    		if(!this.matricula.contains(an)) {
    			this.matricula.add(an);
    		}
		}
    }

    public boolean addNotas(String asignaturaId, Double... notas) {
    	if(this.matricula.indexOf(new AsignaturaNotas(asignaturaId)) == -1) return false;
        this.matricula.get(this.matricula.indexOf(new AsignaturaNotas(asignaturaId))).addNotas(notas);
        return true;
    }

    public String getNotaMedia() {
        double suma = .0;
        for (AsignaturaNotas an : matricula) {
			suma += Double.parseDouble(an.getNotaMedia());
		}
        return this.matricula.size() == 0 ? "0.00" : 
        	Format.formatDouble(suma / this.matricula.size(), 2);
    }

    public String getNotaMedia(String asignaturaId) {
    	int index = this.matricula.indexOf(new AsignaturaNotas(asignaturaId));
        return index == -1 ? null : this.matricula.get(index).getNotaMedia();
    }

    public void clear() {
    	for (AsignaturaNotas an : matricula) {
			an.clear();
		}
        this.matricula.clear();
    }

    @Override
    public String toString() {
        String result = "Estudiante con id = " + this.alumnoId; 
        this.matricula.sort(new AsignaturaNotasComp());
        for (AsignaturaNotas an : matricula) {
			result += "\n\t" + an.toString();
		}
        return result + "\n";
    }

    @Override
    public int compareTo(Estudiante other) {
        return this.alumnoId.compareTo(other.alumnoId);
    }

    @Override
    public Iterator<AsignaturaNotas> iterator() {
        return this.matricula.iterator();
    }
    
}
