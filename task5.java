import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ProductRecommender {

    public static void main(String[] args) {
        try {
            // Create sample data file
          
            File sampleData = createSampleDataFile();
            
            // Create data model from the file
          
            DataModel model = new FileDataModel(sampleData);
            
            // Define similarity metric (Pearson correlation)
          
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            
            // Define neighborhood (users similar to the given user)
          
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);
            
            // Create recommender
          
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
            
            // Generate recommendations for user with ID 1
          
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);
            
            // Print recommendations
            System.out.println("Recommended products for User 1:");
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Product ID: " + recommendation.getItemID() + 
                                 ", Estimated Preference: " + recommendation.getValue());
            }


          
            // Clean up the space
            sampleData.delete();



          
        } catch (IOException | TasteException e) {
            System.out.println("Error in recommendation: " + e.getMessage());
            e.printStackTrace();
        }
    }


  //creat file throws ioexception ,,,
    private static File createSampleDataFile() throws IOException {
        File file = new File("preferences.csv");
        FileWriter writer = new FileWriter(file);
        
        // Format: userID,itemID,preference
        writer.write("1,101,5.0\n");
        writer.write("1,102,3.0\n");
        writer.write("1,103,2.5\n");
        
        writer.write("2,101,2.0\n");
        writer.write("2,102,2.5\n");
        writer.write("2,103,5.0\n");
        writer.write("2,104,3.0\n");
        
        writer.write("3,101,2.5\n");
        writer.write("3,104,4.0\n");
        writer.write("3,105,4.5\n");
        writer.write("3,107,5.0\n");
        
        writer.write("4,101,5.0\n");
        writer.write("4,103,3.0\n");
        writer.write("4,104,4.0\n");
        writer.write("4,106,4.5\n");
        
        writer.write("5,101,3.5\n");
        writer.write("5,102,3.5\n");
        writer.write("5,103,4.0\n");
        writer.write("5,104,4.5\n");
        writer.write("5,105,5.0\n");
        writer.write("5,106,4.0\n");
        
        writer.close();
        return file;
    }
}

