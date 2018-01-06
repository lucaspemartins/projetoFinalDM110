package br.inatel.pos.mobile.dm110.impl;

import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import br.inatel.pos.mobile.dm110.api.NetworkService;
import br.inatel.pos.mobile.dm110.interfaces.NetworkRemote;

@RequestScoped
public class NetworkServiceImpl implements NetworkService {
	
	@EJB(lookup = "java:app/dm110-ejb-1.0.0-SNAPSHOT/NetworkBean!br.inatel.pos.mobile.dm110.interfaces.NetworkRemote")
	private NetworkRemote networkBean;

	@Override
	public void generateIps(String ip, String mask) {
		int maskInteger = Integer.parseInt(mask);
		if (isInputValid(ip, maskInteger)) {
			networkBean.generateIps(ip, mask);
		}
		else {
			System.out.println("IP ou mascara nao sao validos. Por favor, insira novamente!");
		}
	}

	public boolean isInputValid(String ip, int mask) {
		if (isMaskValid(mask) && isIPValid(ip)) {
			return true;
		}
		return false;
	}
	
	public static boolean isMaskValid(int mask) {
	    if (mask >= 8 && mask <= 30) {
	    	return true;
	    }
	    return false;
	}
	
	public static boolean isIPValid(String ip) {
	    String regex = "\\b((25[0–5]|2[0–4]\\d|[01]?\\d\\d?)(\\.)){3}(25[0–5]|2[0–4]\\d|[01]?\\d\\d?)\\b";
	    return Pattern.matches(regex, ip);
	}

	@Override
	public String getIpStatus(String ip) {
		if (isIPValid(ip)) {
			return networkBean.getIpStatus(ip);
		}
		else {
			System.out.println("IP nao e valido. Insira novamente!");
			return "IP nao e valido. Insira novamente!";
		}
	}
}
