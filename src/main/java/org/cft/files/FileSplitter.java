package org.cft.files;


import org.cft.settings.enums.DataType;
import org.cft.settings.enums.SortType;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileSplitter {

    List<File> files;


    private final int MAX_ROWS = 5000;

    private int newFilename = 0;

    private Path tempDir;
    private final DataType dataType;

    private final SortType sortType;

    public Path getTempDir() {
        return tempDir;
    }

    public FileSplitter(List<File> files, DataType dataType, SortType sortType) {
        this.files = files;
        this.dataType = dataType;
        this.sortType = sortType;
    }

    public void splitAll() throws IOException {

        try {
            String TEMP_SPLIT_DIR = "./TEMP_SPLIT_FILES";
            tempDir = Files.createDirectory(Path.of(TEMP_SPLIT_DIR));
        } catch (IOException e) {
            System.out.println("Delete or move this directory");
            System.exit(1);
        }

        for (File file : files) {
            splitFile(file);
        }
    }

    private void splitFile(File file) {
        List<String> list;

        String value;
        try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
            int countIteration = calculateCountIteration(countLines(file));

            int skipLine = 0;

            for (int j = 0; j < countIteration; j++) {

                list = new ArrayList<>();
                reader.setLineNumber(skipLine);
                for (int i = 0; i < MAX_ROWS; i++) {

                    if ((value = reader.readLine()) != null) {

                        if (!value.isEmpty()) {

                            if (!isErrorSort(list, value)) {
                                continue;
                            }

                            list.add(value);
                        }

                    } else {

                        break;
                    }
                }
                skipLine = reader.getLineNumber();

                if (!list.isEmpty()) {
                    createTempFile(new File(String.valueOf(newFilename++)), list);
                }

            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(1);
        }
    }


    public boolean isErrorSort(List<String> list, String value) {
        if (!list.isEmpty()) {

            if (dataType == DataType.INTEGER) {

                Integer tempValue = Integer.valueOf(value);

                if (sortType == SortType.ASC) {
                    return tempValue.compareTo(Integer.valueOf(list.get(list.size() - 1))) >= 0;

                } else {
                    return tempValue.compareTo(Integer.valueOf(list.get(list.size() - 1))) <= 0;
                }

            } else {
                if (sortType == SortType.ASC) {
                    return value.compareTo(list.get(list.size() - 1)) >= 0;
                } else {
                    return value.compareTo(list.get(list.size() - 1)) <= 0;
                }
            }
        }
        return true;
    }

    public void createTempFile(File file, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempDir + "/" + file.getName()))) {
            for (String s : data) {
                writer.write(s + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDirectoryRecursion(Path path) throws IOException {
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteDirectoryRecursion(entry);
                }
            }
        }
        Files.delete(path);
    }


    public int countFilesInNewDir() {
        File dir = new File(tempDir.toString());
        File[] arrFiles = dir.listFiles();
        if (arrFiles != null) {
            return arrFiles.length;
        }

        return 0;
    }

    private int calculateCountIteration(int fileCountRows) {

        int countIteration = 1;

        if (fileCountRows / MAX_ROWS > 0) {
            countIteration = fileCountRows / MAX_ROWS;

            if (fileCountRows % MAX_ROWS > 0) {
                countIteration += 1;
            }
        }
        return countIteration;
    }

    private int countLines(File file) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
            while ((reader.readLine()) != null) ;
            return reader.getLineNumber();
        }
    }
}
