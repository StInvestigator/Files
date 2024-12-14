package com.project.menu;

import com.project.models.Corporation;
import com.project.models.Worker;
import com.project.models.saveOptions.SaveExternalizableImpl;
import com.project.models.saveOptions.SaveStreamImpl;
import com.project.utils.FileManager;

import java.util.*;

public class MenuExecutor {
    public void start() {
        executeTask1();
        System.out.println("-".repeat(30));
        executeTask2();
        System.out.println("-".repeat(30));
        executeTask3();
        System.out.println("-".repeat(30));
        executeTask4();
        System.out.println("-".repeat(30));
        executeTask5();
        System.out.println("-".repeat(30));
    }

    private void executeTask1() {
        List<String> list1 = FileManager.readLines("task1_1.txt");
        List<String> list2 = FileManager.readLinesWithInputStream("task1_2.txt");

        int min = Math.min(list1.size(), list2.size());
        int max = Math.max(list1.size(), list2.size());
        for (int i = 0; i < min; i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                System.out.println("Line #" + (i + 1) + " in first file: " + list1.get(i));
                System.out.println("Line #" + (i + 1) + " in second file: " + list2.get(i));
            }
        }
        for (int i = min; i < max; i++) {
            if (list1.size() == max) {
                System.out.println("Line #" + (i + 1) + " in first file without corresponding one in the second file: " + list1.get(i));
            } else {
                System.out.println("Line #" + (i + 1) + " in second file without corresponding one in the first file: " + list2.get(i));
            }
        }
    }

    private void executeTask2() {
        List<String> list1 = FileManager.readLines("task2.txt");
        list1.stream().max(Comparator.comparingInt(String::length))
                .ifPresent(s -> System.out.println("Longest string with length of " + s.length() + " characters is: " + s));
    }

    private void executeTask3() {
        List<int[]> arrays = FileManager.readIntArrays("task3.txt");
        System.out.println("Arrays read from file:");
        arrays.forEach(a -> System.out.println(Arrays.toString(a) +
                " min: " + Arrays.stream(a).min().getAsInt() +
                " max: " + Arrays.stream(a).max().getAsInt() +
                " sum: " + Arrays.stream(a).sum()
        ));
        System.out.println("Arrays full statistics: ");
        System.out.println("Min: " + Arrays.stream(arrays.stream().min(Comparator.comparingInt(a ->
                Arrays.stream(a).min().getAsInt())).get()).min().getAsInt());
        System.out.println("Max: " + Arrays.stream(arrays.stream().max(Comparator.comparingInt(a ->
                Arrays.stream(a).max().getAsInt())).get()).max().getAsInt());
        System.out.println("Sum: " + arrays.stream().mapToInt(a -> Arrays.stream(a).sum()).sum());
    }

    private void executeTask4() {
        System.out.print("Enter an int array: ");
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        String[] arr = line.split("\\s+");
        int[] vals = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            vals[i] = Integer.parseInt(arr[i]);
        }

        List<int[]> result = new ArrayList<>();
        result.add(Arrays.copyOf(vals, arr.length));
        result.add(Arrays.stream(vals).filter(a -> a % 2 == 0).toArray());
        result.add(Arrays.stream(vals).filter(a -> a % 2 != 0).toArray());
        for (int i = 0; i < arr.length / 2; i++) {
            int temp = vals[i];
            vals[i] = vals[arr.length - i - 1];
            vals[arr.length - i - 1] = temp;
        }
        result.add(vals);

        FileManager.writeIntArrays("task4.txt", result);
    }

    private void executeTask5() {
        Corporation corp = new Corporation(new SaveStreamImpl());

        Scanner scanner = new Scanner(System.in);
        int answ;

        do {
            System.out.println("""
                    Corporation menu options:
                    1. Add new worker
                    2. Edit worker
                    3. Delete worker
                    4. Find workers
                    5. Save workers
                    6. Show workers
                    -1. Exit
                    """);
            answ = scanner.nextInt();
            scanner.nextLine();
            switch (answ) {
                case 1:
                    corp.addWorker(getWorkerFromUser());
                    break;
                case 2:
                    int index = getWorkerIndex(corp.getWorkers());
                    corp.editWorker(getWorkerFromUser(), index);
                    break;
                case 3:
                    corp.deleteWorker(getWorkerIndex(corp.getWorkers()));
                    break;
                case 5:
                    corp.save();
                    break;
                case 6:
                    corp.getWorkers().forEach(System.out::println);
                    break;
                case 4:
                    System.out.println("""
                            Find workers by:
                            1. Surname
                            2. Surname`s first letter
                            3. Age
                            -1. Back
                            """);

                    answ = scanner.nextInt();
                    scanner.nextLine();

                    switch (answ) {
                        case 1:
                            System.out.print("Enter the surname: ");
                            String surname = scanner.nextLine();
                            System.out.println("Results found:");
                            List<Worker> result1 = corp.getWorkers().stream().filter(x -> x.getSurname().equalsIgnoreCase(surname)).toList();
                            result1.forEach(System.out::println);
                            optionSaveResults(corp, result1);
                            break;
                        case 2:
                            System.out.print("Enter the first letter: ");
                            char firstLetter = Character.toLowerCase(scanner.nextLine().charAt(0));
                            System.out.println("Results found:");
                            List<Worker> result2 = corp.getWorkers().stream()
                                    .filter(x -> Character.toLowerCase(x.getSurname()
                                            .charAt(0)) == firstLetter).toList();
                            result2.forEach(System.out::println);
                            optionSaveResults(corp, result2);
                            break;
                        case 3:
                            int age;
                            System.out.print("Enter the age: ");
                            age = scanner.nextInt();
                            System.out.println("Results found:");
                            List<Worker> result3 = corp.getWorkers().stream()
                                    .filter(x -> x.getAge() == age).toList();
                            result3.forEach(System.out::println);
                            optionSaveResults(corp, result3);
                            break;
                        case -1:
                            answ = 0;
                            break;
                    }
                    break;
            }
        } while (answ != -1);


        corp.save();
    }

    private Worker getWorkerFromUser() {
        System.out.print("Enter worker`s name: ");
        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        System.out.print("Enter worker`s surname: ");
        String surname = input.nextLine();
        int age;
        System.out.print("Enter worker`s age: ");
        age = input.nextInt();
        input.nextLine();

        System.out.print("Enter worker`s position: ");
        String position = input.nextLine();

        return new Worker(name, surname, age, position);
    }

    private int getWorkerIndex(List<Worker> workers) {
        for (int i = 0; i < workers.size(); i++) {
            System.out.println((i + 1) + "# " + workers.get(i));
        }
        Scanner scanner = new Scanner(System.in);
        int index;
        System.out.print("Choose the worker`s number:");
        index = scanner.nextInt();
        scanner.nextLine();

        return index - 1;
    }

    private void optionSaveResults(Corporation corp, List<Worker> workers) {
        do {

            System.out.println("Do you want to save the results? (y/n)");
            Scanner input = new Scanner(System.in);
            char answer = Character.toLowerCase(input.nextLine().charAt(0));
            if (answer == 'y') {
                corp.saveSearchResults(workers);
                break;
            } else if (answer == 'n') {
                break;
            }
        } while (true);
    }
}
