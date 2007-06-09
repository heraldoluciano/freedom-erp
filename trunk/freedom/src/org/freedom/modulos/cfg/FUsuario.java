/**
 * @version 30/05/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe:
 * @(#)FUsuario.java <BR>
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
 * Formulário de cadastro de usuários do sistema.
 * 
 */

package org.freedom.modulos.cfg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;

public class FUsuario extends FTabDados implements PostListener, DeleteListener, InsertListener, CarregaListener, ActionListener, CheckBoxListener {

	private static final long serialVersionUID = 1L;

	private static Vector vCodDisp = new Vector();

	private static Vector vCodEmp = new Vector();

	private static Vector vDisp = new Vector();

	private static Vector vEmp = new Vector();

	private JButton btAdicEmp = new JButton( Icone.novo( "btFlechaDir.gif" ) );

	private JButton btDelEmp = new JButton( Icone.novo( "btFlechaEsq.gif" ) );

	private JCheckBoxPad cbAbreGaveta = new JCheckBoxPad( "Permite abrir gaveta no PDV", "S", "N" );

	private JCheckBoxPad cbAlmoxarife = new JCheckBoxPad( "Permite atuar como Almoxarife", "S", "N" );

	private JCheckBoxPad cbAltParc = new JCheckBoxPad( "Permite alterar parcelas da venda", "S", "N" );

	private JCheckBoxPad cbBaixoCusto = new JCheckBoxPad( "Permite vendas abaixo do custo", "S", "N" );

	private JCheckBoxPad cbCompra = new JCheckBoxPad( "Permite fazer compras", "S", "N" );

	private JCheckBoxPad cbReceita = new JCheckBoxPad( "Permite vendas de produto com receita", "S", "N" );

	private JList lsDisp = new JList();

	private JList lsEmp = new JList();

	private JPanelPad pinAcesso = new JPanelPad();

	private JPanelPad pinGeral = new JPanelPad();

	private JRadioGroup rgAprovaRMA = null;

	private JRadioGroup rgAprovaSolicitacao = null;

	private JTextAreaPad txaComentUsu = new JTextAreaPad();

	private JScrollPane spnDisp = new JScrollPane( lsDisp );

	private JScrollPane spnEmp = new JScrollPane( lsEmp );

	private JScrollPane spnObs = new JScrollPane( txaComentUsu );

	private JPasswordFieldPad txpConfirma = new JPasswordFieldPad( 8 );

	private JPasswordFieldPad txpSenha = new JPasswordFieldPad( 8 );

	private JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtCodAlmox = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private JTextFieldFK txtDescAlmox = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtIDGrpUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtIDUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtNomeUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtPNomeUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtUNomeUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private Connection conIB = null;

	private ListaCampos lcAlmox = new ListaCampos( this, "AM" );

	private ListaCampos lcCC = new ListaCampos( this, "CC" );

	private ListaCampos lcGrup = new ListaCampos( this, "IG" );

	private Vector vAprovaRMALab = new Vector();

	private Vector vAprovaRMAVal = new Vector();

	private Vector vAprovaSolicitacaoLab = new Vector();

	private Vector vAprovaSolicitacaoVal = new Vector();

