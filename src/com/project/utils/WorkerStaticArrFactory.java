package com.project.utils;

import com.project.models.Worker;

import java.util.Random;

public class WorkerStaticArrFactory {

    private final static String[] names = new String[]{"John", "Emily", "Michael", "Sarah", "David", "Jessica", "Robert", "Sophia", "James", "Olivia"};
    private final static String[] surnames = new String[]{"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"};
    private final static String[] positions = new String[]{"CEO", "CFO", "COO", "CTO", "Manager", "Team Lead", "Software Engineer", "Marketing Specialist", "HR Coordinator", "Sales Representative"};

    public Worker getWorker(){
        Random random = new Random();
        return new Worker(names[random.nextInt(0, names.length)],surnames[random.nextInt(0, surnames.length)], random.nextInt(18,60), positions[random.nextInt(0, positions.length)]);
    }
}
