package com.project.models;

import com.project.models.saveOptions.Saveable;
import com.project.utils.WorkerStaticArrFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Corporation implements Externalizable {
    private List<Worker> workers;
    Saveable saveable;

    public Corporation() {
        workers = new ArrayList<>();
    }

    public Corporation(Saveable saveable) {
        this.saveable = saveable;
        workers = saveable.load("task5.txt");
        if (workers.isEmpty()) {
            WorkerStaticArrFactory factory = new WorkerStaticArrFactory();
            for (int i = 0; i < 5; i++) {
                workers.add(factory.getWorker());
            }
        }
    }

    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public void editWorker(Worker worker, int index) {
        workers.set(index, worker);
    }

    public void deleteWorker(int index) {
        workers.remove(index);
    }

    public void saveSearchResults(List<Worker> found) {
        Path path = Path.of(".", "files", "task5_search_result_" + LocalDateTime.now().toString().replace(":", "_").replace(".", "_") + ".txt");
        try {
            File file = path.toFile();
            if (!file.createNewFile()) {
                throw new IOException("Could not create file. Try again");
            }
            try (Writer writer = new FileWriter(file)) {
                found.forEach(x -> {
                    try {
                        writer.append(x.toString()).append("\n");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                });
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save() {
        saveable.save("task5.txt", this);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(workers);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        workers = (List<Worker>) in.readObject();
    }

    @Override
    public String toString() {
        return "Corporation{" +
                "workers=" + workers +
                '}';
    }

    public List<Worker> getWorkers() {
        return workers;
    }
}
