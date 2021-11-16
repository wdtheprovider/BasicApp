
import static com.wdtheprovider.sharcourse.utils.APITypeClass.isGooglePlayServicesAvailable;
import static com.wdtheprovider.sharcourse.utils.APITypeClass.isHmsServiceAvailable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.MediaView;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.VideoConfiguration;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeAdConfiguration;
import com.huawei.hms.ads.nativead.NativeAdLoader;
import com.wdtheprovider.sharcourse.R;
import com.wdtheprovider.sharcourse.ads.NativeViewFactory;
import com.wdtheprovider.sharcourse.models.universityOnline;
import com.wdtheprovider.sharcourse.utils.AdsPref;
import com.wdtheprovider.sharcourse.utils.Constant;
import com.wdtheprovider.sharcourse.utils.Prefs;
import com.wdtheprovider.sharcourse.utils.SharedPref;
import com.wdtheprovider.sharcourse.utils.TemplateView;
import com.wdtheprovider.sharcourse.utils.Tools;

import java.util.ArrayList;
import java.util.Collection;

public class universityOnlineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
  
  
  // Do not copy this file and paste it as it is, you will get errors understand it and implement the codes into your adapter.

    private final ArrayList<universityOnline> list;
    private final ArrayList<universityOnline> listFull;
    private Context context;
    itemClick listener;

    private final int VIEW_ITEM = 2;
    private final int VIEW_AD = 1;
    private final int HMS_VIEW_AD = 3;
    private Activity activity;
     View nativeView;

    public universityOnlineAdapter(Activity activity, Context context, ArrayList<universityOnline> list, itemClick listener) {
        this.list = list;
        listFull = new ArrayList<>(list);
        this.activity = activity;
        this.context = context;
        Log.d("offlineL", "ListFull " + listFull.size());
        Log.d("offlineL", "List " + list.size());

        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      
      // Template row is nativead_university_row.xml
      // normal row university_row.xml

      //Here we use the function(getItemViewType) at the bottom to get the number between 1 and 2 
      // 1 is for an Ad row and 2 is for the normal row
      //if getItemViewType function returns 1  then we create this View adItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nativead_university_row, parent, false);
      //if getItemViewType function returns 1  then we create this View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.university_row, parent, false);


        switch (viewType) {
            case 1:
                Log.d("NativeAds", "add row");
                View adItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nativead_university_row, parent, false);
                return new AViewHolder(adItemView);
            case 2:
                Log.d("NativeAds", "normal row");
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.university_row, parent, false);
                return new ViewHolder(v);
            default:
                Log.d("NativeAds", "normal ro");
                View vv = LayoutInflater.from(parent.getContext()).inflate(R.layout.university_row, parent, false);
                return new ViewHolder(vv);

        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        universityOnline item = list.get(position);


       if (getItemViewType(position) == 1) {
         // aViewHolder is the Ad ViewHolder
            AViewHolder aViewHolder = (AViewHolder) holder;
            Log.d("NativeAds", "Inside VIEW_ITEM Binding");
         
         
            if (aViewHolder.admob_native_ad_container.getVisibility() == View.VISIBLE) {
                android.util.Log.d("NativeAds", "layout is visible");
            } else {
              //the add get binded here
                aViewHolder.bindNativeAd();
                android.util.Log.d("NativeAds", "layout is not visible");
            }

            int imageResourceId = context.getResources().getIdentifier(item.getUniversity_logo(), "drawable", context.getPackageName());

            aViewHolder.image.setImageResource(imageResourceId);
            aViewHolder.name.setText(item.getName());
            aViewHolder.name_abr.setText(item.getUni_abr());


        } else if (getItemViewType(position) == 2) {

            ViewHolder viewHolder = (ViewHolder) holder;
            int imageResourceId = context.getResources().getIdentifier(item.getUniversity_logo(), "drawable", context.getPackageName());

            viewHolder.image.setImageResource(imageResourceId);
            viewHolder.name.setText(item.getName());
            viewHolder.name_abr.setText(item.getUni_abr());
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

// your normal viewholder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView name_abr;
        ImageView image, go_icon;
        RelativeLayout uni_card;
        LinearLayout uni_main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.uni_name);
            name_abr = itemView.findViewById(R.id.uni_abr);
            image = itemView.findViewById(R.id.uni_logo);
            go_icon = itemView.findViewById(R.id.go_icon);
            uni_main = itemView.findViewById(R.id.uni_main);
            uni_card = itemView.findViewById(R.id.uni_card);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

  
  
// This the Ad View Holder class.
    public class AViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TemplateView admob_native_ad_container;
        public MediaView admob_media_view;
        TextView primary;
        TextView body;
        TextView secondary;
        TextView name_abr;
        TextView ad_notification_view;
        Button cta;
        TextView name;
        ImageView image, go_icon;
        RelativeLayout uni_card;
        LinearLayout uni_main;

        ScrollView scrollView;

     
      // Ad view Holder constructor
        public AViewHolder(@NonNull View itemView) {
            super(itemView);
          
          // All the below information(Views are coming from the template.
          // Linking the ids from the template row.
            admob_native_ad_container = itemView.findViewById(R.id.admob_native_ad_container);
            admob_media_view = itemView.findViewById(R.id.media_view);
            body = itemView.findViewById(R.id.body);
            primary = itemView.findViewById(R.id.primary);
            secondary = itemView.findViewById(R.id.secondary);
            name_abr = itemView.findViewById(R.id.uni_abr);
            ad_notification_view = itemView.findViewById(R.id.ad_notification_view);

            cta = itemView.findViewById(R.id.cta);
          
            name = itemView.findViewById(R.id.uni_name);
            image = itemView.findViewById(R.id.uni_logo);
            go_icon = itemView.findViewById(R.id.go_icon);
            uni_main = itemView.findViewById(R.id.uni_main);
            uni_card = itemView.findViewById(R.id.uni_card);
            scrollView = itemView.findViewById(R.id.scroll_view_ad);

        }



// Binding (Loading the ad to be shown) the Ad. 
        private void bindNativeAd() {

            final AdsPref adsPref = new AdsPref(context);
           
                AdLoader adLoader = new AdLoader.Builder(context, adsPref.getAdMobNativeId())
                        .forNativeAd(nativeAd -> {
                            admob_native_ad_container.setNativeAd(nativeAd);
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                admob_native_ad_container.setVisibility(View.VISIBLE);
                                Log.d("nativeAd", "add showing ..");
                            }
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                admob_native_ad_container.setVisibility(View.GONE);
                                Log.d("nativeAd", "error " + adError.getMessage());
                            }
                        })
                        .build();
                adLoader.loadAd(Tools.getAdRequest(activity));
             
        }

  
    }


    @Override
    public int getItemViewType(int position) { // function is used to control the number of ads to be shown on the specified index.

        if (list.get(position) != null) {
            if (position != 0) {
                for (int i = 2 i < list.size(); i += 5) {
                    if (position == i) {
                        Log.d("finalTest", "Index " + i);
                       return VIEW_AD;
                    }
                }
            }
        }
        return VIEW_ITEM;
    }

}
