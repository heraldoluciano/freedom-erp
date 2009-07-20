/*******************************************************************************
 * Copyright (c) 2007 by CRP Henri TUDOR - SANTEC LUXEMBOURG 
 * check http://www.santec.tudor.lu for more information
 *  
 * Contributor(s):
 * Johannes Hermen  johannes.hermen(at)tudor.lu                            
 * Martin Heinemann martin.heinemann(at)tudor.lu  
 *  
 * This library is free software; you can redistribute it and/or modify it  
 * under the terms of the GNU Lesser General Public License (version 2.1)
 * as published by the Free Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but     
 * WITHOUT ANY WARRANTY; without even the implied warranty of               
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU        
 * Lesser General Public License for more details.                          
 * 
 * You should have received a copy of the GNU Lesser General Public         
 * License along with this library; if not, write to the Free Software      
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
 *******************************************************************************/
package org.freedom.modulos.crm.agenda;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.freedom.bmps.Icone;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;
import lu.tudor.santec.bizcal.EventModel;
import lu.tudor.santec.bizcal.NamedCalendar;
import lu.tudor.santec.bizcal.listeners.NamedCalendarListener;
import lu.tudor.santec.bizcal.util.ObservableEventList;
import lu.tudor.santec.bizcal.views.DayViewPanel;
import lu.tudor.santec.bizcal.views.ListViewPanel;
import lu.tudor.santec.bizcal.views.MonthViewPanel;
import bizcal.common.Event;
import bizcal.swing.CalendarListener;
import bizcal.swing.util.FrameArea;
import bizcal.util.DateInterval;


public class FAgenda2 extends FFilho{

	private static final long serialVersionUID = 1L;
		
	public static final String CALENDAR_ID = "calendarId";
	
	private ObservableEventList eventDataList;

	private DayViewPanel dayViewPanel;

	private DayViewPanel weekViewPanel;

	private MonthViewPanel monthViewPanel;

	private ListViewPanel listViewPanel;

	private CalendarPanel calendarPanel;

