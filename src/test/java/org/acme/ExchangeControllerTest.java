package org.acme;

import com.google.gson.Gson;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.acme.model.InputData;
import org.junit.jupiter.api.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExchangeControllerTest {
    private final String VALID_INPUT_DATA = "src/test/java/org/acme/json/validInputData.json";
    private final String NULL_AMOUNT_INPUT_DATA =
            "src/test/java/org/acme/json/nullAmountInputData.json";
    private final String NULL_MY_CURRENCY_INPUT_DATA =
            "src/test/java/org/acme/json/nullMyCurrencyInputData.json";
    private final String NULL_TARGET_CURRENCY_INPUT_DATA =
            "src/test/java/org/acme/json/nullTargetCurrencyInputData.json";
    private final String INVALID_TARGET_CURRENCY_INPUT_DATA_PATH =
            "src/test/java/org/acme/json/invalidTargetCurrencyInputData.json";
    private final String INVALID_MY_CURRENCY_INPUT_DATA_PATH =
            "src/test/java/org/acme/json/invalidMyCurrencyInputData.json";

//    @Test
//    public void exchangeCurrencyValidEndpoint() throws IOException {
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(new Gson().fromJson(
//                        readFile(VALID_INPUT_DATA),
//                        InputData.class))
//          .when()
//                .post("/currency/currencyExchangeValue")
//          .then()
//             .statusCode(200)
//             .body(is("13.34"));
//    }
//
//    @Test
//    public void exchangeCurrencyNullAmount() throws IOException {
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(new Gson().fromJson(
//                        readFile(NULL_AMOUNT_INPUT_DATA),
//                        InputData.class))
//                .when()
//                .post("/currency/currencyExchangeValue")
//                .then()
//                .statusCode(400)
//                .body(is("Amount is null."));
//    }
//
//    @Test
//    public void exchangeCurrencyNullMyCurrency() throws IOException {
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(new Gson().fromJson(
//                        readFile(NULL_MY_CURRENCY_INPUT_DATA),
//                        InputData.class))
//                .when()
//                .post("/currency/currencyExchangeValue")
//                .then()
//                .statusCode(400)
//                .body(is("MyCurrency is null."));
//    }
//
//    @Test
//    public void exchangeCurrencyNullTargetCurrency() throws IOException {
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(new Gson().fromJson(
//                        readFile(NULL_TARGET_CURRENCY_INPUT_DATA),
//                        InputData.class))
//                .when()
//                .post("/currency/currencyExchangeValue")
//                .then()
//                .statusCode(400)
//                .body(is("TargetCurrency is null."));
//    }
//
//    @Test
//    public void invalidMyCurrencyInputData() throws IOException {
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(new Gson().fromJson(
//                        readFile(INVALID_MY_CURRENCY_INPUT_DATA_PATH),
//                        InputData.class))
//                .when()
//                .post("/currency/currencyExchangeValue")
//                .then()
//                .statusCode(400)
//                .body(is("Invalid myCurrency value."));
//    }
//
//    @Test
//    public void invalidTargetCurrencyInputData() throws IOException {
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(new Gson().fromJson(
//                        readFile(INVALID_TARGET_CURRENCY_INPUT_DATA_PATH),
//                        InputData.class))
//                .when()
//                .post("/currency/currencyExchangeValue")
//                .then()
//                .statusCode(400)
//                .body(is("Invalid targetCurrency value."));
//    }
//
//    public String readFile(String path) throws IOException {
//        StringBuilder stringBuilder = new StringBuilder();
//
//        BufferedReader bufferedReader =
//                new BufferedReader(
//                        new FileReader(path));
//
//        for(String i = bufferedReader.readLine(); i != null; i = bufferedReader.readLine()) {
//            stringBuilder.append(i);
//        }
//
//        return stringBuilder.toString();
//    }
}