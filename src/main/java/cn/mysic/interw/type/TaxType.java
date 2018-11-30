package cn.mysic.interw.type;

/**
 * Function: Tax <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-11-30 12:19:00
 */
public enum TaxType {
    SALES(0.05), IMPORT(0.10);
    private double rate;

    TaxType(double rate) {
        this.rate = rate;
    }
    public double getRate() {
        return rate;
    }
}
