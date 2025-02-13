package restassured;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredImpl {
    public static void main(String[] args) {
        getAllProducts();
        getSingleProduct();
        searchProduct();
        addProduct();

        updateProduct();

        deleteProduct();
    }


    public static String auth(){

        String token;
        String json = "{\n" + //
                        "  \"username\": \"emilys\",\n" + //
                        "  \"password\": \"emilyspass\",\n" + //
                        "  \"expiresInMins\": 30\n" + //
                        "}";
        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

       Response response = requestSpecification
                               .log()
                               .all()
                               .body(json)
                               .contentType("application/json")
                               .pathParam("path", "auth")
                               .pathParam("section", "login")
                           .when()
                               .post("{path}/{section}");

        JsonPath jsonPath = response.jsonPath();

        token = jsonPath.get("accessToken");

        return token;
    }

    public static void getAllProducts(){
        //Define baseURI
       /*
        *  "https://dummyjson.com/products"
        baseURI = https://dummyjson.com
        path = products
        */

        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification.log().all().get("products");


        Response response2 = requestSpecification
                                .log()
                                .all()
                            .when()
                                .get("products");

        System.out.println("Hasilnya adalah " + response2.asPrettyString());
        System.out.println("Hasilnya adalah " + response.asPrettyString());
    }

    public static void getSingleProduct(){
        /*
         * 'https://dummyjson.com/products/1'
         */

        String token;

        token = auth();


         RestAssured.baseURI = "https://dummyjson.com";
         RequestSpecification requestSpecification = RestAssured
                                                     .given();

        Response response = requestSpecification
                                .log()
                                .all()
                                .pathParam("idProduct", 1)
                                .pathParam("path", "products")
                                .header("Authorization", "Berear " + token)
                            .when()
                                .get("{path}/{idProduct}");


        System.out.println("single Product " + response.asPrettyString()); 
    }

    public static void searchProduct(){
        /*
         * 'https://dummyjson.com/products/search?q=phone'
         */
        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

         Response response = requestSpecification
                                .log()
                                .all()
                                .pathParam("path", "products")
                                .pathParam("method", "search")
                                .queryParam("q","iPhone 5s")
                                .when()
                                    .get("{path}/{method}");
        System.out.println("Ini adalah hasil search" + response.asPrettyString());           
    }

    public static void addProduct(){

        String json = "{\n" + //
                        "            \"title\": \"Annibale Colombo Bed\",\n" + //
                        "            \"price\": \"-314141\",\n" + //
                        "            \"description\": \"The Annibale Colombo Bed is a luxurious and elegant bed frame, crafted with high-quality materials for a comfortable and stylish bedroom.\"\n" + //
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
    }

    public static void updateProduct(){
        String json = "{\n" + //
                        "            \"title\": \"Albert Simanjuntak\",\n" + //
                        "            \"price\": \"-314141\",\n" + //
                        "            \"description\": \"The Annibale Colombo Bed is a luxurious and elegant bed frame, crafted with high-quality materials for a comfortable and stylish bedroom.\"\n" + //
                        "}";

        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "products")
                            .pathParam("idProduct", "1")
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .put("{path}/{idProduct}");
        System.out.println("update product" + response.asPrettyString());
    }

    public static void deleteProduct(){
        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "products")
                            .pathParam("idProduct", "1")
                            .contentType("application/json")
                            .when()
                                .delete("{path}/{idProduct}");
        System.out.println("delete product" + response.asPrettyString());

    }
}
