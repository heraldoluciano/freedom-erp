/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FLogin.java <BR>
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
 * Comentários para a classe...
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;

public class FAtalhos extends FFDialogo {
  private JPanelPad pnEquipe = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  public FAtalhos () {
  	super(Aplicativo.telaPrincipal);
    setTitulo("Atalhos");
	setAtribos(380,580);
	
	setToFrameLayout();

	c.add(pnEquipe);
	pnEquipe.add(new JLabelPad ("<HTML><BODY>" +
			"<UL>"+
        "<LI><STRONG>CTRL + N</STRONG> - <EM>Novo Registro</EM>"+
        "<LI><STRONG>CTRL + S</STRONG> - <EM>Gravar Alterações</EM>"+
        "<LI><STRONG>CTRL + D</STRONG> - <EM>Apagar Registro</EM>"+
        "<LI><STRONG>CTRL + E</STRONG> - <EM>Editar</EM>"+
        "<LI><STRONG>CTRL + W</STRONG> - <EM>Cancelar Alterações</EM>"+
        "<LI><STRONG>CTRL + P</STRONG> - <EM>Imprimir Registro</EM>"+
        "<LI><STRONG>CTRL + R</STRONG> - <EM>Visualizar Impressão</EM>"+
        "<LI><STRONG>CTRL + O</STRONG> - <EM>Observações</EM>" +
        "<LI><STRONG>CTRL + I</STRONG> - <EM>Imprimir</EM>" +
        "<LI><STRONG>CTRL + P</STRONG> - <EM>Previsão de Impressão</EM>" +
        "<LI><STRONG>TAB</STRONG> - <EM>Vai pata o próximo campo</EM>"+
        "<LI><STRONG>SHIFT + TAB</STRONG> - <EM>Volta para o campo anterior</EM>"+
        "<LI><STRONG>SHIFT + F4</STRONG> - <EM>Fecha a Tela</EM>"+
        "<LI><STRONG>BARRA DE ESPAÇOS</STRONG> - <EM>Aperta um botão</EM>"+
        "<LI><STRONG>CTRL + PAGE UP</STRONG> - <EM>Vai para o Primeiro Registro</EM>" +
        "<LI><STRONG>PAGE UP</STRONG> - <EM>Vai para o Registro Anterior</EM>" +
        "<LI><STRONG>PAGE DOWN</STRONG> - <EM>Vai para o Próximo Registro</EM>" +
        "<LI><STRONG>CTRL + PAGE DOWN</STRONG> - <EM>vai para o Último Registro</EM>" +
        "<LI><STRONG>F1</STRONG> - <EM>Atalhos</EM>" +
        "<LI><STRONG>F2</STRONG> - <EM>Procurar</EM>" +
        "<LI><STRONG>F3</STRONG> - <EM>Procurar similar</EM>" +
        "<LI><STRONG>F4</STRONG> - <EM>Completar o Orçamento</EM>" +
        "<LI><STRONG>F4</STRONG> - <EM>Fechar a Compra</EM>" +
		"<LI><STRONG>F4</STRONG> - <EM>Fechar a Venda</EM>" +
		"<LI><STRONG>F5</STRONG> - <EM>Consulta pagamentos</EM>" +
        "<LI><STRONG>ESC</STRONG> - <EM>Sair da Tela</EM>" +
        "</UL>" +
        "</BODY>" +
        "</HTML>"));
  }
}    
