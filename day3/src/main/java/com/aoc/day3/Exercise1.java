package com.aoc.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

public class Exercise1 {
    private static int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

    public static void main(String[] args) throws IOException {
        long sum = 0;
        try {
            List<String> list = Files.readAllLines(Paths.get("day3/src/main/resources/com/aoc/day3/input.txt"));
            char[][] map = new char[list.size()][];
            IntStream.range(0, list.size()).forEach(i -> map[i] = list.get(i).toCharArray());
            for (int i = 0; i < map.length; i++) {
                int num = 0;
                boolean isNum = false;
                for (int j = 0; j < map[i].length; j++) {
                    int tmp = findNum(map[i][j]);
                    if (tmp == -1) {
                        if (isNum) {
                            sum += num;
                            isNum = false;
                        }
                        num = 0;
                    } else {
                        num = num * 10 + tmp;
                        for (int[] dir : directions) {
                            if (i + dir[0] >= 0 && i + dir[0] < map.length && j + dir[1] >= 0 && j + dir[1] < map[i + dir[0]].length) {
                                if (map[i + dir[0]][j + dir[1]] != '.' && findNum(map[i + dir[0]][j + dir[1]]) == -1)
                                    isNum = true;
                            }
                        }
                    }
                }
                if (isNum) {
                    sum += num;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(sum);
    }

    private static int findNum(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        return -1;
    }
}
