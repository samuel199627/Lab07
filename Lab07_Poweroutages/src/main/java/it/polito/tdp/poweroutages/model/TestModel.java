package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		//System.out.println(model.getNercList());
		
		//model.getPowerOutageList(new Nerc(3, null));
		
		//System.out.print(""+((float) 2280)/((float) 3600));
		
		List<PowerOutage> ritorno= model.trovaSequenzaPeggiore(new Nerc(3, null), 200, 4).getLista();
		System.out.println("\n\nBEST SOLUZIONE con "+ritorno.size()+" blackout: \n");
		int personeColpite=0;
		List<Integer> id=new ArrayList<>();
		for(PowerOutage p:ritorno) {
			personeColpite=personeColpite+p.getCustomersAffected();
			id.add(p.getId());
			System.out.println("id: "+p.getId());
			System.out.println("datainizio: "+p.getDateBegan());
			System.out.println("datafine: "+p.getDateFushed()+"\n");
			
		}
		/*
		id.sort(null);
		for(int i: id) {
			System.out.println(" "+i);
		}
		*/
		//System.out.println("personeColpite: "+personeColpite);

	}

}
