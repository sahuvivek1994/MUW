package com.inscripts.ins_armman.muw.midlineInterview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.database.DBHelper;
import com.inscripts.ins_armman.muw.displayForms.displayForm;

import java.util.ArrayList;

import static com.inscripts.ins_armman.muw.utility.Constants.FORM_ID;

public class MidlineVerifyActivity extends AppCompatActivity {
    Button btnCancel,btnContinue;
    ConstraintLayout constraintDetails;
    EditText etParticipantId;
    DBHelper dbHelper;
    ArrayList<String> participantDetails;
    TextView txtParticipantName,txtParticipantPhone,txtSearchResult;
    int searchResult = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.midline_verify_layout);
        setTitle("VERIFY PARTICIPANT DETAILS");
        btnCancel = findViewById(R.id.btnCancel);
        btnContinue = findViewById(R.id.btnContinue);
        constraintDetails = findViewById(R.id.constraintDetails);
        etParticipantId = findViewById(R.id.etParticipantId);
        txtParticipantName = findViewById(R.id.txtParticipantName);
        txtParticipantPhone = findViewById(R.id.txtParticipantPhone);
        txtSearchResult = findViewById(R.id.txtSearchResult);
        dbHelper = new DBHelper(this);
        etParticipantId.setTransformationMethod(null);
        performButtonOperation();
        performEditTextOperation();
    }
    void performEditTextOperation(){
        etParticipantId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                final String enteredValue = etParticipantId.getText().toString();
                if(enteredValue.length() == 5){
                    participantDetails = dbHelper.participantDetails(enteredValue);
                    /**
                     * use following code after search result query is added
                     */
                   /* if(!participantDetails.isEmpty() && participantDetails!=null)
                    {
                        searchResult = 100;
                        txtSearchResult.setText("Search result found");
                        btnContinue.setVisibility(View.VISIBLE);
                    } else{
                        searchResult = 101;
                        txtSearchResult.setText("Search result not found");
                    }
                   */ //make it visible only when the the search result is found
                    constraintDetails.setVisibility(View.VISIBLE);
                    btnContinue.setVisibility(View.VISIBLE);
                    /**
                     * set the participant name and phone number returned in arraylist
                     */
                    txtParticipantName.setText("value");
                    txtParticipantPhone.setText("value");
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null){
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                }else if(enteredValue.length() > 5){
                    etParticipantId.setError("Please enter valid participant id");
                }
                else{
                    constraintDetails.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
void performButtonOperation(){
    btnCancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });

    if(searchResult == 100){
    btnContinue.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MidlineVerifyActivity.this, displayForm.class);
            intent.putExtra(FORM_ID,"11");
            intent.putExtra("midlineFlag", 100);
           intent.putExtra("participant_id",etParticipantId.getText().toString());
            startActivity(intent);
        }
    });
    }
    else{
        btnContinue.setVisibility(View.INVISIBLE);
    }
    }
}

