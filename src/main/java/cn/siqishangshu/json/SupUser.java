package cn.siqishangshu.json;


import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

;

/**
 * Created by LombokPlugin on 2018/12/19
 * Table:     sup_user
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SupUser extends Entity {
    /**
     *   自增型主键
     *   sup_user.id
     */
    private Integer id;

    /**
     *   编码
     *   sup_user.code
     */
    @NotBlank
    private String code;

    /**
     *   全名
     *   sup_user.name
     */
    private String name;

    /**
     *   密码
     *   sup_user.password
     */
    @Null
    private String password;

    /**
     *   加密盐
     *   sup_user.credentialSalt
     */
    @Null
    private String credentialSalt;

    /**
     *   电子邮箱
     *   sup_user.email
     */
    @NotBlank
    private String email;

    /**
     *   联系电话
     *   sup_user.mobilePhone
     */
    private String mobilePhone;

    /**
     *   是否系统用户 0：不是 1：是
     *   sup_user.isMaster
     */
    private Boolean isMaster;

    /**
     *   逻辑删除 1：删除 0：未删除
     *   sup_user.isDeleted
     */

    private Boolean isDeleted;

    /**
     *   用户锁定状态 1：被锁 0：未锁
     *   sup_user.isLocked
     */
    private Boolean isLocked;

    /**
     *   客户主账号ID
     *   sup_user.supplierId
     */
    @NotNull
    private Integer supplierId;

    /**
     *   用户头像
     *   sup_user.avatar
     */
    private String avatar;

    /**
     *   是否需要重新设置密码 0：不是 1：是
     *   sup_user.needResetPwd
     */
    private Boolean needResetPwd;
    /**
     *   记录创建时间
     *   sup_user.createTime
     */
    private Date createTime;

    /**
     *   记录最新一次修改时间
     *   sup_user.lastUpdateTime
     */
    private Date lastUpdateTime;
}