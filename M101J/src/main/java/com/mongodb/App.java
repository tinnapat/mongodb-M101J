package com.mongodb;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App {
	
	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase("students");
		MongoCollection<Document> grades = database.getCollection("grades");
		
		List<Document> all = grades
				.find(eq("type", "homework"))
				.sort(ascending("student_id", "score"))
				.into(new ArrayList<Document>());
		
		boolean sameStudent = false;
		Integer lastStudentId = null;
		for (Document doc : all) {
			Integer studentId = doc.getInteger("student_id");
			if (!studentId.equals(lastStudentId)) {
				sameStudent = false;
			}
			else {
				sameStudent = true;
			}
			lastStudentId = studentId;
			
			System.out.println(doc + " , " + sameStudent);
			
//			if (!sameStudent) {
//				grades.deleteOne(eq("_id", doc.getObjectId("_id")));
//			}
		}
		
		client.close();
	}
}
