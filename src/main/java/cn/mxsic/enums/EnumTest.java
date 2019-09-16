package cn.mxsic.enums;

/**
 * Function: EnumTest <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-09-16 15:55:00
 */
public class EnumTest {
    public static void main(String[] args) {

        for (IdentityTypeEnum identityTypeEnum : IdentityTypeEnum.values()) {
            System.out.println(identityTypeEnum.getName());
            System.out.println(identityTypeEnum.getCode());
        }
    }
}
