package com.latenightproductions.taggeroo.ui.tags;

import com.latenightproductions.taggeroo.data.model.Tag;
import com.latenightproductions.taggeroo.ui.base.BasePresenter;
import com.latenightproductions.taggeroo.ui.base.BaseView;

import java.util.List;

public interface TagsContract {

    interface View extends BaseView {
        void onTagsLoaded(List<Tag> tags);
        void onTagUpdated(Tag tag);
        void onTagCreated(Tag tag);
    }

    interface Presenter extends BasePresenter {
        void loadAllTags();
        void createTag(String title);
        void updateTag(Tag tag);
    }

}
