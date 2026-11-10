package eda1.practicas.practica03.parte03;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
	private static int numNodes = 0;
	private final int id;
	protected final ArrayList<T> components;

	public static void initializeNumNodes(){
		numNodes = 0;
	}
	
	@SafeVarargs
	public Node(T...components) {
		this.id = ++numNodes;
		this.components = new ArrayList<>(List.of(components));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Node)) return false;
		Node<T> other = (Node<T>) o;
		return this.compareTo(other) == 0;
	}

	
	public String toString() {
		return this.components.toString();
	}
	
	public int hashCode() {
		return Objects.hash(components);
	}
	
	public String toStringWithNodeId() {		
		return "Node #" + this.id + " -> " + toString();
	}

	@Override
	public int compareTo(Node<T> o) {
		int longitud1 = this.components.size();
		int longitud2 = o.components.size();
		int iteraciones = Math.min(longitud1, longitud2);
		for(int i = 0; i < iteraciones; i++) {
			int cmp = this.components.get(i).compareTo(o.components.get(i));
			if(cmp != 0) return cmp;
		}
		return Integer.compare(longitud1, longitud2);
	}
}