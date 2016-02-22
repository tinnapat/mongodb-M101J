package finalexam;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Final7 {

	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase("m101");
		MongoCollection<Document> images = database.getCollection("images");
		MongoCollection<Document> albums = database.getCollection("albums");
		
		List<Document> imagesList = images.find().into(new ArrayList<Document>());
		for (Document image : imagesList) {
			System.out.println(image);
			
			Integer imageId = (Integer) image.get("_id");
			if (isOrphanImage(albums, imageId)) {
				images.deleteOne(new Document("_id", imageId));
				System.out.println("Image id'" + imageId + "' removed.");
			}
		}
		
		client.close();
	}
	
	public static boolean isOrphanImage(MongoCollection<Document> albums, Integer imageId) {
		return albums.count(new Document("images", imageId)) <= 0;
	}

}
