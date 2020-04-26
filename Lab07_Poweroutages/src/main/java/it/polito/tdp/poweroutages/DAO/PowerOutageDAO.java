package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	//imprortiamo le entita' regionali destinate al controllo dell'energia elettrica che vanno a costituire 
	//il menu a tendina nella finestra
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	//ecco quello che ci serve degli eventi di blackout in cui li prendiamo con le date crescenti almeno proseguiamo in un ordine
	//senza andare avanti ed indietro di continuo in quanto non necessario
	/*
	 	select id, nerc_id , customers_affected, date_event_began, date_event_finished
		from POwerOutages
		where nerc_id='3'
		order by date_event_began asc
	 */
	
	public List<PowerOutage> getPowerOutageList(Nerc n) {

		String sql = "select id, nerc_id , customers_affected, date_event_began, date_event_finished " + 
				"		from POwerOutages " + 
				"		where nerc_id=? " + 
				"		order by date_event_began asc";
		List<PowerOutage> ritorna = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				//System.out.print(res.getDate("date_event_began"));
				//System.out.println(" "+res.getTime("date_event_began"));
				LocalDateTime inizio= LocalDateTime.of(res.getDate("date_event_began").toLocalDate(), res.getTime("date_event_began").toLocalTime());
				System.out.println(inizio);
				
				//LocalDateTime inizio=res.getDate("date_event_began").toLocalDateTime();
				//LocalDateTime fine=res.getDate("date_event_finished").toLocalDateTime();
				/*
				PowerOutage p = new PowerOutage(res.getInt("id"), n ,res.getInt("customer_afflicted"), inizio, fine);
				ritorna.add(p);
				*/
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return ritorna;
	}
	

}
