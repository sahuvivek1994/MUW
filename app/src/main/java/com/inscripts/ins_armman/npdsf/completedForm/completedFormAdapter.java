package com.inscripts.ins_armman.npdsf.completedForm;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.data.model.completeFiledForm;

import java.util.List;


public class completedFormAdapter extends RecyclerView.Adapter<completedFormAdapter.ViewHolder> {

    private Context mContext;
    private List<completeFiledForm> mWomenList;
    private completedFormAdapter.OnItemClickListener mOnItemClickListener;

    public completedFormAdapter(Context mContext, List<completeFiledForm> womenList, completedFormAdapter.OnItemClickListener mOnItemClickListener) {
        this.mContext = mContext;
        this.mWomenList = womenList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_incomplete_list, parent, false));
    }

    @Override
    public void onBindViewHolder(completedFormAdapter.ViewHolder holder, int i) {
        holder.bindData(mWomenList.get(i));
    }

    @Override
    public int getItemCount() {
        return mWomenList.size();
    }

    public void swapDataList(List<completeFiledForm> womenList) {
        this.mWomenList = womenList;
    }

    public interface OnItemClickListener {
        void onItemClick();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textview_name);
            constraintLayout = itemView.findViewById(R.id.constraint_layout_root);
        }

        private void bindData(final completeFiledForm listModel) {
            if (listModel != null) {
                textViewName.setText(listModel.getName());


                constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(mContext, "Hello", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
