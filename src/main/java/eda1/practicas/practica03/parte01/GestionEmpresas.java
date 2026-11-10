package eda1.practicas.practica03.parte01;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class GestionEmpresas {
	private final TreeMap<String, TreeMap<String, TreeSet<String>>> datos = new TreeMap<>();

	public boolean load(String fileName) {
		Scanner scan = null;
		this.datos.clear();
		try {
			scan = new Scanner(new File(fileName));
		} catch (IOException e) {
			return false;
		}
		while (scan.hasNextLine()) {
			String line = scan.nextLine().trim();
			if (line.isEmpty()) continue;
			if (line.startsWith("@")) continue;
			String[] tokens = line.split(" - ");
			add(tokens[0], tokens[1], tokens[2]);
		}
		scan.close();
		return true;
	}

	public boolean add(String empresaId, String proyectoId, String ciudad) {
		TreeMap<String, TreeSet<String>> proyCurr = this.datos.get(empresaId); 
		if (proyCurr == null) {
		    this.datos.put(empresaId, proyCurr = new TreeMap<String, TreeSet<String>>());
		}
		TreeSet<String> ciudadesCurr = proyCurr.get(proyectoId);
		if (ciudadesCurr == null) {
			proyCurr.put(proyectoId, ciudadesCurr = new TreeSet<String>());
		}
		return ciudadesCurr.add(ciudad);
	}

	public int size() {
		return this.datos.size();
	}

	public Integer getNumProyectosEmpresa(String empresaId) {
		TreeMap<String, TreeSet<String>> proyectoCurr = this.datos.get(empresaId);
		return proyectoCurr == null ? null : proyectoCurr.size();
	}

	public Integer getNumCiudadesProyecto(String proyectoId) {
		for(TreeMap<String, TreeSet<String>> proyCurr : this.datos.values()) {
			TreeSet<String> aux = proyCurr.get(proyectoId);
			if(aux != null) return aux.size();
		}
		return null;
	}
	
	public Integer getNumCiudadesEmpresa(String empresaId) {
		TreeMap<String, TreeSet<String>> aux = this.datos.get(empresaId);
		if(aux == null) return null; 
		TreeSet<String> res = new TreeSet<String>();
		for(TreeSet<String> it : aux.values()) {
			res.addAll(it);
		}
		return res.size();
	}

	@Override
	public String toString() {
		String result = "";
        for(Entry<String, TreeMap<String, TreeSet<String>>> it : this.datos.entrySet()) { 
        	result += it.getKey() + " -> [";
        	for(Entry<String, TreeSet<String>> it2 : it.getValue().entrySet()) {
        		result += it2.getKey() + ": " + it2.getValue().toString() + (it2.equals(it.getValue().lastEntry()) ? "" : ", ");
         	}
        	result += "]\n";
        }
		return result;
	}

	public TreeSet<String> getEmpresasCiudad(String ciudad) {
		TreeSet<String> empresasIdAux = new TreeSet<>();
		for(Entry<String, TreeMap<String, TreeSet<String>>> it: this.datos.entrySet()) {
			for(TreeSet<String> ciudadesCurr : it.getValue().values()) { 
				if(ciudadesCurr.contains(ciudad)) { 
					empresasIdAux.add(it.getKey()); 
					break; 
				}
			}
		}
		return empresasIdAux;
	}

	public ArrayList<String> getProyectosCiudad(String ciudad) {
		ArrayList<String> proyectosIDaux = new ArrayList<>();
		for(TreeMap<String, TreeSet<String>> it : this.datos.values()) { 
			for(Entry<String, TreeSet<String>> it2: it.entrySet()) {
				if(it2.getValue().contains(ciudad)) {
					proyectosIDaux.add(it2.getKey());
				}
			}
		}
		return proyectosIDaux;
	}
	
	 public TreeSet<String> getCiudadesEmpresa(String empresaId) {
		 
			TreeMap<String, TreeSet<String>> aux = this.datos.get(empresaId);
			if(aux == null) return null;
			TreeSet<String> res = new TreeSet<String>();
			
			for(TreeSet<String> it : aux.values()) {
				res.addAll(it);
			}
			return res;
		
	}
}
