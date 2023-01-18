package org.cft.files.merger;

import org.cft.files.merger.elements.IntegerElement;
import org.cft.files.merger.elements.StringElement;
import org.cft.settings.enums.SortType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.PriorityQueue;
import java.util.Scanner;

public class FileMerger {
    private final Path pathDir;

    private final SortType type;

    public FileMerger(Path pathDir, SortType type) {
        this.pathDir = pathDir;
        this.type = type;
    }

    public void mergeFilesByInteger(String outputFileName, int countFiles) throws IOException {
        IntegerElement.type = type;
        Scanner[] scanners = new Scanner[countFiles];
        for (int i = 0; i < countFiles; i++) {
            String fileName = String.format("%d", i);
            scanners[i] = new Scanner(new File(pathDir + "/" + fileName));
        }

        IntegerElement[] elements = new IntegerElement[countFiles];
        PriorityQueue<IntegerElement> elementPriorityQueue = new PriorityQueue<>();

        int i;
        for (i = 0; i < countFiles; i++) {
            if (!scanners[i].hasNext()) {
                break;
            }
            String nextData = scanners[i].next();
            elements[i] = new IntegerElement(Integer.parseInt(nextData), i);
            elementPriorityQueue.add(elements[i]);
        }


        int count = 0;

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));

        while (count != i) {
            IntegerElement root = elementPriorityQueue.poll();

            writer.write(root.getElement() + "\n");

            if (!scanners[root.getIndex()].hasNext()) {
                root = elementPriorityQueue.poll();
                count++;
            } else {
                root.setElement(scanners[root.getIndex()].nextInt());
            }

            if (root != null) {
                elementPriorityQueue.add(root);
            }

        }


        for (int j = 0; j < countFiles; j++) {
            scanners[j].close();
        }

        writer.close();


    }

    public void mergeFilesByString(String outputFileName, int countFiles) throws IOException {
        StringElement.type = type;
        Scanner[] scanners = new Scanner[countFiles];
        for (int i = 0; i < countFiles; i++) {
            String fileName = String.format("%d", i);
            scanners[i] = new Scanner(new File(pathDir + "/" + fileName));
        }

        StringElement[] elements = new StringElement[countFiles];
        PriorityQueue<StringElement> stringElements = new PriorityQueue<>();

        int i;
        for (i = 0; i < countFiles; i++) {
            if (!scanners[i].hasNext()) {
                break;
            }
            String nextData = scanners[i].next();
            elements[i] = new StringElement((nextData), i);
            stringElements.add(elements[i]);
        }


        int count = 0;

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));

        while (count != i) {
            StringElement root = stringElements.poll();

            writer.write(root.getElement() + " ");

            if (!scanners[root.getIndex()].hasNext()) {
                root = stringElements.poll();
                count++;
            } else {
                root.setElement(scanners[root.getIndex()].next());
            }

            if (root != null) {
                stringElements.add(root);
            }

        }

        for (int j = 0; j < countFiles; j++) {
            scanners[j].close();
        }

        writer.close();


    }


}
