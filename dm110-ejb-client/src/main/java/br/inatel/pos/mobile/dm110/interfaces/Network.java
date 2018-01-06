package br.inatel.pos.mobile.dm110.interfaces;

public interface Network {
	
	void generateIps(String ip, String mask);
	
	String getIpStatus(String ip);

}
