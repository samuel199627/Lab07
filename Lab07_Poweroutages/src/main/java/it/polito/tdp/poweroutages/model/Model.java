package it.polito.tdp.poweroutages.model;

import java.time.Duration;
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
	float oreAttuali;
	Duration d;
	
	//serve per effettuare poche stampe della soluzione per controllare se funziona
	int conta=0;
	
	
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
		System.out.println("blackout totali: "+listaTotale.size());
		maxPersoneColpite=0;
		
		ricorsione(0, parziale,0,0,false);
		
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
	 	- una variabile che conta per ora il numero di ore totali che non deve sforare un certo massimo
	 	che abbiamo letto da parametro;
	 	- una variabile che contiene il numero di personeColpite parzialmente da confrontare con il massimo
	 	di persone colpite;
	 	- scorriamo in ordine e facciamo due vie in cui mettiamo oppure no;
	 	 
	 */
	public void ricorsione(int livello, List<PowerOutage> parziale, float oreParzialiTot, int personeColpiteParziale, boolean esci) {
		
		/*
		 	Condizione finale in cui confronto la soluzione parziale che ho con la soluzione fino ad ora che era migliore:
		 	- o siamo alla fine dei balckout che abbiamo, cioe' alla fine della lista importata dal dataset;
		 	- oppure abbiamo un booleano che ci dice che dobbiamo uscire in quanto abbiamo superato gli anni di differenza;
		 	
		 	Perche' fino a che non superiamo gli anni di differenza potenzialmente potrei avere ancora spazio per delle ore
		 */
		if(esci == true || livello==listaTotale.size()) {
			if(parziale.size()>0) {
				conta++;
				if(conta<10) {
					System.out.println("\nSOLUZIONE "+conta+" con "+parziale.size()+" blackout: \n");
					for(PowerOutage p:parziale) {
						System.out.println("personeColpiteParziale: "+personeColpiteParziale);
						System.out.println("oreParzialiTot: "+oreParzialiTot);
						System.out.println("esci: "+esci);
						System.out.println("livello: "+livello);
						System.out.println("id: "+p.getId());
						System.out.println("datainizio: "+p.getDateBegan());
						System.out.println("datafine: "+p.getDateFushed()+"\n");
					}
				}
			}
			return;
		}
		
		//controlliamo se siamo oltre il divario temporale, ma logicamente dobbiamo essere nel caso di avere gia' messo qualcosa in parziale
		if(parziale.size()>0) {
			d=Duration.between(parziale.get(0).getDateBegan(), listaTotale.get(livello).getDateBegan());
			if(d.getSeconds()/((float) 3600*24*365) >= (float) maxYears) {
				esci= true;
				livello++;
				ricorsione(livello,parziale, oreParzialiTot, personeColpiteParziale,esci);
				return;
			}
		}
		
		//se non siamo in una condizione finale esploriamo le strade due strade di aggiunta oppure no
		
		//livello sta puntando alla posizione che dobbiamo scegliere se aggiungere oppure no
		
		//non aggiungo
		livello++;
		ricorsione(livello, parziale, oreParzialiTot, personeColpiteParziale,esci);
		livello--;
		
		//aggiungo
		//se aggiungo controllo se le ore ci stanno oppure no
		d=Duration.between(listaTotale.get(livello).getDateBegan(), listaTotale.get(livello).getDateFushed());
		oreAttuali=d.getSeconds()/((float) 3600);
		oreParzialiTot=oreParzialiTot+oreAttuali;
		if(oreParzialiTot >= (float) maxHours) {
			//non possiamo aggiungere perche' sforiamo le ore
			oreParzialiTot=oreParzialiTot-oreAttuali;
			livello++;
			ricorsione(livello, parziale, oreParzialiTot, personeColpiteParziale,esci);
			return;
		}
		else {
			//se non sforiamo aggiungiamo alla soluzione parziale
			//gli passo direttamente l'oggetto perche' tanto non devo fare nessuna modifica
			parziale.add(listaTotale.get(livello));
			livello++;
			personeColpiteParziale=personeColpiteParziale+listaTotale.get(livello).getCustomersAffected();
			ricorsione(livello, parziale, oreParzialiTot, personeColpiteParziale,esci);
		}
		
		
		
		
		
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> getPowerOutageList(Nerc n) {
		return podao.getPowerOutageList(n);
	}
	

}
