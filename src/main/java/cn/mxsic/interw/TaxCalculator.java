package cn.mxsic.interw;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.mxsic.interw.goods.Goods;
import cn.mxsic.interw.goods.GoodsTaxUtil;
import cn.mxsic.interw.order.Order;
import cn.mxsic.interw.type.GoodsType;
import cn.mxsic.interw.type.TaxPriceUtil;
import cn.mxsic.interw.type.TaxType;

/**
 * Function: 销售税计算问题 <br>
 * # 销售税计算问题
 *
 * - 基本销售税对所有商品征收，税率是10%，但是书籍、食品和药品可以免征。
 * - 进口税是向所有进口商品征收的额外的税，税率5%，没有进口商品可以免征进口税。
 * - 当一个顾客采购物品时，他会收到一个收据，上面列出所有物品的总价，以及全部应付的税费。
 * - 税金需要上舍入到5分（0.05元）。比如，3.14应该上舍入成3.15, 2.48应该上舍入成2.50, 3.01上舍入成3.05。
 *
 * 需求：
 * 需要你实现计算出一份订单中所有物品的总税金和总价的功能。
 *
 * 1 请实现TaxCalculator.java的addProduct() 及 calculate()方法。
 *
 * 2 请创建需要的类来实现这个功能。你的实现应该符合面向对象设计的原则。
 *
 * 3 你的代码必须通过全部测试用例。测试用例已经包含在工程中，请不要修改。
 *
 * 4 编程时请留意代码的可扩展性。业务要求可能会有改变，比如免税的商品品种可能增加，
 * 或者有新的税种。我们希望你的实现对已有代码做尽可能小的改变就能支持这些扩展。
 *
 * @author: siqishangshu <br>
 * @date: 2018-11-30 12:17:00
 */
public class TaxCalculator {
    protected static List<Order> orders = new ArrayList<>();

    public static void addProduct(Order order) {
        orders.add(order);
    }

    public static void calculate(List<Order> orderList) {
        System.out.println("物品清单");
        if (orderList != null) {
            orders.addAll(orderList);
        }
        BigDecimal tax = new BigDecimal(0.0);
        BigDecimal total = new BigDecimal(0.0);
            for (Order order : orders) {
                for (TaxType taxType : TaxType.values()) {
                    double rate = GoodsTaxUtil.getGoodsTax(order.getGoods().getType(),taxType);
                    tax = tax.add(
                            order.getGoods().getPrice()
                                    .multiply(new BigDecimal(rate)).multiply(new BigDecimal(order.getCount())));
                }
                total = total.add(order.getGoods().getPrice())
                        .multiply(new BigDecimal(order.getCount()));
                System.out.println(order.toString());
            }
        System.out.println("所有物口总价值："+TaxPriceUtil.convert(total));
        System.out.println("需要支付税："+TaxPriceUtil.convert(tax));
    }

    public static void main(String[] args) {
        addProduct(Order.builder().goods(Goods.builder().name("书").price(new BigDecimal(10)).type(GoodsType.BOOKS).build()).count(41).build());
        addProduct(Order.builder().goods(Goods.builder().name("电子").price(new BigDecimal(40)).type(GoodsType.ELECTRONICS).build()).count(12).build());
        addProduct(Order.builder().goods(Goods.builder().name("药品").price(new BigDecimal(30)).type(GoodsType.DRUGS).build()).count(14).build());
        addProduct(Order.builder().goods(Goods.builder().name("食品").price(new BigDecimal(30)).type(GoodsType.FOOD).build()).count(12).build());
        addProduct(Order.builder().goods(Goods.builder().name("化学品").price(new BigDecimal(30)).type(GoodsType.CHEMICAL).build()).count(132).build());
        calculate(null);
    }
}
