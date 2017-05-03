package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.dizionario.db.WordDAO;

public class Model {

	private WordDAO wDAO = new WordDAO () ;
	private List<String> tutteleparole = new ArrayList<String>( ) ;
	UndirectedGraph<String, DefaultEdge> grafo = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class) ;
	
	public List<String> createGraph(int numeroLettere) {
		
		tutteleparole.addAll(wDAO.getAllWordsFixedLength(numeroLettere)) ;
		
		
		//aggiungo i vertici
		for(String parola : tutteleparole){
			grafo.addVertex(parola);
		}
		
		//aggiungo i collegamenti
		for(String parola1 : grafo.vertexSet()){
			for(String parola2 : grafo.vertexSet()){
				if(parola1.compareTo(parola2)!=0){
					if(!grafo.containsEdge(parola1, parola2)){
						if(this.controlloParole(parola1, parola2)){
							grafo.addEdge(parola1, parola2);
						}
					}
				}
			}
		}
		
		return tutteleparole ;
	}

	public List<String> displayNeighbours(String parolaInserita) {

		List <String> vicini = new ArrayList<String>();
		
		if(grafo.containsVertex(parolaInserita)){
			vicini.addAll(Graphs.neighborListOf(grafo, parolaInserita)) ;
		}
	
		return vicini;
	}

	public String findMaxDegree() {
		
		String tmp = "";
		String s ="";
		
		int max = 0;
		
		for(String parola : grafo.vertexSet()){
			if(grafo.degreeOf(parola)>max){
				max = grafo.degreeOf(parola);
				tmp = parola ;
			}
		}
		
		for(String vicino : this.displayNeighbours(tmp)){
			s+=vicino+"\n" ;
		}
		
		return tmp+"\n\n"+s;
	}
	
	private boolean controlloParole(String parola1, String parola2){
		boolean v = false ; 
		char c1[] = parola1.toCharArray();
		char c2[] = parola2.toCharArray();
		int cont = 0 ;
		
		for(int i=0 ; i< c1.length; i++){
			if(c1[i]!=c2[i]){
				cont++;
			}
		}
		
		if(cont==1){
			v=true;
		}
		
		return  v ;
	}
	
	public List<String>  findAllNeighbours(String vertice){
		List<String> neighbours = new ArrayList<String>() ;

		if(!grafo.containsVertex(vertice)){
			return null ;
		}
		neighbours.add(vertice);
		this.recursive(neighbours, vertice);
		
		return neighbours ;
	}
	
	private void recursive(List<String> neighbours, String vertice){
		
		List<String> directNeighbours = new ArrayList <String>(Graphs.neighborListOf(grafo, vertice)) ;
	
		for(String tmp : directNeighbours){
			
			if(!neighbours.contains(tmp)){
				neighbours.add(tmp);
				this.recursive(neighbours, tmp);
			}
		}
		
	}
	
}
