package homework;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Homework31 {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase("school");
		MongoCollection<Document> students = database.getCollection("students");
		
		List<Document> studentList = students.find().into(new ArrayList<Document>());
		for (Document student : studentList) {
			System.out.println(student);
			
			List<Document> newScores = new ArrayList<Document>();
			List<Document> scores = (List<Document>) student.get("scores");
			Document highestHw = null;
			for (Document scoreDoc : scores) {
				System.out.println("\t" + scoreDoc);
				
				if ("exam".equals(scoreDoc.get("type"))) {
					newScores.add(scoreDoc);
				}
				else if ("quiz".equals(scoreDoc.get("type"))) {
					newScores.add(scoreDoc);
				}
				else if ("homework".equals(scoreDoc.get("type"))) {
					if (highestHw == null) {
						highestHw = scoreDoc;
					}
					else {
						if (scoreDoc.get("score", double.class) > highestHw.get("score", double.class)) {
							highestHw = scoreDoc;
						}
						newScores.add(highestHw);
					}
				}
			}
			
			System.out.println("\t\t" + newScores);
			students.updateOne(eq("_id", student.get("_id")), 
					new Document("$set", new Document("scores", newScores)));
		}
		
		client.close();
	}
}
