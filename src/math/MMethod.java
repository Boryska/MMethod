package math;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MMethod
{
    private BigFraction  b[], L[], A[][];
    private int m, n;
    private int r = -1,k;
    private boolean min;
    private int Fs0[] = new int[m];
    private int Fs[] = new int[m];
    private static double eps = 1000000000;
    private BigFraction CjI[] = new BigFraction[n+m+1];
    private BigFraction CjII[] = new BigFraction[n+m+1];
    private BigFraction CsI[] = new BigFraction[m];
    private BigFraction CsII[] = new BigFraction[m];

    BigFraction alfa[] = new BigFraction[n+m+1];
    BigFraction betta[] = new BigFraction[n+m+1];
    public MMethod(BigFraction[] L, BigFraction A[][], BigFraction b[], boolean min){
        this.L=L;
        this.b=b;
        this.A=A;
        this.min=min;
        this.m = A.length;
        this.n = A[0].length;
    }
    public BigDecimal checkExtr (BigFraction[] x){ //////////////Функция ответа
    BigFraction val = new BigFraction(0);
    for(int i=0;i<L.length;i++){
        if(min) {
            val=val.add((L[i].multiply(new BigFraction(-1))).multiply(x[i]));
        }else{
            val=val.add(L[i].multiply(x[i]));
        }
    }
    return new BigDecimal(val.doubleValue()).setScale(6);
}
    public BigFraction[] checkMin (BigFraction[] l){ //////////////Функция проверки минимума
            l = L;
        for(int i=0;i<L.length;i++){
            if(min) {
                l[i].multiply(new BigFraction(-1));
            }
        }
        return l;
    }


    public void run(){
        L = checkMin(L);
        for (int i = 0; i < m ; i++)
        {
            Fs0[i] = n+i;                         //////// Fs0
        }
        for (int i = 0; i < m ; i++)
        {
            Fs[i] = n+i;                          //////Fs
        }


        for (int i = 0; i < b.length ; i++)
        {
            A[i][0] = b[i];                                 //////////// Заполнение вектора А0
            if(b[i].signum() < 0){
                for (int j = 0; j <n+1; j++)
                {
                    A[i][j].multiply(new BigFraction(-1));
                }
            }
        }
        for (int i = 0; i <m; i++) {
            for (int j = n+1; j < n+m+1; j++) {
                 A[i][j]  = new BigFraction(0);                                                                      ///////////Заполняем все нулями что бы потом перекрыть единицами и получилась ед. матр.
            }
        }
        for (int i = n+1; i < n+m+1 ; i++)
        {
            A[i-(n+1)][i] = new BigFraction(1);             ///////////Заполнение Единицами
        }



        for (int i = 0; i < n+m+1; i++)
        {
            if(i<=n)  CjI[i] = new BigFraction(0);               ////////////   C'j
            else CjI[i] = new BigFraction(-1);
        }

        for (int i = 0; i < n+m+1; i++)
        {
            if(i>n || i==0)  CjII[i] = new BigFraction(0);      ////////////   C''j
            else {  CjII[i] = L[i];}
        }

        for (int i = 0; i < m; i++)
        {
            CsI[i] = new BigFraction(-1);                                       ////////////   C's      тут первая строчка идет как нулевая в матрице
        }
        for (int i = 0; i <m; i++)
        {
            CsII[i] = new BigFraction(0);                                      ////////////   C''s       тут первая строчка идет как нулевая в матрице
        }

        for (int i = 0; i < n+m+1 ; i++)
        {
            alfa[i]= alfabetta(CsI,A,CjI[i],i);                /////////// Alfa
            A[m][i] = alfa[i];
        }

        for (int i = 0; i < n+m+1 ; i++)
        {
            betta[i]= alfabetta(CsII,A,CjII[i],i);                /////////// Betta
            A[m+1][i] = betta[i];
        }
        System.out.println("Целевая функция");                                                  /////старт
        for (int i = 1; i <n+1; i++)
        {                                                                                       //////
            System.out.print(L[i]+"*X" + i + " + ");                                                         //////////Красивый вывод целевой функции
        }

        System.out.println("НАЧАЛО ИТЕРАЦИОННОГО ПРОЦЕССА");
        int count = 1;
        k = minimumK(alfa,betta);
        while( ((alfa[k] .multiply(1000000000).add(betta[k])).compareTo(new BigFraction(0))) < 0) //////////////////////////////////////////////////////////////////////
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
                        r = i;                                                  //// Но для матрицы все хорошо
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
                    System.out.print(A[i][j] + " | ");

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
        }
    }


    public static BigFraction alfabetta(BigFraction[] Cs, BigFraction A[][], BigFraction Cj, int iter){
        BigFraction res = new BigFraction (0);
        for (int i = 0; i < Cs.length  ; i++)
        {
            res.add(Cs[i].multiply(A[i][iter]));                                    /////функция нахождения альфа подходит для нахождения бетты
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
            if (compareTwoFraction(ma,a[i])==0){
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
    public static BigFraction finaloo (BigFraction c[],BigFraction A[][]){
        BigFraction otvet = new BigFraction(0);
        for (int i = 0; i < c.length; i++)
        {
            otvet.add(c[i].multiply(A[i][0]));
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

   }