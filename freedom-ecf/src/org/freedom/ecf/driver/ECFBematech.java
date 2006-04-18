/*
 * Classe de driver para impressoras Bematech
 * Autor: Robson Sanchez/Setpoint Informática Ltda.
 * Data: 05/04/2006
 */
package org.freedom.ecf.driver;

public class ECFBematech extends ECFDriver {
	
	public ECFBematech(int com) {
		super(com);
	}
	public byte[] preparaCmd(byte[] CMD) {
		int tamCMD = CMD.length;
		int tam = tamCMD + 2;
		byte NBL = (byte) (tam % 256);
		byte NBH = (byte) (tam / 256);
		byte CSL = 0;
		byte CSH = 0;
		int soma = 0;
		byte[] retorno = new byte[5 + tamCMD];
		retorno[0] = STX;
		retorno[1] = NBL;
		retorno[2] = NBH;
		for (int i=0; i<tamCMD; i++) {
			soma += CMD[i];
			retorno[i+3] = CMD[i];
		}
		CSL = (byte) (soma % 256);
		CSH = (byte) (soma / 256);
        retorno[retorno.length-2] = CSL;
        retorno[retorno.length-1] = CSH;
		return retorno;
	}
	
	public int leituraX() {
		byte[] CMD = {ESC,6};
		return executaCmd(CMD);
	}

	public int executaCmd(byte[] CMD) {
		byte[] retorno = null;
		CMD = preparaCmd(CMD);
		retorno = enviaCmd(CMD);
		return checkRetorno(retorno);
	}
	
	public int checkRetorno(byte[] bytes) {
		int retorno = 0;
		bytesLidos = null;
		byte ack = 0;
		byte st1 = 0;
		byte st2 = 0;
		if (bytes!=null) {
		   if (bytes.length>3) 
			   bytesLidos = new byte[bytes.length-3];
		   for (int i=0; i<bytes.length; i++) {
			   if (i==0)
			       ack = bytes[i];
			   else if (i==1)
				   st1 = bytes[i];
			   else if (i==2)
				   st2 = bytes[i];
			   else
				   bytesLidos[i-3] = bytes[i];
		   }
		   if (ack==ACK) {
			   retorno = 1;
		   }
		   else {
			   retorno = -27; // Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)
			   if (st1>127) 
				   st1 -= 128;
			   if (st1>63) 
				   st1 -= 64;
			   if (st1>31)
				   st1 -= 32;
			   if (st1>15)
				   st1 -= 16;
			   if (st1>7)
				   st1 -= 8;
			   if (st1>3)
				   st1 -= 4;
			   if (st1>1)
				   st1 -= 2;
			   if (st1>0) {
				   st1 -= 1;
				   retorno = -2; //"Parâmetro inválido na função. ou Número de parâmetros inválido na funçao"
			   }
			   if (st2>127) {
				   retorno = -2; //"Parâmetro inválido na função."
				   st2 -= 128;
			   }
			   if (st2>63) 
				   st2 -= 64;
			   if (st2>31)
				   st2 -= 32;
			   if (st2>15)
				   st2 -= 16;
			   if (st2>7)
				   st2 -= 8;
			   if (st2>3)
				   st2 -= 4;
			   if (st2>1)
				   st2 -= 2;
			   if (st2>0) {
				   st2 -= 1;
				   retorno = -2; //"Parâmetro inválido na função. ou Número de parâmetros inválido na funçao"
			   }
		   }
		}
		return retorno;
	}
	/*
	 *        case 0: sMensagem = "Erro de comunicação física"; break;
       case 1: sMensagem = ""; break;
       case -2: sMensagem = "Parâmetro inválido na função."; break;
       case -3: sMensagem = "Aliquota não programada"; break;
       case -4: sMensagem = "O arquivo de inicialização BEMAFI32.INI não foi encontrado no diretório de sistema do Windows"; break;
       case -5: sMensagem = "Erro ao abrir a porta de comunicação"; break;
       case -8: sMensagem = "Erro ao criar ou gravar no arquivo STATUS.TXT ou RETORNO.TXT"; break;
       case -27: sMensagem = "Status da impressora diferente de 6,0,0 (ACK, ST1 e ST2)"; break;
       case -30: sMensagem = "Função não compatível com a impressora YANCO"; break;
       case -31: sMensagem = "Forma de pagamento não finalizada"; break;
       default : sMensagem = "Retorno indefinido: "+iRetorno; break;
	 */
}
