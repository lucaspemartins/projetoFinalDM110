package br.inatel.pos.mobile.dm110.entities;

import java.io.Serializable;
import java.util.List;

public class NetworkAddressesList implements Serializable {

	private static final long serialVersionUID = 78088392782173986L;
	
	private List<String> networkAddressesList;

	public List<String> getNetworkAddressesList() {
		return networkAddressesList;
	}

	public void setNetworkAddressesList(List<String> networkAddressesList) {
		this.networkAddressesList = networkAddressesList;
	}

}
