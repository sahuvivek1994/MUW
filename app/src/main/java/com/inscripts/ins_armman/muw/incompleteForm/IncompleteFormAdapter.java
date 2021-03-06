package com.inscripts.ins_armman.muw.incompleteForm;


import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.data.model.IncompleteFiledForm;

import java.util.List;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public class IncompleteFormAdapter extends RecyclerView.Adapter<IncompleteFormAdapter.ViewHolder> {
    private Context mContext;
    private List<IncompleteFiledForm> mWomenList;
    private IncompleteFormAdapter.OnItemClickListener mOnItemClickListener;

    public IncompleteFormAdapter(Context mContext, List<IncompleteFiledForm> womenList, IncompleteFormAdapter.OnItemClickListener mOnItemClickListener) {
        this.mContext = mContext;
        this.mWomenList = womenList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_incomplete_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.bindData(mWomenList.get(i));
        if(i%2==0)
            holder.cardview.setCardBackgroundColor(mContext.getResources().getColor(R.color.light_bg1));
        else
            holder.cardview.setCardBackgroundColor(mContext.getResources().getColor(R.color.light_bg2));

    }

    @Override
    public int getItemCount() {
        return mWomenList.size();
    }

    public void swapDataList(List<IncompleteFiledForm> womenList) {
        this.mWomenList = womenList;
    }

    public interface OnItemClickListener {
        void onItemClick(String uniqueId, int form_id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, incompleteVisitlabel;
        ConstraintLayout constraintLayout;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textview_name);
            constraintLayout = itemView.findViewById(R.id.constraint_layout_root);
            cardview = itemView.findViewById(R.id.card_view_completeList);

        }

        private void bindData(final IncompleteFiledForm listModel) {
            if (listModel != null) {
                textViewName.setText(listModel.getName());


                constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int formIdToOpen = Integer.parseInt(listModel.getFormId());
                        if (listModel.getFormCompleteStatus() == 1)
                            if (formIdToOpen != 10) {
                                formIdToOpen = formIdToOpen + 1;
                            }
                        mOnItemClickListener.onItemClick(listModel.getUniqueId(), formIdToOpen);
                    }
                });
            }
        }
    }
}
