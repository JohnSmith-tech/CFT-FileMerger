package org.cft.settings;


import org.cft.settings.enums.DataType;
import org.cft.settings.enums.SortType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SetupSettings {
    DataType dataType;
    SortType sortType = SortType.ASC;

    File outfile;
    List<File> files = new ArrayList<>();

    public SetupSettings(String[] args) {
        if (args.length == 0) {
            System.out.println("Lack of arguments");
            System.exit(0);
        }

        for (String arg : args) {
            switch (arg) {
                case "-i" -> {
                    dataType = DataType.INTEGER;
                }
                case "-s" -> {
                    dataType = DataType.STRING;
                }
                case "-a" -> {
                    sortType = SortType.ASC;
                }
                case "-d" -> {
                    sortType = SortType.DESC;
                }
                default -> {
                    files.add(new File(arg));
                }
            }
        }

        if (dataType == null) {
            System.out.println("Missing data type argument");
            System.exit(0);
        }
        if (files.size() < 2) {
            System.out.println("Missing data type argument");
            System.exit(0);
        }

        outfile = files.remove(0);
    }

    public DataType getDataType() {
        return dataType;
    }

    public File getOutfile() {
        return outfile;
    }

    public SortType getSortType() {
        return sortType;
    }

    public List<File> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "SetupSettings{" +
                "dataType=" + dataType +
                ", sortType=" + sortType +
                ", files=" + files +
                '}';
    }
}