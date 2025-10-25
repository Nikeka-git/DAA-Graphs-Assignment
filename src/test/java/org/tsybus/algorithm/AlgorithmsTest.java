package org.tsybus.algorithm;

import org.junit.jupiter.api.Test;
import org.tsybus.struct.Graph;
import org.tsybus.struct.MSTResult;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmsTest {
    private Graph buildSmallGraph() {
        Graph g = new Graph();
        g.addEdge("A","B",3);
        g.addEdge("B","C",1);
        g.addEdge("A","C",2);
        return g;
    }

    @Test
    void TestStubs() throws Exception {
        Graph g = buildSmallGraph();

        Algorithm prim = new Prim();
        Algorithm kruskal = new Kruskal();

        MSTResult rPrim = prim.computeMST(g);
        MSTResult rKruskal = kruskal.computeMST(g);

        assertTrue(rPrim.getTotalCost() >= 0);
        assertTrue(rKruskal.getTotalCost() >= 0);
        assertEquals(rPrim.getTotalCost(), rKruskal.getTotalCost(),
                "Prim and Kruskal total cost must match");

        Path out = Path.of("target/test-results/metrics.csv");
        Files.createDirectories(out.getParent());
        try (PrintWriter pw = new PrintWriter(new FileWriter(out.toFile(), true))) {
            if (out.toFile().length() == 0) {
                pw.println("dataset,algorithm,totalCost,timeMs,comparisons,unions,other");
            }
            pw.printf("small1,%s,%d,%d,%d,%d,%d%n",
                    prim.name(),
                    rPrim.getTotalCost(),
                    rPrim.getTimeMs(),
                    rPrim.getOps() != null ? rPrim.getOps().getComparisons() : 0,
                    rPrim.getOps() != null ? rPrim.getOps().getUnions() : 0,
                    rPrim.getOps() != null ? rPrim.getOps().getOther() : 0
            );
            pw.printf("small1,%s,%d,%d,%d,%d,%d%n",
                    kruskal.name(),
                    rKruskal.getTotalCost(),
                    rKruskal.getTimeMs(),
                    rKruskal.getOps() != null ? rKruskal.getOps().getComparisons() : 0,
                    rKruskal.getOps() != null ? rKruskal.getOps().getUnions() : 0,
                    rKruskal.getOps() != null ? rKruskal.getOps().getOther() : 0
            );
        }

        Path jsonOut = Path.of("target/test-results/metrics.json");
        String json = String.format(
                "[{\"dataset\":\"small1\",\"algorithm\":\"%s\",\"totalCost\":%d,\"timeMs\":%d}," +
                        "{\"dataset\":\"small1\",\"algorithm\":\"%s\",\"totalCost\":%d,\"timeMs\":%d}]",
                prim.name(), rPrim.getTotalCost(), rPrim.getTimeMs(),
                kruskal.name(), rKruskal.getTotalCost(), rKruskal.getTimeMs()
        );
        Files.writeString(jsonOut, json);

    }

}
