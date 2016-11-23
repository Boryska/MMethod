package math;

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

    public void checkResult() {
        System.out.println();
        System.out.println();
        System.out.println("FinalA");
        for (int i = 0; i < finalA.length; i++) {
            for (int j = 0; j < finalA[0].length; j++) {
                System.out.print(finalA[i][j].doubleValue() + "           ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("FinalFs");
        // Bubble Fs && X* SORT
//        for(int i = finalFs.length-1 ; i > 0 ; i--){
//            for(int j = 0 ; j < i ; j++){
//                if( finalFs[j] > finalFs[j+1] ){
//                    int tmpFs = finalFs[j];
//                    BigFraction tmpX = XI[j];
//                    finalFs[j] = finalFs[j+1];
//                    XI[j] = XI[j+1];
//                    finalFs[j+1] = tmpFs;
//                    XI[j+1] = tmpX;
//                }
//            }
//        }
        for (int x : finalFs) {
            System.out.print("A" + x + "     ; ");
        }
        System.out.println();
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

//    public static BigInteger findDet (BigFraction x [][]) {
//        if(x.length == 1){
//            return x[0][0].getNumerator();
//        }
//        else {
//            BigDecimal a[][] = new BigDecimal[x.length][x.length];
//            int n = x.length;
//            for (int i = 0; i < n; i++) {
//                for (int j = 0; j < n; j++) {
//                    a[i][j] = x[i][j].toBigDecimal();
//                }
//            }
//            try {
//
//                for (int i = 0; i < n; i++) {
//                    boolean nonzero = false;
//                    for (int j = 0; j < n; j++)
//                        if (a[i][j].compareTo(new BigDecimal(BigInteger.ZERO)) > 0)
//                            nonzero = true;
//                    if (!nonzero)
//                        return BigInteger.ZERO;
//                }
//
//                BigDecimal scaling[] = new BigDecimal[n];
//                for (int i = 0; i < n; i++) {
//                    BigDecimal big = new BigDecimal(BigInteger.ZERO);
//                    for (int j = 0; j < n; j++)
//                        if (a[i][j].abs().compareTo(big) > 0)
//                            big = a[i][j].abs();
//                    scaling[i] = (new BigDecimal(BigInteger.ONE)).divide
//                            (big, 100, BigDecimal.ROUND_HALF_EVEN);
//                }
//
//                int sign = 1;
//
//                for (int j = 0; j < n; j++) {
//
//                    for (int i = 0; i < j; i++) {
//                        BigDecimal sum = a[i][j];
//                        for (int k = 0; k < i; k++)
//                            sum = sum.subtract(a[i][k].multiply(a[k][j]));
//                        a[i][j] = sum;
//                    }
//
//                    BigDecimal big = new BigDecimal(BigInteger.ZERO);
//                    int imax = -1;
//                    for (int i = j; i < n; i++) {
//                        BigDecimal sum = a[i][j];
//                        for (int k = 0; k < j; k++)
//                            sum = sum.subtract(a[i][k].multiply(a[k][j]));
//                        a[i][j] = sum;
//                        BigDecimal cur = sum.abs();
//                        cur = cur.multiply(scaling[i]);
//                        if (cur.compareTo(big) >= 0) {
//                            big = cur;
//                            imax = i;
//                        }
//                    }
//
//                    if (j != imax) {
//
//                        for (int k = 0; k < n; k++) {
//                            BigDecimal t = a[j][k];
//                            a[j][k] = a[imax][k];
//                            a[imax][k] = t;
//                        }
//
//                        BigDecimal t = scaling[imax];
//                        scaling[imax] = scaling[j];
//                        scaling[j] = t;
//
//                        sign = -sign;
//                    }
//
//                    if (j != n - 1)
//                        for (int i = j + 1; i < n; i++)
//                            a[i][j] = a[i][j].divide
//                                    (a[j][j], 100, BigDecimal.ROUND_HALF_EVEN);
//
//                }
//
//                BigDecimal result = new BigDecimal(1);
//                if (sign == -1)
//                    result = result.negate();
//                for (int i = 0; i < n; i++)
//                    result = result.multiply(a[i][i]);
//
//                return result.divide
//                        (BigDecimal.valueOf(1), 0, BigDecimal.ROUND_HALF_EVEN).toBigInteger();
//
//            } catch (Exception e) {
//                return BigInteger.ZERO;
//            }
//        }
//    }
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

    public void AdmissibilityCheck(){
        //Проверка допустимости
        //Проверка неотрицательности
        //--------------------
        //Проверка выполнения условий задачи

        System.out.print("X* = { ");
        for (int i = 0; i < XI.length; i++) {
            System.out.print(XI[i].doubleValue() + ";  ");
        }
        System.out.println("}");
        System.out.println("Проверка выполнения условий задачи ");
        for(int i = 0; i < startA.length; i++){
            for(int j = 0; j < startA[0].length; j++){
                System.out.print("");
            }
            System.out.println();
        }
    }

    private BigFraction findDeterminant(BigFraction[][] m){

        BigFraction raschet[] = new BigFraction[m.length];
        BigFraction c [] = new BigFraction[m.length];
        BigFraction arr[][] = new BigFraction[m.length][m.length];
        BigFraction coef;
        int count = 0;
        for(int i=0;i<arr.length;i++)


            for (int j = 0; j < arr.length ; j++) {
                arr[i][j] = m[i][j];
            }

        for(int i=0;i<arr.length-1;i++){

            for (int j = i; j < arr.length -1  ; j++) {
                for (int k = 0; k < arr.length; k++) {
                        if(MMethod.compareTwoFraction(arr[j][j] , arr[j][j+1]) == -1 ){
                            System.arraycopy(arr[j], 0, c, 0, arr.length);
                            System.arraycopy(arr[j+1], 0, arr[j], 0, arr.length);
                            System.arraycopy(c, 0, arr[j+1], 0, arr.length);
                            count++;
                        }
                }
            }


            for (int j = 0; j < arr[i].length; j++)  {
                        raschet[j] = arr[i][j].divide(arr[i][i]);
        }
            for(int z=i+1;z<arr.length;z++) {
                coef=arr[z][i];
                for (int j = 0; j < arr[z].length; j++) {
                    arr[z][j] = arr[z][j].subtract(raschet[j].multiply(coef));
                }
            }

        }
        BigFraction ans = new BigFraction(1);
        for(int i=0;i<arr.length;i++){
            ans=ans.multiply(arr[i][i]);
        }
        System.out.println(count+"   -------");
        BigFraction znak = new BigFraction(-1).pow(count);
        return ans.multiply(znak);
    }

//    public static void main(String[] args) { // determinant test
//        BigFraction FFA[][] = {
//                {new BigFraction(-1), new BigFraction(0)},
//                {new BigFraction(-8), new BigFraction(1)}};
//        BigFraction ff = findDeterminant(FFA);
//        System.out.println(ff);
//    }

    public void OpornoCheck(){
        //Проверка опорности
        System.out.println("Проверка опорности решения");
        checkResult();
        AFs = getAFs();
        //det = findDet(AFs);
        det = findDeterminant(AFs);
        System.out.println();
        System.out.println("Детерминант равен  " + det);
        BigFraction[][] AfsObr = findAfsObr(AFs, det);
        for (int i = 0; i < AFs.length; i++) {
            for (int j = 0; j < AFs[0].length; j++) {
                System.out.print(AFs[i][j].intValue() + " ");
            }
            System.out.println();        }
        for (int i = 0; i < AfsObr.length ; i++) {
            for (int j = 0; j < AfsObr[0].length; j++) {
                System.out.print(AfsObr[i][j] + " ");
            }
            System.out.println();
        }

    }

    public void OptimalityCheck(){
        //Проверка оптимальности

    }
}
