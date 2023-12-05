package com.aoc.day3;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Exercise2 {

    private static int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

    public static void main(String[] args) {
        long sum = 0;
        Map<Point, Integer> map = new HashMap<>();
        Map<Point, Integer> counts = new HashMap<>();
        try {
            List<String> list = Files.readAllLines(Paths.get("day3/src/main/resources/com/aoc/day3/input.txt"));
            char[][] inputMap = new char[list.size()][];
            IntStream.range(0, list.size()).forEach(i -> inputMap[i] = list.get(i).toCharArray());
            for (int i = 0; i < inputMap.length; i++) {
                int num = 0;
                boolean isGear = false;
                Point gearCoord = new Point(-1, -1);
                for (int j = 0; j < inputMap[i].length; j++) {
                    int tmp = findNum(inputMap[i][j]);
                    if (tmp == -1) {
                        if (isGear) {
                            System.out.println("row " + (i + 1) + ": " + num + "; " + gearCoord);
                            map.put(gearCoord, map.getOrDefault(gearCoord, 1) * num);
                            counts.put(gearCoord, counts.getOrDefault(gearCoord, 0) + 1);
                            gearCoord = new Point(-1, -1);
                            isGear = false;
                        }
                        num = 0;
                    } else {
                        num = num * 10 + tmp;
                        for (int[] dir : directions) {
                            if (i + dir[0] >= 0 && i + dir[0] < inputMap.length && j + dir[1] >= 0 && j + dir[1] < inputMap[i + dir[0]].length) {
                                if (!isGear && inputMap[i + dir[0]][j + dir[1]] == '*') {
                                    gearCoord = new Point(i + dir[0], j + dir[1]);
                                    isGear = true;
                                }
                            }
                        }
                    }
                }
                if (isGear) {
                    map.put(gearCoord, map.getOrDefault(gearCoord, 1) * num);
                    counts.put(gearCoord, counts.getOrDefault(gearCoord, 0) + 1);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        for (Point p : map.keySet()) sum += counts.get(p) == 2 ? map.get(p) : 0;
        System.out.println(sum);
    }

    private static int findNum(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        return -1;
    }
}