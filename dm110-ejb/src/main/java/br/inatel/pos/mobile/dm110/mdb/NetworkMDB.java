package br.inatel.pos.mobile.dm110.mdb;

import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import br.inatel.pos.mobile.dm110.dao.NetworkDAO;
import br.inatel.pos.mobile.dm110.entities.NetworkAddress;
import br.inatel.pos.mobile.dm110.entities.NetworkAddressesList;
import br.inatel.pos.mobile.dm110.utils.Ping;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType",
								  propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination",
								  propertyValue = "java:/jms/queue/networkQueue"),
		@ActivationConfigProperty(propertyName = "maxSession",
		  						  propertyValue = "10")
})
public class NetworkMDB implements MessageListener {

	@EJB
	private NetworkDAO dao;
	
	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof ObjectMessage) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				Object obj = objectMessage.getObject();
				if (obj instanceof NetworkAddressesList) {
					NetworkAddressesList networkAddressesList = (NetworkAddressesList) obj;
					List<String> networkAddresses = networkAddressesList.getNetworkAddressesList();
					boolean isActive;
					
					for (String ip : networkAddresses) {
						isActive = Ping.execPing(ip);
						
						NetworkAddress networkAddress = new NetworkAddress();
						networkAddress.setIp(ip);
						networkAddress.setIsactive(isActive);
						System.out.println("IP: " + ip + " is active: " + isActive + "\n");
						dao.insert(networkAddress);
					}
				}
				else {
					System.out.println("O objeto não é uma instância de NetworkAddressesList");
				}
			}
			else {
				System.out.println("Message não é uma instância de ObjectMessage");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
