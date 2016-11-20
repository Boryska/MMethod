package math;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class TableM {


    private BigFraction alfa[], betta[], c[],CsI[],CsII[],teta[], A[][];
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
        for (int i = 0; i < Fs.length; i++) {
            if (Fs[i] < A[i].length - (A.length +1)) {
                CsI[i] = new BigFraction(0);
                CsII[i] = c[i];
            } else {
                CsI[i] = new BigFraction(-1);
                CsII[i] = new BigFraction(0);
            }
        }
    }
    @Override
    public String toString() {
        fillingTable();






        return "TableM{}";
    }
}
