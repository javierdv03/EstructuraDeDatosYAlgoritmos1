package eda1.practicas.practica02.parte03;


import eda1.practicas.auxiliar.Format;
import java.util.ArrayList;

public class GestionEstudiantes {
    private final String centroId;
    private final MyTreeMap<String, MyTreeMap<String, ArrayList<Double>>> matriculas; 

    public GestionEstudiantes(String centroId) {
        this.centroId = centroId.trim();
        this.matriculas = new MyTreeMap<>();
    }

    public void clear() {
        this.matriculas.clear();
    }
    
    
    public boolean addNotas(String estudianteId, String asignaturaId, Double... notas){
        MyTreeMap<String, ArrayList<Double>> notasAsignaturas = this.matriculas.get(estudianteId);
        if (notasAsignaturas == null) {
        	this.matriculas.put(estudianteId, notasAsignaturas = new MyTreeMap<>());
        }
        ArrayList<Double> notasAsignatura = notasAsignaturas.get(asignaturaId);
        if (notasAsignatura == null) {
        	notasAsignaturas.put(asignaturaId, notasAsignatura = new ArrayList<>());
        }
        for (Double n : notas) {
			notasAsignatura.add(n == null ? 0.0 : n);
		}

        return true;
    }

    public String getNotaMedia(String estudianteId) {
        MyTreeMap<String, ArrayList<Double>> notasAsignaturas = this.matriculas.get(estudianteId);
        if(notasAsignaturas == null) return null;
        double suma = 0;
        int cont = 0;
        for (ArrayList<Double> notas : notasAsignaturas.valueSet()) { 
			cont += notas.size();
			for (Double nota : notas) {
				suma += nota;
			}
		}
        return Format.formatDouble(suma/cont);
    }

    public String getNotaMedia(String estudianteId, String asignaturaId) {
    	MyTreeMap<String, ArrayList<Double>> notasAsignaturas = this.matriculas.get(estudianteId);
    	if(notasAsignaturas == null) return null;
    	ArrayList<Double> notas = notasAsignaturas.get(asignaturaId); 
    	if(notas == null) return null;
    	double suma = 0;
    	for (Double nota : notas) {
			suma += nota;
		}
        return Format.formatDouble(suma/notas.size());
    }

    public String getNotaMediaAsignatura(String asignaturaId) {
    	int cont = 0;
    	double suma = .0;
    	for (MyTreeMap<String, ArrayList<Double>> asig : this.matriculas.valueSet()) {
		
    		ArrayList<Double> notas = asig.get(asignaturaId);
    		if(notas == null) continue;
    		for(Double n : notas) {
    			suma += n;
    			cont++;
    	}		
	}
        return cont == 0 ? null : Format.formatDouble(suma/cont);
    }
 
    @Override
    public String toString() {
        return this.centroId;
    }
}