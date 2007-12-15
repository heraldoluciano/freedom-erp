package org.freedom.modulos.std;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFilho;


public class FTransfEstoque extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodBarProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtQtdPod = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	private JTextFieldPad txtCodAlmoxOrig = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	 
    private JTextFieldFK txtDescAlmoxOrig = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
    
    private JTextFieldPad txtCodAlmoxDest = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	 
    private JTextFieldFK txtDescAlmoxDest = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
    
    private JTextFieldFK txtSaldoProd = new JTextFieldFK(JTextFieldPad.TP_NUMERIC,15,5);
    
    private JTextFieldPad txtQtdTrans =  new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private ListaCampos lcProduto = new ListaCampos( this );
	
	private ListaCampos lcAlmoxOrig = new ListaCampos( this );
	
	private ListaCampos lcAlmoxDest = new ListaCampos( this );
	
	private JPanelPad pnCenter = new JPanelPad( 300, 300 );
	
	private JButtonPad btExecuta = new JButtonPad( Icone.novo( "btExecuta.gif" ));

	public FTransfEstoque() {

		super( true );
		setTitulo( "Tranferência de produtos/almoxarifados" );
		setAtribos( 10, 30, 400, 300 );
		adicBotaoSair();
	
		montaTela();
		montaListaCampos();
		
		
	}

	private void montaTela(){
		
		add( pnCenter );
		
		pnCenter.adic( new JLabelPad("Cód.Prod"), 10, 5, 80, 20 );
		pnCenter.adic( txtCodProd, 10, 25, 80, 20 );
		pnCenter.adic( new JLabelPad("Descrição do produto"), 100, 5, 250, 20 );
		pnCenter.adic( txtDescProd, 100, 25, 250, 20 );
		pnCenter.adic( new JLabelPad("Cód.Alm.Orig"), 10, 45, 80, 20 );
		pnCenter.adic( txtCodAlmoxOrig, 10, 65, 80, 20 );
		pnCenter.adic( new JLabelPad("Descrição do almoxarifado de origen"), 100, 45, 250, 20 );
		pnCenter.adic( txtDescAlmoxOrig, 100, 65, 250, 20 );
		pnCenter.adic( new JLabelPad("Cód.Alm.Dest"), 10, 85, 80, 20 );
		pnCenter.adic( txtCodAlmoxDest, 10, 105, 80, 20 );
		pnCenter.adic( new JLabelPad("Descrição do almoxarifado de destino"), 100, 85, 250, 20 );
		pnCenter.adic( txtDescAlmoxDest, 100, 105, 250, 20 );
		
		pnCenter.adic( new JLabelPad("Quantidade"), 10, 130, 70, 20 );
		pnCenter.adic( txtQtdTrans, 10, 150, 70, 20 );
		pnCenter.adic( btExecuta, 10, 180, 30, 30 );
		
		btExecuta.addActionListener( this );
	}
	
	private void montaListaCampos(){
		
		/**
		 * LC Produto
		 */
		
		lcProduto.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.produto", ListaCampos.DB_PK, true ) );
		lcProduto.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProduto.add( new GuardaCampo( txtRefProd, "RefProd", "Ref. produto", ListaCampos.DB_SI, false ) );
		lcProduto.add( new GuardaCampo( txtCodBarProd, "CodBarProd", "Cód. Barras", ListaCampos.DB_SI, false ) );
		lcProduto.add( new  GuardaCampo( txtCodBarProd, "SldProd", "Saldo", ListaCampos.DB_SI, false ));
		txtCodProd.setTabelaExterna( lcProduto );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProduto.setReadOnly( true );
		lcProduto.montaSql( false, "PRODUTO", "EQ" );
		
		/**
		 * LC almoxarifado de origen 
		 */
		
		lcAlmoxOrig.add(new GuardaCampo( txtCodAlmoxOrig, "CodAlmox", "Cód.almox.", ListaCampos.DB_PK, true));
        lcAlmoxOrig.add(new GuardaCampo( txtDescAlmoxOrig, "DescAlmox", "Descrição do almox.", ListaCampos.DB_SI, false));
        txtCodAlmoxOrig.setTabelaExterna(lcAlmoxOrig);
        txtCodAlmoxOrig.setNomeCampo("CodAlmox");
        txtCodAlmoxOrig.setFK(true);
        lcAlmoxOrig.setReadOnly(true);
        lcAlmoxOrig.montaSql(false, "ALMOX", "EQ");
        
        /**
         *LC almoxarifado de destino 
         */
        
        lcAlmoxDest.add(new GuardaCampo( txtCodAlmoxDest, "CodAlmox", "Cód.almox.", ListaCampos.DB_PK, true));
        lcAlmoxDest.add(new GuardaCampo( txtDescAlmoxDest, "DescAlmox", "Descrição do almox.", ListaCampos.DB_SI, false));
        txtCodAlmoxDest.setTabelaExterna(lcAlmoxDest);
        txtCodAlmoxDest.setNomeCampo("CodAlmox");
        txtCodAlmoxDest.setFK(true);
        lcAlmoxDest.setReadOnly(true);
        lcAlmoxDest.montaSql(false, "ALMOX", "EQ");

	}
	
	private void fazTransf(){
		
		StringBuffer sSQLDelet = new StringBuffer(); 
		StringBuffer sSQLInsert = new StringBuffer(); 
		
		if("".equals(  txtCodProd.getVlrString() )){
			Funcoes.mensagemInforma( this, "Código do produto é requerido!" );
		}
		else if("".equals( txtCodAlmoxOrig.getVlrString())){
			Funcoes.mensagemInforma( this, "Almoxarifado de origen é requerido!" ); 
		}
		else if("".equals( txtCodAlmoxDest.getVlrString() )){
			Funcoes.mensagemInforma( this, "Almoxarifado de destino é requerido!" ); 
		}
		else if( txtCodAlmoxOrig.getVlrString().equals( txtCodAlmoxDest.getVlrString() )){
			Funcoes.mensagemInforma( this, "O almoxarifado de destino não pode ser igual ao de origem" );
			
		}
		
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcProduto.setConexao( cn );
		lcAlmoxOrig.setConexao( cn );
		lcAlmoxDest.setConexao( cn );

	}

	public void actionPerformed( ActionEvent e ) {

		if( e.getSource() == btExecuta ){
			fazTransf();
		}
	}
}
