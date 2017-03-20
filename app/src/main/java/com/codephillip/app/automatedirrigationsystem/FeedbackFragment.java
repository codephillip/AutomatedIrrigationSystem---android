package com.codephillip.app.automatedirrigationsystem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codephillip.app.automatedirrigationsystem.jsonmodels.feedbacks.Feedback;
import com.codephillip.app.automatedirrigationsystem.retrofit.ApiClient;
import com.codephillip.app.automatedirrigationsystem.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;


public class FeedbackFragment extends Fragment{

    private static final String TAG = FeedbackFragment.class.getSimpleName();
    EditText titleEditText, contentEditText;
    Button sendButton;
    FeedbackTask feedbackTask = null;

    public FeedbackFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedack, container, false);

        titleEditText = (EditText) rootView.findViewById(R.id.title);
        contentEditText = (EditText) rootView.findViewById(R.id.content);
        sendButton = (Button) rootView.findViewById(R.id.send_feedback_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedback();
            }
        });
        return rootView;
    }

    private void sendFeedback() {
        if (feedbackTask != null) {
            return;
        }

        // Reset errors.
        titleEditText.setError(null);
        contentEditText.setError(null);

        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();

        Log.d(TAG, "sendFeedback: " + title + "#" + content);

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(content)) {
            contentEditText.setError(getString(R.string.error_field_required));
            focusView = contentEditText;
            cancel = true;
        }

        if (TextUtils.isEmpty(title)) {
            titleEditText.setError(getString(R.string.error_field_required));
            focusView = titleEditText;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (Utils.isConnectedToInternet(getActivity())){
                feedbackTask = new FeedbackTask(title, content);
                feedbackTask.execute((Void) null);
            } else {
                Toast.makeText(getContext(), "Feedback not send. Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class FeedbackTask extends AsyncTask<Void, Void, Boolean> {

        private final String title;
        private final String content;

        FeedbackTask(String title, String content) {
            this.title = title;
            this.content = content;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                sendFeedback(title, content);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        private void sendFeedback(String topic, String message) {
            ApiInterface apiInterface = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);
            //todo get user id
            Feedback feedback = new Feedback(topic, message, 1);
            Call<Feedback> call = apiInterface.createFeedback(feedback);
            call.enqueue(new Callback<Feedback>() {
                @Override
                public void onResponse(Call<Feedback> call, retrofit2.Response<Feedback> response) {
                    int statusCode = response.code();
                    Log.d(TAG, "onResponse: #" + statusCode);
                }

                @Override
                public void onFailure(Call<Feedback> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                }
            });
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            feedbackTask = null;
            if (success)
                Toast.makeText(getContext(), "Thank you for your feedback", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "Feedback not send. Check your Internet connection", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            feedbackTask = null;
            Toast.makeText(getContext(), "Your feedback has been canceled", Toast.LENGTH_SHORT).show();
        }
    }
}
