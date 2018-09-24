package com.inscripts.ins_armman.npdsf.data.model;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class IncompleteFiledForm {

    String uniqueId, name, formId;
    int formCompleteStatus;

    public IncompleteFiledForm(String uniqueId, String name, String formId, int formCompleteStatus) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.formId = formId;
        this.formCompleteStatus = formCompleteStatus;
    }

    public int getFormCompleteStatus() {
        return formCompleteStatus;
    }

    public void setFormCompleteStatus(int formCompleteStatus) {
        this.formCompleteStatus = formCompleteStatus;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
}
