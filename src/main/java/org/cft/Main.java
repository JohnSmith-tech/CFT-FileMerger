package org.cft;

import org.cft.files.FileSplitter;
import org.cft.files.merger.FileMerger;
import org.cft.settings.SetupSettings;
import org.cft.settings.enums.DataType;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        SetupSettings settings = new SetupSettings(args);
        FileSplitter fileSplitter = new FileSplitter(settings.getFiles(), settings.getDataType(), settings.getSortType());
        try {

            fileSplitter.splitAll();

            FileMerger merger = new FileMerger(fileSplitter.getTempDir(), settings.getSortType());

            if (settings.getDataType() == DataType.STRING) {
                merger.mergeFilesByString(settings.getOutfile().getName(), fileSplitter.countFilesInNewDir());
            } else {
                merger.mergeFilesByInteger(settings.getOutfile().getName(), fileSplitter.countFilesInNewDir());
            }

            merger.mergeFilesByString(settings.getOutfile().getName(), fileSplitter.countFilesInNewDir());

            fileSplitter.deleteDirectoryRecursion(fileSplitter.getTempDir());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}