/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FEmpresa.java <BR>
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

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.PainelImagem;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;

public class FEmpresa extends FTabDados implements PostListener, CarregaListener{
  private JPanelPad pinGeral = new JPanelPad(470,470);
  private JPanelPad pinFilial = new JPanelPad(470,270);
  private JPanelPad pnFilial = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pnFilialGeral = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JTextFieldPad txtCodEmp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
  private JTextFieldPad txtRazEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNomeEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtEndEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNumEmp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtCnpjEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 14, 0);
  private JTextFieldPad txtInscEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 15, 0);
  private JTextFieldPad txtBairEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtComplEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtCidEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtCepEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtFoneEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 12, 0);
  private JTextFieldPad txtFaxEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtUFEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  private JTextFieldPad txtEmailEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtWWWEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtPercIssEmp = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 6, 2 );
  private JTextFieldPad txtCodEANEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 6, 0);
  private JTextFieldPad txtCodPaisEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 3, 0);
  private JTextFieldPad txtNomeContEmp = new JTextFieldPad(JTextFieldPad.TP_STRING, 40 , 0);
  private JTextFieldPad txtCodFilial = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldPad txtRazFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtNomeFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCnpjFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldPad txtInscFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,15,0);
  private JTextFieldPad txtEndFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtNumFilial = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtComplFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldPad txtBairFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JTextFieldPad txtCepFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldPad txtCidFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
  private JTextFieldPad txtUFFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
  private JTextFieldPad txtFoneFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,12,0);
  private JTextFieldPad txtFaxFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
  private JTextFieldPad txtEmailFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtWWWFilial = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodDistFilial = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtPercPIS = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,5,2);
  private JTextFieldPad txtPercCofins = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,5,2);
  private JTextFieldPad txtPercIR = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,5,2);
  private JTextFieldPad txtPercCSocial = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,5,2);
  
  private JTextFieldPad txtCodAlmox = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtDescAlmox = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  
  private JCheckBoxPad cbMatriz = new JCheckBoxPad("Matriz","S","N");
  private JCheckBoxPad cbSimples = new JCheckBoxPad("Simples","S","N");
  private ListaCampos lcFilial = new ListaCampos(this);
  private ListaCampos lcAlmox = new ListaCampos(this,"AF");
  private ListaCampos lcAlmoxFilial = new ListaCampos(this,"");
  private Tabela tabFilial = new Tabela();
  private JScrollPane spnFilial = new JScrollPane(tabFilial);
  private Navegador navFilial = new Navegador(true);
  private PainelImagem imFotoEmp = new PainelImagem(65000);
  private JTabbedPanePad tpn2 = new JTabbedPanePad();
  private JCheckBoxPad cbMultiAlmox = new JCheckBoxPad("Sim","S","N");
  
  private JPanelPad pinDetAlmoxFilial = new JPanelPad(JPanelPad.TP_JPANEL, new BorderLayout());
  private Tabela tbAlmoxFilial = new Tabela();
  private JScrollPane spnAlmoxFilial = new JScrollPane(tbAlmoxFilial); // Scrool pane para grid de Bancos.
  private JPanelPad pinCamposAlmoxFilial = new JPanelPad(680,200);
  private JPanelPad pinNavAlmoxFilial = new JPanelPad(680,30);
  private JPanelPad pnAlmoxFilial = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private Navegador navAlmoxFilial = new Navegador(false);
  private JCheckBoxPad cbPermiteBaixa = new JCheckBoxPad("Sim","S","N");
  
  public FEmpresa () {
    setTitulo("Cadastro da Empresa");
    setAtribos( 50, 20, 500, 470);
    
    lcCampos.addPostListener(this);
    lcFilial.addPostListener(this);
    
    lcCampos.setUsaME(false);
    lcFilial.setUsaME(false);

    lcFilial.setMaster(lcCampos);
    lcCampos.adicDetalhe(lcFilial);
    lcFilial.setTabela(tabFilial);

    lcAlmoxFilial.setMaster(lcFilial);
    lcFilial.adicDetalhe(lcAlmoxFilial);
    
    lcAlmoxFilial.setTabela(tbAlmoxFilial);
    
    lcAlmox.add(new GuardaCampo( txtCodAlmox, "CodAlmox", "Cód.Almox.", ListaCampos.DB_PK, true));
    lcAlmox.add(new GuardaCampo( txtDescAlmox, "DescAlmox", "Descrição do almoxarifado", ListaCampos.DB_SI, false));

    lcAlmox.montaSql(false, "ALMOX", "EQ");    
    lcAlmox.setQueryCommit(false);
    lcAlmox.setReadOnly(true);
    txtCodAlmox.setTabelaExterna(lcAlmox);
    
   txtCodEmp.cancelaDLF2();
    
    setPainel(pinGeral);
    adicTab("Geral",pinGeral);

    adicCampo(txtCodEmp, 7, 20, 60, 20, "CodEmp", "Cód.emp.", ListaCampos.DB_PK, true);
    adicCampo(txtRazEmp, 70, 20, 207, 20, "RazEmp", "Razão social da empresa", ListaCampos.DB_SI, true);
    adicCampo(txtNomeEmp, 280, 20, 190, 20, "NomeEmp", "Nome fantasia", ListaCampos.DB_SI, false);
    adicCampo(txtCnpjEmp, 7, 60, 125, 20, "CnpjEmp", "Cnpj", ListaCampos.DB_SI, true);
    adicCampo(txtInscEmp, 135, 60, 112, 20, "InscEmp", "Inscrição Estadual", ListaCampos.DB_SI, true);
    adicCampo(txtEndEmp, 250, 60, 127, 20, "EndEmp", "Endereço", ListaCampos.DB_SI, false);
    adicCampo(txtNumEmp, 380, 60, 37, 20, "NumEmp", "Num.", ListaCampos.DB_SI, false);
    adicCampo(txtComplEmp, 420, 60, 50, 20, "ComplEmp", "Compl.", ListaCampos.DB_SI, false);
    adicCampo(txtBairEmp, 7, 100, 150, 20, "BairEmp", "Bairro", ListaCampos.DB_SI, false);
    adicCampo(txtCidEmp, 160, 100, 157, 20, "CidEmp", "Cidade", ListaCampos.DB_SI, false);
    adicCampo(txtCepEmp, 320, 100, 92, 20, "CepEmp", "Cep", ListaCampos.DB_SI, false);
    adicCampo(txtUFEmp, 415, 100, 55, 20, "UFEmp", "UF", ListaCampos.DB_SI, false);
    adicCampo(txtFoneEmp, 7, 140, 150, 20, "FoneEmp", "Telefone", ListaCampos.DB_SI, false);
    adicCampo(txtFaxEmp, 160, 140, 137, 20, "FaxEmp", "Fax", ListaCampos.DB_SI, false);
    adicCampo(txtEmailEmp, 300, 140, 170, 20, "EmailEmp", "E-Mail", ListaCampos.DB_SI, false);
    adicCampo(txtWWWEmp, 7, 180, 180, 20, "WWWEmp", "Página da WEB", ListaCampos.DB_SI, false);
    adicCampo(txtCodEANEmp, 190, 180, 57, 20, "CodEANEmp", "Cod. EAN", ListaCampos.DB_SI, false);
    adicCampo(txtCodPaisEmp, 250, 180, 47, 20, "CodPaisEmp", "C.pais", ListaCampos.DB_SI, false);
    adicCampo(txtPercIssEmp, 300, 180, 47, 20, "PercIssEmp" , "%Iss" , ListaCampos.DB_SI, false);
    adicCampo(txtNomeContEmp, 350, 180, 120, 20,"NomeContEmp" , "Contato" , ListaCampos.DB_SI,false);
	adicDB(imFotoEmp, 7, 230, 150, 140, "FotoEmp", "Foto: (máx. 64K)",true);
  	adicDB(cbMultiAlmox, 160, 230, 300, 20, "MultiAlmoxEmp", "Intercambio de almox. entre filiais?",true);
    
    txtCnpjEmp.setMascara(JTextFieldPad.MC_CNPJ);
    txtCepEmp.setMascara(JTextFieldPad.MC_CEP);
    txtFoneEmp.setMascara(JTextFieldPad.MC_FONEDDD);
    txtFaxEmp.setMascara(JTextFieldPad.MC_FONE);
    setListaCampos( true, "EMPRESA", "SG");

    setPainel( pinFilial, pnFilial);
    adicTab("Filiais",pnFilialGeral);
    
    tpn2.setTabPlacement(SwingConstants.BOTTOM);
    pnFilialGeral.add(tpn2,BorderLayout.CENTER);

    setListaCampos(lcFilial);
    setNavegador(navFilial);
    pnFilial.add(pinFilial, BorderLayout.SOUTH);
    pnFilial.add(spnFilial, BorderLayout.CENTER);

    adicCampo(txtCodFilial, 7, 20, 60, 20, "CodFilial", "Cód.fil.", ListaCampos.DB_PK, true);
    adicCampo(txtRazFilial, 70, 20, 207, 20, "RazFilial", "Razão social da filial", ListaCampos.DB_SI, true);
    adicCampo(txtNomeFilial, 280, 20, 190, 20, "NomeFilial", "Nome fanatasia da filial", ListaCampos.DB_SI, false);
    adicCampo(txtCnpjFilial, 7, 60, 125, 20, "CnpjFilial", "Cnpj", ListaCampos.DB_SI, true);
    adicCampo(txtInscFilial, 135, 60, 112, 20, "InscFilial", "Inscrição Estadual", ListaCampos.DB_SI, true);
    adicCampo(txtEndFilial, 250, 60, 127, 20, "EndFilial", "Endereço", ListaCampos.DB_SI, false);
    adicCampo(txtNumFilial, 380, 60, 37, 20, "NumFilial", "Num.", ListaCampos.DB_SI, false);
    adicCampo(txtComplFilial, 420, 60, 50, 20, "ComplFilial", "Compl.", ListaCampos.DB_SI, false);
    adicCampo(txtBairFilial, 7, 100, 150, 20, "BairFilial", "Bairro", ListaCampos.DB_SI, false);
    adicCampo(txtCidFilial, 160, 100, 157, 20, "CidFilial", "Cidade", ListaCampos.DB_SI, false);
    adicCampo(txtCepFilial, 320, 100, 92, 20, "CepFilial", "Cep", ListaCampos.DB_SI, false);
    adicCampo(txtUFFilial, 415, 100, 55, 20, "UFFilial", "UF", ListaCampos.DB_SI, false);
    adicCampo(txtFoneFilial, 7, 140, 230, 20, "FoneFilial", "Telefone", ListaCampos.DB_SI, false);
    adicCampo(txtFaxFilial, 240, 140, 230, 20, "FaxFilial", "Fax", ListaCampos.DB_SI, false);
    adicCampo(txtEmailFilial, 7, 180, 140, 20, "EmailFilial", "E-Mail", ListaCampos.DB_SI, false);
    adicCampo(txtWWWFilial, 150, 180, 137, 20, "WWWFilial", "Página da WEB", ListaCampos.DB_SI, false);
    adicCampo(txtCodDistFilial, 290, 180, 77, 20, "CodDistFilial", "C.dist.fil", ListaCampos.DB_SI, false);
    adicDB(cbMatriz, 370, 180, 80, 20, "MzFilial", "Sede",false);
    adicCampo(txtPercPIS, 7, 220, 90, 20, "PercPISFilial", "PIS", ListaCampos.DB_SI, false);
    adicCampo(txtPercCofins, 100, 220, 87, 20, "PercCofinsFilial", "COFINS", ListaCampos.DB_SI, false);
    adicCampo(txtPercIR, 190, 220, 87, 20, "PercIRFilial", "IR", ListaCampos.DB_SI, false);
    adicCampo(txtPercCSocial, 280, 220, 87, 20, "PercCSocialFilial", "Cont.social", ListaCampos.DB_SI, false);
    adicDB(cbSimples, 370, 220, 80, 20, "SimplesFilial", "Fiscal",false);
    txtCnpjFilial.setMascara(JTextFieldPad.MC_CNPJ);
    txtCepFilial.setMascara(JTextFieldPad.MC_CEP);
    txtFoneFilial.setMascara(JTextFieldPad.MC_FONEDDD);
    txtFaxFilial.setMascara(JTextFieldPad.MC_FONE);
    setListaCampos( false, "FILIAL", "SG");
    lcFilial.setOrdem("RazFilial");
    lcFilial.montaTab();
    lcFilial.setQueryInsert(false);
    lcFilial.setQueryCommit(false);
    tabFilial.setTamColuna(120,1);
    pinFilial.adic(navFilial,0,245,270,25);
    tabFilial.setTamColuna(80,0);
    tabFilial.setTamColuna(220,1);
    tabFilial.setTamColuna(220,2);
    tabFilial.setTamColuna(80,3);
    tabFilial.setTamColuna(140,4);
    tabFilial.setTamColuna(90,5);
    tabFilial.setTamColuna(50,6);
    tabFilial.setTamColuna(70,7);
    tabFilial.setTamColuna(70,8);
    tabFilial.setTamColuna(80,9);
    tabFilial.setTamColuna(50,10);
    tabFilial.setTamColuna(40,11);
    tabFilial.setTamColuna(80,12);
    tabFilial.setTamColuna(50,13);
    tabFilial.setTamColuna(70,14);
    tabFilial.setTamColuna(100,15);
    tabFilial.setTamColuna(80,16);
    tabFilial.setTamColuna(60,17);
    tabFilial.setTamColuna(40,18);
    tabFilial.setTamColuna(80,19);


    
//    *************************AlmoxFilial
          
      setPainel( pinDetAlmoxFilial, pnAlmoxFilial);

      pinDetAlmoxFilial.setPreferredSize(new Dimension(600,80));
      pinDetAlmoxFilial.add(pinNavAlmoxFilial,BorderLayout.SOUTH);
      pinDetAlmoxFilial.add(pinCamposAlmoxFilial,BorderLayout.CENTER);
      setListaCampos(lcAlmoxFilial);
      setNavegador(navAlmoxFilial);
          
      pnAlmoxFilial.add(pinDetAlmoxFilial, BorderLayout.SOUTH);
  	  pnAlmoxFilial.add(spnAlmoxFilial, BorderLayout.CENTER);

  	  pinNavAlmoxFilial.adic(navAlmoxFilial,0,0,270,25);
      
      setPainel(pinCamposAlmoxFilial);
      
      adicCampo(txtCodAlmox, 7, 20, 60, 20, "codalmox", "Cod.Almox.", ListaCampos.DB_PF, txtDescAlmox,false);
      adicDescFK(txtDescAlmox, 70, 20, 150, 20, "DescAlmox", "Descrição do almoxarifado");
      adicDB(cbPermiteBaixa, 223, 20, 200, 20, "BaixaEstoqAf", "Permite baixa de estoque?",false);
      
      setListaCampos( false, "ALMOXFILIAL", "EQ");
      lcAlmoxFilial.setQueryInsert(false);
      lcAlmoxFilial.setQueryCommit(false);
      lcAlmoxFilial.montaTab();
      tbAlmoxFilial.setTamColuna(90,0); 
      tbAlmoxFilial.setTamColuna(200,1);

//   **************************    
    
    lcCampos.addCarregaListener(this);
        
    habAbas();
  }  
  public void afterCarrega(CarregaEvent cevt) {
  	habAbas();
  }
  private void habAbas() {
  	tpn2.removeAll();
    tpn2.addTab("Filial",pnFilial);
    
    if(cbMultiAlmox.getVlrString().equals("S"))
    	tpn2.addTab("Almox.",pnAlmoxFilial);
  }

  public void beforePost(PostEvent pevt) {
    if (pevt.getListaCampos() == lcCampos) {
      if (txtCnpjEmp.getText().trim().length() < 1) {
	    pevt.cancela();
        Funcoes.mensagemInforma( this,"Campo CNPJ é requerido! ! !");
        txtCnpjEmp.requestFocus();
      }
      else if (txtInscEmp.getText().trim().length() < 1) {
        if (Funcoes.mensagemConfirma(this, "Inscrição Estadual em branco! Inserir ISENTO?")==0 )
          txtInscEmp.setVlrString("ISENTO");
        else {
          pevt.cancela();
          txtInscEmp.requestFocus();
        }
      }
      else if (txtInscEmp.getText().trim().toUpperCase().compareTo("ISENTO") == 0)
        return;
      else if (txtUFEmp.getText().trim().length() < 2) {
        pevt.cancela();
        Funcoes.mensagemInforma(this,"Campo UF é requerido! ! !");
        txtUFEmp.requestFocus();
      }
      else if (!Funcoes.vIE(txtInscEmp.getText(),txtUFEmp.getText())) {
        pevt.cancela();
        Funcoes.mensagemInforma( this,"Inscrição Estadual Inválida ! ! !");
        txtInscEmp.requestFocus();
      }
      txtInscEmp.setVlrString(Funcoes.sIEValida);
    }
    else if(pevt.getListaCampos() == lcFilial) {
      if (txtCnpjFilial.getText().trim().length() < 1) {
        pevt.cancela();
        Funcoes.mensagemInforma( this,"Campo CNPJ é requerido! ! !");
        txtCnpjFilial.requestFocus();
      }
      else if (txtInscFilial.getText().trim().length() < 1) {
        if (Funcoes.mensagemConfirma(this, "Inscrição Estadual em branco! Inserir ISENTO?")==0 )
          txtInscFilial.setVlrString("ISENTO");
        else {
          pevt.cancela();
          txtInscFilial.requestFocus();
        }
      }
      else if (txtInscFilial.getText().trim().toUpperCase().compareTo("ISENTO") == 0)
        return;
      else if (txtUFFilial.getText().trim().length() < 2) {
        pevt.cancela();
        Funcoes.mensagemInforma( this,"Campo UF é requerido! ! !");
        txtUFFilial.requestFocus();
      }
      else if (!Funcoes.vIE(txtInscFilial.getText(),txtUFFilial.getText())) {
        pevt.cancela();
        Funcoes.mensagemInforma( this,"Inscrição Estadual Inválida ! ! !");
        txtInscFilial.requestFocus();
      }
    }
  }
  private void carregaEmpresa(){
      txtCodEmp.setVlrInteger(new Integer(Aplicativo.iCodEmp));
      lcCampos.carregaDados();
  }
  public void afterPost(PostEvent pevt) { }
  public void beforeCarrega(CarregaEvent cevt) {}
  public void setConexao(Connection cn) {
    super.setConexao(cn);
  	lcFilial.setConexao(cn);
  	lcAlmox.setConexao(cn);
  	lcAlmoxFilial.setConexao(cn);
  	carregaEmpresa();
  }

}
