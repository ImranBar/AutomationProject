package tests.basic;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import tests.supers.TestBaseApi;

public class ApiTest extends TestBaseApi {

	private static String FIRST_NAME = "Tracey";
	private static String LAST_NAME = "Ramos";
	private static String URL = "https://reqres.in/api/users";

	@Test
	public void test() throws JSONException {

		try {

			Response resp = RestAssured.given().get(URL);
			JSONObject jsonResponse = new JSONObject(resp.asString());

			//-------------------------------GET Request-------------------------------------------------------------
			log.info("Verify that there are total of 12 users.");
			verifyUserTotal(jsonResponse);// Q5 - l

			log.info("Verify that the user “Tracey Ramos” is in the list.");
			verifyUserName(jsonResponse);// Q5 - n

			log.info("Verify that the entire object returns the correct information regarding this user.");
			verifySingleUser(jsonResponse);

			//-------------------------------POST Request-------------------------------------------------------------
			String userId = postNewUsersAndVerify(jsonResponse);

			//-------------------------------PUT Request-------------------------------------------------------------
			updateExistingUserAndVerify(userId);

			//-------------------------------DELETE Request-------------------------------------------------------------
			deleteUser(userId);

			endTestSuccess();
		} catch (Throwable t) {
			onTestFailure(t);
		}
	}

	private void deleteUser(String ID) {
		org.json.simple.JSONObject request = new org.json.simple.JSONObject();
		Response res = given().body(request.toJSONString()).when().delete("https://reqres.in/api/users/" + ID).then().statusCode(204).log().all()
				.extract().response();
		Assert.assertEquals(204, res.statusCode());
	}

	@SuppressWarnings("unchecked")
	private void updateExistingUserAndVerify(String ID) {
		org.json.simple.JSONObject request = new org.json.simple.JSONObject();
		request.put("name", "imran update");
		request.put("job", "QA update");

		Response res = given().contentType(ContentType.JSON).body(request.toJSONString()).when().put("https://reqres.in/api/users/" + ID).then()
				.statusCode(200).extract().response();

		Assert.assertEquals(res.jsonPath().getString("name"), "imran update");
		Assert.assertEquals(res.jsonPath().getString("job"), "QA update");
		Assert.assertEquals(200, res.statusCode());
	}

	@SuppressWarnings("unchecked")
	private String postNewUsersAndVerify(JSONObject jsonResponse) {
		org.json.simple.JSONObject request = new org.json.simple.JSONObject();
		request.put("name", "imran");
		request.put("job", "QA");
		Response res = given().contentType(ContentType.JSON).body(request.toJSONString()).when().post(URL).then().statusCode(201).extract()
				.response();

		Assert.assertEquals(res.jsonPath().getString("name"), "imran");
		Assert.assertEquals(res.jsonPath().getString("job"), "QA");
		Assert.assertEquals(201, res.statusCode());

		return res.jsonPath().getString("id");
	}

	private void verifySingleUser(JSONObject jsonResponse) {
		Response user = get(URL + "?id=6");
		JSONObject userJson = new JSONObject(user.asString()).getJSONObject("data");
		JSONArray data = jsonResponse.getJSONArray("data");
		Assert.assertEquals(userJson.getString("first_name"), data.getJSONObject(5).getString("first_name"));
		Assert.assertEquals(userJson.getString("last_name"), data.getJSONObject(5).getString("last_name"));
		Assert.assertEquals(userJson.getString("email"), data.getJSONObject(5).getString("email"));
		Assert.assertEquals(userJson.getString("avatar"), data.getJSONObject(5).getString("avatar"));
	}

	private void verifyUserName(JSONObject jsonResponse) {
		JSONArray data = jsonResponse.getJSONArray("data");
		String firstName, lastName;
		boolean isExist = false;
		for (int i = 0; i < data.length(); i++) {
			firstName = data.getJSONObject(i).getString("first_name");
			lastName = data.getJSONObject(i).getString("last_name");
			if (firstName.equals(FIRST_NAME) && lastName.equals(LAST_NAME)) {
				isExist = true;
			}
		}
		Assert.assertEquals(isExist, true);
	}

	private void verifyUserTotal(JSONObject jsonResponse) {
		int userTotal = jsonResponse.getInt("total");
		Assert.assertEquals(userTotal, 12);
	}

}
