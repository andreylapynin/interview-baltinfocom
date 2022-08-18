package com.baltinfocom.interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class GroupManager {

    public void printResult(List<List<String>> groupsWithContent) {
        List<List<String>> sizeFromTwoElements = groupsWithContent.stream().filter(list -> list.size() > 1).collect(Collectors.toList());
        System.out.println("Групп с более чем одним элементом: " + sizeFromTwoElements.size());
        List<List<String>> sortedGroupsForSize = groupsWithContent.stream().sorted((o1, o2) -> {
            if (o1.size() > o2.size()) {
                return 1;
            } else if (o1.size() < o2.size()) {
                return -1;
            } else {
                return 0;
            }
        }).collect(Collectors.toList());
        long countGroup = 1;
        for (int i = sortedGroupsForSize.size(); i > 0; i--) {
            System.out.println("Группа " + countGroup);
            countGroup++;
            for (String line : sortedGroupsForSize.get(i - 1)) {
                System.out.println(line);
            }
        }

    }

    public List<List<String>> lineCombineIntoGroup(String fileName) throws IOException {
        Set<String> validFile = readFromFile(fileName);
        return sortingLines(validFile);
    }

    private Set<String> readFromFile(String fileName) throws IOException {
        BufferedReader br = Files.newBufferedReader(Paths.get(fileName));
        Set<String> originalLines = new LinkedHashSet<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (LineValidator.validateLine(line)) {
                originalLines.add(line);
            }
        }
        return originalLines;
    }

    private List<List<String>> sortingLines(Set<String> validLines) {
        class NewLineElement {
            private String lineElement;
            private int columnNum;

            private NewLineElement(String lineElement, int columnNum) {
                this.lineElement = lineElement;
                this.columnNum = columnNum;
            }

        }
        List<List<String>> groupsList = new ArrayList<>();
        List<Map<String, Integer>> columns = new ArrayList<>();
        Map<Integer, Integer> combineGroups = new HashMap<>();
        for (String line : validLines) {
            String[] lineElements = line.split(";");
            TreeSet<Integer> groupsDuplicateEl = new TreeSet<>();
            List<NewLineElement> newElements = new ArrayList<>();
            for (int elementInd = 0; elementInd < lineElements.length; elementInd++) {
                String curElement = lineElements[elementInd];
                if (columns.size() == elementInd) {
                    columns.add(new HashMap<>());
                }
                if ("\"\"".equals(curElement)) {
                    continue;
                }
                Map<String, Integer> currColumn = columns.get(elementInd);
                Integer elemGrNum = currColumn.get(curElement);
                if (elemGrNum != null) {
                    while (combineGroups.containsKey(elemGrNum)) {
                        elemGrNum = combineGroups.get(elemGrNum);
                    }
                    groupsDuplicateEl.add(elemGrNum);
                } else {
                    newElements.add(new NewLineElement(curElement, elementInd));
                }
            }
            int groupNumber;
            if (groupsDuplicateEl.isEmpty()) {
                groupsList.add(new ArrayList<>());
                groupNumber = groupsList.size() - 1;
            } else {
                groupNumber = groupsDuplicateEl.first();
            }
            for (NewLineElement newLineElement : newElements) {
                columns.get(newLineElement.columnNum).put(newLineElement.lineElement, groupNumber);
            }
            for (int matchedGrNum : groupsDuplicateEl) {
                if (matchedGrNum != groupNumber) {
                    combineGroups.put(matchedGrNum, groupNumber);
                    groupsList.get(groupNumber).addAll(groupsList.get(matchedGrNum));
                    groupsList.set(matchedGrNum, null);
                }
            }
            groupsList.get(groupNumber).add(line);
        }
        groupsList.removeAll(Collections.singleton(null));
        return groupsList;
    }

}