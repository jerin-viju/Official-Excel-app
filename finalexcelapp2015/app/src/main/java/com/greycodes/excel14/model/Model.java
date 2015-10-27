package com.greycodes.excel14.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;


import com.greycodes.excel14.R;
import com.greycodes.excel14.pojo.DaySchedule;

import com.greycodes.excel14.network.MenuApi;
import com.greycodes.excel14.network.ScheduleApi;
import com.greycodes.excel14.pojo.CommonCategoryItem;
import com.greycodes.excel14.pojo.CompetitionCategory;
import com.greycodes.excel14.pojo.CompetitionEvent;
import com.greycodes.excel14.pojo.EventContact;
import com.greycodes.excel14.pojo.EventFormat;
import com.greycodes.excel14.pojo.EventPrize;
import com.greycodes.excel14.pojo.EventSchedule;
import com.greycodes.excel14.pojo.InfoCategory;
import com.greycodes.excel14.pojo.ScheduleTiming;
import com.greycodes.excel14.pojo.TalkCategory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class Model
{
    private static Model ourInstance;

    private Context context;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<CompetitionCategory> competitionCategories;
    private ArrayList<TalkCategory> talkCategories;
    private ArrayList<CommonCategoryItem> proShowCategories;
    private ArrayList<CommonCategoryItem> excelProCategories;
    private ArrayList<CommonCategoryItem> initiativesCategories;
    private ArrayList<InfoCategory> infoCategories;
    private ArrayList<DaySchedule> dayScheduleCategories;

    private ArrayList<String> imageUrls = new ArrayList<>();
    private ArrayList<String> authors = new ArrayList<>();

    boolean isCopied = false;
    public boolean isScheduleChanged = false;

    private Model() {}

    public static Model getInstance() {
        if (ourInstance == null)
        {
            ourInstance = new Model();
        }
        return ourInstance;
    }

    public static Model getInstance(Context context) {
        Model instance = getInstance();
        instance.context = context;
        instance.sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);
        if (!instance.isCopied)
        {
            instance.readMenuData();
            instance.fetchScheduleData();
            instance.isCopied = true;
        }
        return instance;
    }

    public boolean getLoginState()
    {
        return sharedPreferences.getBoolean(context.getString(R.string.shared_preferences_login_state_key), false);
    }

    public String getLoginUserKey()
    {
        return sharedPreferences.getString(context.getString(R.string.shared_preferences_login_user_key), "Guest");
    }

    public String getLoginUserIdKey()
    {
        return sharedPreferences.getString(context.getString(R.string.shared_preferences_login_userid_key),"0");
    }

    public String getLoginUserEmailKey()
    {
        return sharedPreferences.getString(context.getString(R.string.shared_preferences_login_email_key)," ");
    }

    public void setLoginState(boolean value,String username,String email,String user_id)
    {
        editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.shared_preferences_login_state_key), value); // value to store
        editor.putString(context.getString(R.string.shared_preferences_login_user_key), username);
        editor.putString(context.getString(R.string.shared_preferences_login_email_key),email);
        editor.putString(context.getString(R.string.shared_preferences_login_userid_key),user_id);
        editor.commit();
    }

    public boolean getScheduleDownloadedStatus() {
        return sharedPreferences.getBoolean(context.getString(R.string.shared_preferences_schedule_downloaded_key), false);
    }

    public void setScheduleDownloadedStatus(boolean value) {
        editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.shared_preferences_schedule_downloaded_key), value); // value to store
        editor.commit();
    }

    public int getMenuVersionCode() {
        return sharedPreferences.getInt(context.getString(R.string.shared_preferences_menu_version_key), 1);
    }

    public void setMenuVersionCode(int version) {
        editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.shared_preferences_menu_version_key), version); // value to store
        editor.commit();
    }

    public ArrayList<CompetitionCategory> getCompetitionCategories() {
        return competitionCategories;
    }

    public ArrayList<TalkCategory> getTalkCategories() {
        return talkCategories;
    }

    public ArrayList<CommonCategoryItem> getProShowCategories() {
        return proShowCategories;
    }

    public ArrayList<CommonCategoryItem> getExcelProCategories() {
        return excelProCategories;
    }

    public ArrayList<CommonCategoryItem> getInitiativesCategories() {
        return initiativesCategories;
    }

    public ArrayList<InfoCategory> getInfoCategories() {
        return infoCategories;
    }

    public ArrayList<DaySchedule> getDayScheduleCategories() {
        return dayScheduleCategories;
    }


    public void addCompetitionCategory(CompetitionCategory competitionCategory)
    {
        if (competitionCategories == null)
        {
            competitionCategories = new ArrayList<>();
        }
        competitionCategories.add(competitionCategory);
    }

    public void addTalkCategory(TalkCategory talkCategory)
    {
        if (talkCategories == null)
        {
            talkCategories = new ArrayList<>();
        }
        talkCategories.add(talkCategory);
    }

    public void addProShowCategory(CommonCategoryItem proshowCategory)
    {
        if(proShowCategories == null)
        {
            proShowCategories = new ArrayList<>();
        }
        proShowCategories.add(proshowCategory);
    }

    public void addExcelProCategory(CommonCategoryItem excelProCategory)
    {
        if(excelProCategories == null)
        {
            excelProCategories = new ArrayList<>();
        }
        excelProCategories.add(excelProCategory);
    }

    public void addInitiativesCategory(CommonCategoryItem initiativesCategory)
    {
        if(initiativesCategories == null)
        {
            initiativesCategories = new ArrayList<>();
        }
        initiativesCategories.add(initiativesCategory);
    }

    public void addInfoCategory(InfoCategory infoCategory)
    {
        if(infoCategories == null)
        {
            infoCategories = new ArrayList<>();
        }
        infoCategories.add(infoCategory);
    }

    public void addDayScheduleCategory(DaySchedule daySchedule)
    {
        if(dayScheduleCategories == null)
        {
            dayScheduleCategories = new ArrayList<>();
        }
        dayScheduleCategories.add(daySchedule);
    }

    private void readMenuData()
    {
        final File file = new File(context.getFilesDir(), context.getString(R.string.menu_json_file_name));
        if (!file.exists())
        {
            copyAsset(context.getString(R.string.menu_json_file_name));
        }
        try {
            FileInputStream inputStream = new FileInputStream(file);
            loadMenu(convertStreamToString(inputStream));
            inputStream.close();
            fetchMenuData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchMenuData() {
       final RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("http://balumenon.net63.net/app/").build();

        final MenuApi menuApi = restadapter.create(MenuApi.class);
        menuApi.getData(new Callback<Response>() {
            @Override
            public void success(Response object, Response response) {
                String string = new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    JSONObject menuObject = new JSONObject(string);
                    int version = menuObject.getInt(context.getString(R.string.competitions_json_version_key));
                    if (version > getMenuVersionCode()) {
                        competitionCategories = null;
                        talkCategories = null;
                        proShowCategories = null;
                        excelProCategories = null;
                        initiativesCategories = null;
                        infoCategories = null;
                        loadMenu(string);
                        overWriteMenuFile(string);
                        setMenuVersionCode(version);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("Error");
            }
        });
    }

    private void overWriteMenuFile(String string) throws IOException {
        File file = new File(context.getFilesDir(), context.getString(R.string.menu_json_file_name));
        FileWriter fileWriter = new FileWriter(file, false); // true to append
        // false to overwrite.
        fileWriter.write(string);
        fileWriter.close();
    }

    private void fetchScheduleData()
    {
        if(!this.getScheduleDownloadedStatus()) {
            final RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("http://balumenon.net63.net/app/").build();

            ScheduleApi scheduleApi = restadapter.create(ScheduleApi.class);
            scheduleApi.getData(new Callback<Response>() {
                @Override
                public void success(Response object, Response response) {
                    String string = new String(((TypedByteArray) response.getBody()).getBytes());
                    try {
                        JSONObject object1 = new JSONObject(string);
                        int status = object1.getInt("status");
                        if(status==1) {
                            writeToExternalStorage(string);
                            loadSchedule(string);
                            setScheduleDownloadedStatus(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
        else {
            readScheduleData();
        }
    }

    private void readScheduleData()
    {
        final File file = new File(context.getFilesDir(), context.getString(R.string.schedule_json_file_name));
//        if (!file.exists())
//         {
//            copyAsset(context.getString(R.string.schedule_json_file_name));
//         }
        try {
            FileInputStream inputStream = new FileInputStream(file);
            loadSchedule(convertStreamToString(inputStream));
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertStreamToString(InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    private void loadMenu(String menuString)
    {
        try {
            JSONObject menuObject = new JSONObject(menuString);
            JSONArray mainArray = menuObject.getJSONArray(context.getString(R.string.competitions_json_main_array_key));
            for (int i = 0 ; i < mainArray.length() ; i++)
            {
                addCompetitionCategory(getCategory(mainArray.getJSONObject(i)));
            }
            JSONArray talksArray = menuObject.getJSONArray(context.getString(R.string.talks_json_main_array_key));
            for(int i = 0 ; i<talksArray.length(); i++)
            {
                addTalkCategory(getTalkCategory(talksArray.getJSONObject(i)));
            }
            JSONArray proShowArray = menuObject.getJSONArray(context.getString(R.string.proshow_json_main_array_key));
            for(int i = 0 ; i<proShowArray.length(); i++)
            {
                addProShowCategory(getCommonCategoryItem(proShowArray.getJSONObject(i)));
            }
            JSONArray excelProArray = menuObject.getJSONArray(context.getString(R.string.excelpro_json_main_array_key));
            for(int i = 0 ; i<excelProArray.length(); i++)
            {
                addExcelProCategory(getCommonCategoryItem(excelProArray.getJSONObject(i)));
            }
            JSONArray initiativesArray = menuObject.getJSONArray(context.getString(R.string.initialtives_json_main_array_key));
            for(int i = 0 ; i<initiativesArray.length(); i++)
            {
                addInitiativesCategory(getCommonCategoryItem(initiativesArray.getJSONObject(i)));
            }
            JSONArray infoArray = menuObject.getJSONArray(context.getString(R.string.info_json_main_array_key));
            for(int i = 0 ; i<infoArray.length(); i++)
            {
                addInfoCategory(getInfoCategory(infoArray.getJSONObject(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CompetitionCategory getCategory(JSONObject categoryObject) throws Exception {
        CompetitionCategory category = new CompetitionCategory();
        category.setId(categoryObject.getString(context.getString(R.string.category_json_id_key)));
        category.setTitle(categoryObject.getString(context.getString(R.string.category_json_title_key)));
        category.setImageURL(categoryObject.getString(context.getString(R.string.category_json_image_url_key)));
        //category.setColor(categoryObject.getString(context.getString(R.string.category_json_color_key)));
        JSONArray eventArray = categoryObject.getJSONArray(context.getString(R.string.category_json_events_key));
        for (int i = 0 ; i < eventArray.length() ; i++)
        {
            category.addEvent(getEvent(eventArray.getJSONObject(i)));
        }
        return category;
    }

    private CompetitionEvent getEvent(JSONObject eventObject) throws Exception {
        CompetitionEvent event = new CompetitionEvent();
        event.setId(eventObject.getString(context.getString(R.string.event_json_id_key)));
        event.setTitle(eventObject.getString(context.getString(R.string.event_json_title_key)));
        event.setImage(eventObject.getString(context.getString(R.string.event_json_image_url_key)));
        event.setAbout(eventObject.getString(context.getString(R.string.event_json_about_key)));

        JSONArray eventFormatArray = eventObject.getJSONArray(context.getString(R.string.event_json_eventformat_key));
        for (int i = 0 ; i < eventFormatArray.length() ; i++)
        {
            event.addEventFormat(getEventFormat(eventFormatArray.getJSONObject(i)));
        }
        JSONArray contactArray = eventObject.getJSONArray(context.getString(R.string.event_json_contacts_key));
        for (int i = 0 ; i < contactArray.length() ; i++)
        {
            event.addContact(getContact(contactArray.getJSONObject(i)));
        }
        JSONArray rulesArray = eventObject.getJSONArray(context.getString(R.string.event_json_rules_key));
        for (int i = 0 ; i < rulesArray.length() ; i++)
        {
            event.addRule(rulesArray.getString(i));
        }
        event.setSchedule(getSchedule(eventObject.getJSONObject(context.getString(R.string.event_json_schedule_key))));
        event.setPrize(getPrize(eventObject.getJSONObject(context.getString(R.string.event_json_prize_key))));
        try {
            event.setEventLink(eventObject.getString(context.getString(R.string.event_json_eventlink_key)));
        }
        catch (Exception e) {

        }
        return event;
    }

    private EventFormat getEventFormat(JSONObject eventFormatObject) throws Exception
    {
        EventFormat format = new EventFormat();
        format.setTitle(eventFormatObject.getString(context.getString(R.string.commonitem_json_title_key)));
        format.setDescription(eventFormatObject.getString(context.getString(R.string.commonitem_json_description_key)));
        return format;
    }

    private EventContact getContact(JSONObject contactObject) throws Exception
    {
        EventContact contact = new EventContact();
        contact.setName(contactObject.getString(context.getString(R.string.contact_json_name_key)));
        contact.setPhno(contactObject.getString(context.getString(R.string.contact_json_phno_key)));
        contact.setEmail(contactObject.getString(context.getString(R.string.contact_json_email_key)));
        return contact;
    }

    private EventSchedule getSchedule(JSONObject scheduleObject) throws Exception
    {
        EventSchedule schedule = new EventSchedule();
        schedule.setDate(scheduleObject.getString(context.getString(R.string.schedule_json_date_key)));
        schedule.setTime(scheduleObject.getString(context.getString(R.string.schedule_json_time_key)));
        schedule.setVenue(scheduleObject.getString(context.getString(R.string.schedule_json_venue_key)));
        return schedule;
    }

    private EventPrize getPrize(JSONObject prizeObject) throws Exception
    {
        EventPrize prize = new EventPrize();
        prize.setFirst(prizeObject.getString(context.getString(R.string.prize_json_first_key)));
        prize.setSecond(prizeObject.getString(context.getString(R.string.prize_json_second_key)));
        prize.setThird(prizeObject.getString(context.getString(R.string.prize_json_third_key)));
        return prize;
    }

    private TalkCategory getTalkCategory(JSONObject categoryObject) throws Exception {
        TalkCategory category = new TalkCategory();
        category.setTitle(categoryObject.getString(context.getString(R.string.category_json_title_key)));
        category.setImageId(categoryObject.getString(context.getString(R.string.category_json_image_url_key)));
        //category.setColor(categoryObject.getString(context.getString(R.string.category_json_color_key)));
        category.setAbout(categoryObject.getString(context.getString(R.string.event_json_about_key)));
        category.setRegistration(categoryObject.getString(context.getString(R.string.event_json_registration_key)));
        category.setSchedule(getSchedule(categoryObject.getJSONObject(context.getString(R.string.event_json_schedule_key))));

        JSONArray speakersArray = categoryObject.getJSONArray(context.getString(R.string.category_json_speakers_key));
        for (int i = 0 ; i < speakersArray.length() ; i++)
        {
            category.addSpeaker(getCommonCategoryItem(speakersArray.getJSONObject(i)));
        }
        return category;
    }

    private InfoCategory getInfoCategory(JSONObject categoryObject) throws Exception {
        InfoCategory category = new InfoCategory();
        category.setTitle(categoryObject.getString(context.getString(R.string.category_json_title_key)));
        category.setImage(categoryObject.getString(context.getString(R.string.category_json_image_url_key)));
        //category.setColor(categoryObject.getString(context.getString(R.string.category_json_color_key)));
        JSONArray listArray = categoryObject.getJSONArray(context.getString(R.string.category_json_list_key));
        for (int i = 0 ; i < listArray.length() ; i++)
        {
            category.addtoInfoList(getCommonCategoryItem(listArray.getJSONObject(i)));
        }
        return category;
    }

    private CommonCategoryItem getCommonCategoryItem(JSONObject object) throws Exception {
        CommonCategoryItem item = new CommonCategoryItem();
        item.setTitle(object.getString(context.getString(R.string.commonitem_json_title_key)));
        item.setImage(object.getString(context.getString(R.string.commonitem_json_image_key)));
       // item.setColor(object.getString(context.getString(R.string.category_json_color_key)));
        item.setDescription(object.getString(context.getString(R.string.commonitem_json_description_key)));
        return item;
    }

    private void loadSchedule(String scheduleString) {
        try {
            JSONObject schedulesObject = new JSONObject(scheduleString);
            JSONArray scheduleArray = schedulesObject.getJSONArray(context.getString(R.string.schedule_json_main_array_key));
            for (int i = 0 ; i < scheduleArray.length() ; i++)
            {
                addDayScheduleCategory(getDayScheduleCategory(scheduleArray.getJSONObject(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DaySchedule getDayScheduleCategory(JSONObject dayScheduleObject) throws Exception{
        DaySchedule schedule = new DaySchedule();
        schedule.setDate(dayScheduleObject.getString(context.getString(R.string.schedule_json_date_key)));
        schedule.setDay(dayScheduleObject.getString(context.getString(R.string.schedule_json_day_key)));
        JSONArray timingsArray = dayScheduleObject.getJSONArray(context.getString(R.string.schedule_json_timings_key));
        for (int i = 0 ; i < timingsArray.length() ; i++)
        {
            schedule.addTiming(getTiming(timingsArray.getJSONObject(i)));
        }
        return schedule;
    }

    private ScheduleTiming getTiming(JSONObject timingObject) throws Exception{
        ScheduleTiming timing = new ScheduleTiming();
        timing.setTime(timingObject.getString(context.getString(R.string.schedule_json_time_key)));
        JSONArray eventsArray = timingObject.getJSONArray(context.getString(R.string.category_json_events_key));
        for (int i = 0 ; i < eventsArray.length() ; i++)
        {
            timing.addScheduleEvent(getScheduleEvent(eventsArray.getJSONObject(i)));
        }
        return timing;
    }

    private CompetitionEvent getScheduleEvent(JSONObject eventObject) throws Exception{
        CompetitionEvent event = new CompetitionEvent();
        event.setId(eventObject.getString(context.getString(R.string.event_json_id_key)));
        event.setTitle(eventObject.getString(context.getString(R.string.event_json_title_key)));
        event.setGoing(eventObject.getBoolean(context.getString(R.string.event_json_going_key)));
        return event;
    }

    private void copyAsset(String fileName)
    {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream;
        OutputStream outputStream;
        try {
            inputStream = assetManager.open(fileName);
            File outFile = new File(context.getFilesDir(), fileName);
            outputStream = new FileOutputStream(outFile);
            copyFile(inputStream, outputStream);
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyFile(InputStream inputStream, OutputStream outputStream) throws Exception {
        byte[] buffer = new byte[1024];
        int read;
        while((read = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, read);
        }
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
        if(imageUrls == null)
            this.imageUrls = new ArrayList<>();
    }

    public void appExited() {
        if(isScheduleChanged) {
            String jsonString = writeJSONkeys();
            writeToExternalStorage(jsonString);
        }
    }

    private String writeJSONkeys() {
        JSONObject object = new JSONObject();
        try {
              object.put("schedule",writeScheduleJSON());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    private JSONArray writeScheduleJSON() throws Exception{
        JSONArray jArray = new JSONArray();
        for(int i=0; i<getDayScheduleCategories().size(); i++) {
            JSONObject jObject = new JSONObject();
            DaySchedule schedule = getDayScheduleCategories().get(i);
            jObject.put("date",schedule.getDate());
            jObject.put("day", schedule.getDay());
            jObject.put("timings", writeTimingsJSON(i));
            jArray.put(i, jObject);
        }
        return jArray;
    }

    private JSONArray writeTimingsJSON(int schedulePosition) throws Exception{
        JSONArray jArray = new JSONArray();
        for(int i=0; i<getDayScheduleCategories().get(schedulePosition).getTimingsArrayList().size(); i++) {
            JSONObject jObject = new JSONObject();
            ScheduleTiming timing = getDayScheduleCategories().get(schedulePosition).getTimingsArrayList().get(i);
            jObject.put("time",timing.getTime());
            jObject.put("events", writeEventsJSON(timing.getScheduleEvents()));
            jArray.put(i, jObject);
        }
        return jArray;
    }

    private JSONArray writeEventsJSON(ArrayList<CompetitionEvent> events) throws Exception{
        JSONArray jArray = new JSONArray();
        for(int i=0; i<events.size(); i++) {
            JSONObject jObject = new JSONObject();
            jObject.put("title", events.get(i).getTitle());
            jObject.put("id",events.get(i).getId());
            jObject.put("going",events.get(i).getGoing());
            jArray.put(i, jObject);
        }
        return jArray;
    }

    private void writeToExternalStorage(String jsonString) {
        final File file = new File(context.getFilesDir(), context.getString(R.string.schedule_json_file_name));
        try {
//            FileInputStream inputStream = new FileInputStream(file);
//            loadMenu(convertStreamToString(inputStream));
//            inputStream.close();
            FileOutputStream out = new FileOutputStream(file);
            out.write(jsonString.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
