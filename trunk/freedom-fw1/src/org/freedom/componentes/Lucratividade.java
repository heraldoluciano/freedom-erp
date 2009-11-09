package org.freedom.componentes;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.Aplicativo;


public class Lucratividade {
	
	private BigDecimal totfat = null;
	private BigDecimal totcusto = null;
	private BigDecimal totlucro = null;
	private BigDecimal itemfat = null;
	private BigDecimal itemcusto = null;
	private BigDecimal itemlucro = null;	
	private BigDecimal vlrcustopeps = null;
	private BigDecimal vlrcustouc = null;
	private BigDecimal vlrlucro = null;
	private BigDecimal vlrprod = null;
	private BigDecimal vlrdesc = null;
	private BigDecimal vlricms = null;
	private BigDecimal vlroutras = null;
	private BigDecimal vlrcomis = null;
	private BigDecimal vlrfrete = null;
	private BigDecimal vlradic = null;
	private BigDecimal vlripi = null;
	private BigDecimal vlrpis = null;
	private BigDecimal vlrcofins = null;
	private BigDecimal vlrir = null;
	private BigDecimal vlrcsocial = null;	
	private BigDecimal vlrcustompmit = null;
	private BigDecimal vlrcustopepsit = null;
	private BigDecimal vlrcustoucit = null;
	private BigDecimal vlrlucroit = null;
	private BigDecimal vlrprodit = null;
	private BigDecimal vlrdescit = null;
	private BigDecimal vlricmsit = null;
	private BigDecimal vlrpisit = null;
	private BigDecimal vlrcofinsit = null;
	private BigDecimal vlrcsocialit = null;
	private BigDecimal vlroutrasit = null;
	private BigDecimal vlrcomisit = null;
	private BigDecimal vlrfreteit = null;
	private BigDecimal vlradicit = null;
	private BigDecimal vlripiit = null;
	private BigDecimal vlririt = null;
	private BigDecimal perclucr = null;	
	private BigDecimal perclucrit = null;
	private String tipofrete = null;
	private String adicfrete = null;	
	private BigDecimal fatLucro = new BigDecimal(1);	
	private BigDecimal vlrcustompm = null;	
	private DbConnection con = null;
		
	public Lucratividade(Integer codcab, String tipo, Integer item, BigDecimal fatLucro, DbConnection con) {
		
		this.con = con; 
		
		if(fatLucro!=null) {
			this.fatLucro = fatLucro;
		}
		
		// Se for a lucratividade de uma venda
		
		if( codcab!=null && "V".equals(tipo) ) {
			carregaVenda( codcab, tipo);
			carregaItemVenda( codcab, tipo, item );
		}
		// Se for a lucratividade de um orçamento
		else {
			carregaOrcamento(codcab, tipo);
			
			
		}

		calcTotFat();
		calcTotCusto( "M" );
		calcTotLucro();
		
		calcItemFat();
		calcItemCusto( "M" );
		calcItemLucro();

	}	
		
	public BigDecimal getTotfat() {
	
		return totfat;
	}

	public void setTotfat( BigDecimal totfat ) {
	
		this.totfat = totfat;
	}

