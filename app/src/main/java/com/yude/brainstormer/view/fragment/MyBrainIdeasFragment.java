package com.yude.brainstormer.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.yude.brainstormer.model.Idea;
import com.yude.brainstormer.model.form.CurrentBrainIdeasForm;
import com.yude.brainstormer.model.form.IdeaDeleteForm;
import com.yude.brainstormer.rest.GetTaskJsonWithParam;
import com.yude.brainstormer.rest.PostTaskJson;
import com.yude.brainstormer.view.adapter.IdeaRecycleViewAdapter;
import com.yude.brainstormer.view.adapter.MyDividerItemDecoration;
import com.yude.brainstormer.view.callback.IdeaRowOnClickCallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyBrainIdeasFragment extends Fragment implements ApiCallback<String>, ApiCallbackGet<String> {

    private RecyclerView recyclerView;
    private EditText searchBar;

    private Context context;
    private DataDao dao;
    private IdeaRecycleViewAdapter adapter;
    private List<Idea> ideaList;
    private List<Idea> searchIdeaList;

    public MyBrainIdeasFragment() { }

    private MyBrainIdeasFragment getFragment() {
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
        initialListView();
        CurrentBrainIdeasForm currentBrainIdeasForm = new CurrentBrainIdeasForm(dao.getCurrentBrain().getUsername());
        new GetTaskJsonWithParam<CurrentBrainIdeasForm, String>(String.class, getFragment()).execute(currentBrainIdeasForm);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_brain_ideas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_brainIdeas);
        searchBar = view.findViewById(R.id.editText_brainIdeas_search);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchIdeaByTitle(s.toString());
            }
        });
    }

    private void initialListView() {
        ideaList = new ArrayList<>();
        searchIdeaList = new ArrayList<>();
        adapter = new IdeaRecycleViewAdapter(searchIdeaList, context, new IdeaRowOnClickCallback() {
            @Override
            public void citeBtnOnClick(int position) {

            }

            @Override
            public void contextOnClick(Idea idea) {
                IdeaSimpleDialog ideaSimpleDialog = IdeaSimpleDialog.newInstance(idea, context);
                ideaSimpleDialog.show(getFragmentManager(), "");
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
                showDeleteConfirem(position);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void searchIdeaByTitle(String text) {
        searchIdeaList.clear();
        for (Idea idea : ideaList) {
            if (idea.getTitle().toLowerCase(Locale.ENGLISH).contains(text.toLowerCase())) {
                searchIdeaList.add(idea);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void showDeleteConfirem(final int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Delete");
        alertDialog.setMessage("Are you sure to delete this idea?");

        alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Idea idea = searchIdeaList.get(position);
                IdeaDeleteForm ideaDeleteForm = new IdeaDeleteForm(idea);
                new PostTaskJson<IdeaDeleteForm, String>(String.class, getFragment()).execute(ideaDeleteForm);
                dialog.cancel();
            }
        });
        alertDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void parseStringToIdeaList(String input) {
        ideaList.clear();
        searchIdeaList.clear();

        List<Idea> list = new Gson().fromJson(input, new TypeToken<List<Idea>>(){}.getType());
        ideaList.addAll(list);
        searchIdeaList.addAll(list);
        adapter.notifyDataSetChanged();
        Log.d("Print Idea List", ideaList.toString());
    }

    // region Api Callback
    @Override
    public void postResult(ResponseEntity<String> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            parseStringToIdeaList(responseEntity.getBody().toString());
            Toast.makeText(
                    this.getContext(),
                    "Delete successfully",
                    Toast.LENGTH_LONG
            ).show();
        }
        else {
            Toast.makeText(
                    this.getContext(),
                    "Delete fail",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public void getResult(ResponseEntity<String> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Log.d("BrainIdeas getResult", responseEntity.getBody());
            parseStringToIdeaList(responseEntity.getBody().toString());
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
    // endregion
}