package com.inscripts.ins_armman.npdsf.forms;


/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class Label {
    String textOnLabel, calculation;

    public Label(String textOnLabel, String calculation) {
        this.textOnLabel = textOnLabel;
        this.calculation = calculation;
    }

    public String getTextOnLabel() {
        return textOnLabel;
    }

    public void setTextOnLabel(String textOnLabel) {
        this.textOnLabel = textOnLabel;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    @Override
    public String toString() {
        return "Label{" +
                "textOnLabel='" + textOnLabel + '\'' +
                ", calculation='" + calculation + '\'' +
                '}';
    }
}
