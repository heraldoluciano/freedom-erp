/**
 * @version 10/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)JTextFieldPad.java <BR>
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
 * Classe padrão para entrada de dados.
 */

package org.freedom.componentes;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JTextField;
import org.freedom.acao.EditEvent;
import org.freedom.acao.EditListener;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.DLBuscaEstoq;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.DLF2;
import org.freedom.telas.DLF3;
import org.freedom.telas.FAtalhos;

public class JTextFieldPad extends JTextField implements FocusListener, KeyListener, EditListener {
  public static final int TP_NONE = -1;
  public static final int TP_STRING = 0;
  public static final int TP_DATE = 1;
  public static final int TP_TIME = 2;
  public static final int TP_TIMESTAMP = 3;
  public static final int TP_INTEGER = 4;
  public static final int TP_DOUBLE = 5;
  public static final int TP_FLOAT = 6;
  public static final int TP_DECIMAL = 7;
  public static final int TP_NUMERIC = 8;
  public static final int TP_BOOLEAN = 10;
  public static final int TP_BYTES = 11;
  public static final int MC_NADA = -111;
  public static final int MC_FONEDDD = 110;
  public static final int MC_FONE = 111;
  public static final int MC_CNPJ = 112;
  public static final int MC_CEP = 113;
  public static final int MC_CPF = 114;
  public static final int MC_RG = 115;
  public static final String PR_TEXTO = "texto"; 
  private EditListener editLis = this;
  private GregorianCalendar data = new GregorianCalendar();
  private boolean bPK = false;
  private boolean bFK = false;
  public  int tipoCampo = TP_NONE; 
  private ListaCampos lcTxt = null;
  private ListaCampos lcTabExt = null;
  private boolean bEnterSai = true;
  private char pontoDecMask = Funcoes.getPontoDec();
  private DLF3 dlBuscaAdic = null; 
  public  int iTamanho = 0;
  public  int iDecimal = 0;
  public  int iMascara = -1;
  boolean bValido = false;
  boolean limpa = false;
  boolean bloquear = false;
  boolean bMasc = false;
  boolean runDLF2 = true;
  boolean bRequerido = false;
  boolean bSoLeitura = false;
  boolean bAtivo = true;
//  boolean bBuscaAdicGrid = false;
  String sMasc = "";
  String trans = "";
  String sNomeCampo = "";   
  String sLabel = "";
  String sValAnt = "";
  public JTextFieldPad (int tipo, int tam, int dec) {
    addFocusListener(this);
    addKeyListener(this);
//    setPonto();
    if (( tipo >= TP_INTEGER) && (tipo <= TP_NUMERIC))
      setHorizontalAlignment(RIGHT);
    else 
      setHorizontalAlignment(LEFT);
    iTamanho = tam;
    iDecimal = dec;
    tipoCampo = tipo;    
  }
  public boolean getAtivo() {
    return bAtivo;
  }
  public void setAtivo(boolean b) {
    if (b) {
      setEditable(true);
      bAtivo = true;
    }
    else {
      setEditable(false);
      bAtivo = false;
    }
  }
  public void setSoLeitura(boolean b) {
    bSoLeitura = b;
    if (b) {
      setEditable(false);
      bAtivo = false;
    }
    else {
      setEditable(true);
      bAtivo = true;
    }
  }
  public boolean isFocusable() {
    return (bAtivo);
  }
  public boolean getSoLeitura() {
    return bSoLeitura;
  }
  public void setPKFK(boolean pk, boolean fk) {
     bPK = pk;
     bFK = fk;
  }
  
  public boolean ehPK() {
    if ( bPK )
       return true;
     return false;
  }

  public boolean ehFK() {
    if ( bFK )
       return true;
    return false;
  }
  
  public void setEnterSai(boolean bSai) {
    bEnterSai = bSai;
  }
  public void setBuscaAdic(DLF3 dl) {
	  dlBuscaAdic = dl;
  }
  
