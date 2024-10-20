import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static String EXIT = "exit";
    private static Scanner sc;

    private static String readOrThrowExit(String label) throws Exception {
        System.out.print(label);
        String input = sc.nextLine();
        if(input.equalsIgnoreCase(EXIT))
            throw new ExitException();
        return input;
    }

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в программу! Введите <exit> (без скобочек), чтобы выйти");
        sc =  new Scanner(System.in);
        FileAnalyser fileAnalyser = new FileAnalyser();
        boolean isRun = true;
        while(isRun) {
            try {
                fileAnalyser.setInFile(readOrThrowExit("\nВведите путь до входного файла: "));
                fileAnalyser.setOutFile(readOrThrowExit("Введите путь до выходного файла: "));
                fileAnalyser.analyse();
            } catch (EmptyIOPathException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            catch (FileNotFoundException e) {
                System.out.println("Неверный путь до файла: " + e.getMessage());
            }
            catch (ExitException e) {
                isRun = false;
            }
            catch (Exception e) {
                System.out.println("Возникла непредвиденная ошибка: " + e.getMessage());
                isRun = false;
            }
        }
        System.out.println("Выход из программы...");
    }
}
