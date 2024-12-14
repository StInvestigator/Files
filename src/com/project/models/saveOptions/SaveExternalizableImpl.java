package com.project.models.saveOptions;

import com.project.models.Corporation;
import com.project.models.Worker;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SaveExternalizableImpl implements Saveable {
    @Override
    public void save(String filename, Corporation corp) {
        try (OutputStream fileOut = new FileOutputStream(Path.of(".", "files", filename).toFile());
             ObjectOutputStream outStream = new ObjectOutputStream(fileOut)) {

            outStream.writeObject(corp);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public List<Worker> load(String filename) {
        try (InputStream fileInput = new FileInputStream(Path.of(".", "files", filename).toFile());
             ObjectInputStream objInput = new ObjectInputStream(fileInput)) {

            if (fileInput.available() == 0) {
                return new ArrayList<>();
            }

            Object currentObject = objInput.readObject();

            if (currentObject instanceof Corporation) {
                return ((Corporation) currentObject).getWorkers();
            }

        } catch (IOException | ClassNotFoundException e) {
            if (e.getMessage() != null) System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }
}