  private boolean caracValido(char caract) {
     boolean retorno = true;
     if (caract == KeyEvent.VK_BACK_SPACE) {
     	retorno = true;
     }
     else if (tipoCampo==TP_INTEGER) {
       if ( (Character.isDigit(caract)==false) && (caract!='-') ) 
          retorno = false;
     }
     else if ((tipoCampo==TP_DECIMAL) ||
     		    (tipoCampo==TP_NUMERIC) ||
     		    (tipoCampo==TP_DOUBLE)){
        retorno = false;
       if ( (Character.isDigit(caract)) ) {
         retorno = true;
       }
       else if ((caract == '.') | (caract == ',')) {
         if (getText().indexOf(pontoDecMask) != -1) 
           retorno = false;
         else 
           retorno = true;
       }
       else if ( caract == '-') {
           if (getText().trim().length()>0) 
               retorno = false;
           else
               retorno = true;
       }
     }
     if (tipoCampo==TP_DATE) {
          retorno = false;
       if ( (Character.isDigit(caract)) | (caract=='/') ) {
         retorno = true;
         if ((getText().trim().length() == 1) & (caract=='/')) 
           super.setText("0"+getText());
         else if ((getText().trim().length() == 4) & (caract=='/')) 
           super.setText(getText().substring(0,3)+"0"+getText().substring(3,4));
         if ((getCaretPosition() != 2) & (getCaretPosition() != 5) & (caract=='/'))  
           retorno = false; 
         if (((getText().length() == 2) | (getText().length() == 5)) & (caract != '/'))
             super.setText(getText()+"/");
       }
     }
    return retorno;       
  }
  public void cancelaDLF2() {
    runDLF2 = false;
  }
  public void atualizaFK() {
  	if (lcTabExt != null) {
  		if (getText().trim().length() > 0) {
            lcTabExt.carregaDados();
        }
    	else
  	    	lcTabExt.limpaCampos(false);
  	}
  }
/*  private void setPonto() {
    String sPonto = "";
    sPonto = ""+ (double) ((double) 1 / (double) 2);
    pontoDec = sPonto.substring(1,2);
  } */
  public String getStrMascara() {
    return sMasc;
  }
/*  private void Menu() {
    copiarMI.setText("Copiar");
    copiarMI.setMnemonic('C');
    
    recortarMI.setText("Recortar");
    recortarMI.setMnemonic('R');
    
    colarMI.setText("Colar");
    colarMI.setMnemonic('o');
    
    adicMenu(copiarMI);
    adicMenu(recortarMI);
    adicMenu(colarMI);
  }
  public void adicMenu(JMenuItem mi) {
    pm.add(mi);
    mi.addActionListener(this);
  }
  public void setTamanho(int i) {
    iTamanho = i;
  }
*/  public void execEdic(char op) {
    switch(op) {
      case 'C':
/*        tecla.keyPress(KeyEvent.VK_CONTROL);
        tecla.keyPress(KeyEvent.VK_C);
        tecla.keyRelease(KeyEvent.VK_C);
        tecla.keyRelease(KeyEvent.VK_CONTROL);
      case 'R':
        tecla.keyPress(KeyEvent.VK_CONTROL);
        tecla.keyPress(KeyEvent.VK_X);
        tecla.keyRelease(KeyEvent.VK_X);
        tecla.keyRelease(KeyEvent.VK_CONTROL);
      case 'P':
        tecla.keyPress(KeyEvent.VK_CONTROL);
        tecla.keyPress(KeyEvent.VK_V);
        tecla.keyRelease(KeyEvent.VK_V);
        tecla.keyRelease(KeyEvent.VK_CONTROL);
*/    }
  }
  public void setTipo(int tipo, int tam, int dec) {
    if (( tipo >= TP_INTEGER) & (tipo <= TP_NUMERIC))
      setHorizontalAlignment(RIGHT);
    else 
      setHorizontalAlignment(LEFT);
    iTamanho = tam;
    iDecimal = dec;
    tipoCampo = tipo;    
  }
  public int getTipo() {
    return tipoCampo;
  }
  /**
   * Este metodo ajusta o textfield para Primary/Foreign
   * key através do novo conceito.
   */
  public void setChave(byte key) {
	setPKFK((key == ListaCampos.DB_PK) || (key == ListaCampos.DB_PF),
	        (key == ListaCampos.DB_FK) || (key == ListaCampos.DB_PF));
  }

