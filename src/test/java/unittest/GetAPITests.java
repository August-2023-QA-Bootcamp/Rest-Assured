package unittest;

import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dto.Product;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetAPITests {

	//@Test
	public void getTestPrettyprint() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		Response response = RestAssured.given()
				.log().all().
				get("/products");
		response.prettyPrint();
	}
	
	//@Test
	public void getTestInSigleLine() {
		RestAssured.given()
		.baseUri("https://fakestoreapi.com")
		.log().all()
		.get("/products")
		.prettyPrint();
	}
	
	//@Test
	public void getTestInline() {
		RestAssured.given().log().all().get("https://fakestoreapi.com/products")
		.then().log().all();
	}
	
	//@Test
	public void getTestWorkplaceStandard() {
		String baseUrl = "https://fakestoreapi.com";
		String endpoint = "/products";
		
		//Request Obj
		RequestSpecification request = RestAssured.given();
		request.baseUri(baseUrl);
		request.basePath(endpoint);
		request.log().all();
		
		//Response Obj
		Response response = request.get();
		response.then().log().all();
		
		//Validation
		response.then().statusCode(200); //Equals - default behaviour
		response.then().header("Connection", Matchers.equalTo("keep-alive")); //Equals, contains, allofthelist
		
		JsonPath jsonPath = new JsonPath(response.asString());
		
		List<String> titles = jsonPath.getList("..id");
		
		for(String title : titles) {
			System.out.println("---- " + title);
		}
	}
	
	//@Test
	public void getTestJsonPath() {
		String baseUrl = "https://fakestoreapi.com";
		String endpoint = "/products";
		
		//Request Obj
		RequestSpecification request = RestAssured.given();
		request.baseUri(baseUrl);
		request.basePath(endpoint);
		//request.log().all();
		
		//Response Obj
		Response response = request.get();
		response.then().log().all();
		
		//Validation
		response.then().statusCode(200); //Equals - default behaviour
		response.then().header("Connection", Matchers.equalTo("keep-alive")); //Equals, contains, allofthelist
		
		System.out.println(response.getBody().jsonPath().getString("rating[19].count"));
		
		JsonPath jsonPath = new JsonPath(response.asString());
		
		List<String> titles = jsonPath.getList("title");
		
		for(String title : titles) {
			System.out.println("---- " + title);
		}
	}
	
	//@Test
	public void getTestConvertToDTO() {
		String baseUrl = "https://fakestoreapi.com";
		String endpoint = "/products";
		
		//Request Obj
		RequestSpecification request = RestAssured.given();
		request.baseUri(baseUrl);
		request.basePath(endpoint);
		//request.header(null);
		request.log().all();
		
		//Response Obj
		Response response = request.get();
		response.then().log().all();
		
		Product[] products = response.as(Product[].class);
		System.out.println(products[0].getRating().getRate());
	}
	
	@Test
	public void getTestChainingMultipleApis() {
		String baseUrl = "https://fakestoreapi.com";
		String endpoint = "/products"; // Another /products/1 - Path Param
		
		//Request Obj
		RequestSpecification request = RestAssured.given();
		request.baseUri(baseUrl);
		request.basePath(endpoint);
		//request.header(null);
		//request.log().all();
		
		//Response Obj
		Response response = request.get();
		//response.then().log().all();
		
		Product[] products = response.as(Product[].class);
		int id8 = products[7].getId();
		
		System.err.println("---- path param is : "+id8);
		
		RestAssured.reset();
		
		request = RestAssured.given();
		request.baseUri(baseUrl);
		request.basePath(endpoint);
		request.log().all();
		
		response = request.get("/"+id8);
		response.then().log().all();
		
		Product product = response.as(Product.class);
		
		Assertions.assertEquals(8, product.getId());
		
		System.out.println(product.getId() + "\n" + product.getTitle());
	}
}
