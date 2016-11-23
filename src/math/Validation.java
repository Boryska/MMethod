package math;

import java.math.BigDecimal;
import java.math.BigInteger;
/**
 * Created by Борис on 21.11.2016.
 */
public class Validation { //Проверка достоверности
    private BigFraction[][] AFs;
    private int finalFs[];
    private BigInteger det;
    private BigFraction[][] startA;
    private BigFraction[][] finalA;
    private BigFraction[] startB, XI;

    public Validation(){
        this.finalFs = MMethod.getFs();
        this.finalA = MMethod.getNewA();
        this.startA = MMethod.getStartA();
        this.startB = MMethod.getStartB();
    }
    public void checkResult(){
        System.out.println();
        System.out.println();
        System.out.println("FinalA");
        for(int i = 0; i < finalA.length; i++){
            for(int j = 0; j < finalA[0].length; j++){
                System.out.print(finalA[i][j].doubleValue() + "           ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("FinalFs");
        XI = new BigFraction[finalFs.length];
        for(int i = 0; i < XI.length; i++){
            XI[i] = finalA[i][0];
        }
        // Bubble Fs && X* SORT
        for(int i = finalFs.length-1 ; i > 0 ; i--){
            for(int j = 0 ; j < i ; j++){
                if( finalFs[j] > finalFs[j+1] ){
                    int tmpFs = finalFs[j];
                    BigFraction tmpX = XI[j];
                    finalFs[j] = finalFs[j+1];
                    XI[j] = XI[j+1];
                    finalFs[j+1] = tmpFs;
                    XI[j+1] = tmpX;
                }
            }
        }
        for (int x: finalFs ) {
            System.out.print("A"+x+" ; ");
        }
        System.out.println();
    }

    public BigFraction[][] getAFs(){
        BigFraction[][] Buf = new BigFraction[finalA.length-2][finalFs.length];
        //System.out.print("{");
        for(int i = 0; i < Buf.length; i++){
            //System.out.print("{");
            for(int j = 0; j < Buf[0].length; j++){
                Buf[i][j] = finalA[i][finalFs[j]];
                System.out.print(Buf[i][j].doubleValue() + "   ");
                //System.out.print(Buf[i][j].intValue() + ",");
            }
            //System.out.print("},");
            System.out.println();
        }
        //System.out.print("}");
        return Buf;
    }

    public BigInteger findDet (BigFraction x [][]) {
        BigDecimal a[][] = new BigDecimal[x.length][x.length];
        int n = x.length;
        for(int i = 0 ; i < n ; i++){
            for(int j = 0 ; j < n ; j++){
                a[i][j] = x[i][j].toBigDecimal();
            }
        }
        try {

            for (int i=0; i<n; i++)
            {
                boolean nonzero = false;
                for (int j=0; j<n; j++)
                    if (a[i][j].compareTo (new BigDecimal (BigInteger.ZERO)) > 0)
                        nonzero = true;
                if (!nonzero)
                    return BigInteger.ZERO;
            }

            BigDecimal scaling [] = new BigDecimal [n];
            for (int i=0; i<n; i++)
            {
                BigDecimal big = new BigDecimal (BigInteger.ZERO);
                for (int j=0; j<n; j++)
                    if (a[i][j].abs().compareTo (big) > 0)
                        big = a[i][j].abs();
                scaling[i] = (new BigDecimal (BigInteger.ONE)) .divide
                        (big, 100, BigDecimal.ROUND_HALF_EVEN);
            }

            int sign = 1;

            for (int j=0; j<n; j++)
            {

                for (int i=0; i<j; i++)
                {
                    BigDecimal sum = a[i][j];
                    for (int k=0; k<i; k++)
                        sum = sum.subtract (a[i][k].multiply (a[k][j]));
                    a[i][j] = sum;
                }

                BigDecimal big = new BigDecimal (BigInteger.ZERO);
                int imax = -1;
                for (int i=j; i<n; i++)
                {
                    BigDecimal sum = a[i][j];
                    for (int k=0; k<j; k++)
                        sum = sum.subtract (a[i][k].multiply (a[k][j]));
                    a[i][j] = sum;
                    BigDecimal cur = sum.abs();
                    cur = cur.multiply (scaling[i]);
                    if (cur.compareTo (big) >= 0)
                    {
                        big = cur;
                        imax = i;
                    }
                }

                if (j != imax)
                {

                    for (int k=0; k<n; k++)
                    {
                        BigDecimal t = a[j][k];
                        a[j][k] = a[imax][k];
                        a[imax][k] = t;
                    }

                    BigDecimal t = scaling[imax];
                    scaling[imax] = scaling[j];
                    scaling[j] = t;

                    sign = -sign;
                }

                if (j != n-1)
                    for (int i=j+1; i<n; i++)
                        a[i][j] = a[i][j].divide
                                (a[j][j], 100, BigDecimal.ROUND_HALF_EVEN);

            }

            BigDecimal result = new BigDecimal (1);
            if (sign == -1)
                result = result.negate();
            for (int i=0; i<n; i++)
                result = result.multiply (a[i][i]);

            return result.divide
                    (BigDecimal.valueOf(1), 0, BigDecimal.ROUND_HALF_EVEN).toBigInteger();

        }
        catch (Exception e)
        {
            return BigInteger.ZERO;
        }

    }

    public void AdmissibilityCheck(){ //Проверка допустимости
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

    public void OpornoCheck(){ //Проверка опорности
        System.out.println("Проверка опорности решения");
        checkResult();
        AFs = getAFs();
        det = findDet(AFs);
        System.out.println();
        System.out.println("Детерминант равен  " + det);
    }

    public void OptimalityCheck(){ //Проверка оптимальности

    }
}
