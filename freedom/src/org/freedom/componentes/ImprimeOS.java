/**
 * @version 21/08/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.componentes <BR>
 *         Classe:
 * @(#)ImprimeOS.java <BR>
 *                    Este programa é licenciado de acordo com a LPG-PC (Licença
 *                    Pública Geral para Programas de Computador), <BR>
 *                    versão 2.1.0 ou qualquer versão posterior. <BR>
 *                    A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES
 *                    e REPRODUÇÕES deste Programa. <BR>
 *                    Caso uma cópia da LPG-PC não esteja disponível junto com
 *                    este Programa, você pode contatar <BR>
 *                    o LICENCIADOR ou então pegar uma cópia em: <BR>
 *                    Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 *                    Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou
 *                    ALTERAR este Programa é preciso estar <BR>
 *                    de acordo com os termos da LPG-PC <BR>
 *                    <BR>
 *                    Comentários da classe.....
 */

package org.freedom.componentes;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;

import javax.swing.JInternalFrame;
import javax.swing.Timer;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.DLPrinterJob;
import org.freedom.telas.DLVisualiza;
import org.freedom.telas.FAndamento;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class ImprimeOS implements ActionListener {

	public static final int IMP_NONE = -1;
	public static final int IMP_MATRICIAL = 1;
	public static final int IMP_DESKJET = 2;
	public static final int IMP_LASERJET = 3;

	private String sErrorMessage = "";

	private String strTipoCab = "1";

	private String sClassNota = "";

	private String sPrefCab = "";

	private int numAdicPagina = 0;

	private DLVisualiza dlPrev = null;

	private DLPrinterJob dlPrevGraf = null;

	private ImprimeLayout impLay = null;

	private String[] sVals = new String[5];

	private Component cOwner = null;

	boolean bImpEject = true;

	boolean bSemAcento = true;

	boolean bImpGrafica = false;

	boolean bImpComprimido = false; // Flag para indicar se a impressão vai ser

	// comprimida

	byte bBuf;

	File fImp = null;

	FileWriter fwImp = null;

	RandomAccessFile rfVisual = null;

	int iCol, iRow = 0;

	int iTipoImp = IMP_NONE;

	int iConta = 0;

	int iNumPags = 1;

	int iPonteiro = 0;

	int iPagAtual = 0;

	int iAndamento = 0;

	GregorianCalendar hoje = new GregorianCalendar();

	String sFile = "";

	String sPagina = "";

	String sImpressora = "";

	String sTipoUsoImp = "TO";

	String sHoje = "";

	String sPath = "";

	Connection con = null;

	String sPorta = "";

	String sTitulo = "";

	Vector subTitulos = new Vector();

	Timer tim = null;

	FAndamento and = null;

	public void setImpEject(boolean bImpEject) {
		this.bImpEject = bImpEject;
	}

	public boolean getImpEject() {
		return this.bImpEject;
	}

	public String getSubTitulo(int index) {
		return (String) subTitulos.elementAt(index);
	}

	public void addSubTitulo(String subTitulo) {
		subTitulos.add(subTitulo);
	}

	public int getSubTituloIndex(String subTitulo) {
		return subTitulos.indexOf(subTitulo);
	}

	public int getSubTituloCount() {
		return subTitulos.size();
	}

	public ImprimeOS(String sF, Connection cn) {
		iniImprimeOS(sF, cn, null, false);
	}

	public ImprimeOS(String sF, Connection cn, String sTipoUsoImp,boolean bImpComprimido) {
		iniImprimeOS(sF, cn, sTipoUsoImp, bImpComprimido);
	}

	private void iniImprimeOS(String sF, Connection cn, String sTipoUsoImp,boolean bImpComprimido) {
		if (sTipoUsoImp == null) {
			sTipoUsoImp = "TO";
		}
		this.sTipoUsoImp = sTipoUsoImp;
		if (sF.trim().length() == 0) {
			sF = Funcoes.arquivoTemp();
			sPath = sF;
		}

		con = cn;
		sFile = sF;
		try {
			//        Funcoes.mensagemInforma(cOwner,"ARQ: "+sFile);
			fImp = new File(sFile);
			fwImp = new FileWriter(fImp);
		} catch (IOException err) {
			Funcoes.mensagemInforma(null,
					"Erro de gravação [ InputStream in device: " + sFile + " ]! ! !");
		}
		iTipoImp = getTipoImp();
		say(0,0, retiraExpandido());
	}

	public void setTitulo(String sT) {
		sTitulo = sT;
	}

	public void preview(JInternalFrame pai) {
		if (bImpGrafica)
			previewGrafico(pai);
		else
			previewTexto(pai);
	}

	public void previewGrafico(JInternalFrame pai) {
		dlPrevGraf = new DLPrinterJob(impLay, pai);
		dlPrevGraf.setSize(Aplicativo.telaPrincipal.dpArea.getSize());
		dlPrevGraf.setLocation(Aplicativo.telaPrincipal.dpArea
				.getLocationOnScreen());
		dlPrevGraf.toFront();
		//dlPrevGraf.setNomeImp(sImpressora);
		dlPrevGraf.setVisible(true);
	}

	public void previewTexto(JInternalFrame pai) {
		dlPrev = new DLVisualiza(this, pai);
		dlPrev.setSize(Aplicativo.telaPrincipal.dpArea.getSize());
		dlPrev.setLocation(Aplicativo.telaPrincipal.dpArea.getLocationOnScreen());
		dlPrev.toFront();
		dlPrev.setNomeImp(sImpressora);
		dlPrev.setVisible(true);
	}

	public String getTitulo() {
		return sTitulo;
	}

	public void fechaPreview() {
		if (dlPrev != null) {
			dlPrev.setVisible(false);
			dlPrev.dispose();
		}
	}

	public String lePagina(int iPagina) {
		sPagina = "";
		try {
			rfVisual = new RandomAccessFile(fImp, "r");
		} catch (IOException errf) {
			Funcoes.mensagemErro(null, "Erro acessando arquivo de impressão!\n"
					+ errf.getMessage());
		}

		try {
			try {
				if ((iPagina > iNumPags) | (iPagina == 0)) {
					return "";
				} else if (iPagina == 1) {
					rfVisual.seek(0);
					iPonteiro = 0;
					leProximaPag();
					if (sPagina.trim().length() > 0)
						iPagAtual = 1;
				} else if (iPagina == iNumPags) {
					iPonteiro = ((int) fImp.length()) - 1;
					//             Funcoes.mensagemInforma(null,"Entrou na ultima\n
					// "+iPonteiro);
					iConta = 0;
					for (int i = iPonteiro; i > 0; i--) {
						//                 Funcoes.mensagemInforma(null,"Entrou na ultima\n
						// "+iPonteiro);

						rfVisual.seek(i);
						bBuf = rfVisual.readByte();
						if (bBuf == 12) {
							iConta++;
						}
						if (iConta > 1) {
							iPonteiro = i + 1;
							rfVisual.seek(iPonteiro);
							break;
						}
					}

					leProximaPag();
					if (sPagina.trim().length() > 0)
						iPagAtual = iNumPags;

				} else {
					if (iPagina < iPagAtual) {
						iConta = 0;
						iPonteiro--;
						for (int i = iPonteiro; i > 0; i--) {
							rfVisual.seek(i);
							bBuf = rfVisual.readByte();
							if (bBuf == 12) {
								iConta++;
							}
							if (iConta > 2) {
								iPonteiro = i + 1;
								rfVisual.seek(iPonteiro);
								break;
							}
						}
						leProximaPag();
						if (sPagina.trim().length() > 0)
							iPagAtual--;

					} else if (iPagina > iPagAtual) {
						leProximaPag();
						if (sPagina.trim().length() > 0)
							iPagAtual++;
					}
				}
			} catch (IOException erio) {
				Funcoes.mensagemErro(null, "Erro lendo arquivo de impressão!\n"
						+ erio.getMessage());
			}
		} finally {
			try {
				rfVisual.close();
			} catch (IOException errv) {
				Funcoes.mensagemErro(null,
						"Erro fechando acesso ao arquivo de impressão!\n"
								+ errv.getMessage());
			}
		}
		return tiraCaracEsp(sPagina);
	}

	private void leProximaPag() {
		while (true) {
			try {
				try {
					bBuf = rfVisual.readByte();
					iPonteiro++;
					if (bBuf == 10) {
						sPagina += "" + ((char) 10) + "" + ((char) 13);
						rfVisual.seek(iPonteiro);
					} else if (bBuf == 12) {
						iPonteiro++;
						rfVisual.seek(iPonteiro);
						break;
					} else if (bBuf == 13) {
						rfVisual.seek(iPonteiro);
					} else {
						sPagina += "" + ((char) bBuf);
					}
				} catch (EOFException ereof1) {
					break;
				}
			} catch (IOException eriop) {
				Funcoes.mensagemErro(null, "Erro lendo arquivo de impressão!\n"
						+ eriop.getMessage());
			}
		}
	}

	public String tiraCaracEsp(String sVal) {
		String sRetorno = sVal;
		String[] sCarac = new String[4];
		sCarac[0] = "" + normal();
		sCarac[1] = "" + comprimido();
		sCarac[2] = "" + expandido();
		sCarac[3] = "" + retiraExpandido();
		for (int i = 0; i < 4; i++) {
			try {
				sRetorno = Funcoes.tiraString(sRetorno, sCarac[i]);
				sRetorno = Funcoes.tiraAcentos(sRetorno);
			} catch (PatternSyntaxException e) {

			}
		}
		sRetorno = tiraCR(sRetorno);
		return sRetorno;
	}

	public String tiraCR(String sTexto) {
		sTexto = sTexto.replaceAll("\\r", ""); //Tirando todo os CR
		sTexto = sTexto.replaceAll("\\n", "\r\n"); //Colocando CR so do lado
		// dos LF
		return sTexto;
	}

	public int getPagAtual() {
		return iPagAtual;
	}

	public void fechaGravacao() {
		try {
			if (fwImp != null) {
				fwImp.close();
			}
		} catch (IOException errf) {
			Funcoes.mensagemErro(null, "Erro fechando gravação!\n"
					+ errf.getMessage());
		}

	}

	public boolean print() {
		Thread th = new Thread(new Runnable() {

			public void run() {
				comecaImp();
			}
		});
		try {
			th.start();
		} catch (Exception err) {
			return false;
		}
		return true;
	}

	private boolean comecaImp() {
		if (sPorta.equals(""))
			setPortaImp();

		if (fImp == null) {
			sErrorMessage = "Erro ao abrir arquivo impressão";
			return false;
		}

		if (Aplicativo.strOS.equals("linux")) {
			String[] sComando = { "lpr", "-P" + sPorta, sFile }; //Funcoes.substringByChar(sPort,'/',false)};
			//       Funcoes.mensagemInforma(null,sFile);
			try {
				Runtime.getRuntime().exec(sComando);
			} catch (IOException ioerr) {
				sErrorMessage = "Erro ao imprimir na porta:" + sPorta + "\nArquivo"
						+ sFile + "\n" + ioerr.getMessage();
				return false;
			}
			return true;
		}
		and = new FAndamento("Imprimindo...", 0, (int) fImp.length());
		and.setVisible(true);
		tim = new Timer(200, this);
		tim.start();

		RandomAccessFile rfPrint = null;
		File fPrint = null;
		FileWriter fwPrint = null;
		byte bBuf;
		String sBuf = "";
		try {
			rfPrint = new RandomAccessFile(fImp, "r");
			fPrint = new File(sPorta);
			fwPrint = new FileWriter(fPrint);
			rfPrint.seek(0);
			while (true) {
				try {
					bBuf = rfPrint.readByte();
					if ((bBuf == 12) && (!bImpEject)) {
						sBuf = "";
						bBuf = rfPrint.readByte();
						if (bBuf != 13)
							sBuf = "" + ((char) bBuf);
						iAndamento++;
					} else
						sBuf = "" + ((char) bBuf);
					iAndamento++;
				} catch (EOFException er2) {
					break;
				}
				if (!sBuf.equals("")) {
					fwPrint.write(sBuf);
					fwPrint.flush();
				}
			}
			fwPrint.close();
			rfPrint.close();
		} catch (IOException err) {
			sErrorMessage = "Erro ao imprimir na porta:" + sPorta + "\n"
					+ err.getMessage();
			Funcoes.mensagemErro(null, sErrorMessage);
			return false;
		}
		if (and != null)
			and.dispose();
		return true;
	}

	public String getImpErrorMessage() {
		return sErrorMessage;
	}

	public String verifCab() {
		return "1";
	}

	public void incPags() {
		iNumPags++;
	}

	public int getNumPags() {
		return iNumPags;
	}

	public void limpaPags() {
		iNumPags = 1;
	}

	public void setSemAcento(boolean bSA) {
		bSemAcento = bSA;
	}

	public String space(int iTam) {
		return replicate(" ", iTam);
	}

	public boolean isPrinter() {
		boolean bRetorno = true;
		try {
			fwImp.write((char) 13);
		} catch (IOException err) {
			Funcoes.mensagemErro(null, "Impressora Desligada!");
			bRetorno = false;
		}
		return bRetorno;
	}

	public int pRow() {
		return iRow;
	}

	public int pCol() {
		return iCol;
	}

	public void say(int iR, int iC, String sTexto) {
		if (sTexto == null) {
			sTexto = " ";
		}
		if (iR < iRow) {
			iR = iRow;
		} else if (iR > iRow) {
			for (int i = iRow + 1; i <= iR; i++) {
				//         gravaTexto( ((char) 13) + ((char) 10) + "" );
				gravaTexto("" + '\n');
			}
			iRow = iR;
			iCol = 0;
		}
		if (iC < iCol) {
			gravaTexto(((char) 13) + "");
			iCol = 0;
		}
		if (iR != 0) {
			sTexto = space((iC - iCol) - 1) + sTexto;
		}
		gravaTexto(sTexto);
		iCol = iCol + sTexto.length();

	}

	public void setPrc(int iR, int iC) {
		iCol = iC;
		iRow = iR;
	}

	public String getEject() {
		return 	"" + ((char) 12) + ((char) 13);
	}
	
	public void eject() {
		gravaTexto(getEject());
		setPrc(0, 0);
	}

	public void gravaTexto(String sBuffer) {
		char cBuffer = (char) 0;

		try {
			for (int i = 0; i < sBuffer.length(); i++) {
				cBuffer = sBuffer.charAt(i);
				if (bSemAcento)
					cBuffer = tiraAcento(cBuffer);
				fwImp.write(cBuffer);
			}
			fwImp.flush();
		} catch (IOException err) {
			Funcoes.mensagemErro(null, "Erro de comunicação!");
		}
	}

	public String normal() {
		String sRetorno = "";
		if (iTipoImp == IMP_NONE)
			return sRetorno;

		if (iTipoImp == IMP_MATRICIAL)
			sRetorno = "" + ((char) 18);
		else if (iTipoImp == IMP_DESKJET)
			sRetorno = ((char) 27) + "(s0p10h1s3b2T";
		else if (iTipoImp == IMP_LASERJET)
			sRetorno = ((char) 27) + "(s10H";
		return sRetorno;
	}

	public String comprimido() {
		String sRetorno = "";

		if (iTipoImp == IMP_NONE)
			return sRetorno;

		if (iTipoImp == IMP_MATRICIAL)
			sRetorno = "" + ((char) 15);
		else if (iTipoImp == IMP_DESKJET)
			sRetorno = ((char) 27) + "(s0p20h1s0b2T";
		else if (iTipoImp == IMP_LASERJET)
			sRetorno = ((char) 27) + "(s20H";
		return sRetorno;
	}

	public String expandido() {
		String sRetorno = "";
		if (iTipoImp == IMP_NONE)
			return sRetorno;

		if (iTipoImp == IMP_MATRICIAL)
			sRetorno = "" + ((char) 14);
		else if (iTipoImp == IMP_DESKJET)
			sRetorno = ((char) 27) + "(s05h1S";
		else if (iTipoImp == IMP_LASERJET)
			sRetorno = ((char) 27) + "(s5H";
		return sRetorno;

	}

	public String retiraExpandido() {
		String sRetorno = "";
		if (iTipoImp == IMP_NONE)
			return sRetorno;

		if (iTipoImp == IMP_MATRICIAL)
			sRetorno = "" + ((char) 20);
		else if (iTipoImp == IMP_DESKJET)
			sRetorno = ((char) 27) + "E" + ((char) 27) + "&l26a66p0o0e6D";
		else if (iTipoImp == IMP_LASERJET)
			sRetorno = ((char) 27) + "E" + ((char) 27) + "&l2a&l0o&l6d(s4099t(s0p&k0s(s0s&l12D";
		return sRetorno;
	}

	public static char tiraAcento(char cKey) {

		char cTmp = cKey;

		if (contido(cTmp, "ãâáà"))
			cTmp = 'a';
		else if (contido(cTmp, "ÃÂÁÀ"))
			cTmp = 'A';
		else if (contido(cTmp, "êéè"))
			cTmp = 'e';
		else if (contido(cTmp, "ÊÉÈ"))
			cTmp = 'E';
		else if (contido(cTmp, "îíì"))
			cTmp = 'i';
		else if (contido(cTmp, "ÎÍÌ"))
			cTmp = 'I';
		else if (contido(cTmp, "õôóò"))
			cTmp = 'o';
		else if (contido(cTmp, "ÕÔÓÒ"))
			cTmp = 'O';
		else if (contido(cTmp, "ûúù"))
			cTmp = 'u';
		else if (contido(cTmp, "ÛÚÙ"))
			cTmp = 'U';
		else if (contido(cTmp, "ç"))
			cTmp = 'c';
		else if (contido(cTmp, "Ç"))
			cTmp = 'C';

		return cTmp;
	}

	public int getTipoImp() {

		int iRetorno = IMP_NONE;
		String sRetorno = "";

		PreparedStatement ps = null; //ALTERAR AQUI
		ResultSet rs = null;
		String sSQL = null;

		if (this.sTipoUsoImp == null)
			this.sTipoUsoImp = "TO";
		
		if (sTipoUsoImp.equals("TO")) {
			sSQL = "SELECT I.CODIMP,I.TIPOIMP,I.DESCIMP FROM SGIMPRESSORA I, SGESTACAOIMP EI WHERE "
					+ "EI.CODEST=? AND EI.CODEMP=? AND EI.CODFILIAL=? AND EI.IMPPAD='S' AND "
					+ "EI.CODEMPIP=I.CODEMP AND EI.CODFILIALIP=I.CODFILIAL AND EI.CODIMP=I.CODIMP";
		} 
		else {
			sSQL = "SELECT I.CODIMP,I.TIPOIMP,I.DESCIMP FROM SGIMPRESSORA I, SGESTACAOIMP EI WHERE "
					+ "EI.CODEST=? AND EI.CODEMP=? AND EI.CODFILIAL=? AND "
					+ "EI.TIPOUSOIMP='"
					+ this.sTipoUsoImp
					+ "' AND "
					+ "EI.CODEMPIP=I.CODEMP AND EI.CODFILIALIP=I.CODFILIAL AND EI.CODIMP=I.CODIMP";
		}

		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iNumEst);
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.setInt(3, ListaCampos.getMasterFilial("SGESTACAOIMP"));
			rs = ps.executeQuery();
			if (rs.next()) {
				sImpressora = rs.getString(1);
				sRetorno = rs.getString(2);
				sImpressora += " - " + rs.getString(3);
			} 
			else if(sTipoUsoImp.equals("PD")){
				sTipoUsoImp = "TO";
				getTipoImp();				
			}
			else
				Funcoes.mensagemErro(null,"Não foi encontrado nenhum tipo de impressora!\n"
										+ "Provávelmente não têm impressora cadastrada para esta estação de trabalho!!");
			rs.close();
			ps.close();
			//        if (!con.getAutoCommit())
			//      	con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(null,
					"Erro ao consultar a tabela SGESTACAOIMP E SGIMPRESSORA\n"
							+ err.getMessage());
		}

		if (!sRetorno.trim().equals("")) {
			iRetorno = Integer.parseInt(sRetorno.trim());
		}
		return iRetorno;
	}

	public void setPortaImp() {
		sPorta = "";
		String sSQL = "";
		String sPortaOS = "";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			if (Aplicativo.strOS.compareTo("windows") == 0)
				sPortaOS = "PORTAWIN";
			else if (Aplicativo.strOS.compareTo("linux") == 0)
				sPortaOS = "PORTALIN";
			if (sPortaOS.trim().equals("")) {
				Funcoes.mensagemInforma(cOwner,
						"Não foi possível obter informacões do Sistema Operacional! ! !");
				return;
			}
			if (sTipoUsoImp.equals("TO")) {
				sSQL = "SELECT EI." + sPortaOS
						+ ",EI.IMPGRAFICA FROM SGESTACAOIMP EI,SGIMPRESSORA I "
						+ "WHERE EI.CODEST=? AND EI.IMPPAD='S' AND "
						+ "I.CODIMP=EI.CODIMP AND I.CODFILIAL=EI.CODFILIALIP AND "
						+ "I.CODEMP=EI.CODEMPIP AND EI.CODEMP=? AND EI.CODFILIAL=?";
			} else {
				sSQL = "SELECT EI." + sPortaOS
						+ ",EI.IMPGRAFICA FROM SGESTACAOIMP EI,SGIMPRESSORA I "
						+ "WHERE EI.CODEST=? AND EI.TIPOUSOIMP='" + sTipoUsoImp + "' AND "
						+ "I.CODIMP=EI.CODIMP AND I.CODFILIAL=EI.CODFILIALIP AND "
						+ "I.CODEMP=EI.CODEMPIP AND EI.CODEMP=? AND EI.CODFILIAL=?";
			}
			try {
				ps = con.prepareStatement(sSQL);
				ps.setInt(1, Aplicativo.iNumEst);
				ps.setInt(2, Aplicativo.iCodEmp);
				ps.setInt(3, ListaCampos.getMasterFilial("SGESTACAOIMP"));
				rs = ps.executeQuery();
				if (rs.next()) {
					sPorta = rs.getString(sPortaOS).trim();
					if (rs.getString("IMPGRAFICA") != null)
						bImpGrafica = rs.getString("IMPGRAFICA").equals("S");
				} else
					Funcoes.mensagemInforma(cOwner,
							"Não foi encontrada nome da porta da impressora!\n"
									+ "Tabela: IMPRESSORA");
				rs.close();
				ps.close();
				//if (!con.getAutoCommit())
				//	con.commit();
				//        con.commit();
			} catch (SQLException err) {
				Funcoes.mensagemErro(cOwner,
						"Erro ao consultar a tabela ESTACAOIMP E IMPRESSORA\n"
								+ err.getMessage());
			}
		} finally {
			sSQL = null;
			sPortaOS = null;
			rs = null;
			ps = null;
		}
	}

	private String replicate(String sTexto, int iNum) {
		String sRetorno = "";
		for (int i = 1; i <= iNum; i++)
			sRetorno += sTexto;
		return sRetorno;
	}

	public static boolean contido(char cTexto, String sTexto) {
		boolean bRetorno = false;
		for (int i = 0; i < sTexto.length(); i++) {
			if (cTexto == sTexto.charAt(i)) {
				bRetorno = true;
				break;
			}
		}
		return bRetorno;
	}

	public void montaCab() {
		this.montaCab(0);
	}

	public void montaCab(int numAdicPagina) {
		this.numAdicPagina = numAdicPagina;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		sHoje = Funcoes.dateToStrDate(hoje.getTime());
		strTipoCab = verifCab();
		try {
			if (strTipoCab.compareTo("1") == 0) {
				sSQL = "SELECT RAZEMP,FONEEMP,FAXEMP,EMAILEMP,WWWEMP " +
					   "FROM SGEMPRESA WHERE CODEMP=?";
				try {
					ps = con.prepareStatement(sSQL);
					ps.setInt(1, Aplicativo.iCodEmp);
					rs = ps.executeQuery();
					while (rs.next()) {
						for (int i = 0; i < sVals.length; i++) {
							if (rs.getString(i + 1) != null)
								sVals[i] = rs.getString(i + 1);
							else
								sVals[i] = "";
						}
					}
					rs.close();
					ps.close();
					//        con.commit();
				} catch (SQLException err) {
					Funcoes.mensagemErro(cOwner, "Erro a pesquisar a tabela\n"
							+ err.getMessage());
					return;
				}
				//Coloca as Mascaras
				sVals[1] = Funcoes.setMascara(sVals[1], "####-####");
				sVals[2] = Funcoes.setMascara(sVals[2], "####-####");
			} else if (strTipoCab.compareTo("2") == 0) {
				sSQL = "SELECT CABEMP FROM SGPREFERE1 AND CODEMP=? AND CODFILIAL=?";
				try {
					ps = con.prepareStatement(sSQL);
					ps.setInt(1, Aplicativo.iCodEmp);
					ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
					rs = ps.executeQuery();
					//        con.commit();
					rs.next();
					if (rs.getString(1) != null)
						sPrefCab = rs.getString(1);
					rs.close();
					ps.close();
					//        con.commit();
				} catch (SQLException err) {
					Funcoes.mensagemErro(cOwner,
							"Erro na consulta ao banco de dados! ! !");
					return;
				}
			}
		} finally {
			rs = null;
			ps = null;
			sSQL = null;
		}
	}

	public void impCab(int iTamRel, boolean borda) {
		//Seta Data

		String sData = Funcoes.dateToStrDataHora(hoje.getTime());
		String sTmp = "";

		//Imprime linhas
		if (strTipoCab.compareTo("1") == 0) {
			//        Funcoes.mensagemInforma(cOwner,"Enviando comandos cabecalho
			// \n"+normal().length());
			if (borda) {
				say(pRow() + 0, 0, "" + comprimido());
				say(pRow() + 0, 0, "+" + Funcoes.replicate("-", iTamRel - 3) + "+");
				say(pRow() + 1, 0, comprimido());
				say(pRow() + 0, 0, "|" + sVals[0].trim()); //Razão
				//        say(pRow()+1,0,"");
				say(pRow() + 0, iTamRel - 15, "Pagina: "
						+ Funcoes.strZero("" + (getNumPags() + this.numAdicPagina), 5));
				say(pRow() + 0, iTamRel - 1, "|");
				say(pRow() + 1, 0, comprimido());
				say(pRow() + 0, 0, "| " + sTitulo.trim().toUpperCase());
				say(pRow() + 0, iTamRel - 17, "ID.USU: "+Aplicativo.strUsuario.toUpperCase());
				say(pRow() + 0, iTamRel - 1, "|");
				say(pRow() + 1, 0, comprimido());
				say(pRow() + 0, 0, "|" + " Fone: " + sVals[1]); //Fone
				say(pRow() + 0, 25, " - Fax: " + sVals[2]); //Fax
				say(pRow() + 0, iTamRel - 29, "Data: " + sData);
				say(pRow() + 0, iTamRel - 1, "|");
				say(pRow() + 1, 0, comprimido());
				say(pRow() + 0, 0, "|" + " E-mail: " + sVals[3]); //E-mail
				say(pRow() + 0, iTamRel - 1, "|");
				//say(pRow() + 1, 0, "|");
				//say(pRow() + 0, iTamRel - 1, "|");

				for (int i = 0; i < subTitulos.size(); i++) {
					sTmp = subTitulos.elementAt(i).toString();
					if ((sTmp.length() + 2) > iTamRel)
						sTmp = sTmp.substring(0, iTamRel - 4);
					if (!sTmp.trim().equals("")) {
						say(pRow() + 1, 0, comprimido());
					    
						say(pRow() + 0, 0, "|");
						say(pRow() + 0, ((iTamRel - sTmp.length() - 2) / 2), sTmp);
						say(pRow() + 0, iTamRel - 1, "|");
					}
				}
				/*
				 * if (iTamRel == 80) say(pRow() + 1, 0, normal()); else say(pRow() + 1,
				 * 0, comprimido()); say(pRow() + 0, 0, "|"); say(pRow() + 0, iTamRel -
				 * 1, "|");
				 */

				if (iTamRel == 136)
					say(pRow() + 0, 0, comprimido());
				else
					say(pRow() + 0, 0, normal());
				say(pRow() + 1, 0, normal());
			} else {
				say(pRow() + 1, 0, normal());
				say(pRow() + 0, 0, sVals[0].trim()); //Razão
				//        say(pRow()+1,0,"");
				say(pRow() + 0, iTamRel - 15, "Pagina: "
						+ Funcoes.strZero("" + getNumPags(), 5));
				say(pRow() + 1, 0, sTitulo.trim().toUpperCase());
				say(pRow() + 0, iTamRel - 16, "ID.USU: "
						+ Aplicativo.strUsuario.toUpperCase());
				say(pRow() + 1, 0, comprimido());
				say(pRow() + 0, 0, "Fone: " + sVals[1]); //Fone
				say(pRow() + 0, 25, " - Fax: " + sVals[2]); //Fax
				say(pRow() + 0, 50, " - E-mail: " + sVals[3]); //E-mail
				if (iTamRel == 80)
					say(pRow() + 1, 0, normal());
				else
					say(pRow() + 1, 0, comprimido());
				say(pRow() + 0, 0, "Relatório emitido por "
						+ Aplicativo.strUsuario.toUpperCase());
				say(pRow() + 0, iTamRel - 29, "Data: " + sData);

				if (iTamRel == 136)
					say(pRow() + 1, 0, comprimido());
				else
					say(pRow() + 1, 0, normal());
				say(pRow() + 0, 0, Funcoes.replicate("=", iTamRel - 1));
				say(pRow() + 1, 0, normal());
			}
		} else if (strTipoCab.compareTo("2") == 0) {
			//        Funcoes.mensagemInforma(cOwner,"Enviando comandos cabecalho 2");
			say(pRow() + 0, 0, normal());
			say(pRow() + 0, 0, sPrefCab);
			say(pRow() + 1, 0, "" + normal());
			say(pRow() + 0, 0, sTitulo.toUpperCase().trim());
			say(pRow() + 1, 0, "" + normal());
			say(pRow() + 0, 0, "");
			say(pRow() + 1, 0, "" + normal());
			say(pRow() + 0, 0, "");
			if (iTamRel == 80)
				say(pRow() + 1, 0, "" + normal());
			else
				say(pRow() + 1, 0, "" + comprimido());
			say(pRow() + 0, 0, Funcoes.replicate("=", iTamRel));
			if (iTamRel == 80)
				say(pRow() + 1, 0, "" + normal());
			else
				say(pRow() + 1, 0, "" + comprimido());
		}
	}

	public void exportaPDF(DLVisualiza pai) {
		File fArq = Funcoes.buscaArq(pai, "pdf");
		if (fArq == null)
			return;
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(fArq));
			for (int i = 1; i <= getNumPags(); ++i) {
				ps.print(lePagina(i));
				ps.print(getEject());
			}
			ps.flush();
			ps.close();
		} catch (IOException err) {
			Funcoes.mensagemErro(pai, "Erro ao gravar o arquivo!\n"
					+ err.getMessage());
			err.printStackTrace();
		}
	}

	public void exportaTXT(DLVisualiza pai) {
		File fArq = Funcoes.buscaArq(pai, "txt");
		if (fArq == null)
			return;
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(fArq));
			for (int i = 1; i <= getNumPags(); ++i) {
				ps.print(lePagina(i));
				if (bImpEject)
					ps.print(getEject());
			}
			ps.flush();
			ps.close();
		} catch (IOException err) {
			Funcoes.mensagemErro(pai, "Erro ao gravar o arquivo!\n"
					+ err.getMessage());
			err.printStackTrace();
		}
	}
	
	public String[] getValCab() {
		return sVals;
	}

	public String getClassNota() {
		return sClassNota;
	}

	public int verifLinPag() {
		return verifLinPag("TO");
	}

	public int verifLinPag(String sTipo) {
		int iRetorno = 0;
		String sSQL = "";
		if (sTipo == null)
			sTipo = sTipoUsoImp;
		if (sTipo.equals("TO")) {
			sSQL = "SELECT PP.LINPAPEL,PP.CLASSNOTAPAPEL FROM SGPAPEL PP, SGESTACAOIMP EI WHERE "
					+ "EI.CODEST=? AND EI.CODEMP=? AND EI.CODFILIAL=? AND EI.IMPPAD='S' AND "
					+ "EI.CODEMPPP=PP.CODEMP AND EI.CODFILIALPP=PP.CODFILIAL AND EI.CODPAPEL=PP.CODPAPEL";
		} else {
			sSQL = "SELECT PP.LINPAPEL,PP.CLASSNOTAPAPEL FROM SGPAPEL PP, SGESTACAOIMP EI WHERE "
					+ "EI.CODEST=? AND EI.CODEMP=? AND EI.CODFILIAL=? AND "
					+ "EI.TIPOUSOIMP='"
					+ sTipo
					+ "' AND "
					+ "EI.CODEMPPP=PP.CODEMP AND EI.CODFILIALPP=PP.CODFILIAL AND EI.CODPAPEL=PP.CODPAPEL";
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iNumEst);
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.setInt(3, ListaCampos.getMasterFilial("SGESTACAOIMP"));
			rs = ps.executeQuery();
			if (rs.next()) {
				iRetorno = rs.getInt("LINPAPEL");
				sClassNota = rs.getString("ClassNotaPapel") != null ? rs.getString("ClassNotaPapel").trim() : "";
			} 
			else if(sTipo.equals("PD")){
				verifLinPag("TO");				
			}
			else {
				Funcoes.mensagemInforma(cOwner,"Não foi encontrada nenhuma impressora do tipo '" + sTipo + "!");
			}
			rs.close();
			ps.close();
			//if (!con.getAutoCommit())
			//con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(cOwner, "Erro ao consultar a tabela SGESTACAOIMP\n"
					+ err.getMessage());
			return 0;
		}
		return iRetorno;
	}

	public boolean gravaPdf(File fArq) {
		boolean bRetorno = false;
		com.lowagie.text.Document document = new com.lowagie.text.Document();

		try {
			int iMXPdf = -1;
			int iMYPdf = -1;
			int wt = 0;
			int ht = 0;
			PdfTemplate tp = null;
			Graphics2D g2 = null;
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(
					fArq));
			document.open();
			PdfContentByte cb = writer.getDirectContent();
			PrinterJob printJob = PrinterJob.getPrinterJob();
			Paper paper = printJob.defaultPage().getPaper();
			//PaginaPad pf = new PaginaPad(paper);
			//wt = (int)(pf.getWidth());
			//ht = (int)(pf.getHeight());
			if ((iMXPdf != -1) && (iMYPdf != -1)) {
				paper.setImageableArea(iMXPdf, iMYPdf, wt, ht);
				//pf = new PaginaPad(paper);
			}
			for (int i = 0; i < getNumPags(); i++) {
				if (i > 0) {
					document.newPage();
				}
				tp = cb.createTemplate(wt, ht);
				g2 = tp.createGraphics(wt, ht);
				/*
				 * try { prepPagina(g2, pf, i); } catch (PrinterException pe) {
				 * Funcoes.mensagemErro(null,"Erro criando graphics: "+pe.getMessage()); }
				 */
				tp.setWidth(wt);
				tp.setHeight(ht);
				g2.dispose();
				cb.addTemplate(tp, 0, 0);

			}

		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// step 5: we close the document
		document.close();

		return bRetorno;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == tim) {
			and.atualiza(iAndamento);
		}
	}
}