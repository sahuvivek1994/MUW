package com.inscripts.ins_armman.muw.midlineInterview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.data.model.completeFiledForm;
import com.inscripts.ins_armman.muw.displayForms.displayForm;
import com.inscripts.ins_armman.muw.mainMenu.MainActivity;
import com.inscripts.ins_armman.muw.registration.EnrollmentQuestions;

import java.util.List;

import static com.inscripts.ins_armman.muw.utility.Constants.FORM_ID;

public class MidlineInterviewActivity extends AppCompatActivity implements IMidlineInterviewView, MidlineInterviewAdapter.ClickListener {
    IMidlineInterviewPresenter presenter;
    MidlineInterviewAdapter adapter;
    ConstraintLayout emptyLayout;
    private RecyclerView mRecyclerView;
    //FloatingActionButton interviewFormBtn;
    EditText etUniqueId, etName;
    Button btnContinue, btnCancel,interviewFormBtn;
    TextView fabLabel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.midline_interview_layout);
        setTitle("Filled Participants List");
        emptyLayout = findViewById(R.id.empty_midline_list);
        mRecyclerView = findViewById(R.id.recycler_view);
        interviewFormBtn = findViewById(R.id.floating_action_button);
       // fabLabel = findViewById(R.id.fabLabel);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MidlineInterviewActivity.this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);

        presenter = new MidlineInterviewPresenter();
        presenter.attachView(this);

        interviewFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInterviewForm();
            }
        });

    }

    public void openInterviewForm(){
        Intent intent = new Intent(MidlineInterviewActivity.this, MidlineVerifyActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detch();
    }

    @Override
    public void setAdapter(List<completeFiledForm> mWomenList) {
        if (mWomenList == null || mWomenList.size() < 1) {
            emptyLayout.setVisibility(View.VISIBLE);
            return;
        }if(mWomenList != null) {
            adapter = new MidlineInterviewAdapter(getContext(), mWomenList);
            mRecyclerView.setAdapter(adapter);
            adapter.setClickListener(this);
        } else {
            adapter.swapDataList(mWomenList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getAllParticipants();
    }

    @Override
    public void itemClicked(View view, int position) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MidlineInterviewActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
