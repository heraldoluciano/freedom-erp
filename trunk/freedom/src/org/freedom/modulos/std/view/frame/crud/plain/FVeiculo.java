/**
 * @version 05/07/2011 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.view.frame.crud.plain <BR>
 *         Classe: @(#)FVeiculo.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * 
 *         Tela de cadastro de veículos.
 * 
 */

package org.freedom.modulos.std.view.frame.crud.plain;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.DLCor;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.fnc.view.dialog.report.DLRSinalizadores;

public class FVeiculo extends FDados implements ActionListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtPlaca= new JTextFieldPad( JTextFieldPad.TP_STRING, 7, 0 );

	private JTextFieldPad txtRenavam = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );
	
	private JTextFieldPad txtFabricante = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtModelo = new JTextFieldPad( JTextFieldPad.TP_STRING, 250, 0 );
	
	private JTextFieldPad txtDescCor = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );
	
	private JTextFieldPad txtCodCor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtAnoFabric = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );
	
	private JTextFieldPad txtAnoModelo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );
	
	private JTextAreaPad txtObs = new JTextAreaPad();
	
	private JScrollPane pnObs = new JScrollPane(txtObs);
	
	private JButtonPad btCor = new JButtonPad();
	
	private DLCor dlcor = null;
	
	public FVeiculo() {

		super();

		nav.setNavigation( true, Types.CHAR );

		setTitulo( "Sinalizadores" );
		
		setAtribos( 50, 50, 550, 250 );
		
		txtPlaca.setMascara( JTextFieldPad.MC_PLACA );
		
		adicCampo( txtPlaca			, 7		, 20	, 100	, 20, "Placa"		, "Placa", ListaCampos.DB_PK, true );
		adicCampo( txtRenavam 		, 110	, 20	, 120	, 20, "Renavam"		, "Renavam", ListaCampos.DB_SI, false );
		adicCampo( txtFabricante 	, 233	, 20	, 137	, 20, "Fabricante"	, "Fabricante", ListaCampos.DB_SI, false );
		adicCampo( txtAnoFabric 	, 373	, 20	, 75	, 20, "AnoFabric"	, "Ano Fabric.", ListaCampos.DB_SI, false );
		adicCampo( txtAnoModelo 	, 451	, 20	, 75	, 20, "AnoModelo"	, "Ano Modelo", ListaCampos.DB_SI, false );

		adicCampo( txtModelo		, 7		, 60	, 363	, 20, "MODELO"		, "Modelo", ListaCampos.DB_SI, false );
		adicCampo( txtDescCor 		, 373	, 60	, 133	, 20, "DescCor"		, "Cor", ListaCampos.DB_SI, false );		
		adic( btCor					, 506	, 60	, 19	, 19 );
		
		adicCampoInvisivel( txtCodCor, "CODCOR", "", ListaCampos.DB_SI, false );
		
		adicDB( txtObs, 7, 100, 519, 60, "Obs", "Observações", false );
		
		setListaCampos( false, "VEICULO", "VD" );
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		lcCampos.setQueryInsert( false );
			
		btCor.addActionListener( this );
		
		lcCampos.addCarregaListener( this );
		
		
			
		setImprimir( true );
	}


	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		else if ( evt.getSource() == btCor ) {
			if ( dlcor == null ) {
				
				dlcor = new DLCor();
				
			}

			dlcor.setCor( btCor.getBackground() );
			dlcor.setVisible( true );

			
			if(dlcor.OK) {
				
				lcCampos.edit();
				
				btCor.setBackground( dlcor.getCor() );
				
				txtCodCor.setVlrInteger( dlcor.getCor().getRGB() );
				
			}
			
		} 
		
		
		super.actionPerformed( evt );
	}

	private void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		DLRSinalizadores sl = null;
		ImprimeOS imp = null;
		int linPag = 0;

		sl = new DLRSinalizadores();
		sl.setVisible( true );
		if ( sl.OK == false ) {
			sl.dispose();
			return;
		}

		try {

			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.montaCab();
			imp.setTitulo( "Relatório de Sinalizadores" );
			imp.limpaPags();

			sSQL = "SELECT PLACA,MODELO FROM VDVEICULO WHERE CODEMP=? AND CODFILIAL=? ORDER BY " + sl.getValor();

			ps = con.prepareStatement( sSQL );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVEICULO" ) );
			
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				if ( imp.pRow() == 0 ) {
					imp.impCab( 80, false );
					imp.say( imp.pRow(), 0, imp.normal() );
					imp.say( imp.pRow(), 2, "Placa" );
					imp.say( imp.pRow(), 30, "Modelo" );
					imp.say( imp.pRow() + 1, 0, imp.normal() );
					imp.say( imp.pRow(), 0, StringFunctions.replicate( "-", 79 ) );
				}
				imp.say( imp.pRow() + 1, 0, imp.normal() );
				imp.say( imp.pRow(), 2, rs.getString( "Placa" ) );
				imp.say( imp.pRow(), 30, rs.getString( "Modelo" ) );
				if ( imp.pRow() >= linPag ) {
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, StringFunctions.replicate( "-", 79 ) );
					imp.incPags();
					imp.eject();
				}
			}

			imp.say( imp.pRow() + 1, 0, imp.normal() );
			imp.say( imp.pRow(), 0, StringFunctions.replicate( "=", 79 ) );
			imp.eject();

			imp.fechaGravacao();
			con.commit();
			sl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de veículos!" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			sl = null;
		}

		if ( bVisualizar )
			imp.preview( this );
		else
			imp.print();
	}

	public void afterCarrega( CarregaEvent pevt ) {
		
		Color cor = new Color( txtCodCor.getVlrInteger() );

		btCor.setBackground( cor );
		btCor.repaint();
		btCor.revalidate();
		
	}


	public void beforeCarrega( CarregaEvent cevt ) {

		// TODO Auto-generated method stub
		
	}
	
}
