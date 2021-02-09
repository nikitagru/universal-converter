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
		SpringApplication.run(UniversalConverterApplication.class, args);
	}

}
