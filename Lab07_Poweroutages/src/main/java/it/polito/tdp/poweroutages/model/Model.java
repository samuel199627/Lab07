package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	
	//questa posso crearla globale come variabile perche' tanto nella ricorsione 
	//compare solo alla fine, invece la parziale viene passata e quindi e' bene
	//crearla solo locale
	List<PowerOutage> soluzione;
	
	List<PowerOutage> listaTotale;
	int maxHours;
	int maxYears;
	int maxPersoneColpite;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	/*
	 	prepariamo le variabili per la procedura ricorsiva
	 */
	public List<PowerOutage> trovaSequenzaPeggiore(Nerc n, int maxHours, int maxYears){
		List<PowerOutage> parziale = new ArrayList<PowerOutage>();
		soluzione = new ArrayList<PowerOutage>();
		this.maxHours=maxHours;
		this.maxYears=maxYears;
		listaTotale = new ArrayList<PowerOutage>(); 
		listaTotale=this.getPowerOutageList(n);
		maxPersoneColpite=0;
		
		ricorsione(0, parziale,0,0);
		
		return null;
	}
	
	/*
	 	Nella seguente procedura ricorsiva abbiamo:
	 	- la lista di blackout che sono ordinati in ordine crescente di data;
	 	- una variabile che ci dice a che punto siamo del vettore di blackout (livello);
	 	- la soluzione parziale con finora quello che abbiamo inserito nella sequenza di blackout 
	 	e di cui e' importante il primo valore perche' dobbiamo controllare se non abbiamo superato
	 	il limite di anni e quindi uscire dalla procedura ricorsiva in quanto chi viene dopo sicuramente
	 	sara' in una data successiva. Piu' che uscire ; 
	 	-una variabile che conta per ora il numero di ore totali che non deve sforare un certo massimo
	 	che abbiamo letto da parametro;
	 	-una variabile che contiene il numero di personeColpite parzialmente da confrontare con il massimo
	 	di persone colpite;
	 	 
	 */
	public void ricorsione(int livello, List<PowerOutage> parziale, int oreParzialiTot, int personeColpiteParziale) {
		
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> getPowerOutageList(Nerc n) {
		return podao.getPowerOutageList(n);
	}
	

}
