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

    public void run(){
        // ТУТ САМ КОД АЛГОРИТМА

/*        //BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        //   int m,n,r = -1,k;
//        System.out.println("Количетсво ограничений:");
//        m = Integer.parseInt(read.readLine());
//        System.out.println("Количество переменных");
//        n = Integer.parseInt(read.readLine());
//        double A[][] = {
//                {190, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {1900, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,19, 0, 1, 0, 0,0, 0, 0, 0, 0, 0, 0, 0},
//                {1910, 7, 13, 8, 22, 9, 31, 27, 43, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {1910, 0, 13, 7, 22, 8, 31, 9, 27, 43, 30, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {1910, 0, 0, 1, 30, 13, 7, 22, 8, 31, 9, 27, 0, 0, 0, 0, 0, 0, 0, 43, 0, 0, 0, 0,1, 0, 0, 0, 0, 0, 0, 0},
//                {1910, 0, 27, 0, 0, 43, 1, 30, 7, 22, 8, 31, 9, 0, 0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 0,1, 0, 0, 0, 0, 0, 0},
//                {1910, 0, 0, 0, 0, 43, 27, 1, 30, 7, 22, 8, 31, 9, 0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 0, 0,1, 0, 0, 0, 0, 0},
//                {1910, 0, 0, 0, 0, 0, 1, 8, 22, 43, 27, 31, 9, 30, 13, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0,1, 0, 0, 0, 0},
//                {1910, 0, 0, 0, 0, 0, 0, 8, 22, 1, 43, 27, 9, 31, 7, 13, 0, 0, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0,1, 0, 0, 0},
//                {1910, 0, 1, 0, 0, 0, 0, 0, 0, 0, 8, 22, 43, 27, 9, 31, 7, 13, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0,1, 0, 0},
//                {1910, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 8, 22, 43, 27, 9, 31, 7, 13, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,1, 0},
//                {1910, 1, 22, 8, 43, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 31, 13, 7, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,1},
//                {-21190, -10, -79, -28, -122, -149, -105, -113, -168, -187, -159, -166, -136, -154, -71, -78, -99, -38, -39, -218, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0},
//                {0, -623, -1556, -3111, -5288, -8087, -11508, -15551, -20216, -25503, -31412, -37943, -45096, -52871, -61268, -70287, -79928, -90191, -101076, -112583, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0}};//new double [m+2][m+n+1];
//        boolean min = false;
//        double L[] = {0, 623, 1556, 3111, 5288, 8087, 11508, 15551, 20216, 25503, 31412, 37943, 45096, 52871, 61268, 70287, 79928, 90191, 101076, 112583};
//        //new double[n+1];
        for (int i = 0; i < m ; i++)
        {
            Fs0[i] = n+i;                         //////// Fs0
        }
        for (int i = 0; i < m ; i++)
        {
            Fs[i] = n+i;                          //////Fs
        }
//        System.out.println("Функция минимизируется?");
//        min = Boolean.parseBoolean(read.readLine());
//
//        System.out.println("Целевая функция: (кофы)");
//        for (int i = 1; i < n+1; i++)
//        {
//            L[i] = Double.parseDouble(read.readLine());
//        }
//

//        for (int i = 0; i < m; i++)
//        {
//            System.out.println("Введите "+(i+1)+" ограничение:");
//            for (int j = 1; j <n+1; j++)
//            {
//                System.out.println("X" + j);
//                A[i][j] = Double.parseDouble(read.readLine());
//            }
//            System.out.println("Равно ");
//            B[i] = Double.parseDouble(read.readLine());
//        }


//        for (int i = 0; i < B.length ; i++)
//        {
//            A[i][0] = B[i];                                 //////////// Заполнение вектора А0
//            if(B[i] < 0){
//                for (int j = 0; j <n+1; j++)
//                {
//                    A[i][j]*=-1;
//                }
//            }
//            }


//        for (int i = n+1; i < n+m+1 ; i++)
//        {
//            A[i-(n+1)][i] = 1;                              /////// Дополнение исскуственными переменными
//        }



        for (int i = 0; i < n+m+1; i++)
        {
            if(i<=n)  CjI[i] = 0;               ////////////   C'j
            else CjI[i] = -1;
        }

        for (int i = 0; i < n+m+1; i++)
        {
            if(i>n || i==0)  CjII[i] = 0;      ////////////   C''j
            else {  CjII[i] = L[i];}
        }

        for (int i = 0; i < m; i++)
        {
            CsI[i] = -1;                                       ////////////   C's      тут первая строчка идет как нулевая в матрице
        }
        for (int i = 0; i <m; i++)
        {
            CsII[i] = 0;                                      ////////////   C''s       тут первая строчка идет как нулевая в матрице
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
                if(A[i][j] != 0)
                    System.out.print(A[i][j]+"*X" + j+ " + ");                                               //////  Вывод основной задачи
            }
            System.out.println(" = " + B[i]);
        }                                                                                       ////////fin
        System.out.println("М-задача");                                               //////Start
        for (int i = 0; i < m; i++)                                                              //////
        {
            for (int j = 1; j <n+m+1 ; j++)
            {
                if(A[i][j] != 0)
                    System.out.print(A[i][j]+"*X" + j+ " + ");                                               //////  Вывод М задачи
            }
            System.out.println(" = " + A[i][0]);
        }                                                                                       ////////fin
        System.out.println("Вектор Cj'':");
        for (double x:CjI )
        {
            System.out.print(x + " | ");
        }
        System.out.println();
        System.out.println("Вектор Cj'':");
        for (double x:CjII )
        {
            System.out.print(x + " | ");
        }
        System.out.println("НАЧАЛО ИТЕРАЦИОННОГО ПРОЦЕССА");
        int count = 1;
        k = minimumK(alfa,betta);
        while ((alfa[k] * 1000000000 + betta[k]) < 0) //////////////////////////////////////////////////////////////////////
        {

            System.out.println(count+ " -я итерация");
            System.out.println("Номер направляющего столбца K = "+ (k));
            ArrayList<Integer> omega = new ArrayList<>();
            for (int i = 0; i < m; i++)
            {
                if (A[i][k] > 0)
                {
                    omega.add(i);                               //////////Нахождение положительных элементов
                }
            }
            if (omega.size() == 0){
                System.out.println("СИСТЕМА НЕОГРАНИЧЕННА");
                break;
            }
            else {
                double teta = 10000000.0;
                for (int i = 0; i < m; i++)
                {
                    if (A[i][k] > 0 && teta > A[i][0] / A[i][k])
                    {
                        teta = A[i][0] / A[i][k];                             /////Находим тета0 и устанавливаем р которое на ед меньше в силу того что я проебал этот момент
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
            System.out.println("Вектор Сs':");
            for (double x:CsI )
            {
                System.out.print(x + " | ");
            }
            System.out.println();
            System.out.println("Вектор Cs'':");
            for (double x:CsII )
            {
                System.out.print(x + " | ");
            }
            System.out.println();
            count++;
            for (int i = 0; i < n+m+1 ; i++)
            {
                if (!(A[m][i] - alfa[i] == 0)){
                    // System.out.println("ALFA FAIL");
                }
            }
            for (int i = 0; i < n+m+1 ; i++)
            {
                if (!(A[m+1][i] - betta[i] == 0)){
                    // System.out.println("Betta FAIL");
                }
            }
            System.out.println("Вектор alfa':");
            for (double x:alfa )
            {
                System.out.print(x + " | ");
            }
            System.out.println();
            System.out.println("Вектор betta':");
            for (double x:betta )
            {
                System.out.print(x + " | ");
            }
            System.out.println("Вектор базиса:");
            for (int x:Fs )
            {
                System.out.print("A"+x + " | ");
            }

            System.out.println();
            System.out.println("Оптимальное значение функции при заданных ограничениях равно: " + finaloo(CsII,A));

        }



    }


    public static double alfabetta(double[] Cs, double A[][], double Cj, int iter){
        double res = 0;
        for (int i = 0; i < Cs.length  ; i++)
        {
            res += Cs[i]*A[i][iter];                                    /////функция нахождения альфа подходит для нахождения бетты
        }


        return  Math.rint(eps *  (res - Cj)) / eps;
    }
    public static int minimumK (double[]a,double []b){
        double ma = a[1];
        for (int i = 1; i < a.length ; i++)
        {
            if(ma > a[i] && a[i] < 0)                          /////Нахождение минимального альфа
                ma = a[i];
        }
        ArrayList<Integer> mas = new ArrayList<Integer>();
        for (int i = 1; i < a.length; i++)
        {
            if (a[i] == ma){
                mas.add(i);             ////////Если несколько одинаковых альфа то сравниваем эти альфа по бетте
            }
        }

        if((mas.size() != 1) ) {
            int numb = mas.get(0);
            double mb = b[mas.get(0)];
            for (int i = 0; i < mas.size() ; i++)
            {
                if (mb > b[mas.get(i)]){                   ////////Нахождение минимального бетта
                    mb = b[mas.get(i)];
                    numb = mas.get(i);
                }
            }
            return numb;
        }
        else {
            return mas.get(0);
        }

    }
    public static double[][] findMatrix(double A[][],int stolb,int strok,int m, int n){
        double X[][] = new double [m+2][n+m+1];

        for (int i = 0; i < m+2 ; i++)
        {
            for (int j = 0; j < n+m+1 ; j++)                                //////////////
            {
                if(i != strok){ X[i][j] = A[i][j] - (A[i][stolb]* A[strok][j])/   (A[strok][stolb]); }
                else{           X[i][j] =   A[strok][j]/A[strok][stolb];
                }
                X[i][j] = Math.rint(eps * X[i][j]) / eps;                                       //// Округление
                if(X[i][j] < 0.0000001 && X[i][j] > -0.0000001 ){
                    X[i][j] = 0;
                }
            }
        }
        return  X;
    }
    public static double finaloo (double c[],double A[][]){
        double otvet = 0;
        for (int i = 0; i < c.length; i++)
        {
            otvet += c[i]*A[i][0];
        }
        return otvet;
    }*/
   }}