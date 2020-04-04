package com.techprimers.springbootneo4jexample1.resource;

import com.google.gson.Gson;
import com.techprimers.springbootneo4jexample1.model.DealbucketDet;
import com.techprimers.springbootneo4jexample1.model.Importeddata;
import com.techprimers.springbootneo4jexample1.model.Movie;
import com.techprimers.springbootneo4jexample1.model.Person;
import com.techprimers.springbootneo4jexample1.model.TestDate;
import com.techprimers.springbootneo4jexample1.model.User;
import com.techprimers.springbootneo4jexample1.model.Vrms;
import com.techprimers.springbootneo4jexample1.repository.MovieRepository;
import com.techprimers.springbootneo4jexample1.repository.PersonRepository;
import com.techprimers.springbootneo4jexample1.repository.TestDateRepository;
import com.techprimers.springbootneo4jexample1.repository.UserRepository;
import com.techprimers.springbootneo4jexample1.service.UserService;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

@RestController
@RequestMapping("/import")
public class ImportResource {

	@Autowired
	UserService userService;

	@Autowired
	TestDateRepository testDateRepository;

	@Autowired
	PersonRepository personRepository;

	@SuppressWarnings("deprecation")
	@Autowired
	org.neo4j.ogm.session.SessionFactory sf;

	@GetMapping
	public Vrms getAll() throws ParseException, IOException {

		// reading csv files
		List<Importeddata> idtlist = getCSVtoObjectList();

		// creating initial code set (from column named with code)
		Set<String> codeSet = new LinkedHashSet<String>();
		for (int i = 1; i < idtlist.size(); i++) { // counter starts with 1 because 0 column is header
			codeSet.add(idtlist.get(i).getCode());
		}

		// creating list of DealbucketDet to include in the main NodeEntity to persist in DB
		List<DealbucketDet> listD = createDealbucketDetList(idtlist, codeSet);

		// main NodeEntity to persist in DB

		Vrms vrms = new Vrms();
		vrms.setAgrNo(idtlist.get(1).getAgr_no());
		vrms.setVersion(idtlist.get(1).getVersion_code());
		vrms.setDealbklist(listD);
		return vrms;
	}

	// to create list of DealbucketDet
	private List<DealbucketDet> createDealbucketDetList(List<Importeddata> idtlist, Set<String> codeSet) {
		List<DealbucketDet> listD = new ArrayList<DealbucketDet>();

		for (String cd : codeSet) {
			DealbucketDet dbd = new DealbucketDet();
			dbd.setDealbucket(cd);

			Set<String> assetlistSet = new LinkedHashSet<String>();
			Set<String> hbBucketListSet = new LinkedHashSet<String>();

			for (int i = 1; i < idtlist.size(); i++) {
				if (idtlist.get(i).getCode().equals(cd)) {
					assetlistSet.add(idtlist.get(i).getAsset_id());
					hbBucketListSet.add(idtlist.get(i).getHoldback_code());
				}
			}

			dbd.setAssetList(String.join(", ", assetlistSet));
			dbd.setHbBucketList(String.join(", ", hbBucketListSet));

			listD.add(dbd);
		}
		return listD;
	}

	// to get parse the csv to object list
	private List<Importeddata> getCSVtoObjectList() {

		List<Importeddata> idtlist = new ArrayList<Importeddata>();

		try {
			BufferedReader bReader = new BufferedReader(new FileReader("D:/Book3.csv"));
			while (bReader != null) {
				String read;
				try {
					read = bReader.readLine();
					if (read != null) {
						String[] array = read.split(",+");
						// System.out.println("---"+array[3]);
						idtlist.add(new Importeddata(array[0], array[1], array[2], array[3], array[4]));
					} else {
						break;
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}

		return idtlist;
	}

}
