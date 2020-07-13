package com.yude.brainstormer.view.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yude.brainstormer.ApiCallbackPost;
import com.yude.brainstormer.R;
import com.yude.brainstormer.callback.api.ApiCallback;
import com.yude.brainstormer.callback.api.ApiCallbackGet;
import com.yude.brainstormer.dao.DaoFactory;
import com.yude.brainstormer.model.Brain;
import com.yude.brainstormer.model.Idea;
import com.yude.brainstormer.model.form.IdeaBrainForm;
import com.yude.brainstormer.model.form.IdeaForm;
import com.yude.brainstormer.rest.GetTaskJson;
import com.yude.brainstormer.rest.PostTaskJson;
import com.yude.brainstormer.view.adapter.IdeaRecycleViewAdapter;
import com.yude.brainstormer.view.adapter.MyDividerItemDecoration;
import com.yude.brainstormer.view.callback.IdeaRowOnClickCallback;
import com.yude.brainstormer.view.callback.NewIdeaListener;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class IdeasFragment extends Fragment implements NewIdeaListener, ApiCallback<Idea>, ApiCallbackGet<String> {

    private Context context;
    private NewIdeaDialog newIdeaDialog;
    private RecyclerView recyclerView;
    private IdeaRecycleViewAdapter adapter;
    private List<Idea> ideaList;
    private List<Idea> searchIdeaList;

    private boolean isAssignToBrain = false;

    public IdeasFragment() {
        // Required empty public constructor
    }

    private IdeasFragment getFragment() {
        return this;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        String request = "http://10.0.2.2:8080/ideas";
        new GetTaskJson<String>(String.class, getFragment()).execute(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        newIdeaDialog = NewIdeaDialog.newInstance("Post New Idea", context);
        newIdeaDialog.setListener(this);
        return inflater.inflate(R.layout.fragment_ideas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycleView_ideas);
        initialListView();

        Button btn = view.findViewById(R.id.button_ideas_newIdea);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAssignToBrain = false;
                newIdeaDialog.show(getFragmentManager(), "COUNTRY_PICKER");
            }
        });
    }

    private void initialListView() {
        ideaList = new ArrayList<>();
        searchIdeaList = new ArrayList<>();
        adapter = new IdeaRecycleViewAdapter(searchIdeaList, context, new IdeaRowOnClickCallback() {

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

    @Override
    public void newIdeaInfo(String title, String context, String content) {
        // Call api to add new post
        Brain brain = DaoFactory.getDataDao().getCurrentBrain();
        IdeaForm ideaForm = new IdeaForm(brain.getUsername(), title, context, content);
        new PostTaskJson<IdeaForm, Idea>(Idea.class, getFragment()).execute(ideaForm);
    }

    @Override
    public void postResult(ResponseEntity<Idea> responseEntity) {
        // Result after post, is success, call get
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            if (!isAssignToBrain) {
                Toast.makeText(
                        this.getContext(),
                        responseEntity.getBody().getTitle() + " Post successfully",
                        Toast.LENGTH_LONG
                ).show();

                IdeaBrainForm ideaForm = new IdeaBrainForm(responseEntity.getBody().getId(), responseEntity.getBody().getAuthor().getUsername());
                new PostTaskJson<IdeaBrainForm, Idea>(Idea.class, getFragment()).execute(ideaForm);
                isAssignToBrain = true;

                String request = "http://10.0.2.2:8080/ideas";
                new GetTaskJson<String>(String.class, getFragment()).execute(request);
            }
        }
        else {
            Toast.makeText(
                    this.getContext(),
                    "Post fail",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public void getResult(ResponseEntity<String> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Log.d("getResult", responseEntity.getBody().toString());
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

    private void parseStringToIdeaList(String input) {
        ideaList.clear();
        searchIdeaList.clear();

        List<Idea> list = new Gson().fromJson(input, new TypeToken<List<Idea>>(){}.getType());
        ideaList.addAll(list);
        searchIdeaList.addAll(list);
        adapter.notifyDataSetChanged();
        Log.d("Print Idea List", ideaList.toString());
    }
}