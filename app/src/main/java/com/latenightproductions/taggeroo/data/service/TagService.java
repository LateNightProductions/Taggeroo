package com.latenightproductions.taggeroo.data.service;

import com.latenightproductions.taggeroo.data.model.Tag;

import java.util.List;

import rx.Observable;

public interface TagService {

    Observable<List<Tag>> listAllTags();

    Observable<Tag> getTag(long tagId);

    Observable<Tag> createTag(Tag tag);

    Observable<Tag> updateTag(Tag tag);

    Observable<Void> deleteTag(Tag tag);

}
