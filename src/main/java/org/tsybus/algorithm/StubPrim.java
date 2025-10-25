package org.tsybus.algorithm;

import org.tsybus.struct.MSTResult;
import org.tsybus.struct.OperationCounter;
import org.tsybus.struct.Graph;
import org.tsybus.struct.Edge;

import java.util.Collections;

public class StubPrim implements Algorithm {
    @Override
    public MSTResult computeMST(Graph g) {
        long start = System.nanoTime();
        MSTResult r = new MSTResult();
        r.setEdges(Collections.emptyList());
        r.setTotalCost(0);
        OperationCounter ops = new OperationCounter();
        ops.addOther(1);
        r.setOps(ops);
        long end = System.nanoTime();
        r.setTimeMs((end - start) / 1_000_000);
        return r;
    }

    @Override
    public String name(){
        return "StubKruskal";
    }
}