  public void setPK(boolean PK) {
    bPK = PK;
    bEnterSai = false;
  }
  
  public void setFK(boolean FK) {
    bFK = FK;
  }
  public void setTabelaExterna(ListaCampos lc) {
    lcTabExt = lc;
  }
  public ListaCampos getTabelaExterna() {
    return lcTabExt;
  }
  public void setNomeCampo(String nm) {
    sNomeCampo = nm;
  }
  public String getNomeCampo() {
    return sNomeCampo;
  }
  
//  public void actionPerformed(ActionEvent evt) { 
//    if (evt.getSource() == copiarMI) execEdic('C');
//    else if (evt.getSource() == recortarMI) execEdic('R');
//    else if (evt.getSource() == colarMI) execEdic('P');
//  }

//  public void mousePressed(MouseEvent mevt) {
//    if (mevt.getModifiers() == InputEvent.BUTTON3_MASK) 
//      pm.show( this, mevt.getX(), mevt.getY());  
//  }
  public void focusGained(FocusEvent e) {
    selectAll();
    sValAnt = getText();
  }
  public void focusLost(FocusEvent e) {
    if ((tipoCampo >= TP_DOUBLE) & (tipoCampo <= TP_NUMERIC)) 
      super.setText(transValorNum(getText()));
    else if (tipoCampo == TP_DATE) 
      super.setText(Funcoes.verData(transData(getText())));
    if (bloquear) {
      requestFocus();
      bloquear = false;
    }
    if (!sValAnt.equals(getText())) {
//         System.out.println("ValAnt: "+sValAnt+" getText():"+getText());
         atualizaFK();
    }
  }
  public void buscaAdic(String sTipo) {
  	 if (dlBuscaAdic != null) {
  	 	if (dlBuscaAdic.setValor(getVlrString(),sTipo)) {        
  	 		if (dlBuscaAdic.OK) {
  	 			Object oVal = null;
  	 			oVal = dlBuscaAdic.getValor();
  	 			dlBuscaAdic.oRetVal = null;
  	 			if (oVal != null)
  	 				setVlrString(oVal+"");
  	 		}
  	 	}
  	 }
  }
  public void setRequerido(boolean bR) {
    bRequerido = bR;
    if (bR)
      Funcoes.setBordReq(this);
    else
      setBorder((new JTextField()).getBorder());
  }
  public void setLabel(String lab) {
    sLabel = lab;
  }
  public String getLabel() {
    return sLabel;
  }
  private String transValorNum(String sNum) {
  	if (sNum.equals(""))
  		return "";
    String retorno = "";
    int iPos = -1;
    int iQuant = -1;
    iPos = sNum.indexOf(pontoDecMask);
    if (iPos == -1) {
      retorno = sNum.trim()+pontoDecMask+Funcoes.replicate("0",iDecimal);
    }
    else {
     iQuant = iDecimal-(sNum.length() - (iPos+1));
    if (iQuant > 0) 
      retorno = sNum+Funcoes.replicate("0",iQuant);
    else if (iQuant == 0) 
      retorno = sNum;
    else if (iQuant < 0) 
      retorno = sNum.substring(0,iPos+iDecimal+1); 
    }
    return retorno;
  }
  private String transData(String sData) {
    String retorno = sData;
    switch(retorno.trim().length()) {
		case 1: 
		  retorno = "0"+retorno+"/"+Funcoes.strZero((data.get(Calendar.MONTH)+1)+"",2)+"/"+(data.get(Calendar.YEAR));
		  break;
		case 2: 
		  retorno += "/"+Funcoes.strZero((data.get(Calendar.MONTH)+1)+"",2)+"/"+(data.get(Calendar.YEAR));
		  break;
		case 3: 
		  retorno += Funcoes.strZero((data.get(Calendar.MONTH)+1)+"",2)+"/"+(data.get(Calendar.YEAR));
		  break;
		case 4: 
          retorno = retorno.substring(0,3)+"0"+retorno.substring(3)+"/"+(data.get(Calendar.YEAR));
		  break;
		case 5: 
          retorno += "/"+(data.get(Calendar.YEAR));
		  break;
		case 6: 
		  retorno += (data.get(Calendar.YEAR));
		  break;
		case 7: 
	      retorno = retorno.substring(0,6)+(""+data.get(Calendar.YEAR)).substring(0,2)+Funcoes.strZero(""+retorno.substring(6,7),2);
		  break;
	    case 8:
          retorno = retorno.substring(0,6)+(""+data.get(Calendar.YEAR)).substring(0,2)+retorno.substring(6,8);
		  break;
		case 9:
		  retorno += (""+data.get(Calendar.YEAR)).substring(3);
    }
    if (!Funcoes.validaData(retorno.trim())) {
       retorno = Funcoes.arredondaData(retorno.trim());
    }
    return retorno;
  }
  