	public FUsuario() {

		super();
		setTitulo( "Cadastro de Usuários" );
		setAtribos( 50, 50, 470, 480 );

		vAprovaSolicitacaoLab.add( "Nenhuma" );
		vAprovaSolicitacaoLab.add( "Mesmo Centro de Custo" );
		vAprovaSolicitacaoLab.add( "Todas" );

		vAprovaSolicitacaoVal.add( "ND" );
		vAprovaSolicitacaoVal.add( "CC" );
		vAprovaSolicitacaoVal.add( "TD" );

		vAprovaRMALab.add( "Nenhuma" );
		vAprovaRMALab.add( "Mesmo Centro de Custo" );
		vAprovaRMALab.add( "Todas" );

		vAprovaRMAVal.add( "ND" );
		vAprovaRMAVal.add( "CC" );
		vAprovaRMAVal.add( "TD" );

		rgAprovaSolicitacao = new JRadioGroup( 3, 1, vAprovaSolicitacaoLab, vAprovaSolicitacaoVal );
		rgAprovaRMA = new JRadioGroup( 3, 1, vAprovaRMALab, vAprovaRMAVal );

		txpSenha.setListaCampos( lcCampos );
		txpConfirma.setListaCampos( lcCampos );

		lcGrup.add( new GuardaCampo( txtIDGrpUsu, "IDGRPUSU", "ID grupo", ListaCampos.DB_PK, false ) );
		lcGrup.add( new GuardaCampo( txtDescGrup, "NOMEGRPUSU", "Descriçao do grupo", ListaCampos.DB_SI, false ) );
		lcGrup.montaSql( false, "GRPUSU", "SG" );
		lcGrup.setQueryCommit( false );
		lcGrup.setReadOnly( true );
		txtIDGrpUsu.setTabelaExterna( lcGrup );

		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "Cód.c.c.", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtAnoCC, "AnoCC", "Ano.c.c.", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtDescCC, "DescCC", "Descriçao do centro de custo", ListaCampos.DB_SI, false ) );
		lcCC.montaSql( false, "CC", "FN" );
		lcCC.setQueryCommit( false );
		lcCC.setReadOnly( true );

		txtAnoCC.setTabelaExterna( lcCC );
		txtAnoCC.setNomeCampo( "anocc" );
		txtCodCC.setTabelaExterna( lcCC );
		txtCodCC.setNomeCampo( "codcc" );

		lcAlmox.add( new GuardaCampo( txtCodAlmox, "CodAlmox", "Cod.almox.", ListaCampos.DB_PK, false ) );
		lcAlmox.add( new GuardaCampo( txtDescAlmox, "DescAlmox", "Descrição do almoxarifado", ListaCampos.DB_SI, false ) );
		lcAlmox.montaSql( false, "ALMOX", "EQ" );
		lcAlmox.setQueryCommit( false );
		lcAlmox.setReadOnly( true );
		txtCodAlmox.setTabelaExterna( lcAlmox );

		adicTab( "Usuario", pinGeral );
		setPainel( pinGeral );

		adicCampo( txtIDUsu, 7, 20, 80, 20, "IDUsu", "ID.usu.", ListaCampos.DB_PK, true );
		adicCampo( txtNomeUsu, 90, 20, 350, 20, "NomeUsu", "Nome do usuário", ListaCampos.DB_SI, true );
		adicCampo( txtPNomeUsu, 7, 60, 180, 20, "PNomeUsu", "Primeiro nome", ListaCampos.DB_SI, true );
		adicCampo( txtUNomeUsu, 190, 60, 250, 20, "UNomeUsu", "Último nome", ListaCampos.DB_SI, true );
		adicCampo( txtIDGrpUsu, 7, 100, 70, 20, "IDGRPUSU", "ID.grupo", ListaCampos.DB_FK, false );
		adicDescFK( txtDescGrup, 80, 100, 216, 20, "NOMEGRPUSU", "Descrição do grupo do usuário" );
		adic( new JLabelPad( "Senha" ), 300, 80, 70, 20 );
		adic( txpSenha, 300, 100, 70, 20 );
		adic( new JLabelPad( "Confirma" ), 373, 80, 70, 20 );
		adic( txpConfirma, 373, 100, 70, 20 );
		adicCampo( txtAnoCC, 7, 140, 50, 20, "AnoCC", "Ano c.c.", ListaCampos.DB_FK, txtDescCC, false );
		adicCampo( txtCodCC, 60, 140, 110, 20, "CodCC", "Cód.c.c.", ListaCampos.DB_FK, txtDescCC, false );
		adicDescFK( txtDescCC, 173, 140, 269, 20, "DescCC", "Descrição do centro de custo" );

		adicDBLiv( txaComentUsu, "ComentUsu", "Comentário", false );
		adic( new JLabelPad( "Comentário" ), 7, 160, 100, 20 );
		adic( spnObs, 7, 180, 435, 60 );
		adic( new JLabelPad( "Filiais disponíveis:" ), 7, 240, 120, 20 );
		adic( spnDisp, 7, 260, 195, 100 );
		adic( btAdicEmp, 210, 275, 30, 30 );
		adic( btDelEmp, 210, 315, 30, 30 );
		adic( new JLabelPad( "Acesso:" ), 247, 240, 158, 20 );
		adic( spnEmp, 247, 260, 195, 100 );

		adicTab( "Acesso", pinAcesso );
		setPainel( pinAcesso );

		adicDB( cbBaixoCusto, 7, 10, 350, 20, "BaixoCustoUsu", "", false );
		adicDB( cbReceita, 7, 30, 350, 20, "AprovReceita", "", false );
		adicDB( cbAltParc, 7, 50, 350, 20, "AltParcVenda", "", false );
		adicDB( cbAbreGaveta, 7, 70, 350, 20, "AbreGavetaUsu", "", false );
		adicDB( cbAlmoxarife, 7, 90, 350, 20, "AlmoxarifeUsu", "", false );
		adicDB( cbCompra, 7, 110, 350, 20, "ComprasUsu", "", false );

		txtCodAlmox.setRequerido( cbAlmoxarife.isSelected() );

		adicCampo( txtCodAlmox, 7, 150, 100, 20, "CodAlmox", "Cód.almox.", ListaCampos.DB_FK, false );
		adicDescFK( txtDescAlmox, 110, 150, 330, 20, "DescAlmox", "Descrição do almoxarifado" );
		adicDB( rgAprovaSolicitacao, 7, 210, 210, 80, "AprovCPSolicitacaoUsu", "Aprova solicitação", false );
		adicDB( rgAprovaRMA, 230, 210, 210, 80, "AprovRMAUsu", "Aprova RMA", false );

		setListaCampos( false, "USUARIO", "SG" );
		lcCampos.setQueryInsert( false );

		lcCampos.addCarregaListener( this );
		lcCC.addCarregaListener( this );
		lcCampos.addPostListener( this );
		lcCampos.addInsertListener( this );
		lcCampos.addDeleteListener( this );

		cbAlmoxarife.addCheckBoxListener( this );

		btAdicEmp.addActionListener( this );
		btDelEmp.addActionListener( this );

	}

	private void adicionaEmp() {

		if ( lsDisp.isSelectionEmpty() ) {
			return;
		}
		for ( int i = lsDisp.getMaxSelectionIndex(); i >= 0; i-- ) {
		
			if ( lsDisp.isSelectedIndex( i ) ) {
			
				vEmp.add( vDisp.elementAt( i ) );
				vDisp.remove( i );
				vCodEmp.add( vCodDisp.elementAt( i ) );
				vCodDisp.remove( i );
			}
		}
		
		lsDisp.setListData( vDisp );
		lsEmp.setListData( vEmp );
		lcCampos.edit();
	}

	private int buscaAnoBaseCC() {
	
		int iRet = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try {
			
			ps = con.prepareStatement( "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				iRet = rs.getInt( "ANOCENTROCUSTO" );
			}
			
			rs.close();
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
		}
		return iRet;
	}

	private void carregaAcesso() {
	
		int iPos = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		
		try {
	
			sSQL.append( "SELECT FL.CODFILIAL,FL.NOMEFILIAL " );
			sSQL.append( "FROM SGFILIAL FL, SGACESSOEU AC " );
			sSQL.append( "WHERE AC.IDUSU=? AND FL.CODEMP=AC.CODEMPFL AND FL.CODFILIAL=AC.CODFILIALFL" );
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setString( 1, txtIDUsu.getVlrString() );
			
			rs = ps.executeQuery();
			
			vEmp.clear();
			vCodEmp.clear();
			
			while ( rs.next() ) {
			
				vCodEmp.addElement( rs.getString( "CodFilial" ) );
				vEmp.addElement( rs.getString( "NomeFilial" ) != null ? rs.getString( "NomeFilial" ).trim() : "" );
	
				iPos = vCodDisp.indexOf( rs.getString( "CodFilial" ) );
	
				vCodDisp.remove( iPos );
				vDisp.remove( iPos );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			lsEmp.setListData( vEmp );
			lsDisp.setListData( vDisp );
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemInforma( this, "Não foi carregar as filiais que o usuário tem acesso.\n" + err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	private void carregaDisp() {
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
		
			ps = con.prepareStatement( "SELECT CODFILIAL,NOMEFILIAL FROM SGFILIAL WHERE CODEMP=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			
			rs = ps.executeQuery();
			
			vDisp.clear();
			vCodDisp.clear();
			
			while ( rs.next() ) {
			
				vCodDisp.addElement( rs.getString( "CodFilial" ) );
				vDisp.addElement( rs.getString( "NomeFilial" ) != null ? rs.getString( "NomeFilial" ).trim() : "" );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			lsDisp.setListData( vDisp );
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Não foi carregar as filiais diponíveis.\n" + err );
		} finally {
			ps = null;
			rs = null;
		}
	}

	private void gravaAcesso() {
	
		String sSep = null;
		String sSqlI = null;
		String sSqlD = null;
		String sSqlG = null;
		
		PreparedStatement ps = null;
		
		try {
			
			sSep = "";
			sSqlI = "";
			
			for ( int i = 0; i < vCodEmp.size(); i++ ) {
				
				sSqlI += sSep + vCodEmp.elementAt( i );
				sSep = ",";
			}
			
			sSqlD = "DELETE FROM SGACESSOEU WHERE IDUSU=? AND CODEMP=?";
			
			ps = con.prepareStatement( sSqlD );
			ps.setString( 1, txtIDUsu.getVlrString() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			
			ps.executeUpdate();
			
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			if ( vCodEmp.size() > 0 ) {
			
				sSqlI = "INSERT INTO SGACESSOEU (CODEMP,CODFILIAL,IDUSU,CODEMPFL,CODFILIALFL) " + "SELECT CODEMP," + Aplicativo.iCodFilial + ",'" + txtIDUsu.getVlrString() + "',CODEMP,CODFILIAL FROM SGFILIAL " + "WHERE CODEMP=? AND CODFILIAL IN (" + sSqlI + ")";
				
				ps = con.prepareStatement( sSqlI );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.executeUpdate();
				ps.close();
				if ( !con.getAutoCommit() )
					con.commit();
			}
			
			sSqlG = "GRANT " + txtIDGrpUsu.getVlrString().trim() + " TO USER \"" + txtIDUsu.getVlrString().trim() + "\"";
			
			ps = con.prepareStatement( sSqlG );
	
			ps.executeUpdate();
			
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemInforma( this, "Erro ao cadastrar o acesso!\n" + err.getMessage() );
		} finally {
			sSep = null;
			sSqlI = null;
			sSqlD = null;
			sSqlG = null;
			ps = null;
		}
	}

	private void removeEmp() {
	
		if ( lsEmp.isSelectionEmpty() ) {
			return;
		}
		
		for ( int i = lsEmp.getMaxSelectionIndex(); i >= 0; i-- ) {
		
			if ( lsEmp.isSelectedIndex( i ) ) {
			
				vDisp.add( vEmp.elementAt( i ) );
				vEmp.remove( i );
				vCodDisp.add( vCodEmp.elementAt( i ) );
				vCodEmp.remove( i );
			}
		}
		
		lsDisp.setListData( vDisp );
		lsEmp.setListData( vEmp );
		lcCampos.edit();
	}

	public void actionPerformed( ActionEvent evt ) {
	
		if ( evt.getSource() == btAdicEmp ) {
			
			adicionaEmp();
		}
		else if ( evt.getSource() == btDelEmp ) {
		
			removeEmp();
		}
		
		super.actionPerformed( evt );
	}

	public void beforeCarrega( CarregaEvent pevt ) {
	
		if ( pevt.getListaCampos() == lcCC ) {
			
//			System.out.println( "Carrega CC" );
		}
		else if ( (pevt.getListaCampos() == lcCampos) && (txtIDUsu.getText()!=null) ) {
			txtIDUsu.setText( txtIDUsu.getText().toLowerCase() );
		}
		
	
	}

	public void afterCarrega( CarregaEvent pevt ) {

		carregaDisp();
		carregaAcesso();
		txpSenha.setVlrString( "88888888" );
		txpConfirma.setVlrString( "88888888" );
		txtCodAlmox.setRequerido( cbAlmoxarife.isSelected() );
	}

	public void beforeDelete( DeleteEvent devt ) {
	
		PreparedStatement ps = null;
		
		if ( "SYSDBA".equals( txtIDUsu.getVlrString().toUpperCase() ) ) {
		
			return;
		}
		
		try {
			
			ps = conIB.prepareStatement( "execute procedure deleteuser(?)" );
			ps.setString( 1, txtIDUsu.getVlrString() );
			
			ps.execute();
			
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemInforma( this, "Não foi possível excluir usuário no banco de dados.\n" + err );
			devt.cancela();
		} finally {
			ps = null;
		}
	}

	public void afterDelete( DeleteEvent devt ) {

	}

	public void beforeInsert( InsertEvent ievt ) {
	
	}

	public void afterInsert( InsertEvent ievt ) {

		carregaDisp();
		carregaAcesso();
	}

	public void beforePost( PostEvent pevt ) {
	
		if ( ! txpSenha.getVlrString().equals( txpConfirma.getVlrString() ) ) {
			
			pevt.cancela();
			Funcoes.mensagemInforma( this, "Senha diferente da confirmação!" );
			txpSenha.requestFocus();
			
			return;
		}
		else if ( "".equals( txpSenha.getVlrString().trim() ) ) {
			
			pevt.cancela();
			Funcoes.mensagemInforma( this, "Senha em branco!" );
			txpSenha.requestFocus();
			
			return;
		}
		else if ( txpSenha.getVlrString().length() > 8 ) {
			
			pevt.cancela();
			Funcoes.mensagemInforma( this, "A senha não pode ultrapassar 8 caracteres!" );
			txpSenha.requestFocus();
			
			return;
		}
		else if ( ! "ADM".equalsIgnoreCase( txtIDGrpUsu.getVlrString() ) ) {
			
			pevt.cancela();
			Funcoes.mensagemInforma( this, "Só o grupo \"ADM\" está disponível!" );
			txtIDGrpUsu.requestFocus();
			
			return;
		}
		else {
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				
				boolean bCheck = false;

				if ( (lcCampos.getStatus() == ListaCampos.LCS_INSERT) && (txtIDUsu.getText()!=null) ) {
					txtIDUsu.setText( txtIDUsu.getText().toLowerCase() );
				}
				
				if ( ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) || ( lcCampos.getStatus() == ListaCampos.LCS_EDIT ) ) {


					ps = conIB.prepareStatement( "SELECT SRET FROM CHECKUSER(?)" );
					ps.setString( 1, txtIDUsu.getVlrString() );
					
					rs = ps.executeQuery();
					
					if ( rs.next() ) {
						
						if ( "S".equals( rs.getString( 1 ).trim() ) ) {
							
							if ( lcCampos.getStatus() == ListaCampos.LCS_INSERT ) {
							
								Funcoes.mensagemInforma( this, "Atenção!!\n" + "O usuário não será inserido no banco de dados ISC4, \n" + "pois este já esta cadastrado." );
							}
							
							bCheck = true;
						}
					}
					
					rs.close();
					ps.close();
					
					if ( bCheck ) {
						
						if ( ! "88888888".equals( txpSenha.getVlrString() ) && 
								! "SYSDBA".equals( txtIDUsu.getVlrString().toUpperCase() ) ) {
						
							ps = conIB.prepareStatement( "EXECUTE PROCEDURE CHANGEPASSWORD(?,?)" );
						}
						else {
						
							return;
						}
					}
					else {
					
						ps = conIB.prepareStatement( "EXECUTE PROCEDURE ADDUSER(?,?)" );
					}
				}
				else {
				
					return;
				}
				
				ps.setString( 1, txtIDUsu.getVlrString() );
				ps.setString( 2, txpSenha.getVlrString() );
				
				ps.execute();
	
				ps.close();
				
				if ( !con.getAutoCommit() ) {
				
					con.commit();
				}
			} catch ( SQLException err ) {
				err.printStackTrace();
				Funcoes.mensagemInforma( this, "Não foi possível criar usuário no banco de dados.\n" + err );
				pevt.cancela();
			} finally {
				ps = null;
				rs = null;
			}
		}
	}

	public void afterPost( PostEvent pevt ) {

		if ( pevt.ok ) {
			
			gravaAcesso();
		}
	}

	public void valorAlterado( CheckBoxEvent evt ) {
	
		boolean checked = evt.getCheckBox().isSelected();
		txtCodAlmox.setRequerido( checked );
	}

	public void setConexao( Connection cn, Connection cnIB ) {

		super.setConexao( cn );
		
		conIB = cnIB;
		lcGrup.setConexao( cn );
		lcCC.setConexao( cn );
		lcCC.setWhereAdic( "NIVELCC=10 AND ANOCC=" + buscaAnoBaseCC() );
		lcAlmox.setConexao( cn );
		
		if ( conIB == null ) {
			Funcoes.mensagemInforma( this, 
					  "A conexão com o banco de dados de usuário está nula,\n" 
					+ "provavelmente o usuário qual você conectou não possui\n" 
					+ "permissão para acessar este banco de dados.\n" 
					+ "Por favor, contate o administrador do sistema." );
			dispose();
		}
	}
}
