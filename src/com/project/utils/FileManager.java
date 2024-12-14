package com.project.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {
    static public List<String> readLines(String filename) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of(".", "files", filename));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return lines;
    }

    static public List<String> readLinesWithInputStream(String filename) {
        List<String> list = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(Path.of(".", "files", filename).toFile())) {

            int size = inputStream.available();
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            String result = new String(bytes);

            int startIndex = 0;
            for (int i = 0; i < result.length(); i++) {
                if (result.charAt(i) == '\n' || result.charAt(i) == '\r') {
                    if (startIndex != i) {
                        list.add(result.substring(startIndex, i));
                    }
                    startIndex = i + 1;
                }
            }
            list.add(result.substring(startIndex));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    static public List<int[]> readIntArrays(String filename) {
        List<String> strings = readLines(filename);
        List<int[]> arrays = new ArrayList<>();
        strings.forEach(s -> arrays.add(Arrays.stream(s.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray()));
        return arrays;
    }

    static public void writeIntArrays(String filename, List<int[]> arrays) {
        try (Writer writer = new FileWriter(Path.of(".", "files", filename).toFile())) {
            arrays.forEach(x -> {
                try {
                    writer.append(Arrays.toString(x)).append("\n");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
