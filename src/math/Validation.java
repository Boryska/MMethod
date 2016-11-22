package math;

/**
 * Created by Борис on 21.11.2016.
 */
public class Validation { //Проверка достоверности
    private BigFraction[][] AFs;
    private int finalFs[];
    private BigFraction det;
    private BigFraction[][] finalA;

    public Validation(){
        this.finalFs = MMethod.getFs();
        this.finalA = MMethod.getNewA();
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
        for(int i = finalFs.length-1 ; i > 0 ; i--){
            for(int j = 0 ; j < i ; j++){
                if( finalFs[j] > finalFs[j+1] ){
                    int tmp = finalFs[j];
                    finalFs[j] = finalFs[j+1];
                    finalFs[j+1] = tmp;
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
        for(int i = 0; i < Buf.length; i++){
            for(int j = 0; j < Buf[0].length; j++){
                Buf[i][j] = finalA[i][finalFs[j]];
                System.out.print(Buf[i][j].doubleValue() + "   ");
            }
            System.out.println();
        }
        return Buf;
    }

    private BigFraction findDeterminant(BigFraction[][] x){
        BigFraction subsidiaryArr[] = new BigFraction[x[0].length];
        BigFraction arr[][] = new BigFraction[x.length][x.length];
        for(int i=0;i<arr.length;i++)
            System.arraycopy(x[i], 0, arr[i], 0, x[i].length);
        BigFraction coef;
        for(int i=0;i<arr.length-1;i++){
            coef=arr[i][i];
            if(coef.doubleValue()==1){
                System.arraycopy(arr[i], 0, subsidiaryArr, 0, arr[i].length);
            }else {
                for (int j = 0; j < arr[i].length; j++) {
                    subsidiaryArr[j] = arr[i][j].divide(arr[i][i]);
                }
            }
            for(int z=i+1;z<arr.length;z++) {
                coef=arr[z][i];
                for (int j = 0; j < arr[z].length; j++) {
                    arr[z][j] = arr[z][j].subtract(subsidiaryArr[j].multiply(coef));
                }
            }

        }
        BigFraction ans = new BigFraction(1);
        for(int i=0;i<arr.length;i++){
            ans=ans.multiply(arr[i][i]);
        }
        return ans;
    }

    public void AdmissibilityCheck(){ //Проверка допустимости

    }

    public void OpornoCheck(){ //Проверка опорности
        checkResult();
        AFs = getAFs();
        det = findDeterminant(AFs);
        System.out.println();
        System.out.println("det = " + det);
    }

    public void OptimalityCheck(){ //Проверка оптимальности

    }
}
