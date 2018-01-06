package br.inatel.pos.mobile.dm110.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.inatel.pos.mobile.dm110.entities.NetworkAddress;

@Stateless
public class NetworkDAO {
	@PersistenceContext(unitName = "network")
	private EntityManager em;

	public void insert(NetworkAddress networkAddress) {
		em.merge(networkAddress);
	}

	public String getIpStatus(String ip) {

		if ((em.createQuery("from NetworkAddress na where na.ip=:ip", NetworkAddress.class).setParameter("ip", ip).getResultList()).size() == 0){
			return "IP não existe!";
		}

		String result = "IP is active: ";

		if (em.createQuery("from NetworkAddress na where na.ip=:ip", NetworkAddress.class).setParameter("ip", ip).getResultList().get(0).isActive()){
			result += "true"; 						
		}else{
			result += "false";
		}
		System.out.println(result);
		return result;
	}
}
