package com.yude.brainstormer.view.callback;

import com.yude.brainstormer.model.Idea;

public interface IdeaRowOnClickCallback {

    void citeBtnOnClick(int position);
    void contextOnClick(Idea idea);

}
