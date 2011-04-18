package org.freedom.library.business.component;

import java.math.BigDecimal;

import org.freedom.library.functions.Funcoes;

public class Itau extends Banco {
	
	private String agencia = "";
	private String conta = "";
	private String codcli = "";
	private String carteira = "";
	private int moeda = 9; // Real
	private String codigofornagencia = "";
	private String dvcodigofornagencia = "";
	private Long fatvenc = new Long(0);
	private long doc = 0;
	private long seq = 0;
	private long nparc = 0;
	private long rec = 0;
	private String tpnossonumero = "D";
	private String modalidade = "";
	private String convenio = "";
	private BigDecimal valorTitulo;
	
	public Itau() {
		super();
	}
	
	public Itau(String codbanco, String codmoeda, String dvbanco, Long fatvenc, BigDecimal vlrtitulo, String tpnossonumero, String convenio,
			Long doc, Long seq, Long rec, Long nparc, String agencia, String contap, String carteira, String modalidade) {

		setMoeda(new Integer(codmoeda).intValue());
		setTpnossonumero(tpnossonumero);
		setValorTitulo(vlrtitulo);
		setCodcli(convenio);
		setConvenio(convenio);
		setAgencia(agencia);
		setCarteira(carteira);
		setFatvenc(fatvenc);
		setRec(rec);
		setNparc(nparc);
		setModalidade(modalidade);
		setConta(contap);
		setSeq(seq);
		setDoc(doc);
	}

	public BigDecimal getValorTitulo() {
		return valorTitulo;
	}

