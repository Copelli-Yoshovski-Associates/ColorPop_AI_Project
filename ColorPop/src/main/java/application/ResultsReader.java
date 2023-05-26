package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ResultsReader {

    private static ResultsReader instance = null;

    private ResultsReader(){

    }

    public static ResultsReader getInstance(){
        if(instance == null)
            instance = new ResultsReader();
        return instance;
    }
    

    public int getHighestScore() {
        String filePath = System.getProperty("user.dir") + "/src/main/resources/results.txt";
        int highestScore = Integer.MIN_VALUE;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int score = Integer.parseInt(line);
                if (score > highestScore) {
                    highestScore = score;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return highestScore;
    }    

    public void writeResultToFile(int score) {
		String filePath = System.getProperty("user.dir") + "/src/main/resources/results.txt";

		
		try {
			// Check if the file exists
			boolean fileExists = Files.exists(Path.of(filePath));
	

			System.out.println("file exists: " + fileExists);

			// If the file doesn't exist, create it
			if (!fileExists) {
				Files.createFile(Path.of(filePath));
			}
	
			// Convert the score to a string and append it to the file as a new line
			String scoreString = String.valueOf(score);
			Files.writeString(Path.of(filePath), scoreString + System.lineSeparator(), StandardOpenOption.APPEND);
			
			System.out.println("Score added to the file.");
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}
    

}
