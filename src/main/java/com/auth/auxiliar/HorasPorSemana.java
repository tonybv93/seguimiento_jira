package com.auth.auxiliar;

import java.util.Date;

public class HorasPorSemana {
	private Date fecha;
	private long total;
	private String leyenda;

	
	public String getLeyenda() {
		return leyenda;
	}
	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}

	public HorasPorSemana() {

	}
	public HorasPorSemana(Date fecha, long total) {
		super();
		this.fecha = fecha;
		this.total = total;
	}	
	
}
