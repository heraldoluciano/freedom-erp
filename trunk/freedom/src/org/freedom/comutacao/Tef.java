/**
 * @version 19/03/2005 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.comutacao <BR>
 * Classe: @(#)TesteBema.java <BR>
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
 * Classe de interface com TEF.
 * 
 */

package org.freedom.comutacao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.Vector;

import org.freedom.funcoes.Funcoes;

public class Tef {
	boolean bAtivo = false;
	File fTmp;
	File fEnvio;
	File fStatus;
	File fRetorno;
	File fAtivo;
	static final String ARQ_TMP = "IntPos.tmp";
	static final String ARQ_ENVIO = "IntPos.001";
	static final String ARQ_STATUS = "intpos.sts";
	static final String ARQ_RETORNO = "intpos.001";
	static final String ARQ_ATIVO = "ativo.001";

	static final String TEF_HEADER = "000-000";
	static final String TEF_IDENTIFICACAO = "001-000";
	static final String TEF_DOC_FISCAL = "002-000";
	static final String TEF_VAL_TOTAL = "003-000";
	static final String TEF_ST_TRANSACAO = "009-000";
	static final String TEF_NOME_REDE = "010-000";
	static final String TEF_TIPO_TRANSACAO = "011-000";
	static final String TEF_NSU = "012-000";
	static final String TEF_AUTORIZACAO = "013-000";
	static final String TEF_LOTE_TRANSACAO = "014-000";
	static final String TEF_TIM_SERVER = "015-000";
	static final String TEF_TIM_LOCAL = "016-000";
	static final String TEF_DT_COMPROVANTE = "022-000";
	static final String TEF_HR_COMPROVANTE = "023-000";
	static final String TEF_FINALIZACAO = "027-000";
	static final String TEF_QTD_LINHAS = "028-000";
	static final String IMP_BASE = "029-"; //Base para impressao do comprovante (somar esta mais 3 digitos): 029-001,029-002...
	static final String TEF_MSG_OPERADOR = "030-000";
	static final String TEF_ADMINISTRADORA = "040-000";
	static final String TEF_EOT = "999-999"; //"E"nd "O"f "T"rasaction. (final do arquivo)
	
	private long lIdentUniq = 1;
	