  public ListaCampos getListaCampos() {
    return lcTxt;
  }
  
  public void setListaCampos(ListaCampos lc) {
    lcTxt = lc;      
  }
  
  private char trataChar(char cVal) {
  	char cRet = cVal;
	if ( ( getText().length() >= iTamanho ) && (iTamanho != 0) && 
		   (getSelectedText() == null )) {
		   cRet = (char)0;
	}
	else { 
	  if (bMasc) {
		if (!masc(cVal))
		  cRet = (char)0;
	  }
	  else {
		if (caracValido(cVal)==false) {
  		  cRet = (char)0;
		}
		else if (((tipoCampo == TP_DECIMAL) ||
				    (tipoCampo == TP_DOUBLE) ||
				    (tipoCampo == TP_NUMERIC)) && 
			  ((cVal == ',') || (cVal == '.'))) 
		  cRet = pontoDecMask;
	  }
	}
	return cRet;
  }      

  private void editDB() {
  	if (lcTxt != null && !bPK)
  	  lcTxt.edit();
  }
    
  public boolean mostraDLF2FK() {
  	boolean bRet = false; 
//    DLF2 dl = new DLF2(lcTabExt,Funcoes.getOwnerTela(this));
  	  DLF2 dl = new DLF2(lcTabExt,Aplicativo.telaPrincipal);
	  dl.setVisible(true);
    if (dl.OK) {
        if (!bPK)
		    editDB();
        setVlrString((String)dl.getValor(sNomeCampo));
		  dl.dispose();
//        sValAnt = getText();
        if (lcTabExt.carregaDados() && (!bPK || lcTxt.carregaDados()))
        	transferFocus();
        bRet = true;
    }
    else  {
      dl.dispose();
    }
    return bRet;
  }
  public void keyTyped(KeyEvent kevt) { 
	if (kevt.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
		editDB();
		return;
	}

	if ((kevt.getKeyChar() == KeyEvent.CHAR_UNDEFINED) ||
	   (kevt.getKeyChar() == KeyEvent.VK_ENTER) ||
	   (kevt.getKeyChar() == KeyEvent.VK_TAB))
	   return;
	
    kevt.setKeyChar(trataChar(kevt.getKeyChar()));
    editDB();
    
    fireEdit();
  }
  
  public void keyPressed(KeyEvent kevt) {

/*
 * Esse metodo foi colocado aqui e não no keyTyped
 * por que o tecla DEL não chama o keyTyped:
 */
  
	if (kevt.getKeyCode() == KeyEvent.VK_DELETE) {
		editDB();
		return;
	}
	else if((kevt.getKeyCode() == KeyEvent.VK_ENTER) && (dlBuscaAdic!=null) && (dlBuscaAdic instanceof DLBuscaEstoq)){
		 buscaAdic("estoque");
	}    
	else if ((kevt.getKeyCode() == KeyEvent.VK_ENTER) && 
            (getText().trim().length() > 0) &&
            (bPK || bFK)) {
 	  if (Aplicativo.bBuscaProdSimilar)
	  	buscaAdic("similar");
 	  
//   	lcTxt.carregaDados();
  //   	transferFocus();

   }

    if (((kevt.getKeyCode() == KeyEvent.VK_F2) || 
    	((kevt.getKeyCode() == KeyEvent.VK_ENTER) &&  
    	 (getText().trim().length() == 0))) && ((bPK) || (bFK))) {    	
      if ((bFK) && (lcTabExt != null)) {
        if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
          if (bRequerido) {
        	mostraDLF2FK();
          }
        }
        else {
        	mostraDLF2FK();
        }
      }
      else if ((bPK) & (lcTxt != null) & (runDLF2)) {
//        DLF2 dl = new DLF2(lcTxt,Funcoes.getOwnerTela(this));
        DLF2 dl = new DLF2(lcTxt,Aplicativo.telaPrincipal);
        dl.setVisible(true);

        if (dl.OK) {
          setVlrString((String)dl.getValor(sNomeCampo));
		  dl.dispose();          
          if (lcTabExt.carregaDados()) 
  	        transferFocus();
        }
        else  {
          dl.dispose();
        }
      }
    }
    else if (kevt.getKeyCode() == KeyEvent.VK_F1) {
        FAtalhos tela = new FAtalhos();
        tela.setVisible(true);
        tela.dispose();
    }
    else if ((kevt.getKeyCode() == KeyEvent.VK_ENTER) && 
             (getText().trim().length() > 0) &&
             (bPK) & (lcTxt != null)) {
  	  if (Aplicativo.bBuscaProdSimilar) {  	  	
  	  		buscaAdic("similar");
  	  }
	  	
  	  
    	lcTxt.carregaDados();
      	transferFocus();

    }
    
