/**
 * @version 28/01/2005 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe: @(#)FArea.java <BR>
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
 * Formulário para cadastro das áreas de conhecimento para uso nas funções de recrutamento e seleção.
 * 
 */

package org.freedom.modulos.grh;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class FArea extends FDados implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCodArea = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDescArea = new JTextFieldPad(JTextFieldPad.TP_STRING,60,0);
	
	public FArea () {
		super();
		setTitulo("Cadastro de Areas");
		setAtribos( 50, 50, 380, 135);
		adicCampo(txtCodArea, 7, 20, 70, 20,"CodArea","Cód.area", ListaCampos.DB_PK, true);
		adicCampo(txtDescArea, 80, 20, 250, 20,"DescArea","Descrição da area", ListaCampos.DB_SI, true);
		setListaCampos( true, "AREA", "RH");
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
	
	private void imprimir(boolean bVisualizar) {}
}
