/**
 * @version 11/11/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez e Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FProcessaSL.java <BR>
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
 * Efetua somatórias nos lançamentos e insere saldos.
 * 
 */


package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import javax.swing.JOptionPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.Processo;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.ProcessoSec;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FProcessaEQ extends FFilho implements ActionListener, CarregaListener {
    private JPanelPad pin = new JPanelPad();
	private JButton btProcessar = new JButton("Executar agora!",Icone.novo("btExecuta.gif"));
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtRefProd = new JTextFieldFK(JTextFieldPad.TP_STRING,13,0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	//private JTextFieldPad txtCodProdFim = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	//private JTextFieldFK txtRefProdFim = new JTextFieldFK(JTextFieldPad.TP_STRING,13,0);
	//private JTextFieldFK txtDescProdFim = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
    private JCheckBoxPad cbTudo = new JCheckBoxPad("Processar todo estoque (Atenção!)","S","N");
	private JLabelPad lbStatus = new JLabelPad();
	private ListaCampos lcProd = new ListaCampos(this);
	private ListaCampos lcProdFim = new ListaCampos(this);
	boolean bRunProcesso = false;
	int iFilialMov = 0; 
    int iUltProd = 0;
	public FProcessaEQ() {
    	setTitulo("Processamento de estoque");
    	setAtribos(100,100,330,430);
    	
    	Container c = getContentPane();
    	c.setLayout(new BorderLayout());
        c.add(pin,BorderLayout.CENTER);
        
        lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod", ListaCampos.DB_PK, true));
        lcProd.add(new GuardaCampo( txtRefProd, "RefProd", "Referência", ListaCampos.DB_SI,false));
        lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI,false));
        txtCodProd.setTabelaExterna(lcProd);
        txtCodProd.setNomeCampo("CodProd");
        txtCodProd.setFK(true);
        lcProd.setReadOnly(true);
        lcProd.montaSql(false, "PRODUTO", "EQ");

        /*txtCodProdFim.setTipo(JTextFieldPad.TP_INTEGER,8,0);
        txtDescProdFim.setTipo(JTextFieldPad.TP_STRING,40,0);
        txtRefProdFim.setTipo(JTextFieldPad.TP_STRING,13,0);
        lcProdFim.add(new GuardaCampo( txtCodProdFim, 7, 100, 80, 20, "CodProd", "CodProd", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodProdFim");
        lcProdFim.add(new GuardaCampo( txtRefProdFim, 90, 100, 100, 20, "RefProd", "Referência", false, false, null, JTextFieldPad.TP_STRING,false),"txtRefProdFim");
        lcProdFim.add(new GuardaCampo( txtDescProdFim, 90, 100, 207, 20, "DescProd", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescProdFim");
        txtCodProdFim.setTabelaExterna(lcProdFim);
        txtCodProdFim.setNomeCampo("CodProd");
        txtCodProdFim.setFK(true);
        lcProdFim.setReadOnly(true);
        lcProdFim.montaSql(false, "PRODUTO", "EQ"); */
        
        JLabelPad lbAviso = new JLabelPad();
        lbAviso.setForeground(Color.RED);
        lbAviso.setText("<HTML> ATENÇÃO! <BR><BR>"+
        		"Assegure-se que apenas esta estação de trabalho<BR>" +
        		"esteja conectada ao sistema.</HTML>"
        );
        
        pin.adic(lbAviso,10,0,460,150);
        
        pin.adic(new JLabelPad("Apartir de:"),7,160,70,20);
        pin.adic(txtDataini,80,160,107,20);
        pin.adic(new JLabelPad("Cód.prod."),7,180,250,20);
        pin.adic(txtCodProd,7,200,70,20);
        pin.adic(new JLabelPad("Descrição do produto"),80,180,250,20);
        pin.adic(txtDescProd,80,200,220,20);

        /*pin.adic(new JLabelPad("Código e descrição do produto final"),7,200,250,20);
        pin.adic(txtCodProdFim,7,220,70,20);
        pin.adic(txtDescProdFim,80,220,220,20);*/

        if (Aplicativo.strUsuario.toUpperCase().equals("SYSDBA")) {
          pin.adic(cbTudo,7,240,250,30);
          txtCodProd.setRequerido(false);
        }
        pin.adic(btProcessar,10,280,180,30);

    	lbStatus.setForeground(Color.BLUE);
      
        pin.adic(lbStatus,10,320,400,20);

		adicBotaoSair();

        lcProd.addCarregaListener(this);
		btProcessar.addActionListener(this);
        state("Aguardando...");
    }
    private void processarTudo() {
      String sSQL = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      Vector vProds = null;
      if (iUltProd > 0) {
            if (Funcoes.mensagemConfirma(this,"Gostaria de continuar a partir do produto '"+iUltProd+"'?") != JOptionPane.YES_OPTION)
                  iUltProd = 0;
      }
      try {
          sSQL = "SELECT CODPROD FROM EQPRODUTO WHERE ATIVOPROD='S'" +
            " AND CODFILIAL=? AND CODEMP=? AND CODPROD>=?" +
            " ORDER BY CODPROD";
         ps = con.prepareStatement(sSQL);
         ps.setInt(1,ListaCampos.getMasterFilial("EQPRODUTO"));
         ps.setInt(2,Aplicativo.iCodEmp);
         ps.setInt(3,iUltProd);
         rs = ps.executeQuery();
         vProds = new Vector();
         while (rs.next()) {
             vProds.addElement(new Integer(rs.getInt("CodProd")));
         }
         rs.close();
         ps.close();
         if (!con.getAutoCommit())
            con.commit();
         
         for (int i=0; i<vProds.size(); i++) {
             iUltProd =  ( (Integer) vProds.elementAt(i) ).intValue();
             if (!processar(iUltProd)) {
                 if (Funcoes.mensagemConfirma(this,"Ocorreram problemas com o produto: '"+iUltProd+"'.\n" +
                                                   "Deseja continuar mesmo assim?") != JOptionPane.YES_OPTION)
                 	break;
             }
         }
      }
      catch (SQLException err) {
        Funcoes.mensagemErro(this,"Não foi possível processar um produto.\n" +
                                  "Ultimo processado: '"+iUltProd+"'.\n"+err.getMessage());
      }
      finally {
          sSQL = null;
      }
    }
    
    private boolean processar(int iCodProd) {
       String sSQL = null;
       String sSQLCompra = null;
       String sSQLInventario = null;
       String sSQLVenda = null;
       String sWhere = null;
       String sProd = null;
       String sWhereCompra = null;
       String sWhereInventario = null;
       String sWhereVenda = null;
       PreparedStatement ps = null;
       ResultSet rs = null;
       boolean bOK = false;
       
       try {
           try {
               sWhere = "";
               sProd = "";
               if (cbTudo.getVlrString().equals("S"))
                  sProd = "["+iCodProd+"] ";
               if (!(txtDataini.getVlrString().equals(""))) {
                	  sWhere = " AND DTMOVPROD >= '"+Funcoes.dateToStrDB(txtDataini.getVlrDate())+"'";
               }

               sSQL = "DELETE FROM EQMOVPROD WHERE " +
                    "TIPOMOVPROD='S' AND CODEMP=? AND CODFILIAL=? " +
                    "AND CODPROD=?"+sWhere;
             	 state(sProd+"Limpando saídas desatualizadas...");
             	 ps = con.prepareStatement(sSQL);
             	 ps.setInt(1,Aplicativo.iCodEmp);
             	 ps.setInt(2,iFilialMov);
             	 ps.setInt(3,iCodProd);
             	 ps.executeUpdate();
             	 ps.close();
             	 state(sProd+"Limpando inventários desatualizados...");
             	 sSQL = "DELETE FROM EQMOVPROD WHERE " +
             	 	"CODEMP=? AND CODFILIAL=? " +
             	 	"AND CODPROD=? AND QTDMOVPROD<0"+sWhere;
             	 ps = con.prepareStatement(sSQL);
             	 ps.setInt(1,Aplicativo.iCodEmp);
             	 ps.setInt(2,iFilialMov);
             	 ps.setInt(3,iCodProd);
             	 ps.executeUpdate();
             	 ps.close();
             	 state(sProd+"Limpando entradas desatualizadas...");
             	 	sSQL = "DELETE FROM EQMOVPROD WHERE " +
             	 	"CODEMP=? AND CODFILIAL=? " +
             	 	"AND CODPROD=?"+sWhere;
             	 ps = con.prepareStatement(sSQL);
             	 ps.setInt(1,Aplicativo.iCodEmp);
             	 ps.setInt(2,iFilialMov);
             	 ps.setInt(3,iCodProd);
             	 ps.executeUpdate();
             	 ps.close();
             	 state(sProd+"Estoques desatualizados excluidos.");
             	 bOK = true;
             }
             catch (SQLException err) {
              	 Funcoes.mensagemErro(this,"Erro ao limpar estoques!\n"+err.getMessage());
             }
             if (bOK) {
             	 bOK = false;
             	 if (!txtDataini.getVlrString().equals("")) {
             	     sWhereCompra = " AND C.DTENTCOMPRA >= '"+Funcoes.dateToStrDB(txtDataini.getVlrDate())+"'";
             	     sWhereInventario = " AND I.DATAINVP >= '"+Funcoes.dateToStrDB(txtDataini.getVlrDate())+"'";
             	     sWhereVenda = " AND V.DTEMITVENDA >= '"+Funcoes.dateToStrDB(txtDataini.getVlrDate())+"'";
             	 }
             	 else {
                 	 sWhereCompra = "";
                 	 sWhereInventario = "";
                 	 sWhereVenda = "";
             	 }

             	 sSQLInventario = "SELECT 'A',I.CODEMPPD,I.CODFILIALPD,I.CODPROD," +
             	 				"I.CODEMPLE,I.CODFILIALLE,I.CODLOTE," +
             	 				"I.CODEMPTM,I.CODFILIALTM,I.CODTIPOMOV,"+
             	 				"I.CODEMP,I.CODFILIAL,CAST(NULL AS CHAR(1)),I.CODINVPROD,I.CODINVPROD,"+
             	 				"CAST(NULL AS INTEGER),CAST(NULL AS SMALLINT),CAST(NULL AS CHAR(4)),"+
             	 				"I.DATAINVP,I.CODINVPROD,'N'," +
             	 				"I.QTDINVP,I.PRECOINVP "+
             	 				"FROM EQINVPROD I " +
             	 				"WHERE I.CODEMP=? AND I.CODFILIAL=? AND " +
             	 				"I.CODPROD = ?"+sWhereInventario;
             	 sSQLCompra = "SELECT 'C',IC.CODEMPPD,IC.CODFILIALPD,IC.CODPROD," +
             	 				"IC.CODEMPLE,IC.CODFILIALLE,IC.CODLOTE," +
             	 				"C.CODEMPTM,C.CODFILIALTM,C.CODTIPOMOV," +             	 				
             	 				"C.CODEMP,C.CODFILIAL,CAST(NULL AS CHAR(1)),C.CODCOMPRA,IC.CODITCOMPRA,"+
                                "IC.CODEMPNT,IC.CODFILIALNT,IC.CODNAT,"+
                                "C.DTENTCOMPRA,C.DOCCOMPRA,C.FLAG," +
                                "IC.QTDITCOMPRA,IC.CUSTOITCOMPRA "+
                                "FROM CPCOMPRA C,CPITCOMPRA IC " +
                                "WHERE IC.CODCOMPRA=C.CODCOMPRA AND "+
                                "IC.CODEMP=C.CODEMP AND IC.CODFILIAL=C.CODFILIAL AND "+
                                "IC.QTDITCOMPRA > 0 AND "+
                                "C.CODEMP=? AND C.CODFILIAL=? AND IC.CODPROD = ?"+sWhereCompra;
                 sSQLVenda = "SELECT 'V',IV.CODEMPPD,IV.CODFILIALPD,IV.CODPROD," +
                 				"IV.CODEMPLE,IV.CODFILIALLE,IV.CODLOTE," +
                 				"V.CODEMPTM,V.CODFILIALTM,V.CODTIPOMOV," +
                 				"V.CODEMP,V.CODFILIAL,V.TIPOVENDA,V.CODVENDA,IV.CODITVENDA,"+
                                "IV.CODEMPNT,IV.CODFILIALNT,IV.CODNAT," +
                                "V.DTEMITVENDA,V.DOCVENDA,V.FLAG," +
                                "IV.QTDITVENDA,IV.VLRLIQITVENDA " +
                                "FROM VDVENDA V ,VDITVENDA IV " +
                                "WHERE IV.CODVENDA=V.CODVENDA AND "+
                                "IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND "+
                                "IV.QTDITVENDA > 0 AND "+
                                "V.CODEMP=? AND V.CODFILIAL=? AND IV.CODPROD = ?"+sWhereVenda;
                 try {
             	    state(sProd+"Iniciando reconstrução...");
             	    sSQL = sSQLInventario+" UNION "+sSQLCompra+" UNION "+sSQLVenda+" ORDER BY 19,1,20";// 1 POR QUE C-Compra,I-Inventario,V-Venda
             	    System.out.println(sSQL);
             	    ps = con.prepareStatement(sSQL);
             	    ps.setInt(1,Aplicativo.iCodEmp);
             	    ps.setInt(2,ListaCampos.getMasterFilial("CPCOMPRA"));
             	    ps.setInt(3,iCodProd);
             	    ps.setInt(4,Aplicativo.iCodEmp);
             	    ps.setInt(5,ListaCampos.getMasterFilial("EQINVPROD"));
             	    ps.setInt(6,iCodProd);
             	    ps.setInt(7,Aplicativo.iCodEmp);
             	    ps.setInt(8,ListaCampos.getMasterFilial("VDVENDA"));
             	    ps.setInt(9,iCodProd);
             	    rs = ps.executeQuery();
             	    bOK = true;
             	    while (rs.next() && bOK) {
             	 	   bOK = insereMov(rs,sProd);
             	    }
             	    rs.close();
             	    ps.close();
             	    state(sProd+"Aguardando gravação final...");
                 }
                 catch (SQLException err) {
                    bOK = false;
                    Funcoes.mensagemErro(this,"Erro ao reconstruir base!\n"+err.getMessage());
                 }
             }
             try {
                 if (bOK) {
                 	if (!con.getAutoCommit())
                 		con.commit();
                	    state(sProd+"Registros processados com sucesso!");
                 }
                 else { 
             	    state(sProd+"Registros antigos restaurados!");
             	    con.rollback();
                 }
             }
             catch (SQLException err) {
               Funcoes.mensagemErro(this,"Erro ao relizar procedimento!\n"+err.getMessage());
             }
           
       }
       finally {
           sSQL = null;
           sSQLCompra = null;
           sSQLInventario = null;
           sSQLVenda = null;
           sWhere = null;
           sProd = null;
           sWhereCompra = null;
           sWhereInventario = null;
           sWhereVenda = null;
           rs = null;
           ps = null;
           bRunProcesso = false;
           btProcessar.setEnabled(true);
       }
       return bOK;
    } 
    private boolean insereMov(ResultSet rs,String sProd) {
        /*   Parâmetros da procedure de reconstrução da tabela EQMOVPROD
         *    CIUD CHAR(1), ICODEMPPD INTEGER, SCODFILIALPD SMALLINT, ICODPROD INTEGER,
         *   ICODEMPLE INTEGER, SCODFILIALLE SMALLINT, CCODLOTE CHAR(13), ICODEMPTM INTEGER,
         *   SCODFILIALTM SMALLINT, ICODTIPOMOV INTEGER, 
         *   11
         *   ICODEMPIV INTEGER,  SCODFILIALIV SMALLINT, ICODINVPROD INTEGER,
         *   14
         *   ICODEMPCP INTEGER, SCODFILIALCP SMALLINT, ICODCOMPRA INTEGER, SCODITCOMPRA SMALLINT,
         *   18
         *   ICODEMPVD INTEGER, SCODFILIALVD SMALLINT, CTIPOVENDA CHAR(1), ICODVENDA INTEGER, SCODITVENDA SMALLINT,
         *   23
         *   ICODEMPRM INTEGER,  SCODFILIALRM SMALLINT, ICODRMA INTEGER, SCODITRMA SMALLINT,
         *   27 
         *   ICODEMPNT INTEGER, SCODFILIALNT SMALLINT, CCODNAT CHAR(4),
         *   30 
         *   DDTMOVPROD DATE, IDOCMOVPROD INTEGER, CFLAG CHAR(1), 
         *   NQTDMOVPROD NUMERIC(15,3), NPRECOMOVPROD NUMERIC(15,3))
         */
    	boolean bRet = false;
    	String sSQL = null;
    	String sCIV = null;
    	PreparedStatement ps = null;
    	double dePrecoMovprod = 0;
    	try {
    	    sSQL =  "EXECUTE PROCEDURE EQMOVPRODIUDSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
    	    										 "?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    		state(sProd+"Processando dia: "+Funcoes.sqlDateToStrDate(rs.getDate(19))+" Doc: ["+rs.getInt(20)+"]");
    		ps = con.prepareStatement(sSQL);
    		sCIV = rs.getString(1); // tipo COMPRA, INVENTARIO, VENDA
    		ps.setString(1,"I");
    		ps.setInt(2,rs.getInt(2)); //CodEmpPD
    		ps.setInt(3,rs.getInt(3)); //CodFilialPD
    		ps.setInt(4,rs.getInt(4)); //CodProd
    		if (rs.getString(7) != null) {
      		  ps.setInt(5,rs.getInt(5)); //CodEmpLE
      		  ps.setInt(6,rs.getInt(6)); //CodFilialLE
      		  ps.setString(7,rs.getString(7)); //CodLote
      		}
      		else {
      			ps.setNull(5,Types.INTEGER); //CodEmpLE
      			ps.setNull(6,Types.INTEGER); //CodFilialLE
      			ps.setNull(7,Types.CHAR); //CodLote
      		}
  			ps.setInt(8,rs.getInt(8)); //CodEmpTM
  			ps.setInt(9,rs.getInt(9)); //CodFilialTM
  			ps.setInt(10,rs.getInt(10)); //CodTipoMov
    		if (sCIV.equals("A")) {
      			ps.setInt(11,rs.getInt(11)); //CodEmpIv
      			ps.setInt(12,rs.getInt(12)); //CodFilialIv
      			ps.setInt(13,rs.getInt(14)); //CodInvProd
    		}
    		else {
      			ps.setNull(11,Types.INTEGER); //CodEmpIv
      			ps.setNull(12,Types.INTEGER); //CodFilialIv
      			ps.setNull(13,Types.INTEGER); //CodInvProd
    		}
  			if (sCIV.equals("C")) {
      			ps.setInt(14,rs.getInt(11)); //CodEmpCp
      			ps.setInt(15,rs.getInt(12)); //CodFilialCp
      			ps.setInt(16,rs.getInt(14)); //CodCompra
      			ps.setInt(17,rs.getInt(15)); //CodItCompra
    		}
    		else {
      			ps.setNull(14,Types.INTEGER); //CodEmpCp
      			ps.setNull(15,Types.INTEGER); //CodFilialCp
      			ps.setNull(16,Types.INTEGER); //CodCompra
      			ps.setNull(17,Types.INTEGER); //CodItCompra
    		}
  			if (sCIV.equals("V")) {
      			ps.setInt(18,rs.getInt(11)); //CodEmpVd
      			ps.setInt(19,rs.getInt(12)); //CodFilialVd
      			ps.setString(20,rs.getString(13)); //TipoVenda
      			ps.setInt(21,rs.getInt(14)); //CodVenda
      			ps.setInt(22,rs.getInt(15)); //CodItVenda
    		}
    		else {
      			ps.setNull(18,Types.INTEGER); //CodEmpVd
      			ps.setNull(19,Types.INTEGER); //CodFilialVd
      			ps.setNull(20,Types.CHAR); //TipoVenda
      			ps.setNull(21,Types.INTEGER); //CodVenda
      			ps.setNull(22,Types.INTEGER); //CodItVenda
    		}
  			ps.setNull(23,Types.INTEGER); //CodEmpRm
  			ps.setNull(24,Types.INTEGER); //CodFilialRm
  			ps.setNull(25,Types.INTEGER); //CodRma
  			ps.setNull(26,Types.INTEGER); //CodItRma
  			
  			if (rs.getString(18)!=null) {
  			    ps.setInt(27,rs.getInt(16)); // CodEmpNt
  			    ps.setInt(28,rs.getInt(17)); // CodFilialNt
  			    ps.setString(29,rs.getString(18)); // CodNat
  			}
  			else {
  			    ps.setNull(27,Types.INTEGER); // CodEmpNt
  			    ps.setNull(28,Types.INTEGER); // CodFilialNt
  			    ps.setNull(29,Types.CHAR); // CodNat
  			}
  			
  			ps.setDate(30, rs.getDate(19)); // dtMovProd
  			ps.setInt(31,rs.getInt(20)); // docMovProd
  			ps.setString(32,rs.getString(21)); // Flag
  			ps.setDouble(33,rs.getDouble(22)); // QtdMovProd
  			if (sCIV.equals("V")) {
  				if (rs.getDouble(22)>0)
    				dePrecoMovprod = rs.getDouble(23) / rs.getDouble(22);
  				else
  					dePrecoMovprod = 0;
  			}
  			else {
  				dePrecoMovprod = rs.getDouble(23);
  			}
			ps.setDouble(34,dePrecoMovprod); // PrecoMovProd
    		ps.executeUpdate();
   		    ps.close();
    		bRet = true;
    	}
    	catch (SQLException err) {
    		Funcoes.mensagemErro(this,"Erro ao inserir novo movimento!\n"+err.getMessage());
    		//err.printStackTrace();
    	}
    	catch (Exception e) {
    		Funcoes.mensagemErro(this,"Erro ao inserir novo movimento!\n"+e.getMessage());
    	}
    	finally {
        	sSQL = null;
        	sCIV = null;
        	ps = null;
    	}
    	return bRet;
    }
	public void actionPerformed(ActionEvent evt) {
		 if (evt.getSource() == btProcessar) {
            if (cbTudo.getVlrString().equals("S")) {
                if (Funcoes.mensagemConfirma(this,"ATENÇÂO!!!\n" +
                                                  "Esta operação exige um longo tempo e muitos recursos do banco de dados,\n" +
                                                  "assegure-se que NINGUÉM esteja conectado ao banco de dados em outra \n" +
                                                  "estação de trabalho. Deseja continuar?") != JOptionPane.YES_OPTION)
                	return;
            }
		 	else if (txtCodProd.getVlrString().equals("")) {
		 		Funcoes.mensagemInforma(this,"Código do produto em branco!");
		 		return;
		 	}
		 	ProcessoSec pSec = new ProcessoSec(100,
			  new Processo() {
				public void run() {
				  lbStatus.updateUI();
				}
			  },
			  new Processo() {
				public void run() {
                  if (cbTudo.getVlrString().equals("S"))
				    processarTudo();
                  else {
                    iUltProd = txtCodProd.getVlrInteger().intValue();
                    processar(iUltProd);
                  }
				}
			  }
			);
			bRunProcesso = true;
			btProcessar.setEnabled(false);
			pSec.iniciar();
		 }
	}
	public void state(String sStatus) {
		lbStatus.setText(sStatus);
//        System.out.println(sStatus);
	}
    public void setConexao(Connection cn) {
    	super.setConexao(cn);
    	lcProd.setConexao(cn);
    	iFilialMov = ListaCampos.getMasterFilial("EQMOVPROD");
    }
	public void beforeCarrega(CarregaEvent cevt) {
	}
	public void afterCarrega(CarregaEvent cevt) {
        if (cevt.ok && cevt.getListaCampos() == lcProd)
            iUltProd = txtCodProd.getVlrInteger().intValue();
	}
}
