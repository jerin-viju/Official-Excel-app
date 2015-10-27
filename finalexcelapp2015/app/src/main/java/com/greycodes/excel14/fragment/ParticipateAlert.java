package com.greycodes.excel14.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.greycodes.excel14.R;
import com.greycodes.excel14.model.Model;
import com.greycodes.excel14.network.ParticipateExistingGroupApi;
import com.greycodes.excel14.network.ParticipateNewGroupApi;
import com.greycodes.excel14.pojo.ParticipateResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by jincy on 28/8/15.
 */
public class ParticipateAlert extends DialogFragment {
    private ButtonClickListener listener;
    Button newGroupButton,existingGroupButton,okButton;
    EditText groupNumberEditText;
    TextView description;
    RelativeLayout alertHeader,alertBody;

    boolean existingGroupSelected = false;
    int parentPosition,position;

    public interface ButtonClickListener {
        void onClick(int option);
    }

    public void setButtonClickListener(ButtonClickListener listener) {
        this.listener = listener;
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//       // communicator=(Communicator)getActivity().getFragmentManager().findFragmentByTag("ABC");
//    }
    public ParticipateAlert() {

    }

    @SuppressLint("ValidFragment")
    public ParticipateAlert(int parentPosition,int position) {
        this.parentPosition = parentPosition;
        this.position = position;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.participate_alert, null);
        linkViews(view);
        setClickListeners();

        builder.setView(view);
        Dialog dialog=builder.create();
        return dialog;
    }

    private void linkViews(View view) {
        existingGroupButton=(Button)view.findViewById(R.id.btn_yes);
        newGroupButton=(Button)view.findViewById(R.id.btn_no);
        okButton = (Button)view.findViewById(R.id.okButton);
        groupNumberEditText = (EditText)view.findViewById(R.id.gruopNumberEditText);
        description = (TextView)view.findViewById(R.id.requestDescription);
        alertHeader = (RelativeLayout)view.findViewById(R.id.alertHeader);
        alertBody = (RelativeLayout)view.findViewById(R.id.alertBody);
    }

    private void setClickListeners() {
        existingGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                existingGroupSelected = true;
                alertHeader.setVisibility(View.GONE);
                alertBody.setVisibility(View.VISIBLE);
                description.setText(" ");

            }
        });

        newGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                existingGroupSelected = false;
                alertHeader.setVisibility(View.GONE);
                alertBody.setVisibility(View.VISIBLE);
                groupNumberEditText.setVisibility(View.GONE);
                sendNewGroupRequest();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(existingGroupSelected){
                    if(groupNumberEditText.getText().toString().equals("")|| groupNumberEditText.getText().toString().equals(" "))
                        Toast.makeText(getActivity().getApplicationContext(),"Enter a group number",Toast.LENGTH_SHORT).show();
                    else {
                        String groupNum = groupNumberEditText.getText().toString();
                        sendExistingGroupRequest(groupNum);
                        description.setText("Your Request has been send");
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dismiss();
                                //Do something after 100ms
                            }
                        }, 1000);
                    }
                }
                else
                    dismiss();
            }
        });
    }


    private void sendNewGroupRequest() {
        final RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("http://balumenon.net63.net/app/").build();
        final ParticipateNewGroupApi participateNewGroupApi = restadapter.create(ParticipateNewGroupApi.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", Model.getInstance().getLoginUserIdKey());
        params.put("comp_id", Model.getInstance().getCompetitionCategories().get(parentPosition).getEvents().get(position).getId());
        params.put("login", "login");
        ;
        participateNewGroupApi.getData(params, new Callback<ParticipateResponse>() {
            @Override
            public void success(ParticipateResponse participateResponse, Response response) {
                if (participateResponse.getStatus() == 1) {
                    description.setText("Your group id is :  "+participateResponse.getGroupid()+" . Save for future reference");
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            dismiss();
//                            //Do something after 100ms
//                        }
//                    }, 1000);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to select participate", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Failed to connect to internet", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    private void sendExistingGroupRequest(String groupNum) {
        final RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("http://balumenon.net63.net/app/").build();
        final ParticipateExistingGroupApi participateExistingGroupApi = restadapter.create(ParticipateExistingGroupApi.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", Model.getInstance().getLoginUserIdKey());
        params.put("group_id", groupNum);
        params.put("comp_id", Model.getInstance().getCompetitionCategories().get(parentPosition).getEvents().get(position).getId());
        params.put("login", "login");
        ;
        participateExistingGroupApi.getData(params, new Callback<Response>() {
            @Override
            public void success(Response object, Response response) {
                String string = new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    JSONObject menuObject = new JSONObject(string);
                    int status = menuObject.getInt("status");
                    if (status == 1) {
                        Toast.makeText(getActivity().getApplicationContext(),"You have succesfully joined group", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Error,Please try again", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
                catch (Exception e) {

                }
            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Failed to connect to internet", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
}
