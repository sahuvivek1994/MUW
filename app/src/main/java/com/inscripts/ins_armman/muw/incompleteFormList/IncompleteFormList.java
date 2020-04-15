package com.inscripts.ins_armman.muw.incompleteFormList;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.data.model.CompleteFormQnA;
import com.inscripts.ins_armman.muw.database.DBHelper;
import com.inscripts.ins_armman.muw.mainMenu.MainActivity;
import com.inscripts.ins_armman.muw.settingActivity.Settings;

import java.util.ArrayList;

/**
 * This activity is to display the form list completed from total forms of mother and her child.
 */
public class IncompleteFormList extends AppCompatActivity implements IIncompleteFormList,IncompleteFormListAdapter.ClickListener {
    RecyclerView recyclerView;
    DBHelper db;
    IncompleteFormListPresentor presentor;
    IncompleteFormListAdapter adapter;
    String id, name;
    int form_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_forms_list);
        setTitle("Filled Form List");
        recyclerView = findViewById(R.id.recyclerView);
        db = new DBHelper(this);
        presentor = new IncompleteFormListPresentor();
        presentor.attachView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(IncompleteFormList.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        id = getIntent().getStringExtra("unique_id");
        form_id = getIntent().getIntExtra("form_id", 0);
        presentor.getCompleteFormList(id);

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void getData(ArrayList<CompleteFormQnA> formDetails, ArrayList<CompleteFormQnA> childNo) {
        adapter = new IncompleteFormListAdapter(formDetails, childNo, this, id, form_id);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
    }

    @Override
    public void itemClicked(View view, int position) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(IncompleteFormList.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
