package com.example.volleypractice.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.volleypractice.MainActivity;
import com.example.volleypractice.data.AnswerListAsyncResponse;
import com.example.volleypractice.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.example.volleypractice.controller.AppController.TAG;

public class HandleAsynceTasks extends AsyncTask<String,Void,String> {

    public ArrayList<Question> questionArrayList=new ArrayList<>();

    public AnswerListAsyncResponse delegate=null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("process start");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("process finished");
        delegate.processFinished(questionArrayList);

    }

    @Override
    protected String doInBackground(String... strings) {

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET, strings[0], null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Question question=new Question();



                        for (int i = 0; i < response.length(); i++) {
                            try {
                                question.setAnswer(response.getJSONArray(i).get(0).toString());
                                question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));

                                questionArrayList.add(question);

//                                Log.d(TAG, "onResponse async: " + response.getJSONArray(i));




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

//                        if (null !=callBack) callBack.processFinished(questionArrayList);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);


        return null;
    }

}
