package com.example.volleypractice.data;

import com.example.volleypractice.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
