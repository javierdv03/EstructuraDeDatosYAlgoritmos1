package eda1.practicas.practica03.parte02;

 import java.io.File;
 import java.io.IOException;
 import java.util.Arrays;
 import java.util.Map.Entry;
 import java.util.Scanner;
 import java.util.TreeMap;
 import java.util.TreeSet;

public class GestionRepositorios {
	private final TreeMap<String, TreeMap<String, Integer>> articulos;
	private final TreeMap<String, TreeMap<String, TreeSet<String>>> datos;
	
	public GestionRepositorios() {
		this.articulos = new TreeMap<>();
		this.datos = new TreeMap<>();
	}
 
	public boolean add(String repositorioId, String articuloId, String ...autoresId) {
		String repositorio = getRepositorio(articuloId);
		if(repositorio != null && !repositorioId.equals(repositorio)) return false;
		
		TreeMap<String, TreeSet<String>> aux = this.datos.get(repositorioId);
		
		if(aux == null) this.datos.put(repositorioId, aux = new TreeMap<>());
		
		TreeSet<String> tsautores = aux.get(articuloId);
		
		if(tsautores == null) {
			aux.put(articuloId, tsautores = new TreeSet<>());
			this.articulos.put(articuloId, new TreeMap<>());
		}
		for (String autor: autoresId) {
			tsautores.add(autor);
		} 
		return true;
	}
	
	public String getRepositorio(String articuloId) { 
		for (Entry<String, TreeMap<String, TreeSet<String>>> it : this.datos.entrySet()) {
			if(it.getValue().containsKey(articuloId)) return it.getKey();
		}
		return null;
	}
	
	public boolean loadArticulo(String articuloId, String file) {
		TreeMap<String, Integer> palabrasFrecuencia = this.articulos.get(articuloId);
		if(palabrasFrecuencia == null) return false;
		Scanner scan = null;
		try {
			scan = new Scanner(new File(file));
		}catch (IOException e) {
			return false;
		}
		while (scan.hasNextLine()){
			String linea = scan.nextLine();
			if (linea.isEmpty()) continue;
			for(String palabra : linea.split("[0123456789/(/)+-;,.¿?¡! ]+")) { 
				if(palabra.isEmpty() || StopWords.isStopWord(palabra.toLowerCase().trim())) continue;
				Integer n = palabrasFrecuencia.get(palabra.toLowerCase()); 
				palabrasFrecuencia.put(palabra.toLowerCase(), n == null ? 1 : n + 1);
			}
		}
		return true;
	}
	
	public boolean load(String directorioEntrada, String file) { 
		Scanner scan = null;
		String repositorioId = "";
		this.articulos.clear();
		this.datos.clear();
		
		try {
			scan = new Scanner(new File(directorioEntrada + file));
		} catch (IOException e) {
			return false;
		}
		
		while(scan.hasNextLine()){
			String lineIn = scan.nextLine().trim();
			if (lineIn.isEmpty()) continue;
			if (lineIn.startsWith("%")) continue;
			if (lineIn.startsWith("@")) {
				repositorioId = lineIn.substring(1);
				continue;
			}
			String[] items = lineIn.split("[ ]+");
			add(repositorioId, items[0], Arrays.copyOfRange(items, 2, items.length));
			loadArticulo(items[0], directorioEntrada + items[1]);
		}
		scan.close();
		return true;
	}

	
	public TreeSet<String> getArticulosId(String autorId) { 
		TreeSet<String> result = new TreeSet<>();
		for(TreeMap<String, TreeSet<String>> it : this.datos.values()) {
			for (Entry<String, TreeSet<String>> it2 :it.entrySet()) {
				if(it2.getValue().contains(autorId)) result.add(it2.getKey());
			}
		}
		return result;
	}
	
	public TreeSet<String> getCoAutores(String autorId) {
		TreeSet<String> result = new TreeSet<>();
		for(TreeMap<String, TreeSet<String>> it : this.datos.values()) {
			for(TreeSet<String> autores : it.values()) {
				if(autores.contains(autorId)) result.addAll(autores);
			}
		}
		result.remove(autorId);
		return result;
	}
	
	public TreeSet<String> getPalabrasClave(String autorId, int minFrec) {
		TreeMap<String, Integer> macroPalabrasFrecuencia = new TreeMap<>();
		TreeSet<String> resultado = new TreeSet<>();
		for(String articuloId : getArticulosId(autorId)) {
			TreeMap<String, Integer> tm = this.articulos.get(articuloId);
			for (Entry<String, Integer> it : tm.entrySet()) {
				Integer n = macroPalabrasFrecuencia.get(it.getKey());
				macroPalabrasFrecuencia.put(it.getKey(), it.getValue() + (n == null ? 0 : n));
			}
		}
		for(Entry<String, Integer> it : macroPalabrasFrecuencia.entrySet()) {
			if(it.getValue() >= minFrec) {
				resultado.add(it.getKey() + "=" + it.getValue());
			}
		}
		return resultado;
	}
	
	public int size() {
		return this.datos.size();
	}
	
	public void clear() {
		this.articulos.clear();
		this.datos.clear();
	}
	
	@Override
	public String toString() {
		String result = "";
		for(Entry<String, TreeMap<String, TreeSet<String>>> it : this.datos.entrySet()) { 
			result += it.getKey() + "\n";
			for(Entry<String, TreeSet<String>> it2 : it.getValue().entrySet()) {
				result += "\t" + it2.getKey() + 
						" <" + this.articulos.get(it2.getKey()).size() + 
						" palabras> " + it2.getValue() + "\n";
			}
		}
		return result;
	}
}