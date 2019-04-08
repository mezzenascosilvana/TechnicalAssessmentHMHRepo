package common.collections;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.AbstractTest;
import common.CommonMethods;
import common.cities.CitiesNegativeScenariosTest;

public class CollectionsPositiveScenariosTest extends AbstractTest {

	private static final Logger LOGGER = Logger.getLogger(CitiesNegativeScenariosTest.class.getName());
	private static CommonMethods commonMethods = new CommonMethods();

	private static final String COLLECTIONS_KEY = "collections";
	private static final String PARAMETER_SHARED_URL_KEY = "share_url";
	private static final String COLLECTION_ID_KEY = "collection_id";

	private static final String CONNECTOR_EQUAL = "=";
	private static final String CONNECTOR_AND = "&";
	private static final String CONNECTOR_QUESTION = "?";
	private static final String PARAMETER_COUNT = "count";
	private static final Integer PARAMETER_COUNT_NUMBER = 2;
	private static final Integer PARAMETER_CITY_ID_NUMBER = 91;
	private static final String PARAMETER_CITY_ID = "city_id";
	/** Dublin Lat and Lon **/
	private static final String PARAMETER_LAT = "lat";
	private static final String PARAMETER_LON = "lon";
	private static final String PARAMETER_LAT_NUMBER = "53.3434";
	private static final String PARAMETER_LON_NUMBER = "-6.2334755436";

	/***
	 * Verifies "shared_url" value key. For this test the @DataProvider method was
	 * used. Receives parameter from @DataProvider method:
	 * 
	 * @param parameters is a combination the different parameters
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "getData")
	public void testSharedURLKeyVerify(String parameters) throws Exception {

		String newRequestUrl = COLLECTIONS_KEY + parameters;
		JSONObject result = getHttpResponse(newRequestUrl);
		LOGGER.log(Level.INFO, "Result: " + result);
		AssertJUnit.assertTrue(result != null);
		AssertJUnit.assertTrue(
				result.getString(PARAMETER_SHARED_URL_KEY).equals("http://www.zoma.to/c-" + PARAMETER_CITY_ID_NUMBER));
		LOGGER.log(Level.INFO, "'Shared url' value key was verified successfully");
	}

	/***
	 * Verifies that the result displayed matches with the count value key. For this
	 * test the @DataProvider method was used. Receives parameter from @DataProvider
	 * method:
	 * 
	 * @param parameters is a combination of different parameters
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "getData")
	public void testVerifyResultSizeWhenSetUpCityIDAndCountParameters(String parameters) throws Exception {

		JSONArray collectionJsonArray = null;
		String newRequestUrl = COLLECTIONS_KEY + parameters;
		if (commonMethods.searchAWordInAPhrase(PARAMETER_COUNT, parameters)) {
			JSONObject result = getHttpResponse(newRequestUrl);
			LOGGER.log(Level.INFO, "Result: " + result);
			AssertJUnit.assertTrue(result != null);
			Iterator<?> keysJSONObjectResult = result.keys();
			collectionJsonArray = iterator(keysJSONObjectResult, result);
			int size = collectionJsonArray.length();
			AssertJUnit.assertTrue(size == PARAMETER_COUNT_NUMBER);

		} else {
			AssertJUnit.assertTrue(commonMethods.searchAWordInAPhrase(PARAMETER_COUNT, parameters));
			LOGGER.log(Level.INFO, "Endpoint URL does not have countKey applied");
		}
	}

	/***
	 * Verifies that the result displayed matches with the 'lat' or 'lon' value
	 * keys. For this test the @DataProvider method was used. Receives parameter
	 * from @DataProvider method:
	 * 
	 * @param parameters is a combination of different parameters
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "getData")
	public void testShowsResulWhenSetUpLatAndLonParameters(String parameters) throws Exception {

		JSONArray collectionJsonArray = null;
		String newRequestUrl = COLLECTIONS_KEY + parameters;
		if (commonMethods.searchAWordInAPhrase(PARAMETER_LAT, parameters)
				|| commonMethods.searchAWordInAPhrase(PARAMETER_LON, parameters)) {
			JSONObject result = getHttpResponse(newRequestUrl);
			LOGGER.log(Level.INFO, "Result: " + result);
			AssertJUnit.assertTrue(result != null);
			Iterator<?> keysJSONObjectResult = result.keys();
			collectionJsonArray = iterator(keysJSONObjectResult, result);
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
						LOGGER.log(Level.INFO, "Value: " + valueFromLastLevel);
						break;
					}
				}
			} // end for

		} else {
			AssertJUnit.assertFalse(commonMethods.searchAWordInAPhrase(PARAMETER_LAT, parameters)
					|| commonMethods.searchAWordInAPhrase(PARAMETER_LON, parameters));
			LOGGER.log(Level.INFO, "Endpoint URL does not have Lat or Lon keys applied");
		}
	}

	/***
	 * Verifies if the idCollection value key is in sharedURLKey. For this test
	 * the @DataProvider method was used. Receives parameter from @DataProvider
	 * method:
	 * 
	 * @param parameters is a combination of different parameters.
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "getData")
	public void testIsCityIDParameterWorkingFine(String parameters) throws Exception {

		String idCollection = null;
		JSONArray collectionJsonArray = null;
		String newRequestUrl = COLLECTIONS_KEY + parameters;
		JSONObject result = getHttpResponse(newRequestUrl);
		AssertJUnit.assertTrue(result != null);
		LOGGER.log(Level.INFO, "Result: " + result);
		Iterator<?> keysJSONObjectResult = result.keys();
		collectionJsonArray = iterator(keysJSONObjectResult, result);
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
					String valueFromLastLevel = valueListCollectionJsonArray.get(keyFromLastLevel).toString();
					if (keyFromLastLevel.equals(COLLECTION_ID_KEY))
						idCollection = valueFromLastLevel;
					if (keyFromLastLevel.equals(PARAMETER_SHARED_URL_KEY)) {
						AssertJUnit.assertTrue(
								commonMethods.verifyIdCollectionInSharedURLKey(i, valueFromLastLevel, idCollection));
						LOGGER.log(Level.INFO, "Key:" + keyFromLastLevel + "   ValueKey:" + valueFromLastLevel + " "
								+ COLLECTION_ID_KEY + ":" + idCollection + " OK..");
						break;
					}
				}
			}
		} // end for

	}

	/***
	 * This method was created to simplify the iterations of a JSONObject.
	 * 
	 * @param keysJSONObjectResult : is the iterator variable
	 * @param result               : is an array with JSONObjects
	 * @return
	 * @throws JSONException
	 */
	private JSONArray iterator(Iterator<?> keysJSONObjectResult, JSONObject result) throws JSONException {

		JSONArray collectionJsonArray = null;
		while (keysJSONObjectResult.hasNext()) {
			String keyJSONObjectResult = (String) keysJSONObjectResult.next();
			if (keyJSONObjectResult.equals(COLLECTIONS_KEY)) {
				collectionJsonArray = (JSONArray) result.get(keyJSONObjectResult);
				break;
			}
		}

		return collectionJsonArray;
	}

