package com.example.sahip.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.sahip.bakingapp.R;
import com.example.sahip.bakingapp.database.TinyDB;
import com.example.sahip.bakingapp.models.Ingredient;
import com.example.sahip.bakingapp.models.Recipe;
import com.example.sahip.bakingapp.models.Step;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeDetailFragment extends Fragment {
    public static final String TAG = RecipeDetailFragment.class.getSimpleName();
    public static final String RECIPES_KEY = "recipes";
    public static final String RECIPE_POSITION = "position";

    @BindView(R.id.steps_listview) ListView listView;
    @BindView(R.id.ingredients_expandable_listview) ExpandableListView expandableListView;
    private TextView recipeNameTv;
    private ExpandableListAdapter expandableListAdapter;
    private StepsAdapter stepsAdapter;
    private ArrayList<Step> mStepList = new ArrayList<Step>();
    private List<String> mHeaderList;
    private HashMap<String, List<String>> mItemList;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false );

        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        if(args != null){
            int position = args.getInt(RECIPE_POSITION, 0);

            TinyDB tiny = new TinyDB(getContext());

            ArrayList<Object> recipeObjects = tiny.getListObject(RECIPES_KEY, Recipe.class);
            ArrayList<Recipe> mRecipeList = new ArrayList<Recipe>();

            for(Object objs : recipeObjects){
                mRecipeList.add((Recipe) objs);
            }

            // Get reference to the seleted recipe
            Recipe recipe = mRecipeList.get(position);

            // Prepare the list data
            prepareListData(recipe);

            expandableListAdapter = new ExpandableListAdapter(getActivity(), mHeaderList, mItemList);
            stepsAdapter = new StepsAdapter(getActivity(), (ArrayList<Step>) recipe.getSteps());

            // Setting list adapter
            expandableListView.setAdapter(expandableListAdapter);
            listView.setAdapter(stepsAdapter);



            // Set action bar title as the name of the recipe
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(recipe.getName());
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void prepareListData(Recipe recipe) {

        mHeaderList = new ArrayList<String>();
        mItemList = new HashMap<String, List<String>>();

        mHeaderList.add("SEE INGREDIENTS");

        List<String> ingredients = new ArrayList<String>();

        for(Ingredient i : recipe.getIngredients()){
            String val = i.getQuantity().toString() + " " + i.getMeasure() + " " + i.getIngredient();
            ingredients.add(val);
        }
        mItemList.put(mHeaderList.get(0), ingredients);

    }
}
