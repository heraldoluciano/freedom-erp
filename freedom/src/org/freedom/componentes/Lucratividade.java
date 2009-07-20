package org.freedom.componentes;

import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	private BigDecimal vlrprodvenda = null;
	private BigDecimal vlrdescvenda = null;
	private BigDecimal vlricmsvenda = null;
	private BigDecimal vlroutrasvenda = null;
	private BigDecimal vlrcomisvenda = null;
	private BigDecimal vlrfretevenda = null;
	private BigDecimal vlradicvenda = null;
	private BigDecimal vlripivenda = null;
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
	private BigDecimal vlroutrasitvenda = null;
	private BigDecimal vlrcomisitvenda = null;
	private BigDecimal vlrfreteitvenda = null;
	private BigDecimal vlradicitvenda = null;
	private BigDecimal vlripiitvenda = null;

	private BigDecimal perclucrvenda = null;	
	private BigDecimal perclucritvenda = null;
	
	private String tipofrete = null;
	private String adicfretevd = null;
	
	private DbConnection con = null;
		
	public Lucratividade(Integer codvenda, String tipovenda, Integer itvenda, DbConnection con) {
		
		this.con = con; 
		
		if(codvenda!=null) {
			carregaVenda( codvenda, tipovenda);
			carregaItem( codvenda, tipovenda, itvenda );
			calcTotFat();
			calcTotCusto( "M" );
			calcTotLucro();
			
			calcItemFat();
			calcItemCusto( "M" );
			calcItemLucro();
		}
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
			
			sql.append( "select vd.vlrprodvenda, vd.vlrdescvenda, vd.vlricmsvenda, ");
			sql.append( "vd.vlroutrasvenda, vd.vlrcomisvenda, vd.vlradicvenda, ");
			sql.append( "vd.vlripivenda, vd.vlrcofinsvenda, vd.vlrirvenda, vd.vlrcsocialvenda, ");
			sql.append( "fr.vlrfretevd,fr.tipofretevd,fr.adicfretevd, ");
			sql.append( "sum(icv.vlrcustopeps * iv.qtditvenda) as vlrcustopeps, ");
			sql.append( "sum(icv.vlrcustompm * iv.qtditvenda) as vlrcustompm, ");
			sql.append( "sum(icv.vlrprecoultcp * iv.qtditvenda) as vlrcustouc ");
			sql.append( "from vdvenda vd left outer join vdfretevd fr ");
			sql.append( "on fr.codemp=vd.codemp and fr.codfilial=vd.codfilial and fr.codvenda=vd.codvenda ");
			sql.append( "and fr.tipovenda=vd.tipovenda ");
			sql.append( ", vditvenda iv left outer join vditcustovenda icv on ");
			sql.append( "icv.codemp=iv.codemp and icv.codfilial=iv.codfilial and icv.codvenda = iv.codvenda ");
			sql.append( "and icv.tipovenda=iv.tipovenda and icv.coditvenda=iv.coditvenda ");
			sql.append( "where vd.codemp=? and vd.codfilial=? and vd.codvenda=? and vd.tipovenda=? ");
			sql.append( "and iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.tipovenda=vd.tipovenda and ");
			sql.append( "iv.codvenda=vd.codvenda ");
			sql.append( "group by 1,2,3,4,5,6,7,8,9,10,11,12,13 ");			

			System.out.println(sql.toString());
			
			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setInt( 3, codvenda );
			ps.setString( 4, tipovenda );
			
			rs = ps.executeQuery();

			if ( rs.next() ) {
				setVlrprodvenda( rs.getBigDecimal( "vlrprodvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrprodvenda" ));
				setVlrdescvenda( rs.getBigDecimal( "vlrdescvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrdescvenda" ));
				setVlricmsvenda( rs.getBigDecimal( "vlricmsvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlricmsvenda" ));
				setVlroutrasvenda( rs.getBigDecimal( "vlroutrasvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlroutrasvenda" ));
				setVlrcomisvenda( rs.getBigDecimal( "vlrcomisvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcomisvenda" ));
				setVlrfretevenda( rs.getBigDecimal( "vlrfretevd" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrfretevd" ));
				setVlradicvenda( rs.getBigDecimal( "vlradicvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlradicvenda" ));
				setVlripivenda( rs.getBigDecimal( "vlripivenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlripivenda" ));
				setVlrcofinsvenda( rs.getBigDecimal( "vlrcofinsvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcofinsvenda" ));
				setVlrirvenda( rs.getBigDecimal( "vlrcofinsvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcofinsvenda" ));
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

	private void carregaItem(Integer codvenda, String tipovenda, Integer coditvenda) {		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		try {
			
			sql.append( "select iv.vlrproditvenda, iv.vlrdescitvenda, iv.vlricmsitvenda, ");			
			sql.append( "iv.vlroutrasitvenda, iv.vlrcomisitvenda, iv.vlradicitvenda, ");
			sql.append( "iv.vlripiitvenda, ");
			sql.append( "iv.vlrfreteitvenda, ");
			sql.append( "(icv.vlrcustopeps * iv.qtditvenda) as vlrcustoitpeps, ");
			sql.append( "(icv.vlrcustompm * iv.qtditvenda) as vlrcustoitmpm, ");
			sql.append( "(icv.vlrprecoultcp * iv.qtditvenda) as vlrcustoituc ");
			sql.append( "from vditvenda iv left outer join vditcustovenda icv on ");
			sql.append( "icv.codemp=iv.codemp and icv.codfilial=iv.codfilial and icv.codvenda = iv.codvenda ");
			sql.append( "and icv.tipovenda=iv.tipovenda and icv.coditvenda=iv.coditvenda ");
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
				setVlrproditvenda( rs.getBigDecimal( "vlrproditvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrproditvenda" ));
				setVlrdescitvenda( rs.getBigDecimal( "vlrdescitvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrdescitvenda" ));
				setVlricmsitvenda( rs.getBigDecimal( "vlricmsitvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlricmsitvenda" ));
				setVlroutrasitvenda( rs.getBigDecimal( "vlroutrasitvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlroutrasitvenda" ));
				setVlrcomisitvenda( rs.getBigDecimal( "vlrcomisitvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcomisitvenda" ));
				setVlrfreteitvenda( rs.getBigDecimal( "vlrfreteitvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrfreteitvenda" ));
				setVlradicitvenda( rs.getBigDecimal( "vlradicitvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlradicitvenda" ));
				setVlripiitvenda( rs.getBigDecimal( "vlripiitvenda" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlripiitvenda" ));
				setVlrcustoucitvenda( rs.getBigDecimal( "vlrcustoituc" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustoituc" ));
				setVlrcustompmitvenda( rs.getBigDecimal( "vlrcustoitmpm" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustoitmpm" ));
				setVlrcustopepsitvenda( rs.getBigDecimal( "vlrcustoitpeps" )==null ? new BigDecimal(0) : rs.getBigDecimal( "vlrcustoitpeps" )); 
			}

			rs.close();
			ps.close();
			con.commit();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	

	
	
	public BigDecimal getVlrprodvenda() {
	
		return vlrprodvenda;
	}


	
	public void setVlrprodvenda( BigDecimal vlrprodvenda ) {
	
		this.vlrprodvenda = vlrprodvenda;
	}


	
	public BigDecimal getVlrdescvenda() {
	
		return vlrdescvenda;
	}


	
	public void setVlrdescvenda( BigDecimal vlrdescvenda ) {
	
		this.vlrdescvenda = vlrdescvenda;
	}


	
	public BigDecimal getVlricmsvenda() {
	
		return vlricmsvenda;
	}


	
	public void setVlricmsvenda( BigDecimal vlricmsvenda ) {
	
		this.vlricmsvenda = vlricmsvenda;
	}


	
	public BigDecimal getVlroutrasvenda() {
	
		return vlroutrasvenda;
	}


	
	public void setVlroutrasvenda( BigDecimal vlroutrasvenda ) {
	
		this.vlroutrasvenda = vlroutrasvenda;
	}


	
	public BigDecimal getVlrcomisvenda() {
	
		return vlrcomisvenda;
	}


	
	public void setVlrcomisvenda( BigDecimal vlrcomisvenda ) {
	
		this.vlrcomisvenda = vlrcomisvenda;
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

			// Se frete for FOB e não destacado na nota é custo. 
			if("F".equals(tipofrete) && "N".equals(adicfretevd)) {
				calc = calc.add( getVlrfretevenda() );
			}

			calc = calc.add( getVlrcomisvenda() );		
			calc = calc.add( getVlrcofinsvenda()) ;			
			calc = calc.add( getVlricmsvenda()) ;
			calc = calc.add( getVlrcsocialvenda()) ;
			calc = calc.add( getVlrirvenda()) ;
			calc = calc.add( getVlripivenda()) ;
			
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

			// Se frete for FOB e não destacado na nota é custo. 
			if("F".equals(tipofrete) && "N".equals(adicfretevd)) {
				calc = calc.add( getVlrfreteitvenda() );
			}

			calc = calc.add( getVlrcomisitvenda() );			
			calc = calc.add( getVlricmsitvenda()) ;
			calc = calc.add( getVlripiitvenda()) ;
			
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

	private BigDecimal calcTotLucr() {
		BigDecimal ret = null;
		
		try {
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
		
	}
	
	private void calcTotFat() {
		BigDecimal calc = null;
		try {
			
			calc = vlrprodvenda;
			calc = calc.add( vlradicvenda );
			calc = calc.subtract( vlrdescvenda );			

			// Se frete for destacado na nota, entra como valor faturado
			if("S".equals(adicfretevd)) {
				calc = calc.add( vlrfretevenda );
			}
			
			calc = calc.add(vlroutrasvenda);
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
			
			calc = vlrproditvenda;
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
	
	private BigDecimal vlrcustompmvenda = null;
	
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

	
}
