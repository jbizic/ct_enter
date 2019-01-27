/**
 * 
 */
package com.comtrade.enter.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comtrade.enter.dao.TravelerDao;
import com.comtrade.enter.model.Traveler;
import com.jcabi.aspects.Loggable;

/**
 * @author Jovan
 *
 */
@RestController
public class TravelerController {

	private static Logger LOGGER = LoggerFactory.getLogger(TravelerController.class);

	@Autowired
	TravelerDao td;

	@Loggable(value = Loggable.DEBUG, prepend = true)
	@RequestMapping("/api")
	public String test() {
		LOGGER.info("servis is working!");
		return "Server test";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@CrossOrigin
	@RequestMapping("/api/travelers")
	public List<Traveler> getAllTravelers() {
		LOGGER.info("get all travelers");
		return td.allTravelers();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@CrossOrigin
	@RequestMapping(value = "/api/save", method = { RequestMethod.POST })
	public Traveler createNode(@Valid @RequestBody Traveler traveler) {
		LOGGER.info("save traveler object into db");
		return td.save(traveler);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@CrossOrigin
	@RequestMapping("/api/edit")
	public ResponseEntity<Traveler> updateNode(@Valid @RequestBody Traveler updatedTraveler) {
		LOGGER.info("edit traveler object from db");
		td.save(updatedTraveler);
		return ResponseEntity.ok().body(updatedTraveler);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@CrossOrigin
	@RequestMapping("/api/remove")
	public ResponseEntity<Traveler> deleteNode(@Valid @RequestBody Traveler traveler) {
		LOGGER.info("delete traveler object from db");
		td.delete(traveler);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@CrossOrigin
	@RequestMapping("/api/travelers/sort/{sort}/{column}")
	public List<Traveler> getAllTravelers(@PathVariable("sort") String sort, @PathVariable("column") String column) {
		LOGGER.info("get all travelers by column " + column + " and sort " + sort + "");
		return td.allTravelersSortByColumnName(sort, column);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@CrossOrigin
	@RequestMapping("/api/travelers/filter/{country}/{fromDate}/{toDate}/{direction}")
	public List<Traveler> filterTravelers(@PathVariable("country") String country,
			@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			@PathVariable("direction") String direction) {
		LOGGER.info("get all filtered travelers");
		return td.filter(country, fromDate, toDate, direction);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@CrossOrigin
	@RequestMapping(value="/api/travelers/sum/{offset}/{limit}", method = RequestMethod.GET)
	public Map<Double, String> sumOfTravelers(@PathVariable ("offset") String offset,@PathVariable ("limit") String limit) {
		LOGGER.info("get sum of travelers by country");
		return td.mapOfTravelersByCountry(Integer.parseInt(offset), Integer.parseInt(limit));
	}

	@CrossOrigin
	@RequestMapping("/upload")
	@ResponseBody
	public void handleFileUpload(@RequestParam("file") MultipartFile file)
			throws IOException, ParseException {
		LOGGER.info("inserting statistics into db started...");
		
		
		InputStream inputStream = file.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					
		Traveler traveler;
		List<Traveler> list = new ArrayList<>();
		String line;
		String[] helperLine;
		String day = "-01";
		bufferedReader.readLine();
		while ((line = bufferedReader.readLine()) != null)
		{
			helperLine = line.split(",");
			if(helperLine.length>5) {
				helperLine[3] = helperLine[3]+" "+helperLine[4];
				helperLine[4] = helperLine[5];
			}
			helperLine[0] = helperLine[0].substring(0, 4) + "-" + helperLine[0].substring(4, helperLine[0].length());
			helperLine[0] = helperLine[0].concat(day);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(helperLine[0]);
			traveler = new Traveler();
			
			traveler.setPeriod(date);
			traveler.setPassenger_type(helperLine[1]);
			traveler.setDirection(helperLine[2]);
			traveler.setCountry(helperLine[3]);
			traveler.setCount(helperLine[4]);
			list.add(traveler);
		}
		td.saveAll(list);

		LOGGER.info("inserting statistics into db is finished!");
	}
}