	public FAgenda2() {
		/* ================================================== */
		super( false );

		this.calendarPanel = new CalendarPanel();
		/* ------------------------------------------------------- */
		// this is the "data base" for all events. All created events 
		// will be stored in this list
		/* ------------------------------------------------------- */
		this.eventDataList = new ObservableEventList();
		/* ------------------------------------------------------- */
		// create a model for each view day, week, month, list
		// they all gain the same data list to operate on
		/* ------------------------------------------------------- */
		EventModel dayModel   = new EventModel(eventDataList, EventModel.TYPE_DAY);
		EventModel weekModel  = new EventModel(eventDataList, EventModel.TYPE_WEEK);
		EventModel monthModel = new EventModel(eventDataList, EventModel.TYPE_MONTH);
		EventModel listModel  = new EventModel(eventDataList, EventModel.TYPE_MONTH);
		
		/* ------------------------------------------------------- */
		// create the panels for each kind of view
		/* ------------------------------------------------------- */
		this.dayViewPanel 	= new DayViewPanel(  dayModel);
		this.weekViewPanel 	= new DayViewPanel(  weekModel);
		this.monthViewPanel = new MonthViewPanel(monthModel);
		this.listViewPanel 	= new ListViewPanel( listModel);
		/* ------------------------------------------------------- */
		// create a new calendar listener.
		// It will be informed of many interactions on the calendar like, event selected
		// copy & paste, date changed etc. Have a look at the interface
		/* ------------------------------------------------------- */
		DemoCalendarListener calListener = new DemoCalendarListener();
		
		/* ------------------------------------------------------- */
		// add the same listener to all views
		// you can create different listeners for each view, if you like to.
		/* ------------------------------------------------------- */
		dayViewPanel.addCalendarListener(  calListener );
		weekViewPanel.addCalendarListener( calListener );
		monthViewPanel.addCalendarListener( calListener );
		listViewPanel.addCalendarListener( calListener );

		/* ------------------------------------------------------- */
		// now we add all views to the base panel
		/* ------------------------------------------------------- */
		calendarPanel.addCalendarView(dayViewPanel);
		calendarPanel.addCalendarView(weekViewPanel);
		calendarPanel.addCalendarView(monthViewPanel);
		calendarPanel.addCalendarView(listViewPanel);
		/* ------------------------------------------------------- */
		
		/* ------------------------------------------------------- */
		// now we create some sample calendars.
		// they will appear in the right bar.
		/* ------------------------------------------------------- */

		
		/* ------------------------------------------------------- */
		// next step is to create a listener that is responsible for selecting and deselecting of
		// the calendars created above.
		//
		// we distinguish between active and selected calendars.
		// An active calendar is allowed to display its events on the views
		// A selected calendar is the calendar which will recieve the actions on the view, 
		// like creating a new event, moving, deleting etc.
		/* ------------------------------------------------------- */
		calendarPanel.addNamedCalendarListener(new NamedCalendarListener() {

			public void activeCalendarsChanged(Collection<NamedCalendar> calendars) {
				/* ====================================================== */
				// if no calendar is active, remove all events
				/* ------------------------------------------------------- */
				if (calendars == null || calendars.size() < 1) {
					eventDataList.clear();
					return;
				}
				/* ------------------------------------------------------- */
				// fetch the appointments of the active calendars
				/* ------------------------------------------------------- */
				updateEventsForActiveCalendars();
				/* ====================================================== */
			}

			public void selectedCalendarChanged(NamedCalendar selectedCalendar) {
				/* ====================================================== */
				// we do nothing here.
				// If you have any ideas of something that should be triggerd when a calendar was selected...
				/* ====================================================== */
			}

		});

		this.add(calendarPanel);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.pack();
		this.setSize(1000, 700);
		this.setVisible(true);
//		for (int i = 0; i < 50; i++) {
//			this.setSize(850+i*5, 800+i*2);
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	}

	@SuppressWarnings("unchecked")
	private synchronized void updateEventsForActiveCalendars() {
		/* ================================================== */
		// add all events from active calendars
		List<Event> allActiveEvents = new ArrayList<Event>();

		for (NamedCalendar nc : calendarPanel.getCalendars()) {
			if (nc.isActive())
				// this is just for demonstration. You can define a time periode to 
				// get events from the calendar
				allActiveEvents.addAll(nc.getEvents(null, null));
		}

		Collections.sort(allActiveEvents);

		eventDataList.clear();
		eventDataList.addAll(allActiveEvents);
		/* ================================================== */
	}

	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		SwingTracing.enableEventDispatcherTimeTracing(1000, false);
//		SwingTracing.enableEventDispatcherThreadViolationTracing(false);
//		SwingTracing.enableRepaintTracing("bizcal");
		
