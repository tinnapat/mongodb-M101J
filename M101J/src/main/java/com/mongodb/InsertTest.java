package com.mongodb;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertTest {

	public static void main(String[] args) {
		MongoClient c = new MongoClient();
		MongoDatabase db = c.getDatabase("test");
		MongoCollection<Document> animals = db.getCollection("animals");

		Document animal = new Document("animal", "monkey");

		animals.insertOne(animal);
		animal.remove("animal");
		animal.append("animal", "cat");
		animals.insertOne(animal);
		animal.remove("animal");
		animal.append("animal", "lion");
		animals.insertOne(animal);
		
		c.close();
	}

}
