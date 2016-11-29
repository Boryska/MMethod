package math;

import java.math.BigDecimal;
import java.util.ArrayList;


public class Graphics {
    private int firstdbetta;
    private int seconddbetta;
    private BigFraction[] startB;
    private BigFraction[][] obrAFs;
    private ArrayList<Point> listPoint = new ArrayList();
    StringBuilder ustoichevost = new StringBuilder();

    public Graphics(int firstBetta, int secondBetta){
      this.firstdbetta = firstBetta;
        this.seconddbetta = secondBetta;
        this.startB = MMethod.getStartB();
        this.obrAFs = Validation.getObrAFs();
    }

    public void OblastUstoichevosti(){

        BigFraction [][] Ddb = new BigFraction[obrAFs.length+2][3];
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
            for (int j = i+1; j < Ddb.length; j++) {
                if(checktochek(Gaus(Ddb[i].clone() , Ddb[j].clone()),Ddb)){
                    listPoint.add(Gaus(Ddb[i].clone() , Ddb[j].clone()));
                    System.out.println((i+1) +" n " +(j+1));
                }
            }
        }
        for (Point x : listPoint){
            System.out.println(x.getX().doubleValue() +"    ;   "+ x.getY().doubleValue());
        }
    }

    public boolean checktochek(Point tochka ,  BigFraction[][] uslovia){
        boolean cheking = true;
        for (int i = 0; i < uslovia.length ; i++) {
            if(MMethod.compareTwoFraction(tochka.getX().multiply(uslovia[i][0]).add(tochka.getY().multiply(uslovia[i][1])),uslovia[i][2])== -1){
                cheking =false;
                break;
            }

        }
        return cheking;
    }
    public static Point Gaus(BigFraction[] f, BigFraction[] s) { /// не ставить в Ф уравнение типа (0 + б9 > -1910)
        BigFraction x;
        BigFraction y;
        BigFraction[] arr = new BigFraction[f.length];
        for (int i = 0; i < arr.length; i++){
            arr[i] = f[i].divide(f[0]);
            arr[i] = arr[i].multiply(s[0]);
         }
        for (int i = 0; i <arr.length ; i++) {
            s[i] = s[i].subtract(arr[i]);
        }
        y = s[2].divide(s[1]);
        x = f[1].multiply(y);
        x = f[2].subtract(x);
        x = x.divide(f[0]);
        return new Point(x,y);
    }


//    public static void main(String[] args) {
//
//        BigFraction[] test1 = {new BigFraction(0.0279656),new BigFraction(0.010635),new BigFraction(-17.598298)};
//        BigFraction[] test2 = {new BigFraction(0.007633),new BigFraction(0.0176601),new BigFraction(-14.767295)};
//        System.out.print(Gaus(test1,test2).getX().doubleValue() + " ;   "+ Gaus(test1,test2).getY().doubleValue() );
//    }

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
