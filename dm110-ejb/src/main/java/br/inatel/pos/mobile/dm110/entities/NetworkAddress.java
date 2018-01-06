package br.inatel.pos.mobile.dm110.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class NetworkAddress implements Serializable {

	private static final long serialVersionUID = -3077507576339762388L;
	
	@Id
	@Column(name = "ip")
	private String ip;
	
	@Column(name = "isactive")
	private boolean isactive;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isActive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
}
