/**
 * 
 */
package com.comtrade.enter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.comtrade.enter.model.Traveler;

/**
 * @author Jovan
 *
 */
public interface TravelerRepository extends JpaRepository<Traveler, Integer> {

}
