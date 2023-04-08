import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static Path currentPath = Paths.get("").toAbsolutePath();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        String command = "";

        while (!command.equals("exit")) {
            System.out.print(currentPath + "> ");
            command = scanner.nextLine().trim();

            String[] commandParts = command.split("\\s+");

            switch (commandParts[0]) {
                case "cd":
                    if (commandParts.length > 1) {
                        changeDirectory(commandParts[1]);
                    } else {
                        System.out.println("cd: missing operand");
                    }
                    break;
                case "ls":
                    listFiles();
                    break;
                case "pwd":
                    printWorkingDirectory();
                    break;
                case "cp":
                    if (commandParts.length > 2) {
                        copyFile(commandParts[1], commandParts[2]);
                    } else {
                        System.out.println("cp: missing operand");
                    }
                    break;
                case "exit":
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Command not found: " + commandParts[0]);
            }
        }
    }

    private static void changeDirectory(String path) {
        Path newDirectory = currentPath.resolve(path).normalize();

        if (Files.isDirectory(newDirectory)) {
            currentPath = newDirectory;
        } else {
            System.out.println("cd: " + path + ": No such file or directory");
        }
    }

    private static void listFiles() {
        try {
            Files.list(currentPath).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("ls: " + e.getMessage());
        }
    }

    private static void printWorkingDirectory() {
        System.out.println(currentPath);
    }

    private static void copyFile(String source, String destination) {
        Path sourcePath = currentPath.resolve(source).normalize();
        Path destinationPath = currentPath.resolve(destination).normalize();

        try {
            Files.copy(sourcePath, destinationPath);
        } catch (Exception e) {
            System.out.println("cp: " + e.getMessage());
        }
    }
}