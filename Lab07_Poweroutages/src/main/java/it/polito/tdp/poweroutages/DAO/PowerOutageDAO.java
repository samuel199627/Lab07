package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
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
			
			//int livello=0;
			//LocalDateTime inizio1=null;

			while (res.next()) {
				
				
				
				//if(livello==0) {
				//	inizio1= LocalDateTime.of(res.getDate("date_event_began").toLocalDate(), res.getTime("date_event_began").toLocalTime());
				//}
				//livello++;
				
				
				
				//System.out.print(res.getDate("date_event_began"));
				//System.out.println(" "+res.getTime("date_event_began"));
				
				
				//LocalDateTime inizio= LocalDateTime.of(res.getDate("date_event_began").toLocalDate(), res.getTime("date_event_began").toLocalTime());
				//System.out.print(inizio);
				//LocalDateTime fine= LocalDateTime.of(res.getDate("date_event_finished").toLocalDate(), res.getTime("date_event_finished").toLocalTime());
				//System.out.print(" "+fine);
				
				
				/*
				LocalDate inizioD=res.getDate("date_event_began").toLocalDate();
				LocalDate fineD=res.getDate("date_event_finished").toLocalDate();
				
				Period pe=Period.between(inizioD,fineD);
				System.out.print(" "+pe.getDays());
				*/
				
				
				//Duration d=Duration.between(inizio, fine);
				//System.out.print(" giorni precisi: "+(d.getSeconds()/((float) 3600*24)));
				//System.out.println(" ore precise: "+(d.getSeconds()/((float) 3600)));
				
				
				//System.out.println(" "+(d.getSeconds()));
				
				/*
				 	2002-12-25T17:00 2002-12-26T05:00 giorni precisi: 0.5 ore precise: 12.0
					2003-07-21T17:15 2003-07-24T05:33 giorni precisi: 2.5125 ore precise: 60.3
					2003-08-26T16:00 2003-08-29T12:00 giorni precisi: 2.8333333 ore precise: 68.0
					2003-08-26T16:22 2003-08-31T18:00 giorni precisi: 5.0680556 ore precise: 121.63333
					2003-09-18T14:00 2003-09-24T00:00 giorni precisi: 5.4166665 ore precise: 130.0
					2003-09-18T21:00 2003-09-21T17:00 giorni precisi: 2.8333333 ore precise: 68.0
				*/
				
				/*
				if(livello==15) {
					d=Duration.between(inizio1, inizio);
					System.out.println("ULTIMO CONFRONTO");
					System.out.print(inizio1);
					System.out.print(" "+inizio);
					System.out.print(" anni precisi: "+(d.getSeconds()/((float) 3600*24*365))+"\n");
					
					
					// 	2002-12-25T17:00 2005-10-24T20:00 anni precisi: 2.8332193
					
					
				}
				*/
				
				//LocalDateTime inizio=res.getDate("date_event_began").toLocalDateTime();
				//LocalDateTime fine=res.getDate("date_event_finished").toLocalDateTime();
				
				PowerOutage p = new PowerOutage(res.getInt("id"), n ,res.getInt("customers_affected"), LocalDateTime.of(res.getDate("date_event_began").toLocalDate(), res.getTime("date_event_began").toLocalTime()), LocalDateTime.of(res.getDate("date_event_finished").toLocalDate(), res.getTime("date_event_finished").toLocalTime()));
				ritorna.add(p);
				
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return ritorna;
	}
	

}
