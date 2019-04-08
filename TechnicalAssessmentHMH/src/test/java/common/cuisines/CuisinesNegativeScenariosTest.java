package common.cuisines;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.AbstractTest;
import common.cities.CitiesNegativeScenariosTest;

public class CuisinesNegativeScenariosTest extends AbstractTest {

	private static final Logger LOGGER = Logger.getLogger(CitiesNegativeScenariosTest.class.getName());
	private static final String CUISINES = "cuisines";
	private static final String CONNECTOR_EQUAL = "=";
	private static final String CONNECTOR_QUESTION = "?";
	private static final String PARAMETER_COUNT = "count";
	private static final String PARAMETER_COUNT_NUMBER = "2";
	private static final String STATUS_KEY = "status";
	private static final String MESSAGE_KEY = "message";

	/** Dublin Lat and Lon **/

	private static final String PARAMETER_LAT = "lat";
	private static final String PARAMETER_LAT_NUMBER = "53.3434";

	/***
	 * `Verifies the status value key; even when results are not displayed. For this
	 * test the @DataProvider method was used. Receives parameter from @DataProvider
	 * method:
	 * 
	 * @param parameters is a combination the different parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getData")
	public void testStatusVerify(String parameters) throws Exception {

		String newRequestUrl = CUISINES + parameters;
		JSONObject result = getHttpResponse(newRequestUrl);
		AssertJUnit.assertTrue(result != null);
		LOGGER.log(Level.INFO, "Result: " + result);
		AssertJUnit.assertTrue(result.getString(STATUS_KEY).equals("Bad Request"));
		LOGGER.log(Level.INFO, "Status value key was verified successfully");
	}

	/***
	 * Verifies the message value key. For this test the @DataProvider method was
	 * used. Receives parameter from @DataProvider method:
	 * 
	 * @param parameters is a combination of different parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getData")
	public void testMessageVerify(String parameters) throws Exception {

		String newRequestUrl = CUISINES + parameters;
		JSONObject result = getHttpResponse(newRequestUrl);
		AssertJUnit.assertTrue(result != null);
		LOGGER.log(Level.INFO, "Result: " + result);
		AssertJUnit.assertTrue(result.getString(MESSAGE_KEY).equals("Invalid or missing parameter \\\"city_id\\\""));
		LOGGER.log(Level.INFO, "Message value key was verified successfully");
	}

	/***
	 * This method is used to test different combinations of data. object has two
	 * dimension [row][column]: [2]= number of the combinations [1] = number of
	 * parameters
	 */
	@DataProvider
	public Object[][] getData() {

		Object[][] data = new Object[2][1];
		data[0][0] = CONNECTOR_QUESTION + PARAMETER_COUNT + CONNECTOR_EQUAL + PARAMETER_COUNT_NUMBER;
		data[1][0] = CONNECTOR_QUESTION + PARAMETER_LAT + CONNECTOR_EQUAL + PARAMETER_LAT_NUMBER;
		return data;
	}

}
