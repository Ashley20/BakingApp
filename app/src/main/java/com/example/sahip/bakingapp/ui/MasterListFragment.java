package com.example.sahip.bakingapp.ui;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sahip.bakingapp.MainActivity;
import com.example.sahip.bakingapp.R;
import com.example.sahip.bakingapp.RecipeDetailActivity;
import com.example.sahip.bakingapp.database.TinyDB;
import com.example.sahip.bakingapp.models.Recipe;
import com.example.sahip.bakingapp.models.Step;
import com.example.sahip.bakingapp.rest.HttpClient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


public class MasterListFragment extends Fragment {
    public static final String TAG = MasterListFragment.class.getSimpleName();

    private static final String BASE_URL
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private RecyclerView recyclerView;
    private MasterListAdapter masterListAdapter;
    private ArrayList<Recipe> mRecipeList = new ArrayList<Recipe>();
    private Request mRequest = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        // Get reference to xml layout file
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recipes_recycler_view);

        recyclerView.setHasFixedSize(true);

        // Set a LinearLayoutManager on the recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(masterListAdapter == null){
            masterListAdapter = new MasterListAdapter(getContext(), mRecipeList);
        }
        recyclerView.setAdapter(masterListAdapter);

        // Get all recipes from network resource and store in a ArrayList
        getRecipies();
    }



    public void getRecipies() {
        if(mRequest == null){
            mRequest = new Request.Builder()
                    .url(BASE_URL)
                    .build();
        }

        HttpClient.getClient().newCall(mRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    throw new IOException("Unexpected code " + response.code());
                }else {
                    String data = null;
                    if (response.body() != null) {
                        data = response.body().string();
                        Gson gson = new Gson();
                        try {
                            JSONArray root = new JSONArray(data);

                            for(int i = 0; i < root.length(); i++){
                                JSONObject object = root.getJSONObject(i);

                                Recipe recipe = gson.fromJson(object.toString(), Recipe.class);
                                // fill up the mRecipeList with Recipe data from Json resource
                                mRecipeList.add(recipe);

                            }

                            // Store recipes in tiny db
                            storeInTindyDB();

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Create the adapter
                                    // This adapter takes in the context and an ArrayList of ALL the recipes to display
                                    if(masterListAdapter == null) {
                                        masterListAdapter = new MasterListAdapter(getContext(), mRecipeList);
                                    }

                                    // Set the adapter on the RecyclerView
                                    recyclerView.setAdapter(masterListAdapter);
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        });
    }

    private void storeInTindyDB() {
        TinyDB tiny = new TinyDB(getContext());
        ArrayList<Object> recipeObjects = new ArrayList<Object>();

        for(Recipe a : mRecipeList){
            recipeObjects.add((Object)a);
        }
        tiny.putListObject(RecipeDetailFragment.RECIPES_KEY, recipeObjects);
    }
}
