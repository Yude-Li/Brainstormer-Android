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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yude.brainstormer.R;
import com.yude.brainstormer.callback.api.ApiCallback;
import com.yude.brainstormer.callback.api.ApiCallbackGet;
import com.yude.brainstormer.dao.DaoFactory;
import com.yude.brainstormer.dao.DataDao;
import com.yude.brainstormer.model.Brain;
import com.yude.brainstormer.model.form.FollowForm;
import com.yude.brainstormer.model.form.UnFollowForm;
import com.yude.brainstormer.rest.GetTaskJson;
import com.yude.brainstormer.rest.PostTaskJson;
import com.yude.brainstormer.view.adapter.BrainsRecycleViewAdapter;
import com.yude.brainstormer.view.adapter.MyDividerItemDecoration;
import com.yude.brainstormer.view.callback.BrainRowOnClickCallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BrainToFollowFragment extends Fragment implements ApiCallback<Brain>, ApiCallbackGet<String> {

    private RecyclerView recyclerView;
    private EditText searchBar;

    private Context context;
    private DataDao dao;
    private BrainsRecycleViewAdapter adapter;
    private List<Brain> brainList;
    private List<Brain> searchBrainList;

    public BrainToFollowFragment() {
        // Required empty public constructor
    }

    private BrainToFollowFragment getFragment() {
        return this;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        dao = DaoFactory.getDataDao();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        String request = "http://10.0.2.2:8080/brains";
        new GetTaskJson<String>(String.class, getFragment()).execute(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_follow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycleView_follow);
        initialListView();

        searchBar = view.findViewById(R.id.editText_follow_search);
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
        recyclerViewOnTouchListener();
    }

    public void recyclerViewOnTouchListener() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
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

    @Override
    public void postResult(ResponseEntity<Brain> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Log.d("Post Result", responseEntity.getBody().toString());
            Toast.makeText(
                    this.getContext(),
                    "Follow/Unfollow successfully",
                    Toast.LENGTH_LONG
            ).show();
            dao.setCurrentBrain(responseEntity.getBody());
            adapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(
                    this.getContext(),
                    "Error: " + responseEntity.getStatusCode().toString(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public void getResult(ResponseEntity<String> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Log.d("getResult", responseEntity.getBody().toString());
            parseStringToBrainList(responseEntity.getBody().toString());
            Toast.makeText(
                    this.getContext(),
                    "Get List successfully",
                    Toast.LENGTH_LONG
            ).show();
        }
        else {
            Toast.makeText(
                    this.getContext(),
                    "Error: " + responseEntity.getStatusCode().toString(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void parseStringToBrainList(String input) {
        brainList.clear();
        searchBrainList.clear();

        List<Brain> list = new Gson().fromJson(input, new TypeToken<List<Brain>>(){}.getType());
        Brain currentBrain = dao.getCurrentBrain();

        for (Brain brain : list) {
            if (brain.getUsername().equals(currentBrain.getUsername())) {
                list.remove(brain);
                break;
            }
        }

        brainList.addAll(list);
        searchBrainList.addAll(list);
        adapter.notifyDataSetChanged();
        Log.d("Print Brain List", brainList.toString());
    }
}