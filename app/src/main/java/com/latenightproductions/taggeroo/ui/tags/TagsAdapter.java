package com.latenightproductions.taggeroo.ui.tags;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.latenightproductions.taggeroo.R;
import com.latenightproductions.taggeroo.data.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Tag> tags;

    public TagsAdapter(Context context) {
        this.context = context;
        this.tags = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_tags, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tag tag = tags.get(position);
        holder.title.setText(tag.getText());
        holder.memo.setText(tag.getMemo());
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public void swapObjects(List<Tag> tags) {
        this.tags = new ArrayList<>(tags);
        notifyDataSetChanged();
    }

    //================================================================================
    // View holder
    //================================================================================

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView memo;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.adapter_tags_title);
            memo = (TextView) itemView.findViewById(R.id.adapter_tags_memo);
        }
    }
}
