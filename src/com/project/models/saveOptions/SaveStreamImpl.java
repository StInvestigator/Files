package com.project.models.saveOptions;

import com.project.models.Corporation;
import com.project.models.Worker;
import com.project.utils.FileManager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveStreamImpl implements Saveable {

    @Override
    public void save(String filename, Corporation corp) {
        try (Writer writer = new FileWriter(Path.of(".", "files", filename).toFile())) {
            corp.getWorkers().forEach(x -> {
                try {
                    writer.append(x.getName()).append(",")
                            .append(x.getSurname()).append(",")
                            .append(Integer.toString(x.getAge())).append(",")
                            .append(x.getPosition()).append("\n");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Worker> load(String filename) {
        List<String> lines = FileManager.readLinesWithInputStream(filename);
        List<Worker> workers = new ArrayList<>();
        lines.forEach(line -> {
            if (line.length() > 1) {
                String[] words = line.split(",");
                workers.add(new Worker(words[0], words[1], Integer.parseInt(words[2])
                        , words[3]));
            }
        });
        return workers;
    }
}
