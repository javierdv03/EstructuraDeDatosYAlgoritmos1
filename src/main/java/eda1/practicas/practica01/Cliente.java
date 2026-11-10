package eda1.practicas.practica01;

import eda1.practicas.auxiliar.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Cliente implements Comparable<Cliente>, Iterable<Mascota> {
    private static int numClientes = 0;
    private final String nombre;
    private final ArrayList<Mascota> mascotas;

    public static void inicializaNumClientes() {
        numClientes = 0;
    }

    public Cliente(String nombre) {
    	this.nombre = Format.formatInt(++numClientes, 5) + ".- " + (nombre == null || nombre.isBlank() ? "sinNombre" : nombre.toLowerCase());
        this.mascotas = new ArrayList<>();
    }

    public boolean addMascota(String nombre, String especie) {
    	Mascota m = new Mascota(nombre,especie);
    	if(this.mascotas.indexOf(m) != -1) return false;
    	return this.mascotas.add(m);
    }

    public Cita addCita(String nombre, String especie) {
    	int index = this.mascotas.indexOf(new Mascota(nombre,especie));
    	return index == -1 ? null : this.mascotas.get(index).addCita();
    }

    public void clear() {
    	for (Mascota m : mascotas) {
			m.clear();
		}
        this.mascotas.clear();
    }

    public int size() {
        return this.mascotas.size();
    }

    public ArrayList<ArrayList<Integer>> getCitasId(){
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<Integer> aux;
        for (Mascota m : mascotas) {
			aux = new ArrayList<>();
			for (Cita c : m) {
				aux.add(c.getCitaId());
			}
			result.add(aux);
		}
        return result;
    }

    public ArrayList<ArrayList<Integer>> getCitasId(String palabra) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<Integer> aux;
        for (Mascota m : mascotas) {
			aux = new ArrayList<>();
			for (Cita c : m) {
				if(c.contienePalabra(palabra)) aux.add(c.getCitaId());
			}
			result.add(aux);
		}
        return result;
    }

    public ArrayList<Integer> getCitasId(String nombre, String especie){
        int index = this.mascotas.indexOf(new Mascota(nombre,especie));
        if(index == -1) return null;
    	ArrayList<Integer> result = new ArrayList<>();
        for (Cita c : this.mascotas.get(index)) {
			result.add(c.getCitaId());
		}
        return result;
    }

    public Cita getCita(int citaId){
        Cita result = null;
        for (Mascota m : mascotas) {
			result = m.getCita(citaId);
			if(result != null) break;
		}
        return result;
    }

    public Mascota getMascota(int citaId){
        for (Mascota m : mascotas) {
			 if(m.getCita(citaId)!= null) return m;
		}
        return null;
    }

    @Override
    public String toString() {
        return this.nombre + " -> " + this.mascotas.toString();
    }

    public String toStringExtended() {
        String result = this.nombre + " -> {";
        int cont = 0;
        for (Mascota m : mascotas) {
        	result += (cont++ != 0 ? ", " : "") + m.toStringExtended();
		}
        return result + "}";
    }

    public boolean load(String nombreArchivo) {
    	Scanner scan;
        try {
            scan = new Scanner(new File(nombreArchivo));
        }catch(Exception e) {
            return false;
        }
        while (scan.hasNextLine()) {
            String linea = scan.nextLine(); 
            if (linea.isEmpty()) continue;
            if (linea.startsWith("%")) continue;
            String[] items = linea.split("[ ]+"); 
            int posInicial = items[0].isEmpty() ? 1 : 0;
            Mascota mascota = new Mascota(items[posInicial], items[posInicial+1]);
            int index = this.mascotas.indexOf(mascota);
            if(index != -1) continue;
            int nCitas = Integer.parseInt(items[posInicial + 2]);
            for (int i = 0; i < nCitas; i++) {
				mascota.addCita();
			}
            
             this.mascotas.add(mascota);
        }
        scan.close();
        return true;
    }
    public boolean equals(Object other){
        if (this == other) return true;
        if (!(other instanceof Cliente)) return false;
        return this.compareTo((Cliente)other) == 0;
    }

	@Override
	public int compareTo(Cliente o) {
		return this.nombre.substring(8).compareTo(o.nombre.substring(8));		
	}

	@Override
	public Iterator<Mascota> iterator() {
		
		return this.mascotas.iterator();
	}
}

