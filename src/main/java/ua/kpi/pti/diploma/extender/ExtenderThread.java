package ua.kpi.pti.diploma.extender;

import java.util.List;

import static ua.kpi.pti.diploma.extender.SboxExtender.extendedSBox;
import static ua.kpi.pti.diploma.utils.Constants.FIELD;
import static ua.kpi.pti.diploma.utils.Constants.Q;

public class ExtenderThread extends Thread {
    private final List<Integer> aList;
    private final int[][][][] table;
    private int start;
    private int end;

    ExtenderThread(int start1, int end1, int[][][][] table, List<Integer> aList) {

        this.start = start1;
        this.end = end1;
        this.aList = aList;
        this.table = table;
    }

    @Override
    public void run() {
        for (int base = start; base < end; base++) {
            for (int a : aList) {
                for (int b = 0; b < Q; b++) {
                    for (int power = 0; power < Q; power++) {
                        int x = (a * power + b) & 0xFF;
                        table[base][a][b][power] = FIELD.exp(base, x);
                    }
                }
            }
        }
    }
}
