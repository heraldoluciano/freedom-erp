/**
 * @version 11/01/2010 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FSetorAtend.java <BR>
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
 * Tela para cadastro de tipos de recebimento de mercadorias.
 * 
 */

package org.freedom.modulos.gms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.FDetalhe;

public class FTipoRecMerc extends FDetalhe implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public static String PESAGEM_INICIAL = "PI";
	
	public static String DESCARREGAMENTO = "TR";
	
	public static String PESAGEM_FINAL = "PF";
	
	private JTextFieldPad txtCodTipoRecMerc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);

	private JTextFieldPad txtDescTipoRecMerc = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);

	private JTextFieldPad txtCodProcRecMerc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);

	private JTextFieldPad txtDescProcRecMerc = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
	
	private JTextFieldPad txtNroAmostProcRecMerc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,2,0);

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();
	
	private JComboBoxPad cbTipoProcRecMerc = null;
	
	private Vector<String> vValsTipo = new Vector<String>();

	private Vector<String> vLabsTipo = new Vector<String>();


	public FTipoRecMerc () {
		
		super();

		setTitulo("Cadastro de tipos de recepção de mercadorias");
		setAtribos( 50, 50, 555, 350);

		setAltCab(90);
		
		pinCab = new JPanelPad(420,90);
		
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);

		vLabsTipo.addElement( "Pesagem inicial" );
		vLabsTipo.addElement( "Descarregamento" );
		vLabsTipo.addElement( "Pesagem final" );
		
		vValsTipo.addElement( PESAGEM_INICIAL );
		vValsTipo.addElement( DESCARREGAMENTO );
		vValsTipo.addElement( PESAGEM_FINAL );
		
		cbTipoProcRecMerc = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_STRING, 2, 0 );
		
		adicCampo(txtCodTipoRecMerc, 7, 20, 70, 20,"CodTipoRecMerc","Cód.Tp.Rec.",ListaCampos.DB_PK,true);
		adicCampo(txtDescTipoRecMerc, 80, 20, 440, 20,"DescTipoRecMerc","Descrição do tipo recepção de mercadorias",ListaCampos.DB_SI,true);
		
		setListaCampos( true, "TIPORECMERC", "EQ");

		setAltDet(60);
		setPainel( pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);

		adicCampo( txtCodProcRecMerc, 7, 20, 40, 20, "CodProcRecMerc","Cód.Et.",ListaCampos.DB_PK, true );
		adicCampo( txtDescProcRecMerc, 50, 20, 240, 20, "DescProcRecMerc","Descrição do processo de recepção", ListaCampos.DB_SI, true );		
		adicCampo( txtNroAmostProcRecMerc, 293, 20, 85, 20, "NroAmostProcRecMerc","Nro.Amostras", ListaCampos.DB_SI, true );
		adicDB( cbTipoProcRecMerc, 381, 20, 139, 24, "TipoProcRecMerc","Tipo de processo", true );
		
		setListaCampos( true, "PROCRECMERC", "EQ");

		montaTab();    
		
		tab.setTamColuna(50,0); 
		tab.setTamColuna(264,1);
		tab.setTamColuna(100,2);
		tab.setTamColuna(110,3);

		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);  
		setImprimir(true);

	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp) {
			imprimir(true);
		}
		else if (evt.getSource() == btImp) 
			imprimir(false);
		super.actionPerformed(evt);
	}

	public void setConexao(DbConnection cn) {
		super.setConexao(cn);

	}

	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		imp.montaCab();
		imp.setTitulo("Relatório de tipo de recebimento de mercadorias");

		String sSQL = "SELECT CODTIPORECMERC,DESCTIPORECMERC FROM EQTIPORECMERC ORDER BY 1";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sSQL);
			rs = ps.executeQuery();
			imp.limpaPags();
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow()+0,0,""+imp.normal());
					imp.say(imp.pRow()+0,0,"");
					imp.say(imp.pRow()+0,2,"Código");
					imp.say(imp.pRow()+0,25,"Descrição");
					imp.say(imp.pRow()+1,0,""+imp.normal());
					imp.say(imp.pRow()+0,0,Funcoes.replicate("-",79));
				}
				imp.say(imp.pRow()+1,0,""+imp.normal());
				imp.say(imp.pRow()+0,2,rs.getString("CodTipoRecMerc"));
				imp.say(imp.pRow()+0,25,rs.getString("DescTipoRecMerc"));
				if (imp.pRow()>=linPag) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say(imp.pRow()+1,0,""+imp.normal());
			imp.say(imp.pRow()+0,0,Funcoes.replicate("=",79));
			imp.eject();

			imp.fechaGravacao();

			//      rs.close();
			//      ps.close();
			
			con.commit();
			
		}  
		catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de tipos de recebimento de mercadorias!\n"+err.getMessage(),true,con,err);      
		}

		if (bVisualizar) {
			imp.preview(this);
		}
		else {
			imp.print();
		}
	}
}
