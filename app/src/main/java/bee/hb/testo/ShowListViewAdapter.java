package bee.hb.testo;

/**
 * Created by biniam on 10/2/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ShowListViewAdapter extends BaseAdapter {

    //variables
    Context mContext;
    LayoutInflater inflater;
    List<ShowModel> modellist;
    ArrayList<ShowModel> arrayList;
    static String classname;


    int language;
    public String addtext, nfatext;

    public void setUpLanguage() {
        language = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(mContext).getString("display_language", "1"));
        switch (language) {
            case 0:
                addtext = mContext.getResources().getString(R.string.adde);
                nfatext = mContext.getResources().getString(R.string.nfae);
                break;
            case 1:
                addtext = mContext.getResources().getString(R.string.addt);
                nfatext = mContext.getResources().getString(R.string.nfat);
                break;

        }
    }

    //constructor
    public ShowListViewAdapter(Context context, List<ShowModel> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<ShowModel>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder {
        TextView mTitleTv, mDescTv, mCostTv;
        ImageView mIconIv;
        Button mAddBt;
    }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent) {
        final ViewHolder holder;
        setUpLanguage();
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.show_row, null);

            //locate the views in row.xml
            holder.mTitleTv = (TextView) view.findViewById(R.id.showitemname);
            holder.mDescTv = (TextView) view.findViewById(R.id.showitemdescription);
            holder.mCostTv = (TextView) view.findViewById(R.id.showitemprice);
            holder.mIconIv = (ImageView) view.findViewById(R.id.showitemimage);
            holder.mAddBt = (Button) view.findViewById(R.id.showadditemtocart);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        //set the results into textviews
        holder.mTitleTv.setText(modellist.get(postition).getTitle());
        holder.mDescTv.setText(modellist.get(postition).getDescription());
        holder.mCostTv.setText("+" + modellist.get(postition).getCost() + " " + nfatext);
        holder.mAddBt.setText(addtext);
        int theme = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(mContext).getString("app_theme", "1"));
        if(theme == 1){
            holder.mAddBt.setBackgroundResource(R.drawable.button_rounded_background_orange);
        }else{
            holder.mAddBt.setBackgroundResource(R.drawable.button_rounded_background);
        }
        //set the result in imageview
        Glide.with(mContext).load(modellist.get(postition).getIcon()).into(holder.mIconIv);
        holder.mAddBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, AddDialog.class);

                i.putExtra("name", modellist.get(postition).getTitle());
                i.putExtra("picture", modellist.get(postition).getType() + postition);
                i.putExtra("cost", modellist.get(postition).getCost());
                i.putExtra("id", modellist.get(postition).getId());
                i.putExtra("description", modellist.get(postition).getDescription());

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(i);

            }
        });

//        //listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //code later
//                //start NewActivity with title for actionbar and text for textview
                Intent intent = new Intent(mContext, ItemDetailsActivity.class);

                intent.putExtra("name", modellist.get(postition).getTitle());
                intent.putExtra("picture", modellist.get(postition).getType() + postition);
                intent.putExtra("cost", modellist.get(postition).getCost());
                intent.putExtra("id", modellist.get(postition).getId());
                intent.putExtra("description", modellist.get(postition).getDescription());
                intent.putExtra("extra", modellist.get(postition).getExtra());

                mContext.startActivity(intent);
//                Toast.makeText(mContext, "Make the user see the details of the item", Toast.LENGTH_SHORT).show();
            }
        });
//        view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                //if(classname.equals("main")) {
//                if (classname.equals("main")) {
//
//                    FavSql favSql = new FavSql(mContext);
//                    favSql.openDataBase();
//                    boolean faved = favSql.isFaved(modellist.get(postition).getTitle(), modellist.get(postition).getDate());
//                    favSql.close();
//                    Intent delintent = new Intent(mContext, NewsPaperOptionsChooser.class);
//                    delintent.putExtra("papername", modellist.get(postition).getTitle());
//                    delintent.putExtra("paperdate", modellist.get(postition).getDate());
//                    delintent.putExtra("like",faved);
//                    mContext.startActivity(delintent);
////                    Toast.makeText(mContext, "from main", Toast.LENGTH_LONG).show();
//                } else {
//                    Intent delintent = new Intent(mContext, RemoveFromFavs.class);
//                    delintent.putExtra("papername", modellist.get(postition).getTitle());
//                    delintent.putExtra("paperdate", modellist.get(postition).getDate());
//                    mContext.startActivity(delintent);
////                    Toast.makeText(mContext, "from favs", Toast.LENGTH_LONG).show();
//                }
//                // }
//                return true;
//            }
//        });

        return view;
    }

    //filter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        modellist.clear();
        if (charText.length() == 0) {
            modellist.addAll(arrayList);
        } else {
            for (ShowModel model : arrayList) {
                if (model.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    modellist.add(model);
                    continue;
                }
                if (model.getDescription().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }

}