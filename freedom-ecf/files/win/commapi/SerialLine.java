/**
* Version 03.09.2003
*
* @author Leandro Heleno Möller
*
*/
import java.util.*;
import java.io.*;
import javax.comm.*;

public class SerialLine implements SerialPortEventListener,ActionListener
{
	// program variables
	private String port;
	private int speed, byteSize, parity, stopBits, readTimeout;
	private int iTransmitter,iReceiver,iChar,countReceiver=0,countTransmitter=0;
	private String directory=System.getProperty("user.dir")+File.separator;
	private boolean receive;

	// terminal variables
	private Vector components;
	private int width=200,height=300;
	private int machine=0,command,index=0;
	private String source;

	// serial interface variables
	private CommPortIdentifier portId;
	private SerialPort serialPort;
	private OutputStream outputStream;
	private InputStream inputStream;

	// file access
	private BufferedWriter received;
	private String transmitFile;
	private int receiveBytes;

	/**
	* @param args the command line arguments
	*/
	public static void main(String args[])
	{
		new SerialLine(args);
	}

    /**
    * Creates new form SerialLine
    */
    public SerialLine(String args[])
    {
        //captura as informações da comunicaçao com a serial
		getConfig();

		//conecta com a serial
		connect();

		if(args[0].equals("transmit") )
		{
			transmitFile=args[1];
			transmit(transmitFile);
		}
		else if(args[0].equals("receive") )
		{
			try
			{
				receiveBytes=Integer.valueOf(args[1]).intValue();
			}
			catch(NumberFormatException nfe)
			{
				System.out.println("Número de bytes a serem recebidos inválido. Entre com um número positivo");
			}
			receive=true;
		}
		else
		{
			System.out.println("Entre com:");
			System.out.println("java SerialLine transmit <caminho do arquivo a ser transmitido>");
			System.out.println("ou");
			System.out.println("java SerialLine receive <numero positivo que corresponde ao numero de bytes a serem recebidos>");
			System.exit();
		}
    }

