package com.yude.brainstormer.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yude.brainstormer.R;
import com.yude.brainstormer.model.Idea;
import com.yude.brainstormer.view.callback.IdeaRowOnClickCallback;

import java.util.List;

public class IdeaRecycleViewAdapter extends RecyclerView.Adapter<IdeaRecycleViewAdapter.ViewHolder> {

    private List<Idea> ideaList = null;
    private Context context;
    private final IdeaRowOnClickCallback listener;

    public IdeaRecycleViewAdapter(List<Idea> ideaList, Context context, IdeaRowOnClickCallback listener) {
        this.ideaList = ideaList;
        this.context = context;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView rowTitleTV;
        public TextView rowAuthTV;
        public TextView rowContextTV;
        public TextView rowContentTV;

        public ViewHolder(View itemView) {
            super(itemView);
            rowTitleTV = itemView.findViewById(R.id.textView_ideaRow_title);
            rowAuthTV = itemView.findViewById(R.id.textView_ideaRow_author);
            rowContextTV = itemView.findViewById(R.id.textView_ideaRow_context);
            rowContentTV = itemView.findViewById(R.id.textView_ideaRow_content);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView;

        contactView = inflater.inflate(R.layout.row_idea, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final IdeaRecycleViewAdapter.ViewHolder viewHolder, final int position) {

        Idea idea = ideaList.get(position);

        TextView rowTitleTV = viewHolder.rowTitleTV;
        TextView rowAuthTV = viewHolder.rowAuthTV;
        TextView rowContextTV = viewHolder.rowContextTV;
        TextView rowContentTV = viewHolder.rowContentTV;

        rowTitleTV.setText(idea.getTitle());
        rowAuthTV.setText("- by " + idea.getAuthor().getUsername());
        rowContextTV.setText(idea.getContext());
        rowContentTV.setText(idea.getContent());

        if(idea.getOriginalIdea() != null) {
            rowContextTV.setTextColor(Color.BLUE);
            rowContextTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.contextOnClick(idea.getOriginalIdea());
                }
            });
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return ideaList.size();
    }

    public void add(int position, Idea item) {
        ideaList.add(item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        ideaList.remove(position);
        notifyItemRemoved(position);
    }
}
