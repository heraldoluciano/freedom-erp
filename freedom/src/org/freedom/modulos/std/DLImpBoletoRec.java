/**
 * @version 18/11/2008 <BR>
 * @author Setpoint Informática Ltda./Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLImpBoletoRec.java <BR>
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

package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JInternalFrame;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ObjetoEmpresa;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDialogo;
import org.freedom.telas.FPrinterJob;

public class DLImpBoletoRec extends FDialogo {

	private static final long serialVersionUID = 1L;
	
	public JTextFieldPad txtCodModBol = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescModBol = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtImpInst = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldFK txtPreImpModBol = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldFK txtClassModBol = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private ListaCampos lcModBol = new ListaCampos( this );
	
	private int codRec;
	
    private	int parcRec;
	
	Connection con = null;
	
	private JInternalFrame owner = null;
	
	public DLImpBoletoRec( JInternalFrame owner, Connection con, int codRec, int parcRec ){
		
		super();
		setAtribos( 370, 150 );
		setTitulo( "Impressão de Boleto" );
		
		this.con = con;
		this.codRec = codRec;
		this.parcRec = parcRec;
		this.owner = owner;
		
		montaListaCampos();
		montaTela();
		
	}
	
	private void montaListaCampos(){
		
		/********************
		 * MODELO DE BOLETO *
		 ********************/
		lcModBol.add( new GuardaCampo( txtCodModBol, "CodModBol", "Cód.mod.", ListaCampos.DB_PK, true ) );
		lcModBol.add( new GuardaCampo( txtImpInst, "ImpInfoParc", "Imp.Info.", ListaCampos.DB_SI, false ) );
		lcModBol.add( new GuardaCampo( txtDescModBol, "DescModBol", "Descrição do modelo de boleto", ListaCampos.DB_SI, false ) );
		lcModBol.add( new GuardaCampo( txtPreImpModBol, "PreImpModBol", "Pré-impr.", ListaCampos.DB_SI, false ) );
		lcModBol.add( new GuardaCampo( txtClassModBol, "ClassModBol", "Classe do modelo", ListaCampos.DB_SI, false ) );
		lcModBol.setReadOnly( true );
		lcModBol.montaSql( false, "MODBOLETO", "FN" );
		txtCodModBol.setTabelaExterna( lcModBol );
		txtCodModBol.setFK( true );
		txtCodModBol.setNomeCampo( "CodModBol" );
		lcModBol.setConexao( con );
	}
	
	private void montaTela(){
		
		adic( new JLabelPad("Cód.Mod.Bol."), 7, 10, 80, 20 );
		adic( txtCodModBol, 7, 30, 80, 20 );
		adic( new JLabelPad("Descrição do modelo de boleto"), 90, 10, 220, 20 );
		adic( txtDescModBol, 90, 30, 250, 20 );
	}
	
	public void imprimir(){		
		
		if ( txtCodModBol.getVlrString().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Modelo de boleto não selecionado!" );
			txtCodModBol.requestFocus();
			return;
		}
		
		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		sSQL.append( "SELECT ITR.DTVENCITREC,ITR.NPARCITREC,ITR.VLRAPAGITREC, ITR.VLRPARCITREC, MB.PREIMPMODBOL, " );
		sSQL.append( "ITR.VLRDESCITREC, (ITR.VLRJUROSITREC+ITR.VLRMULTAITREC) VLRMULTA, R.DOCREC,ITR.CODBANCO, B.DVBANCO, " );
		sSQL.append( "B.IMGBOLBANCO LOGOBANCO01, B.IMGBOLBANCO LOGOBANCO02, B.IMGBOLBANCO LOGOBANCO03, IM.CODCARTCOB, " );
		sSQL.append( "MB.ESPDOCMODBOL ESPDOC, MB.ACEITEMODBOL ACEITE, MB.MDECOB, ITR.dtitrec AS DTEMITVENDA, " );
		sSQL.append( "C.RAZCLI,C.CPFCLI,C.CNPJCLI, C.ENDCLI,C.NUMCLI,C.COMPLCLI,C.CEPCLI,C.BAIRCLI, " );
		sSQL.append( "C.CIDCLI,C.UFCLI, C.ENDCOB,C.NUMCOB,C.COMPLCOB,C.CEPCOB,C.BAIRCOB,C.CIDCOB,C.UFCOB, P.CODMOEDA, " );
		sSQL.append( "C.PESSOACLI, (ITR.DTVENCITREC-CAST('07.10.1997' AS DATE)) FATVENC, M.CODFBNMOEDA, " );
		sSQL.append( "CT.AGENCIACONTA, MB.NUMCONTA, MB.DESCLPMODBOL, MB.INSTPAGMODBOL, IM.CONVCOB, ITR.DESCPONT " );
		sSQL.append( "FROM VDCLIENTE C, FNRECEBER R, SGPREFERE1 P, FNMOEDA M, FNBANCO B, FNMODBOLETO MB, " );
		sSQL.append( "FNITMODBOLETO IM, FNITRECEBER ITR, SGFILIAL F, FNCONTA CT " );
		sSQL.append( "WHERE " );
		sSQL.append( "C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI " );
		sSQL.append( "AND P.CODEMP=R.CODEMP AND P.CODFILIAL=R.CODFILIAL " );
		sSQL.append( "AND F.CODEMP=R.CODEMP AND F.CODFILIAL=R.CODFILIAL " );
		sSQL.append( "AND M.CODEMP=P.CODEMPMO AND M.CODFILIAL=P.CODFILIALMO AND M.CODMOEDA=P.CODMOEDA " );
		sSQL.append( "AND B.CODEMP=ITR.CODEMPBO AND B.CODFILIAL=ITR.CODFILIALBO AND B.CODBANCO=ITR.CODBANCO " );
		sSQL.append( "AND IM.CODEMP=MB.CODEMP AND IM.CODFILIAL=MB.CODFILIAL AND IM.CODMODBOL=MB.CODMODBOL " );
		sSQL.append( "AND IM.CODEMPBO=B.CODEMP AND IM.CODFILIALBO=B.CODFILIAL AND IM.CODBANCO=B.CODBANCO " );
		sSQL.append( "AND IM.CODEMPCB=ITR.CODEMPCB AND IM.CODFILIALCB=ITR.CODFILIALCB AND IM.CODCARTCOB=ITR.CODCARTCOB " );
		sSQL.append( "AND CT.CODEMP=MB.CODEMPCC AND CT.CODFILIAL=MB.CODFILIALCC AND CT.NUMCONTA=MB.NUMCONTA " );
		sSQL.append( "AND ITR.CODEMP=R.CODEMP AND ITR.CODFILIAL=R.CODFILIAL AND ITR.CODREC=R.CODREC " );
		sSQL.append( "AND ITR.STATUSITREC IN ('R1','RL') " );
		sSQL.append( "AND MB.CODEMP=? AND MB.CODFILIAL=? AND MB.CODMODBOL=?" );
		sSQL.append( "AND R.CODEMP=? AND R.CODFILIAL=? AND R.CODREC=? AND ITR.nparcitrec=? " );
		
		try {
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 3, txtCodModBol.getVlrInteger() );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 6, codRec );
			ps.setInt( 7, parcRec );
			rs = ps.executeQuery();
			
			imprimeGrafico( true, rs );
			
		} catch ( SQLException err ) {
			
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados do boleto!" );
		}
	}
	
	private HashMap< String, Object > getParametros() {

		HashMap< String, Object > parametros = new HashMap< String, Object >();
		ObjetoEmpresa empresa = new ObjetoEmpresa( con );
		
		parametros.put( "CODEMP", Aplicativo.iCodEmp );
		parametros.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNITRECEBER" ) );
		parametros.put( "IMPDOC", txtImpInst.getVlrString() );
		
		if ( Aplicativo.empresa != null ) {
			parametros.put(  "RAZEMP", empresa.getAll().get( "RAZEMP" ) );
		}

		return parametros;
	}
	
	private void imprimeGrafico( final boolean bVisualizar, final ResultSet rs ){
		
		FPrinterJob dlGr = new FPrinterJob( "relatorios/boleto.jasper", "Boleto", null, rs, getParametros(), owner );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro ao tentar imprimir boleto!" + err.getMessage(), true, con, err );
			}
		}
	}
}
