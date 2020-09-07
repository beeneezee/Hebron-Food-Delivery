package bee.hb.testo;

/**
 * Created by biniam on 10/2/2018.
 */

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CartListViewAdapter extends BaseAdapter {

    //variables
    Context mContext;
    LayoutInflater inflater;
    List<CartModel> modellist;
    ArrayList<CartModel> arrayList;

    String[] quantities = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    HandleDatabase handleDatabase;


    int language;
    public String nfatext, areyousureordertext, removelltext, oktext, canceltext, removedfromcarttext, qtytext;

    public void setUpLanguage() {
        language = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(mContext).getString("display_language", "1"));
        switch (language) {
            case 0:
                nfatext = mContext.getResources().getString(R.string.nfae);
                qtytext = mContext.getResources().getString(R.string.qtye);
                areyousureordertext = mContext.getResources().getString(R.string.areyousureordere);
                removelltext = mContext.getResources().getString(R.string.removelle);
                oktext = mContext.getResources().getString(R.string.oke);
                canceltext = mContext.getResources().getString(R.string.cancele);
                removedfromcarttext = mContext.getResources().getString(R.string.removedfromcarte);
                break;
            case 1:
                nfatext = mContext.getResources().getString(R.string.nfat);
                qtytext = mContext.getResources().getString(R.string.qtyt);
                areyousureordertext = mContext.getResources().getString(R.string.areyousureordert);
                removelltext = mContext.getResources().getString(R.string.removellt);
                oktext = mContext.getResources().getString(R.string.okt);
                canceltext = mContext.getResources().getString(R.string.cancelt);
                removedfromcarttext = mContext.getResources().getString(R.string.removedfromcartt);
                break;

        }
    }

    //constructor
    public CartListViewAdapter(Context context, List<CartModel> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<CartModel>();
        this.arrayList.addAll(modellist);
    }


    public class ViewHolder {
        TextView mTitleTv, mDescTv, mCostTv, mCountSp;
        ImageView mIconIv, mRemoveIv;
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

        // TODO: 05/02/2019  yo better do it fast @BeeNee!!
        final FragmentManager fm = ((Activity) mContext).getFragmentManager();
//        final FourthFragment fourthFragment = fm.findFragmentByTag()
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.cart_row, null);

            handleDatabase = new HandleDatabase(mContext);
            handleDatabase.openDataBase();

            //locate the views in row.xml
            holder.mTitleTv = (TextView) view.findViewById(R.id.mycartitemname);
            holder.mDescTv = (TextView) view.findViewById(R.id.mycartitemdescription);
            holder.mCostTv = (TextView) view.findViewById(R.id.mycartitemtypeprice);
            holder.mIconIv = (ImageView) view.findViewById(R.id.mycartitemimage);
            holder.mRemoveIv = (ImageView) view.findViewById(R.id.mycartremoveitem);
            holder.mCountSp = (TextView) view.findViewById(R.id.mycartitemcounter);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        //set the results into textviews
        holder.mTitleTv.setText(modellist.get(postition).getTitle());
        holder.mDescTv.setText(modellist.get(postition).getDescription());
        // holder.mCostTv.setText( "+" +   + " Nfa");
        //set the result in imageview
        Glide.with(mContext).load(modellist.get(postition).getIcon()).into(holder.mIconIv);
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

//        Toast.makeText(mContext, modellist.get(postition).getCost(), Toast.LENGTH_LONG).show();

        holder.mRemoveIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder builder = new Builder(mContext);
                builder.setIcon(android.R.drawable.ic_delete);
                if (language == 0)
                    builder.setTitle("Remove ALL " + modellist.get(postition).getTitle() + "\'s?");
                else
                    builder.setTitle(mContext.getResources().getString(R.string.removellt) + " " + modellist.get(postition).getTitle() + " " + mContext.getResources().getString(R.string.removell2t));
                builder.setPositiveButton(oktext,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                HandleDatabase handleDatabase = new HandleDatabase(mContext);
                                handleDatabase.openDataBase();
                                handleDatabase.deleteFromCart(modellist.get(postition).getId());
                                modellist.remove(postition);
                                CartListViewAdapter.this.notifyDataSetChanged();
                                handleDatabase.close();
                                Toasty.error(mContext, removedfromcarttext, Toasty.LENGTH_SHORT, true).show();

                            }
                        }
                );
                builder.setNegativeButton(canceltext,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }
                );

                builder.show();
            }
        });


        holder.mCountSp.setText(qtytext  + " " + (Integer.parseInt(modellist.get(postition).getCount())) + "");
        int dispaly = Integer.parseInt(quantities[Integer.parseInt(modellist.get(postition).getCount()) - 1]) * Integer.parseInt(modellist.get(postition).getCost());
        holder.mCostTv.setText("+" + dispaly + " " + nfatext);

//        //listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //code later
//                //start NewActivity with title for actionbar and text for textview
//                Intent intent = new Intent(mContext, NewsPaperReader.class);
//                intent.putExtra("newspaper", modellist.get(postition).getTitle());
//                intent.putExtra("date", modellist.get(postition).getDate());
//                mContext.startActivity(intent);
//                Toast.makeText(mContext, "Make the user see the details of the item", Toast.LENGTH_LONG).show();
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
            for (CartModel model : arrayList) {
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