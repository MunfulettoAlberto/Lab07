package it.polito.tdp.dizionario.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.dizionario.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioController {

	private Model model;
	public void setModel(Model model){
		this.model = model ;
	}
	
    @FXML
    private Button btnAllNeighbours;

	
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextArea txtResult;
	@FXML
	private TextField inputNumeroLettere;
	@FXML
	private TextField inputParola;
	@FXML
	private Button btnGeneraGrafo;
	@FXML
	private Button btnTrovaVicini;
	@FXML
	private Button btnTrovaGradoMax;

	@FXML
	void doReset(ActionEvent event) {
		inputNumeroLettere.clear();
		inputParola.clear();
		txtResult.clear();
	}

	private boolean controlGrafo = false;
	@FXML
	void doGeneraGrafo(ActionEvent event) {
		try {
			btnTrovaVicini.setDisable(false);
			btnTrovaGradoMax.setDisable(false);
			int numerolettere =0;
			controlGrafo = true;
			if(inputNumeroLettere.getText().matches("[0-9]*")){
				numerolettere = Integer.parseInt(inputNumeroLettere.getText()) ;
			}
			else{
				txtResult.setText("Inserire un numero");
				inputNumeroLettere.clear();
			}

			if(numerolettere!=0){
				model.createGraph(numerolettere) ;
				txtResult.setText("Caricamento avvenuto correttamente");
			}
			else{
				txtResult.setText("Inserire dimensione");
				inputNumeroLettere.clear();
			}
			
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaGradoMax(ActionEvent event) {
		
		try {

			if(!controlGrafo){
				txtResult.setText("Generare prima il grafo");
				btnTrovaVicini.setDisable(true);
				btnTrovaGradoMax.setDisable(true);
				return;
			}
			
			txtResult.setText(model.findMaxDegree());

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaVicini(ActionEvent event) {
		
		try {
			txtResult.clear();
			String parola = inputParola.getText() ;
			
			if(!controlGrafo){
				txtResult.setText("Generare prima il grafo");
				btnTrovaVicini.setDisable(true);
				btnTrovaGradoMax.setDisable(true);
				return;
			}
			
			if(!parola.matches("[a-z]*")){
				txtResult.setText("Inserire stringa di sole lettere");
				return;
			}
			if(parola.length()!= Integer.parseInt(inputNumeroLettere.getText())){
				txtResult.setText("Campi incoerenti tra di loro");
				return;
			}
			
			for(String s : model.displayNeighbours(parola)) {
				txtResult.appendText(s+"\n");
			}

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	 @FXML
     void doAllNeighbours(ActionEvent event) {

    }
	@FXML
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputNumeroLettere != null : "fx:id=\"inputNumeroLettere\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputParola != null : "fx:id=\"inputParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
	    assert btnAllNeighbours != null : "fx:id=\"btnAllNeighbours\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaGradoMax != null : "fx:id=\"btnTrovaTutti\" was not injected: check your FXML file 'Dizionario.fxml'.";
	}
}