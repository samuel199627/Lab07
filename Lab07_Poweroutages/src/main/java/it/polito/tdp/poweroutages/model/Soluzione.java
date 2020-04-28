package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

public class Soluzione {
	//mi serve per restituire la lista con il totale di ore e di persone per evitare di calcolare di nuovo tutto
	private List<PowerOutage> lista;
	private int personeColpite;
	private float oreTotali;
	
	public Soluzione(List<PowerOutage> lista, int personeColpite, float oreTotali) {
		super();
		this.lista = new ArrayList<>(lista);
		this.personeColpite = personeColpite;
		this.oreTotali = oreTotali;
	}

	public List<PowerOutage> getLista() {
		return lista;
	}

	public void setLista(List<PowerOutage> lista) {
		this.lista = lista;
	}

	public int getPersoneColpite() {
		return personeColpite;
	}

	public void setPersoneColpite(int personeColpite) {
		this.personeColpite = personeColpite;
	}

	public float getOreTotali() {
		return oreTotali;
	}

	public void setOreTotali(float oreTotali) {
		this.oreTotali = oreTotali;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lista == null) ? 0 : lista.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Soluzione other = (Soluzione) obj;
		if (lista == null) {
			if (other.lista != null)
				return false;
		} else if (!lista.equals(other.lista))
			return false;
		return true;
	}

	
	
	
	

}
