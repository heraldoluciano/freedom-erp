/**
 * @version 25/09/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)FImportaCto.java <BR>
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
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.tmk;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;



/**
 * @author robson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FImportaCto extends FFilho implements ActionListener,FocusListener {
	private JTextFieldPad txtCodTipo = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescTipo = new JTextFieldFK(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtArqOrig = new JTextFieldPad();
	private JTextFieldPad txtArqDest = new JTextFieldPad();
	private JButton btBuscaOrig = new JButton(Icone.novo("btAbrirPeq.gif"));
	private JButton btBuscaDest = new JButton(Icone.novo("btAbrirPeq.gif"));
	private JButton btGerar = new JButton("Gerar",Icone.novo("btGerar.gif"));
	private JPanelPad pinGeral = new JPanelPad(400,200);
	private JProgressBar pbAnd = new JProgressBar();
	private ListaCampos lcTipoImp = new ListaCampos(this,"TK");
	private JLabel lAndamento = new JLabel("Andamento");
	private Timer tim = null;

    public FImportaCto() {
    	setTitulo("Importação de Contatos");
    	setAtribos(100,100,380,280);
    	//setSize(380,260);
    	Container c = getContentPane();
		c.add(pinGeral);
		//setPainel(pinGeral);
		
		txtCodTipo.setRequerido(true);
		txtCodTipo.setTipo(JTextFieldPad.TP_INTEGER,8,0);
		txtDescTipo.setTipo(JTextFieldPad.TP_STRING,40,0);
		lcTipoImp.add(new GuardaCampo( txtCodTipo, "CodTpImp", "Cód.tp.imp.", ListaCampos.DB_PK, false),"txtCodProd");
		lcTipoImp.add(new GuardaCampo( txtDescTipo, "DescTpImp", "Descrição do tipo de importação", ListaCampos.DB_SI, false),"txtCodProd");
		txtCodTipo.setTabelaExterna(lcTipoImp);
		txtCodTipo.setNomeCampo("CodProd");
		txtCodTipo.setFK(true);
		lcTipoImp.setReadOnly(true);
		lcTipoImp.montaSql(false, "TIPOIMP", "TK");
		
		pinGeral.adic(new JLabel("Cód.tp.imp."),7,0,290,20);
		pinGeral.adic(txtCodTipo,7,20,80,20);
		pinGeral.adic(new JLabel("Descrição do tipo de importação"),90,0,290,20);
		pinGeral.adic(txtDescTipo,90,20,250,20);
		pinGeral.adic(new JLabel("Arquivo de origem"),7,40,200,20);
		pinGeral.adic(txtArqOrig,7,60,313,20);
		pinGeral.adic(btBuscaOrig,320,60,20,20);
		pinGeral.adic(new JLabel("Arquivo de destino"),7,80,200,20);
		pinGeral.adic(txtArqDest,7,100,313,20);
		pinGeral.adic(btBuscaDest,320,100,20,20);
		pinGeral.adic(btGerar,7,128,100,30);
		pinGeral.adic(lAndamento,7,160,150,20);
		pinGeral.adic(pbAnd,7,180,330,20);
		adicBotaoSair();
		btBuscaOrig.addActionListener(this);
		btBuscaDest.addActionListener(this);
		btGerar.addActionListener(this);
		txtCodTipo.addFocusListener(this);
		habDest(false);
		pbAnd.setStringPainted(true);

    }
    public void setConexao(Connection cn) {
    	super.setConexao(cn);
    	lcTipoImp.setConexao(cn);
    }
    private void habDest(boolean bHab) {
    	txtArqDest.setEnabled(bHab);
    	btBuscaDest.setEnabled(bHab);
    }
    private String buscaArq() {
    	String sRetorno = "";
		JFileChooser fcImagem = new JFileChooser();
		if (fcImagem.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			sRetorno = fcImagem.getSelectedFile().getPath();
		}
		return sRetorno;
    }
	public void actionPerformed(ActionEvent evt) {
		 String sArq = "";
		 if (evt.getSource() == btBuscaOrig) {
			sArq = buscaArq();
			if (!sArq.equals("")) {
				txtArqOrig.setText(sArq);
			}
		 }
		 else if (evt.getSource() == btBuscaDest) {
			sArq = buscaArq();
			if (!sArq.equals("")) {
				txtArqDest.setText(sArq);
			}
		 }
		 else if (evt.getSource() == btGerar) {
		 	importar();
		 }
		 
	}
	private void importar() {
		String sCodTipo = txtCodTipo.getVlrString();
		String sArqOrig = txtArqOrig.getVlrString();
		String sArqDest = txtArqDest.getVlrString();
		String sTransBinTpImp = "";
		if (sCodTipo.trim().equals("")) {
			Funcoes.mensagemInforma(this,"Selecione o tipo de importação!");
			txtCodTipo.requestFocus();
		}
		else if (sArqOrig.trim().equals("")) {
			Funcoes.mensagemInforma(this,"Selecione o arquivo de origem!");
			txtArqOrig.requestFocus();
		}
		else if (!sCodTipo.equals("")) {
			sTransBinTpImp = getTransBin(sCodTipo);
			if ( (sTransBinTpImp.equals("S")) && (sArqDest.trim().equals("")) ) {
				Funcoes.mensagemInforma(this,"Selecione o arquivo de destino!");
				habDest(true);
				txtArqDest.requestFocus();
			}
			else if (Funcoes.mensagemConfirma(this,"Confirma importação?")==JOptionPane.YES_OPTION) {
				try {
					this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					if (sTransBinTpImp.equals("S")) {
						Thread th = new Thread(
						  new Runnable() {
							public void run() {
							  if (convArq()) 
							  	impArq(false);
							}
						  }
						);
						try {
						  th.start();
						}
						catch(Exception err) {
							Funcoes.mensagemErro(this,"Não foi possível criar processo!\n"+err.getMessage());
						}
					}
					else {
						Thread th = new Thread(
						  new Runnable() {
							public void run() {
								impArq(true);
							}
						  }
						);
						try {
						  th.start();
						}
						catch(Exception err) {
							Funcoes.mensagemErro(this,"Não foi possível criar processo!\n"+err.getMessage());
						}
					}
				}
				catch (Exception e) {
					Funcoes.mensagemConfirma(this,"Erro executando importação\n"+e.getMessage());
					e.printStackTrace();
				}
				finally {
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		}
	}

    private boolean impArq(boolean bOrigem) {
    	boolean bRetorno = true;
    	String sArq = "";
    	String sLabelAnt = "";
		File fOrig = null;
		FileReader frOrig = null;
		int iSep = ';';
		int iCarac = 0;
		String sColuna = "";
		Vector vColunas = null;
		
		try {
			btGerar.setEnabled(false);
			sLabelAnt = lAndamento.getText();
			lAndamento.setText("Convertendo arquivo...");
			tim = new Timer(300,this);
			pbAnd.setMinimum(0);
			pbAnd.setValue(0);
			pbAnd.setMaximum((int) fOrig.length());
			try {
				if (bOrigem) 
					sArq = txtArqOrig.getVlrString();
				else 
					sArq = txtArqDest.getVlrString();
				fOrig = new File(sArq);
				frOrig = new FileReader(fOrig);
				vColunas = new Vector(); 
				for (int i=0; i<fOrig.length(); i++) {
				   iCarac = frOrig.read();
				   if (iCarac==-1) 
				   	  break;
				   if (iCarac==iSep) {
				   	  vColunas.addElement(sColuna);
				   	  sColuna = "";
				   }
				   else if (iCarac==13) {
				   	  vColunas.addElement(sColuna);
				   	  gravaBanco();
				   	  sColuna = "";
				   }
				   else if (iCarac!=10) {
					  sColuna += (char) iCarac;
				   } 
				   pbAnd.setValue(i);
				}				
			
			}
			catch (Exception e) {
				Funcoes.mensagemErro(this,"Erro importando arquivo!\n"+e.getMessage());
				bRetorno = false;
				e.printStackTrace();
			}		
			
		}
		finally {
			btGerar.setEnabled(true);
			lAndamento.setText(sLabelAnt);
			if (bRetorno) 
			   Funcoes.mensagemInforma(this,"Importação concluída!");
			if (tim!=null) {
				tim.stop();
			}
			
		}

    	return bRetorno;
    }
    private boolean gravaBanco() {
    	boolean bRetorno = true;
    	return bRetorno;
    }
 /*   private Vector preparaDados(Vector vColunas) {
    	for (int i=0; i<vColunas.size(); i++) {
    		if (i==0)
    			vColunas.setElementAt(Funcoes.adicionaEspacos(vColunas.elementAt(i).toString(),50),i);
    		else if (i==1)
    			vColunas.setElementAt(Funcoes.adicionaEspacos(vColunas.elementAt(i).toString(),50),i);
			else if (i==2)
				vColunas.setElementAt(Funcoes.adicionaEspacos(vColunas.elementAt(i).toString(),50),i);
			else if (i==3)
				vColunas.setElementAt(Funcoes.adicionaEspacos(vColunas.elementAt(i).toString(),50),i);
			else if (i==4)
				vColunas.setElementAt(Funcoes.adicionaEspacos(vColunas.elementAt(i).toString(),50),i);
			else if (i==5)
				vColunas.setElementAt(Funcoes.adicionaEspacos(vColunas.elementAt(i).toString(),50),i);
			else if (i==6)
				vColunas.setElementAt(Funcoes.adicionaEspacos(vColunas.elementAt(i).toString(),50),i);
			else if (i==7)
				vColunas.setElementAt(Funcoes.adicionaEspacos(vColunas.elementAt(i).toString(),50),i);
    	}
    	return vColunas;
    } */
	private boolean convArq() {
	    String sArqOrig = txtArqOrig.getVlrString();
	    String sArqDest = txtArqDest.getVlrString();
	    String sColunaTmp = "";	
		boolean bRetorno = true;
		boolean bInicio = true;
		boolean bCInvalido = false;
		boolean bCompletou = false;
		boolean bLoop = false;
		//boolean bGravaLinha = false;
		//boolean bTestaColuna = false;
		String sLabelAnt = "";
		String sLinha = "";
		String sColuna = "";
		Vector vColunas = new Vector();
		Vector vSel = null;
//		int iSobra = 0;
		int iColunas = 0;
		int iCarac = 0;
		int iReduz = 0;
		int iCaracAnt = 0;
		int iPos2 = 0;
		int iSep = ';';

		try {
			btGerar.setEnabled(false);
			File fOrig = new File(sArqOrig);
			File fDest = new File(sArqDest);
			FileReader frOrig = null;
			FileWriter fwDest = null;
			//int iQtdSep = 0;
			if (!fOrig.exists()) {
				Funcoes.mensagemInforma(this,"Arquivo de origem não encontrado!");
				bRetorno = false;
			}
			else {
				if (fDest.exists()) {
					if (Funcoes.mensagemConfirma(this,"Arquivo de destino já existe! Sobscrever?")!=JOptionPane.YES_OPTION) {
						bRetorno = false;
					}
				    else {
				    	fDest.delete();
				    }
				}
				if (bRetorno) {
					try {
						frOrig = new FileReader(fOrig);
						fwDest = new FileWriter(fDest);
						sLabelAnt = lAndamento.getText();
						lAndamento.setText("Convertendo arquivo...");
						tim = new Timer(300,this);
						pbAnd.setMinimum(0);
						pbAnd.setValue(0);
						pbAnd.setMaximum((int) fOrig.length());
						tim.start();
						for (int i=0; i<fOrig.length(); i++) {
							if (sColuna.length()==2) {
								if ("1 ;2 ;3 ;4 ;5 ;6 ;7 ;8 ;9 ".indexOf(sColuna)>-1) {
									sColuna = "";
								}
							}
							else if (sColuna.length()==4) { 
								if ( (sColuna.equals("001 ")) || (sColuna.equals("002 ")) || (sColuna.equals("003 ")) ) {
									sColuna = "";
								}
							}
							iCarac = frOrig.read();
							if (iCarac==-1) {
								break;
							}
							iCaracAnt = iCarac;
							iCarac -= 127;
							if ( iCarac<32 ) {
							    iCarac = iCaracAnt;
							}
							if ( iCarac>126 ) {
								iCarac = 32;
							}
							else if ( iCarac<32 ) {
								iCarac = 0;
								bCInvalido = true;
							} 
							else if ( "\"#;*!%$]`()'´".indexOf( (char) iCarac) >-1 ) {
								iCarac = 0;
							} 
							else if ( ( (char) iCarac)=='{') {
								iCarac = 'u';
							}
							else if ( ( (char) iCarac)=='[') {
								iCarac = 'U';
							}

							if (iCarac!=0) {
								if (!bInicio) { 
								    bInicio = true;
								    bCInvalido = false;
								}
								else {
									if (bCInvalido) {
										bCInvalido = false;
	 								    sColuna = sColuna.trim() + (char) iSep;
										if (colunaValida(sColuna)) {
											vColunas.addElement(sColuna);
											//sLinha += sColuna;
											sColuna = "";
											iColunas++;
										}
										else {
											sColuna = "";
										}
									}
								}
								sColuna += (char) iCarac;
							}
							if (iColunas==28) {
								sLinha = "";
								bCompletou = false;
								vSel = new Vector();
								iColunas=0;
								sColunaTmp = "";
//								iSobra = 0;
								iPos2 = 0;
							
								for (int iPos=0; iPos<vColunas.size(); iPos++ ) {
									bLoop = false;
									sColunaTmp = vColunas.elementAt(iPos).toString();
									/*if (sColunaTmp.length()>7) {
										if (sColunaTmp.substring(0,8).equals("XIANGFEN")) {
											Funcoes.mensagemInforma(this,"XIANGFEN");
										}
									}*/
										 
									if (bCompletou) {
										for (int iComp=iPos-iReduz; iComp<vColunas.size(); iComp++) {
											sColunaTmp = vColunas.elementAt(iComp).toString();
											iColunas ++;
											vSel.add(sColunaTmp);
										}
										break;
									}
									if (iPos2==0) { // Razão Social 
									  if (eh70Alta(sColunaTmp)) {
									    bLoop = false;
									  } 
									  else { 
									    bLoop = true;
									  }
									}
									if (!bLoop) {
									  if (iPos2==1) { // Nome Fantasia
									    if (ehEndereco(sColunaTmp)) {
									      sLinha += "NULL;";
									      iPos2++;
									    }
									  }
									  if (iPos2==2) { // Endereço 
									    if (!ehEndereco(sColunaTmp)) {
									      sLinha += "NULL;";
									      iPos2++;
									    }
									  }
									  if (iPos2==3) { // Complemento 
									    if ( (!ehUF(vColunas.elementAt(iPos+3).toString())) &&
									        ( (!ehCEP(vColunas.elementAt(iPos+3).toString()))) ) { 
									      sLinha += "NULL;";
									      iPos2++;
									    }
									  }
									  if (iPos2==4) { // Cidade
									    if ( ehFone(sColunaTmp) ) {
									      sLinha += "NULL;NULL;NULL;NULL;";
									      iPos2+=4;
									    }
									  }
									  if (iPos2==5) { // Bairro 
									    if (ehUF(sColunaTmp)) {
									      sLinha += "NULL;";
									      iPos2++;
									    }
									  }
									  if (iPos2==6) { // UF 
									    if (!ehUF(sColunaTmp)) {
									      if (ehCEP(sColunaTmp)) {
									        sLinha += "NULL;";
									        iPos2++;
									      }
									      else if (ehFone(sColunaTmp)) {
									        sLinha += "NULL;NULL;";
									        iPos2+=2;
									      }
									    }
									  }
									  if (iPos2==7) { // CEP 
									    if (!ehCEP(sColunaTmp)) {
									      sLinha += "NULL;";
									      iPos2++;
									    }
									  }
									  if (iPos2==8) { // Fone 
									    if (!ehFone(sColunaTmp)) {
									      sLinha += "NULL;";
									      iPos2++;
									    }
									  }
									  if (iPos2==9) { // Fax
									    if (!ehFone(sColunaTmp)) {
									      sLinha += "NULL;";
									      iPos2++;
									    }
									  }
									  if (iPos2==10) { // End. Cob 
									    if (!ehEndereco(sColunaTmp)) {
									      sLinha += "NULL;";
									      iPos2++;
									    }
									  }
									  if (iPos2==11) { // Cnpj 
									    if (!ehCnpj(sColunaTmp)) {
									      sLinha += "NULL;";
									      iPos2++;
									    }
									  }
									  if (iPos2==12) { // e-mail 
									    if (!ehEmail(sColunaTmp)) {
									      sLinha += "NULL;";
									      iPos2++;
									    }
									  }
									  if (iPos2==13) { // Pagina 
									    if (!ehPagina(sColunaTmp)) {
									      sLinha += "NULL;";
									      iPos2++;
									    }
									  }
									  if (iPos2==14) { // Ramo 
									    if ( (!eh70Alta(vColunas.elementAt(iPos+1).toString())) && 
									        (!ehFone(vColunas.elementAt(iPos+1).toString()))) {
									      sLinha += "GERAL";
									      iReduz = 1;
									    }
									    else {
									      sColunaTmp = sColunaTmp.trim();
									      sColunaTmp = sColunaTmp.substring(0,sColunaTmp.length()-1);
									      sLinha += sColunaTmp;
									      iPos2++;
									      iReduz = 0;
									    }
									    bCompletou = true;
									  }
									  if (!bCompletou) {
									    sLinha += sColunaTmp;
									    iPos2 ++;
									  }
									}
								}
								vColunas = new Vector();
								for (int iPos=0; iPos<vSel.size(); iPos++) {
									sColunaTmp = vSel.elementAt(iPos).toString();
									vColunas.add(sColunaTmp);
								}
								fwDest.write(sLinha+"\n");
							}

							pbAnd.setValue(i);
						}
						fwDest.flush();
						fwDest.close();
						frOrig.close();
					}
					finally {
						btGerar.setEnabled(true);
						if (tim!=null) {
							tim.stop();
						}
						lAndamento.setText(sLabelAnt);
						Funcoes.mensagemInforma(this,"Importação concluída!");
					}
					
				}
			}
		}
		catch (Exception e) {
		    Funcoes.mensagemErro(this,"Erro convertendo arquivo!\n"+e.getMessage());
			bRetorno = false;
			e.printStackTrace();
		}
		return bRetorno;
	}
	
	private boolean eh70Alta(String sTexto) {
		boolean bRetorno = false;
		int iTam = 0;
		int iAlta = 0;
		char cChar = ' '; 
		sTexto = sTexto.trim();
		iTam = sTexto.length()-1;
		for (int i=0; i<iTam; i++ ) {
			cChar = sTexto.charAt(i);
			if (" .&".indexOf(cChar)>-1) {
				iAlta ++;
			}
			else if ( ( (cChar)>64 ) && ((cChar)<91 ) ) {
				iAlta ++;
			}  
		}
		if ( iTam>0 ) {
			if ( (iAlta*100/iTam)>=70) {
				bRetorno = true;
			}
		}
		
		return bRetorno;
	}
	private boolean ehCnpj(String sCnpj) {
		boolean bRetorno = false;
		sCnpj = sCnpj.trim();
		if (sCnpj.length()>16) {
			if ( sCnpj.charAt(10)=='/' ) {
				bRetorno = true;
			} 
		}
		return bRetorno;
	}
	private boolean ehEmail(String sEmail) {
		boolean bRetorno = false;
		if (sEmail.indexOf('@')>-1) {
			bRetorno = true;
		}
		return bRetorno;
	}
	private boolean ehPagina(String sPagina) {
		boolean bRetorno = false;
		if ((sPagina.indexOf("www")>-1) || (sPagina.indexOf(".com")>-1)) {
			bRetorno = true;
		}
		return bRetorno;
	}
	private boolean ehFone(String sFone) {
		boolean bRetorno = false;
		sFone = sFone.trim();
		if (sFone.indexOf("0xx")>-1) {
			bRetorno = true;
		}
		else if (sFone.toUpperCase().indexOf("FONE")>-1) {
			bRetorno = true;
		}
		else if (sFone.toUpperCase().indexOf("FAX")>-1) {
			bRetorno = true;
		}
		return bRetorno;
	}
	private boolean ehCEP(String sCEP) {
		boolean bRetorno = false;
		sCEP = sCEP.trim();
		if (sCEP.length()>6) {
  		   if (sCEP.charAt(5)=='-') {
  		   	  bRetorno = true;
  		   }
  		   else if (sCEP.substring(0,4).equals("CEP:")) {
  		   	  bRetorno = true;
  		   }
		}
		return bRetorno;
	}
	private boolean ehUF(String sUF) {
		sUF = sUF.trim();
		int iChar1 = 0;
		int iChar2 = 0;
		boolean bRetorno = false;
		if ( (sUF.length()>=3) && (sUF.length()<=4) ) {
			iChar1 = sUF.charAt(0);
			iChar2 = sUF.charAt(1);
			if ( (iChar1>64) && (iChar1<91) && (iChar2>64) && (iChar2<91) ) {
				if ("AC-AL-AM-BA-CE-DF-ES-GO-MS-MT-PA-PE-PI-PB-PR-RJ-RN-RO-RR-RS-SC-SE-SP-TO".indexOf(""+iChar1+iChar2)==-1) {
					bRetorno = false;
				}
				else {
				    bRetorno = true;
				}
			}
			else {
				bRetorno = false;
			}
		}
		else {
			bRetorno = false;
		} 
		return bRetorno;
	}
	private boolean ehEndereco(String sEnd) {
		boolean bRetorno = false;
		String sTexto = sEnd.trim();
		if (sTexto.length()>6) {
		  if ( (sTexto.substring(0,3).trim().toUpperCase().equals("RUA")) ){
		     bRetorno = true;	
		  }
		  else if ( (sTexto.substring(0,3).trim().toUpperCase().equals("AV.")) ){
             bRetorno = true;		  	
		  }
		  else if ( (sTexto.substring(0,2).trim().toUpperCase().equals("BR")) ){
			 bRetorno = true;		  	
		  }
		  else if ( (sTexto.substring(0,7).trim().toUpperCase().equals("AVENIDA")) ){
			 bRetorno = true;		  	
		  }
		  else if ( (sTexto.substring(0,7).trim().toUpperCase().equals("ALAMEDA")) ){
			 bRetorno = true;		  	
		  }
		  else if ( (sTexto.substring(0,7).trim().toUpperCase().equals("RODOVIA")) ){
			 bRetorno = true;		  	
		  }
		  else if ( (sTexto.substring(0,7).trim().toUpperCase().equals("ESTRADA")) ){
			 bRetorno = true;		  	
		  }
		  else if ( (sTexto.substring(0,7).trim().toUpperCase().equals("GALERIA")) ){
			 bRetorno = true;		  	
		  }
		  else if ( (sTexto.substring(0,7).trim().toUpperCase().equals("COLUNIA")) ){
			 bRetorno = true;		  	
		  }
		  else if ( (sTexto.substring(0,7).trim().toUpperCase().equals("COLONIA")) ){
			 bRetorno = true;		  	
		  }

		}
		if (sTexto.length()>9) {
			if ( (sTexto.substring(0,10).trim().toUpperCase().equals("LOCALIDADE")) ){
			   bRetorno = true;		  	
			}
		}
		if (sTexto.length()>11) {
			if ( (sTexto.substring(0,12).trim().toUpperCase().equals("AUTO ESTRADA")) ){
			   bRetorno = true;		  	
			}
		}

		return bRetorno;
	}
	private boolean colunaValida(String sColuna) {
		boolean bRetorno = true;
		if (sColuna.trim().length()<=2) {
			bRetorno = false;
		}
		else if (sColuna.length()==3) {
			if (!ehUF(sColuna)) {
				bRetorno = false;
			}
		}
		else if (sColuna.length()==4) {
			if ( ("0123456789".indexOf(sColuna.charAt(0))>-1) && ("0123456789".indexOf(sColuna.charAt(1))>-1) && 
			     ("0123456789".indexOf(sColuna.charAt(2))>-1) && ((""+sColuna.charAt(3)).equals(";")) ) {
			    bRetorno = false;	
   	     	}  
		}
		else if (sColuna.length()==5) {
			if (sColuna.equals("018+;")) {
				bRetorno = false;
			}
		}
		else if (";&;/; - ;a;,;+;*;.;N,;".indexOf(sColuna)>-1) 
			bRetorno = false;
		return bRetorno;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent ev) {
	}
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent ev) {
		if (ev.getSource()==txtCodTipo) { 
			String sTransBin = getTransBin(txtCodTipo.getVlrString());
			habDest(sTransBin.equals("S"));
		}
	}
	
	private String getTransBin(String sCodTipo) {
		String sRetorno = "";
		try { 
			String sSql = "SELECT CODTPIMP,DESCTPIMP,TRANSBINTPIMP FROM TKTIPOIMP  WHERE CODEMP=? AND CODFILIAL=? AND CODTPIMP=?";
			PreparedStatement ps = con.prepareStatement(sSql);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("TKTIPOCTO"));
			ps.setInt(3,Integer.parseInt(sCodTipo));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				sRetorno = rs.getString("TRANSBINTPIMP");
				if (sRetorno==null)
					sRetorno = "";
			}
			rs.close();
			if (!con.getAutoCommit())
				con.commit();
		}
		catch (SQLException e) {
			Funcoes.mensagemInforma(this,"Erro carregando dados da tabela TKTIPOCTO.\n"+e.getMessage());
			e.printStackTrace();
		}
		return sRetorno;
		
	}
}