	/**
	* Read File config.cfg with the configurations
	*/
	public void getConfig()
	{
		try
		{
			String temp="";

			//captura o diretório atual
			String caminho=System.getProperty("user.dir")+File.separator+"config.cfg";

			//abre o arquivo selecionado
			BufferedReader br=open(caminho);

			//arquivo invalido, saindo do método
			if(br==null)
				return;

			//colocando todo o texto em uma linha só
			while(true)
			{
				temp=read(br);

				if(temp==null)
					break;
				if(temp.indexOf(File.separator)!=-1)
					directory=temp;
				else if(temp.indexOf("COM")!=-1)
				{
					if(temp.indexOf("COM1")!=-1)
						com="COM1";
					else if(temp.indexOf("COM2")!=-1)
						com="COM2";
					else if(temp.indexOf("COM3")!=-1)
						com="COM3";
					else if(temp.indexOf("COM4")!=-1)
						com="COM4";
				}
				else if(temp.indexOf("baud")!=-1)
				{
					if(temp.indexOf("baud600")!=-1)
						speed=600;
					else if(temp.indexOf("baud1200")!=-1)
						speed=1200;
					else if(temp.indexOf("baud2400")!=-1)
						speed=2400;
					else if(temp.indexOf("baud4800")!=-1)
						speed=4800;
					else if(temp.indexOf("baud9600")!=-1)
						speed=9600;
					else if(temp.indexOf("baud19200")!=-1)
						speed=19200;
					else if(temp.indexOf("baud38400")!=-1)
						speed=38400;
					else if(temp.indexOf("baud56000")!=-1)
						speed=56000;
					else if(temp.indexOf("baud115200")!=-1)
						speed=115200;
				}
				else if(temp.indexOf("stop")!=-1)
				{
					if(temp.indexOf("stopBits10")!=-1)
						stopBits=1;
					else if(temp.indexOf("stopBits20")!=-1)
						stopBits=2;
					else if(temp.indexOf("stopBits15")!=-1)
						stopBits=3;
				}
				else if(temp.indexOf("byteSize")!=-1)
				{
					if(temp.indexOf("byteSize4")!=-1)
						byteSize=4;
					else if(temp.indexOf("byteSize5")!=-1)
						byteSize=5;
					else if(temp.indexOf("byteSize6")!=-1)
						byteSize=6;
					else if(temp.indexOf("byteSize7")!=-1)
						byteSize=7;
					else if(temp.indexOf("byteSize8")!=-1)
						byteSize=8;
				}
				else if(temp.indexOf("parity")!=-1)
				{
					if(temp.indexOf("parityNo")!=-1)
						parity=1;
					else if(temp.indexOf("parityOdd")!=-1)
						parity=2;
					else if(temp.indexOf("parityEven")!=-1)
						parity=3;
					else if(temp.indexOf("parityMark")!=-1)
						parity=4;
					else if(temp.indexOf("paritySpace")!=-1)
						parity=5;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Impossible to read config.cfg file");
		}
	}

	/**
	* Connect
	*/
	public void connect()
	{
		//cria um arquivo com o que foi lido da serial
		if(receive==true)
			received=create("received.txt");

		readTimeout=2000;

		if(!init(port,speed,byteSize,parity,stopBits,readTimeout))
		{
			System.out.println("Impossible to communicate with the serial interface");
			return;
		}
	}

	/**
	* Initiate serial
	* @param port serial port communication to be used
	* @param speed serial speed communication to be used
	* @param byteSize byte size to be used in the serial communication
	* @param parity parity to be used in the serial communication
	* @param stopBits number of stop bits to be used in the serial communication
	* @param readTimeout timeout of serial communication
	*/
	public boolean init(String port,int speed,int byteSize,int parity,int stopBits,int readTimeout)
	{
		try
		{
			portId = CommPortIdentifier.getPortIdentifier(port);
			serialPort = (SerialPort)portId.open("SerialLine", readTimeout);
			inputStream = serialPort.getInputStream();
			outputStream = serialPort.getOutputStream();
			serialPort.setSerialPortParams(speed,byteSize,stopBits,parity);
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		}
		catch (PortInUseException e) {return false;}
		catch (IOException e) {return false;}
		catch (UnsupportedCommOperationException e) {return false;}
		catch (TooManyListenersException e) {return false;}
		catch (NoSuchPortException nspe) {return false;}
		catch (Exception e) {return false;}
		return true;
	}

	/**
	*Capture any serial events of the serial interface
	*/
	public void serialEvent(SerialPortEvent event)
	{
		byte[] readBuffer = new byte[20];
		int intData,numBytes=0;
		String stringData;

		try
		{
			//captura o numero de bytes recebidos no stream
			while (inputStream.available() > 0)
			{
				numBytes = inputStream.read(readBuffer);
			}

			//imprime todos os bytes que chegaram
			for(int i=0;i<numBytes;i++)
			{
				//passa byte(-128 .. +127) para positivo
				if(readBuffer[i]<0)
					intData=readBuffer[i]+256;
				else
					intData=readBuffer[i];

				//passa de inteiro para hexadecimal
				stringData=(Integer.toHexString(intData)).toUpperCase();

				//verifica se deve completar o valor com um zero
				if(stringData.length()==1)
					stringData="0" + stringData;

				//imprime na janela de recepcao
				appendReceiver(stringData);
			}
		}
		catch (IOException e)
		{
			System.out.println("Impossible to read by the serial interface");
		}
		catch (NumberFormatException nfe)
		{
			System.out.println("Invalid number format");
		}
	}

	/**
	* Append a string in the receiver box
	*/
    private void appendReceiver(String text)
    {
		if(iReceiver<25)
		{
			write(received,text+ " ");
        	iReceiver++;
        	countReceiver++;
		}
        else
        {
			write(received,"\n" + text+ " ");
			iReceiver=1;
			countReceiver++;
		}

		if(countReceive==receiveBytes)
			exit();
    }


	/**
	* Send a string by the serial interface
	*/
	public void sendSerial(String string)
	{
		//compacta o texto
		String sendString="";
		String compactado=compactText(string);
		int sendInt=0;

		if(!verifyChars(compactado))
		{
			System.out.println("It was detected a invalid byte to send by the serial");
			return;
		}

		//envia um byte de cada vez, ou seja, dois caracteres
		for(int i=0;i<=compactado.length()-2;i=i+2)
		{
			sendString=compactado.substring(i,i+2);
			write(sendString);
		}
	}

	/**
	*Convert the hexadecimal value to a decimal value and send by the serial interface
	*/
	public void write(String data)
	{
		try
		{
			String s=hex2dec(data);
			int i=Integer.valueOf(s).intValue();
			if(i>=128)
				i=i-256;
			outputStream.write(i);
		}
		catch (IOException e)
		{
			System.out.println("Impossible to write by the serial interface");
		}
	}


	/**
	* Check if the string has only hexadecimal values
	*/
	public boolean verifyChars(String string)
	{
		char c;
		for(int i=0;i<string.length();i++)
		{
			c=string.charAt(i);
			if( !( (c>='0' && c<='9') || ( c>='A' && c<='F') || ( c>='a' && c<='f') ) )
				return false;
		}
		return true;
	}

	/**
	* Load the requested file and append in the transmitter box
	*/
	private String loadFile(String caminho)
	{
		//variáveis locais
		String texto="";
		String temp;

		//abre o arquivo selecionado
		BufferedReader br=open(caminho);

		//arquivo invalido, saindo do método
		if(br==null)
			return;

		//colocando todo o texto em uma linha só
		while(true)
		{
			temp=read(br);
			if(temp==null)
				break;
			if(temp.indexOf("/")!=-1)
				temp=temp.substring(0,temp.indexOf("/"));
			if(temp.indexOf(";")!=-1)
				temp=temp.substring(0,temp.indexOf(";"));
			if(temp.indexOf("#")!=-1)
				temp=temp.substring(0,temp.indexOf("#"));
			if(temp.indexOf("\\t")!=-1)
				temp=temp.substring(0,temp.indexOf("\\t"));
			texto=texto+temp;
		}

		//organiza byte a byte o texto
		return organizeText(texto);
	}


	/**
	* Return a line of the file or null if it is the end of the file
	*/
	public String read(BufferedReader br)
	{
		try
		{
			return br.readLine();
		}
		catch(IOException ioe)
		{
			System.out.println("Impossible to access file");
		}
		return null;
	}

	/**
	* Return a pointer of a file
	*/
	public BufferedReader open(String inFile)
	{
		FileInputStream fis;
		BufferedReader br=null;
		try
		{
			fis=new FileInputStream(inFile);
			br=new BufferedReader(new InputStreamReader(fis));
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("File " +inFile+ " not found");
		}
		return br;
	}

	/**
	* Compact the text and organize it byte a byte
	*/
	private String organizeText(String texto)
	{
		//compacta o texto
		String text=compactText(texto);
		//organiza o texto passado por parâmetro em bytes
		String correta=organizeInBytes(text);
		return correta;
	}

	/**
	* Put a text in a string
	*/
	private String compactText(String text)
	{
		int pos;
		String compactada=text;

		//retira os espaços em branco da string
		while(true)
		{
			pos=compactada.indexOf(" ");
			if(pos==-1)
				break;
			else
				compactada=compactada.substring(0,pos) + compactada.substring(pos+1);
		}

		//retira as novas linhas da string
		while(true)
		{
			pos=compactada.indexOf("\n");
			if(pos==-1)
				break;
			else
				compactada=compactada.substring(0,pos) + compactada.substring(pos+1);
		}

		return compactada;

	}

	/**
	* Organize text byte a byte
	*/
	private String organizeInBytes(String text)
	{
		String correta="";
		countTransmitter=1;
		//coloca um espaço a cada dois caracteres
		for(int i=0;i<text.length()-2;i=i+2)
		{
			correta=correta + text.substring(i,i+2) + " ";
			countTransmitter++;
		}

		if(text.length()%2==1)
			correta = correta + text.substring(text.length()-1);
		else
			correta = correta + text.substring(text.length()-2);
		return correta;
	}

	/**
	* Exit the aplication
	*/
	private void exit()
	{
		//fecha a porta serial
		if(!miConnect.isEnabled())
			serialPort.close();

		if(receive==true)
		{
			//fecha o arquivo
			try
			{
				received.close();
			}
			catch(IOException ioe)
			{
				System.out.println("Impossible to close received.txt");
			}
		}

		//sai do programa
		System.exit(0);
	}

	/**
	* Write a string in a file
	*/
	public void write(BufferedWriter bw,String x)
	{
		try
		{
			bw.write(x,0,x.length());
		}
		catch(IOException ioe)
		{
			System.out.println("Impossible to write file");
		}
	}

	/**
	* Create a file
	*/
	public BufferedWriter create(String caminho)
	{
		FileOutputStream fos;
		BufferedWriter bw=null;
		try
		{
			fos=new FileOutputStream(caminho);
			bw=new BufferedWriter(new OutputStreamWriter(fos));
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("Impossible to create file");
		}
		return bw;
	}

	/**
	* Converts a hexadecimal string to a decimal string
	*/
	public String hex2dec(String valor)
	{
		valor=valor.toUpperCase();
		char[] carac ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		long aux=0;
		for (int j=0;j<valor.length();j++)
			for (int i=0;i<16;i++)
				if ( carac[i]==valor.charAt(valor.length()-j-1))
					aux=aux+(int)Math.pow(16,j)*i;
		return(""+aux);
	}

	/**
	* Disconnect
	*/
	public void disconnect()
	{
		//fecha a porta serial
		serialPort.close();
	}

	/**
	* Check if it is attempt to send data
	*/
	public void transmit(String transmitFile)
	{
		loadFile(transmitFile);
		sendSerial(taTransmitter.getText());
	}
}