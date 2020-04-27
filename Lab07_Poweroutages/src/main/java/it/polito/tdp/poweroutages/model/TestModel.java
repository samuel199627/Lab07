package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		//System.out.println(model.getNercList());
		
		//model.getPowerOutageList(new Nerc(3, null));
		
		//System.out.print(""+((float) 2280)/((float) 3600));
		
		model.trovaSequenzaPeggiore(new Nerc(3, null), 200, 4);

	}

}
