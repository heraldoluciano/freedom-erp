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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Properties;

import org.freedom.funcoes.Funcoes;

public class Tef {
	boolean bAtivo = false;
	File fTmp;
	File fEnvio;
	File fStatus;
	File fRetorno;
	static final String ARQ_TMP = "IntPos.tmp";
	static final String ARQ_ENVIO = "IntPos.001";
	static final String ARQ_STATUS = "IntPos.Sts";
	static final String ARQ_RETORNO = "IntPos.001";

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
	static final String TEF_ADMINISTRADORA = "040-000";
	static final String TEF_EOT = "040-000"; //"E"nd "O"f "T"rasaction. (final do arquivo)
	
	private long lIdent = 1;
	
	public Tef(String sPathEnv, String sPathRet) {
		fTmp = new File(sPathEnv+"/"+ARQ_TMP);
		fEnvio = new File(sPathEnv+"/"+ARQ_ENVIO);
		fStatus = new File(sPathRet+"/"+ARQ_STATUS);
		fRetorno = new File(sPathRet+"/"+ARQ_RETORNO);
	}
	public boolean enviaArquivo(String sConteudo[]) {
		boolean bRet = false;
		
		if (fTmp.exists())
			fTmp.delete();
		try {
			PrintStream psEnvio = new PrintStream(new FileOutputStream(fTmp));
			for (int i=0;i<sConteudo.length;i++)
				psEnvio.println(sConteudo[i]);
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
		return true;
	}
	public boolean existeStatus(String sCab) {
		return existeInfo(sCab,fStatus,7);//7 é padrao do GP.
	}
	public boolean existeRetorno(String sCab) {
		return existeInfo(sCab,fRetorno,15);
	}
	public boolean existeInfo(String sCab,File fArq, int iTentativas) {
		String sLinha;
		boolean bRet = false;
		boolean bCabOK = false;
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
			bCabOK = false;
			try {
				BufferedReader br = new BufferedReader(new FileReader(fArq));
				while ((sLinha = br.readLine()) != null) {
					if (sLinha.equals(TEF_HEADER+" = "+sCab))
						bCabOK = true; //Armazenado em uma variavel a leitura da outra linha só acontecerá no próximo loop.
					if (bCabOK && sLinha.equals(TEF_IDENTIFICACAO+" = "+lIdent))
						bRet = true;
				}
				br.close();
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
		}
		catch(IOException err) {
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
		bRet = enviaArquivo(new String[] {
						(TEF_HEADER + " = "+ "CRT"),
						(TEF_IDENTIFICACAO + " = "+ lIdent),
						(TEF_DOC_FISCAL + " = "+ iNumCupom),
						(TEF_VAL_TOTAL + " = "+ (Funcoes.transValor(bigVal,12,2,false)).trim())
					}
				);
		if (!bRet || 
		    !existeStatus("CRT") || 
			!existeRetorno("CRT"))
			return null;
		
		return leRetorno();
	}
}
