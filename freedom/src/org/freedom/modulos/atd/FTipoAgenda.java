/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe: @(#)FTipoAgenda.java <BR>
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
 * Tela de cadastro de tipos de agendamento
 * 
 */

package org.freedom.modulos.atd; 

import java.awt.BorderLayout;
import java.sql.Connection;

import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.telas.FDialogo;

public class FTipoAgenda extends FDialogo {
	
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCodTipoAGD= new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDescTipoAGD= new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
	public ListaCampos lcCampos = new ListaCampos(null); 
	public Navegador nav = new Navegador(false); 
	  
	public FTipoAgenda () {
		  
		super();
		setTitulo("Tipos de Agendamentos");
		setAtribos( 345, 125 );
		setToFrameLayout();
		
		pnRodape.add(nav, BorderLayout.WEST);
		nav.setListaCampos(lcCampos);
		lcCampos.setNavegador(nav);
		
		lcCampos.add(new GuardaCampo( txtCodTipoAGD, "CodTipoAGD", "Cód.tp.agd.", ListaCampos.DB_PK, true));
		lcCampos.add(new GuardaCampo( txtDescTipoAGD, "DescTipoAGD", "Descrição do tipo de agendamento", ListaCampos.DB_SI,true));
		lcCampos.montaSql(true, "TIPOAGENDA", "SG");    
		lcCampos.setReadOnly(false);
		txtCodTipoAGD.setTabelaExterna(lcCampos);
		txtCodTipoAGD.setFK(true);
		txtCodTipoAGD.setNomeCampo("CodTipoAGD");
		
		adic(new JLabel("Cód.tp.agd."), 7, 10, 70, 20);
		adic(txtCodTipoAGD, 7, 30, 70, 20);
		adic(new JLabel("Descrição do tipo de agendamento"), 80, 10, 240, 20);
		adic(txtDescTipoAGD, 80, 30, 240, 20);
		    
		lcCampos.setQueryInsert(false);
	    
	}
	
	public void setConexao(Connection con) {
		//super.setConexao(con);
		lcCampos.setConexao(con);
	}
  
}