	public void setValorTitulo(BigDecimal valorTitulo) {
		this.valorTitulo = valorTitulo;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getCodcli() {
		return codcli;
	}

	public void setCodcli(String codcli) {
		this.codcli = codcli;
	}

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public int getMoeda() {
		return moeda;
	}

	public void setMoeda(int moeda) {
		this.moeda = moeda;
	}

	public String getCodigofornagencia() {
		return codigofornagencia;
	}

	public void setCodigofornagencia(String codigofornagencia) {
		this.codigofornagencia = codigofornagencia;
	}

	public String getDvcodigofornagencia() {
		return dvcodigofornagencia;
	}

	public void setDvcodigofornagencia(String dvcodigofornagencia) {
		this.dvcodigofornagencia = dvcodigofornagencia;
	}

	public Long getFatvenc() {
		return fatvenc;
	}

	public void setFatvenc(Long fatvenc) {
		this.fatvenc = fatvenc;
	}

	public long getDoc() {
		return doc;
	}

	public void setDoc(long doc) {
		this.doc = doc;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public long getNparc() {
		return nparc;
	}

	public void setNparc(long nparc) {
		this.nparc = nparc;
	}

	public long getRec() {
		return rec;
	}

	public void setRec(long rec) {
		this.rec = rec;
	}

	public String getTpnossonumero() {
		return tpnossonumero;
	}

	public void setTpnossonumero(String tpnossonumero) {
		this.tpnossonumero = tpnossonumero;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	@Override
	public String geraCodBar(String codbanco, String codmoeda, String dvbanco,
			Long fatvenc, BigDecimal vlrtitulo, String convenio,
			String tpnossonumero, Long doc, Long seq, Long rec, Long nparc,
			String agencia, String conta, String carteira, String modalidade) {
		
		final StringBuffer barcode = new StringBuffer();
		final StringBuffer parte1 = new StringBuffer();
		final StringBuffer parte2 = new StringBuffer();

		final String bufCodbanco = strZero(codbanco, 3);
		final String bufCodmoeda = strZero(codmoeda, 1);
		final String bufFatvenc = strZero(fatvenc.toString(), 4);
		final String bufVlrtitulo = geraVlrtitulo(vlrtitulo);
		final String bufConvenio = geraConvenio(convenio);
		final String bufModalidade = strZero(modalidade, 2);
		final String bufNossoNumero = geraNossoNumero(tpnossonumero, bufModalidade, bufConvenio, doc, seq, rec, nparc, false);
		final String bufAgencia = strZero(getCodSig(agencia)[0], 4);
		final String bufConta = strZero(getCodSig(conta)[0], 8);
		final String bufCarteira = strZero(carteira, 2);

		parte1.append(bufCodbanco);
		parte1.append(bufCodmoeda);
		parte2.append(bufFatvenc);
		parte2.append(bufVlrtitulo);

		if ("21".equals(bufModalidade)) {
			// Formato do código de barras para convênios da carteira sem
			// registro
			// 16 e 18 - com nossó número livre de 17 posições
			parte2.append(bufConvenio);
			parte2.append(bufNossoNumero);
			parte2.append(bufModalidade);
		}
		else if (bufConvenio.length() >= 7) {
			// Código de barras bara convêncios acima de 1.000.000
			parte2.append("000000");
			parte2.append(bufNossoNumero);
			parte2.append(bufCarteira);
		}
		else {
			// Formato do código de barras para convênios com 4 ou 6 posições
			parte2.append(bufNossoNumero);
			parte2.append(bufAgencia);
			parte2.append(bufConta);
			parte2.append(bufCarteira);
		}

		barcode.append(parte1);
		barcode.append(digVerif(parte1.toString() + parte2.toString(), 11));
		barcode.append(parte2);

		return barcode.toString();
	}

	@Override
	public String geraCodBar() {
		String[] agencia = getCodSig(getAgencia());
		String[] conta = getCodSig(getConta());
		
		StringBuilder campo1 = new StringBuilder();
		campo1.append(ITAU);
		campo1.append("9");
		campo1.append(getCarteira().trim());
		//campo1.append("109");
		
		String nossonumero = getNossoNumero();
		
		campo1.append(nossonumero.substring(0, 2));
		//campo1.append(digVerif(campo1.toString(), 10));
		
		StringBuilder campo2 = new StringBuilder();
		campo2.append(nossonumero.substring(2, nossonumero.length()));
		campo2.append(digVerif( agencia[0].trim()  + conta[0].trim() + getCarteira().trim() + nossonumero, 10));
		campo2.append(agencia[0].substring(0,3));
		//campo2.append(digVerif(campo2.toString(), 10));
		
		StringBuilder campo3 = new StringBuilder();
		campo3.append(agencia[0].substring(3,4));
		campo3.append(conta[0] + conta[1]);
		campo3.append("000");
		//campo3.append(digVerif(campo3.toString(), 10));
		
		StringBuilder codBarr = new StringBuilder();
		codBarr.append(campo1.toString());
		codBarr.append(campo2.toString());
		codBarr.append(campo3.toString());
		
		StringBuilder campo4 = new StringBuilder();
		campo4.append(digVerif(codBarr.toString(), 10));
		
		codBarr.append(campo4.toString());
		
		StringBuilder campo5 = new StringBuilder();
		campo5.append( strZero(getFatvenc().toString(), 4) );
		campo5.append( geraVlrtitulo(getValorTitulo()) );
		
		codBarr.append(campo5.toString());
		
		return codBarr.toString();
	}
	
	@Override
	public String geraLinhaDig(String codbar, Long fatvenc, BigDecimal vlrtitulo) {
		StringBuilder linhaDig = new StringBuilder();
		
		String campo1 = codbar.substring(0, 9);
		String campo2 = codbar.substring(9, 19);
		String campo3 = codbar.substring(19, 29);
		String campo4 = codbar.substring(29, 30);
		String campo5 = codbar.substring(30, codbar.length());
		
		linhaDig.append(campo1.substring(0,5));
		linhaDig.append(".");
		linhaDig.append(campo1.substring(5,9));
		linhaDig.append(digVerif(campo1, 10));
		linhaDig.append(" ");
		
		linhaDig.append(campo2.substring(0,5));
		linhaDig.append(".");
		linhaDig.append(campo2.substring(5,10));
		linhaDig.append(digVerif(campo2, 10));
		linhaDig.append(" ");
		
		linhaDig.append(campo3.substring(0,5));
		linhaDig.append(".");
		linhaDig.append(campo3.substring(5,10));
		linhaDig.append(digVerif(campo3, 10));
		linhaDig.append(" ");
		
		linhaDig.append(campo4);
		linhaDig.append(" ");
		
		linhaDig.append(campo5);
		
		return linhaDig.toString();
	}

	@Override
	public String geraNossoNumero(String tpnossonumero, String modalidade,
			String convenio, Long doc, Long seq, Long rec, Long nparc) {
		return geraNossoNumero(tpnossonumero, modalidade, convenio, doc, seq, rec, nparc, true);
	}

	@Override
	public String geraNossoNumero(String tpnossonumero, String modalidade,
			String convenio, Long doc, Long seq, Long rec, Long nparc,
			boolean comdigito) {
		return geraNossoNumero(tpnossonumero, modalidade, convenio, doc, seq, rec, nparc, comdigito, false);
	}

	@Override
	public String geraNossoNumero(String tpnossonumero, String modalidade,
			String convenio, Long doc, Long seq, Long rec, Long nparc,
			boolean comdigito, boolean comtraco) {
		StringBuffer retorno = new StringBuffer();

		retorno.append(seq);
		retorno.append(rec);
		retorno.append(nparc);
		
		StringBuilder nossoNumero =  new StringBuilder();
		nossoNumero.append( Funcoes.completaTexto(retorno.toString(), 7, "0") );
		
		if(comdigito){
			String dig = digVerif(nossoNumero.toString(), 10);
			if(comtraco)
				nossoNumero.append("-");
				
			nossoNumero.append( dig );
		}
		
		return nossoNumero.toString();
	}
	
	public String getNossoNumero() {
		return geraNossoNumero(getTpnossonumero(), getModalidade(), getConvenio(), getDoc(), getSeq(), getRec(), getNparc());
	}
	
	@Override
	public String getNossoNumeroFormatted() {
		return super.getNossoNumeroFormatted();
	}

	@Override
	public String[] getCodSig(String codigo) {
		final String[] retorno = new String[2];
		final StringBuffer buffer = new StringBuffer();
		final String valido = "0123456789X";

		for (int i = 0; i < codigo.length(); i++) {

			if (valido.indexOf(codigo.charAt(i)) > -1) {
				buffer.append(codigo.charAt(i));
			}
			else if (codigo.charAt(i) == '-') {
				retorno[0] = buffer.toString();
				buffer.delete(0, buffer.length());
			}
		}
		if (retorno[0] == null) {
			retorno[0] = buffer.toString();
			retorno[1] = "";
		}
		else {
			retorno[1] = buffer.toString();
		}

		return retorno;
	}

	@Override
	public String getNumCli(String tpnossonumero, String modalidade,
			String convenio, Long doc, Long seq, Long rec, Long nparc) {
		StringBuffer retorno = new StringBuffer();

		if ("21".equals(modalidade)) {
			retorno.append(getNumCli(tpnossonumero, doc, seq, rec, nparc, 17));
		}
		else if (convenio.length() <= 4) {
			retorno.append(getNumCli(tpnossonumero, doc, seq, rec, nparc, 6));
		}
		else if (convenio.length() == 6) {
			retorno.append(getNumCli(tpnossonumero, doc, seq, rec, nparc, 5));
		}
		else {
			retorno.append(getNumCli(tpnossonumero, doc, seq, rec, nparc, 10));
		}

		return retorno.toString();
	}

	@Override
	public String digVerif(String codigo, int modulo) {
		return digVerif(codigo, modulo, false);
	}

	@Override
	public String digVerif(String codigo, int modulo, boolean digx) {
		int[] peso;

		if (modulo == 10) {
			peso = new int[2];
			peso[0] = 2;
			peso[1] = 1;
		} else {
			peso = new int[8];
			for (int i = peso.length - 1; i > -1; i--) {
				peso[i] = peso.length - ( i - 1 );
			}
		}

		int soma = 0;
		int calc = 0;
		int posi = 0;
		int resto = 0;
		String str = "";
		String dig = null;

		for (int i = codigo.length() - 1; i > -1; i--) {
			if (modulo == 11) {
				calc = Integer.parseInt(codigo.substring(i, i + 1)) * ( peso[posi] );
			} else {
				// transforma o valor do produto em String
				str = String.valueOf(Integer.parseInt(codigo.substring(i, i + 1)) * ( peso[posi] ));
				// soma os valores. Exemplo: para resultado do produto = 14
				// (1+4=5).
				calc = 0;
				for (int s = 0; s < str.length(); s++) {
					calc += Integer.parseInt(str.substring(s, s + 1));
				}
			}
			soma += calc;
			posi++;
			if (posi >= peso.length) {
				posi = 0;
			}
		}

		resto = soma % modulo;

		if (modulo == 10) {
			dig = String.valueOf(modulo - resto);
		} else {
			dig = String.valueOf(resto);
		}
		
		if (modulo == 10 && "10".equals(dig)) {
			dig = "0";
		} else if (modulo == 11 && "10".equals(dig) && digx) {
			dig = "X";
		} else if (modulo == 11 && "0-1-10-11".indexOf(dig) > -1 && !digx) {
			dig = "1";
		}

		return dig;
	}
	
}
