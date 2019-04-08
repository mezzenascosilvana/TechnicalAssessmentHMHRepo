package common.categories;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import common.AbstractTest;
import common.CommonMethods;

public class CategoriesTest extends AbstractTest {

	private static final Logger LOGGER = Logger.getLogger(AbstractTest.class.getName());

	private static CommonMethods commonMethods = new CommonMethods();
	private static final String CATEGORIES_KEY = "categories";
	private static final Integer PARAMETER_NUMBER = 13;

	/***
	 * Verifies the size of the JSON array
	 * 
	 * @throws Exception
	 */

	@Test(enabled = true)
	public void testSizeVerify() throws Exception {

		JSONObject result = getHttpResponse(CATEGORIES_KEY);
		AssertJUnit.assertTrue(result != null);
		LOGGER.log(Level.INFO, "Result: " + result);
		JSONArray categoriesList = result.getJSONArray(CATEGORIES_KEY);
		AssertJUnit.assertTrue(categoriesList.length() == PARAMETER_NUMBER);
		LOGGER.log(Level.INFO, "Size of categories endpoint was checked successfully");
	}

	/***
	 * Verifies the JSON structure, checked all the categories names are displayed.
	 * @throws Exception 
	 */
	@Test(enabled = true)
	public void testVerifyStructure() throws Exception {

		JSONObject result = getHttpResponse(CATEGORIES_KEY);
		AssertJUnit.assertTrue(result != null);
		LOGGER.log(Level.INFO, "Result: " + result);
		JSONArray categoriesList = result.getJSONArray(CATEGORIES_KEY);
		for (int i = 0; i < categoriesList.length(); i++) {
			AssertJUnit.assertTrue(categoriesList.length() >= 0);
			JSONObject oj = categoriesList.getJSONObject(i);
			Iterator<?> keys = oj.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				JSONObject valueList = (JSONObject) oj.get(key);
				Iterator<?> keysOfValueList = valueList.keys();
				// Iterating in the array and getting the value of the keys
				while (keysOfValueList.hasNext()) {
					String keyFromValueList = (String) keysOfValueList.next();
					String valueFromValueList = valueList.get(keyFromValueList).toString();
					LOGGER.log(Level.INFO, "Value: " + valueFromValueList);
					if (keyFromValueList.equals("name")) {
						AssertJUnit.assertTrue(commonMethods.isItExists(valueFromValueList, CATEGORIES_KEY));
						break;
					} else {
						AssertJUnit.assertFalse(commonMethods.isItExists(valueFromValueList, CATEGORIES_KEY));
						LOGGER.log(Level.INFO, "The value doesn't match");
						break;
					}
				}
			}
		}
	}

}
