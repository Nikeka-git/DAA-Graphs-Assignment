package org.tsybus.generator;

import org.tsybus.struct.MSTResult;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class CSVExporter {
    public static void writeResultsToCSV(String filename, List<MSTResult> results) {
        char separator = ';';
        DecimalFormat df = new DecimalFormat("#0.000");

        try (FileWriter writer = new FileWriter(filename)) {
            writer.append("Algorithm").append(separator)
                    .append("Vertices").append(separator)
                    .append("Edges").append(separator)
                    .append("Total Cost").append(separator)
                    .append("Comparisons").append(separator)
                    .append("Unions").append(separator)
                    .append("OtherOps").append(separator)
                    .append("Execution Time (ms)").append("\n");

            for (MSTResult r : results) {
                String timeFormatted = df.format(r.getTimeMs()) + " ms";
                writer.append(escape(r.getAlgorithm())).append(separator)
                        .append(String.valueOf(r.getVertexCount())).append(separator)
                        .append(String.valueOf(r.getEdgeCount())).append(separator)
                        .append(String.valueOf(r.getTotalCost())).append(separator)
                        .append(String.valueOf(r.getOps() != null ? r.getOps().getComparisons() : 0)).append(separator)
                        .append(String.valueOf(r.getOps() != null ? r.getOps().getUnions() : 0)).append(separator)
                        .append(String.valueOf(r.getOps() != null ? r.getOps().getOther() : 0)).append(separator)
                        .append("\"").append(timeFormatted).append("\"")
                        .append("\n");
            }
            System.out.println("CSV file created: " + filename);
        } catch (IOException e) {
            System.err.println("Error during creating CSV file: " + e.getMessage());
        }
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\"\"");
    }
}
