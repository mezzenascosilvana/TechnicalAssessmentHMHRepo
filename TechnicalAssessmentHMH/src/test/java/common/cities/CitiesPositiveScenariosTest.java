package common.cities;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.AbstractTest;
import common.CommonMethods;

public class CitiesPositiveScenariosTest extends AbstractTest {

	private static CommonMethods commonMethods = new CommonMethods();
	private static final Logger LOGGER = Logger.getLogger(AbstractTest.class.getName());
	private static final String CITIES_KEY = "cities";
	private static final String LOCATION_SUGGESTIONS_KEY = "location_suggestions";
	private static final String STATUS_KEY = "status";
	private static final String STATUS_KEY_VALUE = "success";

	private static final String CONNECTOR_EQUAL = "=";
	private static final String CONNECTOR_AND = "&";
	private static final String PARAMETER_COUNT = "count";
	private static final Integer PARAMETER_COUNT_NUMBER = 2;
	private static final Integer PARAMETER_CITY_ID_NUMBER = 113;
	private static final String PARAMETER_CITY_ID = "city_ids";
	

	/***
	 * `Verify the status is success when results are displayed For this test
	 * the @DataProvider method was used. Receives parameter from @DataProvider
	 * method:
	 * 
	 * @param parameters :is a combination of different parameters
	 * @throws Exception 
	 */
	@Test(enabled = true, dataProvider = "getData")
	public void testSuccessStatusVerify(String parameters) throws Exception {

		String newRequestUrl = CITIES_KEY + "?q=" + parameters;
		JSONObject result = getHttpResponse(newRequestUrl);
		LOGGER.log(Level.INFO, "Result: " + result);
		AssertJUnit.assertTrue(result.getString(STATUS_KEY).equals(STATUS_KEY_VALUE));
		LOGGER.log(Level.INFO, "Status key value was verified successfully");
	}

	/**
	 * Verifies that the result displayed matches with the count value key. For this
	 * test the @DataProvider method was used. Receives parameter from @DataProvider
	 * method:
	 * 
	 * @param parameters is a combination of different parameters
	 * @throws Exception 
	 */
	@Test(enabled = true, dataProvider = "getData")
	public void testVerifyResultSizeWhenSetUpQAndCountParameters(String parameters) throws Exception {

		String newRequestUrl = CITIES_KEY + "?q=" + parameters;
		JSONObject result = getHttpResponse(newRequestUrl);
		LOGGER.log(Level.INFO, "Result: " + result);
		JSONArray localSuggestionList = result.getJSONArray(LOCATION_SUGGESTIONS_KEY);
		if (commonMethods.searchAWordInAPhrase(PARAMETER_COUNT, parameters)) {
			AssertJUnit.assertTrue(localSuggestionList.length() == PARAMETER_COUNT_NUMBER);
		} else {
			AssertJUnit.assertTrue(commonMethods.searchAWordInAPhrase(PARAMETER_COUNT, parameters));
			LOGGER.log(Level.INFO, "Endpoint URL does not have countKey applied");
		}
	}

	/**
	 * Verifies that the result displayed matches with the state_id and name value keys. For
	 * this test the @DataProvider method was used. Receives parameter
	 * from @DataProvider method:
	 * 
	 * @param parameters is a combination of different parameters
	 * @throws Exception 
	 */
	@Test(enabled = true, dataProvider = "getData")
	public void testShowsResulsWhenSetUpQAndCityIDParameters(String parameters) throws Exception {

		String nameCity = commonMethods.getCityNameOfParameters(parameters);
		String newRequestUrl = CITIES_KEY + "?q=" + parameters;
		JSONObject result = getHttpResponse(newRequestUrl);
		AssertJUnit.assertTrue(result != null);
		LOGGER.log(Level.INFO, "Result: " + result);
		JSONArray localSuggestionList = result.getJSONArray(LOCATION_SUGGESTIONS_KEY);
		if (commonMethods.searchAWordInAPhrase(PARAMETER_CITY_ID, parameters)) {
			AssertJUnit.assertTrue(localSuggestionList.length() >= 0);
			for (int i = 0; i < localSuggestionList.length(); i++) {
				JSONObject oj = localSuggestionList.getJSONObject(i);
				Iterator<?> keys = oj.keys();
				while (keys.hasNext()) {
					String key = (String) keys.next();
					String valueKey = oj.get(key).toString();
					if (key.equals("name") || key.equals("state_id"))
						AssertJUnit.assertTrue(commonMethods.verifyValueKeyIsInParameters(i, valueKey, nameCity,
								PARAMETER_CITY_ID_NUMBER));
				}
			}
		} else {
			AssertJUnit.assertTrue(commonMethods.searchAWordInAPhrase(PARAMETER_CITY_ID, parameters));
			LOGGER.log(Level.INFO, "Endpoint URL does not have cityID applied");
		}
	}

