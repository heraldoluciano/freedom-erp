/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTabICMS.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Tabela de alíquotas de ICMS por estado. 
 * 
 */

package org.freedom.modulos.std;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class FTabICMS extends FDados implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtUFTabICMS = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
	private JTextFieldPad txtAliqTabICMS = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,9,2);

	public FTabICMS() {
		super();
		setTitulo("Cadastro de alíquotas de ICMS");
		setAtribos( 50, 50, 330, 125);
		adicCampo(txtUFTabICMS, 7, 20, 50, 20,"UFTI","UF", ListaCampos.DB_PK, true);
		adicCampo(txtAliqTabICMS, 60, 20, 100, 20,"ALIQTI","Alíquota", ListaCampos.DB_SI, true);
		setListaCampos( false, "TABICMS", "LF");
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		lcCampos.setQueryInsert(false);    
		setImprimir(true);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp)
			imprimir(true);
		else if (evt.getSource() == btImp) 
			imprimir(false);
		super.actionPerformed(evt);
	}
	
	private void imprimir(boolean bVisualizar) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		ImprimeOS imp = null;
		int linPag = 0;

		try {
			
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relatório de Alíquotas");
			imp.limpaPags();
			
			sSQL = "SELECT UFTI,ALIQTI " +
				   "FROM LFTABICMS " +
				   "WHERE CODEMP=? AND CODFILIAL=? " +
				   "ORDER BY UFTI";
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("LFTABICMS"));
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow(), 0, imp.normal());
					imp.say(imp.pRow(), 2, "UF");
					imp.say(imp.pRow(), 20, "Alíquota");
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(),0,Funcoes.replicate("-",79));
				}
				imp.say(imp.pRow() + 1, 0, imp.normal());
				imp.say(imp.pRow(), 2, rs.getString("UFTI"));
				imp.say(imp.pRow(), 20, Funcoes.strDecimalToStrCurrency(9,2,rs.getString("AliqTi")));
				if (imp.pRow()>=linPag) {
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(),0,Funcoes.replicate("-",79));
					imp.incPags();
					imp.eject();
				}
			}
			
			imp.say(imp.pRow() + 1, 0, imp.normal());
			imp.say(imp.pRow(),0,Funcoes.replicate("=",79));
			imp.eject();      
			imp.fechaGravacao();
			  
			if (!con.getAutoCommit())
				con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro na consulta à tabela de icms!\n"+err.getMessage());      
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		    
		if (bVisualizar) 
			imp.preview(this);
		else 
			imp.print();
	}
}
