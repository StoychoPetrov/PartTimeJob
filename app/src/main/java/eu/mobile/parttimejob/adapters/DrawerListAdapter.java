package eu.mobile.parttimejob.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import eu.mobile.parttimejob.R;

/**
 * Created by stoycho.petrov on 02/06/2017.
 */

public class DrawerListAdapter extends RecyclerView.Adapter<DrawerListAdapter.ViewHolder> {

    private static final int TYPE_HEADER    = 0;
    private static final int TYPE_ITEM      = 1;

    private Context             mContext;
    private OnClickViewListener mListener;

    public DrawerListAdapter(Context context,OnClickViewListener listener){
        mContext    = context;
        mListener   = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private int                     mTypeId;
        private ImageView               mProfileImageView;
        private TextView                mNameTxt;
        private OnClickViewListener     mOnClickViewListener;


        public ViewHolder(View itemView, int viewType, OnClickViewListener listener) {
            super(itemView);
            mOnClickViewListener    = listener;
            if(viewType == TYPE_HEADER){
                mProfileImageView   = (ImageView)   itemView.findViewById(R.id.profile_image);
                mNameTxt            = (TextView)    itemView.findViewById(R.id.name);
                mTypeId             = 0;

                mProfileImageView.setOnClickListener(this);
            }
            else if(viewType == TYPE_ITEM){
                mTypeId = 1;
            }
        }

        @Override
        public void onClick(View view) {
            if(mOnClickViewListener != null)
                mOnClickViewListener.onClickViewInDrawer(view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_menu_heather, parent, false);
            return new ViewHolder(v, viewType,mListener);
        }

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer, parent, false);
            return new ViewHolder(v, viewType,mListener);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == TYPE_HEADER)
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

   public interface OnClickViewListener {
       void onClickViewInDrawer(View view);
   }
}
