package common;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommonMethods {

	private static final Logger LOGGER = Logger.getLogger(AbstractTest.class.getName());

	private static String[] citiesArray = { "Dublin", "Belfast", "BlaBlaDublin" };

	public static String[] categoriesArray = { "Delivery", "Dine-out", "Nightlife", "Catching-up", "Takeaway", "Cafes",
			"Daily Menus", "Breakfast", "Lunch", "Dinner", "Pubs & Bars", "Pocket Friendly Delivery",
			"Clubs & Lounges" };
	public static String[] cusineArray = { "African", "American", "Argentine", "Asian", "Australian", "BBQ", "Bakery",
			"Brazilian", "British", "Burger", "Cafe", "Carvery", "Chinese", "Coffee and Tea", "Crepes", "Czech", "Deli",
			"Desserts", "Drinks Only", "Egyptian", "European", "Fast Food", "Filipino", "Finger Food", "Fish and Chips",
			"French", "Greek", "Grill", "Healthy Food", "Ice Cream", "Indian", "Indonesian", "Iranian", "Irish",
			"Italian", "Japanese", "Juices", "Kebab", "Korean", "Lebanese", "Malaysian", "Mediterranean", "Mexican",
			"Middle Eastern", "Modern European", "Mongolian", "Moroccan", "Nepalese", "Pakistani", "Pizza", "Polish",
			"Portuguese", "Pub Food", "Russian", "Sandwich", "Seafood", "Spanish", "Steak", "Street Food", "Sushi",
			"Tapas", "Tea", "Tex-Mex", "Thai", "Turkish", "Vegetarian", "Vietnamese", "World Cuisine" };
	public static String[] establishmentsArray = { "Food Court", "Quick Bites", "Café", "Casual Dining", "Pub",
			"Bakery", "Bar", "Beer Garden", "Club", "Cocktail Bar", "Dessert Parlour", "Kiosk", "Lounge", "Wine Bar",
			"Fine Dining", "Food Truck", "Juice Bar", "Bistro", "Noodle Shop" };

	
	/***
	 * This method verify if exist each value of an array. It's select
	 * an array by global key
	 * 
	 * @param value     : it is a string that is search
	 * @param globalKey : name of the endpoint key.
	 * @return boolean
	 * @throws IOException
	 */

	public boolean isItExists(String value, String globalkey) throws IOException {
		boolean result = false;

		if ("categories".equalsIgnoreCase(globalkey)) {
			for (String cell : categoriesArray) {
				if (cell.equals(value)) {
					result = true;
					break;
				}
			}
		}
		if ("cuisines".equalsIgnoreCase(globalkey)) {
			for (String cell : cusineArray) {
				if (cell.equals(value)) {
					result = true;
					break;
				}
			}
		}
		if ("establishments".equalsIgnoreCase(globalkey)) {
			for (String cell : establishmentsArray) {
				if (cell.equals(value)) {
					result = true;
					break;
				}
			}
		}
		if (result == true)
			LOGGER.log(Level.INFO, "The values of the array: " + globalkey + " was verified successfully");

		else {
			LOGGER.log(Level.INFO, "The values of the array: " + globalkey + " was not verified successfully");
		}
		return result;
	}

	/***
	 * To get a city of an specific array
	 * 
	 * @param index: position of the city
	 * @return String
	 */

	public String getNameCity(int index) {
		String result = null;
		for (int i = 0; i < citiesArray.length; i++) {
			if (i == index) {
				result = citiesArray[i];
				break;
			}
		}
		return result;
	}

	/***
	 * Verify the value of a JSON key is equals an string. In particular, the value
	 * of two keys is being verified
	 * 
	 * @param       i:
	 * @param value : is the value to be finding
	 * @param       : toCompareString1: is the name of a key
	 * @param       : toComareString2: is the name of a second key
	 * @return
	 */
	public boolean verifyValueKeyIsInParameters(int i, String value, String toCompareString1, int toCompareString2) {
	
		boolean result = false;
		if (value.startsWith(toCompareString1) || value.equals(Integer.toString(toCompareString2))){
			result = true;
		}
		if (result == false)
			LOGGER.log(Level.INFO, "Different key value:  " + value + " It was expected : " + toCompareString2
					+ " Verify position JsonArray :" + i);
		return result;
	}

	/***
	 * Verifies the value of a JSON key is equals an string. In particular, the value
	 * of one key is being verified
	 * 
	 * @param       i: position of the element in JSON structure
	 * @param value : is the value to be finding
	 * @param       : toCompareString1: is the name of a key, for ejemplo cityName
	 * @return
	 */
	public Boolean verifyValueKeyIsInParameters(int i, String value, String toCompareString1) {

		boolean result = false;
		if (value.startsWith(toCompareString1))
			result = true;
		if (result == false)
			LOGGER.log(Level.INFO, "Different name:" + value + "position :" + i );			
		return result;
	}



	/***
	 * Searchs in a text for a particular word 
	 * @param word : word to be searched
	 * @param text: is the URL parameter 
	 * @return
	 */
	public boolean searchAWordInAPhrase(String word, String text) {

		boolean result = false;
		try {
			String[] parts = text.split("&");
			for (String i : parts) {
				if (i.startsWith(word)) {
					result = true;
					break;
				}
			}
		} catch (Exception e) {
			result = false;
		}
		if (result == false)
			LOGGER.log(Level.INFO,"The word:" + word + ", wasn't found");
		return result;
	}

	/***
	 * This method was created to obtain the name of the city that is
	 *  used as a search parameter and is part of the chain of the url of the endpoint.
	 * 
	 * @param parameters : string conformed with the parameters . It is used to make the url
	 * @return
	 */
	public String getCityNameOfParameters(String parameters) {

		String result = null;
		try {
			String[] parts = parameters.split("&");
			result = parts[0];
		} catch (Exception e) {
			result = parameters;
		}
		return result;
	}

	/***
	 *This method verifies that "connection_Id" key is included in the  "shared_url" key.
	 * 
	 * @param j : collection JsonArray to identify the position.
	 * @param valueKey : is the value of the idCollection key.
	 * @param idCollection : is the numberID of the collection key.
	 * @return
	 */
	public boolean verifyIdCollectionInSharedURLKey(int j, String valueKey, String idCollection) {

		boolean result = false;
		try {
			String[] parts = valueKey.split("/");
			for (String i : parts) {
				if (i.equals(idCollection)) {
					System.out.println(idCollection);
					result = true;
					break;
				}
			}
		} catch (Exception e) {

			result = false;
		}
		if (result == false)
			System.out.println("Word:" + idCollection + ", wasn't found");
		return result;

	}

}
