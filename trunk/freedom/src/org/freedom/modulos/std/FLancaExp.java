/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FLancaExp.java <BR>
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;
public class FLancaExp extends FDados implements ActionListener {
	private JTextFieldPad txtCodLExp = new JTextFieldPad();
	private JTextFieldPad txtCodTipoExp = new JTextFieldPad();
	private JTextFieldFK txtDescTipoExp = new JTextFieldFK();
	private JTextFieldPad txtCodCli = new JTextFieldPad();
	private JTextFieldFK txtDescCli = new JTextFieldFK();
	private JTextFieldPad txtDtRExp = new JTextFieldPad();
	private JTextFieldPad txtDtSExp = new JTextFieldPad();
	private JTextFieldPad txtQtdExp = new JTextFieldPad();
	private ListaCampos lcTipoExp = new ListaCampos(this,"TE");
	private ListaCampos lcCli = new ListaCampos(this,"CL");
	private Connection con = null;
	public FLancaExp() {
		setTitulo("Lançamentos de Expositores");
		setAtribos(50,50,400,200);

		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		
		txtCodTipoExp.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtDescTipoExp.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcTipoExp.add(new GuardaCampo( txtCodTipoExp, 7, 100, 80, 20, "CodTipoExp", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodTipoExpx");
    		lcTipoExp.add(new GuardaCampo( txtDescTipoExp, 90, 100, 207, 20, "DescTipoExp", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescTipoExpx");
		lcTipoExp.montaSql(false, "TIPOEXP", "EQ");   
                lcTipoExp.setQueryCommit(false);
		lcTipoExp.setReadOnly(true);
		txtCodTipoExp.setTabelaExterna(lcTipoExp);
		
		txtCodCli.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtDescCli.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcCli.add(new GuardaCampo( txtCodCli, 7, 100, 80, 20, "CodCli", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodClix");
    		lcCli.add(new GuardaCampo( txtDescCli, 90, 100, 207, 20, "RazCli", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescClix");
                lcCli.setQueryCommit(false);
		lcCli.montaSql(false, "CLIENTE", "VD");    
                lcCli.setQueryCommit(false);
		lcCli.setReadOnly(true);
		txtCodCli.setTabelaExterna(lcCli);
		
		adicCampo(txtCodLExp, 7, 20, 80, 20, "CodLExp", "Código", JTextFieldPad.TP_INTEGER, 8, 0, true, false, null,true);
		adicCampo(txtQtdExp, 90, 20, 77, 20, "QtdLExp", "Quantidade", JTextFieldPad.TP_INTEGER, 8, 0, false, false, null,true);
		adicCampo(txtDtSExp, 170, 20, 97, 20, "DtLExp", "Data Saida", JTextFieldPad.TP_DATE, 10, 0, false, false, null,true);
		adicCampo(txtDtRExp, 270, 20, 100, 20, "DtRetLExp", "Data Retorno", JTextFieldPad.TP_DATE, 10, 0, false, false, null,false);
		adicCampo(txtCodTipoExp, 7, 60, 80, 20, "CodTipoExp", "Código", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescTipoExp,true);
		adicDescFK(txtDescTipoExp, 90, 60, 250, 20, "DescTipoExp", "e crição do tipo de expositor",JTextFieldPad.TP_STRING, 40, 0);
		adicCampo(txtCodCli, 7, 100, 80, 20, "CodCli", "Código", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescCli,true);
		adicDescFK(txtDescCli, 90, 100, 250, 20, "RazCli", "e descrição do cliente", JTextFieldPad.TP_STRING, 40, 0);
		setListaCampos( true, "LANCTOEXP", "EQ");
        lcCampos.setQueryInsert(false);    
	}
	public void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		String sWhere = "";
		imp.setTitulo("Relatório de Lancamentos de Expositores");
 		DLRLancaExp dlle = new DLRLancaExp();
		dlle.setConexao(con);
		dlle.show();
		if (!dlle.OK) {
			dlle.dispose();
			return;
		}
		String sRet[] = dlle.getValores();
		dlle.dispose();
		if (sRet[2].trim().length() > 0) {
			sWhere = " AND CODCLI = "+sRet[2];
		}
		else if (sRet[3].trim().length() > 0) {
			sWhere = " AND CODVEND = "+sRet[3];
		}
		if (sRet[4].trim().length() > 0) {
			sWhere += " AND CODTIPOEXP = "+sRet[4];
		}
		if (sRet[5].equals("R")) {
			sWhere += " AND DTRETLEXP IS NOT NULL";
		}
		else if (sRet[5].equals("E")) {
			sWhere += " AND DTRETLEXP IS NULL";
		}
		String sSQL = "SELECT C.CODVEND,V.NOMEVEND,C.CODCLI,C.RAZCLI,"+
			     "L.DTLEXP,T.DESCTIPOEXP,L.DTRETLEXP FROM "+
			     "VDCLIENTE C, VDVENDEDOR V, EQLANCTOEXP L, EQTIPOEXP T "+
			     "WHERE C.CODCLI = L.CODCLI AND V.CODVEND = "+
			     "C.CODVEND AND T.CODTIPOEXP = L.CODTIPOEXP AND "+
			     "L.DTLEXP BETWEEN ? AND ?"+sWhere;
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setDate(1,Funcoes.strDateToSqlDate(sRet[0]));
			ps.setDate(2,Funcoes.strDateToSqlDate(sRet[1]));
		        ResultSet rs = ps.executeQuery();
		        imp.limpaPags();
	        	while ( rs.next() ) {
				if (imp.pRow()==0) {
					String sTitulo = "RELATORIO LANCAMENTOS DE EXPOSITORES   -   PERIODO DE :"+sRet[0]+" ATE: "+sRet[1];
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|   Emitido em :"+Funcoes.dateToStrDate(new Date()));
					imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|");
					imp.say(imp.pRow()+0,(136-sTitulo.length())/2,sTitulo);
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|Vend");
					imp.say(imp.pRow()+0,7,"|Nome Vendedor");
					imp.say(imp.pRow()+0,38,"|Cod.Cli ");
					imp.say(imp.pRow()+0,49,"|Razão Cliente");
					imp.say(imp.pRow()+0,81,"|Data Saida");
					imp.say(imp.pRow()+0,93,"|Tipo de Expositor");
					imp.say(imp.pRow()+0,125,"|Data Ret. ");
					imp.say(imp.pRow()+0,136,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
				}	
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,0,"|"+rs.getString("CodVend"));
				imp.say(imp.pRow()+0,7,"|"+Funcoes.copy(rs.getString("NomeVend"),0,30));
				imp.say(imp.pRow()+0,38,"|"+rs.getString("CodCli"));
				imp.say(imp.pRow()+0,49,"|"+Funcoes.copy(rs.getString("RazCli"),0,30));
				imp.say(imp.pRow()+0,81,"|"+Funcoes.sqlDateToStrDate(rs.getDate("DtLExp")));
				imp.say(imp.pRow()+0,93,"|"+Funcoes.copy(rs.getString("DescTipoExp"),0,30));
				imp.say(imp.pRow()+0,125,"|"+(rs.getDate("DtRetLExp") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtRetLExp")) : ""));
				imp.say(imp.pRow()+0,136,"|");
				if (imp.pRow()>=linPag) {
					imp.incPags();
					imp.eject();
				}
	        	}
	        	imp.say(imp.pRow()+1,0,""+imp.comprimido());
        		imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
		        imp.eject();
		
        		imp.fechaGravacao();

//		        rs.close();
//        		ps.close();
        		if (!con.getAutoCommit())
        			con.commit();
		}
		catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de setores!"+err.getMessage());
		}
		if (bVisualizar) {
			imp.preview(this);
		}
		else {
			imp.print();
		}
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp) 
			imprimir(true);
		else if (evt.getSource() == btImp) 
			imprimir(false);
		super.actionPerformed(evt);
	}
	public void execShow(Connection cn) {
		con = cn;
		lcCampos.setConexao(cn);
		lcTipoExp.setConexao(cn);
		lcCli.setConexao(cn);
	}
}
