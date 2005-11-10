package org.freedom.modulos.pdv;

/**
 * @version 01/11/2005 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe: @(#)FFechaVenda.java <BR>
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
 * Tela de fechamento de venda no PDV.
 * 
 */

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.comutacao.Tef;
import org.freedom.drivers.JBemaFI32;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.Logger;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFDialogo;

public class DLFechaVenda extends FFDialogo implements FocusListener {
	private static final long serialVersionUID = 1L;
	private int casasDec = Aplicativo.casasDec;

	private JTextFieldPad txtCodVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtTipoVenda = new JTextFieldPad(JTextFieldPad.TP_STRING,1,0);	
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldFK txtVlrCupom = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,12,2);
	private JTextFieldPad txtVlrDinheiro = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,12,2);
	private JTextFieldPad txtVlrCheque = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,12,2);
	private JTextFieldPad txtVlrChequeElet = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,12,2);
	private JTextFieldFK txtVlrPago = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,12,2);
	private JTextFieldFK txtVlrTroco = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,12,2);
	private JTextFieldPad txtCodTran = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescTran = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtPlacaFreteVD = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
	private JTextFieldPad txtUFFreteVD = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
	private JTextFieldPad txtVlrFreteVD = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
	private JTextFieldPad txtConhecFreteVD = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
	private JTextFieldPad txtQtdFreteVD = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,casasDec);
	private JTextFieldPad txtPesoBrutVD = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,casasDec);
	private JTextFieldPad txtPesoLiqVD = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,casasDec);
	private JTextFieldPad txtEspFreteVD = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
	private JTextFieldPad txtMarcaFreteVD = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
	private JTextFieldPad txtCodAuxV = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtCPFCliAuxV = new JTextFieldPad(JTextFieldPad.TP_STRING,11,0);
	private JTextFieldPad txtNomeCliAuxV = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtEndCliAuxV = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtNumCliAuxV = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCidCliAuxV = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
	private JTextFieldPad txtUFCliAuxV = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);	
	private JLabelPad lbCodTran = new JLabelPad("Cód.tran.");
	private JLabelPad lbNomeTran = new JLabelPad("Nome do transportador");
	private JLabelPad lbTipoFreteVD = new JLabelPad("Tipo");
	private JLabelPad lbPlacaFreteVD = new JLabelPad("Placa");
	private JLabelPad lbUFFreteVD = new JLabelPad("UF");
	private JLabelPad lbVlrFreteVD = new JLabelPad("Valor");
	private JLabelPad lbQtdFreteVD = new JLabelPad("Volumes");
	private JLabelPad lbPesoBrutVD = new JLabelPad("Peso B.");
	private JLabelPad lbPesoLiqVD = new JLabelPad("Peso L.");
	private JLabelPad lbEspFreteVD = new JLabelPad("Espec.");
	private JLabelPad lbMarcaFreteVD = new JLabelPad("Marca");
	private JLabelPad lbChequeElet;
	private Vector vVals = new Vector();
	private Vector vLabs = new Vector();
	private JRadioGroup rgFreteVD = null;
	private JTabbedPanePad tpn = new JTabbedPanePad();
	private JPanelPad pnVenda = new JPanelPad(400,300);
	private JPanelPad pnAdic = new JPanelPad(400,300);
	private JPanelPad pnFrete = new JPanelPad(400,300);
	private ListaCampos lcAuxVenda = new ListaCampos(this);
	private ListaCampos lcFreteVD = new ListaCampos(this);
	private ListaCampos lcPlanoPag = new ListaCampos(this,"PG");
	private ListaCampos lcTran = new ListaCampos(this,"TN");
	private JBemaFI32 bf = (AplicativoPDV.bECFTerm ? new JBemaFI32() : null);
	private Tef tef = null;
	private String sTipoVenda = null;
	private int iCodVenda = 0;
	private int iNumCupom = 0;
	private Vector vTefsOK = new Vector();
	private BigDecimal bigPagoTef = new BigDecimal("0.00");
	private boolean bPref;
	Connection con = null;
	
	public DLFechaVenda( int iCodVenda, String sTipoVenda, BigDecimal valCupom, int iNumCupom,Connection conn) {
		//super(Aplicativo.telaPrincipal);
		setTitulo("Fechamento de venda");
		setAtribos(330,345);
		
		this.sTipoVenda = sTipoVenda;
		this.iCodVenda = iCodVenda;
		this.iNumCupom = iNumCupom;
		
		txtVlrCupom.setVlrBigDecimal(valCupom);
		txtVlrChequeElet.setAtivo(false);
		
		vVals.addElement("C");
	    vVals.addElement("F");
	    vLabs.addElement("CIF");
	    vLabs.addElement("FOB");	    
	    rgFreteVD = new JRadioGroup(1,2,vLabs, vVals);
		
		lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, true));
		lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI,false));
		lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag.setReadOnly(true);
		txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
		txtCodPlanoPag.setNomeCampo("CodPlanoPag");
		txtCodPlanoPag.setFK(true);
		
		txtCodTran.setNomeCampo("CodTran");
	    lcTran.add(new GuardaCampo( txtCodTran, "CodTran", "Cód.tran.", ListaCampos.DB_PK, false));
	    lcTran.add(new GuardaCampo( txtDescTran, "RazTran", "Nome do transportador", ListaCampos.DB_SI,false));
	    txtDescTran.setListaCampos(lcTran);
	    txtCodTran.setTabelaExterna(lcTran);
		txtCodTran.setFK(true);
	    lcTran.montaSql(false, "TRANSP", "VD");
	    lcTran.setQueryCommit(false);
	    lcTran.setReadOnly(true);
	    
	    lcFreteVD.add(new GuardaCampo( txtTipoVenda, "TipoVenda", "Tipo", ListaCampos.DB_PK, false));
	    lcFreteVD.add(new GuardaCampo( txtCodVenda, "CodVenda", "N.pedido", ListaCampos.DB_PK, false));
	    lcFreteVD.add(new GuardaCampo( txtCodTran, "CodTran", "Cód.tran.", ListaCampos.DB_FK, txtDescTran, false));
	    lcFreteVD.add(new GuardaCampo( rgFreteVD, "TipoFreteVD", "Tipo", ListaCampos.DB_SI,true));
	    lcFreteVD.add(new GuardaCampo( txtConhecFreteVD, "ConhecFreteVD", "Conhec.", ListaCampos.DB_SI, false));
	    lcFreteVD.add(new GuardaCampo( txtPlacaFreteVD, "PlacaFreteVD", "Placa", ListaCampos.DB_SI,true));
	    lcFreteVD.add(new GuardaCampo( txtUFFreteVD, "UFFreteVD", "Placa", ListaCampos.DB_SI,true));
	    lcFreteVD.add(new GuardaCampo( txtVlrFreteVD, "VlrFreteVD", "Valor", ListaCampos.DB_SI,true));
	    lcFreteVD.add(new GuardaCampo( txtQtdFreteVD, "QtdFreteVD", "Qtd.", ListaCampos.DB_SI,true));
	    lcFreteVD.add(new GuardaCampo( txtPesoBrutVD, "PesoBrutVD", "Peso bruto", ListaCampos.DB_SI,true));
	    lcFreteVD.add(new GuardaCampo( txtPesoLiqVD, "PesoLiqVD", "Peso liq.", ListaCampos.DB_SI,true));
	    lcFreteVD.add(new GuardaCampo( txtEspFreteVD, "EspFreteVD", "Esp.fiscal", ListaCampos.DB_SI,true));
	    lcFreteVD.add(new GuardaCampo( txtMarcaFreteVD, "MarcaFreteVD", "Marca", ListaCampos.DB_SI,true));
	    lcFreteVD.montaSql(false, "FRETEVD", "VD");
	    rgFreteVD.setListaCampos(lcFreteVD);
	    txtPlacaFreteVD.setListaCampos(lcFreteVD);
	    txtUFFreteVD.setListaCampos(lcFreteVD);
	    txtVlrFreteVD.setListaCampos(lcFreteVD);
	    txtQtdFreteVD.setListaCampos(lcFreteVD);
	    txtPesoBrutVD.setListaCampos(lcFreteVD);
	    txtPesoLiqVD.setListaCampos(lcFreteVD);
	    txtEspFreteVD.setListaCampos(lcFreteVD);
	    txtMarcaFreteVD.setListaCampos(lcFreteVD);
	    txtConhecFreteVD.setListaCampos(lcFreteVD);
	    txtCodTran.setListaCampos(lcFreteVD);
	    
	    lcAuxVenda.add(new GuardaCampo( txtTipoVenda, "TipoVenda", "Tp.venda", ListaCampos.DB_PK, true));
	    lcAuxVenda.add(new GuardaCampo( txtCodVenda, "CodVenda", "N.pedido", ListaCampos.DB_PK,true));
	    lcAuxVenda.add(new GuardaCampo( txtCodAuxV, "CodAuxV", "Cód.aux.", ListaCampos.DB_PK, true));
	    lcAuxVenda.add(new GuardaCampo( txtCPFCliAuxV, "CPFCliAuxV", "CPF", ListaCampos.DB_SI ,true));
	    lcAuxVenda.add(new GuardaCampo( txtNomeCliAuxV, "NomeCliAuxV", "Nome", ListaCampos.DB_SI,true));
	    lcAuxVenda.add(new GuardaCampo( txtEndCliAuxV, "EndCliAuxV", "Endereco", ListaCampos.DB_SI,true));
	    lcAuxVenda.add(new GuardaCampo( txtNumCliAuxV, "NumCliAuxV", "Numero", ListaCampos.DB_SI,true));
	    lcAuxVenda.add(new GuardaCampo( txtCidCliAuxV, "CidCliAuxV", "Cidade", ListaCampos.DB_SI,true));
	    lcAuxVenda.add(new GuardaCampo( txtUFCliAuxV, "UFCliAuxV", "UF", ListaCampos.DB_SI,true));
	    lcAuxVenda.montaSql(false, "AUXVENDA", "VD");
	    txtCodAuxV.setListaCampos(lcAuxVenda);
	    txtCPFCliAuxV.setListaCampos(lcAuxVenda);
	    txtNomeCliAuxV.setListaCampos(lcAuxVenda);
	    txtEndCliAuxV.setListaCampos(lcAuxVenda);
	    txtNumCliAuxV.setListaCampos(lcAuxVenda);
	    txtCidCliAuxV.setListaCampos(lcAuxVenda);
	    txtUFCliAuxV.setListaCampos(lcAuxVenda);
	    txtCPFCliAuxV.setMascara(JTextFieldPad.MC_CPF);
		
		c.add(tpn);
		
		tpn.add("Fechamento",pnVenda);
		tpn.add("Adicionais",pnAdic);
	    tpn.add("Frete",pnFrete);
	    	    	   	   		
	    // FECHAMENTO
	    setPainel(pnVenda);
	    
	    adic(new JLabelPad("Cód.p.pag."),7,5,250,15);
	    adic(txtCodPlanoPag,7,20,80,20);
	    adic(new JLabelPad("Descrição da forma de pagamento"),90,5,250,15);
	    adic(txtDescPlanoPag,90,20,200,20);
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		
		adic(lbLinha,7,50,300,2);
		
		adic(new JLabelPad("Valor total do cupom: "),7,60,150,20);
		adic(txtVlrCupom,170,60,120,20);
		adic(new JLabelPad("Valor em dinheiro: "),7,85,150,20);
		adic(txtVlrDinheiro,170,85,120,20);
		adic(new JLabelPad("Valor em cheque: "),7,110,150,20);
		adic(txtVlrCheque,170,110,120,20);
		adic((lbChequeElet = new JLabelPad("Valor em ch. elet./cartão: ")),7,135,150,20);
		adic(txtVlrChequeElet,170,135,120,20);
		adic(new JLabelPad("Valor pago: "),7,160,150,20);
		adic(txtVlrPago,170,160,120,20);
		adic(new JLabelPad("Valor troco: "),7,185,150,20);
		adic(txtVlrTroco,170,185,120,20);
		
		//AUXILIAR
		
		setPainel(pnAdic);
		
		adic(new JLabelPad("Nome"),7,0,240,20);
	    adic(txtNomeCliAuxV,7,20,285,20);
	    adic(new JLabelPad("Endereço"),7,40,240,20);
	    adic(txtEndCliAuxV,7,60,231,20);
	    adic(new JLabelPad("Num."),241,40,50,20);
	    adic(txtNumCliAuxV,241,60,53,20);
	    adic(new JLabelPad("CPF"),7,80,120,20);
	    adic(txtCPFCliAuxV,7,100,120,20);
	    adic(new JLabelPad("Cidade"),130,80,100,20);
	    adic(txtCidCliAuxV,130,100,120,20);
	    adic(new JLabelPad("UF"),253,80,40,20);
	    adic(txtUFCliAuxV,253,100,40,20);
		
		//FRETE
		
		setPainel(pnFrete);
		
		adic(lbCodTran,7,0,80,20);
	    adic(txtCodTran,7,20,80,20);
	    adic(lbNomeTran,90,0,210,20);
	    adic(txtDescTran,90,20,210,20);
	    adic(lbTipoFreteVD,7,40,170,20);
	    adic(rgFreteVD,7,60,130,30);
	    adic(new JLabelPad("Conhec."),140,50,77,20);
	    adic(txtConhecFreteVD,140,70,77,20);
	    adic(lbPlacaFreteVD,220,50,80,20);
	    adic(txtPlacaFreteVD,220,70,80,20);
	    adic(lbVlrFreteVD,7,90,120,20);
	    adic(txtVlrFreteVD,7,110,120,20);
	    adic(lbQtdFreteVD,130,90,77,20);
	    adic(txtQtdFreteVD,130,110,120,20);
	    adic(lbUFFreteVD,253,90,40,20);
	    adic(txtUFFreteVD,253,110,45,20);
	    adic(lbPesoBrutVD,7,130,120,20);
	    adic(txtPesoBrutVD,7,150,120,20);
	    adic(lbPesoLiqVD,130,130,120,20);
	    adic(txtPesoLiqVD,130,150,120,20);
	    adic(lbEspFreteVD,7,170,120,20);
	    adic(txtEspFreteVD,7,190,120,20);
	    adic(lbMarcaFreteVD,130,170,120,20);
	    adic(txtMarcaFreteVD,130,190,120,20);
	    

		setConexao(conn);
		
		if(!bPref){
	    	tpn.setEnabledAt(1,false);
	    	tpn.setEnabledAt(2,false);
	    }

	    int iCodAux = buscaCodAux();
	    if (iCodAux > 0) {
	    	txtCodAuxV.setVlrInteger(new Integer(iCodAux));
	        lcAuxVenda.carregaDados();
	    }
	    else
	        txtCodAuxV.setVlrInteger(new Integer(1));
		
		
