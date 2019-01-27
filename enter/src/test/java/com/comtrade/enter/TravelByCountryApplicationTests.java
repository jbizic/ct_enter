package com.comtrade.enter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.comtrade.enter.dao.TravelerDao;
import com.comtrade.enter.model.Traveler;
import com.comtrade.enter.repository.TravelerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelByCountryApplicationTests {
	
	public static int id;

	@Test
	public void contextLoads() {
	}
	@Autowired
	EntityManager em;
	
	@Autowired
	TravelerDao td = new TravelerDao();
	
//	@Autowired
//	TravelerRepository tr = new TravelerDao();

	/**
	 * Test method for {@link com.comtrade.enter.dao.TravelerDao#save(com.comtrade.enter.model.Traveler)}.
	 */
	@Test
	public void testSave() {
		Traveler t = new Traveler();
		t.setCount("testCount");
		t.setCountry("testCountry");
		t.setDirection("testDirection");
		t.setPassenger_type("testPassengerType");
		t.setPeriod(new Date());
		
		td.save(t);
		
		id=t.getId();
	    // when
	    Optional<Traveler> found = td.findOne(t.getId());
	 
	    // then
	    assertThat(found.get().getId())
	      .isEqualTo(t.getId());
	}
//
//	/**
//	 * Test method for {@link com.comtrade.enter.dao.TravelerDao#allTravelers()}.
//	 */
	@Test
	public void testAllTravelers() {
		List<Traveler> travelers = new LinkedList<Traveler>();
		travelers = td.allTravelers();
		if(travelers.isEmpty()) {
			assertTrue(travelers.isEmpty());
		}else {
			assertTrue(travelers.size() > 0);
		}
	}

//	/**
//	 * Test method for {@link com.comtrade.enter.dao.TravelerDao#findOne(int)}.
//	 */
	@Test
	public void testFindOne() {

		Traveler traveler = em.find(Traveler.class, id);
	    Optional<Traveler> found = td.findOne(id);
	    
	   assertNotNull(found);
	   assertNotEquals(traveler, found);
	  
	}
//
//	/**
//	 * Test method for {@link com.comtrade.enter.dao.TravelerDao#delete(com.comtrade.enter.model.Traveler)}.
//	 */
	@Test
	public void testDelete() {
		
		Traveler traveler = em.find(Traveler.class, id);
		assertNotNull(traveler);
		td.delete(traveler);
		assertNull(em.find(Traveler.class, id));
	}
}

