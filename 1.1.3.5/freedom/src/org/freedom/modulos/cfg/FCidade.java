/**
 * @version 25/06/2004 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe:
 * @(#)FCidade.java <BR>
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
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.cfg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;

public class FCidade extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCidade = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNomeCidade = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSiglaCidade = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDDDCidade = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	public FCidade() {

		super();
		setTitulo( "Cadastro de Cidades" );
		setAtribos( 50, 50, 410, 125 );

		lcCampos.setUsaME( false );

		adicCampo( txtCodCidade, 7, 20, 70, 20, "CodCid", "Cód.cid.", ListaCampos.DB_PK, true );
		adicCampo( txtNomeCidade, 80, 20, 177, 20, "NomeCid", "Nome da cidade", ListaCampos.DB_SI, true );
		adicCampo( txtSiglaCidade, 260, 20, 77, 20, "SiglaCid", "Sigla", ListaCampos.DB_SI, true );
		adicCampo( txtDDDCidade, 340, 20, 40, 20, "DDDCid", "DDD", ListaCampos.DB_SI, false );
		setListaCampos( true, "CIDADE", "SG" );
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		lcCampos.setQueryInsert( false );
		
		setImprimir( true );
		
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		
		super.actionPerformed( evt );
		
	}

	private void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		ImprimeOS imp = new ImprimeOS( "", con );	
		int linPag = imp.verifLinPag() - 1;	
		
		try {
		
			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relatório de Cidades" );
			
			String sSQL = "SELECT CODCID,NOMECID,SIGLACID,DDDCID FROM SGCIDADE ORDER BY NOMECID";
			
			ps = con.prepareStatement( sSQL );
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				
				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
				
				if ( imp.pRow() == 0 ) {
					
					imp.impCab( 136, false );
					
					imp.say( 0, imp.normal() );					
					imp.say( 2, "Cód.cid." );
					imp.say( 15, "Nome" );
					imp.say( 80, "Sigla" );
					imp.say( 100, "DDD" );
					
					imp.pulaLinha( 1, imp.normal() );
					imp.say( 0, Funcoes.replicate( "-", 135 ) );
					
				}
				
				imp.pulaLinha( 1, imp.normal() );
				imp.say( 2, rs.getString( "CODCID" ) != null ? rs.getString( "CODCID" ) : ""  );
				imp.say( 15, rs.getString( "NOMECID" ) != null ? rs.getString( "NOMECID" ) : ""  );
				imp.say( 80, rs.getString( "DDDCID" ) != null ? rs.getString( "SIGLACID" ) : ""  );
				imp.say( 100, rs.getString( "SIGLACID" ) != null ? rs.getString( "DDDCID" ) : "" );
				
				
			}

			imp.pulaLinha( 1, imp.normal() );
			imp.say( imp.pRow() + 0, 0, Funcoes.replicate( "=", 135 ) );
			
			imp.eject();
			imp.fechaGravacao();

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta tabela de cidades!" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
		
	}
	
}
