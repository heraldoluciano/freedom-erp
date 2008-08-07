/**
 * @version 6/08/2008 <BR>
 * @author Setpoint Informática Ltda.
 * @author Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)FContraProva.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Comentários sobre a classe...
 */

package org.freedom.modulos.pcp;

import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDetalhe;


public class FContraProva extends FDetalhe {
	
	private static final long serialVersionUID = 1L;
	
	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();
	
	private JTextFieldPad txtCodRetCp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8 , 0 );
	
	private JTextFieldPad txtCodRetCpIt = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8 , 0 );
	
	private JTextFieldPad txtQtdItRet = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15 , 5 );
	
	private JTextFieldPad txtCodOp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5 , 0 );
	
	private JTextFieldPad txtSeqOp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5 , 0 );
	
	private JTextFieldPad txtDtRet = new JTextFieldPad( JTextFieldPad.TP_DATE, 12 , 0 );
	
	private JTextFieldPad txtDtDesc = new JTextFieldPad( JTextFieldPad.TP_DATE, 12 , 0 );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	 
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
		
	private JTextFieldFK txtRefProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldFK txtRefProdOp = new JTextFieldFK( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private ListaCampos lcProd = new ListaCampos( this, "PD"  );
	
	private ListaCampos lcOp = new ListaCampos( this, "" );
	
	
	public FContraProva( int codOp, int seqOp ){
		
		this();
		txtCodOp.setVlrInteger( codOp );
		txtSeqOp.setVlrInteger( seqOp );
		txtCodRetCp.setEditable( false );
		txtCodOp.setEditable( false );
		txtSeqOp.setEditable( false );
		txtDtDesc.setEditable( false );
		txtDtRet.setEditable( false );
		txtCodProd.setEditable( false );
		txtQtdItRet.setEditable( false );
		txtCodRetCpIt.setEditable( false );
		
		
	}
	
	public FContraProva(){
		
		setAtribos( 50, 50, 450, 350 );
		setTitulo( "Contra prova" );
		
		montaListaCampos();
		montaTela();
		
	}
	
	private void montaTela(){
		
		/*************
		 * Cabeçalho * 
		 *************/
		
		pinCab = new JPanelPad( 440, 80 );		
		setPainel( pinCab, pnCliCab );
		setListaCampos( lcCampos );
		adicCampo( txtCodRetCp, 7, 25, 70, 20, "CODRETCP", "Cód.Ret.Cp", ListaCampos.DB_PK, true );
		adicCampo( txtCodOp, 80, 25, 70, 20, "CODOP", "Cód.Op.", ListaCampos.DB_FK, true );
		adicCampo( txtSeqOp, 153, 25, 55, 20, "SEQOP", "Seq.Op.", ListaCampos.DB_FK, true );
		adicCampo( txtDtRet, 211, 25, 100, 20, "DTRETENCAO", "Data retenção", ListaCampos.DB_SI, true );
		adicCampo( txtDtDesc, 314, 25, 100, 20, "DTDESCARTE", "Data descarte", ListaCampos.DB_SI, true );
		setListaCampos( true, "RETCP", "PP" );
		lcCampos.setQueryInsert( true );
		lcCampos.adicDetalhe( lcDet );		
		
		/************
		 * Detalhe  * 
		 ************/
		
		setAltDet( 80 );
		pinDet = new JPanelPad( 600, 80 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		adicCampo( txtCodRetCpIt, 10, 25, 40, 20, "CODITRETCP", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtCodProd, 53, 25, 80, 20, "CODPROD", "Cód.Prod", ListaCampos.DB_FK, txtDescProd, true );
		adicDescFK( txtDescProd, 136, 25, 225, 20, "DESCPROD", "Descrição do produto" );
		adicCampo( txtQtdItRet, 364, 25, 60, 20, "QTDITRET", "Qtd.It", ListaCampos.DB_SI, true );
		setListaCampos( true, "ITRETCP", "PP" );
		lcDet.setMaster( lcCampos );
		lcDet.setQueryInsert( true ); 
		
		montaTab();
		tab.setTamColuna( 25, 0 );
		tab.setTamColuna( 260, 2 );
		tab.setTamColuna( 70, 3 );

	}
	
	private void montaListaCampos(){
		
		/**************
		 *  Produto   * 
		 **************/
		
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, txtDescProd,  true ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		txtCodProd.setTabelaExterna( lcProd );
		
		/**************
		 *     OP     * 
		 **************/
		
		lcOp.add( new GuardaCampo( txtCodOp, "CodOp", "Cód.Op", ListaCampos.DB_PK, true ) );
		lcOp.add( new GuardaCampo( txtSeqOp, "SeqOp", "Seq.Op", ListaCampos.DB_PK, true ) );
		txtCodOp.setNomeCampo( "CodOp" );
		lcOp.setReadOnly( true );
		lcOp.montaSql( false, "OP", "PP" );
		txtCodOp.setTabelaExterna( lcOp );
		txtSeqOp.setTabelaExterna( lcOp );
		
		
	}
	
	public void setConexao( Connection con ){
		
		super.setConexao( con );
		lcProd.setConexao( con );
		lcOp.setConexao( con );
		
	}
}
