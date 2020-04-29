
import demo.StudentRequest;
import demo.StudentResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class Student {

    //HTTP REQUEST GET
    @Test
    public void getStudent(){
        //instance object "response" for HTTP REQUEST "GET"
        Response response = RestAssured
                .given()
                .baseUri("https://apingweb.com/")
                .basePath("/api")
                .log()
                .all()
                .header("Content-type","application/json")
                .header("Accept","*/*")
                .get("/rest/students");

        //get body of response and print them
        response.getBody().prettyPrint();
        System.out.println(response.getStatusCode());
        Assert.assertEquals(200,response.getStatusCode());
        Assert.assertThat("Too Late Response",response.getTime(), Matchers.lessThan(10000L));

        //Validate if status message
        Assert.assertEquals("Success",response.path("message"));

        //Validate if "Tiger Nixon is employee_name in data[0]"
        Assert.assertEquals("Andrei",response.path("data[0].name"));

        //Deserializer
        // All of EmployeeResponse filled in variable employeeResponse
        StudentResponse studentResponse = response.as(StudentResponse.class);
        // Print status from employeeResponse
        System.out.println(studentResponse.getMessage());

        //Print employeename for data-0 on employeeResponse
        // System.out.println(employeeResponse.get(0).getEmployeeName());
    }

    //HTTP REQUEST POST
    @Test
    public void createStudent(){

        //Serialize
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setName("Oriani");
        studentRequest.setEmail("ori@hotmail.com");
        studentRequest.setAge("20");

        //instance object "response" for HTTP REQUEST "POST"
        Response response = RestAssured
                .given()
                .baseUri("https://apingweb.com/")
                .basePath("/api")
                .log()
                .all()
                .header("Content-type","application/json")
                .header("Accept","*/*")
                .body(studentRequest) //requestBody
                .post("rest/student/create");

        //get body of response and print them
        response.getBody().prettyPrint();
    }

    // HTTP REQUEST DELETE
    @Test
    public void deleteStudent() {

        int student_id = 434;

        RestAssured.baseURI = "https://apingweb.com/api/rest/student";
        RequestSpecification request = RestAssured.given();

        // Add a header stating the Request body is a JSON
        request.header("Content-Type", "application/json");

        // Delete the request and check the response
        Response response = request.delete("/delete/"+ student_id);

        int statusCode = response.getStatusCode();
        System.out.println(response.asString());
        Assert.assertEquals(statusCode, 200);

        Assert.assertEquals("Success",response.path("message"));
//        String jsonString =response.asString();
//        Assert.assertEquals(jsonString.contains("successfully! deleted Records"), true);
    }
}
