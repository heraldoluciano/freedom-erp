/**
 * @version 02/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPPrefereGeral.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela para cadastro de preferências para o sistema.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class RPPrefereGeral extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final JCheckBoxPad cbIpiComis = new JCheckBoxPad( "Incluir IPI no calculo da comissão?", "S", "N" );
	
	private final JCheckBoxPad cbIPIPed = new JCheckBoxPad( "Imprimir IPI no pedido?", "S", "N" );
	
	private final JCheckBoxPad cbCodBarProd = new JCheckBoxPad( "Usar código de barras no pedido?", "S", "N" );
	
	private final JCheckBoxPad cbEndCliPed = new JCheckBoxPad( "Incluir endereço dos clientes no pedido?", "S", "N" );
	
	private final JCheckBoxPad cbOrdemPed = new JCheckBoxPad( "Ordena pedido por ordem alfabetíca?", "S", "N" );

	private final JTextFieldPad txtServidoSMTP = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtUsuarioSMTP = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );	

	private final JTextFieldPad txtCasasDesc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtCasasDescFin = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );	
	

	public RPPrefereGeral() {

		super( false );
		setTitulo( "Preferências gerais" );		
		setAtribos( 50, 50, 430, 430 );
		
		montaTela();
		setListaCampos( true, "PREFERE1", "SG" );
	}
	
	private void montaTela() {
		
		JLabel vendas = new JLabel( "Vendas", SwingConstants.CENTER );
		vendas.setOpaque( true );
		JLabel linha1 = new JLabel();
		linha1.setBorder( BorderFactory.createEtchedBorder() );
		
		adic( vendas, 27, 0, 80, 20 );
		adic( linha1, 7, 10, 400, 120 );
		
		adicDB( cbIpiComis, 17, 20, 300, 20, "IpiComis", null, true );
		adicDB( cbIPIPed, 17, 40, 300, 20, "IPIPed", null, true );
		adicDB( cbCodBarProd, 17, 60, 300, 20, "CodBarProd", null, true );
		adicDB( cbEndCliPed, 17, 80, 300, 20, "EndCliPed", null, true );
		adicDB( cbOrdemPed, 17, 100, 300, 20, "OrdemPed", null, true );
		
		JLabel email = new JLabel( "E - Mail", SwingConstants.CENTER );
		email.setOpaque( true );
		JLabel linha2 = new JLabel();
		linha2.setBorder( BorderFactory.createEtchedBorder() );
		
		adic( email, 27, 130, 80, 20 );
		adic( linha2, 7, 140, 400, 100 );
		
		adicCampo( txtServidoSMTP, 17, 170, 377, 20, "ServidoSMTP", "Servidor de SMTP", ListaCampos.DB_PK, false );
		adicCampo( txtUsuarioSMTP, 17, 210, 377, 20, "UsuarioSMTP", "Id do usuario", ListaCampos.DB_SI, false );
		
		JLabel campos = new JLabel( "Campos", SwingConstants.CENTER );
		campos.setOpaque( true );
		JLabel linha3 = new JLabel();
		linha3.setBorder( BorderFactory.createEtchedBorder() );
		
		adic( campos, 27, 240, 80, 20 );
		adic( linha3, 7, 250, 400, 100 );
		
		adicCampo( txtCasasDesc, 17, 280, 160, 20, "CasasDesc", "Casas decimais", ListaCampos.DB_PK, false );
		adicCampo( txtCasasDescFin, 17, 320, 160, 20, "CasasDescFin", "Casas decimais ( financeiro )", ListaCampos.DB_SI, false );
		
	}
}
