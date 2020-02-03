package com.github.jahwag;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        for(int j = 0; j<1000; j++) {
            new Bfs(Main.class.getResourceAsStream("/input"));
        }

        long end = System.currentTimeMillis() - start;
        System.out.println(String.format("Finished in %s s", (float)end / 1000f));
    }


}
