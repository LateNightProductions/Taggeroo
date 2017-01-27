package com.latenightproductions.taggeroo.ui.tags;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.latenightproductions.taggeroo.R;
import com.latenightproductions.taggeroo.data.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Tag> tags;
    private TagsContract.Presenter presenter;

    public TagsAdapter(Context context, TagsContract.Presenter presenter) {
        this.context = context;
        this.tags = new ArrayList<>();
        this.presenter = presenter;
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

        if (!TextUtils.isEmpty(tag.getMemo())) {
            holder.memo.setVisibility(View.VISIBLE);
            holder.memo.setText(tag.getMemo());
        } else {
            holder.memo.setVisibility(View.GONE);
            holder.memo.setText("");
        }

        if (tag.getTimestamp() != null) {
            holder.time.setVisibility(View.VISIBLE);
            holder.time.setText(DateUtils.formatDateTime(context, tag.getTimestamp().getTime(),
                    DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        } else {
            holder.time.setVisibility(View.GONE);
            holder.time.setText("");
        }

        holder.itemView.setOnLongClickListener(view -> {

            LinearLayout layout = new LinearLayout(context);

            EditText input = new EditText(context);
            input.setHint(context.getString(R.string.dialog_memo_hint, tag.getText()));
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            input.setEllipsize(TextUtils.TruncateAt.END);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = context.getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin);
            lp.setMargins(margin, 0, margin, 0);
            input.setLayoutParams(lp);
            layout.addView(input);

            new AlertDialog.Builder(context)
                    .setTitle(R.string.dialog_memo_title)
                    .setView(layout)
                    .setPositiveButton(R.string.action_set,
                            (dialog, which) -> {
                                tag.setMemo(input.getText().toString());
                                presenter.updateTag(tag);
                            })
                    .setNegativeButton(R.string.action_cancel,
                            (dialog, which) -> dialog.dismiss())
                    .show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public void swapObjects(List<Tag> tags) {
        this.tags = new ArrayList<>(tags);
        notifyDataSetChanged();
    }

    public Tag getTag(int i) {
        return tags.get(i);
    }

    //================================================================================
    // View holder
    //================================================================================

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView memo;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.adapter_tags_title);
            memo = (TextView) itemView.findViewById(R.id.adapter_tags_memo);
            time = (TextView) itemView.findViewById(R.id.adapter_tags_time);
        }
    }
}
