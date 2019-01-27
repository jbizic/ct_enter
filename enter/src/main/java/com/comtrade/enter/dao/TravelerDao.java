/**
 * 
 */
package com.comtrade.enter.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.comtrade.enter.model.Traveler;
import com.comtrade.enter.repository.TravelerRepository;

/**
 * @author Jovan
 *
 */
@Service
public class TravelerDao {

	@Autowired
	TravelerRepository tr;

	@Autowired
	EntityManager em;

	public Traveler save(Traveler traveler) {
		return tr.save(traveler);
	}

	public List<Traveler> allTravelers() {
		return tr.findAll();
	}

	public Optional<Traveler> findOne(int travelerId) {
		return tr.findById(travelerId);
	}

	public void delete(Traveler traveler) {
		tr.delete(traveler);
	}

	public void saveAll(List<Traveler> traveler) {
		tr.saveAll(traveler);
	}

	public List<Traveler> allTravelersSortByColumnName(String sort, String column) {
		return tr.findAll(new Sort(sort.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, column));
	}

	@SuppressWarnings("unchecked")
	public List<Traveler> filter(String country, String fromDate, String toDate, String direction) {
		String query = "select t from Traveler t where t.country = :country and t.period between :fromDate and :toDate";
		if (direction != null)
			query.concat(" and t.direction = :direction");
		Query q = em.createQuery(query);
		q.setParameter("country", country);
		q.setParameter("fromDate", fromDate);
		q.setParameter("toDate", toDate);
		if (direction != null)
			q.setParameter("direction", direction);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Map<Double, String> mapOfTravelersByCountry(int startPosition, int maxResult) {
		Map<Double, String> mapa = new HashMap<>();
		Query q = em.createNativeQuery(
				"select sum(t.count) as sum, t.country as country from Traveler t group by t.country order by sum DESC limit "
						+ maxResult + ";");

		// Pagination
//		q.setFirstResult(startPosition);
//		q.setMaxResults(maxResult);
		List<Object[]> results = q.getResultList();

		results.stream().forEach((record) -> {
			Double sum = (Double) record[0];
			String country = (String) record[1];
			mapa.put(sum, country);
		});
		return mapa;
	}
}
