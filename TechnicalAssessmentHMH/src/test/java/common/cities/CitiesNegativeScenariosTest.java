package common.cities;

import java.util.logging.Level;
import java.util.logging.Logger;

import common.AbstractTest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CitiesNegativeScenariosTest extends AbstractTest {

    private static final Logger LOGGER = Logger.getLogger(CitiesNegativeScenariosTest.class.getName());
    private static final String CITIES_KEY = "cities";
    private static final String PARAMETER_COUNT = "count";
    private static final String PARAMETER_COUNT_NUMBER = "2";
    private static final String LOCATION_SUGGESTIONS_KEY = "location_suggestions";
    private static final Integer LOCATION_SUGGESTIONS_KEY_NUMBER = 0;
    private static final String STATUS_KEY = "status";
    private static final String STATUS_KEY_VALUE = "success";

    /***
     * Verifies the status value key even when results are not displayed.
     *  For this test the @DataProvider method was used.
     * Receives parameter from @DataProvider method:
     * @param parameters is a combination of different parameters
     * @throws Exception
     */

    @Test(dataProvider = "getData")
    public void testStatusSuccessVerify(String parameters) throws Exception {
    	
        JSONObject result = getHttpResponse(CITIES_KEY + parameters);
        AssertJUnit.assertTrue(result != null);
        LOGGER.log(Level.INFO, "Result: "+ result);
        AssertJUnit.assertTrue(result.getString(STATUS_KEY).equals(STATUS_KEY_VALUE));
        LOGGER.log(Level.INFO, "Status value key  was verified successfully");
    }


    /***
     * Verifies Location Suggestions value key, when results are not shown.
     * For this test the @DataProvider method was used.
     * Receives parameter from @DataProvider method:
     * @param parameters is a combination of different parameters.
     * @throws Exception
     */
    @Test(dataProvider = "getData")
    public void testIsLocationSuggestionsEmpty(String parameters) throws Exception {
    	
        JSONObject result = getHttpResponse(CITIES_KEY + parameters);
        AssertJUnit.assertTrue(result != null);
        LOGGER.log(Level.INFO, "Result: "+ result);
        JSONArray localSuggestionList = result.getJSONArray(LOCATION_SUGGESTIONS_KEY);
        AssertJUnit.assertTrue(localSuggestionList.length() == LOCATION_SUGGESTIONS_KEY_NUMBER);
        LOGGER.log(Level.INFO, "Local suggestions value key  was verified successfully");
    }

    /***
     * This method is used to test different combinations of data.
     * object has two dimension [row][column]:
     * 	[2]= number of the combinations
     *  [1] = number of parameters
     */
    @DataProvider
    public Object[][] getData() {

        Object[][] data = new Object[2][1];
        data[0][0] = "?q=Argentina";
        data[1][0] = "?" + PARAMETER_COUNT + "=" + PARAMETER_COUNT_NUMBER;

        return data;
    }


}
