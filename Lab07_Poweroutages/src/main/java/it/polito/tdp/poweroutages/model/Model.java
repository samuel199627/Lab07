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
	//int personeColpiteSoluzione;
	float oreTotaliSoluzione;
	
	//serve per effettuare poche stampe della soluzione per controllare se funziona
	int conta=0;
	
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	/*
	 	prepariamo le variabili per la procedura ricorsiva
	 */
	public Soluzione trovaSequenzaPeggiore(Nerc n, int maxHours, int maxYears){
		//parziale e' meglio solo averla locale nella soluzione
		//List<PowerOutage> parziale = new ArrayList<PowerOutage>();
		soluzione = new ArrayList<PowerOutage>();
		this.maxHours=maxHours;
		this.maxYears=maxYears;
		listaTotale = new ArrayList<PowerOutage>(); 
		listaTotale=this.getPowerOutageList(n);
		//System.out.println("blackout totali: "+listaTotale.size());
		maxPersoneColpite=0;
		oreTotaliSoluzione=0;
		//ricorsione(0, parziale,0,0,false);
		ricorsione(0, new ArrayList<PowerOutage>(),0,0,false);
		/*
		System.out.println("personeColpite: "+maxPersoneColpite);
		System.out.println("oreTotali: "+oreTotaliSoluzione);
		*/
		
		Soluzione soluzioneCompleta= new Soluzione(soluzione, maxPersoneColpite, oreTotaliSoluzione);
		
		return soluzioneCompleta;
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
				if(conta>0) {
				//if(conta>20&&conta<100) {
					//System.out.println("\nSOLUZIONE "+conta+" con "+parziale.size()+" blackout: \n");
					
					
					//System.out.println("personeColpiteParziale: "+personeColpiteParziale);
					//System.out.println("oreParzialiTot: "+oreParzialiTot);
					
					/*
					System.out.println("esci: "+esci);
					System.out.println("livello: "+livello);
					for(PowerOutage p:parziale) {
						
						System.out.println("id: "+p.getId());
						System.out.println("datainizio: "+p.getDateBegan());
						System.out.println("datafine: "+p.getDateFushed()+"\n");
					}
					*/
				}
				if(personeColpiteParziale>maxPersoneColpite) {
					maxPersoneColpite=personeColpiteParziale;
					soluzione=new ArrayList<>(parziale);
					oreTotaliSoluzione=oreParzialiTot;
					//System.out.println("oreParzialiTot: "+oreParzialiTot);
				}
				//System.out.println("maxPersoneColpite: "+maxPersoneColpite);
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
				//rimetto a posto le variabili che avevo modificato
				livello--;
				//parziale.remove(parziale.size()-1);
				return;
			}
		}
		
		//se non siamo in una condizione finale esploriamo le strade due strade di aggiunta oppure no
		
		//livello sta puntando alla posizione che dobbiamo scegliere se aggiungere oppure no
		
		//non aggiungo
		livello++;
		ricorsione(livello, parziale, oreParzialiTot, personeColpiteParziale,esci);
		//rimetto a posto le variabili che avevo modificato
		livello--;
		
		//aggiungo
		//se aggiungo controllo se le ore ci stanno oppure no
		d=Duration.between(listaTotale.get(livello).getDateBegan(), listaTotale.get(livello).getDateFushed());
		oreAttuali=d.getSeconds()/((float) 3600);
		oreParzialiTot=oreParzialiTot+oreAttuali;
		if(oreParzialiTot >= (float) maxHours) {
			//non possiamo aggiungere perche' sforiamo le ore e quindi siccome il caso di non aggiunta lo abbiamo gia' esplorato
			//sopra, non mi pare proprio il caso di esplorare di fatto la stessa strada
			oreParzialiTot=oreParzialiTot-oreAttuali;
			/*
			livello++;
			ricorsione(livello, parziale, oreParzialiTot, personeColpiteParziale,esci);
			//rimetto a posto le variabili che avevo modificato
			livello--;
			return;
			*/
			
		}
		else {
			//se non sforiamo aggiungiamo alla soluzione parziale
			//gli passo direttamente l'oggetto perche' tanto non devo fare nessuna modifica
			parziale.add(listaTotale.get(livello));
			//System.out.println("\ndimensione prima: "+parziale.size());
			/*
			for(PowerOutage p:parziale) {
				
				System.out.println("id: "+p.getId());
				System.out.println("datainizio: "+p.getDateBegan());
				System.out.println("datafine: "+p.getDateFushed()+"\n");
			}
			*/
			personeColpiteParziale=personeColpiteParziale+listaTotale.get(livello).getCustomersAffected();
			livello++;
			ricorsione(livello, parziale, oreParzialiTot, personeColpiteParziale,esci);
			//rimetto a posto le variabili che avevo modificato
			livello--;
			
			//System.out.println("\ndimensione dopo: "+parziale.size());
			/*
			for(PowerOutage p:parziale) {
				
				System.out.println("id: "+p.getId());
				System.out.println("datainizio: "+p.getDateBegan());
				System.out.println("datafine: "+p.getDateFushed()+"\n");
			}
			*/
			parziale.remove(parziale.size()-1);
			personeColpiteParziale=personeColpiteParziale-listaTotale.get(livello).getCustomersAffected();
			oreParzialiTot=oreParzialiTot-oreAttuali;
			return;
		}
			
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> getPowerOutageList(Nerc n) {
		return podao.getPowerOutageList(n);
	}
	

}
