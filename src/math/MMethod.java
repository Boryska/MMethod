package math;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
@XmlRootElement(name = "mmethod")
@XmlType(propOrder = {"c","a","b","extr"})
public class MMethod
{
    private BigFraction teta0, b[], c[], A[][];
    private int m, n;
    private int r = -1,k;
    private boolean extr;
    private int Fs0[] = new int[m];
    private int Fs[] = new int[m];
    private static double eps = 1000000000;
    private double CjI[] = new double[n+m+1];
    private double CjII[] = new double[n+m+1];
    private double CsI[] = new double[m];
    private double CsII[] = new double[m];

    double alfa[] = new double[n+m+1];
    double betta[] = new double[n+m+1];
    public MMethod(){}
    public MMethod(BigFraction[] c, BigFraction A[][], BigFraction b[], boolean extr){
        this.c=c;
        this.b=b;
        this.A=A;
        this.extr=extr;
        this.m = A.length;
        this.n = A[0].length;
    }
    public BigDecimal checkExtr (BigFraction[] x){
        BigFraction val = new BigFraction(0);
        for(int i=0;i<c.length;i++){
            if(extr) {
                val=val.add((c[i].multiply(new BigFraction(-1))).multiply(x[i]));
            }else{
                val=val.add(c[i].multiply(x[i]));
            }
        }
        return new BigDecimal(val.doubleValue()).setScale(6);
    }
    public boolean getExtr() {
        return extr;
    }
    public BigFraction[][] getA() {
        return A;
    }

    @XmlElement
    public void setA(BigFraction[][] a) {
        A = a;
    }

    @XmlElement
    public void setExtr(boolean extr) {
        this.extr = extr;
    }

    public BigFraction[] getC() {

        return c;
    }

    @XmlElement
    public void setC(BigFraction[] c) {
        this.c = c;
    }

    public BigFraction[] getB() {

        return b;
    }
    @XmlElement
    public void setB(BigFraction[] b) {
        this.b = b;
    }

    public void run(){

   }
}