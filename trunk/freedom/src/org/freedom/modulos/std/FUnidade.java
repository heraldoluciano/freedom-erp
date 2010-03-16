/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FUnidade.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class FUnidade extends FDados implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodUnidade = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
	
	private JTextFieldPad txtDescUnidade = new JTextFieldPad(JTextFieldPad.TP_STRING,60,0);
	
	private JTextFieldPad txtCasasDec = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	
	public FUnidade () {
		super();
		setTitulo("Cadastro de Unidades");
		setAtribos( 50, 50, 450, 160);
		adicCampo(txtCodUnidade, 7, 20, 110, 20,"CodUnid","Cód.unid.",ListaCampos.DB_PK, true);
		adicCampo(txtDescUnidade, 120, 20, 300, 20,"DescUnid","Descrição da unidade",ListaCampos.DB_SI, true);
		adicCampo(txtCasasDec, 7, 60, 110, 20,"CasasDec","Casas decimais",ListaCampos.DB_SI, true);
		setListaCampos( true, "UNIDADE", "EQ");
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
		DLRUnidade dl = null;
		ImprimeOS imp = null;
		int linPag = 0;
		
		dl = new DLRUnidade();
		dl.setVisible(true);

		if (dl.OK == false) {
			dl.dispose();
			return;
		}

		try {
			
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relatório de Unidades");
			imp.limpaPags();
			
			sSQL = "SELECT CODUNID,DESCUNID " 
				+  "FROM EQUNIDADE " 
				+  "WHERE CODEMP=? AND CODFILIAL=? " 
				+  "ORDER BY "+ dl.getValor();
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("EQUNIDADE"));
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow(), 0, imp.normal());
					imp.say(imp.pRow(), 2, "Cód.unid.");
					imp.say(imp.pRow(), 30, "Descrição");
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(), 0, StringFunctions.replicate("-",79));
				}
				imp.say(imp.pRow() + 1, 0, imp.normal());
				imp.say(imp.pRow(), 2, rs.getString("Codunid"));
				imp.say(imp.pRow(), 30, rs.getString("Descunid"));
				if (imp.pRow()>=linPag) {
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(), 0, StringFunctions.replicate("-",79));
					imp.incPags();
					imp.eject();
				}
			}
			  
			imp.say(imp.pRow() + 1, 0, imp.normal());
			imp.say(imp.pRow(), 0, StringFunctions.replicate("=",79));
			imp.eject();      
			imp.fechaGravacao();
			  
			con.commit();
			dl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro na consulta à tabela de UNIDADES!\n"+err.getMessage(),true,con,err);
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
