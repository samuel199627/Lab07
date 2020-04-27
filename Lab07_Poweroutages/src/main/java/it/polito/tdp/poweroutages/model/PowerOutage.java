package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class PowerOutage {
	
	int id;
	Nerc nerc;
	int customersAffected;
	LocalDateTime dateBegan;
	LocalDateTime dateFinished;
	
	public PowerOutage(int id, Nerc nerc, int customersAffected, LocalDateTime dateBegan, LocalDateTime dateFinished) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.customersAffected = customersAffected;
		this.dateBegan = dateBegan;
		this.dateFinished = dateFinished;
	}
	
	//HashCode ed equals sull'id
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Nerc getNerc() {
		return nerc;
	}
	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}
	public int getCustomersAffected() {
		return customersAffected;
	}
	public void setCustomersAffected(int customersAffected) {
		this.customersAffected = customersAffected;
	}
	public LocalDateTime getDateBegan() {
		return dateBegan;
	}
	public void setDateBegan(LocalDateTime dateBegan) {
		this.dateBegan = dateBegan;
	}
	public LocalDateTime getDateFushed() {
		return dateFinished;
	}
	public void setDateFushed(LocalDateTime dateFinished) {
		this.dateFinished = dateFinished;
	}
	
	
	
	

}
