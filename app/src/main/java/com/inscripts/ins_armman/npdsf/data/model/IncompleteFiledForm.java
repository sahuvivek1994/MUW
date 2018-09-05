package com.inscripts.ins_armman.npdsf.data.model;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class IncompleteFiledForm {

    String uniqueId, name, formId;

    public IncompleteFiledForm(String UniqueId, String Name, String FormId) {
        this.uniqueId = UniqueId;
        this.name = Name;
        this.formId = FormId;
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
