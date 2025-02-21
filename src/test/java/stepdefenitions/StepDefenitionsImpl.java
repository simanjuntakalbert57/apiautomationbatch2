package stepdefenitions;

import java.util.Map;

import org.testng.Assert;

import com.apiautomation.model.ResponseItem;
import com.apiautomation.model.request.RequestItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import apiengine.Assertion;
import apiengine.Endpoints;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.DataRequest;

public class StepDefenitionsImpl {
    /*
     *  Given A list of products are available
        When I add new products to etalase
        Then The product is available
     */
    ResponseItem responseItem;
    RequestItem requestItem;
    DataRequest dataRequest;
    String json;
    int idProduct;
    Endpoints endpoints;
    Response response;
    Assertion assertion;

    @BeforeStep
    public void setUp(){
        endpoints = new Endpoints();
        assertion = new Assertion();
    }

    @Given("A list of products are available")
    public void getAllProducts(){
        //Implementation
        response = endpoints.getAllProducts("products");
        System.out.println("reponse migration" + response.asPrettyString());

    }

    @When("I add new products to etalase")
    public void addNewProduct(){
         //Implementation
         System.out.println("Add new product");
         String json = "{\n" + //
                          "  \"id\": 1,\n" + //
                          "  \"title\": \"Le minerale\",\n" + //
                          "  \"description\": \"The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula.\",\n" + //
                          "  \"category\": \"food\",\n" + //
                          "  \"price\": 10000,\n" + //
                          "  \"discountPercentage\": 5,\n" + //
                          "  \"rating\": 5,\n" + //
                          "  \"stock\": 15,\n" + //
                          "  \"tags\": [\n" + //
                          "    \"beauty\",\n" + //
                          "    \"mascara\"\n" + //
                          "  ],\n" + //
                          "  \"dimensions\": {\n" + //
                          "    \"width\": 23.17,\n" + //
                          "    \"height\": 14.43,\n" + //
                          "    \"depth\": 28.01\n" + //
                          "  }\n" + //
                          "}";

        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "products")
                            .pathParam("method", "add")
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .post("{path}/{method}");
        System.out.println("add product" + response.asPrettyString());


        //Validation
        JsonPath addJsonPath = response.jsonPath();
        responseItem = addJsonPath.getObject("", ResponseItem.class);

        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals(responseItem.title,"Le minerale");
        Assert.assertEquals(responseItem.price,10000);
        Assert.assertEquals(responseItem.discountPercentage, 5);
        Assert.assertEquals(responseItem.stock, 15);
        Assert.assertEquals(responseItem.category, "food");

        /*
         * Simulate kalau idproduct nya kita dapat dari responseItem.id,
         * Tapi karena id nya akan selalu sama bakanya kita modify manual
         *  idProduct = responseItem.id;
         */
        idProduct = 1;


    }

    @When("I add new {string} to etalase")
    public void addNewProducts(String payload) throws JsonMappingException, JsonProcessingException{
         //Implementation
        dataRequest = new DataRequest();
        
        for(Map.Entry<String, String> entry : dataRequest.addItemCollection().entrySet()){
            if (entry.getKey().equals(payload)) {
                json = entry.getValue();
                break;
            }
        }
        response = endpoints.addProductData("products", "add", json);

        //Object mapper
        /*
         * Convert JSON to POJO
         */
        ObjectMapper requestAddItem = new ObjectMapper();
        requestItem = requestAddItem.readValue(json, RequestItem.class);

        //Validation
        JsonPath addJsonPath = response.jsonPath();
        responseItem = addJsonPath.getObject("", ResponseItem.class);

        Assert.assertEquals(response.statusCode(), 201);
        assertion.assertAddProduct(responseItem, requestItem);

    }

    @Then("The product is available")
    public void getSingleProduct(){
         //Implementation
         System.out.println("get single product");
          /*
         * 'https://dummyjson.com/products/1'
         */

        response = endpoints.getProductById("product", idProduct);
        
        System.out.println("ini adalah response" + response.asPrettyString());
        //Validation
        //-----------------*****--------------------
    }

    @Then("I can update item {string}")
    public void updateSingleProduct(String payload){
        //Implementation
        System.out.println("update single product");

        for(Map.Entry<String, String> entry : dataRequest.addItemCollection().entrySet()){
            if (entry.getKey().equals(payload)) {
                json = entry.getValue();
                break;
            }
        }
        response = endpoints.updateProductById("product", idProduct, json);

        System.out.println("update product" + response.asPrettyString());
        //Validation
        //-----------------*****--------------------
    }

}
