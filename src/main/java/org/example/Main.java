package org.example;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        String folderPath = "/home/ivan/Desktop";
        File file = new File(folderPath);

        long start = System.currentTimeMillis();

        FolderSizeCalculator calculator =
                new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        Long size = pool.invoke(calculator);
        System.out.println(size);
        System.out.println(getHumanReadableSize(size));
        Long duration = System.currentTimeMillis() - start;
        System.out.println(duration + " ms.");
    }

    public static long getFolderSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            sum += getFolderSize(file);
        }
        return sum;
    }

    public static String getHumanReadableSize(long sum) {
        String humanReadable = "";
        if (sum <= 1_023L) {
            humanReadable = sum + "B";
        } else if (sum >= 1_024L && sum <= 1_048_575L) {
            humanReadable = sum / 1_024 + "Kb";
        } else if (sum >= 1_048_576L && sum <= 1_073_741_823L) {
            humanReadable = sum / 1_048_576 + "Mb";
        } else if (sum >= 1_073_741_824L && sum <= 1_099_511_627_775L) {
            humanReadable = sum / 1_073_741_824 + "Gb";
        } else if (sum >= 1_099_511_627_776L) {
            humanReadable = sum / 1_099_511_627_776L + "Tb";
        }
        return humanReadable;
    }

    public static Long getSizeFromHumanReadable(String humanReadable) {
        Long size = 0L;
        if (humanReadable.contains("B")) {
            size = Long.getLong(humanReadable.replace("B", ""));
        } else if (humanReadable.contains("Kb")) {
            size = Long.getLong(humanReadable.replace("Kb", "")) * 1_024L;
        } else if (humanReadable.contains("K")) {
            size = Long.getLong(humanReadable.replace("K", "")) * 1_024L;
        } else if (humanReadable.contains("Mb")) {
            size = Long.getLong(humanReadable.replace("Mb", "")) * 1_048_576L;
        } else if (humanReadable.contains("M")) {
            size = Long.getLong(humanReadable.replace("M", "")) * 1_048_576L;
        } else if (humanReadable.contains("Gb")) {
            size = Long.getLong(humanReadable.replace("Gb", "")) * 1_073_741_824L;
        } else if (humanReadable.contains("M")) {
            size = Long.getLong(humanReadable.replace("G", "")) * 1_073_741_824L;
        } else if (humanReadable.contains("Gb")) {
            size = Long.getLong(humanReadable.replace("Tb", "")) * 1_099_511_627_776L;
        } else if (humanReadable.contains("M")) {
            size = Long.getLong(humanReadable.replace("T", "")) * 1_099_511_627_776L;
        }
        return size;
    }
}