/**
 * @version 07/2007 <BR>
 * @author Setpoint Informática Ltda. / Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRemCnab.java <BR>
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
 * Tela de remessa de arquivo de CNAB.
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.FileDialog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.fnc.CnabUtil.Reg1;
import org.freedom.modulos.fnc.CnabUtil.Reg3P;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;


public class FRemCnab extends FRemFBN {
	
	public static final long serialVersionUID = 1L;
	
	private final CnabUtil cnabutil = new CnabUtil();

	public FRemCnab() {

		super( TIPO_FEBRABAN_CNAB );
	}
	
	private Reg1 getReg1() {
		
		Reg1 reg = cnabutil.new Reg1();
		/*
		reg.setCodBanco( (String) prefs.get( FbnUtil.EPrefs.CODBANCO ) );
		reg.setLoteServico( loteServico );
		reg.setTipoOperacao( tipoOperacao );
		reg.setFormaLancamento( formaLancamento );
		reg.setTipoInscEmp( 2 );
		reg.setCpfCnpjEmp(  );
		reg.setCodConvBanco( codConvBanco );
		reg.setAgencia( prefs.get( FbnUtil.EPrefs. ) );
		reg.setDigAgencia( digAgencia );
		reg.setConta( conta );
		reg.setDigConta( digConta );
		reg.setDigAgConta( digAgeConta );
		reg.setRazEmp( (String) prefs.get( FbnUtil.EPrefs.NOMEEMP ) );
		reg.setMsg1( msg1 );
		reg.setMsg2( msg2 );
		reg.setNrRemRet( nrRemRet );
		reg.setDataRemRet( dataRemRet );
		reg.setDataCred( dataCred );*/
		
		return reg;
	}
	
	private Reg3P getReg3P () {
		
		Reg3P reg = cnabutil.new Reg3P();
		
		/*
		reg.setAgencia( agencia );
		reg.setDigAgencia( digAgencia );
		reg.setConta( conta );
		reg.setDigConta( digConta );
		reg.setDigAgConta( digAgConta );
		reg.setIdentTitulo( identTitulo );
		reg.setCodCarteira( codCarteira );
		reg.setFormaCadTitulo( formaCadTitulo );
		reg.setTipoDoc( tipoDoc );
		reg.setIdentEmitBol( identEmitBol );
		reg.setIdentDist( identDist );
		reg.setDocCobranca( docCobranca );
		reg.setDtVencTitulo( dtVencTitulo );
		reg.setVlrTitulo( vlrTitulo );
		reg.setAgenciaCob( agenciaCob );
		reg.setDigAgenciaCob( digAgenciaCob );
		reg.setEspecieTit( especieTit );
		reg.setAceite( aceite );
		reg.setDtEmitTit( dtEmitTit );
		reg.setCodJuros( codJuros );
		reg.setDtJuros( dtJuros );
		reg.setVlrJurosTaxa( vlrJurosTaxa ); 
		reg.setCodDesc( codDesc );
		reg.setDtDesc( dtDesc );
		reg.setVlrpercConced( vlrpercConced );
		reg.setVlrIOF( vlrIOF );
		reg.setVlrAbatimento( vlrAbatimento );
		reg.setIdentTitEmp( identTitEmp );
		reg.setCodProtesto( codProtesto );
		reg.setDiasProtesto( diasProtesto );
		reg.setCodBaixaDev( codBaixaDev );
		reg.setDiasBaixaDevol( diasBaixaDevol );  
		reg.setCodMoeda( codMoeda );
		reg.setContrOperCred( contrOperCred );*/
		
		return reg;
	}
	
	protected boolean execExporta() {
		
		boolean retorno = false;
		String sFileName = null;
		File fileSiacc = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		HashSet<CnabUtil.StuffCli> hsCli = new HashSet<CnabUtil.StuffCli>();
		HashSet<CnabUtil.StuffRec> hsRec = new HashSet<CnabUtil.StuffRec>();
	
		if ( consisteExporta( hsCli, hsRec ) ) {
			
			retorno = setPrefs();
			
			if ( retorno ) {
				
				lbStatus.setText( "     criando arquivo ..." );
		
				FileDialog fileDialogSiacc = null;
				fileDialogSiacc = new FileDialog( Aplicativo.telaPrincipal, "Exportar arquivo.", FileDialog.SAVE );
				sFileName = "remessa"+prefs.get( SiaccUtil.EPrefs.NROSEQ )+".txt";
				fileDialogSiacc.setFile( sFileName );
				fileDialogSiacc.setVisible( true );
		
				if ( fileDialogSiacc.getFile() == null ) {
					lbStatus.setText( "" );
					return retorno;
				}
		
				sFileName = fileDialogSiacc.getDirectory() + fileDialogSiacc.getFile();
		
				fileSiacc = new File( sFileName );
		
				try {
					fileSiacc.createNewFile();
					fw = new FileWriter( fileSiacc );
					bw = new BufferedWriter( fw );
		
					lbStatus.setText( "     gravando arquivo ..." );
					//retorno = gravaRemessa( bw, hsCli, hsRec );
				} catch ( IOException ioError ) {
					Funcoes.mensagemErro( this, "Erro Criando o arquivo!\n " + sFileName + "\n" + ioError.getMessage() );
					lbStatus.setText( "" );
					return retorno;
				}
				
				lbStatus.setText( "     pronto ..." );
				//atualizaSitremessaExp(hsCli, hsRec);
			}
			
		}
		return retorno;
	}
	
	private boolean gravaRemessa( final BufferedWriter bw, HashSet< SiaccUtil.StuffCli > hsCli, HashSet< SiaccUtil.StuffRec > hsRec ) {
		
		boolean retorno = false;
		
		try {
			
			ArrayList< CnabUtil.Reg > list = new ArrayList< CnabUtil.Reg >();
			
			list.add( getReg1() );
			
			for ( CnabUtil.Reg reg : list ) {
				bw.write( reg.getLine() );
			}
			
			bw.flush();
			bw.close();
		} 
		catch ( ExceptionCnab e ) {
			Funcoes.mensagemErro( this, e.getMessage() );
			lbStatus.setText( "" );
			retorno = false;
		}
		catch ( IOException ioError ) {
			Funcoes.mensagemErro( this, "Erro gravando no arquivo!\n" + ioError.getMessage() );
			lbStatus.setText( "" );
			retorno = false;
		}
		
		return retorno;
	}

	public void imprimir( boolean visualizar ) {

		ResultSet rs = null;
		String sDtFiltro = "E".equals( rgData.getVlrString() ) ? "IR.DTITREC" : "IR.DTVENCITREC";
		PreparedStatement ps = null;

		if ( txtCodBanco.getVlrString().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Código do banco é requerido!" );
			return;
		}

		try {

			StringBuilder sSQL = new StringBuilder();

			sSQL.append( "SELECT IR.CODREC, IR.NPARCITREC, R.DOCREC, R.CODCLI, C.RAZCLI, IR.DTITREC, IR.DTVENCITREC," );
			sSQL.append( "IR.VLRAPAGITREC, FC.AGENCIACLI, FC.IDENTCLI, COALESCE(FR.SITREMESSA,'00') SITREMESSA, " );
			sSQL.append( "FR.SITRETORNO, COALESCE(COALESCE(FR.STIPOFEBRABAN,FC.STIPOFEBRABAN),'02') STIPOFEBRABAN, " );
			sSQL.append( "COALESCE(FC.TIPOREMCLI,'B') TIPOREMCLI, C.PESSOACLI, C.CPFCLI, C.CNPJCLI " );
			sSQL.append( "FROM VDCLIENTE C," );
			sSQL.append( "FNRECEBER R LEFT OUTER JOIN FNFBNCLI FC ON " );
			sSQL.append( "FC.CODEMP=R.CODEMPCL AND FC.CODFILIAL=R.CODFILIALCL AND FC.CODCLI=R.CODCLI ," );
			sSQL.append( "FNITRECEBER IR LEFT OUTER JOIN FNFBNREC FR ON " );
			sSQL.append( "FR.CODEMP=IR.CODEMP AND FR.CODFILIAL=IR.CODFILIAL AND " );
			sSQL.append( "FR.CODREC=IR.CODREC AND FR.NPARCITREC=IR.NPARCITREC AND " );
			sSQL.append( "FR.CODEMPBO=IR.CODEMPBO AND FR.CODFILIALBO=IR.CODFILIALBO AND FR.CODBANCO=IR.CODBANCO " );
			sSQL.append( "WHERE R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND R.CODREC=IR.CODREC AND " );
			sSQL.append( "C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI AND " );
			sSQL.append( sDtFiltro );
			sSQL.append( " BETWEEN ? AND ? AND IR.STATUSITREC IN ('R1','RL') AND " );
			sSQL.append( "IR.CODEMPBO=? AND IR.CODFILIALBO=? AND IR.CODBANCO=? " );
			sSQL.append( where );
			sSQL.append( "ORDER BY C.RAZCLI, R.CODREC, IR.NPARCITREC " );
			ps = con.prepareStatement( sSQL.toString() );

			ps.setDate( 1, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
			ps.setDate( 2, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, Aplicativo.iCodFilial );
			ps.setInt( 5, txtCodBanco.getVlrInteger() );

			rs = ps.executeQuery();

			HashMap< String, Object > hParam = new HashMap< String, Object >();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "REPORT_CONNECTION", con );

			FPrinterJob dlGr = new FPrinterJob( "relatorios/RemSiacci.jasper", "RELATÓRIO DE REMESSA", null, rs, hParam, this );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
			e.printStackTrace();
		}
	}
}
