package com.movie.kit.util;

public class TestData {
    static {
        System.out.println("Lokesh");
    }
    public static void main(String[] args) {
        int i = 10;
        TestData testData = new TestData();
        testData.extracted(i);
    }


    private Integer extracted(int i) {
        if(i == 10) {
            System.out.println("Data " + i);
        }
        if(i == 2) {
            System.out.println(i);
        }
        if(i == 3) {
            System.out.println(i);
        }

        return i;
    }
}
