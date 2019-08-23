package com.inscripts.ins_armman.muw.completedFormList;

import android.database.Cursor;

import com.inscripts.ins_armman.muw.data.model.CompleteFormQnA;

import java.util.ArrayList;

public class CompletedFormsListPresentor implements ICompletedFormsListPresentor<CompletedFormsList> {
    ICompletedFormList view;
    CompletedFormsListInteractor interactor;
    ArrayList<CompleteFormQnA> formDetails = new ArrayList<>();
    ArrayList<CompleteFormQnA> childNumber = new ArrayList<>();
    int formId = 0;
    int count = 0, c = 0;
    Cursor res = null;
    String child_name, formName;

    @Override
    public void attachView(CompletedFormsList view) {
        this.view = view;
        this.interactor = new CompletedFormsListInteractor(view.getContext());
    }

    @Override
    public void detch() {
        view = null;
    }


    /**
     * this method is for getting the forms list of mother and her child.
     * if child count is greater than one then the child forms ie. form_id from 6 to 9 are repeated for every child.
     *
     * @param unique_mother_id= id to get the list of filled forms
     */
    @Override
    public void getCompleteFormList(String unique_mother_id) {
        res = interactor.getChildNo(unique_mother_id);
        c = res.getCount();
        //check child count
        if (res != null && res.moveToFirst()) {
            do {
                CompleteFormQnA childObj = new CompleteFormQnA();
                for (int i = 0; i < 4; i++) {
                    childObj.setUnique_id(res.getString(res.getColumnIndex("unique_id")));
                    childObj.setChildNAme(res.getString(res.getColumnIndex("name")));
                    childNumber.add(childObj);
                }
            } while (res.moveToNext());
        }

        //get forms
            res = interactor.getCompleteFormList();

            if (res != null & res.moveToFirst()) {
                do {
                    CompleteFormQnA obj = new CompleteFormQnA();
                    obj.setFormName(res.getString(res.getColumnIndex("visit_name")));
                    obj.setForm_id(res.getInt(res.getColumnIndex("form_id")));
                    int formId = res.getInt(res.getColumnIndex("form_id"));
                    formDetails.add(obj);
                } while (res.moveToNext());
            }
        if (!formDetails.isEmpty() || !childNumber.isEmpty()) {
            view.getData(formDetails, childNumber);
        } }//end of getCompleteFormList()
}

