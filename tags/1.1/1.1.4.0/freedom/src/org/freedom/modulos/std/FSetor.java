/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FSetor.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
import org.freedom.telas.FPrinterJob;

public class FSetor extends FDados implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	
	private JTextFieldPad txtDescSetor= new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
	
	public FSetor () {
		
		super();
		setTitulo("Cadastro de Setor");
		setAtribos( 50, 50, 350, 125);
		
		adicCampo(txtCodSetor, 7, 20, 70, 20,"CodSetor","C�d.setor",ListaCampos.DB_PK, true);
		adicCampo(txtDescSetor, 80, 20, 250, 20,"DescSetor","Descri��o do setor",ListaCampos.DB_SI, true);
		setListaCampos( true, "SETOR", "VD");
		
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		lcCampos.setQueryInsert(false);    
		
		setImprimir(true);
	}
	
	public void actionPerformed(ActionEvent evt) {
		
		if (evt.getSource() == btPrevimp){
			imprimir(true);
		}
		else if (evt.getSource() == btImp) {
			imprimir(false);
		}
		super.actionPerformed(evt);
	}
	
	private void imprimir(boolean bVisualizar) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		DLRSetor dl = new DLRSetor();
		dl.setVisible(true);
		
		try {
			
			if (dl.OK == false) {
				dl.dispose();
				return;
			}
			
			sSQL = "SELECT CODSETOR,DESCSETOR " +
				   "FROM VDSETOR " +
				   "WHERE CODEMP=? AND CODFILIAL=? " +
				   "ORDER BY " + dl.getValor();
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("VDSETOR"));
			rs = ps.executeQuery();
			
			if ( "T".equals( dl.getTipo() ) ) {
				imprimirTexto( bVisualizar, rs );
			}
			else if ( "G".equals( dl.getTipo() ) ) {
				imprimirGrafico( bVisualizar, rs );
			}

			
			rs.close();
			ps.close();
			
			if (!con.getAutoCommit()){
				con.commit();
			}
			dl.dispose();
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de setores!\n"+err.getMessage(),true,con,err);      
		}    
	}
	
	private void imprimirTexto( final boolean bVisualizar, final ResultSet rs ) {
		
		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		imp.montaCab();

		try {
			
			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relat�rio de Setor" );
			
			
		while ( rs.next() ) {
			if (imp.pRow()==0) {
				imp.impCab(80, false);
				imp.say(imp.pRow(), 0, imp.normal());
				imp.say(imp.pRow(), 2, "C�digo");
				imp.say(imp.pRow(), 30, "Descri��o");
				imp.say(imp.pRow() + 1, 0, "" + imp.normal());
				imp.say(imp.pRow(), 0, Funcoes.replicate("-",79));
			}
			imp.say(imp.pRow() + 1, 0, imp.normal());
			imp.say(imp.pRow(), 2, rs.getString("Codsetor"));
			imp.say(imp.pRow(), 30, rs.getString("Descsetor"));
			
			if (imp.pRow()>=linPag) {
				imp.say(imp.pRow() + 1, 0, imp.normal());
				imp.say(imp.pRow(), 0, Funcoes.replicate("-",79));
				imp.incPags();
				imp.eject();
			}
		}
		
		imp.say(imp.pRow() + 1, 0, imp.normal());
		imp.say(imp.pRow(), 0, Funcoes.replicate("=",79));
		imp.eject();      
		imp.fechaGravacao();
		
			if ( bVisualizar ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}	
		
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de setores!\n"+err.getMessage(),true,con,err);      
		} 
	
	}
	
	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs ) {

		FPrinterJob dlGr = new FPrinterJob( "relatorios/Setor.jasper", "Relatorio por Setor", null, rs, null, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o do relatorio por Setor!" + err.getMessage(), true, con, err );
			}
		}
	}
}