package cn.siqishangshu.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 所有为null字段不给前端
 * @JsonIgnoreProperties(ignoreUnknown=true)
 * 选择忽略某些字段不给前端
 * @JsonIgnoreProperties({"isDeleted","password","credentialSalt","creatorId","creator","createTime","lastUpdateTime"})
 */
@JsonIgnoreProperties(value = {"isDeleted", "password", "credentialSalt", "creatorId", "creator", "createTime", "lastUpdateTime"})
public abstract class Entity implements Serializable {
}
