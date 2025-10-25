package org.tsybus.struct;

public class OperationCounter {
    private long comparisons = 0;
    private long unions = 0;
    private long other = 0;

    public void incComparisons() { comparisons++; }
    public void addComparisons(long n) { comparisons += n; }
    public void incUnions() { unions++; }
    public void addUnions(long n) { unions += n; }
    public void incOther() { other++; }
    public void addOther(long n) { other += n; }

    public long getComparisons() { return comparisons; }
    public long getUnions() { return unions; }
    public long getOther() { return other; }

    public long totalOps() { return comparisons + unions + other; }

    @Override
    public String toString() {
        return "OperationCounter{" +
                "comparisons=" + comparisons +
                ", unions=" + unions +
                ", other=" + other +
                '}';
    }
}
