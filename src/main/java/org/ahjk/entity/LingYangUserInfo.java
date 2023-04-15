package org.ahjk.entity;


import java.io.Serializable;
import java.util.List;

public class LingYangUserInfo implements Serializable {

    // 用户ID
    private String userId;

    //姓名（当前登录用户名称）
    private String name;

    //手机号（当前登录用户手机号）
    private String phone;

    //公司名
    private String orgName;

    //统一社会信用代码
    private String creditCode;

    //管理员 0 非管理员 1 管理员
    private String manager;

    //使用方式：0试用、1购买
    private Integer accountStatus;

    //组织成员列表（该组织下，具有使用权限的成员列表信息。该列表信息非固定）
    private List<OrgMember> orgMembers;

    //组织订单信息（用户所属组织购买的全部订单信息，针对当前应用。该列表信息非固定）
    private List<OrgOrder> orgOrders;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<OrgMember> getOrgMembers() {
        return orgMembers;
    }

    public void setOrgMembers(List<OrgMember> orgMembers) {
        this.orgMembers = orgMembers;
    }

    public List<OrgOrder> getOrgOrders() {
        return orgOrders;
    }

    public void setOrgOrders(List<OrgOrder> orgOrders) {
        this.orgOrders = orgOrders;
    }

    public class OrgMember {
        //成员名字
        private String name;
        //成员手机
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public class OrgOrder {
        //规格名称：服务商配置的规格名称
        private String specs;
        //购买数量：该笔订单，用户购买的应用规格数量
        private Integer amount;
        //订单成功时间
        private String orderTime;
        //规格类型 1-元/年 2-元/套 3-元/次
        private Integer specType;
        //该规格最大使用用户数，-1为无限制。来自服务商配置的商品规格数据
        private Integer userNum;
        //该规格最大使用年限，单位年 -1为无限制。来自服务商配置的商品规格数据
        private Integer expireTime;
        //该规格最大使用次数，-1为无限制。来自服务商配置的商品规格数据
        private Integer useCount;

        public String getSpecs() {
            return specs;
        }

        public void setSpecs(String specs) {
            this.specs = specs;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public Integer getSpecType() {
            return specType;
        }

        public void setSpecType(Integer specType) {
            this.specType = specType;
        }

        public Integer getUserNum() {
            return userNum;
        }

        public void setUserNum(Integer userNum) {
            this.userNum = userNum;
        }

        public Integer getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(Integer expireTime) {
            this.expireTime = expireTime;
        }

        public Integer getUseCount() {
            return useCount;
        }

        public void setUseCount(Integer useCount) {
            this.useCount = useCount;
        }
    }
}
