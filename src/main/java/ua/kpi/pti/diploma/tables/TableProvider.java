package ua.kpi.pti.diploma.tables;

import ua.kpi.pti.diploma.Type;
import ua.kpi.pti.diploma.tables.threads.DdtXorXorThread;
import ua.kpi.pti.diploma.tables.threads.TableThread;

import java.util.List;
import java.util.Map;

import static ua.kpi.pti.diploma.extender.SboxExtender.aList;
import static ua.kpi.pti.diploma.extender.SboxExtender.extendedSBox;
import static ua.kpi.pti.diploma.utils.Constants.*;
import static ua.kpi.pti.diploma.utils.Constants.CORES;

public abstract class TableProvider {
    protected String tableName;
    protected List<TableThread> threadPool;


    public int[][] getTable(int basis,Type type) {
        int[][] table = new int[Q][Q];
        if (type == Type.USUAL) {
            calculateWithMultiThreads(getThreadPool(table, sBoxUsusal[basis]));


        }
        if (type == Type.EXTENDED) {
            for (int a : aList) {
                for (int b = 0; b < Q; b++) {
                    calculateWithMultiThreads(getThreadPool(table,extendedSBox[basis][a][b]));
                }
            }
        }
        return table;

    }

    int maxInTable(int[][] matrix) {
        int max = 0;
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }
        return max;
    }

    void calculateWithMultiThreads(List<TableThread> threadPool) {
        threadPool.forEach(TableThread::start);

        for (TableThread thread : threadPool) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public abstract Map<Integer, Integer> calculateStatistics(List<Integer> basises, Type type);


    public String getTableName() {
        return tableName;
    }


    protected abstract List<TableThread> getThreadPool(int[][] table,int[] sbox);


}

