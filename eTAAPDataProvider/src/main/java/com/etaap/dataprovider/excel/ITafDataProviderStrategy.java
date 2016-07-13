/*
 * 
 */
package com.etaap.dataprovider.excel;

import java.util.Map;


/**
 * The Interface ITafDataProviderStrategy.
 *
 * @param <T> the generic type
 */
public interface ITafDataProviderStrategy<T> {
	
	/**
	 * Read test data.
	 *
	 * @return the map
	 */
	Map<String, T[][]> readTestData();
	
	/**
	 * Read test data.
	 *
	 * @param sheet the sheet
	 * @param id the id
	 * @return the map
	 */
	Map<String, T[][]> readTestData(String sheet,String id);
}
