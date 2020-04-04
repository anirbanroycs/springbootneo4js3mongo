package com.techprimers.springbootneo4jexample1.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.techprimers.springbootneo4jexample1.model.Person;

public class CSVConverterUtil implements Callable<String> {

	private static final String SEPARATOR = ",";
	private static final String lineSeparator = java.security.AccessController
			.doPrivileged(new sun.security.action.GetPropertyAction("line.separator"));

	StringBuffer oneLine = new StringBuffer();
	List<Person> personList = new ArrayList();

	public CSVConverterUtil(List<Person> personList) {
		super();
		this.personList = personList;
	}

	@Override
	public String call() throws Exception {
		try {

			for (Person person : personList) {

				oneLine.append(person.getName());
				oneLine.append(SEPARATOR);
				oneLine.append(person.getAge());
				oneLine.append(SEPARATOR);
				oneLine.append(person.getSalary());
				oneLine.append(SEPARATOR);
				oneLine.append(person.getAddress());
				oneLine.append(lineSeparator);
			}

		} catch (Exception e) {

		}

		return oneLine.toString();
	}

}
