package com.library.utility;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class LibraryAPI_Util {

    /**
     * Provide email and password manually
     * Return directly POST /login request to obtain token in the response body
     */
    public static String getToken(String email, String password) {

        return given()
                //.contentType(ContentType.JSON) //404
                //.contentType("application/x-www-form-urlencoded") //OK or
                .contentType(ContentType.URLENC) //OK
                .formParam("email", email)
                .formParam("password", password).
                when()
                .post(ConfigurationReader.getProperty("library.baseUri") + "/login")
                .prettyPeek()
                .then().statusCode(200)
                .extract().jsonPath().getString("token");
    }

    public static String getToken(String userType) {

        String email = ConfigurationReader.getProperty(userType + "_username");
        String password = ConfigurationReader.getProperty(userType + "_password");

        return getToken(email, password);
    }


    public static Map<String, Object> getRandomBookMap() {

        Faker faker = new Faker();
        Map<String, Object> bookMap = new LinkedHashMap<>();
        String randomBookName = "AR "+faker.book().title() + faker.number().numberBetween(0, 1000);
        //to differentiate the name of book we can add our initials
        bookMap.put("name", randomBookName);
        bookMap.put("isbn", faker.code().isbn10());
        bookMap.put("year", String.valueOf(faker.number().numberBetween(1000, 2021)));
        bookMap.put("author", faker.book().author());
        bookMap.put("book_category_id", String.valueOf(faker.number().numberBetween(1, 20)));  // in library app valid category_id is 1-20
        bookMap.put("description", faker.chuckNorris().fact());

        return bookMap;
    }

    public static Map<String, Object> getRandomUserMap() {

        Faker faker = new Faker();
        Map<String, Object> userMap = new LinkedHashMap<>();
        String fullName = faker.name().fullName();
        String email = fullName.substring(0, fullName.indexOf(" ")) +faker.number().numberBetween(1,100) +"@library";
        System.out.println(email);
        userMap.put("full_name", fullName);
        userMap.put("email", email);
        userMap.put("password", "libraryUser");
        // 2 is librarian as role
        userMap.put("user_group_id", "2");
        userMap.put("status", "ACTIVE");
        userMap.put("start_date", "2023-03-11");
        userMap.put("end_date", "2024-03-11");
        userMap.put("address", faker.address().cityName());

        return userMap;
    }


}
