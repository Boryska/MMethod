package math;

/**
 * Created by Админ on 26.11.2016.
 */
public class Graphics {
    private int firstdbetta;
    private int seconddbetta;
    private BigFraction[] startB;
    private BigFraction[][] obrAFs;

    public Graphics(int firstBetta, int secondBetta){
        this.firstdbetta = firstBetta;
        this.seconddbetta = secondBetta;
        this.startB = MMethod.getStartB();
        this.obrAFs = Validation.getObrAFs();
    }

}
