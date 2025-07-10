import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandlingUtility {

    // Method to write text to a file
    public static void writeToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            System.out.println("Content written to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to read text from a file
    public static void readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println("Reading from file: " + fileName);
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

    // Method to modify text in a file
    public static void modifyFile(String fileName, String oldContent, String newContent) {
        try {
            // Read the existing content
            StringBuilder fileContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append(System.lineSeparator());
                }
            }

            // Replace old content with new content
            String updatedContent = fileContent.toString().replace(oldContent, newContent);

            // Write the updated content back to the file
            writeToFile(fileName, updatedContent);
            System.out.println("File modified successfully.");
        } catch (IOException e) {
            System.err.println("Error modifying file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String fileName = "example.txt";

        // Writing to the file
        writeToFile(fileName, "Hello, World!\nThis is a sample text file.");

        // Reading from the file
        readFromFile(fileName);

        // Modifying the file
        modifyFile(fileName, "sample", "modified sample");

        // Reading the modified file
        readFromFile(fileName);
    }
}
