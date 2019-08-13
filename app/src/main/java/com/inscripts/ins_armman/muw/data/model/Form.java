package com.inscripts.ins_armman.muw.data.model;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */
public class Form {
    private int formId;

    /**
     * fromDays and toDays is from_weeks and to_weeks from Db form_details table respectively
     */

    private String visitName;

    private String orderId;

    public Form() {
    }

    public Form(int formId, String visitName, String orderId) {
        this.formId = formId;
        this.visitName = visitName;
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }
}
