package org.freedom.layout.telas;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDialogo;


public class DLConveniadoPDV extends FDialogo {
	
	private static final long serialVersionUID = 1;
	
	private JTextFieldPad txtCodBarras = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 15, 0 );
	
	private JTextFieldPad txtMatricula = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtNome = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCateg = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCred = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtHorario = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtSemNome = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtDisp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtPreco = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtQtdMinDesc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtPercDesc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtPrecoDesc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtQtd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtTotal = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextAreaPad txtMenssage = new JTextAreaPad( 300 );

	public DLConveniadoPDV() {

		super();
		
		setTitulo( "Conveniado" );
		setAtribos( 383, 400 );		
		
		montaTela();
		
	}
	
	private void montaTela() {
		
		adic( new JLabel( "Cód.barras" ), 7, 10, 200, 20 );
		adic( txtCodBarras, 7, 30, 230, 20 );
		adic( new JLabel( "Matricula" ), 240, 10, 100, 20 );
		adic( txtMatricula, 240, 30, 120, 20 );
		
		JLabel linha = new JLabel();
		linha.setBorder( BorderFactory.createEtchedBorder() );
		
		adic( linha, 7, 65, 353, 2 );		

		adic( new JLabel( "Nome" ), 7, 80, 300, 20 );
		adic( txtNome, 7, 100, 300, 20 );
		adic( new JLabel( "Categ." ), 310, 80, 50, 20 );
		adic( txtCateg, 310, 100, 50, 20 );
		
		adic( new JLabel( "Menssagem" ), 7, 120, 100, 20 );
		adic( new JScrollPane( txtMenssage ), 7, 140, 352, 40 );

		adic( new JLabel( "Creditos" ), 7, 180, 85, 20 );
		adic( txtCred, 7, 200, 85, 20 );
		adic( new JLabel( "Horario" ), 95, 180, 85, 20 );
		adic( txtHorario, 95, 200, 85, 20 );
		adic( new JLabel( "" ), 183, 180, 85, 20 );
		adic( txtSemNome, 183, 200, 85, 20 );
		adic( new JLabel( "Disponiveis" ), 271, 180, 85, 20 );
		adic( txtDisp, 271, 200, 86, 20 );
		
		adic( new JLabel( "Preço" ), 7, 220, 85, 20 );
		adic( txtPreco, 7, 240, 85, 20 );
		adic( new JLabel( "Qtd.min.desc." ), 95, 220, 85, 20 );
		adic( txtQtdMinDesc, 95, 240, 85, 20 );
		adic( new JLabel( "% Desconto" ), 183, 220, 85, 20 );
		adic( txtPercDesc, 183, 240, 85, 20 );
		adic( new JLabel( "Preço c/ desc." ), 271, 220, 85, 20 );
		adic( txtPrecoDesc, 271, 240, 86, 20 );		

		adic( new JLabel( "Quantidade" ), 7, 270, 120, 20 );
		adic( txtQtd, 7, 290, 120, 20 );
		adic( new JLabel( "Total" ), 130, 270, 120, 20 );
		adic( txtTotal, 130, 290, 120, 20 );
		
		txtMatricula.setAtivo( false );
		txtNome.setAtivo( false );
		txtCateg.setAtivo( false );
		txtMenssage.setEditable( false );
		txtCred.setAtivo( false );
		txtHorario.setAtivo( false );
		txtSemNome.setAtivo( false );
		txtDisp.setAtivo( false );
		txtPreco.setAtivo( false );
		txtQtdMinDesc.setAtivo( false );
		txtPercDesc.setAtivo( false );
		txtPrecoDesc.setAtivo( false );
		txtTotal.setAtivo( false );
		
	}
	
	private void montaICard( final boolean sca ) {
		
		F2ICard icard = new F2ICard();
		
		icard.setVisible( true );
		
		String sICard = null;
		
		/*GetICard(bSca: Boolean; docMatric: TDOCSESC): String;
begin
 if bSca then
    Result := Copy(strZero(docMatric.VIA,4),4,1)+strZero(docMatric.CDUOP,4)+StrZero(docMatric.MATRIC,6)+IntToStr(docMatric.MATRICS)
 else
    Result := '0'+docMatric.UUCC+StrZero(docMatric.MATRIC,7)+StrZero(docMatric.MATRICS,2);
end;
 TDOCSESC = record
   VIA: Integer;
   CDUOP: Integer;
   CAT: String[1];
   UUCC: String[2];
   UUEE: String[2];
   MATRIC: Integer;
   MATRICS: Integer;
   DV: String[2];
   VALIDO: Boolean;
 end;
Enviado às 21:07 de Terça-feira
 
Pressione a tecla Enter para enviar sua mensagem.
*/
		
		if ( sca ) {
			
			int via = getVia();
			
			sICard = Funcoes.strZero( String.valueOf( via ), 4 ) + 
					 Funcoes.strZero( icard.getUUCC(), 4 ) + 
					 Funcoes.strZero( icard.getMatricula(), 6 ) + 
					 icard.getDep();
			
		}
		else {
			
			sICard = "0" +
					 icard.getUUCC() + 
					 Funcoes.strZero( icard.getMatricula(), 7 ) +
					 Funcoes.strZero( icard.getDep(), 2 );
			
		}
				
		txtCodBarras.setVlrString( sICard );
		
	}
	
	private int getVia() {
		return 1;
	}
	
	public String getIcard() {
		
		return txtCodBarras.getVlrString().trim();		
	}
	
	public void keyPressed( KeyEvent e ) {
		
		if ( e.getKeyCode() == KeyEvent.VK_F2 ) {
			
			montaICard( false );
			
		}

		super.keyPressed( e );

	}
	
	public void actionPerformed(ActionEvent evt) {
		System.exit(0);
	}
	
	private class F2ICard extends FDialogo {
		
		private static final long serialVersionUID = 1;
		
		private JTextFieldPad txtUUCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );
		
		private JTextFieldPad txtMatricula = new JTextFieldPad( JTextFieldPad.TP_STRING, 7, 0 );
		
		private JTextFieldPad txtDep = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );
		
		public F2ICard() {
			
			super();
			
			setAtribos( 255, 120 );
			setTitle( "Matricula" );
			
			montaTela();
			
		}
		
		private void montaTela() {
			
			adic( new JLabel( "UUCC" ), 7, 0, 50, 20 );
			adic( txtUUCC, 7, 20, 50, 20 );
			adic( new JLabel( "Matricula" ), 60, 0, 120, 20 );
			adic( txtMatricula, 60, 20, 120, 20 );
			adic( new JLabel( "Dep" ), 183, 0, 50, 20 );
			adic( txtDep, 183, 20, 50, 20 );
			
		}
		
		public String getUUCC() {
			
			return txtUUCC.getVlrString().trim();
		}
		
		public String getMatricula() {
			
			return txtMatricula.getVlrString().trim();
		}
		
		public String getDep() {
			
			return txtDep.getVlrString().trim();
		}
		
	}
	
	public static void main( String[] args ) {
		
		new DLConveniadoPDV().setVisible( true );
		
	}

}
