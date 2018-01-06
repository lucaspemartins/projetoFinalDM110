package br.inatel.pos.mobile.dm110.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import br.inatel.pos.mobile.dm110.dao.NetworkDAO;
import br.inatel.pos.mobile.dm110.entities.NetworkAddressesList;
import br.inatel.pos.mobile.dm110.interfaces.NetworkLocal;
import br.inatel.pos.mobile.dm110.interfaces.NetworkRemote;
import br.inatel.pos.mobile.dm110.utils.NetworkIpGen;

@Stateless
@Local(NetworkLocal.class)
@Remote(NetworkRemote.class)
public class NetworkBean implements NetworkLocal, NetworkRemote {
	
	@EJB
	private NetworkDAO dao;
	
	@Resource(lookup = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Resource(lookup = "java:/jms/queue/networkQueue")
	private Queue queue;	

	@Override
	public void generateIps(String ip, String mask) {
		
		int maskInteger = Integer.parseInt(mask);
		String[] networkAddresses = NetworkIpGen.generateIps(ip, maskInteger);
		List<String> networkAddressesList = Arrays.asList(networkAddresses);
		NetworkAddressesList networkAddressesObject = new NetworkAddressesList();
		
		try (
				Connection connection = connectionFactory.createConnection();
				Session session = connection.createSession();
				MessageProducer producer = session.createProducer(queue);
		) {
			int networkAddressesListSize = networkAddressesList.size();
			for (int fromIndex = 0; fromIndex < networkAddressesListSize; fromIndex += 10) {
				int toIndex = Math.min(fromIndex + 10, networkAddressesListSize);
				
				List<String> networkAddressesSubList = new ArrayList<String>(networkAddressesList.subList(fromIndex, toIndex));
				networkAddressesObject.setNetworkAddressesList(networkAddressesSubList);
				ObjectMessage objectMessage = session.createObjectMessage();
				objectMessage.setObject(networkAddressesObject);
				producer.send(objectMessage);
			}
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getIpStatus(String ip) {
		System.out.println("Test Bean");
		return dao.getIpStatus(ip);
	}

}