//Não pode commitar enquanto todo o processo tive OK:
		
		lcPlanoPag.setPodeCommit(false);
		
		txtVlrDinheiro.addFocusListener(this);
		txtVlrCheque.addFocusListener(this);
		txtVlrChequeElet.addFocusListener(this);
		
	}
	private int buscaCodAux() {
	 	int iRet = 0;
	 	PreparedStatement ps = null;
	 	ResultSet rs = null;
	  	String sSQL = null;
	  	try {
	  		sSQL = "SELECT CODAUXV FROM VDAUXVENDA WHERE CODEMP=?" +
              " AND CODFILIAL=? AND CODVENDA=?";
		  	ps = con.prepareStatement(sSQL);
		  	ps.setInt(1,Aplicativo.iCodEmp);
		  	ps.setInt(2,ListaCampos.getMasterFilial("VDAUXVENDA"));
		  	ps.setInt(3,txtCodVenda.getVlrInteger().intValue());
		  	rs = ps.executeQuery();
		  	if (rs.next()) {
		  		 iRet = rs.getInt("CodAuxV");
		  	}
		  	rs.close();
		  	ps.close();
	  	}
	    catch(SQLException err) {
	    	Funcoes.mensagemErro(this,"Erro ao buscar codaux.\n"+err.getMessage(),true,con,err);
	    	err.printStackTrace();
	    }
	    finally{
	    	ps = null;
	    	rs = null;
	    	sSQL = null;
	    }
	  	return iRet;
	}
	private Properties processaTef() {
		Properties retTef = tef.solicVenda(iNumCupom, txtVlrChequeElet.getVlrBigDecimal());
		
		if (retTef == null || !tef.validaTef(retTef))
			return null;
		
		return retTef;
			
	}
	private boolean finalizaTEF(Properties retTef) {
	    boolean bRet = false;
	    boolean bLeituraX = false;
        Object sLinhas[] = tef.retImpTef(retTef);
        String sComprovante = "\n\n";
        for (int i=0;i<sLinhas.length;i++)
            sComprovante += sLinhas[i]+"\n";
        
        
        //verifica se ha linhas a serem impressas, caso contrário sai sem imprimir nada.
        if (sLinhas.length == 0)
            return true;
        
        do {
            try {
                //Soh abre o comprovante vinculado se não é para imprimir a leituraX (ou seja não esta reimprimindo).
                if (!bLeituraX) {
                    if (!bf.abreComprovanteNaoFiscalVinculado(
                        Aplicativo.strUsuario, txtDescPlanoPag
                                .getVlrString(), AplicativoPDV.bModoDemo))
                        throw new Exception("");
                    if (!bf.usaComprovanteNaoFiscalVinculadoTef(
                            Aplicativo.strUsuario, sComprovante,
                            AplicativoPDV.bModoDemo))
                         throw new Exception("");
                }
                else {
                    //Esta reimprimindo entao vamos usar o relatorioGerencial.
                    if (!bf.leituraX(
                            Aplicativo.strUsuario, AplicativoPDV.bModoDemo))
                            throw new Exception("");
                    if (!bf.relatorioGerencialTef(
                            Aplicativo.strUsuario, sComprovante, AplicativoPDV.bModoDemo))
                            throw new Exception("");
                }
                

                //Coloca uns espacos para retirar o comprovante.

                if (!bLeituraX) {
                	for (int i=0;i<10;i++)
                		if (!bf.usaComprovanteNaoFiscalVinculadoTef(
                				Aplicativo.strUsuario, " ",
								AplicativoPDV.bModoDemo))
                			throw new Exception("");
                }
                else {
                	for (int i=0;i<10;i++)
                		if (!bf.relatorioGerencialTef(
                            Aplicativo.strUsuario, " ",
                            AplicativoPDV.bModoDemo))
                			throw new Exception("");
                }

                //Aguarda 5 segundo para imprimir o segundo comprovante:

                bf.iniciaModoTEF(Aplicativo.strUsuario,AplicativoPDV.bModoDemo);
                
                Thread.sleep(5000);

                bf.finalizaModoTEF(Aplicativo.strUsuario,AplicativoPDV.bModoDemo);

                if (!bLeituraX) {
                    if (!bf.usaComprovanteNaoFiscalVinculadoTef(
                        Aplicativo.strUsuario, sComprovante,
                        AplicativoPDV.bModoDemo))
                     throw new Exception("");
                }
                else {
                    if (!bf.relatorioGerencialTef(
                            Aplicativo.strUsuario, sComprovante,
                            AplicativoPDV.bModoDemo))
                         throw new Exception("");
                }

                if (!bLeituraX) {
                    if (!bf.fechaComprovanteNaoFiscalVinculado(
                        Aplicativo.strUsuario, 
                        AplicativoPDV.bModoDemo))
                     throw new Exception("");
                }
                else {
                    if (!bf.fechaRelatorioGerencial(
                            Aplicativo.strUsuario, 
                            AplicativoPDV.bModoDemo))
                         throw new Exception("");
                }

                bRet = true;
            } catch (Exception err) {
                bf.finalizaModoTEF(Aplicativo.strUsuario,AplicativoPDV.bModoDemo);
                bRet = false;
                if (Funcoes.mensagemConfirma(null,
                        "Impressora não responde, tente novamente?") == JOptionPane.YES_OPTION) {
                    bLeituraX = true;
                    continue;
                } 
            }
            break;
        } while (true);
		if (bRet) {
		    bRet = tef.confirmaVenda(retTef);
		    if (bRet) {
		    	vinculaTef(retTef);
		    }
		}
		else {
		    //Estornando a TEF:
		    bRet = tef.naoConfirmaVenda(retTef);
		    if (bigPagoTef.doubleValue() > 0.0)
		        bigPagoTef = bigPagoTef.subtract(Tef.retValor(retTef));
		    recalcPago();
		    vTefsOK.remove(retTef);
		}
		
		return bRet;

	}
	private void vinculaTef(Properties prop) {
		String sSQL = "INSERT INTO VDTEF (CODEMP,CODFILIAL,TIPOVENDA,CODVENDA,NSUTEF,REDETEF,DTTRANSTEF,VLRTEF)"+
					  " VALUES (?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("VDTEF"));
			ps.setString(3,"E");
			ps.setInt(4,iCodVenda);
			ps.setString(5,Tef.retNsu(prop));
			ps.setString(6,Tef.retRede(prop));
			ps.setDate(7,Funcoes.dateToSQLDate(Tef.retData(prop)));
			ps.setBigDecimal(8,Tef.retValor(prop));
			ps.executeUpdate();
		}
		catch (SQLException err) {
			Logger.gravaLogTxt("",Aplicativo.strUsuario,Logger.LGEB_BD,"Erro ao gravar tef vinculado no banco: "+err.getMessage());
		}
	}
	private boolean gravaVenda() {
		boolean bRet = false;
		PreparedStatement ps = null;
/*		String sCVTEF = null;
		String sPlanoTEF = null;
		
		//Ajusta variaveis de TEF se existir.
		if (vTefsOK.size() > 0) {
		    sCVTEF = Tef.retNsu((Properties)vTefsOK.elementAt(0));
		    sPlanoTEF = Funcoes.copy(txtDescPlanoPag.getVlrString(),16);
		}
*/					
		String sSQL = "UPDATE VDVENDA SET STATUSVENDA='V2' WHERE CODEMP=?" +
					" AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='E'";			
		
		try {
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("VDVENDA"));
			ps.setInt(3,iCodVenda);
			ps.executeUpdate();
			bRet = true;
			
		}
		catch (SQLException err) {
			Logger.gravaLogTxt("",Aplicativo.strUsuario,Logger.LGEB_BD,"Erro ao gravar a venda: "+err.getMessage());
		}
		
		return bRet;
	}
	private boolean finalizaVenda() {
	    boolean bRet = false;
		String sSQL = "UPDATE VDVENDA SET STATUSVENDA='V3' WHERE CODEMP=?" +
		" AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='E'";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("VDVENDA"));
			ps.setInt(3,iCodVenda);
			ps.executeUpdate();
			bRet = true;
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(null,"Não foi possível finalizar a venda!\n"+err.getMessage(),true,con,err);
			err.printStackTrace();
		}
		return bRet;
	}
	private boolean execFechamento() {
		boolean bRet = false;
		if (txtVlrPago.getVlrDouble().doubleValue() == 0) {
			Funcoes.mensagemInforma(this,"Digite o valor pago!");
			return false;
		}
		else if (txtCodPlanoPag.getVlrInteger().intValue() == 0) {
			Funcoes.mensagemInforma(this,"Digite o código da forma de pagamento!");
			return false;
		}
		else if (txtVlrChequeElet.getVlrDouble().doubleValue() > 0) {
		    Properties ppCompTef;
		    if ((ppCompTef = processaTef()) == null) {
		        Funcoes.mensagemInforma(this,"Não foi possível processar TEF");
		        return false;
		    }
		    if (txtVlrChequeElet.getVlrDouble().doubleValue() < txtVlrCupom.getVlrDouble().doubleValue()) {
		        bigPagoTef = bigPagoTef.add(txtVlrChequeElet.getVlrBigDecimal());
		        txtVlrChequeElet.setVlrString("");
		        vTefsOK.add(ppCompTef);
		        
		        //bloqueio para imprimir somente um comprovante por cupom:
		        
		        lbChequeElet.setEnabled(false);
		        txtVlrChequeElet.setAtivo(false);
		        txtVlrDinheiro.requestFocus();
		        
		        recalcPago();
		        return false;
		    }
		    vTefsOK.add(ppCompTef);
		}
		else if (txtVlrPago.getVlrDouble().doubleValue() < txtVlrCupom.getVlrDouble().doubleValue()) {
			Funcoes.mensagemInforma(this,"Valor pago menor que o valor da venda!");
			return false;
		}
		
		if (bPref){
			if(lcFreteVD.getStatus() == ListaCampos.LCS_EDIT || lcFreteVD.getStatus() == ListaCampos.LCS_INSERT){
				if(!lcFreteVD.post())
					return false;
			}
			if(lcAuxVenda.getStatus() == ListaCampos.LCS_EDIT || lcAuxVenda.getStatus() == ListaCampos.LCS_INSERT){
				if(!lcAuxVenda.post())
					return false;
			}
		}
		
		if (!gravaVenda()) 
			return false;
				
		try {
			
//			Fecha a venda:			

			String sSQL = "EXECUTE PROCEDURE PVFECHAVENDASP(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("PVMOVCAIXA"));
			ps.setBigDecimal(3,txtVlrPago.getVlrBigDecimal());
			ps.setInt(4,AplicativoPDV.iCodCaixa);
			ps.setDate(5,Funcoes.dateToSQLDate(new Date()));
			ps.setString(6,Aplicativo.strUsuario);
			ps.execute();
			ps.close();
			bRet = true;
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao executar fechamento!\n"+err.getMessage(),true,con,err);
			Logger.gravaLogTxt("",Aplicativo.strUsuario,Logger.LGEB_BD,"Erro ao executar fechamento.");
		}
		return bRet;
	}
	private boolean execTroco() {
		boolean bRet = false;
		
// Sangria para o troco:		
		
		try {
			String sSQL = "EXECUTE PROCEDURE PVSANGRIASP(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("PVMOVCAIXA"));
			ps.setBigDecimal(3,txtVlrTroco.getVlrBigDecimal());
			ps.setInt(4,AplicativoPDV.iCodCaixa);
			ps.setDate(5,Funcoes.dateToSQLDate(new Date()));
			ps.setString(6,Aplicativo.strUsuario);
			ps.execute();
			ps.close();
			bRet = true;
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao executar o troco!\n"+err.getMessage(),true,con,err);
			Logger.gravaLogTxt("",Aplicativo.strUsuario,Logger.LGEB_BD,"Erro ao executar o troco.");
		}
		return bRet;
	}
	private void recalcPago() {
		txtVlrPago.setVlrBigDecimal(
				txtVlrDinheiro.getVlrBigDecimal().add(
						txtVlrCheque.getVlrBigDecimal().add(
								txtVlrChequeElet.getVlrBigDecimal()
						)
				).add(bigPagoTef)
		);
		txtVlrTroco.setVlrBigDecimal(
				txtVlrPago.getVlrBigDecimal().subtract(
						txtVlrCupom.getVlrBigDecimal()
				)
		);
	}
	private int buscaPlanoPag() {
		int iRet = 0;
		String sSQL = "SELECT CODPLANOPAG FROM SGPREFERE4 WHERE " +
		"CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,Aplicativo.iCodFilial);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt("CodPlanoPag");
			}
			rs.close();
			ps.close();
		}
		catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar o plano de pagemento.\n"+
					"Provavelmente não foram gravadas corretamente as preferências!\n"+err.getMessage());
			Logger.gravaLogTxt("",Aplicativo.strUsuario,Logger.LGEB_BD,"Erro ao buscar o plano de pagemento.");
		}
		return iRet;
	}
	
	private String getMenssage(){
		String sMenssage = "";
		if(txtNomeCliAuxV.getVlrString().trim().length() > 0){
			sMenssage = txtNomeCliAuxV.getVlrString().trim()+" - "
						+ txtCPFCliAuxV.getVlrString().trim()+"\n"
						+ txtEndCliAuxV.getVlrString().trim()+" , "
						+ txtNumCliAuxV.getVlrString().trim()+" - "
						+ txtCidCliAuxV.getVlrString().trim()+"/"
						+ txtUFCliAuxV.getVlrString().trim()+"\n"
						+ txtDescTran.getVlrString().trim()+" - "
						+ txtPlacaFreteVD.getVlrString().trim();
			if(sMenssage.length()>300)
				sMenssage = sMenssage.substring(0,300);
		}
		return sMenssage;
	}
	
	public void actionPerformed(ActionEvent evt) {
	    boolean bRet = false;
		if (evt.getSource() == btOK) {
			if (execFechamento()) {
				if (AplicativoPDV.bECFTerm) {
					if (bf.fechaCupomFiscal(Aplicativo.strUsuario,Funcoes.copy(txtDescPlanoPag.getVlrString(),16),"","",0.0,txtVlrPago.getVlrDouble().doubleValue(),getMenssage(),AplicativoPDV.bModoDemo)) {
						if (finalizaVenda()) {
						    btCancel.setEnabled(false);
						    
						    //Verifica se existe um comprovante de TEF para imprimir.
						    if (vTefsOK.size() > 0) {
						        for (int i=0;i<vTefsOK.size();i++) {
						            if (finalizaTEF((Properties)vTefsOK.elementAt(i)))
						                bRet = true;
						            else {
						                bRet = false;
						                break;
						            }
						        }
						    }
						    else
							    bRet = true;
						        
							if (bRet && txtVlrTroco.getVlrDouble().doubleValue() > 0) {
							    bRet = execTroco();
							}
						}
					}
				}
			}
			if (!bRet) {
			    return;
			}
		}
		
		super.actionPerformed(evt);
	}
	public void focusLost(FocusEvent fevt) {
		if (fevt.getSource() == txtVlrDinheiro ||
				fevt.getSource() == txtVlrCheque ||
				fevt.getSource() == txtVlrChequeElet) {
			recalcPago();
		}
	}
	public void focusGained(FocusEvent arg0) { }
	
	private boolean prefs() {
		boolean ret = false;
		
		String sSQL = "SELECT ADICPDV FROM SGPREFERE4 WHERE CODEMP=? AND CODFILIAL=?";
	  	PreparedStatement ps = null;
	  	ResultSet rs = null;
	  	
	  	try {
	  		ps = con.prepareStatement(sSQL);
	  		ps.setInt(1,Aplicativo.iCodEmp);
	  		ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE4"));
	  		rs = ps.executeQuery();
	  		if (rs.next()) {
	  			if (rs.getString("AdicPDV").trim().equals("S"))
	  				ret = true;	  			
	  		}
	        rs.close();
	        ps.close();
	        if (!con.getAutoCommit())
	        	con.commit();
	  	}
	  	catch (SQLException err) {
	  		Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage(),true,con,err);
	  	}
	  	finally{
	  		sSQL = null;
	  		ps = null;
	  		rs = null;
	  	}
		
		return ret;
	}
	public void setTef(Tef tef) {
		this.tef = tef;
		txtVlrChequeElet.setAtivo(true);
	}
	public void setConexao(Connection cn) {
		con = cn;
		lcPlanoPag.setConexao(cn);
		txtCodPlanoPag.setVlrInteger(new Integer(buscaPlanoPag()));
		lcPlanoPag.carregaDados();
	    lcTran.setConexao(cn);
	    lcFreteVD.setConexao(cn);
	    lcAuxVenda.setConexao(cn);
	    
	    txtCodVenda.setVlrInteger(new Integer(iCodVenda));
	    txtTipoVenda.setVlrString(sTipoVenda);	    

		bPref = prefs();
	}
}