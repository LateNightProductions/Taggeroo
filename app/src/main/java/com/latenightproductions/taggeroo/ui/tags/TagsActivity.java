package com.latenightproductions.taggeroo.ui.tags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.latenightproductions.taggeroo.R;
import com.latenightproductions.taggeroo.data.model.Tag;
import com.latenightproductions.taggeroo.injection.component.DaggerTagsComponent;
import com.latenightproductions.taggeroo.injection.module.TagsPresenterModule;
import com.latenightproductions.taggeroo.ui.base.BaseActivity;
import com.latenightproductions.taggeroo.ui.base.TaggerooApplication;
import com.latenightproductions.taggeroo.util.RecyclerItemTapListener;

import java.util.List;

import javax.inject.Inject;

public class TagsActivity extends BaseActivity implements TagsContract.View {

    @Inject TagsContract.Presenter presenter;

    RecyclerView recycler;
    EditText omnibox;
    Button addButton;

    private TagsAdapter tagsAdapter;

    //================================================================================
    // Lifecycle methods
    //================================================================================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        recycler = (RecyclerView) findViewById(R.id.activity_tags_recycler);
        omnibox = (EditText) findViewById(R.id.activity_tags_omnibox);
        addButton = (Button) findViewById(R.id.activity_tags_add_button);

        DaggerTagsComponent.builder()
                .tagsPresenterModule(new TagsPresenterModule(this))
                .servicesComponent(((TaggerooApplication) getApplication()).getServicesComponent())
                .build().inject(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        tagsAdapter = new TagsAdapter(this, presenter);
        recycler.setAdapter(tagsAdapter);

        recycler.addOnItemTouchListener(new RecyclerItemTapListener(this,
                position ->  {
                    Tag t = tagsAdapter.getTag(position);
                    omnibox.setText(t.getText());
                }));

        presenter.loadAllTags();

        addButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(omnibox.getText())) {
                return;
            }
            presenter.createTag(omnibox.getText().toString());
            omnibox.setText("");
        });
    }


    //================================================================================
    // View methods
    //================================================================================

    @Override
    public void onTagsLoaded(List<Tag> tags) {
        tagsAdapter.swapObjects(tags);
    }

    @Override
    public void onTagUpdated(Tag tag) {

    }

    @Override
    public void onTagCreated(Tag tag) {
        Snackbar.make(findViewById(android.R.id.content), "Tag created!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
