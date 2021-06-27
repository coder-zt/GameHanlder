package com.coder.zt.gamehanlder.net;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Set;


public class Result {



    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Result Success(Boolean success) {
        this.success = success;
        return this;
    }
    public Set<Integer> getPerCodes() {
        return perCodes;
    }

    public void setPerCodes(Set<Integer> perCodes) {
        this.perCodes = perCodes;
    }

    public Result PerCodes(Set<Integer> perCodes) {
        this.perCodes = perCodes;
        return this;
    }
    public Set<Integer> getCurCodes() {
        return curCodes;
    }

    public void setCurCodes(Set<Integer> curCodes) {
        this.curCodes = curCodes;
    }
    public Result CurCodes(Set<Integer> perCodes) {
        this.curCodes = perCodes;
        return this;
    }

    public Result Index(Integer index) {
        this.index = index;
        return this;
    }


    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("perCodes")
    @Expose
    private Set<Integer> perCodes;
    @SerializedName("curCodes")
    @Expose
    private Set<Integer> curCodes;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @SerializedName("index")
    @Expose
    private Integer index;//aaaaaaaaaaaa

}