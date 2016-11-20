package math;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.ArrayList;
@XmlRootElement(name = "mmethod")
@XmlType(propOrder = {"c","a","b","extr"})
public class MMethod
{
    private BigFraction b[], c[], A[][];
    private int m, n;
    private int r = -1,k;
    private boolean min;
    private int Fs0[];
    private int Fs[];
    private static double eps = 1000000000;
    private BigFraction CjI[];
    private BigFraction CjII[];
    private BigFraction CsI[];
    private BigFraction CsII[];
    BigFraction alfa[];
    BigFraction betta[];
    public MMethod(){}
    public MMethod(BigFraction[] c, BigFraction A[][], BigFraction b[], boolean extr){
        this.c=c;
        this.b=b;
        this.A=A;
        this.min=extr;
        this.m = 12; //A.length;
        this.n = 19;//A[0].length;
    }
    public boolean getExtr() {
        return min;
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
        this.min = extr;
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

//    public BigFraction[][] MainTable(BigFraction[][] a, BigFraction[] b){
//        BigFraction l[][];
//        l = new BigFraction[a.length+2][a[0].length+1];
//        for(int i = 0; i < a.length+2; i++){
//            for(int j = 0; j < a[0].length+1; j++){
//                if(j == 0 && i < a.length){
//                    l[i][j] =  b[i];
//                }
//                else if(j > a[0].length && i < a.length){
//                    if(j==a[0].length+i+1){
//                        l[i][j] = new BigFraction(1);
//                    }
//                    else{
//                        l[i][j] = new BigFraction(0);
//                    }
//
//                }
//                else if(j = )
//            }
//        }
//
//        return l;
//    }

    public void run() throws Exception{
        Fs0 = new int[m];
        Fs = new int[m];
        CjI = new BigFraction[n+m+1];
        CjII = new BigFraction[n+m+1];
        CsI = new BigFraction[m];
        CsII = new BigFraction[m];
        alfa = new BigFraction[n+m+1];
        betta = new BigFraction[n+m+1];


        if(min){
            for(int i=0;i<c.length;i++){
                c[i]=c[i].multiply(new BigFraction(-1)); //c[i]*=-1;
            }
        }
        for(int i=0;i<b.length;i++){
            if(b[i].signum()==-1){ //b[i]<0
                b[i].multiply(new BigFraction(-1)); //b[i]*=-1;
                for(int j=0;j<A[i].length;j++)
                    A[i][j].multiply(new BigFraction(-1)); //A[i][j]*=-1;
            }
        }
        BigFraction newA[][] = new BigFraction[A.length][A[0].length+b.length];
        for(int i=0;i<newA.length;i++){
            System.arraycopy(A[i],0,newA[i],0,A[i].length);
        }
        for(int i=0;i<newA.length;i++){
            for(int j=A[0].length; j<newA[0].length;j++) {
                if(j==A[0].length+i){
                    newA[i][j] = new BigFraction(1);
                }else {
                    newA[i][j] = new BigFraction(0);
                }
            }
        }
        A=newA;
        System.out.println("m = " + A.length);System.out.println("n = " + A[0].length);

        for (int i = 0; i < Fs0.length; i++)
        {
            Fs0[i] = i+A[0].length-Fs0.length;
            //Fs0[i] = n+i;                         //////// Fs0
        }
        for (int i = 0; i < Fs.length ; i++)
        {
            Fs[i] = i+A[0].length-Fs.length;                          //////Fs
        }
        for (int i = 0; i < (n+m+1); i++)
        {
            if(i<=n) {
                CjI[i] = new BigFraction(0);               ////////////   C'j
            }
            else {
                CjI[i] = new BigFraction(-1);
            }
        }
        System.out.println(m);
        System.out.println(n);
        for (int i = 0; i < (n+m+1); i++)
        {
            if(i>=n || i==0) {
                CjII[i] = new BigFraction(0);
            } ////////////   C''j
            else{  CjII[i] = c[i];}
        }
        for (int i = 0; i < m; i++)
        {
            CsI[i] = new BigFraction(-1);                                       ////////////   C's      тут первая строчка идет как нулевая в матрице
        }
        for (int i = 0; i < m; i++)
        {
            CsII[i] = new BigFraction(0);                                      ////////////   C''s       тут первая строчка идет как нулевая в матрице
        }

        for (int i = 0; i < (n+m+1) ; i++)
        {
            System.out.println(i);
            System.out.println("m = " + newA.length);
            System.out.println("n = " + newA[0].length);
            alfa[i]= alfabetta(CsI,A,CjI[i],i);
            System.out.println(i + " " + alfa[i]);
            newA[m][i] = alfa[i];
        }

        for (int i = 0; i < n+m+1 ; i++)
        {
            betta[i]= alfabetta(CsII,A,CjII[i],i);                /////////// Betta
            A[m+1][i]  = betta[i] ;
        }
        System.out.println("Целевая функция");                                                  /////старт
        for (int i = 1; i <n+1; i++)
        {                                                                                       //////
            System.out.print(c[i]+"*X" + i + " + ");                                                         //////////Красивый вывод целевой функции
        }
        if(min){                                                                                //////
            System.out.println("->min");
        }                                                                                       /////
        else{ System.out.println("->max");}                                                     //////фин
        System.out.println();
        System.out.println("Вектора ограничений");                                               //////Start
        for (int i = 0; i < m; i++)                                                              //////
        {
            for (int j = 1; j <n+1 ; j++)
            {
                if(!(compareTwoFraction(A[i][j],new BigFraction(0))==0))
                    System.out.print(A[i][j]+"*X" + j+ " + ");                                               //////  Вывод основной задачи
            }
            System.out.println(" = " + A[i][0]);
        }                                                                                       ////////fin
        System.out.println("М-задача");                                               //////Start
        for (int i = 0; i < m; i++)                                                              //////
        {
            for (int j = 1; j <n+m+1 ; j++)
            {
                if(compareTwoFraction(A[i][j],new BigFraction(0))==0)
                    System.out.print(A[i][j]+"*X" + j+ " + ");                                               //////  Вывод М задачи
            }
            System.out.println(" = " + A[i][0]);
        }                                                                                       ////////fin
        System.out.println("Вектор Cj':");
        for (BigFraction x:CjI )
        {
            System.out.print(x + " | ");
        }
        System.out.println();
        System.out.println("Вектор Cj'':");
        for (BigFraction x:CjII )
        {
            System.out.print(x + " | ");
        }
        System.out.println("Вектор Сs':");
        for (BigFraction x:CsI )
        {
            System.out.print(x + " | ");
        }
        System.out.println();
        System.out.println("Вектор Cs'':");
        for (BigFraction x:CsII )
        {
            System.out.print(x + " | ");
        }
        System.out.println();
        System.out.println("НАЧАЛО ИТЕРАЦИОННОГО ПРОЦЕССА");
        int count = 1;
        k = minimumK(alfa,betta);
        System.out.println(k);



        while( ((alfa[k].multiply(1000000000).add(betta[k])).compareTo(new BigFraction(0))) < 0) //////////////////////////////////////////////////////////////////////
        {

            System.out.println(count+ " -я итерация");
            System.out.println("Номер направляющего столбца K = "+ (k));
            ArrayList<Integer> omega = new ArrayList<>();
            for (int i = 0; i < m; i++)
            {

                if ( compareTwoFraction(A[i][k],new BigFraction(0)) ==1)

                {
                    omega.add(i);                               //////////Нахождение положительных элементов
                }
            }
            if (omega.size() == 0){
                System.out.println("СИСТЕМА НЕОГРАНИЧЕННА");
                break;
            }
            else {
                BigFraction teta = new BigFraction(10000000);
                for (int i = 0; i < m; i++)
                {
                    if ((compareTwoFraction(A[i][k],new BigFraction(0)) ==1) && ( compareTwoFraction(teta,A[i][0].divide(A[i][k])) ==1) )
                    {
                        teta = A[i][0].divide(A[i][k]);                             /////Находим тета0 и устанавливаем р которое на ед меньше в силу того что я проебал этот момент
                        r = i;                                   //// Но для матрицы все хорошо
                    }
                }
            }
            System.out.println("Номер направляющей строки R = "+ (r+1));
            Fs[r] = k;                         ////// Замена условия в базисе
            CsI[r] = CjI[k] ;                      ////// Замена С's
            CsII[r] = CjII[k];                     ////// Замена С''s
            A = findMatrix(A,k,r,m,n);
            for (int i = 0; i < m+2; i++)
            {

                for (int j = 0; j <n+m+1; j++)
                {
                    System.out.print(A[i][j].doubleValue()  + " | ");

                }
                System.out.println();
            }


            for (int i = 0; i < n+m+1 ; i++)
            {
                alfa[i]= alfabetta(CsI,A,CjI[i],i);                /////////// Alfa

            }
            for (int i = 0; i < n+m+1 ; i++)
            {
                betta[i] = alfabetta(CsII, A, CjII[i], i);                /////////// Betta

            }

            k = minimumK(alfa,betta);
            System.out.println("Вектор Сs':");
            for (BigFraction x:CsI )
            {
                System.out.print(x.doubleValue() + " | ");
            }
            System.out.println();
            System.out.println("Вектор Cs'':");
            for (BigFraction x:CsII )
            {
                System.out.print(x.doubleValue()  + " | ");
            }
            System.out.println();
            count++;

            System.out.println("Вектор alfa':");
            for (BigFraction x:alfa )
            {
                System.out.print(x.doubleValue()  + " | ");
            }
            System.out.println();
            System.out.println("Вектор betta':");
            for (BigFraction x:betta )
            {
                System.out.print(x.doubleValue()  + " | ");
            }
            System.out.println("Вектор базиса:");
            for (int x:Fs )
            {
                System.out.print("A"+x + " | ");
            }

            System.out.println();
            k = minimumK(alfa,betta);
            System.out.println(k+"--------------------");
            System.out.println("Оптимальное значение функции при заданных ограничениях равно: " + finaloo(CsII,A).doubleValue() );
        }
    }


    public static BigFraction finaloo (BigFraction c[],BigFraction A[][]){
        BigFraction otvet = new BigFraction(0);
        for (int i = 0; i < c.length; i++)
        {
            otvet = otvet.add(c[i].multiply(A[i][0]));
        }
        return otvet;
    }
    private static int compareTwoFraction(BigFraction fr1, BigFraction fr2){
        if(fr1.signum()>fr2.signum()){
            return 1;
        }else if(fr1.signum()<fr2.signum()){
            return -1;
        }else{
            if(fr1.getDenominator()==fr2.getDenominator()){
                return fr1.getNumerator().compareTo(fr2.getNumerator());
            }else{
                return (fr1.getNumerator().multiply(fr2.getDenominator())).compareTo((fr2.getNumerator().multiply(fr1.getDenominator())));
            }
        }

    }
    public static BigFraction[][] findMatrix(BigFraction A[][],int stolb,int strok,int m, int n){
        BigFraction X[][] = new BigFraction [m+2][n+m+1];

        for (int i = 0; i < m+2 ; i++)
        {
            for (int j = 0; j < n+m+1 ; j++)                                //////////////
            {
                if(i != strok){
                    X[i][j] = A[i][j].subtract(A[i][stolb].multiply(A[strok][j]).divide((A[strok][stolb])));
                }
                else{           X[i][j] =   A[strok][j].divide(A[strok][stolb]);
                }
            }
        }
        return  X;
    }
    public static BigFraction alfabetta(BigFraction[] Cs, BigFraction A[][], BigFraction Cj, int iter){
        BigFraction res = new BigFraction (0);
        for (int i = 0; i < Cs.length; i++)
        {
            res = res.add(Cs[i].multiply(A[i][iter]));                                    /////функция нахождения альфа подходит для нахождения бетты
        }
        return  res.subtract(Cj);
    }
    public static int minimumK (BigFraction[]a,BigFraction []b){
        BigFraction ma = a[1];
        for (int i = 1; i < a.length ; i++)
        {
            if(compareTwoFraction(ma,a[i])==1 && compareTwoFraction(new BigFraction(0),a[i]) == 1)                          /////Нахождение минимального альфа
                ma = a[i];
        }
        ArrayList<Integer> mas = new ArrayList<Integer>();
        for (int i = 1; i < a.length; i++)
        {
            if (compareTwoFraction(ma,a[i])==0)
            {
                mas.add(i);             ////////Если несколько одинаковых альфа то сравниваем эти альфа по бетте
            }
        }
        if((mas.size() > 1) || mas.size() == 0 ) {
            int numb = mas.get(0);
            BigFraction mb = b[mas.get(0)];
            for (int i = 0; i < mas.size() ; i++)
            {
                if (compareTwoFraction(mb , b[mas.get(i)]) == 1){                   ////////Нахождение минимального бетта
                    mb = b[mas.get(i)];
                    numb = mas.get(i);
                }
            }
            return numb;
        }
        else  {
            return mas.get(0);
        }
    }}