		new FAgenda2();
	}
	
	
	class DemoCalendarListener implements CalendarListener {

		public void closeCalendar(Object calId) throws Exception {
			/* ====================================================== */
			// TODO Auto-generated method stub
			/* ====================================================== */
		}

		public void copy(List<Event> list) throws Exception {
			/* ====================================================== */
			// TODO Auto-generated method stub
			/* ====================================================== */
		}

		public void dateChanged(Date date) throws Exception {
			/* ====================================================== */
			// TODO Auto-generated method stub
			/* ====================================================== */
		}

		public void dateSelected(Date date) throws Exception {
			/* ====================================================== */
			// TODO Auto-generated method stub
			/* ====================================================== */
		}

		public void deleteEvent(Event event) throws Exception {
			/* ====================================================== */
			eventDataList.remove(event);
			/* ====================================================== */
		}

		public void deleteEvents(List<Event> events) {
			/* ====================================================== */
			eventDataList.removeAll(events);
			/* ====================================================== */
		}

		public void eventClicked(Object id, Event _event, FrameArea area, MouseEvent e) {
			/* ====================================================== */
			// TODO Auto-generated method stub
			/* ====================================================== */
		}

		public void eventDoubleClick(Object id, Event event, MouseEvent mouseEvent) {
			/* ====================================================== */
			// TODO Auto-generated method stub
			/* ====================================================== */
		}

		public void eventSelected(Object id, Event event) throws Exception {
			/* ====================================================== */
//			 try to find the calendar by its id
			if (calendarPanel.getCalendars() == null)
				return;
			/* ------------------------------------------------------- */
			for (NamedCalendar nc : calendarPanel.getCalendars()) {
				if (nc.getId().equals(event.get(CALENDAR_ID))) {
					/* ------------------------------------------------------- */
					calendarPanel.setSelectedCalendar(nc);
					return;
					/* ------------------------------------------------------- */
				}
			}
			/* ====================================================== */
		}

		public void eventsSelected(List<Event> list) throws Exception {
			/* ====================================================== */
			// TODO Auto-generated method stub
			/* ====================================================== */
		}

		public void moved(Event event, Object orgCalId, Date orgDate, Object newCalId, Date newDate) throws Exception {
			/* ====================================================== */
			event.move(newDate);
			eventDataList.trigger();
			/* ====================================================== */
		}

		public void newCalendar() throws Exception {
			/* ====================================================== */
			// TODO Auto-generated method stub
			/* ====================================================== */
		}

		public void newEvent(Object id, Date date) throws Exception {
			/* ====================================================== */
			// create a normal appointment lasting 15 min
			DateInterval interval = new DateInterval(date, new Date(date.getTime()+900000));
			newEvent(id, interval);
			/* ====================================================== */
		}

		public void newEvent(Object id, DateInterval interval) throws Exception {
			/* ====================================================== */

			// create an Event object
//			Event newEvent = appointment2Event(ap);

			NamedCalendar nc = calendarPanel.getSelectedCalendar();
			/* ------------------------------------------------------- */
			if (nc == null)
				return;
			/* ------------------------------------------------------- */

			Event event1 = new Event();
			event1.setStart(interval.getStartDate());
			event1.setEnd(interval.getEndDate());
			event1.setId(id);
			
			Event event2 = new Event();
			event2.setStart(interval.getStartDate());
			event2.setEnd(interval.getEndDate());
			event2.setId(id);
			
			nc.addEvent("client A", event1);
			nc.addEvent("client B", event2);
			
			/* ====================================================== */
		}

		public void paste(Object calId, Date date) throws Exception {
			/* ====================================================== */
			// TODO Auto-generated method stub
			/* ====================================================== */
		}

		public void resized(Event event, Object orgCalId, Date orgEndDate, Date newEndDate) throws Exception {
			/* ====================================================== */
			NamedCalendar nc = calendarPanel.getSelectedCalendar();
			/* ------------------------------------------------------- */
			if (nc == null)
				return;
			/* ------------------------------------------------------- */
			event.setEnd(newEndDate);

			nc.saveEvent("clientXXX", event, false);
			/* ====================================================== */
		}

		public void selectionReset() throws Exception {
			/* ====================================================== */
			// TODO Auto-generated method stub
			/* ====================================================== */
		}

		public void showEvent(Object id, Event event) throws Exception {
			/* ====================================================== */
			// TODO Auto-generated method stub
			/* ====================================================== */
		}
		
	}
		
	class TestNamedCalendar extends NamedCalendar {
		
		
		private List<Event> calendarEvents = new ArrayList<Event>();
		
		public TestNamedCalendar(String name, String description, Color color) {
			super(name, description, color);
			
			this.setId(this.hashCode());
			
		}

		@Override
		public List<Event> getEvents(Date from, Date to) {
			return calendarEvents;
		}

		@Override
		public void deleteEvent(String clientId, Event event) {
			/* ====================================================== */
			calendarEvents.remove(event);
			eventDataList.remove(event);
			/* ====================================================== */
		}

		@Override
		public List<Event> addEvent(String clientId, Event event) {
			/* ====================================================== */
			event.set(CALENDAR_ID, this.getId());
			event.setColor(this.getColor());
			
			eventDataList.add(event);
			calendarEvents.add(event);
			return null;
			/* ====================================================== */
		}

		@Override
		public List<Event> saveEvent(String clientId, Event event, boolean userInteraction) {
			/* ====================================================== */
			// TODO Auto-generated method stub
			return null;
			/* ====================================================== */
		}

	

	}

	private void carregaListaUsuarios() {
		StringBuffer sql = new StringBuffer();
		try {
			
			sql.append( "SELECT PNOMEUSU,UNOMEUSU,CORAGENDA FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=?" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilialPad );

			ResultSet rs = ps.executeQuery();

			for ( int i = 0; rs.next(); i++ ) {

				String pnome = rs.getString( "PNOMEUSU" ).trim();
				String unome = rs.getString( "UNOMEUSU" ).trim();
				if(pnome!=null) {
					pnome = pnome.substring( 0,1 ).toUpperCase() + pnome.substring( 1 );
				}
				if(unome!=null) {
					unome = unome.substring( 0,1 ).toUpperCase() + unome.substring( 1 );
				}

				int icor = rs.getInt( "CORAGENDA" );
				
				calendarPanel.addNamedCalendar(new TestNamedCalendar(pnome + " " + unome, pnome + " " + unome, new Color(icor)));								


			}
			
			rs.close();
			ps.close();

			con.commit();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		
		carregaListaUsuarios();
		
	}

	public static void carregaTabAgd( final int codAge, final String tipoAge, final Object[] datas, final Tabela tabAgd, final boolean todos, final DbConnection con, final Component cOrig, final Vector<String> usu, final String sPeriodo, boolean pendentes, boolean cancelados, boolean concluidos ) {

		if ( codAge > 0 ) {
			
			tabAgd.limpa();
			Object[] oDatas = datas;
			String sDatas = "";

			ImageIcon nenhuma = Icone.novo( "prior_sem.gif" );
			ImageIcon baixa = Icone.novo( "prior_baixa.gif" );
			ImageIcon media = Icone.novo( "prior_media.gif" );
			ImageIcon alta = Icone.novo( "prior_alta.gif" );
			ImageIcon prioridade = null;
			
			ImageIcon pend = Icone.novo( "clAgdPend.gif" );
			ImageIcon canc = Icone.novo( "clAgdCanc.gif" );
			ImageIcon fina = Icone.novo( "clAgdFin.gif" );						
			ImageIcon situacao = null;
		
			for ( int i = 0; i < oDatas.length; i++ ) {
				if ( i == 0 ) {
					sDatas = "'" + Funcoes.dateToStrDB( (Date) oDatas[ i ] ) + "'";
				}
				else {
					sDatas = sDatas + "," + "'" + Funcoes.dateToStrDB( (Date) oDatas[ i ] ) + "'";
				}
			}
			
			StringBuffer sSQL = new StringBuffer();
			sSQL.append( "SELECT A.CODAGD,A.SITAGD,A.DTAINIAGD,A.HRINIAGD,A.DTAFIMAGD," );
			sSQL.append( "A.HRFIMAGD,A.ASSUNTOAGD,A.PRIORAGD,U.IDUSU," );
			sSQL.append( "(SELECT FIRST 1 U2.CORAGENDA FROM SGUSUARIO U2 ");
			sSQL.append( "WHERE U2.CODEMPAE=A.CODEMPAE AND U2.CODFILIALAE=A.CODFILIALAE AND U2.CODAGE=A.CODAGE AND U2.TIPOAGE=A.TIPOAGE) AS CORAGENDA" );
			sSQL.append( " FROM SGAGENDA A, SGUSUARIO U" );
			sSQL.append( " WHERE A.CODEMP=? AND A.CODFILIAL=?" );
			sSQL.append( " AND U.CODEMPAE=A.CODEMP AND U.CODFILIALAE=A.CODFILIAL" );
			sSQL.append( " AND U.CODAGE=A.CODAGE AND U.TIPOAGE=A.TIPOAGE" );

			if ( todos ) {
				sSQL.append( " AND (( A.CAAGD='PU') OR ( A.CODAGE=? AND A.TIPOAGE=? ))" );
			}
			else {
				sSQL.append( " AND A.CODAGE=? AND A.TIPOAGE=?" );
			}

			Vector<String> vfiltros = new Vector<String>();
			if(pendentes) {
				vfiltros.addElement( "'PE'" );
			}
			if(cancelados){
				vfiltros.addElement( "'CA'" );
			}
			if(concluidos){
				vfiltros.addElement( "'FN'" );
			}

			String sfiltros = Funcoes.vectorToString( vfiltros,"," );

			if((sfiltros!=null) && (!"".equals( sfiltros ))) {
				sSQL.append( " AND A.SITAGD IN (" + sfiltros + ")" );
			}
			
			

			sSQL.append( " AND DTAINIAGD IN(" + sDatas + ")" );
			sSQL.append( " ORDER BY A.DTAINIAGD DESC,A.HRINIAGD DESC,A.DTAFIMAGD DESC,A.HRFIMAGD DESC" );

			System.out.println("sql:" + sSQL.toString());
			
			try {

				PreparedStatement ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, Aplicativo.iCodFilialPad );
				ps.setInt( 3, codAge );
				ps.setString( 4, tipoAge );

				ResultSet rs = ps.executeQuery();

				if ( usu != null ) {
					usu.clear();
				}

				for ( int i = 0; rs.next(); i++ ) {

					if ( usu != null ) {
						usu.addElement( rs.getString( "IDUSU" ) );
					}

					switch ( rs.getInt( "PriorAgd" ) ) {
						case 2 :// baixa
							prioridade = baixa;
							break;
						case 3 :// media
							prioridade = media;
							break;
						case 4 :// alta
							prioridade = alta;
							break;
						default :// nenhuma
							prioridade = nenhuma;
							break;
					}

					String sitagd = rs.getString( "SitAgd" );
					
					if("PE".equals( sitagd )) {
						situacao = pend;
					}
					else if("CA".equals( sitagd )) {
						situacao = canc;
					}
					else if("FN".equals( sitagd )) {
						situacao = fina;
					}
/*					
					tabAgd.adicLinha();
					tabAgd.setValor( rs.getString( "CodAgd" ), i, 0 );
					tabAgd.setValor( situacao, i, 1 );
					tabAgd.setValor( prioridade, i, 2 );
					tabAgd.setValor( rs.getString( "AssuntoAgd" ), i, 3 );
					tabAgd.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DtaIniAgd" ) ), i, 4 );
					tabAgd.setValor( rs.getString( "HrIniAgd" ), i, 5 );
					tabAgd.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DtaFimAgd" ) ), i, 6 );
					tabAgd.setValor( rs.getString( "HrFimAgd" ), i, 7 );
					
					int icor = rs.getInt( "CORAGENDA" );
					
					tabAgd.setColColor( i, 3, new Color(icor), icor < -10000 ? Color.WHITE : Color.BLACK  );
					*/
					
//					calendarpa
					
					

				}

				rs.close();
				ps.close();

				con.commit();

			} 
			catch ( SQLException err ) {
				Funcoes.mensagemErro( cOrig, "Erro ao carregar agenda!\n" + err.getMessage(), true, con, err );
			}

		}
		else {
			Funcoes.mensagemErro( cOrig, "Não existe agente para o objeto especificado!" );
		}

		tabAgd.repaint();

	}

	

}
