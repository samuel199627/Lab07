package it.polito.tdp.poweroutages;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

//COMMENTI LABORATORIO 7
/*
 	per i calcoli di ore e anni nella differenza tra due date facciamo la differenza giusta quindi riferendoci proprio allo ore
 	e ci salviamo le differenze questione  in dei float che ci danno i valori precisi.
 	Queste differnze precise le ho costruite a partire dai secondi di differenza tra le due date che e' la nostra unita' di tempo
 	in base a come sono stati salvati i dati nella base dati.
 */

public class FXMLController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imageNERC;

    @FXML
    private ComboBox<Nerc> choiceNERC;

    @FXML
    private TextField txtYears;

    @FXML
    private TextField txtHours;

    @FXML
    private Button actionButton;

    @FXML
    private TextArea txtRis;

    @FXML
    void worstCase(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert imageNERC != null : "fx:id=\"imageNERC\" was not injected: check your FXML file 'Scene.fxml'.";
        /*
   
<ComboBox fx:id="choiceNERC" prefWidth="180.0" xmlns="http://javafx.com/javafx/11.0.1" xmlns:fx="http://javafx.com/fxml/1" />

         */
        assert choiceNERC != null : "fx:id=\"choiceNERC\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert actionButton != null : "fx:id=\"actionButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRis != null : "fx:id=\"txtRis\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model=model;
		//dobbiamo collegare il menu a tendina con i nerc
		List<Nerc> ritornaNerc= model.getNercList();
		
		choiceNERC.getItems().addAll(ritornaNerc);
		
	}
}

