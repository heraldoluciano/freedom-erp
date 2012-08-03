package org.freedom.modulos.gms.test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.Date;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;

import org.freedom.infra.comm.CtrlPort;
import org.freedom.infra.driver.scale.AbstractScale;
import org.freedom.infra.driver.scale.ScaleResult;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.gms.business.object.TipoRecMerc;

public class TestBalancaHidro extends FFDialogo{
	private Date data;
	
	private BigDecimal peso1;
	
	private BigDecimal peso2;
	
	private SerialPort porta = null;

	private AbstractScale balanca = null;

	private String tipoprocrecmerc = null;

	private String driverbal = null;

	private Integer portabal = null;

	private Integer timeout = null;

	private Integer baundrate = null;

	private Integer databits = null;

	private Integer stopbits = null;

	private Integer parity = null;
	
	private boolean contingencia = false;
	
	private String mensagem = "";
	
	public static void main(String[] args){
		
		TestBalancaHidro test = new TestBalancaHidro();
		test.instanciaBalanca();		
	}
	
	private void setMensagem( String msg  ) {

		mensagem = msg ;

		System.out.println( "mensagem:" + msg );
		

	}

	private synchronized void buscaPeso() {

		try {

			if ( balanca != null ) {

				setMensagem( "Inicializando balança..." );

				Date data = null;
				Time hora = null;
				BigDecimal peso = null;

				if ( !contingencia ) {

					balanca.initialize( portabal, AbstractScale.TIMEOUT_ACK, baundrate, databits, stopbits, parity );
					// Usar thred apenas para balanca bci.
					Thread t = null;
					if ( balanca.isBufferized() ) {
						t = new Thread( balanca );
						t.start();

						Thread.sleep( 1000 );
					}

					setMensagem( "Recuperando dados da balança..." );

					balanca.readstring = false;

					//Thread.sleep( 500 );
					
					ScaleResult scaleresult = balanca.parseString();
					
					for (int i=0; i<10; i++) {
						if ( scaleresult!=null) {
							break;
						}
						Thread.sleep( 1000 );
						scaleresult = balanca.parseString();
						if (scaleresult!=null) {
							System.out.println("Peso no loop: "+i+" = "+scaleresult.getWeight());
						}
					}

					if (scaleresult==null) {
						System.out.println( "Não foi possível obter pesso da balança após 10 tentativas!" );
					} else {
						data = scaleresult.getDate();
						hora = scaleresult.getTime();
						peso = scaleresult.getWeight();
					}
/*					data = balanca.getDate();
					hora = balanca.getTime();
					peso = balanca.getWeight();
*/
				}
				// Modo de contingencia ativado
				else {

					balanca.inactivePort();

					data = new Date();
					hora = new Time( new Date().getTime() );
					peso = new BigDecimal( 0 );

				}

				if ( peso != null ) {
					if ( getPeso1().floatValue() > 0 && tipoprocrecmerc.equals( TipoRecMerc.PROCESSO_DESCARREGAMENTO.getValue() ) ) {
						setPeso2( peso );
					}
					else {
						setPeso1( peso );
					}

				}
				else {
					setMensagem( "Peso inválido na pesagem!" );
					return;
				}
				if ( data != null ) {
					 setData(data);
				}
				else {
					setMensagem( "Data inválida na pesagem!");
					return;
				}

				if ( hora != null ) {
					//txtHora.setVlrTime( hora );
				}
				else {
					setMensagem( "Hora inválida na pesagem!" );
					return;
				}

				if ( getPeso1().floatValue() > 0 && getPeso2().floatValue() == 0 && tipoprocrecmerc.equals( TipoRecMerc.PROCESSO_DESCARREGAMENTO.getValue() ) ) {
					setMensagem( "Aguardando segunda pesagem..." + "2a. Pesagem" );
				}
				else {
					setMensagem( "Pesagem realizada com sucesso!" );
				}

				// balanca.inactivePort();
				// balanca = null;

			}
			else {
				setMensagem( "Balança não inicializada!" );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void buscaParansBalanca() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sql.append( "select first 1 driverbal, portabal, speedbal, bitsbal, stopbitbal, paritybal " );
			sql.append( "from sgestacaobal " );
			sql.append( "where codemp=? and codfilial=? and codest=? and tipoprocrecmerc=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, Aplicativo.iNumEst );
			ps.setString( 4, tipoprocrecmerc );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				driverbal = rs.getString( "driverbal" );
				portabal = rs.getInt( "portabal" );
				baundrate = rs.getInt( "speedbal" );
				databits = rs.getInt( "bitsbal" );
				stopbits = rs.getInt( "stopbitbal" );
				parity = rs.getInt( "paritybal" );

			}
			else {
				setMensagem( "Balança não localizada!" );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	@ SuppressWarnings ( "unchecked" )
	private void instanciaBalanca() {

		try {

			if ( balanca != null ) {
				balanca.inactivePort();
			}

			buscaParansBalanca();

			Class<AbstractScale> classbalanca = ( (Class<AbstractScale>) Class.forName( StringFunctions.alltrim( driverbal ) ) );

			balanca = classbalanca.newInstance();

			setMensagem( "Balança " + balanca.getName() );

			buscaPeso();

		} catch ( Exception e ) {
			setMensagem( "Erro ao instanciar driver... " );
			e.printStackTrace();
		}

	}


	private void abrePorta() {

		try {

			CommPortIdentifier cp = CommPortIdentifier.getPortIdentifier( "/dev/ttyS0" );
			porta = (SerialPort) cp.open( "SComm", 1 );

			InputStream entrada = porta.getInputStream();

			porta.notifyOnDataAvailable( true );

			
		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	public void serialEvent( SerialPortEvent ev ) {

		try {

			if ( ev.getEventType() == SerialPortEvent.DATA_AVAILABLE ) {

				InputStream entrada = porta.getInputStream();
				int nodeBytes = 0;

				byte[] bufferLeitura = new byte[ 64 ];

				while ( entrada.available() > 0 ) {

					nodeBytes = entrada.read( bufferLeitura );

				}

				String leitura = new String( bufferLeitura );

				if ( bufferLeitura.length > 0 ) {
					System.out.print( leitura );
					parseString( leitura );
				}
				else {
					System.out.print( "nada lido!" );
				}

				System.out.println( "número de bytes lidos:" + nodeBytes );

			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private void parseString( String str ) {

		String strweight = "";
		String strdate = "";
		String strtime = "";

		try {

			int posicaobranca = str.lastIndexOf( " " );

			if ( posicaobranca < 24 && posicaobranca > -1 ) {
				str = str.substring( posicaobranca );
				str = StringFunctions.alltrim( str );
			}

			if ( str.length() >= 24 ) {

				if ( posicaobranca + 24 > str.length() ) {
					str = str.substring( posicaobranca );
				}
				else {
					str = str.substring( posicaobranca, 24 );
				}

				str = StringFunctions.alltrim( str );

				System.out.println( "Parcial analisada: " + str );

				String validador = str.substring( 11, 12 ) + str.substring( 14, 15 ) + str.substring( 19, 20 );

				if ( "//:".equals( validador ) ) {

					CtrlPort.getInstance().disablePort();

					// InputStream input = null;
					// CtrlPort ctrlport = CtrlPort.getInstance();
					// ctrlport.setActive(false);
					// input = ctrlport.getInput();

					// input.close();

					// ctrlport.disablePort();
					// ctrlport = null;

					// buffer = null;

					System.out.println( "Finalizou leitura e fechou a porta!" );

					strweight = str.substring( 0, 07 );
					strdate = str.substring( 9, 17 );
					strtime = str.substring( 17 );

					strtime = StringFunctions.clearString( strtime );

					System.out.println( "peso:" + ConversionFunctions.stringToBigDecimal( strweight ) );
					System.out.println( "data:" + ConversionFunctions.strDate6digToDate( strdate ) );
					System.out.println( "data:" + ConversionFunctions.strTimetoTime( strtime ) );
					// setWeight( ConversionFunctions.stringToBigDecimal( strweight ) );
					// setDate( ConversionFunctions.strDate6digToDate( strdate ) );
					// setTime( ConversionFunctions.strTimetoTime( strtime ) );

				}

			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public SerialPort getPorta() {
		return porta;
	}

	public void setPorta(SerialPort porta) {
		this.porta = porta;
	}

	public AbstractScale getBalanca() {
		return balanca;
	}

	public void setBalanca(AbstractScale balanca) {
		this.balanca = balanca;
	}

	public String getTipoprocrecmerc() {
		return tipoprocrecmerc;
	}

	public void setTipoprocrecmerc(String tipoprocrecmerc) {
		this.tipoprocrecmerc = tipoprocrecmerc;
	}

	public String getDriverbal() {
		return driverbal;
	}

	public void setDriverbal(String driverbal) {
		this.driverbal = driverbal;
	}

	public Integer getPortabal() {
		return portabal;
	}

	public void setPortabal(Integer portabal) {
		this.portabal = portabal;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getBaundrate() {
		return baundrate;
	}

	public void setBaundrate(Integer baundrate) {
		this.baundrate = baundrate;
	}

	public Integer getDatabits() {
		return databits;
	}

	public void setDatabits(Integer databits) {
		this.databits = databits;
	}

	public Integer getStopbits() {
		return stopbits;
	}

	public void setStopbits(Integer stopbits) {
		this.stopbits = stopbits;
	}

	public Integer getParity() {
		return parity;
	}

	public void setParity(Integer parity) {
		this.parity = parity;
	}

	public boolean isContingencia() {
		return contingencia;
	}

	public void setContingencia(boolean contingencia) {
		this.contingencia = contingencia;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setPeso1(BigDecimal peso1) {
		this.peso1 = peso1;
	}

	public void setPeso2(BigDecimal peso2) {
		this.peso2 = peso2;
	}

	public Date getData() {
		return data;
	}

	public BigDecimal getPeso1() {
		return peso1;
	}

	public BigDecimal getPeso2() {
		return peso2;
	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		instanciaBalanca();
		// abrePorta();



	}

}
