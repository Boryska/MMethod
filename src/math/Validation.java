package math;

/**
 * Created by Борис on 21.11.2016.
 */
public class Validation { //Проверка достоверности
    private BigFraction[][] AFs;
    private BigFraction det;
    public void AdmissibilityCheck(){ //Проверка допустимости

    }

    private BigFraction findDeterminant(BigFraction[][] AFss){
        BigFraction subsidiaryArr[] = new BigFraction[AFss[0].length];
        BigFraction arr[][] = new BigFraction[AFss.length][AFss.length];
        for(int i=0;i<arr.length;i++)
            System.arraycopy(AFss[i], 0, arr[i], 0, AFss[i].length);
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
    public void OpornoCheck(){ //Проверка опорности
        //AFs = MMethod.getAFs();
        det = findDeterminant(AFs);
    }
    public void OptimalityCheck(){ //Проверка оптимальности

    }

}
