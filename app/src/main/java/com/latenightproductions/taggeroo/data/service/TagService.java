package com.latenightproductions.taggeroo.data.service;

import com.latenightproductions.taggeroo.data.model.Tag;

import java.util.List;

import rx.Observable;

public interface TagService {

    Observable<Tag> getTag(long tagId);
    Observable<List<Tag>> listAllTags();
    Observable<List<Tag>> searchForTags(Observable<String> query);

    Observable<Tag> createTag(Tag tag);

    Observable<Tag> updateTag(Tag tag);

    Observable<Void> deleteTag(Tag tag);

}
