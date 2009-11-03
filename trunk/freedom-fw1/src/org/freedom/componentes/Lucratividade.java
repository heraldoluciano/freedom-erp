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
	private BigDecimal vlrcustopepsvenda = null;
	private BigDecimal vlrcustoucvenda = null;
	private BigDecimal vlrlucrovenda = null;
	private BigDecimal vlrprod = null;
	private BigDecimal vlrdesc = null;
	private BigDecimal vlricms = null;
	private BigDecimal vlroutras = null;
	private BigDecimal vlrcomis = null;
	private BigDecimal vlrfretevenda = null;
	private BigDecimal vlradicvenda = null;
	private BigDecimal vlripivenda = null;
	private BigDecimal vlrpisvenda = null;
	private BigDecimal vlrcofinsvenda = null;
	private BigDecimal vlrirvenda = null;
	private BigDecimal vlrcsocialvenda = null;	
	private BigDecimal vlrcustompmitvenda = null;
	private BigDecimal vlrcustopepsitvenda = null;
	private BigDecimal vlrcustoucitvenda = null;
	private BigDecimal vlrlucroitvenda = null;
	private BigDecimal vlrproditvenda = null;
	private BigDecimal vlrdescitvenda = null;
	private BigDecimal vlricmsitvenda = null;
	private BigDecimal vlrpisitvenda = null;
	private BigDecimal vlrcofinsitvenda = null;
	private BigDecimal vlrcsocialitvenda = null;
	private BigDecimal vlroutrasitvenda = null;
	private BigDecimal vlrcomisitvenda = null;
	private BigDecimal vlrfreteitvenda = null;
	private BigDecimal vlradicitvenda = null;
	private BigDecimal vlripiitvenda = null;
	private BigDecimal vlriritvenda = null;
	private BigDecimal perclucrvenda = null;	
	private BigDecimal perclucritvenda = null;
	private String tipofrete = null;
	private String adicfretevd = null;	
	private BigDecimal fatLucro = new BigDecimal(1);	
	private BigDecimal vlrcustompmvenda = null;	
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
				setVlrfretevenda( rs.getBigDecimal( "vlrfretevd" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrfretevd" ));
				setVlradicvenda( rs.getBigDecimal( "vlradicvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlradicvenda" ));
				setVlripivenda( rs.getBigDecimal( "vlripivenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlripivenda" ));
				setVlrpisvenda( rs.getBigDecimal( "vlrpisvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrpisvenda" ));
				setVlrcofinsvenda( rs.getBigDecimal( "vlrcofinsvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcofinsvenda" ));
				setVlrirvenda( rs.getBigDecimal( "vlrirvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrirvenda" ));
				setVlrcsocialvenda( rs.getBigDecimal( "vlrcsocialvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcsocialvenda" ));
				setVlrcustoucvenda( rs.getBigDecimal( "vlrcustouc" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustouc" ));
				setVlrcustompmvenda( rs.getBigDecimal( "vlrcustompm" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustompm" ));
				setVlrcustopepsvenda( rs.getBigDecimal( "vlrcustopeps" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustopeps" )); 
				setTipofrete( rs.getString( "tipofretevd" )==null ? "F" : rs.getString( "tipofretevd" ));
				setAdicfretevd( rs.getString( "adicfretevd" )==null ? "N" : rs.getString( "adicfretevd" ));
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
				setVlrfreteitvenda( rs.getBigDecimal( "vlrfreteitvenda" ));
				setVlradicitvenda( rs.getBigDecimal( "vlradicitvenda" ));
				setVlripiitvenda( rs.getBigDecimal( "vlripiitvenda" ));
				setVlrcustoucitvenda( rs.getBigDecimal( "vlrcustoituc" ));
				setVlrcustompmitvenda( rs.getBigDecimal( "vlrcustoitmpm" ));
				setVlrcustopepsitvenda( rs.getBigDecimal( "vlrcustoitpeps" )); 
				
				setVlrpisitvenda(rs.getBigDecimal( "vlrpis" ));
				setVlrcofinsitvenda(rs.getBigDecimal( "vlrcofins" ));
				setVlriritvenda(rs.getBigDecimal( "vlrir" ));
				setVlrcsocialitvenda( rs.getBigDecimal( "vlrcsocial" ));
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
	
	public void setVlrcomis( BigDecimal vlrcomisvenda ) {
	
		this.vlrcomis = vlrcomisvenda;
	}
	
	public BigDecimal getVlrfretevenda() {
	
		return vlrfretevenda;
	}
	
	public void setVlrfretevenda( BigDecimal vlrfretevenda ) {
	
		this.vlrfretevenda = vlrfretevenda;
	}
	
	public BigDecimal getVlradicvenda() {
	
		return vlradicvenda;
	}
	
	public void setVlradicvenda( BigDecimal vlradicvenda ) {
	
		this.vlradicvenda = vlradicvenda;
	}
	
	public BigDecimal getVlripivenda() {
	
		return vlripivenda;
	}
	
	public void setVlripivenda( BigDecimal vlripivenda ) {
	
		this.vlripivenda = vlripivenda;
	}
	
	public BigDecimal getVlrcofinsvenda() {
	
		return vlrcofinsvenda;
	}

	public void setVlrcofinsvenda( BigDecimal vlrcofinsvenda ) {
	
		this.vlrcofinsvenda = vlrcofinsvenda;
	}

	public BigDecimal getVlrpisvenda() {
		return vlrpisvenda;
	}

	public void setVlrpisvenda(BigDecimal vlrpisvenda) {
		this.vlrpisvenda = vlrpisvenda;
	}

	public BigDecimal getVlrirvenda() {
	
		return vlrirvenda;
	}

	public void setVlrirvenda( BigDecimal vlrirvenda ) {
	
		this.vlrirvenda = vlrirvenda;
	}

	public BigDecimal getVlrcsocialvenda() {
	
		return vlrcsocialvenda;
	}

	public void setVlrcsocialvenda( BigDecimal vlrcsocialvenda ) {
	
		this.vlrcsocialvenda = vlrcsocialvenda;
	}

	public BigDecimal getVlrpisitvenda() {
		return vlrpisitvenda;
	}

	public void setVlrpisitvenda(BigDecimal vlrpisitvenda) {
		this.vlrpisitvenda = vlrpisitvenda;
	}

	public BigDecimal getVlrcofinsitvenda() {
		return vlrcofinsitvenda;
	}

	public void setVlrcofinsitvenda(BigDecimal vlrcofinsitvenda) {
		this.vlrcofinsitvenda = vlrcofinsitvenda;
	}

	public BigDecimal getVlrcsocialitvenda() {
		return vlrcsocialitvenda;
	}

	public void setVlrcsocialitvenda(BigDecimal vlrcsocialitvenda) {
		this.vlrcsocialitvenda = vlrcsocialitvenda;
	}

	public BigDecimal getVlriritvenda() {
		return vlriritvenda;
	}

	public void setVlriritvenda(BigDecimal vlriritvenda) {
		this.vlriritvenda = vlriritvenda;
	}

	private void calcTotCusto(String tipocusto) {
		BigDecimal calc = null;
		
		try {
			if("M".equals( tipocusto )) {
				calc = getVlrcustompmvenda();	
			}
			else if ("P".equals( tipocusto )) {
				calc = getVlrcustopepsvenda();
			}
			else if ("U".equals( tipocusto )) {
				calc = getVlrcustoucvenda();
			}

			System.out.println("VALOR TOT. CUSTO COMPRA: " + calc);
			
			// Se frete for FOB e não destacado na nota é custo. 
			if("F".equals(tipofrete) && "N".equals(adicfretevd)) {
				calc = calc.add( getVlrfretevenda() );
				System.out.println("VALOR TOT. FRETE: " + getVlrfretevenda());
			}
			// Se frete for CIF e destacado na nota é custo, pois é faturado.
			else if ("C".equals(tipofrete) && "S".equals(adicfretevd)) {
				calc = calc.add( getVlrfretevenda() );
				System.out.println("VALOR TOT. FRETE: " + getVlrfretevenda());
			}

			calc = calc.add( getVlrcomis() );	
			System.out.println("VALOR TOT. COMISSÃO: " + getVlrcomis());
			
			calc = calc.add( getVlrpisvenda());
			System.out.println("VALOR TOT. PIS: " + getVlrpisvenda());
			
			calc = calc.add( getVlrcofinsvenda()) ;
			System.out.println("VALOR TOT. COFINS: " + getVlrcofinsvenda());
			
			calc = calc.add( getVlricms()) ;
			System.out.println("VALOR TOT. ICMS: " + getVlricms());
			
			calc = calc.add( getVlrcsocialvenda()) ;
			System.out.println("VALOR TOT. CSOCIAL: " + getVlrcsocialvenda());
			
			calc = calc.add( getVlrirvenda()) ;
			System.out.println("VALOR TOT. IR: " + getVlrirvenda());
			
			calc = calc.add( getVlripivenda()) ;
			System.out.println("VALOR TOT. IPI: " + getVlripivenda());
			
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
				calc = getVlrcustompmitvenda();	
			}
			else if ("P".equals( tipocusto )) {
				calc = getVlrcustopepsitvenda();
			}
			else if ("U".equals( tipocusto )) {
				calc = getVlrcustoucitvenda();
			}
			
			System.out.println("VALOR ITEM CUSTO COMPRA: " + calc);

			// Se frete for FOB e não destacado na nota é custo. 
			if("F".equals(tipofrete) && "N".equals(adicfretevd)) {
				calc = calc.add( getVlrfreteitvenda() );
				System.out.println("VALOR ITEM FRETE: " + getVlrfreteitvenda());
			}
			// Se frete for CIF e destacado na nota é custo, pois é faturado.
			else if ("C".equals(tipofrete) && "S".equals(adicfretevd)) {
				calc = calc.add( getVlrfretevenda() );
				System.out.println("VALOR ITEM FRETE: " + getVlrfretevenda());
			}

			calc = calc.add( getVlrcomisitvenda() );	
			System.out.println("VALOR ITEM COMISSÃO: " + getVlrcomisitvenda());
			
			calc = calc.add( getVlrpisitvenda());
			System.out.println("VALOR ITEM PIS: " + getVlrpisitvenda());
			
			calc = calc.add( getVlrcofinsitvenda()) ;
			System.out.println("VALOR ITEM COFINS: " + getVlrcofinsitvenda());
			
			calc = calc.add( getVlricmsitvenda()) ;
			System.out.println("VALOR ITEM ICMS: " + getVlricmsitvenda());
			
			calc = calc.add( getVlrcsocialitvenda()) ;
			System.out.println("VALOR ITEM CSOCIAL: " + getVlrcsocialitvenda());
			
			calc = calc.add( getVlriritvenda()) ;
			System.out.println("VALOR ITEM IR: " + getVlriritvenda());
			
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
	
		return vlrcustompmvenda;
	}

	public void setVlrcustompmvenda( BigDecimal vlrcustompmvenda ) {
	
		this.vlrcustompmvenda = vlrcustompmvenda;
	}

	public BigDecimal getVlrcustopepsvenda() {
	
		return vlrcustopepsvenda;
	}

	public void setVlrcustopepsvenda( BigDecimal vlrcustopepsvenda ) {
	
		this.vlrcustopepsvenda = vlrcustopepsvenda;
	}

	public BigDecimal getVlrcustoucvenda() {
	
		return vlrcustoucvenda;
	}

	public void setVlrcustoucvenda( BigDecimal vlrcustoucvenda ) {
	
		this.vlrcustoucvenda = vlrcustoucvenda;
	}

	public BigDecimal getVlrlucrovenda() {
	
		return vlrlucrovenda;
	}

	public void setVlrlucrovenda( BigDecimal vlrlucrovenda ) {
	
		this.vlrlucrovenda = vlrlucrovenda;
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
			calc = calc.add( vlradicvenda );
			calc = calc.subtract( vlrdesc );			

			// Se frete for destacado na nota, entra como valor faturado
			if("S".equals(adicfretevd)) {
				calc = calc.add( vlrfretevenda );
			}
			
			calc = calc.add(vlroutras);
			calc = calc.add(vlripivenda);

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
			
			calc = vlrproditvenda.multiply(fatLucro) ;				
			calc = calc.add( vlradicitvenda );
			calc = calc.subtract( vlrdescitvenda );			

			// Se frete for destacado na nota, entra como valor faturado
			if("S".equals(adicfretevd)) {
				calc = calc.add( vlrfreteitvenda );
			}
			
			calc = calc.add(vlroutrasitvenda);
			calc = calc.add(vlripiitvenda);

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
			
			setVlrlucrovenda( vlrlucro );
			
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
	
		return perclucrvenda;
	}
	
	public void setPerclucrvenda( BigDecimal perclucrvenda ) {
	
		this.perclucrvenda = perclucrvenda;
	}
	
	public String getAdicfretevd() {
		
		return adicfretevd;
	}

	public void setAdicfretevd( String adicfretevd ) {
	
		this.adicfretevd = adicfretevd;
	}
	
	public BigDecimal getTotlucro() {
		
		return totlucro;
	}

	public void setTotlucro( BigDecimal totlucro ) {
	
		this.totlucro = totlucro;
	}
	
	public BigDecimal getVlrcustompmitvenda() {
	
		return vlrcustompmitvenda;
	}
	
	public void setVlrcustompmitvenda( BigDecimal vlrcustompmitvenda ) {
	
		this.vlrcustompmitvenda = vlrcustompmitvenda;
	}
	
	public BigDecimal getVlrcustopepsitvenda() {
	
		return vlrcustopepsitvenda;
	}

	public void setVlrcustopepsitvenda( BigDecimal vlrcustopepsitvenda ) {
	
		this.vlrcustopepsitvenda = vlrcustopepsitvenda;
	}

	public BigDecimal getVlrcustoucitvenda() {
	
		return vlrcustoucitvenda;
	}

	public void setVlrcustoucitvenda( BigDecimal vlrcustoucitvenda ) {
	
		this.vlrcustoucitvenda = vlrcustoucitvenda;
	}
	
	public BigDecimal getVlrlucroitvenda() {
	
		return vlrlucroitvenda;
	}
	
	public void setVlrlucroitvenda( BigDecimal vlrlucroitvenda ) {
	
		this.vlrlucroitvenda = vlrlucroitvenda;
	}
	
	public BigDecimal getVlrproditvenda() {
	
		return vlrproditvenda;
	}
	
	public void setVlrproditvenda( BigDecimal vlrproditvenda ) {
	
		this.vlrproditvenda = vlrproditvenda;
	}
	
	public BigDecimal getVlrdescitvenda() {
	
		return vlrdescitvenda;
	}

	public void setVlrdescitvenda( BigDecimal vlrdescitvenda ) {
	
		this.vlrdescitvenda = vlrdescitvenda;
	}

	public BigDecimal getVlricmsitvenda() {
	
		return vlricmsitvenda;
	}

	public void setVlricmsitvenda( BigDecimal vlricmsitvenda ) {
	
		this.vlricmsitvenda = vlricmsitvenda;
	}
	
	public BigDecimal getVlroutrasitvenda() {
	
		return vlroutrasitvenda;
	}
	
	public void setVlroutrasitvenda( BigDecimal vlroutrasitvenda ) {
	
		this.vlroutrasitvenda = vlroutrasitvenda;
	}
	
	public BigDecimal getVlrcomisitvenda() {
	
		return vlrcomisitvenda;
	}

	public void setVlrcomisitvenda( BigDecimal vlrcomisitvenda ) {
	
		this.vlrcomisitvenda = vlrcomisitvenda;
	}
	
	public BigDecimal getVlrfreteitvenda() {
	
		return vlrfreteitvenda;
	}

	public void setVlrfreteitvenda( BigDecimal vlrfreteitvenda ) {
	
		this.vlrfreteitvenda = vlrfreteitvenda;
	}

	public BigDecimal getVlradicitvenda() {
	
		return vlradicitvenda;
	}
	
	public void setVlradicitvenda( BigDecimal vlradicitvenda ) {
	
		this.vlradicitvenda = vlradicitvenda;
	}
	
	public BigDecimal getVlripiitvenda() {
	
		return vlripiitvenda;
	}
	
	public void setVlripiitvenda( BigDecimal vlripiitvenda ) {
	
		this.vlripiitvenda = vlripiitvenda;
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
		
		return perclucritvenda;
	}
	
	public void setPerclucritvenda( BigDecimal perclucritvenda ) {
	
		this.perclucritvenda = perclucritvenda;
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
				setVlrfretevenda( rs.getBigDecimal( "vlrfretevd" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrfretevd" ));
				setVlradicvenda( rs.getBigDecimal( "vlradicvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlradicvenda" ));
				setVlripivenda( rs.getBigDecimal( "vlripivenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlripivenda" ));
				setVlrpisvenda( rs.getBigDecimal( "vlrpisvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrpisvenda" ));
				setVlrcofinsvenda( rs.getBigDecimal( "vlrcofinsvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcofinsvenda" ));
				setVlrirvenda( rs.getBigDecimal( "vlrirvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrirvenda" ));
				setVlrcsocialvenda( rs.getBigDecimal( "vlrcsocialvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcsocialvenda" ));
				setVlrcustoucvenda( rs.getBigDecimal( "vlrcustouc" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustouc" ));
				setVlrcustompmvenda( rs.getBigDecimal( "vlrcustompm" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustompm" ));
				setVlrcustopepsvenda( rs.getBigDecimal( "vlrcustopeps" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustopeps" )); 
				setTipofrete( rs.getString( "tipofretevd" )==null ? "F" : rs.getString( "tipofretevd" ));
				setAdicfretevd( rs.getString( "adicfretevd" )==null ? "N" : rs.getString( "adicfretevd" ));
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
