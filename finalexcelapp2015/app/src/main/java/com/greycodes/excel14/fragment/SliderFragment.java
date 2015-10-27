package com.greycodes.excel14.fragment;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.greycodes.excel14.R;
import com.greycodes.excel14.enums.SliderFragmentType;
import com.greycodes.excel14.model.Model;
import com.greycodes.excel14.network.ParticipateApi;
import com.greycodes.excel14.network.ParticipateNewGroupApi;
import com.greycodes.excel14.pojo.CompetitionEvent;
import com.greycodes.excel14.pojo.EventContact;
import com.greycodes.excel14.pojo.EventFormat;
import com.greycodes.excel14.pojo.EventPrize;
import com.greycodes.excel14.pojo.EventSchedule;
import com.greycodes.excel14.pojo.ParticipateResponse;
import com.greycodes.excel14.pojo.ScheduleTiming;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


@SuppressLint("ValidFragment")
public class SliderFragment extends Fragment {

    Context context;
    int position,parentPosition;
    SliderFragmentType sliderFragmentType;
    public static final String EXTRA_IMAGE_KEY = "EXTRA_IMAGE_KEY";
    public int RESID =0;
    public SliderFragment() {

    }

    public SliderFragment(int resId,SliderFragmentType fragmentType)
    {
        RESID = resId;
        this.sliderFragmentType = fragmentType;
    }
//    public SliderFragment(Bitmap image,SliderFragmentType fragmentType)
//    {
//        Bundle bundle = new Bundle(1);
//        bundle.putParcelable(EXTRA_IMAGE_KEY, image);
//        this.setArguments(bundle);
//        this.sliderFragmentType = fragmentType;
//    }

    public SliderFragment(Context context,int position,SliderFragmentType fragmentType)
    {
        this.context = context;
        this.position = position;
        this.sliderFragmentType = fragmentType;
    }

    public void setParentPosition(int parentPosition)
    {
        this.parentPosition = parentPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = null;
        if (sliderFragmentType == null)
        {
            sliderFragmentType = SliderFragmentType.InitialSliderFragmentType;
        }
        switch (sliderFragmentType) {
            case GalleryFragmentType:
                view = inflater.inflate(R.layout.fragment_gallery_slider, container, false);
                ImageView imageView = (ImageView) view.findViewById(R.id.slider_pager_image);
                Picasso.with(context).load(Model.getInstance().getImageUrls().get(position)).fit().into(imageView);
                imageView.setHorizontalFadingEdgeEnabled(true);
                imageView.setVerticalFadingEdgeEnabled(true);
                imageView.setFadingEdgeLength(40);
                return view;
            case InitialSliderFragmentType:
                view = inflater.inflate(R.layout.fragment_gallery_slider, container, false);
                ImageView imageView1 = (ImageView) view.findViewById(R.id.slider_pager_image);
                imageView1.setHorizontalFadingEdgeEnabled(true);
                imageView1.setVerticalFadingEdgeEnabled(true);
                imageView1.setFadingEdgeLength(40);
                if(RESID!=0)
                imageView1.setImageResource(RESID);
               // imageView1.setImageBitmap(getArguments().<Bitmap>getParcelable(EXTRA_IMAGE_KEY));

                return view;
            case SingleCompetitionFragmentType:
                view = inflater.inflate(R.layout.fragment_single_competition, container, false);
                TextView eventTitle = (TextView)view.findViewById(R.id.eventTitle);
                eventTitle.setText(Model.getInstance().getCompetitionCategories().get(parentPosition).getEvents().get(position).getTitle());
                Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
                eventTitle.setTypeface(type);
                setFloatingButton(view);
                setTextDescriptions(view);
                return view;
            case FloorMapSliderFragment:
                view = inflater.inflate(R.layout.fragment_gallery_slider, container, false);
                ImageView imageView2 = (ImageView) view.findViewById(R.id.slider_pager_image);
                imageView2.setHorizontalFadingEdgeEnabled(true);
                imageView2.setVerticalFadingEdgeEnabled(true);
                imageView2.setFadingEdgeLength(40);
                if(RESID!=0)
                imageView2.setImageResource(RESID);
                //imageView2.setImageBitmap(getArguments().<Bitmap>getParcelable(EXTRA_IMAGE_KEY));
                return view;
        }
        return null;
    }

