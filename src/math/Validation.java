package math;

import Jama.LUDecomposition;
import Jama.Matrix;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Validation { //Проверка достоверности
    private BigFraction[][] AFs;
    private int finalFs[];

    private BigFraction det;
    private BigFraction[][] startA;
    private BigFraction[][] finalA;
    private BigFraction[] startB, startL, XI;

    public Validation() {
        this.finalFs = MMethod.getFs();
        this.finalA = MMethod.getNewA();
        this.startA = MMethod.getStartA();
        this.startB = MMethod.getStartB();
        this.startL = MMethod.getStartL();
        this.XI = new BigFraction[finalFs.length];
        for (int i = 0; i < XI.length; i++) {
            XI[i] = finalA[i][0];
        }
    }

    public BigFraction[][] getAFs() {
        BigFraction[][] Buf = new BigFraction[finalFs.length][finalFs.length];
        for (int i = 0; i < Buf.length; i++) {
            for (int j = 0; j < Buf[0].length; j++) {
                Buf[i][j] = startA[i][finalFs[j] - 1];
            }
        }
        return Buf;
    }

    public static BigFraction findDet (BigFraction x [][]) {
        BigFraction determinant = new BigFraction(0);
        for (int i = 0; i < x.length; i++) {
            //BigFraction mn = x[][];
            for (int j = 0; j < x.length; j++) {
                }
            }
            return determinant;
    }

    public BigFraction [][] findAfsObr(BigFraction [][] aMatrix , BigFraction det){
        BigFraction[][] result = new BigFraction [aMatrix.length][aMatrix[0].length];
        BigFraction[][] sss = new BigFraction [aMatrix.length-1][aMatrix[0].length-1];
        for (int i = 0; i < aMatrix.length ; i++) {/////Строки
            for (int j = 0; j < aMatrix[0].length; j++) {//////Столбцы
                for (int k = 0; k < aMatrix.length ; k++) {/////Строки
                    for (int l = 0; l < aMatrix[0].length; l++) {//////Столбцы
                        if( k < i && l < j){
                            sss[k][l] = aMatrix[k][l];
                        }
                        else if( k > i && l > j){
                            sss[k-1][l-1] = aMatrix[k][l];
                        }
                        else if( k < i && l > j){
                            sss[k][l-1] = aMatrix[k][l];
                        }
                        else if( k > i && l < j){
                            sss[k-1][l] = aMatrix[k][l];}
                        }
                }
                if ((i+j)%2 == 0) {
                    //result[j][i] = new BigFraction(findDet(sss)).divide(new BigFraction(det));
                    result[j][i] = findDeterminant(sss).divide(det);
                }
                else  {
                    //result[j][i] =   new BigFraction(findDet(sss)).divide(new BigFraction(det)).multiply(new BigFraction(-1));
                    result[j][i] = findDeterminant(sss).divide(det).multiply(new BigFraction(-1));
                }
            }
        }
        return result;
    }

    public BigFraction[] vectorMatrix(BigFraction[][] matrix, BigFraction[] vector){
        BigFraction[] result = new BigFraction[vector.length];
        BigFraction ssum;
        for (int i = 0; i < vector.length; i++) {
            ssum = new BigFraction(0);
            for (int j = 0; j < vector.length; j++) {
                 ssum = ssum.add(matrix[j][i].multiply(vector[j]));
            }
            result[i] = ssum;
        }
        return result;
    }

    public BigFraction[][] Minor ( BigFraction[][] matrix, int rowNum, int colNum ) {
        BigFraction[][] result = new BigFraction[matrix.length - 1][matrix[0].length - 1];

        for ( int i = 0; i < matrix.length; i++ ) {
            boolean isRowDeleted = rowNum < i;
            int resultRowIndex = isRowDeleted ? i - 1 : i;

            for ( int j = 0; j < matrix[i].length; j++ ) {
                boolean isColDeleted = colNum < j;
                int resultColIndex = isColDeleted ? j - 1 : j;

                if (rowNum != i && colNum != j)
                    result[resultRowIndex][resultColIndex] = matrix[i][j];
            }
        }
        return result;
    }

    public BigFraction findDeterminant(BigFraction[][] m) {
        double[][] A = new double[m.length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                A[i][j] = m[i][j].doubleValue();
            }
        }
        Matrix matrix = new Matrix(A);
        LUDecomposition dec = new LUDecomposition(matrix);
        det =  new BigFraction(dec.det());
        return det;
    }

    public void DopustimostCheck(){

        System.out.print("X* ={");
        for (BigFraction x: XI) {
            System.out.print(x.doubleValue() + " ; ");
        }
        System.out.println("}");

        AFs = getAFs();
        det = findDeterminant(AFs);
        System.out.println("Afs-1");
        BigFraction[][] AfsObr = findAfsObr(AFs, det);
        for (int i = 0; i < AfsObr.length ; i++) {
            for (int j = 0; j < AfsObr[0].length; j++) {
                System.out.print(AfsObr[i][j] + " ");
            }
            System.out.println();
        }
        BigFraction [] Cs = new BigFraction[finalFs.length];
        for (int i = 0; i < finalFs.length ; i++) {
            Cs[i] = startL[finalFs[i]-1];
            System.out.println(i+1 + " " + Cs[i]);
        }
        BigFraction YI [] = vectorMatrix(AfsObr,Cs);
        System.out.println("Оптимальный базис двойственной задачи");
        for (int i = 0; i < YI.length; i++) {
            System.out.print(YI[i]+ " ; ");
        }

        System.out.println();
    }

    public void OpornoCheck(){
        //Проверка опорности
        System.out.println("Проверка опорности решения");
        AFs = getAFs();
        det = findDeterminant(AFs);
        System.out.println("AFs*");
        for (int x: finalFs) {
            System.out.print("A"+x + " ");
        }
        System.out.println();
        for (int i = 0; i < AFs.length; i++) {
            for (int j = 0; j < AFs[0].length; j++) {
                System.out.print(AFs[i][j].intValue() + " ");
            }
            System.out.println();
        }
        System.out.println("Так как детерминант данной матарицы равен " + det.doubleValue()+ " и не равно 0, то вектора условий соответствующие базисным компонентам линейно независимы.");

    }

    public void OptimalityCheck(){
        //Проверка оптимальности
    }
}