	public Tef(String sPathEnv, String sPathRet) {
		fTmp = new File(sPathEnv+"/"+ARQ_TMP);
		fEnvio = new File(sPathEnv+"/"+ARQ_ENVIO);
		fStatus = new File(sPathRet+"/"+ARQ_STATUS);
		fRetorno = new File(sPathRet+"/"+ARQ_RETORNO);
		fAtivo = new File(sPathRet+"/"+ARQ_ATIVO);

		if (!verifTef()) {
			Funcoes.mensagemInforma(null,"Gerenciador Padrão de TEF não está ativo!");
		}
			
	}
	public boolean enviaArquivo(String sConteudo[]) {
		boolean bRet = false;
		
		if (fTmp.exists())
			fTmp.delete();
		try {
			PrintStream psEnvio = new PrintStream(new FileOutputStream(fTmp));
			for (int i=0;i<sConteudo.length;i++)
				psEnvio.println(sConteudo[i]);
			psEnvio.println(TEF_EOT+" = 0");
			psEnvio.close();
			fTmp.renameTo(fEnvio);
			bRet = true;
		}
		catch(IOException err) {
			Funcoes.mensagemErro(null,"Não foi possível gravar o arquivo temporário de TEF!");
			err.printStackTrace();
		}
		return bRet;
	}
	public boolean verifTef() {
		boolean bRet = false;
		
		if (!fAtivo.exists() || !fAtivo.canRead())
			return false;

		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(fAtivo));
			if (prop.getProperty(TEF_HEADER,"").equals("TEF"))
				bRet = true;
			prop.clear();
		}
		catch(IOException err) {
			Funcoes.mensagemErro(null,"Não foi possível verificar se o módulo TEF está ativo!");
			err.printStackTrace();
		}
		return bRet;
	}
	public boolean existeStatus(String sCab, long lIdent) {
		return existeInfo(sCab,fStatus,7,(lIdent > 0 ? lIdent : 0));//7 é padrao do GP.
	}
	public boolean existeRetorno(String sCab, long lIdent) {
		return existeInfo(sCab,fRetorno,15,(lIdent > 0 ? lIdent : 0));
	}
	public boolean existeInfo(String sCab,File fArq, int iTentativas,long lIdent) {
		String sLinha;
		boolean bRet = false;
		int iConta = 0;
		while(true) {
			try {
				Thread.sleep(1000);
			}
			catch(Exception err) {
				break;
			}
			if (!fArq.exists() || !fArq.canRead())
				continue;
			try {
				Properties prop = new Properties();
				FileInputStream fis = new FileInputStream(fArq);
				prop.load(fis);
				fis.close();
				//Se for necessário identificação o lIdent será maior a 0;
				if (prop.getProperty(TEF_HEADER,"").equals(sCab) && lIdent > 0 &&
					prop.getProperty(TEF_IDENTIFICACAO,"").equals(""+lIdent))
					bRet = true;
				//Se não for necessário identificação o lIdent será igual a 0;
				else if (prop.getProperty(TEF_HEADER,"").equals(sCab) && lIdent == 0)
					bRet = true;
				prop.clear();
			}
			catch(IOException err) {
				Funcoes.mensagemErro(null,"Não foi possível ler o retorno da TEF!");
				err.printStackTrace();
			}
			if (!bRet && iConta < iTentativas)
				iConta++;
			else
				break;
		}
		return bRet;
	}
	private Properties leRetorno() {
		Properties pRet = null;
		try {
			FileInputStream fis = new FileInputStream(fRetorno);
			pRet = new Properties();
			pRet.load(fis);
			fis.close();
			fRetorno.delete();
			fStatus.delete();
		}
		catch(Exception err) {
			Funcoes.mensagemErro(null,"Não foi possível carregar o retorno da TEF!");
			err.printStackTrace();
		}
		return pRet;
	}
	public Properties solicVenda(int iNumCupom, BigDecimal bigVal) {
		String pRet = null;
		boolean bRet;
		int iConta;
		if (!verifTef())
			return null;
//Pega uma identificação e já deixa outra disponível;		
		long lIdent = this.lIdentUniq++;
		
		bRet = enviaArquivo(new String[] {
						(TEF_HEADER + " = "+ "CRT"),
						(TEF_IDENTIFICACAO + " = "+ lIdent),
						(TEF_DOC_FISCAL + " = "+ iNumCupom),
						(TEF_VAL_TOTAL + " = "+ (Funcoes.transValor(bigVal,12,2,false)).trim())
					}
				);
		if (!bRet || 
		    !existeStatus("CRT",lIdent) || 
			!existeRetorno("CRT",lIdent))
			return null;
		
		return leRetorno();
	}
	public boolean finalizaVenda(Properties prop) {
		String pRet = null;
		boolean bRet;
		int iConta;
		if (!verifTef())
			return false;
		bRet = enviaArquivo(new String[] {
						(TEF_HEADER + " = "+ "CNF"),
						(TEF_IDENTIFICACAO + " = "+ prop.getProperty(TEF_DOC_FISCAL)),
						(TEF_DOC_FISCAL + " = "+ prop.getProperty(TEF_DOC_FISCAL)),
						(TEF_NOME_REDE + " = "+ prop.getProperty(TEF_NOME_REDE)),
						(TEF_NSU + " = "+ prop.getProperty(TEF_NSU)),
						(TEF_FINALIZACAO + " = "+ prop.getProperty(TEF_FINALIZACAO)),
					}
				);
		if (!bRet || !existeStatus("CNF", Long.parseLong(prop.getProperty(TEF_DOC_FISCAL)))) 
			return false;
		
		return bRet;
	}
	public Properties solicAdm(String sTipoTrans) {
		String pRet = null;
		boolean bRet;
		int iConta;
		if (!verifTef())
			return null;
		bRet = enviaArquivo(new String[] {
						(TEF_HEADER + " = "+ "ADM"),
						(TEF_TIPO_TRANSACAO + " = "+ sTipoTrans)
					}
				);
		if (!bRet || 
		    !existeStatus("ADM",0) || 
			!existeRetorno("ADM",0))
			return null;
		
		return leRetorno();
	}
	public boolean validaTef(Properties prop) {
		boolean bRet = false; 
		if (!prop.getProperty(TEF_ST_TRANSACAO,"").equals("0")) {
			Funcoes.mensagemErro(null,prop.getProperty(TEF_MSG_OPERADOR));
			bRet = false;
		}
		else
			bRet = true;
		return bRet;
	}
	public Object[] retImpTef(Properties prop) {
		Vector vRet = new Vector();
		String sLinha = null;
		for (int i=1;i<999;i++) {
		    if ((sLinha = prop.getProperty(IMP_BASE+Funcoes.strZero(""+i,3))) != null)
		        vRet.addElement(sLinha.replaceAll("\"",""));
		    else
		        break;
		}
		return vRet.toArray();
	}

}
