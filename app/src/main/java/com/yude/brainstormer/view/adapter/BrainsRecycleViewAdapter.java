package com.yude.brainstormer.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yude.brainstormer.R;
import com.yude.brainstormer.dao.DaoFactory;
import com.yude.brainstormer.dao.DataDao;
import com.yude.brainstormer.model.Brain;
import com.yude.brainstormer.model.Idea;
import com.yude.brainstormer.view.callback.BrainRowOnClickCallback;
import com.yude.brainstormer.view.callback.IdeaRowOnClickCallback;

import java.util.List;

public class BrainsRecycleViewAdapter  extends RecyclerView.Adapter<BrainsRecycleViewAdapter.ViewHolder> {

    private List<Brain> brainList = null;
    private Brain currentBrain = null;
    private Context context;
    private DataDao dao;
    private final BrainRowOnClickCallback listener;

    public BrainsRecycleViewAdapter(List<Brain> brainList, Context context, BrainRowOnClickCallback listener) {
        this.brainList = brainList;
        this.context = context;
        this.listener = listener;
        dao = DaoFactory.getDataDao();
        currentBrain = dao.getCurrentBrain();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView rowUsername;
        public TextView rowEmail;
        public ImageButton rowFollowBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            rowUsername = itemView.findViewById(R.id.textView_rowBrain_username);
            rowEmail = itemView.findViewById(R.id.textView_rowBrain_email);
            rowFollowBtn = itemView.findViewById(R.id.button_rowBrain_follow);
        }
    }

    @Override
    public BrainsRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView;

        contactView = inflater.inflate(R.layout.row_brain, parent, false);
        // Return a new holder instance
        BrainsRecycleViewAdapter.ViewHolder viewHolder = new BrainsRecycleViewAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final BrainsRecycleViewAdapter.ViewHolder viewHolder, final int position) {
        Brain currBrain = DaoFactory.getDataDao().getCurrentBrain();
        List<Brain> follows = currBrain.getFollows();
        final Brain brain = brainList.get(position);

        TextView rowUsername = viewHolder.rowUsername;
        TextView rowEmail = viewHolder.rowEmail;
        ImageButton rowFollowBtn = viewHolder.rowFollowBtn;

        rowUsername.setText(brain.getUsername());
        rowEmail.setText(brain.getEmail());

        final boolean isFollow = follows.stream().filter(o -> o.getUsername().equals(brain.getUsername())).findFirst().isPresent();

        if (isFollow) {
            rowFollowBtn.setImageResource(R.drawable.ic_check_box);
        }
        else {
            rowFollowBtn.setImageResource(R.drawable.ic_add_box);
        }

        rowFollowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddBtnClick(brain.getUsername(), isFollow);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return brainList.size();
    }

    public void add(int position, Brain item) {
        brainList.add(item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        brainList.remove(position);
        notifyItemRemoved(position);
    }
}

