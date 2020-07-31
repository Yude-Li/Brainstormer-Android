package com.yude.brainstormer.view.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yude.brainstormer.R;
import com.yude.brainstormer.callback.api.ApiCallback;
import com.yude.brainstormer.dao.DaoFactory;
import com.yude.brainstormer.dao.DataDao;
import com.yude.brainstormer.model.Brain;
import com.yude.brainstormer.model.form.FollowForm;
import com.yude.brainstormer.model.form.UnFollowForm;
import com.yude.brainstormer.rest.PostTaskJson;
import com.yude.brainstormer.view.adapter.BrainsRecycleViewAdapter;
import com.yude.brainstormer.view.adapter.MyDividerItemDecoration;
import com.yude.brainstormer.view.callback.BrainRowOnClickCallback;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyBrainFriendsFragment extends Fragment implements ApiCallback<Brain> {

    private RecyclerView recyclerView;
    private EditText searchBar;

    private Context context;
    private DataDao dao;
    private BrainsRecycleViewAdapter adapter;
    private List<Brain> brainList;
    private List<Brain> searchBrainList;

    public MyBrainFriendsFragment() { }

    private MyBrainFriendsFragment getFragment() {
        return this;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        dao = DaoFactory.getDataDao();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_brain_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_brainFriends);
        initialListView();

        searchBar = view.findViewById(R.id.editText_brainFriends_search);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchBrainByUsername(s.toString());
            }
        });
    }

    private void initialListView() {
        brainList = new ArrayList<>();
        searchBrainList = new ArrayList<>();
        brainList.addAll(dao.getCurrentBrain().getFollows());
        searchBrainList.addAll(dao.getCurrentBrain().getFollows());

        adapter = new BrainsRecycleViewAdapter(searchBrainList, context, new BrainRowOnClickCallback() {
            @Override
            public void onAddBtnClick(String followBrainUsername, boolean isAdd) {
                if (isAdd) { // Already follow, want to unfollow
                    UnFollowForm followForm = new UnFollowForm();
                    followForm.setUsername(dao.getCurrentBrain().getUsername());
                    followForm.setFollowUsername(followBrainUsername);
                    new PostTaskJson<UnFollowForm, Brain>(Brain.class, getFragment()).execute(followForm);
                }
                else { // not yet follow, follow
                    FollowForm followForm = new FollowForm();
                    followForm.setUsername(dao.getCurrentBrain().getUsername());
                    followForm.setFollowUsername(followBrainUsername);
                    new PostTaskJson<FollowForm, Brain>(Brain.class, getFragment()).execute(followForm);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        Drawable dividerDrawable = ContextCompat.getDrawable(context, R.drawable.recycle_divider);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(context, dividerDrawable));
    }

    private void searchBrainByUsername(String text) {
        searchBrainList.clear();
        for (Brain brain : brainList) {
            if (brain.getUsername().toLowerCase(Locale.ENGLISH).contains(text.toLowerCase())) {
                searchBrainList.add(brain);
            }
        }
        adapter.notifyDataSetChanged();
    }

    // region Api Callback
    @Override
    public void postResult(ResponseEntity<Brain> responseEntity) {

    }
    // endregion
}