	private void carregaVenda(Integer codvenda, String tipovenda) {		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		try {
			
			sql.append( "select " );
			
			sql.append( "coalesce(vd.vlrprodvenda,0) vlrprodvenda , coalesce(vd.vlrdescvenda,0) vlrdescvenda, coalesce(vd.vlricmsvenda,0) vlricmsvenda, ");
			sql.append( "coalesce(vd.vlroutrasvenda,0) vlroutrasvenda , coalesce(vd.vlrcomisvenda,0) vlrcomisvenda, coalesce(vd.vlradicvenda,0) vlradicvenda, ");
			sql.append( "coalesce(vd.vlripivenda,0) vlripivenda, coalesce(vd.vlrpisvenda,0) vlrpisvenda, coalesce(vd.vlrcofinsvenda,0) vlrcofinsvenda, ");
			sql.append( "coalesce(vd.vlrirvenda,0) vlrirvenda, coalesce(vd.vlrcsocialvenda,0) vlrcsocialvenda, ");
			sql.append( "coalesce(fr.vlrfretevd,0) vlrfretevd, fr.tipofretevd, fr.adicfretevd, ");
			
			sql.append( "sum(icv.vlrcustopeps * iv.qtditvenda) as vlrcustopeps, ");
			sql.append( "sum(icv.vlrcustompm * iv.qtditvenda) as vlrcustompm, ");
			sql.append( "sum(icv.vlrprecoultcp * iv.qtditvenda) as vlrcustouc ");
			
			sql.append( "from " );
			sql.append( "vdvenda vd left outer join vdfretevd fr on ");
			sql.append( "fr.codemp=vd.codemp and fr.codfilial=vd.codfilial and fr.codvenda=vd.codvenda ");
			sql.append( "and fr.tipovenda=vd.tipovenda, ");
			
			sql.append( "vditvenda iv left outer join vditcustovenda icv on ");
			sql.append( "icv.codemp=iv.codemp and icv.codfilial=iv.codfilial and icv.codvenda = iv.codvenda ");
			sql.append( "and icv.tipovenda=iv.tipovenda and icv.coditvenda=iv.coditvenda ");
			
			sql.append( "where vd.codemp=? and vd.codfilial=? and vd.codvenda=? and vd.tipovenda=? ");
			sql.append( "and iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.tipovenda=vd.tipovenda and ");
			sql.append( "iv.codvenda=vd.codvenda ");
			
			sql.append( "group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14 ");			

			System.out.println(sql.toString());
			
			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setInt( 3, codvenda );
			ps.setString( 4, tipovenda );
			
			rs = ps.executeQuery();

			if ( rs.next() ) {
				setVlrprod( rs.getBigDecimal( "vlrprodvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrprodvenda" ));
				setVlrdesc( rs.getBigDecimal( "vlrdescvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrdescvenda" ));
				setVlricms( rs.getBigDecimal( "vlricmsvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlricmsvenda" ));
				setVlroutras( rs.getBigDecimal( "vlroutrasvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlroutrasvenda" ));
				setVlrcomis( rs.getBigDecimal( "vlrcomisvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcomisvenda" ));
				setVlrfrete( rs.getBigDecimal( "vlrfretevd" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrfretevd" ));
				setVlradic( rs.getBigDecimal( "vlradicvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlradicvenda" ));
				setVlripi( rs.getBigDecimal( "vlripivenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlripivenda" ));
				setVlrpis( rs.getBigDecimal( "vlrpisvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrpisvenda" ));
				setVlrcofins( rs.getBigDecimal( "vlrcofinsvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcofinsvenda" ));
				setVlrir( rs.getBigDecimal( "vlrirvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrirvenda" ));
				setVlrcsocial( rs.getBigDecimal( "vlrcsocialvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcsocialvenda" ));
				setVlrcustouc( rs.getBigDecimal( "vlrcustouc" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustouc" ));
				setVlrcustompm( rs.getBigDecimal( "vlrcustompm" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustompm" ));
				setVlrcustopeps( rs.getBigDecimal( "vlrcustopeps" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustopeps" )); 
				setTipofrete( rs.getString( "tipofretevd" )==null ? "F" : rs.getString( "tipofretevd" ));
				setAdicfrete( rs.getString( "adicfretevd" )==null ? "N" : rs.getString( "adicfretevd" ));
			}

			rs.close();
			ps.close();

			con.commit();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	

	private void carregaItemVenda(Integer codvenda, String tipovenda, Integer coditvenda) {		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		try {
			
			sql.append( "select " );
			sql.append( "coalesce(iv.vlrproditvenda,0) vlrproditvenda, coalesce(iv.vlrdescitvenda,0) vlrdescitvenda, coalesce(iv.vlricmsitvenda,0) vlricmsitvenda, ");			
			sql.append( "coalesce(iv.vlroutrasitvenda,0) vlroutrasitvenda, coalesce(iv.vlrcomisitvenda,0) vlrcomisitvenda, coalesce(iv.vlradicitvenda,0) vlradicitvenda, ");
			sql.append( "coalesce(iv.vlripiitvenda,0) vlripiitvenda, coalesce(iv.vlrfreteitvenda,0) vlrfreteitvenda, ");
			sql.append( "coalesce(lfi.vlrir,0) vlrir, coalesce(lfi.vlrcsocial,0) vlrcsocial, coalesce(lfi.vlrpis,0) vlrpis, coalesce(lfi.vlrcofins,0) vlrcofins, " );
			
			sql.append( "(icv.vlrcustopeps * iv.qtditvenda) as vlrcustoitpeps, ");
			sql.append( "(icv.vlrcustompm * iv.qtditvenda) as vlrcustoitmpm, ");
			sql.append( "(icv.vlrprecoultcp * iv.qtditvenda) as vlrcustoituc ");
			
			sql.append( "from ");
			
			sql.append( "vditvenda iv left outer join vditcustovenda icv on ");
			sql.append( "icv.codemp=iv.codemp and icv.codfilial=iv.codfilial and icv.codvenda = iv.codvenda ");
			sql.append( "and icv.tipovenda=iv.tipovenda and icv.coditvenda=iv.coditvenda ");
			
			sql.append( "left outer join lfitvenda lfi on ");
			sql.append( "lfi.codemp=iv.codemp and lfi.codfilial=iv.codfilial and lfi.codvenda=iv.codvenda " );
			sql.append( "and lfi.tipovenda=iv.tipovenda and lfi.coditvenda=iv.coditvenda  " );
			
			sql.append( "where iv.codemp=? and iv.codfilial=? and iv.codvenda=? and iv.tipovenda=? and iv.coditvenda=? ");	

			System.out.println(sql.toString());
			
			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setInt( 3, codvenda );
			ps.setString( 4, tipovenda );
			ps.setInt( 5, coditvenda );
			
			rs = ps.executeQuery();

			if ( rs.next() ) {
				setVlrproditvenda( rs.getBigDecimal( "vlrproditvenda" ));
				setVlrdescitvenda( rs.getBigDecimal( "vlrdescitvenda" ));
				setVlricmsitvenda( rs.getBigDecimal( "vlricmsitvenda" ));
				setVlroutrasitvenda( rs.getBigDecimal( "vlroutrasitvenda" ));
				setVlrcomisitvenda( rs.getBigDecimal( "vlrcomisitvenda" ));
				setVlrfreteitvenda(rs.getBigDecimal( "vlrfreteitvenda" ));
				setVlradicitvenda( rs.getBigDecimal( "vlradicitvenda" ));
				setVlripiitvenda( rs.getBigDecimal( "vlripiitvenda" ));
				setVlrcustoucit( rs.getBigDecimal( "vlrcustoituc" ));
				setVlrcustompmit( rs.getBigDecimal( "vlrcustoitmpm" ));
				setVlrcustopepsit( rs.getBigDecimal( "vlrcustoitpeps" )); 
				
				setVlrpisit(rs.getBigDecimal( "vlrpis" ));
				setVlrcofinsit(rs.getBigDecimal( "vlrcofins" ));
				setVlririt(rs.getBigDecimal( "vlrir" ));
				setVlrcsocialit( rs.getBigDecimal( "vlrcsocial" ));
			}

			rs.close();
			ps.close();
			con.commit();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public BigDecimal getVlrprod() {
	
		return vlrprod;
	}
	
	public void setVlrprod( BigDecimal vlrprodvenda ) {
	
		this.vlrprod = vlrprodvenda;
	}
	
	public BigDecimal getVlrdesc() {
	
		return vlrdesc;
	}
	
	public void setVlrdesc( BigDecimal vlrdescvenda ) {
	
		this.vlrdesc = vlrdescvenda;
	}
	
	public BigDecimal getVlricms() {
	
		return vlricms;
	}
	
	public void setVlricms( BigDecimal vlricmsvenda ) {
	
		this.vlricms = vlricmsvenda;
	}
	
	public BigDecimal getVlroutras() {
	
		return vlroutras;
	}
	
	public void setVlroutras( BigDecimal vlroutrasvenda ) {
	
		this.vlroutras = vlroutrasvenda;
	}
	
	public BigDecimal getVlrcomis() {
	
		return vlrcomis;
	}
	
	public void setVlrcomis( BigDecimal vlrcomis ) {
	
		this.vlrcomis = vlrcomis;
	}
	
	public BigDecimal getVlrfrete() {
	
		return vlrfrete;
	}
	
	public void setVlrfrete( BigDecimal vlrfrete ) {
	
		this.vlrfrete = vlrfrete;
	}
	
	public BigDecimal getVlradic() {
	
		return vlradic;
	}
	
	public void setVlradic( BigDecimal vlradic ) {
	
		this.vlradic = vlradic;
	}
	
	public BigDecimal getVlripi() {
	
		return vlripi;
	}
	
	public void setVlripi( BigDecimal vlripi ) {
	
		this.vlripi = vlripi;
	}
	
	public BigDecimal getVlrcofins() {
	
		return vlrcofins;
	}

	public void setVlrcofins( BigDecimal vlrcofins ) {
	
		this.vlrcofins = vlrcofins;
	}

	public BigDecimal getVlrpis() {
		return vlrpis;
	}

	public void setVlrpis(BigDecimal vlrpis) {
		this.vlrpis = vlrpis;
	}

	public BigDecimal getVlrir() {
	
		return vlrir;
	}

	public void setVlrir( BigDecimal vlrir ) {
	
		this.vlrir = vlrir;
	}

	public BigDecimal getVlrcsocial() {
	
		return vlrcsocial;
	}

	public void setVlrcsocial( BigDecimal vlrcsocial ) {
	
		this.vlrcsocial = vlrcsocial;
	}

	public BigDecimal getVlrpisit() {
		return vlrpisit;
	}

	public void setVlrpisit(BigDecimal vlrpisit) {
		this.vlrpisit = vlrpisit;
	}

	public BigDecimal getVlrcofinsit() {
		return vlrcofinsit;
	}

	public void setVlrcofinsit(BigDecimal vlrcofinsit) {
		this.vlrcofinsit = vlrcofinsit;
	}

	public BigDecimal getVlrcsocialit() {
		return vlrcsocialit;
	}

	public void setVlrcsocialit(BigDecimal vlrcsocialit) {
		this.vlrcsocialit = vlrcsocialit;
	}

	public BigDecimal getVlririt() {
		return vlririt;
	}

	public void setVlririt(BigDecimal vlririt) {
		this.vlririt = vlririt;
	}

	private void calcTotCusto(String tipocusto) {
		BigDecimal calc = null;
		
		try {
			if("M".equals( tipocusto )) {
				calc = getVlrcustompmvenda();	
			}
			else if ("P".equals( tipocusto )) {
				calc = getVlrcustopeps();
			}
			else if ("U".equals( tipocusto )) {
				calc = getVlrcustouc();
			}

			System.out.println("VALOR TOT. CUSTO COMPRA: " + calc);
			
			// Se frete for FOB e não destacado na nota é custo. 
			if("F".equals(tipofrete) && "N".equals(adicfrete)) {
				calc = calc.add( getVlrfrete() );
				System.out.println("VALOR TOT. FRETE: " + getVlrfrete());
			}
			// Se frete for CIF e destacado na nota é custo, pois é faturado.
			else if ("C".equals(tipofrete) && "S".equals(adicfrete)) {
				calc = calc.add( getVlrfrete() );
				System.out.println("VALOR TOT. FRETE: " + getVlrfrete());
			}

			calc = calc.add( getVlrcomis() );	
			System.out.println("VALOR TOT. COMISSÃO: " + getVlrcomis());
			
			calc = calc.add( getVlrpis());
			System.out.println("VALOR TOT. PIS: " + getVlrpis());
			
			calc = calc.add( getVlrcofins()) ;
			System.out.println("VALOR TOT. COFINS: " + getVlrcofins());
			
			calc = calc.add( getVlricms()) ;
			System.out.println("VALOR TOT. ICMS: " + getVlricms());
			
			calc = calc.add( getVlrcsocial()) ;
			System.out.println("VALOR TOT. CSOCIAL: " + getVlrcsocial());
			
			calc = calc.add( getVlrir()) ;
			System.out.println("VALOR TOT. IR: " + getVlrir());
			
			calc = calc.add( getVlripi()) ;
			System.out.println("VALOR TOT. IPI: " + getVlripi());
			
			setTotcusto( calc );
			setTotlucro( getTotfat().subtract( calc ) );
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void calcItemCusto(String tipocusto) {
		BigDecimal calc = null;
		
		try {
			if("M".equals( tipocusto )) {
				calc = getVlrcustompmit();	
			}
			else if ("P".equals( tipocusto )) {
				calc = getVlrcustopepsit();
			}
			else if ("U".equals( tipocusto )) {
				calc = getVlrcustoucit();
			}
			
			System.out.println("VALOR ITEM CUSTO COMPRA: " + calc);

			// Se frete for FOB e não destacado na nota é custo. 
			if("F".equals(tipofrete) && "N".equals(adicfrete)) {
				calc = calc.add( getVlrfreteitvenda() );
				System.out.println("VALOR ITEM FRETE: " + getVlrfreteitvenda());
			}
			// Se frete for CIF e destacado na nota é custo, pois é faturado.
			else if ("C".equals(tipofrete) && "S".equals(adicfrete)) {
				calc = calc.add( getVlrfrete() );
				System.out.println("VALOR ITEM FRETE: " + getVlrfrete());
			}

			calc = calc.add( getVlrcomisitvenda() );	
			System.out.println("VALOR ITEM COMISSÃO: " + getVlrcomisitvenda());
			
			calc = calc.add( getVlrpisit());
			System.out.println("VALOR ITEM PIS: " + getVlrpisit());
			
			calc = calc.add( getVlrcofinsit()) ;
			System.out.println("VALOR ITEM COFINS: " + getVlrcofinsit());
			
			calc = calc.add( getVlricmsitvenda()) ;
			System.out.println("VALOR ITEM ICMS: " + getVlricmsitvenda());
			
			calc = calc.add( getVlrcsocialit()) ;
			System.out.println("VALOR ITEM CSOCIAL: " + getVlrcsocialit());
			
			calc = calc.add( getVlririt()) ;
			System.out.println("VALOR ITEM IR: " + getVlririt());
			
			calc = calc.add( getVlripiitvenda()) ;
			System.out.println("VALOR ITEM IPI: " + getVlripiitvenda());
						
			setItemcusto( calc );
			setItemlucro( getItemfat().subtract( calc ) );
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public BigDecimal getVlrcustompmvenda() {
	
		return vlrcustompm;
	}

	public void setVlrcustompm( BigDecimal vlrcustompm ) {
	
		this.vlrcustompm = vlrcustompm;
	}

	public BigDecimal getVlrcustopeps() {
	
		return vlrcustopeps;
	}

	public void setVlrcustopeps( BigDecimal vlrcustopeps ) {
	
		this.vlrcustopeps = vlrcustopeps;
	}

	public BigDecimal getVlrcustouc() {
	
		return vlrcustouc;
	}

	public void setVlrcustouc( BigDecimal vlrcustouc ) {
	
		this.vlrcustouc = vlrcustouc;
	}

	public BigDecimal getVlrlucro() {
	
		return vlrlucro;
	}

	public void setVlrlucro( BigDecimal vlrlucro ) {
	
		this.vlrlucro = vlrlucro;
	}

	public BigDecimal getTotcusto() {
		if(totcusto!=null)		
			return totcusto;
		else
			return new BigDecimal(0);
	}

	public void setTotcusto( BigDecimal totcusto ) {
	
		this.totcusto = totcusto;
	}

	private void calcTotFat() {
		BigDecimal calc = null;
		try {
			
			calc = vlrprod.multiply(fatLucro) ;			
			calc = calc.add( vlradic );
			calc = calc.subtract( vlrdesc );			

			// Se frete for destacado na nota, entra como valor faturado
			if("S".equals(adicfrete)) {
				calc = calc.add( vlrfrete );
			}
			
			calc = calc.add(vlroutras);
			calc = calc.add(vlripi);

			setTotfat( calc );
			
			System.out.println("VALOR FATURADO:" + calc.toString());
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void calcItemFat() {
		BigDecimal calc = null;
		try {
			
			calc = vlrprodit.multiply(fatLucro) ;				
			calc = calc.add( vlradicit );
			calc = calc.subtract( vlrdescit );			

			// Se frete for destacado na nota, entra como valor faturado
			if("S".equals(adicfrete)) {
				calc = calc.add( vlrfreteit );
			}
			
			calc = calc.add(vlroutrasit);
			calc = calc.add(vlripiit);

			setItemfat( calc );
			
			System.out.println("VALOR FATURADO ITEM:" + calc.toString());
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void calcTotLucro() {
		BigDecimal perclucro = null;
		BigDecimal vlrlucro = null;
		
		try {

			vlrlucro = getTotfat().subtract( getTotcusto() );
			
			perclucro = (vlrlucro.multiply( new BigDecimal(100) )).divide( getTotfat(), 0, BigDecimal.ROUND_DOWN )  ;
			
			setVlrlucro( vlrlucro );
			
			setPerclucrvenda( perclucro );
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void calcItemLucro() {
		BigDecimal percitemlucro = null;
		BigDecimal vlritemlucro = null;
		
		try {

			vlritemlucro = getItemfat().subtract( getItemcusto() );
			
			percitemlucro = (vlritemlucro.multiply( new BigDecimal(100) )).divide( getItemfat(), 0, BigDecimal.ROUND_DOWN )  ;
			
			setVlrlucroitvenda( vlritemlucro );
			
			setPerclucritvenda( percitemlucro );
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTipofrete() {
		
		return tipofrete;
	}

	public void setTipofrete( String tipofrete ) {
	
		this.tipofrete = tipofrete;
	}

	public BigDecimal getPerclucrvenda() {
	
		return perclucr;
	}
	
	public void setPerclucrvenda( BigDecimal perclucrvenda ) {
	
		this.perclucr = perclucrvenda;
	}
	
	public String getAdicfretevd() {
		
		return adicfrete;
	}

	public void setAdicfrete( String adicfrete ) {
	
		this.adicfrete = adicfrete;
	}
	
	public BigDecimal getTotlucro() {
		
		return totlucro;
	}

	public void setTotlucro( BigDecimal totlucro ) {
	
		this.totlucro = totlucro;
	}
	
	public BigDecimal getVlrcustompmit() {
	
		return vlrcustompmit;
	}
	
	public void setVlrcustompmit( BigDecimal vlrcustompmit ) {
	
		this.vlrcustompmit = vlrcustompmit;
	}
	
	public BigDecimal getVlrcustopepsit() {
	
		return vlrcustopepsit;
	}

	public void setVlrcustopepsit( BigDecimal vlrcustopepsit ) {
	
		this.vlrcustopepsit = vlrcustopepsit;
	}

	public BigDecimal getVlrcustoucit() {
	
		return vlrcustoucit;
	}

	public void setVlrcustoucit( BigDecimal vlrcustoucitvenda ) {
	
		this.vlrcustoucit = vlrcustoucitvenda;
	}
	
	public BigDecimal getVlrlucroitvenda() {
	
		return vlrlucroit;
	}
	
	public void setVlrlucroitvenda( BigDecimal vlrlucroitvenda ) {
	
		this.vlrlucroit = vlrlucroitvenda;
	}
	
	public BigDecimal getVlrproditvenda() {
	
		return vlrprodit;
	}
	
	public void setVlrproditvenda( BigDecimal vlrproditvenda ) {
	
		this.vlrprodit = vlrproditvenda;
	}
	
	public BigDecimal getVlrdescitvenda() {
	
		return vlrdescit;
	}

	public void setVlrdescitvenda( BigDecimal vlrdescitvenda ) {
	
		this.vlrdescit = vlrdescitvenda;
	}

	public BigDecimal getVlricmsitvenda() {
	
		return vlricmsit;
	}

	public void setVlricmsitvenda( BigDecimal vlricmsitvenda ) {
	
		this.vlricmsit = vlricmsitvenda;
	}
	
	public BigDecimal getVlroutrasitvenda() {
	
		return vlroutrasit;
	}
	
	public void setVlroutrasitvenda( BigDecimal vlroutrasitvenda ) {
	
		this.vlroutrasit = vlroutrasitvenda;
	}
	
	public BigDecimal getVlrcomisitvenda() {
	
		return vlrcomisit;
	}

	public void setVlrcomisitvenda( BigDecimal vlrcomisitvenda ) {
	
		this.vlrcomisit = vlrcomisitvenda;
	}
	
	public BigDecimal getVlrfreteitvenda() {
	
		return vlrfreteit;
	}

	public void setVlrfreteitvenda( BigDecimal vlrfreteitvenda ) {
	
		this.vlrfreteit = vlrfreteitvenda;
	}

	public BigDecimal getVlradicitvenda() {
	
		return vlradicit;
	}
	
	public void setVlradicitvenda( BigDecimal vlradicitvenda ) {
	
		this.vlradicit = vlradicitvenda;
	}
	
	public BigDecimal getVlripiitvenda() {
	
		return vlripiit;
	}
	
	public void setVlripiitvenda( BigDecimal vlripiitvenda ) {
	
		this.vlripiit = vlripiitvenda;
	}
		
	public BigDecimal getItemfat() {
	
		return itemfat;
	}
	
	public void setItemfat( BigDecimal itemfat ) {
	
		this.itemfat = itemfat;
	}

	public BigDecimal getItemcusto() {
	
		return itemcusto;
	}

	public void setItemcusto( BigDecimal itemcusto ) {
	
		this.itemcusto = itemcusto;
	}

	public BigDecimal getItemlucro() {
	
		return itemlucro;
	}
	
	public void setItemlucro( BigDecimal itemlucro ) {
	
		this.itemlucro = itemlucro;
	}
	
	public BigDecimal getPerclucritvenda() {
		
		return perclucrit;
	}
	
	public void setPerclucritvenda( BigDecimal perclucritvenda ) {
	
		this.perclucrit = perclucritvenda;
	}

	private void carregaOrcamento(Integer codorc, String tipoorc) {		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		try {
			
			sql.append( "select " );
			
			sql.append( "coalesce(vd.vlrprodvenda,0) vlrprodvenda , coalesce(vd.vlrdescvenda,0) vlrdescvenda, coalesce(vd.vlricmsvenda,0) vlricmsvenda, ");
			sql.append( "coalesce(vd.vlroutrasvenda,0) vlroutrasvenda , coalesce(vd.vlrcomisvenda,0) vlrcomisvenda, coalesce(vd.vlradicvenda,0) vlradicvenda, ");
			sql.append( "coalesce(vd.vlripivenda,0) vlripivenda, coalesce(vd.vlrpisvenda,0) vlrpisvenda, coalesce(vd.vlrcofinsvenda,0) vlrcofinsvenda, ");
			sql.append( "coalesce(vd.vlrirvenda,0) vlrirvenda, coalesce(vd.vlrcsocialvenda,0) vlrcsocialvenda, ");
			sql.append( "coalesce(fr.vlrfretevd,0) vlrfretevd, fr.tipofretevd, fr.adicfretevd, ");
			
			sql.append( "sum(icv.vlrcustopeps * iv.qtditvenda) as vlrcustopeps, ");
			sql.append( "sum(icv.vlrcustompm * iv.qtditvenda) as vlrcustompm, ");
			sql.append( "sum(icv.vlrprecoultcp * iv.qtditvenda) as vlrcustouc ");
			
			sql.append( "from " );
			sql.append( "vdvenda vd left outer join vdfretevd fr on ");
			sql.append( "fr.codemp=vd.codemp and fr.codfilial=vd.codfilial and fr.codvenda=vd.codvenda ");
			sql.append( "and fr.tipovenda=vd.tipovenda, ");
			
			sql.append( "vditvenda iv left outer join vditcustovenda icv on ");
			sql.append( "icv.codemp=iv.codemp and icv.codfilial=iv.codfilial and icv.codvenda = iv.codvenda ");
			sql.append( "and icv.tipovenda=iv.tipovenda and icv.coditvenda=iv.coditvenda ");
			
			sql.append( "where vd.codemp=? and vd.codfilial=? and vd.codvenda=? and vd.tipovenda=? ");
			sql.append( "and iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.tipovenda=vd.tipovenda and ");
			sql.append( "iv.codvenda=vd.codvenda ");
			
			sql.append( "group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14 ");			

			System.out.println(sql.toString());
			
			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setInt( 3, codorc );
			ps.setString( 4, tipoorc );
			
			rs = ps.executeQuery();

			if ( rs.next() ) {
				setVlrprod( rs.getBigDecimal( "vlrprodvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrprodvenda" ));
				setVlrdesc( rs.getBigDecimal( "vlrdescvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrdescvenda" ));
				setVlricms( rs.getBigDecimal( "vlricmsvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlricmsvenda" ));
				setVlroutras( rs.getBigDecimal( "vlroutrasvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlroutrasvenda" ));
				setVlrcomis( rs.getBigDecimal( "vlrcomisvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcomisvenda" ));
				setVlrfrete( rs.getBigDecimal( "vlrfretevd" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrfretevd" ));
				setVlradic( rs.getBigDecimal( "vlradicvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlradicvenda" ));
				setVlripi( rs.getBigDecimal( "vlripivenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlripivenda" ));
				setVlrpis( rs.getBigDecimal( "vlrpisvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrpisvenda" ));
				setVlrcofins( rs.getBigDecimal( "vlrcofinsvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcofinsvenda" ));
				setVlrir( rs.getBigDecimal( "vlrirvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrirvenda" ));
				setVlrcsocial( rs.getBigDecimal( "vlrcsocialvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcsocialvenda" ));
				setVlrcustouc( rs.getBigDecimal( "vlrcustouc" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustouc" ));
				setVlrcustompm( rs.getBigDecimal( "vlrcustompm" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustompm" ));
				setVlrcustopeps( rs.getBigDecimal( "vlrcustopeps" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustopeps" )); 
				setTipofrete( rs.getString( "tipofretevd" )==null ? "F" : rs.getString( "tipofretevd" ));
				setAdicfrete( rs.getString( "adicfretevd" )==null ? "N" : rs.getString( "adicfretevd" ));
			}

			rs.close();
			ps.close();

			con.commit();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	

	
}
