import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelCopy {

    public static void main(String[] args) {
        String sourceFile1 = "src\\source1.txt";
        String sourceFile2 = "src\\source2.txt";
        String destFile1 = "src\\dest1.txt";
        String destFile2 = "src\\dest2.txt";

        ExecutorService executor = Executors.newFixedThreadPool(2); // два потока

        long startTime = System.nanoTime(); // замер времени

        executor.submit(() -> copyFile(sourceFile1, destFile1));
        executor.submit(() -> copyFile(sourceFile2, destFile2));

        executor.shutdown(); // завершение работы пула потоков
        while (!executor.isTerminated()) {
            // ожидание завершения всех задач
        }

        long endTime = System.nanoTime();
        System.out.println("Параллельное копирование заняло: " + (endTime - startTime) / 1_000_000 + " мс");
    }

    private static void copyFile(String source, String destination) {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(destination)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            System.out.println("Копирование файла " + source + " завершено.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
