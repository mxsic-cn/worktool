package cn.mysic.interw.goods;

import java.util.ArrayList;
import java.util.List;

import cn.mysic.interw.type.GoodsType;
import cn.mysic.interw.type.TaxForGoods;
import cn.mysic.interw.type.TaxType;

/**
 * Function: GoodsTax <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-11-30 12:47:00
 */
public class GoodsTaxUtil {

    public static List<GoodsTax> discountTax = new ArrayList<>();

    static {
        initTaxList(GoodsType.BOOKS, TaxType.SALES,true);
        initTaxList(GoodsType.DRUGS,TaxType.SALES,true);
        initTaxList(GoodsType.FOOD,TaxType.SALES,true);
    }

    public static double getGoodsTax(GoodsType goodsType, TaxType taxType){
        if (taxType.equals(TaxType.IMPORT)) {
            return taxType.getRate();
        }
        for (GoodsTax tax : discountTax) {
            if (tax.getGoodsType().equals(goodsType)) {
                for (TaxForGoods taxFotGoods : tax.getTaxForGoods()) {
                    if (taxFotGoods.getTaxType().equals(taxType)) {
                        if (taxFotGoods.isFree()) {
                            return 0;
                        }
                    }
                }
            }
        }
        return taxType.getRate();
    }
    private static void initTaxList(GoodsType goodsType,TaxType taxType,boolean free) {
         boolean add = true;
        TaxForGoods taxFotGoods = TaxForGoods.builder().taxType(taxType).free(free).build();
        for (GoodsTax goodsTax : discountTax) {
            if (goodsTax.getGoodsType().equals(goodsType)) {
                goodsTax.getTaxForGoods().add(taxFotGoods);
                add = false;
            }
        }
        if (add) {
            GoodsTax goodsTax = GoodsTax.builder().goodsType(goodsType).build();
            List<TaxForGoods> list = new ArrayList<>();
            list.add(taxFotGoods);
            goodsTax.setTaxForGoods(list);
            discountTax.add(goodsTax);
        }
    }


}
