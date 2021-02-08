package com.example.universalconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class UniversalConverterApplication {

	public static void main(String[] args) throws IOException {
//		try (FileReader reader = new FileReader(args[0])) {
//			Files.createFile(Path.of("resources\\dataUnits.csv"));
//			File dataUnits = new File("resources\\dataUnits.csv");
//			FileWriter writer = new FileWriter(dataUnits);
//
//			while(reader.read() != -1) {
//				writer.append((char)reader.read());
//			}
////			File units = new File(args[0]);
////			units.renameTo(new File("resources\\" + units.getName()));
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//		try{
//			Files.move(Paths.get(args[0]),Paths.get("resources/"));
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
		File fileToMove = new File(args[0]);
		boolean isMoved = fileToMove.renameTo(new File("src\\main\\resources\\"));
		if (isMoved) {
			System.out.println("Fin");
		}

		SpringApplication.run(UniversalConverterApplication.class, args);
	}

}
