package com.greycodes.excel14.fragment;

        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.content.res.TypedArray;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v4.view.ViewPager;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.Gallery;
        import android.widget.TextView;
        import android.widget.Toast;


        import com.greycodes.excel14.R;
        import com.greycodes.excel14.adapter.SliderPagerAdapter;
        import com.greycodes.excel14.enums.SliderFragmentType;
        import com.greycodes.excel14.model.Model;
        import com.greycodes.excel14.pojo.GalleryImages;
        import com.greycodes.excel14.pojo.Images;

        import com.greycodes.excel14.adapter.GalleryImageAdapter;
        import com.greycodes.excel14.network.GalleryImagesApi;
        import com.greycodes.excel14.listener.BackButtonClickListener;

        import java.util.ArrayList;
        import java.util.List;

        import retrofit.Callback;
        import retrofit.RestAdapter;
        import retrofit.RetrofitError;
        import retrofit.client.Response;

public class GalleryFragment extends Fragment {

    Context context;
    TypedArray typedArray;
    Gallery gallery;

    int imageIndex = 0;

    ViewPager viewPager;
    private SliderPagerAdapter sliderPagerAdapter;

    private BackButtonClickListener listener;

    public GalleryFragment()
    {
    }

    @SuppressLint("ValidFragment")
    public GalleryFragment(Context context,TypedArray typedArray,BackButtonClickListener listener) {
        this.context = context;
        this.typedArray = typedArray;
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        TextView view1=(TextView) view.findViewById(R.id.date);
        TextView view2=(TextView) view.findViewById(R.id.logolaunch);
        Typeface face1= Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/Roboto-Thin.ttf");
        view1.setTypeface(face1);
        view2.setTypeface(face1);
        try {
            setBackButtonOverriding(view);
            setUpView(view);
            setImages(view);
        }
        catch (Exception e) {

        }
        return view;
    }

    private void setBackButtonOverriding(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {



                    listener.BackButtonClicked(0);
//                      getFragmentManager().popBackStack();

                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void setUpView(final View view) {
        viewPager = (ViewPager) view.findViewById(R.id.view_pager_slider_gallery);
        gallery = (Gallery) view.findViewById(R.id.gallery1);
    }

    private void setImages(final View view) {
        final RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("http://balumenon.net63.net/app/").build();

        GalleryImagesApi imagesapi = restadapter.create(GalleryImagesApi.class);

        imagesapi.getData(new Callback<GalleryImages>(){
            @Override
            public void success(GalleryImages object, Response response) {
                if(object.getStatus()==1) {
                    addImages(object);
                    setUpPager(view);
                    setUpGallery();
                }
                else {
                    Toast.makeText(context, "Sorry!! No images uploaded", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, "Failed to connect to internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addImages(GalleryImages object) {
        Images[] images = object.getImages();
        Model.getInstance().setImageUrls(null);
        for (Images image : images) {
            Model.getInstance().getImageUrls().add(image.getUrl());
            Model.getInstance().getAuthors().add(image.getAuthor());
        }
    }


    private void setUpPager(View view) {
        try {

            List<Fragment> fragments = getFragments();
            sliderPagerAdapter = new SliderPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
            viewPager.setAdapter(sliderPagerAdapter);
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    imageIndex = position;
                    setViewPagerItem();
                    gallery.setSelection(imageIndex);
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<>();
        for(int i =0;i<Model.getInstance().getImageUrls().size();i++)
        {
            try {
                fList.add(new SliderFragment(context,i, SliderFragmentType.GalleryFragmentType));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fList;
    }

    private void setUpGallery() {
        gallery.setAdapter(new GalleryImageAdapter(context, typedArray,gallery.getMeasuredHeight()));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                imageIndex = position;
                setViewPagerItem();
            }
        });
    }

    private void setViewPagerItem() {
        if (imageIndex >= Model.getInstance().getImageUrls().size()) {
            imageIndex = 0;
        }
        viewPager.setCurrentItem(imageIndex, true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}