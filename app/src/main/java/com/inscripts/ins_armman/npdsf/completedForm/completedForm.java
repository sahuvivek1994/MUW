package com.inscripts.ins_armman.npdsf.completedForm;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.data.model.completeFiledForm;

import java.util.List;

public class completedForm extends AppCompatActivity implements IcompletedFormView {

    IcompletedPresenter icompletedPresenter;
    private RecyclerView mRecyclerView;
    completedFormAdapter mcompleteFormAdapter;
    ProgressBar mProgressBar;
    RelativeLayout emptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_form);
        setTitle("Completed Form");
        mProgressBar = findViewById(R.id.child_list_progress_bar);
        emptyLayout = findViewById(R.id.empty_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(completedForm.this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);

        icompletedPresenter = new completedFormPresenter();
        icompletedPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        icompletedPresenter.detch();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setAdapter(List<completeFiledForm> womenList) {
        mProgressBar.setVisibility(View.GONE);

        if (womenList == null || womenList.size() < 1) {
            emptyLayout.setVisibility(View.VISIBLE);
            TextView emptyTextView = findViewById(R.id.text_empty_list);
            emptyTextView.setText(R.string.Reg_women_com);
            return;
        }

      if(womenList != null) {
            mcompleteFormAdapter = new completedFormAdapter(getContext(),womenList,null);
          mRecyclerView.setAdapter(mcompleteFormAdapter);
      }
      else {
          mcompleteFormAdapter.swapDataList(womenList);
          mcompleteFormAdapter.notifyDataSetChanged();
      }
    }

    @Override
    protected void onResume() {
        super.onResume();
        icompletedPresenter.getListCompleteForm();
    }
}
