package math;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class TableM {


    private BigFraction alfa[], betta[], c[],CsI[],CsII[],CjI[],CjII[],teta[], A[][];
    private int k,r,Fs[];
    public TableM(BigFraction[] alfa, BigFraction[] betta, BigFraction[] c, BigFraction[] teta, BigFraction[][] a, int k, int r,int[]Fs) {
        this.alfa = alfa;
        this.betta = betta;
        this.c = c;
        this.teta = teta;
        A = a;
        this.k = k;
        this.r = r;
        this.Fs = Fs;
    }
    public BigFraction[] getAlfa() {
        return alfa;
    }
    public BigFraction[] getBetta() {
        return betta;
    }
    public BigFraction[] getC() {
        return c;
    }
    public BigFraction[] getCsI() {
        return CsI;
    }
    public BigFraction[] getCsII() {
        return CsII;
    }
    public void setAlfa(BigFraction[] alfa) {
        this.alfa = alfa;
    }
    public void setBetta(BigFraction[] betta) {
        this.betta = betta;
    }
    public void setC(BigFraction[] c) {
        this.c = c;
    }
     public void setTeta(BigFraction[] teta) {
        this.teta = teta;
    }
    public void setA(BigFraction[][] a) {
        A = a;
    }
    public void setK(int k) {
        this.k = k;
    }
    public void setR(int r) {
        this.r = r;
    }
    public BigFraction[] getTeta() {
        return teta;
    }
    public BigFraction[][] getA() {
        return A;
    }
    public int getK() {
        return k;
    }
    public int getR() {
        return r;
    }
    public int[] getFs() {
        return Fs;
    }
    public void setFs(int[] fs) {
        Fs = fs;
    }


    private void fillingTable() {
        CsI = new BigFraction[Fs.length];
        CsII = new BigFraction[Fs.length];
        CjI = new BigFraction[A[0].length + A.length];
        CjII = new BigFraction[A[0].length + A.length];

        for (int i = 0; i < Fs.length; i++) {
            if (Fs[i] < A[i].length - (A.length -2)) {
                CsI[i] = new BigFraction(0);
                CsII[i] = c[i];
            } else {
                CsI[i] = new BigFraction(-1);
                CsII[i] = new BigFraction(0);
            }
        }
        for (int i = 0; i < A[0].length + A.length; i++) {
            if (i <(A[0].length - A.length)) {
                CjI[i] = new BigFraction(0);
                CjII[i] = c[i];
            }
            else {
                CjI[i] = new BigFraction(-1);
                CjII[i] = new BigFraction(0);
            }
        }

    }
    @Override
    public String toString() {
        fillingTable();
        StringBuilder mas = new StringBuilder();
        StringBuilder format = new StringBuilder();
        Object [][] mainTable = new Object[A.length+3][A[0].length+5];
        for(int i=0;i<A[0].length+5;i++){
            if(i==0){
                format.append("%"+(i+1)+"$-3.2s |");
            }else{
                format.append("%"+(i+1)+"$-"+((int) (5*3)+4)+"."+(6)+"s |");
            }
        }
        for (int i = 0; i < mainTable[0].length -1 ; i++) {
            if(  i == 4){
                mainTable[0][i] = "C`j";
                mainTable[1][i] = "C``j";
            }
            else if(i > 4){
                mainTable[0][i]=new BigDecimal(CjI[i-4].intValue()).setScale(6,RoundingMode.FLOOR);
                mainTable[1][i]=new BigDecimal(CjII[i-4].intValue()).setScale(5,RoundingMode.FLOOR);
            }
        }
        Object [] ogl = new Object[mainTable[0].length ];
        ogl[0] = "№";
        ogl[1] = "Cs`";
        ogl[2] = "Cs``";
        ogl[3] = "Fs";
        ogl[ogl.length -1] = "Θ";
        for (int i = 4; i < ogl.length-1 ; i++) {
            ogl[i]= ("A"+(i-4));
        }
        for (int i = 0; i < mainTable[0].length  ; i++) {
         mainTable[2][i] = ogl[i];
        }
        for (int i = 3; i < mainTable.length-2 ; i++) {
            mainTable[i][0] = (i-2);
            mainTable[i][1] = CsI[i-3].intValue();
            mainTable[i][2] = CsII[i-3].intValue();
            mainTable[i][3] = Fs[i-3];
            mainTable[i][mainTable[0].length-1] = teta[i-3];
            mainTable[i+1][0] = (i - 1) ;
            mainTable[i+2][0] = i;


        }
        for (int i = 3; i < mainTable.length; i++) {
            for (int j = 4; j < mainTable[0].length; j++) {
                if(j!=mainTable[0].length-1){
                    mainTable[i][j] = A[i-3][j-4].doubleValue();
                }
            }
        }
        for (int i = 0; i < mainTable.length; i++) {
            for (int j = 0; j < mainTable[0].length; j++) {
                if(mainTable[i][j] == null){
                    mainTable[i][j] = "";
                }
            }
        }

        for (int i = 0; i < mainTable.length ; i++) {
            mas.append(String.format(format.toString(), mainTable[i]));
            mas.append("\n");
        }

        return mas.toString();
    }
}