    private void setFloatingButton(View view) {
        Button floatingButton = (Button)view.findViewById(R.id.participateFloatingButton);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAlert myAlert = new MyAlert();
                myAlert.setButtonClickListener(new MyAlert.ButtonClickListener() {
                    @Override
                    public void onClick(int option) {
                        switch (option) {
                            case 0:
                                setGoing(true);
                                break;
                            case 1:
                                setGoing(false);
                        }
                    }
                });
                myAlert.show(getActivity().getFragmentManager(), "A");
            }
        });
    }

    private void setGoing(boolean value) {
        if(!value)
        {
        }
        //else if(value && Model.getInstance().getScheduleDownloadedStatus()) {
        else if(value) {
            if(Model.getInstance().getScheduleDownloadedStatus())
                addToSchedule(value,Model.getInstance().getCompetitionCategories().get(parentPosition).getEvents().get(position));
            final RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("http://balumenon.net63.net/app/").build();
            final ParticipateApi participateApi = restadapter.create(ParticipateApi.class);
            Map<String, String> params = new HashMap<String, String>();
            params.put("user_id", Model.getInstance().getLoginUserIdKey());
            params.put("comp_id", Model.getInstance().getCompetitionCategories().get(parentPosition).getEvents().get(position).getId());
            params.put("login", "login");
            ;
            participateApi.getData(params, new Callback<Response>() {
                @Override
                public void success(Response object, Response response) {
                    String string = new String(((TypedByteArray) response.getBody()).getBytes());
                    try {
                        JSONObject menuObject = new JSONObject(string);
                        int status = menuObject.getInt("status");
                        switch (status) {
                            case 10:ParticipateAlert alert = new ParticipateAlert(parentPosition,position);
                                alert.show(getActivity().getFragmentManager(), "A");
                                break;
                            case 1:Toast.makeText(getActivity().getApplicationContext(),"Your registration has been sucesfull",Toast.LENGTH_SHORT).show();
                                break;
                            default:Toast.makeText(getActivity().getApplicationContext(),"Please login",Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                    catch (Exception e) {

                    }

                }
                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to connect to internet", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            Toast.makeText(getActivity().getApplicationContext(),"This feature will be updated soon",Toast.LENGTH_SHORT).show();
        }
    }

    private void addToSchedule(boolean value,CompetitionEvent event) {
        int flag=0;
        if(Model.getInstance().getDayScheduleCategories()!=null) {
            for(int i=0;i<3;i++)
            {
                ArrayList<ScheduleTiming> timingsList = Model.getInstance().getDayScheduleCategories().get(i).getTimingsArrayList();
                for(int j=0;j<timingsList.size();j++) {
                    ArrayList<CompetitionEvent> events = timingsList.get(j).getScheduleEvents();
                    for(int k =0;k<events.size();k++) {
                        if(events.get(k).getId().equals(event.getId())) {
                            if(value) {
                                if(!Model.getInstance().getDayScheduleCategories().get(i).getTimingsArrayList().get(j).getScheduleEvents().get(k).getGoing()) {
                                    Model.getInstance().isScheduleChanged = true;
                                    Model.getInstance().getDayScheduleCategories().get(i).getTimingsArrayList().get(j).getScheduleEvents().get(k).setGoing(true);
                                }
                            }
                            else {
                                if(Model.getInstance().getDayScheduleCategories().get(i).getTimingsArrayList().get(j).getScheduleEvents().get(k).getGoing()) {
                                    Model.getInstance().isScheduleChanged = true;
                                    Model.getInstance().getDayScheduleCategories().get(i).getTimingsArrayList().get(j).getScheduleEvents().get(k).setGoing(false);
                                }
                            }
                            flag=1;
                            break;
                        }
                    }
                    if(flag==1)
                        break;
                }
                if(flag==1)
                    break;
            }
        }
    }

    private void setTextDescriptions(View view) {
        final TextView description =(TextView)view.findViewById(R.id.descriptionTextView);
        final TextView description2 =(TextView)view.findViewById(R.id.descriptionTextViewrules);
        final TextView description3 =(TextView)view.findViewById(R.id.descriptionTextViewrules2);
        Button contactButton = (Button)view.findViewById(R.id.contactButton);
        Button eventFormatButton = (Button)view.findViewById(R.id.eventFormatButton);
        Button rulesButton = (Button)view.findViewById(R.id.rulesButton);
        Button venueButton = (Button) view.findViewById(R.id.venueButton);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");

        final Button eventLinkButton = (Button) view.findViewById(R.id.eventLink);
        Typeface type1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");

        description.setTypeface(type1);
        description2.setTypeface(type1);
        description3.setTypeface(type1);

        final CompetitionEvent event = Model.getInstance().getCompetitionCategories().get(parentPosition).getEvents().get(position);


        eventLinkButton.setText(event.getEventLink());
        //eventLinkButton.setText("www.stackoverflow.com");

        String rulestext = "";
        String string="";
        for(int i=0;i<event.getRules().size();i++)
        {
            rulestext = rulestext + event.getRules().get(i);
        }
        String text = "<br><br>"+event.getAbout()+"<br><br>"+"<br><b>EVENT FORMAT</b>";
        for(int i=0;i<event.getEventFormats().size();i++)
        {
            String title="";

            EventFormat format = event.getEventFormats().get(i);
            title=""+format.getTitle()+"";

           string = string+"\n"+title +"\n"+format.getDescription();
        }


        description.setVisibility(View.VISIBLE);
        description2.setVisibility(View.GONE);
        description3.setVisibility(View.VISIBLE);
        description.setText(Html.fromHtml(text));
        description3.setText(string);

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description2.setVisibility(View.GONE);
                description.setVisibility(View.VISIBLE);
                description3.setVisibility(View.GONE);
                String text = "";
                for (int i = 0; i < event.getContacts().size(); i++) {
                    EventContact contact1 = event.getContacts().get(i);
                    text = text + "<br><br>" +"<b>"+ contact1.getName()+"</b>" + "<br>" + contact1.getPhno() + "<br>" + contact1.getEmail();
                }
                description.setText(Html.fromHtml(text));
            }
        });
        final String stupid=text;
        final String Bledy=string;
        eventFormatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description2.setVisibility(View.GONE);
                description.setVisibility(View.VISIBLE);
                description3.setVisibility(View.VISIBLE);

                description3.setText(Html.fromHtml(Bledy));

                description.setText(Html.fromHtml(stupid));
            }
        });
        venueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description2.setVisibility(View.GONE);
                description.setVisibility(View.VISIBLE);
                description3.setVisibility(View.GONE);
                String text ="<br>"+"<b>SCHEDULE</b>"+"<br>";
                EventSchedule schedule = event.getSchedule();
                text+=schedule.getDate()+"<br>"+schedule.getTime()+"<br>"+schedule.getVenue();
                text+="<br><br><b>PRIZES</b><br>";
                EventPrize prize = event.getPrize();
                text+=prize.getFirst();
                description.setText(Html.fromHtml(text));
            }
        });
        final String finalRulestext = rulestext;
        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setVisibility(View.GONE);
                description3.setVisibility(View.GONE);
                description2.setVisibility(View.VISIBLE);
                description2.setText(finalRulestext);
            }
        });

        eventLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = eventLinkButton.getText().toString();
                if(link.equals("")|| link.equals(" ")) {

                }
                else {

                    Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                  /*  Intent internetIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(link));
                    internetIntent.setComponent(new ComponentName("com.android.browser","com.android.browser.BrowserActivity"));
                    internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(internetIntent);
                    */
                }

            }
        });
    }

}