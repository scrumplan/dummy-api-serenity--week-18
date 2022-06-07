package com.restapiexample.dummy.dummyrestapiInfo;

import com.restapiexample.dummy.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasKey;

@RunWith(SerenityRunner.class)
public class DummyRestAPICURDTest extends TestBase {

    static String status = "success";
    static int employeeID;
    static String message;
    static String employeeName = "Doris Wilder";
    @Steps
    DummyRestAPISteps dummyRestAPISteps;

    @Title("This will create a new employee")
    @Test
    public void test001() {
        HashMap<String, Object> createRecord = new HashMap<>();
        createRecord.put("name", employeeName);
        createRecord.put("salary", "12500");
        createRecord.put("age", "65");
        createRecord.put("id", 104);
        ValidatableResponse response = dummyRestAPISteps.createUser(status, createRecord);
        response.log().all().statusCode(200);
        employeeID = response.log().all().extract().path("data.id");
        message = response.log().all().extract().path("message");
        Assert.assertThat(message, anything("Successfully!"));
        System.out.println(employeeID + " is added " + message);
    }

    @Title("This will Read an employee by ID")
    @Test
    public void test002() {
        ValidatableResponse response = dummyRestAPISteps.readingEmployee();
        response.log().all().statusCode(200);
        employeeID = response.log().all().extract().path("data.id");
        HashMap<?, ?> getEmployee = response.log().all().extract().path("");
        Assert.assertThat(getEmployee, hasKey("status"));
        Assert.assertThat(getEmployee, hasKey("data"));
        message = response.log().all().extract().path("message");
        Assert.assertThat(message, anything("Successfully!"));
    }

    @Title("This will Update an employee by ID")
    @Test
    public void test003() {
        HashMap<String, Object> createRecord = new HashMap<>();
        employeeName = employeeName + "_updated";
        createRecord.put("name", employeeName);
        ValidatableResponse response = dummyRestAPISteps.updatingEmployee(createRecord);
        response.log().all().statusCode(200);
        message = dummyRestAPISteps.readingEmployee().log().all().extract().path("message");
        System.out.println(message);
        Assert.assertThat(message, anything("Successfully!"));
    }

    @Title("This will Delete an employee by ID")
    @Test
    public void test004() {
        ValidatableResponse response = dummyRestAPISteps.deletingEmployee();
        response.log().all().statusCode(200);
        message = dummyRestAPISteps.readingEmployee().log().all().extract().path("message");
        System.out.println(message);
        Assert.assertThat(message, anything("Successfully!"));
    }


}
