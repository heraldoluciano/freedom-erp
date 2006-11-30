package org.freedom.plugin;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.modulos.atd.FAgenda;
import org.freedom.modulos.std.FCliente;
import org.freedom.modulos.std.FManutPag;
import org.freedom.modulos.std.FManutRec;
import org.freedom.modulos.std.FOrcamento;
import org.freedom.modulos.std.FProduto;
import org.freedom.modulos.std.FVenda;
import org.freedom.telas.Aplicativo;

public class Backgroud34 extends JPanelPad implements MouseListener {

	private static final long serialVersionUID = 1l;

	private JLabelPad lbfundo = new JLabelPad( Icone.novo( "bgFreedomSTD2.jpg" ) );

	private PanelImagemEmpresa pnEmpresa = new PanelImagemEmpresa();

	private JLabelPad lbAgenda = new JLabelPad();

	private JLabelPad lbVenda = new JLabelPad();

	private JLabelPad lbReceber = new JLabelPad();

	private JLabelPad lbPagar = new JLabelPad();

	private JLabelPad lbProduto = new JLabelPad();

	private JLabelPad lbOrcamento = new JLabelPad();

	private JLabelPad lbCliente = new JLabelPad();

	private Connection con = null;

	public Backgroud34() {

		super();

		setBorder( null );
		setSize( 500, 400 );

		adic( pnEmpresa, 180, 126, 147, 147 );
		adic( lbAgenda, 130, 2, 92, 92 );
		adic( lbVenda, 319, 12, 92, 92 );
		adic( lbReceber, 397, 130, 92, 81 );
		adic( lbPagar, 371, 257, 92, 81 );
		adic( lbProduto, 197, 289, 92, 92 );
		adic( lbOrcamento, 41, 236, 92, 92 );
		adic( lbCliente, 3, 98, 92, 92 );
		adic( lbfundo, 0, 0, 500, 400 );

		lbAgenda.addMouseListener( this );
		lbVenda.addMouseListener( this );
		lbPagar.addMouseListener( this );
		lbReceber.addMouseListener( this );
		lbProduto.addMouseListener( this );
		lbOrcamento.addMouseListener( this );
		lbCliente.addMouseListener( this );
	}

	public void setConexao( Connection con ) {

		this.con = con;
		carregaImagemEmpresa();
	}

	private void carregaImagemEmpresa() {

		try {

			String sql = "SELECT FOTOEMP FROM SGEMPRESA WHERE CODEMP=?";
			PreparedStatement ps = con.prepareStatement( sql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {

				Blob blob = rs.getBlob( "FOTOEMP" );
				BufferedInputStream bi = new BufferedInputStream( blob.getBinaryStream() );
				byte[] bytes = new byte[ 65000 ];

				try {
					bi.read( bytes, 0, bytes.length );
				} catch ( IOException e ) {
					e.printStackTrace();
				}
				
				Image image = new ImageIcon( bytes ).getImage();
				pnEmpresa.setImage( image );
			}

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void mouseClicked( MouseEvent e ) {

		if ( e.getSource() == lbAgenda ) {

			FAgenda tela = new FAgenda();

			if ( !Aplicativo.telaPrincipal.temTela( "Agenda" ) ) {

				Aplicativo.telaPrincipal.criatela( "Agenda", tela, con );
			}
		}
		else if ( e.getSource() == lbVenda ) {

			FVenda tela = new FVenda();

			if ( !Aplicativo.telaPrincipal.temTela( "Venda" ) ) {

				Aplicativo.telaPrincipal.criatela( "Venda", tela, con );
			}
		}
		else if ( e.getSource() == lbReceber ) {

			FManutRec tela = new FManutRec();

			if ( !Aplicativo.telaPrincipal.temTela( "Manutenção de contas a receber" ) ) {

				Aplicativo.telaPrincipal.criatela( "Manutenção de contas a receber", tela, con );
			}
		}
		else if ( e.getSource() == lbPagar ) {

			FManutPag tela = new FManutPag();

			if ( !Aplicativo.telaPrincipal.temTela( "Manutenção de contas a pagar" ) ) {

				Aplicativo.telaPrincipal.criatela( "Manutenção de contas a pagar", tela, con );
			}
		}
		else if ( e.getSource() == lbProduto ) {

			FProduto tela = new FProduto();

			if ( !Aplicativo.telaPrincipal.temTela( "Produtos" ) ) {

				Aplicativo.telaPrincipal.criatela( "Produtos", tela, con );
			}
		}
		else if ( e.getSource() == lbOrcamento ) {

			FOrcamento tela = new FOrcamento();

			if ( !Aplicativo.telaPrincipal.temTela( "Orçamento" ) ) {

				Aplicativo.telaPrincipal.criatela( "Orçamento", tela, con );
			}
		}
		else if ( e.getSource() == lbCliente ) {

			FCliente tela = new FCliente();

			if ( !Aplicativo.telaPrincipal.temTela( "Clientes" ) ) {

				Aplicativo.telaPrincipal.criatela( "Clientes", tela, con );
			}
		}
	}

	public void mouseEntered( MouseEvent e ) {

	}

	public void mouseExited( MouseEvent e ) {

	}

	public void mousePressed( MouseEvent e ) {

	}

	public void mouseReleased( MouseEvent e ) {

	}
	
	private class PanelImagemEmpresa extends JPanel {
		
		private static final long serialVersionUID = 1l;
		
		private Image image = null;
		
		public PanelImagemEmpresa() {

			super();
			setOpaque( false );
		}
		
		public void setImage( Image image ) {
			
			this.image = image;
			repaint();
		}

		public void paint( Graphics g ) {

			super.paint( g );

			try {

				Dimension tam = getSize();

				if ( image != null ) {

					g.drawImage( image, 0, 0, tam.width, tam.height, this );
				}

			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}		
		
	}

}
