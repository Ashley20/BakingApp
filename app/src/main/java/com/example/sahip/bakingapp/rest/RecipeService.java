package com.example.sahip.bakingapp.rest;

import com.example.sahip.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET
    Call<List<Recipe>> getAllRecipes();
}