	/***
	 * This method is used to test different combinations of data. object has two
	 * dimension [row][column]: [6]= number of the combinations [1] = number of
	 * parameters
	 */
	@DataProvider
	public Object[][] getData() {

		Object[][] data = new Object[5][1];
		data[0][0] = CONNECTOR_QUESTION + PARAMETER_CITY_ID + CONNECTOR_EQUAL + PARAMETER_CITY_ID_NUMBER;
		data[1][0] = CONNECTOR_QUESTION + PARAMETER_CITY_ID + CONNECTOR_EQUAL + PARAMETER_CITY_ID_NUMBER + CONNECTOR_AND
				+ PARAMETER_COUNT + CONNECTOR_EQUAL + PARAMETER_COUNT_NUMBER;
		data[2][0] = CONNECTOR_QUESTION + PARAMETER_CITY_ID + CONNECTOR_EQUAL + PARAMETER_CITY_ID_NUMBER + CONNECTOR_AND
				+ PARAMETER_LAT + CONNECTOR_EQUAL + PARAMETER_LAT_NUMBER + PARAMETER_LON + CONNECTOR_EQUAL
				+ PARAMETER_LON_NUMBER;
		data[3][0] = CONNECTOR_QUESTION + PARAMETER_CITY_ID + CONNECTOR_EQUAL + PARAMETER_CITY_ID_NUMBER + CONNECTOR_AND
				+ PARAMETER_LAT + CONNECTOR_EQUAL + PARAMETER_LAT_NUMBER;
		data[4][0] = CONNECTOR_QUESTION + PARAMETER_CITY_ID + CONNECTOR_EQUAL + PARAMETER_CITY_ID_NUMBER + CONNECTOR_AND
				+ PARAMETER_LON + CONNECTOR_EQUAL + PARAMETER_LON_NUMBER;
		return data;
	}
}