	/**
	 * Verifies if the Q value key is in of the parameters. For this test
	 * the @DataProvider method was used. Receives parameter from @DataProvider
	 * method:
	 * 
	 * @param parameters: is a combination of different parameters
	 * @throws Exception 
	 */
	@Test(enabled = false, dataProvider = "getData")
	public void testIsQParameterWorkingFine(String parameters) throws Exception {

		String cityName = commonMethods.getCityNameOfParameters(parameters);
		String newRequestUrl = CITIES_KEY + "?q=" + parameters;
		LOGGER.log(Level.INFO, "Endpoint :" + newRequestUrl);
		JSONObject result = getHttpResponse(newRequestUrl);
		LOGGER.log(Level.INFO, "Result: " + result);
		AssertJUnit.assertTrue(result != null);
		JSONArray localSuggestionList = result.getJSONArray(LOCATION_SUGGESTIONS_KEY);
		for (int i = 0; i < localSuggestionList.length(); i++) {
			AssertJUnit.assertTrue(localSuggestionList.length() >= 0);
			JSONObject oj = localSuggestionList.getJSONObject(i);
			Iterator<?> keys = oj.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				String valueKey = oj.get(key).toString();
				if (key.equals("name")) {
					AssertJUnit.assertTrue(commonMethods.verifyValueKeyIsInParameters(i, valueKey, cityName));
					break;
				} else {
					AssertJUnit.assertFalse(commonMethods.verifyValueKeyIsInParameters(i, valueKey, cityName));
					LOGGER.log(Level.INFO, "City name doesn't match with a key value or it is empty");
				}
			}
		}
	}

	/***
	 * This method is used to test different combinations of data. object has two
	 * dimension [row][column]: [6]= number of the combinations [1] = number of
	 * parameters
	 */
	@DataProvider
	public Object[][] getData() {

		Object[][] data = new Object[7][1];
		data[0][0] = commonMethods.getNameCity(0);
		data[1][0] = commonMethods.getNameCity(1);
		data[2][0] = commonMethods.getNameCity(0) + CONNECTOR_AND + PARAMETER_CITY_ID + CONNECTOR_EQUAL
				+ PARAMETER_CITY_ID_NUMBER;
		data[3][0] = commonMethods.getNameCity(1) + CONNECTOR_AND + PARAMETER_CITY_ID + CONNECTOR_EQUAL
				+ PARAMETER_CITY_ID_NUMBER;
		data[4][0] = commonMethods.getNameCity(0) + CONNECTOR_AND + PARAMETER_COUNT + CONNECTOR_EQUAL
				+ PARAMETER_COUNT_NUMBER;
		data[5][0] = commonMethods.getNameCity(1) + CONNECTOR_AND + PARAMETER_COUNT + CONNECTOR_EQUAL
				+ PARAMETER_COUNT_NUMBER;
		data[6][0] = commonMethods.getNameCity(0) + CONNECTOR_AND + PARAMETER_COUNT + CONNECTOR_EQUAL
				+ PARAMETER_COUNT_NUMBER;
		return data;
	}
}
