# Minimum Spanning Tree (MST) Algorithms Analysis

Student: Tsybus Nikita, Group: SE-2421

## 1. Summary of Input Data and Results

This project compares two algorithms for finding a Minimum Spanning Tree - Prim and Kruskal.  
The input data consisted of randomly generated undirected weighted graphs with different sizes (from 5 to 2000 vertices).  
For each case, total cost, number of operations, and execution time were measured. 


## 2. Comparison of Prim and Kruskal Algorithms

### Theoretical overview
- Prim algorithm grows the MST by adding the cheapest edge connecting the current tree to a new vertex.  
  It works efficiently with dense graphs when implemented with a priority queue (O(E log V)).
- Kruskal’s algorithm sorts all edges and adds them one by one, avoiding cycles via a Union-Find structure.  
  It performs better on sparse graphs and has complexity O(E log E).

### Experimental results
The collected data confirms the theoretical expectations:

- For small graphs (<=10 vertices), both algorithms perform similarly due to low edge counts and overhead.
- As the graph grows denser, Prim algorithm consistently outperforms Kruskal in execution time.
- For very large graphs (1000-2000 vertices), Prim execution time remains lower, often by 30-50%.
- Kruskal algorithm requires significantly more comparisons and union operations, which explains the longer time.
- Total MST cost is identical across both algorithms, which shows both implementations are correct.

In general:
- Prim algorithm - faster on dense graphs, fewer operations.
- Kruskal algorithm - simpler to implement but slower when E > V.

---

## 3. Conclusions

From the experiments, we can make the following conclusions:

1. Efficiency - Prim algorithm scales better with graph density, while Kruskal algorithm slows down with a large number of edges due to sorting and union operations.
2. Implementation complexity - Kruskal algorithm is easier to implement conceptually, but requires efficient Union-Find data structure for good performance.
3. Practical recommendation -
    - For dense graphs or adjacency-matrix-based storage use Prim.
    - For sparse graphs or edge-list representations Kruskal may still be competitive.
4. Performance difference - In most medium to large cases, Prim algorithm executed roughly 1.5-3x faster.

---

