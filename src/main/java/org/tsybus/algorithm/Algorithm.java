package org.tsybus.algorithm;

import org.tsybus.struct.MSTResult;
import org.tsybus.struct.Graph;

public interface Algorithm {

    MSTResult computeMST(Graph g);
    String name();
}
