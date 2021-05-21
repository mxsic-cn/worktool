package cn.mxsic.seata;

/**
 * Function: AccountService <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-03-23 14:40:00
 */
public interface AccountService {


    /**
     * 从用户账户中借出
     */
    void debit(String userId, int money);
}
