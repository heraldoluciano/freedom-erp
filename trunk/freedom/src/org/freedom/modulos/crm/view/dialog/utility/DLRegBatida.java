/**
 * @version 19/09/2011 <BR>
 * @author Setpoint Informática Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe: @(#)DLRegBatida.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Tela que registra Batida de ponto do empregado.
 * 
 */

package org.freedom.modulos.crm.view.dialog.utility;

import java.awt.event.ActionEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.modulos.gpe.business.object.Batida;
import org.freedom.modulos.grh.view.frame.crud.plain.FTurnos;

public class DLRegBatida extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private final JTextFieldFK txtMatempr = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 6, 0 );
	
	private final JTextFieldFK txtNomeempr = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0);
	
	private final JTextFieldFK txtDtbat = new JTextFieldFK( JTextFieldFK.TP_DATE, 10, 0 );

	private final JTextFieldPad txtHbat = new JTextFieldPad( JTextFieldPad.TP_TIME, 5, 0 );
	
	private final ListaCampos lcBatida = new ListaCampos(this);
	
	private final ListaCampos lcEmpr = new ListaCampos(this, "EP");
	
	private Batida batida = null;
	
	public DLRegBatida() {

		super();
		setTitulo( "Registro da batida." );
		setAtribos( 50, 50, 450, 200 );
		montaListaCampos();
		montaTela();
	
	}
	
	public void montaTela(){

		adic( txtMatempr, 7, 25, 70, 20, "Matrícula" );
		adic( txtNomeempr, 80, 25, 300, 20, "Nome");
		adic( txtDtbat, 7, 65, 90, 20, "Data" );
		adic( txtHbat, 100, 65, 90, 20, "Horário");
		
	}
	
	public void setValores(Batida batida) {
		this.batida = batida;
		txtMatempr.setVlrInteger( batida.getMatempr() );
		lcEmpr.carregaDados();
		txtDtbat.setVlrDate( batida.getDataponto() );
		txtHbat.setVlrString( batida.getHoraponto() );
	}
	
	public void montaListaCampos(){
		lcEmpr.add( new GuardaCampo( txtMatempr, "Matempr", "Matrícula", ListaCampos.DB_PK, false ) );
		lcEmpr.add( new GuardaCampo( txtNomeempr, "Nomeempr", "Nome", ListaCampos.DB_SI, false ) );
		lcEmpr.montaSql( false, "EMPREGADO", "RH" );
		lcEmpr.setQueryCommit( false );
		lcEmpr.setReadOnly( true );
		txtMatempr.setTabelaExterna( lcEmpr, FTurnos.class.getCanonicalName() );
	}

	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcEmpr.setConexao( cn );

	}
}
