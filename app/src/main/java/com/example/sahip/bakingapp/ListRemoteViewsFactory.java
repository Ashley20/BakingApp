package com.example.sahip.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.sahip.bakingapp.models.Recipe;
import com.example.sahip.bakingapp.rest.HttpClient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String BASE_URL
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private Context mContext;
    private ArrayList<Recipe> mRecipeList;
    private Request mRequest;


    ListRemoteViewsFactory(Context context) {
        mContext = context;
        mRecipeList = new ArrayList<Recipe>();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

        if (mRequest == null) {
            mRequest = new Request.Builder()
                    .url(BASE_URL)
                    .build();
        }

        if(!mRecipeList.isEmpty()) return;

        HttpClient.getClient().newCall(mRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    String data = null;
                    if (response.body() != null) {
                        data = response.body().string();
                        Gson gson = new Gson();

                        try {
                            JSONArray root = new JSONArray(data);

                            for (int i = 0; i < root.length(); i++) {
                                JSONObject object = root.getJSONObject(i);

                                Recipe recipe = gson.fromJson(object.toString(), Recipe.class);
                                // fill up the mRecipeList with Recipe data from Json resource
                                mRecipeList.add(recipe);

                            }
                            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                            ComponentName thisWidget = new ComponentName(mContext, BakingWidgetProvider.class);
                            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
                            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        mRecipeList.clear();
    }

    @Override
    public int getCount() {
        if(mRecipeList == null) return 0;
        return mRecipeList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_widget_provider);
        Recipe recipe = mRecipeList.get(i);
        views.setTextViewText(R.id.appwidget_text, recipe.getName());

        Bundle extras = new Bundle();
        extras.putInt(RecipeDetailActivity.RECIPE_POSITION, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.appwidget_text, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
