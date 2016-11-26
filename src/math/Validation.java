package math;

import Jama.LUDecomposition;
import Jama.Matrix;


public class Validation {
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
        this.XI = new BigFraction[startA[0].length];
        for(int i = 0; i < startA[0].length; i++) {
            XI[i] = new BigFraction(0);
        }
        for(int i = 0; i < finalFs.length; i++){
            XI[finalFs[i]-1]= finalA[i][0];
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
        BigFraction[][] AfsObr = findAfsObr(AFs, det);
        BigFraction [] Cs = new BigFraction[finalFs.length];
        for (int i = 0; i < finalFs.length ; i++) {
            Cs[i] = startL[finalFs[i]-1];
        }
        BigFraction deltaI[] = new BigFraction[startA.length];
        BigFraction deltaJ[] = new BigFraction[startA[0].length];
        BigFraction YI [] = vectorMatrix(AfsObr,Cs);

        System.out.println("Проверка допустимости решения");
        System.out.print("X* ={");
        for (BigFraction x: XI) {
            System.out.print(x.doubleValue() + " ; ");
        }
        System.out.println("}\nОпределение Δi");
        for (int i = 0; i < startA.length; i++) {
            BigFraction sum = new BigFraction(0);
            StringBuilder sbi = new StringBuilder();
            sbi.append("Δ"+(i+1)+" = ");
            for (int j = 0; j <startA[0].length; j++) {
                sum = sum.add(startA[i][j].multiply(XI[j]));
               sbi.append( startA[i][j].doubleValue() + " * "+ XI[j].doubleValue()+" + ");
            }
             deltaI[i] = startB[i].subtract(sum);
            sbi.append("-" + startB[i].doubleValue() + " = " +deltaI[i].doubleValue() );
            System.out.println(sbi.toString());
        }
        BigFraction maxI = new BigFraction(-1);
        for (BigFraction x: deltaI) {
            if(MMethod.compareTwoFraction(x,maxI) == 1 )
             maxI = x;
        }
        System.out.println("maxΔi = " + maxI.doubleValue());
        System.out.print("Y* ={");
        for (BigFraction x: YI) {
            System.out.print(x.doubleValue() + " ; ");
        }
        System.out.println("}\nОпределение Δj");
        for (int i = 0; i < startA[0].length; i++) {
            BigFraction sum = new BigFraction(0);
            StringBuilder sbj = new StringBuilder();
            sbj.append("Δ"+(i+1)+" = ");
            for (int j = 0; j <startA.length; j++) {
                sum = sum.add(startA[j][i].multiply(YI[j]));
                sbj.append( startA[j][i].doubleValue() + " * "+ YI[j].doubleValue()+" + ");
            }
            deltaJ[i] = sum.subtract(startL[i]);
            sbj.append("-" + startL[i].doubleValue() + " = " +deltaJ[i].doubleValue() );
            System.out.println(sbj.toString());
        }
        BigFraction maxJ = new BigFraction(-1);
        for (BigFraction x: deltaJ) {
            if(MMethod.compareTwoFraction(x,maxJ) == 1 )
                maxJ = x;
        }
        System.out.println("maxΔj = " + maxJ.doubleValue());
    }

    public void OpornoCheck(){
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
        System.out.println("Проверка оптимальности решения");
        det = findDeterminant(AFs);
        BigFraction[][] AfsObr = findAfsObr(AFs, det);
        BigFraction [] Cs = new BigFraction[finalFs.length];
        for (int i = 0; i < finalFs.length ; i++) {
            Cs[i] = startL[finalFs[i]-1];
        }
        BigFraction YI [] = vectorMatrix(AfsObr,Cs);

        System.out.print("Оптимальный значение прямой задачи:\nL1* = ");
        BigFraction l1 = new BigFraction(0);
        String l11 ="";
        String l22 ="";
        for (int i = 0; i < startA[0].length ; i++) {
            l1= l1.add(XI[i].multiply(startL[i]));
            l11 += (XI[i].doubleValue() +" * "+ startL[i].doubleValue()+" + ");
        }
        System.out.print( l11 + " = ");
        System.out.println(l1.doubleValue());
        System.out.print("Оптимальный значение двойственной задачи:\nL2* = ");
        BigFraction l2 = new BigFraction(0);
        for (int i = 0; i < startA.length ; i++) {
            l2 = l2.add(YI[i].multiply(startB[i]));
            l22 += (YI[i].doubleValue() +" * "+ startB[i].doubleValue()+" + ");
        }
        System.out.print( l22 + " = ");
        System.out.println(l2.doubleValue());
        System.out.print("Оптимальное значение через Бетта 0:\nL3* = ");
        BigFraction l3 = finalA[finalA.length-1][0];
        if(finalA[finalA.length-1][0].signum() < 0)
            l3 = l3.multiply(new BigFraction(-1));
        System.out.println(l3.doubleValue());

        System.out.print("Среднее оптимальное значение:\nLср* = (" + l1.doubleValue() + " + "+ l2.doubleValue() + " + "+ l3.doubleValue() + ")\\3 = ");
        BigFraction avgl = l1.add(l2.add(l3)).divide(new BigFraction(3));
        System.out.println(avgl.doubleValue());
        System.out.println(l1.subtract(avgl).doubleValue() < 0.0000001 ? "L1* - Lср* = " + l1.doubleValue() + " - " + avgl.doubleValue() + " = " + l1.subtract(avgl).doubleValue() + " < 0.0000001" : "L1* - Lср* = " + l1.doubleValue() + " - " + avgl.doubleValue() + " = " + l1.subtract(avgl).doubleValue() + " >= 0.0000001");
        System.out.println(l2.subtract(avgl).doubleValue() < 0.0000001 ? "L2* - Lср* = " + l2.doubleValue() + " - " + avgl.doubleValue() + " = " + l2.subtract(avgl).doubleValue() + " < 0.0000001" : "L2* - Lср* = " + l2.doubleValue() + " - " + avgl.doubleValue() + " = " + l2.subtract(avgl).doubleValue() + " >= 0.0000001");
        System.out.println(l3.subtract(avgl).doubleValue() < 0.0000001 ? "L3* - Lср* = " + l3.doubleValue() + " - " + avgl.doubleValue() + " = " + l3.subtract(avgl).doubleValue() + " < 0.0000001" : "L3* - Lср* = " + l3.doubleValue() + " - " + avgl.doubleValue() + " = " + l3.subtract(avgl).doubleValue() + " >= 0.0000001");
    }
}
