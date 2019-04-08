package common.cuisines;

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
import common.cities.CitiesNegativeScenariosTest;

public class CuisinesPositiveScenariosTest extends AbstractTest {

	private static CommonMethods commonMethods = new CommonMethods();
	private static final Logger LOGGER = Logger.getLogger(CitiesNegativeScenariosTest.class.getName());
	private static final String CUISINES = "cuisines";
	private static final String CONNECTOR_EQUAL = "=";
	private static final String CONNECTOR_AND = "&";
	private static final String CONNECTOR_QUESTION = "?";
	private static final Integer PARAMETER_CITY_ID_NUMBER = 91;
	private static final String PARAMETER_CITY_ID = "city_id";
	/** Dublin Lat and Lon **/
	private static final String PARAMETER_LAT = "lat";
	private static final String PARAMETER_LON = "lon";
	private static final String PARAMETER_LAT_NUMBER = "53.3434";
	private static final String PARAMETER_LON_NUMBER = "-6.2334755436";

	/***
	 * Verifies the keys aren't null or has a value, when cityId key is applied. For
	 * test the @DataProvider method was used. Receives parameter from @DataProvider
	 * method:
	 * 
	 * @param parameters is a combination of different parameters
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "getData") // ok
	public void testShowsResultWhenSetUpCityIDParameter(String parameters) throws Exception {

		String newRequestUrl = CUISINES + parameters;
		JSONObject result = getHttpResponse(newRequestUrl);
		AssertJUnit.assertTrue(result != null);
		LOGGER.log(Level.INFO, "Result: " + result);
		JSONArray collectionJsonArray = result.getJSONArray(CUISINES);
		AssertJUnit.assertTrue(collectionJsonArray.length() >= 0);
		for (int i = 0; i < collectionJsonArray.length(); i++) {
			JSONObject oj = collectionJsonArray.getJSONObject(i);
			Iterator<?> keysCollectionJsonArray = oj.keys();
			while (keysCollectionJsonArray.hasNext()) {
				String keyCollectionJsonArray = (String) keysCollectionJsonArray.next();
				JSONObject valueListCollectionJsonArray = (JSONObject) oj.get(keyCollectionJsonArray);
				Iterator<?> keysLastLevel = valueListCollectionJsonArray.keys();
				while (keysLastLevel.hasNext()) {
					String keyFromLastLevel = (String) keysLastLevel.next();
					LOGGER.log(Level.INFO, "Sub_Key: " + keyFromLastLevel + " ");
					String valueFromLastLevel = valueListCollectionJsonArray.get(keyFromLastLevel).toString();
					AssertJUnit.assertTrue(valueFromLastLevel != null);
					LOGGER.log(Level.INFO, "Value: " + valueFromLastLevel);
					break;
				}
			}
		}
	}

	/***
	 * Verifies the JSON structure, checking all the cusine names are displayed. For
	 * this test the @DataProvider method was used. Receives parameter
	 * from @DataProvider method:
	 * 
	 * @param parameters is a combination of different parameters
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "getData")
	public void verifyStructure(String parameters) throws Exception {

		String newRequestUrl = CUISINES + parameters;
		JSONObject result = getHttpResponse(newRequestUrl);
		AssertJUnit.assertTrue(result != null);
		LOGGER.log(Level.INFO, "Result: " + result);
		JSONArray cuisinesList = result.getJSONArray(CUISINES);
		for (int i = 0; i < cuisinesList.length(); i++) {
			JSONObject oj = cuisinesList.getJSONObject(i);
			Iterator<?> keys = oj.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				JSONObject valueList = (JSONObject) oj.get(key);
				Iterator<?> keysOfValueList = valueList.keys();
				while (keysOfValueList.hasNext()) {
					String keyFromValueList = (String) keysOfValueList.next();
					LOGGER.log(Level.INFO, "Sub_Key: " + keyFromValueList + " ");
					String valueFromValueList = valueList.get(keyFromValueList).toString();
					LOGGER.log(Level.INFO, "Value: " + valueFromValueList);
					if (keyFromValueList.equals("cuisine_name")) {
						AssertJUnit.assertTrue(commonMethods.isItExists(valueFromValueList, CUISINES));
					} else {
						AssertJUnit.assertFalse(commonMethods.isItExists(valueFromValueList, CUISINES));
						LOGGER.log(Level.INFO, "The value doesn't match");
					}
				}
			}
		} // end for

	}

	/***
	 * 
	 * For this test the @DataProvider method was used. Receives parameter
	 * from @DataProvider method:
	 * 
	 * @param parameters is a combination of different parameters
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "getData")
	public void showsResulWhenSetUpLatAndLonParameters(String parameters) throws Exception {

		String newRequestUrl = CUISINES + parameters;
		if (commonMethods.searchAWordInAPhrase(PARAMETER_LAT, parameters)
				|| commonMethods.searchAWordInAPhrase(PARAMETER_LON, parameters)) {
			JSONObject result = getHttpResponse(newRequestUrl);
			AssertJUnit.assertTrue(result != null);
			LOGGER.log(Level.INFO, "Result: " + result);
			JSONArray cuisinesList = result.getJSONArray(CUISINES);
			AssertJUnit.assertTrue(cuisinesList.length() >= 0);
			for (int i = 0; i < cuisinesList.length(); i++) {
				JSONObject oj = cuisinesList.getJSONObject(i);
				Iterator<?> keys = oj.keys();
				while (keys.hasNext()) {
					String key = (String) keys.next();
					JSONObject valueList = (JSONObject) oj.get(key);
					Iterator<?> keysOfValueList = valueList.keys();
					while (keysOfValueList.hasNext()) {
						String keyFromValueList = (String) keysOfValueList.next();
						LOGGER.log(Level.INFO, "Sub_Key: " + keyFromValueList + " ");
						String valueFromValueList = valueList.get(keyFromValueList).toString();
						LOGGER.log(Level.INFO, "Value: " + valueFromValueList);
						AssertJUnit.assertTrue(valueFromValueList != null);
					}
				}
			} // end for

		}
	}

	/***
	 * This method is used to test different combinations of data. object has two
	 * dimension [row][column]: [4]= number of the combinations [1] = number of
	 * parameters
	 */
	@DataProvider
	public Object[][] getData() {

		Object[][] data = new Object[4][1];
		data[0][0] = CONNECTOR_QUESTION + PARAMETER_CITY_ID + CONNECTOR_EQUAL + PARAMETER_CITY_ID_NUMBER;
		data[1][0] = CONNECTOR_QUESTION + PARAMETER_CITY_ID + CONNECTOR_EQUAL + PARAMETER_CITY_ID_NUMBER + CONNECTOR_AND
				+ PARAMETER_LAT + CONNECTOR_EQUAL + PARAMETER_LAT_NUMBER + PARAMETER_LON + CONNECTOR_EQUAL
				+ PARAMETER_LON_NUMBER;
		data[2][0] = CONNECTOR_QUESTION + PARAMETER_CITY_ID + CONNECTOR_EQUAL + PARAMETER_CITY_ID_NUMBER + CONNECTOR_AND
				+ PARAMETER_LAT + CONNECTOR_EQUAL + PARAMETER_LAT_NUMBER;
		data[3][0] = CONNECTOR_QUESTION + PARAMETER_CITY_ID + CONNECTOR_EQUAL + PARAMETER_CITY_ID_NUMBER + CONNECTOR_AND
				+ PARAMETER_LON + CONNECTOR_EQUAL + PARAMETER_LON_NUMBER;
		return data;
	}
}