    else if ((kevt.getKeyCode() == KeyEvent.VK_ENTER) &&
             (getText().trim().length() > 0) &&
             (bFK) && (lcTabExt != null)) {

        if (lcTabExt.carregaDados())
	      transferFocus();
    }
    else if (kevt.getKeyCode() == KeyEvent.VK_F3 && (bPK || bFK)){
    	buscaAdic("alternativo");
    }
    		    		
	if ((kevt.getKeyCode() == KeyEvent.VK_ENTER) && (bEnterSai)) {
		transferFocus(); 
	}
  }
  public void keyReleased(KeyEvent kevt) { }
  public int getMascara() {
    return iMascara;
  }
  public boolean getEhMascara() {
    return bMasc;
  }
  public void setMascara(int iMasc) {
    iMascara = iMasc;
    bMasc = true;
    if (iMascara == MC_FONEDDD) {
      sMasc = "(####)####-####";
      iTamanho = 15;
    }
    else if (iMascara == MC_FONE) {
      sMasc = "####-####";
      iTamanho = 9;
    }
    else if (iMascara == MC_CNPJ) {
      sMasc = "##.###.###/####-##";
      iTamanho = 18;
    }
    else if (iMascara == MC_CEP) {
      sMasc = "#####-###";
      iTamanho = 9;
    }
    else if (iMascara == MC_CPF) {
      sMasc = "###.###.###-##";
      iTamanho = 14;
    }
	else if (iMascara == MC_RG) {
	  sMasc = "#.###.###-#";
	  iTamanho = 11;
	}
  }
  public void setStrMascara(String sMatriz) {
    sMasc = sMatriz;
    iTamanho = sMatriz.length();
    bMasc = true;
  }
  public void setEhMascara(boolean bVal) {
          bMasc = bVal;
  }
  private boolean masc(char cVal) {
    if (iMascara == MC_FONEDDD) {
      if (!((cVal == (char) KeyEvent.VK_SPACE) | (Character.isDigit(cVal)))) 
        return false;
    }
    else if (iMascara == MC_FONE) {
      if (!((cVal == (char) KeyEvent.VK_SPACE) | (Character.isDigit(cVal))))
        return false;
    }
    else if (iMascara == MC_CNPJ) {
      if (!Character.isDigit(cVal)) 
        return false;
    }
    else if (iMascara == MC_CEP) {
      if (!Character.isDigit(cVal)) 
        return false;
    }
    else if (iMascara == MC_CPF) {
      if (!Character.isDigit(cVal)) 
        return false;
    }
	else if (iMascara == MC_RG) {
	  if (!Character.isDigit(cVal)) 
		return false;
	}

    if (sMasc.length() == 0)
      return false;
    char[] ca = sMasc.toCharArray(); 
    int pos = getCaretPosition();
    if (pos != iTamanho) {
      if (ca[pos] != '#')
        super.setText(getText()+ca[pos]);
    }
    return true;
  }
  public void setVlrString(String sVal) {
    String texto = "";
	if (sVal == null) {
		super.setText(texto);
		fireActionPerformed();
		return;
	}
    sVal = Funcoes.trimFinal(sVal);
    if (sVal.length() > iTamanho && iTamanho > 0)
    	sVal = sVal.substring(0,iTamanho);
    int i2 = 0;
    if ((sVal.length() > 0) & (sMasc.length() > 0)){
    char[] va = sVal.toCharArray();
    char[] ma = sMasc.toCharArray();
      for (int i = 0; i < va.length && i2 < ma.length; i++) {
        if (ma[i2] == '#') {
          texto += va[i];
        }  
        else {
          texto += ma[i2];
          texto += va[i];
          i2++;
        }
        i2++;
      }
    }
    else
      texto = sVal;
    super.setText(texto);
    setCaretPosition(0);
    fireActionPerformed();
  }
  public void setVlrInteger(Integer iVal) {
    String texto = "";
    String sVal = ""+iVal;
    if ((sVal.length() > 0) & (sMasc.length() > 0)){
    char[] va = sVal.toCharArray();
    char[] ma = sMasc.toCharArray();
      for (int i = 0; i < sVal.length(); i++) {
        if (ma[i] == '#') {
          texto += va[i];
        }  
        else 
          texto += ma[i];
      }
    }
    else
      texto = ""+iVal;
    super.setText(texto);
    fireActionPerformed();
  }
  public void setVlrBigDecimal(BigDecimal bigVal) {
    String texto = "";
    bigVal = bigVal.setScale(iDecimal,BigDecimal.ROUND_HALF_UP);
    String sVal = bigVal.toString();
    char[] val = sVal.toCharArray();
    if (sVal.indexOf('.') >= 0)
      val[sVal.indexOf('.')] = pontoDecMask;
    texto = new String(val);
    super.setText(texto);
  }
  public void setVlrDouble(Double dVal) {
    String texto = "";
    BigDecimal bigVal = new BigDecimal(dVal.toString());
    bigVal = bigVal.setScale(iDecimal,BigDecimal.ROUND_HALF_UP);
    String sVal = dVal.toString();
    char[] val = sVal.toCharArray();
    if (sVal.indexOf('.') >= 0)
      val[sVal.indexOf('.')] = pontoDecMask;
    texto = new String(val);
    texto += Funcoes.replicate("0",iDecimal-(texto.length()-(texto.indexOf(pontoDecMask)+1)));
    super.setText(texto);
  }
  public void setVlrDate(Date dVal) {
  	super.setText(Funcoes.dateToStrDate(dVal));
  }
  public void setVlrBoolean(Boolean bVal) {
    super.setText(bVal.toString());
  }
  public String getVlrString() {
    String sRetorno = "";
    if ((getText().length() > 0) & (sMasc.length() > 0)) {
      char ma[] = sMasc.toCharArray();
      char va[] = getText().toCharArray();
      for (int i = 0; i < getText().length(); i++) {
        if ( ma[i] == '#' )
          sRetorno += va[i];
      }
    }
    else 
        sRetorno = getText();
    if (sRetorno.length() > iTamanho)
    	sRetorno = sRetorno.substring(0,iTamanho);
    return sRetorno;
  }
  public Integer getVlrInteger() {
    String sRetorno = "";
    Integer iRetorno = new Integer(0);
    if ((getText().length() > 0) & (sMasc.length() > 0)) {
      char ma[] = sMasc.toCharArray();
      char va[] = getText().toCharArray();
      for (int i = 0; i < getText().length(); i++) {
        if ( ma[i] == '#' )
          sRetorno += va[i];
      }
    }
    else 
      sRetorno = getText();
    try {
      iRetorno = new Integer(sRetorno);
    }
    catch (Exception err) {
      iRetorno = new Integer(0);
    }
    return iRetorno;
  }
  public Double getVlrDouble() {
    String sRetorno = "";
    Double dRetorno = new Double(0);
    if ((getText().length() > 0) & (sMasc.length() > 0)) {
      char ma[] = sMasc.toCharArray();
      char va[] = getText().toCharArray();
      for (int i = 0; i < getText().length(); i++) {
        if ( ma[i] == '#' )
          sRetorno += va[i];
      }
    }
    else 
      sRetorno = getText();
    char[] ret = sRetorno.toCharArray(); 
    if ((pontoDecMask == ',') & (sRetorno.indexOf(',') != -1))
      ret[sRetorno.indexOf(',')] = '.';
    sRetorno = new String(ret);
    try {
      dRetorno = new Double(sRetorno);
    }
    catch (Exception err) {
      dRetorno = new Double(0);
    }
    return dRetorno;
  }
  public double doubleValue() {
      return getVlrDouble().doubleValue();
  }
  public BigDecimal getVlrBigDecimal() {
    String sRetorno = "";
    BigDecimal bigRetorno = new BigDecimal("0");
    sRetorno = getText().trim();
    if ((pontoDecMask == ',') & (sRetorno.indexOf(',') >= 0)) {
      sRetorno = sRetorno.replaceAll("\\.","");
      sRetorno = sRetorno.replace(',','.');
    }
    try {
      bigRetorno = new BigDecimal(sRetorno);
    }
    catch (Exception err) {
      bigRetorno = new BigDecimal("0");
    }
    return bigRetorno;
  }
  
  public Date getVlrDate() {
    String sRetorno = "";
    GregorianCalendar cRetorno = new GregorianCalendar(0,0,0);
    if (getText().length() > 0) {
      sRetorno = transData(getText());
      try {
        int iAno = Integer.parseInt(sRetorno.substring(6));
        int iMes = Integer.parseInt(sRetorno.substring(3,5));
        int iDia = Integer.parseInt(sRetorno.substring(0,2));
        cRetorno.set(iAno,iMes-1,iDia);
      }
      catch (Exception err) {
        cRetorno = null;
      }
    }
    return cRetorno == null ? null : cRetorno.getTime();
  }
  public Boolean getVlrBoolean() {
    return new Boolean(getText());
  }
  public void setText(String sVal) {
  	if (tipoCampo == TP_NONE)
  	  super.setText(sVal);
	else if (tipoCampo == TP_BOOLEAN)
	  setVlrBoolean(new Boolean(sVal));
	else if (tipoCampo == TP_BYTES)
	  super.setText("<BINÀRIO>");
	else if (tipoCampo == TP_DATE) {
      try {
	    setVlrDate((new SimpleDateFormat()).parse(sVal));
      }
      catch (ParseException err) {
      	super.setText("");
      }
	}
	else if (tipoCampo == TP_NUMERIC)
	  setVlrBigDecimal(new BigDecimal(sVal));
	else if (tipoCampo == TP_DECIMAL)
	  setVlrBigDecimal(new BigDecimal(sVal));
	else if (tipoCampo == TP_DOUBLE)
	  setVlrDouble(new Double(sVal));
	else if (tipoCampo == TP_FLOAT)
	  setVlrDouble(new Double(sVal));
	else if (tipoCampo == TP_INTEGER)
	  setVlrInteger(new Integer(sVal));
	else if (tipoCampo == TP_NUMERIC)
	  setVlrBigDecimal(new BigDecimal(sVal));
	else if (tipoCampo == TP_STRING)
	  super.setText(sVal);
	else if (tipoCampo == TP_TIME) {
	  try {
		setVlrDate((new SimpleDateFormat()).parse(sVal));
	  }
	  catch (ParseException err) {
		super.setText("");
	  }
	}
    else if (tipoCampo == TP_TIMESTAMP) {
      try {
	    setVlrDate((new SimpleDateFormat()).parse(sVal));
      }
      catch (ParseException err) {
	    super.setText("");
      }
    }
  }
  public void addEditListener(EditListener eLis) {
  	editLis = eLis;
  }
  private void fireEdit() {
  	editLis.edit(new EditEvent(this));
  }
  public void inputMethodTextChanged(InputMethodEvent event) {
  	if (getVlrString().length() > iTamanho)
  		setVlrString(getVlrString().substring(0,iTamanho));
  	if (event.getSource() == this)
  	  fireEdit();
  }
  public void edit(EditEvent eevt) { }
  public void beforeEdit(EditEvent eevt) { }
  public void afterEdit(EditEvent eevt) { }
  


}
