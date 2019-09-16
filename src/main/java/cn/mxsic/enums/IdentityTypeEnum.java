package cn.mxsic.enums;

/**
 * Function: IdentityTypeEnum <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-09-16 15:48:00
 */
public enum IdentityTypeEnum implements IEnum {

    CUSTOMER(0b000001, "客户"),

    SUPPLIER(0b000010, "供应商");
    /**
     * 编码
     */
    private int code;

    /**
     * 名称
     */
    private String name;

    IdentityTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
