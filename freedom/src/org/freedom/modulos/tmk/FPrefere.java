/**
 * @version 10/10/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)FPrefere.java <BR>
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


package org.freedom.modulos.tmk;
import java.sql.Connection;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FTabDados;

public class FPrefere extends FTabDados {
	private JPanelPad pinMail = new JPanelPad();
	private JPanelPad pinSmtp = new JPanelPad();
	private JTextFieldPad txtSmtpMail = new JTextFieldPad(JTextFieldPad.TP_STRING, 40 , 0);
	private JTextFieldPad txtUserMail = new JTextFieldPad(JTextFieldPad.TP_STRING, 40 , 0);
	private JPasswordFieldPad txpPassMail = new JPasswordFieldPad(16);
	public FPrefere() {
		setTitulo("Preferências do Telemarketing");
		setAtribos(50, 50, 355, 375);
		
		setPainel(pinMail);
		adicTab("Mail", pinMail);
		JLabelPad lbServer = new JLabelPad("  Servidor para envio de email");
		lbServer.setOpaque(true);
		adic(lbServer,15,10,200,15);
		adic(pinSmtp,10,15,220,160);
		setPainel(pinSmtp);
		adicCampo(txtSmtpMail,10,30,150,20,"SmtpMail","SMTP", ListaCampos.DB_SI, false);
		adicCampo(txtUserMail,10,70,150,20,"UserMail","Usuario", ListaCampos.DB_SI,false);
		adicCampo(txpPassMail,10,110,150,20,"PassMail","Senha",ListaCampos.DB_SI,false);
		setListaCampos(false, "PREFERE3", "SG");
		
		nav.setAtivo(0,false);
		nav.setAtivo(1,false);
	}
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcCampos.carregaDados();
	}
}