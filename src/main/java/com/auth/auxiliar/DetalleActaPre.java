package com.auth.auxiliar;

import java.math.BigDecimal;

import com.auth.entity.HJira;

public class DetalleActaPre {
	private HJira hjira;
	private BigDecimal totalHoras;
	private BigDecimal totalHorasGesDem;
	public HJira getHjira() {
		return hjira;
	}
	public void setHjira(HJira hjira) {
		this.hjira = hjira;
	}
	public BigDecimal getTotalHoras() {
		return totalHoras;
	}
	public void setTotalHoras(BigDecimal totalHoras) {
		this.totalHoras = totalHoras;
	}
	public BigDecimal getTotalHorasGesDem() {
		return totalHorasGesDem;
	}
	public void setTotalHorasGesDem(BigDecimal totalHorasGesDem) {
		this.totalHorasGesDem = totalHorasGesDem;
	}

	public DetalleActaPre(HJira hjira, BigDecimal totalHoras, BigDecimal totalHorasGesDem) {
		super();
		this.hjira = hjira;
		this.totalHoras = totalHoras;
		this.totalHorasGesDem = totalHorasGesDem;
	}
	
	
}
