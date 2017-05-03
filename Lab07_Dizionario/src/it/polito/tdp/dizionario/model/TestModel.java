package it.polito.tdp.dizionario.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		System.out.println(String.format("**Grafo creato** - Trovate #%d parole di lunghezza 4\n",  model.createGraph(4).size()));
		
		List<String> vicini = model.displayNeighbours("casa");
		System.out.println("Vicini di casa: " + vicini);
		
		System.out.println();
		
		System.out.print("Cerco il vertice con grado massimo... ");
		System.out.println( model.findMaxDegree());
		
		List<String> vicini1 = model.findAllNeighbours("casa");
		System.out.println("All Neighbours: " + vicini1);
	}

}
