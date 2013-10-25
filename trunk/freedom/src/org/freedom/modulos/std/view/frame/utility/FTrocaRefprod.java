/**
 * @version 23/10/2013 <BR>
 * @author Setpoint Tecnologia em Informática Ltda./Robson Sanchez<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.view.frame.utility <BR>
 *         Classe: @(#)FTrocaRefprod.java <BR>
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
 *         Classe responsável pela substituição de referência dos produtos.
 */

package org.freedom.modulos.std.view.frame.utility;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.dao.DAOTrocaRefprod;
import org.freedom.modulos.std.dao.DAOTrocaRefprod.Change;
import org.freedom.modulos.std.dao.DAOTrocaRefprod.Table;

public class FTrocaRefprod extends FDetalhe implements InsertListener, PostListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtId = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtMotivo = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtDtTroca = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtSituacao = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtCodprod = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtRefprodold = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtRefprodnew = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtDescprod = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSituacaoIt = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtId_troca = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtId_it = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private ListaCampos lcProd = new ListaCampos( this, "PD" );

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JProgressBar pbAndamento = new JProgressBar();

	private JButtonPad btExecutar = new JButtonPad( Icone.novo( "btGerar.png" ) );

	JPanelPad pnAndamento = new JPanelPad();

	private DAOTrocaRefprod daotrocarefprod = null;

	public FTrocaRefprod() {

		setTitulo( "Troca referência dos produtos" );
		setAtribos( 10, 10, 600, 450 );

		setAltCab( 130 );
		pinCab = new JPanelPad( 420, 90 );

		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );

		lcProd.add( new GuardaCampo( txtCodprod, "codprod", "Cód.prod.", ListaCampos.DB_PK, true ) );
		lcProd.add( new GuardaCampo( txtRefprodold, "refprod", "Referência", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtDescprod, "descprod", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );
		txtCodprod.setTabelaExterna( lcProd, FProduto.class.getCanonicalName() );

		txtSituacao.setSoLeitura( true );
		txtSituacaoIt.setSoLeitura( true );

		adicCampo( txtId, 7, 20, 70, 20, "id", "ID.", ListaCampos.DB_PK, true );
		adicCampo( txtMotivo, 80, 20, 330, 20, "motivo", "Motivo", ListaCampos.DB_SI, true );
		adicCampo( txtDtTroca, 413, 20, 80, 20, "dttroca", "Data troca", ListaCampos.DB_SI, true );
		adicCampo( txtSituacao, 496, 20, 80, 20, "situacao", "Situação", ListaCampos.DB_SI, false );
		setListaCampos( false, "TROCAREFPROD", "EQ" );
		adic( btExecutar, 7, 50, 30, 30 );
		adic( pbAndamento, 43, 50, 300, 30 );

		setAltDet( 100 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		adicCampo( txtId_it, 7, 20, 40, 20, "id_it", "ID.", ListaCampos.DB_PK, true );
		adicCampo( txtCodprod, 50, 20, 70, 20, "Codprod", "Cód.prod.", ListaCampos.DB_FK, txtDescprod, true );
		adicDescFK( txtDescprod, 123, 20, 330, 20, "Descprod", "Descrição do produto" );
		adicCampo( txtRefprodold, 7, 60, 150, 20, "refprodold", "Referência atual", ListaCampos.DB_SI, true );
		adicCampo( txtRefprodnew, 160, 60, 150, 20, "refprodnew", "Referência nova", ListaCampos.DB_SI, true );
		adicCampo( txtSituacaoIt, 313, 60, 80, 20, "situacao", "Situação", ListaCampos.DB_SI, false );

		setListaCampos( false, "ITTROCAREFPROD", "EQ" );

		montaTab();
		int col = 0;
		tab.setTamColuna( 60, col++ );
		tab.setTamColuna( 100, col++ );
		tab.setTamColuna( 350, col++ );
		lcCampos.addInsertListener( this );
		lcDet.addInsertListener( this );
		txtId.setEditable( false );
		txtId_it.setAtivo( false );
		txtRefprodold.setAtivo( false );
		lcCampos.addCarregaListener( this );
		lcDet.addPostListener( this );
		btExecutar.addActionListener( this );
	}

	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		lcProd.setConexao( cn );
		daotrocarefprod = new DAOTrocaRefprod( cn, Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "EQTROCAREFPROD" ) );
	}

	public void beforeInsert( InsertEvent ievt ) {

	}

	public void afterInsert( InsertEvent ievt ) {
		try {
			if ( ievt.getListaCampos() == lcCampos ) {
				txtId.setVlrInteger( lcCampos.gerarSeqId() );
				txtDtTroca.setVlrDate( new Date() );
			}
			else if ( ievt.getListaCampos() == lcDet ) {
				txtId_troca.setVlrInteger( txtId.getVlrInteger() );
				txtId_it.setVlrInteger( lcDet.gerarSeqId() );
			}
		} catch ( SQLException e ) {
			try {
				con.rollback();
			} catch ( SQLException e1 ) {
				e1.printStackTrace();
			}
			Funcoes.mensagemErro( this, "Erro buscando sequencia para ID.\n" + e.getMessage() );
		}

	}

	public void beforePost( PostEvent pevt ) {
		super.beforePost( pevt );
		if ( pevt.getListaCampos() == lcDet ) {
			try {
				StringBuffer seek = daotrocarefprod.seekRefprod( txtRefprodnew.getVlrString() );
				if ( seek.length() > 0 ) {
					pevt.cancela();
					Funcoes.mensagemInforma( this, seek.toString() );
					return;
				}
			} catch ( Exception e ) {
				pevt.cancela();
				Funcoes.mensagemErro( this, "Erro pesquisando referência !\n" + e.getMessage() );
			}
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {
		if ( cevt.getListaCampos() == lcCampos ) {
			if ( !txtId_troca.getVlrString().equals( txtId.getVlrString() ) ) {
				txtId_troca.setVlrInteger( txtId.getVlrInteger() );
				lcDet.carregaDados();
			}
		}
	}

	public void actionPerformed( ActionEvent evt ) {
		super.actionPerformed( evt );
		if ( evt.getSource() == btExecutar ) {
			execute();
		}
	}

	private void execute() {
		if ( txtId.getVlrInteger().intValue() == 0 || txtId_it.getVlrInteger() == 0 ) {
			Funcoes.mensagemInforma( this, "Selecione a(s) referência(s) para execução da(s) troca(s) !" );
			return;
		}
		if ( Funcoes.mensagemConfirma( this, "Confirma execução da troca !" ) == JOptionPane.YES_NO_OPTION ) {
			try {
				Vector<Table> tables = daotrocarefprod.selectTableChange();
				Vector<Change> valuesChange = daotrocarefprod.selectValuesChange( txtId.getVlrInteger() );
				if ( tables.size() == 0 ) {
					Funcoes.mensagemInforma( this, "Não foram encontradas tabelas para execução da troca !" );
					return;
				}
				executeChange(tables, valuesChange);
			} catch ( Exception e ) {
				Funcoes.mensagemErro( this, "Erro executando a troca !\n" + e.getMessage() );
			}
		}
	}

	private void executeChange(Vector<Table> tables, Vector<Change> valuesChange) throws Exception {
		pbAndamento.setMinimum( 0 );
		pbAndamento.setMaximum( tables.size() * valuesChange.size() );
		int i=0;
		for (Change value: valuesChange) { 
			for (Table table:tables) {
				table.setCodfilial( ListaCampos.getMasterFilial( table.getTable_name() ) );
				daotrocarefprod.executeChange(value, table);
				i++;
				pbAndamento.setValue( i );
				pbAndamento.updateUI();
			}
		}
	}
}
