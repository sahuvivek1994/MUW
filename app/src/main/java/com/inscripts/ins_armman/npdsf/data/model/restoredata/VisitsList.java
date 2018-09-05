package com.inscripts.ins_armman.npdsf.data.model.restoredata;

import com.google.gson.annotations.SerializedName;
import com.inscripts.ins_armman.npdsf.data.model.syncing.QuestionAnswer;

import java.util.ArrayList;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class VisitsList {
    @SerializedName("id")
    private int formId;
    @SerializedName("created_on")
    private String createdOn;
    @SerializedName("data")
    private ArrayList<QuestionAnswer> questionAnswers;

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public ArrayList<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(ArrayList<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    @Override
    public String toString() {
        return "VisitsList{" +
                "formId=" + formId +
                ", createdOn='" + createdOn + '\'' +
                ", questionAnswers=" + questionAnswers +
                '}';
    }
}
