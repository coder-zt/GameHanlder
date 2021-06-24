package com.coder.zt.gamehanlder.net;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


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
    public List<Integer> getPerCodes() {
        return perCodes;
    }

    public void setPerCodes(List<Integer> perCodes) {
        this.perCodes = perCodes;
    }

    public Result PerCodes(List<Integer> perCodes) {
        this.perCodes = perCodes;
        return this;
    }
    public List<Integer> getCurCodes() {
        return curCodes;
    }

    public void setCurCodes(List<Integer> curCodes) {
        this.curCodes = curCodes;
    }
    public Result CurCodes(List<Integer> perCodes) {
        this.curCodes = perCodes;
        return this;
    }
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("perCodes")
    @Expose
    private List<Integer> perCodes;
    @SerializedName("curCodes")
    @Expose
    private List<Integer> curCodes;

}