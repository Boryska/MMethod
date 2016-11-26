package math;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;

@XmlRootElement(name = "mmethod")
@XmlType(propOrder = {"c","a","b","extr"})
public class MMethod
{
    private BigFraction b[], c[], A[][];
    private static BigFraction newA[][], startA[][], startB[], startL[];
    private int m, n;
    private int r = -1,k;
    private boolean min;
    private static int Fs[];
    private BigFraction CjI[];
    private BigFraction CjII[];
    private BigFraction CsI[];
    private BigFraction CsII[];
    private BigFraction alfa[];
    private BigFraction betta[];
    private LinkedList<StringBuilder> listAnswer = new LinkedList<>();

    public MMethod(){}

    public MMethod(BigFraction[] c, BigFraction A[][], BigFraction b[], boolean extr){
        this.c=c;
        this.b=b;
        this.A=A;
        this.startA = A;
        this.startB = b;
        this.startL = c;
        this.min=extr;
        this.m = A.length;

        this.n = A[0].length;
    }

    public static BigFraction[] getStartL() {
        return startL;
    }

    public static BigFraction[][] getStartA() {
        return startA;
    }

    public static BigFraction[] getStartB() {
        return startB;
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

    public LinkedList<StringBuilder> getAnswer() {
        return listAnswer;
    }

    public static BigFraction[][] getNewA() {
        return newA;
    }

    public static int[] getFs() {
        return Fs;
    }

    public void run() throws Exception{

        Fs = new int[m];
        CjI = new BigFraction[n+m+1];
        CjII = new BigFraction[n+m+1];
        CsI = new BigFraction[m];
        CsII = new BigFraction[m];
        alfa = new BigFraction[n+m+1];
        betta = new BigFraction[n+m+1];
        StringBuilder usl = new StringBuilder();

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
        newA = MainTable(A,b);

        for (int i = 0; i < Fs.length ; i++)
        {
            Fs[i] = i+2+ newA[0].length - newA.length;                          //////Fs
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
        BigFraction L [] = new BigFraction[n+1];
        for (int i = 0; i < n+1  ; i++) {
            if(i==0)
                L[i]= new BigFraction(0);
            else{
                L[i] = c[i-1];
            }
        }
        for (int i = 0; i < (n+m+1); i++)
        {
            if(i>=n+1 || i==0) {
                CjII[i] = new BigFraction(0);  ////////////   C''j
            }
            else{  CjII[i] = L[i];}
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
            alfa[i]= alfabetta(CsI,newA,CjI[i],i);
            newA[m][i] = alfa[i];
        }

        for (int i = 0; i < n+m+1 ; i++)
        {
            betta[i]= alfabetta(CsII,newA,CjII[i],i);                /////////// Betta
            newA[m+1][i]  = betta[i] ;
        }

        System.out.println("Целевая функция");                                                  /////старт
        usl.append("Условия исходной задачи:\n");
        usl.append("Целевая функиця:\nL = ");
        StringBuilder usl1 = new StringBuilder();
        for (int i = 1; i <n+1; i++)
        {        if(i != n)     usl1.append(L[i].intValue()+"*X" + i + " + ");
          //  System.out.print(L[i]+"*X" + i + " + ");           //////////Красивый вывод целевой функции

        else  usl1.append(L[i].intValue()+"*X" + i);
            //System.out.print(L[i]+"*X" + i);
        }
        usl.append(usl1);
        if(min){                                                                                //////
            //System.out.println("->min");
            usl.append("->min\n");
        }                                                                                       /////
        else{ usl.append("->max\n");
            //System.out.println("->max");
            }                                                     //////фин
            //System.out.println();
        //System.out.println("Вектора ограничений");                                               //////Start
        usl.append("Вектора ограничений:\n");
        for (int i = 0; i < m; i++)                                                              //////
        {
            for (int j = 1; j <n+1 ; j++)
            {
                if(j!=n){
                if(!(compareTwoFraction(newA[i][j],new BigFraction(0))==0))
                   // System.out.print(newA[i][j]+"*X" + j+ " + ");
                    usl.append(newA[i][j].intValue()+"*X" + j+ " + ");
                }
                else{
                    if(!(compareTwoFraction(newA[i][j],new BigFraction(0))==0))
                        //System.out.print(newA[i][j]+"*X" + j);
                        usl.append(newA[i][j].intValue()+"*X" + j);
                }
            }
           // System.out.println(" = " + newA[i][0]);
            usl.append(" = " + newA[i][0].intValue() + "\n");
        }                                                                                       ////////fin
        //System.out.println("М-задача");                                               //////Start
        usl.append("\nМ-задача:"+"\nЦелевая функция М-задачи:\n"+ usl1 + " - M*(");
        for (int i = n+1; i < n+m+1 ; i++) {
                if(i != (n+m))
                    usl.append("X"+i+" + ");
                    else usl.append("X"+i);
        }
        usl.append(")");
        if(min){                                                                                //////

            usl.append("->min\n");
        }                                                                                       /////
        else{ usl.append("->max\n");

        }
        for (int i = 0; i < m; i++)                                                              //////
        {
            for (int j = 1; j <n+m+1 ; j++)
            {
                if(j!= (n+i+1)){
                    if(!(compareTwoFraction(newA[i][j],new BigFraction(0))==0))

                        usl.append(newA[i][j].intValue()+"*X" + j+ " + ");
                }
                else{
                    if(!(compareTwoFraction(new BigFraction(0),newA[i][j])==0))
                    usl.append(newA[i][j].intValue()+"*X" + j);
                }
            }
            // System.out.println(" = " + newA[i][0]);
            usl.append(" = " + newA[i][0].intValue() + "\n");
        }

//        System.out.println("Вектор Cj':");
//        for (BigFraction x:CjI )
//        {
//            System.out.print(x + " | ");
////        }
//        System.out.println();
//        System.out.println("Вектор Cj'':");
//        for (BigFraction x:CjII )
//        {
//            System.out.print(x + " | ");
//        }
//        System.out.println("Вектор Сs':");
//        for (BigFraction x:CsI )
//        {
//            System.out.print(x + " | ");
//        }
//        System.out.println();
//        System.out.println("Вектор Cs'':");
//        for (BigFraction x:CsII )
//        {
//            System.out.print(x + " | ");
//        }
//
//        System.out.println();
//        System.out.println("НАЧАЛО ИТЕРАЦИОННОГО ПРОЦЕССА");

        usl.append("\nНАЧАЛО ИТЕРАЦИОННОГО ПРОЦЕССА\n");
        int count = 1;
        k = minimumK(alfa,betta);
        for (int i = 0; i < m+2; i++)
        {
            for (int j = 0; j <n+m+1; j++)
            {
                System.out.print(newA[i][j].doubleValue()  + " | ");
            }
            System.out.println();
        }
        listAnswer.add(usl);
        while( ((alfa[k].multiply(1000000000).add(betta[k])).compareTo(new BigFraction(0))) < 0) //////////////////////////////////////////////////////////////////////
        {
            StringBuilder iterat = new StringBuilder();
            System.out.println(count+ " -я итерация");
            iterat.append(count+"-я итерация\n");
            System.out.println("Номер направляющего столбца K = "+ (k));
            boolean check = false;
            BigFraction tetaMas[] = new BigFraction[m];
            for (int j = 0;j < n+m+1; j++) {
                if(alfa[j].signum() == -1 || alfa[j].signum() == 0 && betta[j].signum() == -1)
                for (int i = 0; i < m; i++) {
                    if (!(compareTwoFraction(newA[i][j], new BigFraction(0)) == 0)) {
                        check = false;
                    }
                }
                if(check) break;
            }
            if (check){
                iterat.append("Внимание! Введенная система является неограниченной.");
                listAnswer.add(iterat);
                System.out.println("СИСТЕМА НЕОГРАНИЧЕННА");
                return;
            }
            else {
                BigFraction teta = new BigFraction(10000000);

                for (int i = 0; i < m; i++)
                {
                    if( compareTwoFraction(new BigFraction(0),newA[i][k]) ==-1 ){
                        tetaMas[i] = newA[i][0].divide(newA[i][k]);
                    }
                    if ((compareTwoFraction(newA[i][k],new BigFraction(0)) ==1) && ( compareTwoFraction(teta,newA[i][0].divide(newA[i][k])) ==1) )
                    {
                        teta = newA[i][0].divide(newA[i][k]);                             /////Находим тета0 и устанавливаем р которое на ед меньше в силу того что я проебал этот момент
                        r = i;                                   //// Но для матрицы все хорошо
                    }
                }
            }
            for (BigFraction x: tetaMas  ) {
                System.out.print(x + " ");
            }
            System.out.println();


            TableM raschet = new TableM(alfa,betta,c,tetaMas,newA,k,r,Fs);
            iterat.append(raschet.toString());
            iterat.append("\nТак как, существуют Δj <0 и все Ωj≠∅, имеет место ситуация 3 \nНаправляющий столбец: " + k +"-ый\nНаправляющая строка: " + (r+1)+"-ая\n\n");
            System.out.println("Номер направляющей строки R = "+ (r+1));
            iterat.append("Fs ->A" +Fs[r]+"\nA"+k+"->Fs\n\n");
            Fs[r] = k;                         ////// Замена условия в базисе
            CsI[r] = CjI[k] ;                      ////// Замена С's
            CsII[r] = CjII[k];                     ////// Замена С''s

            newA = findMatrix(newA,k,r,m,n);
            for (int i = 0; i < m+2; i++)
            {

                for (int j = 0; j <n+m+1; j++)
                {
                    System.out.print(newA[i][j].doubleValue()  + " | ");

                }
                System.out.println();
            }


            for (int i = 0; i < n+m+1 ; i++)
            {
                alfa[i]= alfabetta(CsI,newA,CjI[i],i);                /////////// Alfa

            }
            for (int i = 0; i < n+m+1 ; i++)
            {
                betta[i] = alfabetta(CsII, newA, CjII[i], i);                /////////// Betta

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

            listAnswer.add(iterat);
        }
        if(min){
            for(int i=0;i<c.length;i++){
                c[i]=c[i].multiply(new BigFraction(-1)); //c[i]*=-1;
            }
        }
        for (int i = 0; i < Fs.length ; i++) {
            if(Fs[i] > n){
                Fs[i] = (i+1);
            }
        }
        System.out.println(" " + finaloo(c,xOptimalniy(Fs,newA)).doubleValue() );
        StringBuilder ab = new StringBuilder();
        ab.append("Конечная таблица М-метода:\n");
        BigFraction[] tetaMas = new BigFraction[m];
        TableM raschet = new TableM(alfa,betta,c,tetaMas,newA,k,r,Fs);
        ab.append(raschet.toString());
        ab.append("Так как,все Δj >0 , имеет место ситуация 1\nФормируем решение:\n");
        ab.append("Fs* = {");

        for (int x: Fs ) {
            ab.append("A"+x+" ; ");
        }
        ab.append("}\n");
        ab.append("X* = {");
        for (BigFraction x:xOptimalniy(Fs,newA)) {
           // System.out.println(x);
            ab.append(new BigDecimal(x.doubleValue()).setScale(6,BigDecimal.ROUND_FLOOR)+" ; ");
        }
        ab.append("}\nОптимальное значение функции при заданных ограничениях равно: " + new BigDecimal(finaloo(c,xOptimalniy(Fs,newA)).doubleValue()).setScale(6,BigDecimal.ROUND_FLOOR));


        listAnswer.add(ab);
    }

    public BigFraction[] xOptimalniy(int[] fs,BigFraction[][] a){
        BigFraction[] xToReturn = new BigFraction[n];
        for(int i = 0; i < n; i++) {
            xToReturn[i] = new BigFraction("0");
        }
        for(int i = 0; i < fs.length; i++){
            xToReturn[fs[i]-1] = a[i][0];
        }
        return xToReturn;
    }

    public static BigFraction finaloo (BigFraction c[],BigFraction x[]){
        BigFraction otvet = new BigFraction(0);
        for (int i = 0; i < c.length; i++)
        {
            otvet = otvet.add(c[i].multiply(x[i]));
        }

         return otvet;
    }

    public static int compareTwoFraction(BigFraction fr1, BigFraction fr2){
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
    }

    public BigFraction[][] MainTable(BigFraction[][] a, BigFraction[] b){
    BigFraction l[][] = new BigFraction[m+2][m+n+1];
    for (int i = 0; i < m+2 ; i++) {
        for (int j = 0; j < m+n+1 ; j++) {
            if( j == (i + n+1))
                l[i][j] = new BigFraction(1);                           ///////////Заполнение ед матрицой
            else
                l[i][j] = new BigFraction(0);
        }
    }

    for (int i = 0; i < m ; i++) {
        l[i][0] = b[i];                                     //// Vector B
    }

    for (int i = 0; i < m ; i++) {
        for (int j = 1; j < n+1 ; j++) {
            l[i][j] = a[i][j-1];
        }
    }
    return l;
}
}