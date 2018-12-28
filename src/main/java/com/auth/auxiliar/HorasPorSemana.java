package com.auth.auxiliar;

import java.math.BigDecimal;
import java.util.Date;

public class HorasPorSemana {
	private Date fecha;
	private BigDecimal total;
	private String leyenda;
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}	
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getLeyenda() {
		return leyenda;
	}
	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
	}

	public HorasPorSemana() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HorasPorSemana(Date fecha, BigDecimal total) {
		super();
		this.fecha = fecha;
		this.total = total;
	}
	
}
