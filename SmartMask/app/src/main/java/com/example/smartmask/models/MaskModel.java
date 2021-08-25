package com.example.smartmask.models;

public class MaskModel {

    private String mask_code;
    private String dateadded;


    public MaskModel(String mask_code, String dateadded) {
        this.mask_code = mask_code;
        this.dateadded = dateadded;
    }

    public String getMask_code() {
        return mask_code;
    }

    public void setMask_code(String mask_code) {
        this.mask_code = mask_code;
    }

    public String getDateadded() {
        return dateadded;
    }

    public void setDateadded(String dateadded) {
        this.dateadded = dateadded;
    }
}
