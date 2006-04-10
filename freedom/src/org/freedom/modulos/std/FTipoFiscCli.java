/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTipoFiscCli.java <BR>
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
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
public class FTipoFiscCli extends FDados implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCod= new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDesc= new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
	
	public FTipoFiscCli () {
		super();
		setTitulo("Cadastro de tipos fiscal de clientes");
		setAtribos(50, 50, 360, 125);
		adicCampo(txtCod, 7, 20, 80, 20,"CodFiscCli","Cód.fisc.cli.", ListaCampos.DB_PK, true);
		adicCampo(txtDesc, 90, 20, 250, 20,"DescFiscCli","Descrição fiscal do cliente",ListaCampos.DB_SI, true);
		setListaCampos( true, "TIPOFISCCLI", "LF");
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
		DLRTipoFiscCli dl = null;
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = 0;
		int iTot = 0;

		dl = new DLRTipoFiscCli();
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}

		try {
			
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relatório de Tipos Fiscal de Cliente");dl = new DLRTipoFiscCli();
			imp.limpaPags();
			
			sSQL = "SELECT TP.CODFISCCLI,TP.DESCFISCCLI, " +
				   "(SELECT COUNT(CLI.CODCLI) FROM VDCLIENTE CLI " +
				   							 "WHERE CLI.CODEMPFC=TP.CODEMP AND CLI.CODFILIALFC=TP.CODFILIAL " +
				   							 "AND CLI.CODFISCCLI = TP.CODFISCCLI) "+
				   "FROM LFTIPOFISCCLI TP " +
				   "WHERE TP.CODEMP=? AND TP.CODFILIAL=? " + 
				   "ORDER BY TP." + dl.getValor();
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("LFTIPOFISCCLI"));
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow(), 0, "" + imp.normal());
					imp.say(imp.pRow(), 0, "");
					imp.say(imp.pRow(), 2, "Cód.fisc.cli.");
					imp.say(imp.pRow(), 20, "Descrição");
					imp.say(imp.pRow(), 70, "Qtd.cli.");
					imp.say(imp.pRow() + 1, 0, "" + imp.normal());
					imp.say(imp.pRow(), 0, Funcoes.replicate("-",79));
				}
				imp.say(imp.pRow() + 1, 0, "" + imp.normal());
				imp.say(imp.pRow(), 2, rs.getString("CodFiscCli"));
				imp.say(imp.pRow(), 20, rs.getString("DescFiscCli"));
				imp.say(imp.pRow(), 70, Funcoes.alinhaDir(rs.getInt(3),8));
				iTot += rs.getInt(3);
				if (imp.pRow()>=linPag) {
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow(), 0, Funcoes.replicate("-", 79));
					imp.incPags();
					imp.eject();
				}
			}
			  
			imp.say(imp.pRow() + 1, 0, "" + imp.normal());
			imp.say(imp.pRow(), 0, Funcoes.replicate("=",79));
			imp.say(imp.pRow() + 1, 0, "" + imp.normal());
			imp.say(imp.pRow(), 0, "|");
			imp.say(imp.pRow(), 50, "Total de clientes:");
			imp.say(imp.pRow(), 70, Funcoes.alinhaDir(iTot,8));
			imp.say(imp.pRow(), 80, "|");
			imp.say(imp.pRow() + 1, 0, "" + imp.normal());
			imp.say(imp.pRow(), 0, Funcoes.replicate("=",79));
			imp.eject();  
			imp.fechaGravacao();
			
			if (!con.getAutoCommit())
				con.commit();
			dl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela fiscal de tipos de cliente!"+err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			dl = null;
		}
		    
		if (bVisualizar)
			imp.preview(this);
		else
			imp.print();
	}
}
