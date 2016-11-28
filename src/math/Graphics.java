package math;

import java.math.BigDecimal;
import java.util.ArrayList;


public class Graphics {
    private int firstdbetta;
    private int seconddbetta;
    private BigFraction[] startB;
    private BigFraction[][] obrAFs;
    ArrayList<Double> startX = new ArrayList();
    ArrayList<Double> finY = new ArrayList();

    public Graphics(int firstBetta, int secondBetta){
      this.firstdbetta = firstBetta;
        this.seconddbetta = secondBetta;
        this.startB = MMethod.getStartB();
        this.obrAFs = Validation.getObrAFs();
    }

    public void OblastUstoichevosti(){
        BigFraction [][] Ddb = new BigFraction[obrAFs.length][3];
        System.out.println();
        for (int i = 0; i < obrAFs.length; i++) {
            for (int j = 0; j < 3; j++) {
                if(j == 2){
                    Ddb[i][j] = MV(obrAFs,startB)[i].multiply(new BigFraction(-1));
                    System.out.print(new BigDecimal(Ddb[i][j].doubleValue()).setScale(7,BigDecimal.ROUND_FLOOR) + " ");
                }
                else if(j == 0) {
                    Ddb[i][j] = obrAFs[i][firstdbetta-1];
                    System.out.print(new BigDecimal(Ddb[i][j].doubleValue()).setScale(7,BigDecimal.ROUND_FLOOR) + " ");
                }
            else if( j == 1){
                    Ddb[i][j] = obrAFs[i][seconddbetta-1];
                    System.out.print(new BigDecimal(Ddb[i][j].doubleValue()).setScale(7,BigDecimal.ROUND_FLOOR) + " ");
                }
            }
            System.out.println();
        }
        for (int i = 0; i < Ddb.length; i++) {
            finY.add((Ddb[i][2].divide(Ddb[i][1])).doubleValue());
            startX.add((Ddb[i][2].divide(Ddb[i][0])).doubleValue());

                System.out.println("( " + startX.get(i) + " ; 0)     ( 0 ;" + finY.get(i) + " )") ;
        }
    }

    public BigFraction []MV( BigFraction [][] Matrix, BigFraction[]Vector ){
        BigFraction[] result = new BigFraction[Matrix.length];

        for (int i = 0; i < Matrix.length ; i++) {
            BigFraction sum = new BigFraction(0);
            for (int j = 0; j < Matrix[0].length; j++) {
                sum = sum.add(Matrix[i][j].multiply(Vector[j]));
            }
            result[i] = sum;
        }
        return result;
    }
}
