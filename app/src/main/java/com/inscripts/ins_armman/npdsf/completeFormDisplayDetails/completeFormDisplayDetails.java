package com.inscripts.ins_armman.npdsf.completeFormDisplayDetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.inscripts.ins_armman.npdsf.R;

import static com.inscripts.ins_armman.npdsf.utility.Constants.UNIQUE_ID;

public class completeFormDisplayDetails extends AppCompatActivity implements IcompleteFormDisplayDetailsView{

    RecyclerView mRecyclerView;
    completeFormDisplayDetailsAdapter completeFormDisplayDetailsAdapter;
    IcompleteFormDisplayDetailsPresenter icompleteFormDisplayDetailsPresenter;
    Bundle b;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_form_display_details);
        setTitle("Completed Form List");
        mProgressBar = findViewById(R.id.child_list_progress_bar);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(completeFormDisplayDetails.this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);

        Intent in = getIntent();
        b = in.getExtras();
        String id = b.getString(UNIQUE_ID);
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        icompleteFormDisplayDetailsPresenter = new completeFormDisplayDetailsPresenter();
        icompleteFormDisplayDetailsPresenter.attachView(this);
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        icompleteFormDisplayDetailsPresenter.detch();
    }

}
