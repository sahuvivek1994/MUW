package com.inscripts.ins_armman.muw.forms;


/**
 * @author Vivek & Juilee  Created on 14/8/2019
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
