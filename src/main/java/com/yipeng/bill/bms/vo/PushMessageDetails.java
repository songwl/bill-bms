package com.yipeng.bill.bms.vo;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/24.
 */
public class PushMessageDetails {
    private Long id;

    private Long receiveuserid;

    private String alias;

    private String tag;

    private String typev;

    private Boolean modal;

    private String title;

    private String content;

    private String extras;

    private Integer flag;

    private Integer retries;

    private String createtime;

    private Date sendtime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReceiveuserid() {
        return receiveuserid;
    }

    public void setReceiveuserid(Long receiveuserid) {
        this.receiveuserid = receiveuserid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getTypev() {
        return typev;
    }

    public void setTypev(String typev) {
        this.typev = typev == null ? null : typev.trim();
    }

    public Boolean getModal() {
        return modal;
    }

    public void setModal(Boolean modal) {
        this.modal = modal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras == null ? null : extras.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }
}
