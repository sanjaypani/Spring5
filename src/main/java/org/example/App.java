package org.example;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;


/**
 * Hello world!
 *
 */
public class App {
	
    public static Object handleRequest(Request request, Context context) {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion("ap-south-1").build();
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Student student = null;

        switch (request.getHttpMethod()) {
            case "GET":
                student = mapper.load(Student.class, request.getId());
                if (student == null) {
                	throw new RuntimeException("Student Not Found");
                }
                return student;

            case "POST":
                student = request.getStudent();
                mapper.save(student);
                return student;

            default:
                System.out.println("Method Not Supported Exception");
                break;

        }

        return null;
    }
}
