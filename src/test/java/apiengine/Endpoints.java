package apiengine;

import com.apiautomation.constants.Constants;

import io.cucumber.java.an.E;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Endpoints {
    RequestSpecification requestSpecification;

    public Endpoints(){
        RestAssured.baseURI = Constants.BASE_URL;
        requestSpecification = RestAssured
                                .given();
    }


    public Response getAllProducts(String path){
        Response response = requestSpecification
                            .when()
                                .get(path);
        return response;
    }


    public Response addProductData(String path, String method, String json){
        Response response = requestSpecification
                            .pathParam("path", path)
                            .pathParam("method", method)
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .post("{path}/{method}");
        return response;
    }

    public Response getProductById(String path, int idProduct){
        Response response = requestSpecification
                               .pathParam("idProduct", idProduct)
                               .pathParam("path", path)
                           .when()
                               .get("{path}/{idProduct}");
        return response;
    }


    public Response updateProductById(String path, int idProduct, String json){
        Response response = requestSpecification
                            .pathParam("path", path)
                            .pathParam("idProduct", idProduct)
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .put("{path}/{idProduct}");
        return response;
    }

    public Response deleteProductById(String path, int idProduct){
        Response response = requestSpecification
                            .pathParam("path", path)
                            .pathParam("idProduct", idProduct)
                            .contentType("application/json")
                            .when()
                                .delete("{path}/{idProduct}");
        return response;
    }

    public Response getObjectByIds(String path, String idProducts){
         Response response = requestSpecification
                               .pathParam("path", path)
                               .queryParam("id", idProducts)
                           .when()
                               .get("{path}/{id}");
        return response;
    }
}



