package eda1.practicas.practica02.parte01;


import eda1.practicas.auxiliar.AVLTree;
import eda1.practicas.auxiliar.Format;
import eda1.practicas.auxiliar.Par;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class GestionEstudiantes implements Iterable<Par<Integer, ArrayList<Par<String, String>>>> {
    private final String centroId; 
    private final AVLTree<Asignatura> asignaturasOfertadas; 
    private final AVLTree<Estudiante> estudiantesMatriculados;

    public GestionEstudiantes(String centroId) {
        this.centroId = centroId.trim();
        this.asignaturasOfertadas = new AVLTree<>();
        this.estudiantesMatriculados = new AVLTree<>();
    }

    public void clear() {
        this.asignaturasOfertadas.clear();
        this.estudiantesMatriculados.clear();
    }

    public void addAsignaturas(Asignatura... asignaturas) {
        for (Asignatura asignatura: asignaturas) {
        	this.asignaturasOfertadas.add(asignatura);
        }
    }

    public void addEstudiantes(Estudiante... estudiantes){
        for (Estudiante estudiante: estudiantes) {
        	this.estudiantesMatriculados.add(estudiante);
        }
    }

    public boolean addMatricula(String estudianteId, String...asignaturasId) {
    	Estudiante est = this.estudiantesMatriculados.find(new Estudiante(estudianteId));
    	if(est == null) return false;
    	for (String asig : asignaturasId) {
			Asignatura a = this.asignaturasOfertadas.find(new Asignatura (asig));
			if(a == null) continue;
			est.addAsignaturas(a);
		}
        return true;
    }

    public boolean addNotas(String estudianteId, String asignaturaId, Double... notas){
    	Estudiante estudianteCurr 
    	= this.estudiantesMatriculados.find(new Estudiante(estudianteId));
    	if(estudianteCurr == null) return false;
        return estudianteCurr.addNotas(asignaturaId, notas);
    }

    public String getNotaMedia(String estudianteId) {
    	Estudiante e = this.estudiantesMatriculados.find(new Estudiante(estudianteId));
    	return e == null ? null : e.getNotaMedia();
    }

    public String getNotaMedia(String estudianteId, String asignaturaId) {
    	Estudiante e = this.estudiantesMatriculados.find(new Estudiante(estudianteId));
    	return e == null ? null : e.getNotaMedia(asignaturaId);
    }

    public String getNotaMediaAsignatura(String asignaturaId) {
    	int cont = 0;
    	double suma = .0;
    	for (Estudiante est : this.estudiantesMatriculados) {
    		String n = est.getNotaMedia(asignaturaId);
    		if(n != null) {
    		suma += Double.parseDouble(n);
    		cont++;
    		}
    		
		}
        return cont == 0 ? null : Format.formatDouble(suma/cont);
    }

    public String getEquipoDocenteEstudiante(String estudianteId) {
    	Estudiante est = this.estudiantesMatriculados.find(new Estudiante(estudianteId));
    	if(est == null) return "[]";
    	ArrayList<String> result = new ArrayList<>();
    	for (AsignaturaNotas an : est) {
			for (String prof : an.getAsignatura()) {
				if(!result.contains(prof)) result.add(prof);
			}
		}
    	result.sort(null); 
        return result.toString();
    }

    public boolean load(String fileName) {
        Scanner scan;
        String line;
        String[] items;
        this.asignaturasOfertadas.clear();
        this.estudiantesMatriculados.clear();
        try {
            scan = new Scanner(new File(fileName));
        } catch (IOException e) {
            return false;
        }
        while (scan.hasNextLine()) {
            line = scan.nextLine().trim();
            if (line.isEmpty()) continue;
            if (line.startsWith("#")) continue;
            if (line.startsWith("@")) {
                items = line.split("[ ]+");
                switch (items[0]){
                    case "@Asignaturas":
                    	int nAsignaturas = Integer.parseInt(items[1]);
                    	for(int i = 0; i < nAsignaturas; i++) {
                    		line = scan.nextLine().trim();
                    		items = line.split("[ ]+"); 
                    		/**[ip | 1 | julio | joaquin]*/
                    		Asignatura asign = new Asignatura(items[0], Integer.parseInt(items[1]));
                    		items = Arrays.copyOfRange(items, 2, items.length);
                    		asign.addDocentes(items);
                    		this.asignaturasOfertadas.add(asign);
                    	}
                        break;
                    case "@Estudiantes":
                    	int nEstud = Integer.parseInt(items[1]);
                    	for(int i = 0; i < nEstud; i++) {
                    		line = scan.nextLine().trim();
                    		Estudiante e = new Estudiante(line);
                    		this.estudiantesMatriculados.add(e);
						}
                        break;
                    case "@Matriculas":
                    	int nMatri = Integer.parseInt(items[1]);
                    	for (int i = 0; i < nMatri; i++) {
							line = scan.nextLine().trim();
							items = line.split("[ ]+");
							addMatricula(items[0], Arrays.copyOfRange(items, 1, items.length));
						}
                        break;
                    case "@Notas":
                        int nNotas = Integer.parseInt(items[1]);
                        for(int i = 0; i < nNotas; i++) {
                        	line = scan.nextLine().trim();
                            items = line.split("[ \t]+");
                        	Double[] d = new Double[items.length - 2];
                        	for(int j = 0; j < d.length; j++) {
                        		d[j] = Double.parseDouble(items[j+2]);
                        	}
                        	addNotas(items[0], items[1], d);
                        	
                        }
                        break;
                    default:
                        return false;
                }
            }
        }
        scan.close();
        return true;
    }

    @Override
    public String toString() {
        return this.centroId;
    }

	@Override
	public Iterator<Par<Integer, ArrayList<Par<String, String>>>> iterator() {
		AVLTree<Par<Integer, ArrayList<Par<String, String>>>> arb = new AVLTree<>();
		for(Asignatura asign : this.asignaturasOfertadas) { 
			int c = asign.getCuatrimestre(); 
			String asignId = asign.getAsignaturaId(); 
			String media = getNotaMediaAsignatura(asignId); 
			Par<Integer, ArrayList<Par<String, String>>> parActual = arb.find(new Par<>(c, null));
			if(parActual == null) { 
				arb.add(parActual = new Par<>(c, new ArrayList<>())); 
			}
			parActual.getValue().add(new Par<>(asignId, media)); 
		}
		
		return arb.iterator();
	}

}
