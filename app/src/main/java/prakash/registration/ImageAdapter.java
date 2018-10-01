package prakash.registration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.EventLogTags;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by praka on 01/04/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHoler> {

    private Context mContext;
    private List<post> mPost;
    private onItemClickListener mListener;

    public  ImageAdapter(Context context, List<post> posts){
        mContext = context;
        mPost = posts;
    }

    @Override
    public ImageViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return  new ImageViewHoler(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHoler holder, int position) {
        post postCurrent = mPost.get(position);
        holder.ptype.setText(postCurrent.getPtype());
        holder.city.setText(postCurrent.getCity());
        holder.state.setText(postCurrent.getState());
        holder.locality.setText(postCurrent.getLandmark());
        holder.pin.setText(postCurrent.getPin());
        holder.rent.setText(postCurrent.getRent());
        holder.contact.setText(postCurrent.getContact());
        holder.description.setText(postCurrent.getDesp());
        Picasso.with(mContext).load(postCurrent.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public  class ImageViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener,
            MenuItem.OnMenuItemClickListener{

        public TextView state, city, locality, pin, rent, description, ptype, contact;
        public ImageView imageView;
        public  ImageViewHoler(View itemView){

            super(itemView);
            ptype = itemView.findViewById(R.id.textPtype);
            city = itemView.findViewById(R.id.textCity);
            state = itemView.findViewById(R.id.textState);
            locality = itemView.findViewById(R.id.textLocality);
            pin = itemView.findViewById(R.id.textPin);
            rent = itemView.findViewById(R.id.textRent);
            description = itemView.findViewById(R.id.textDesp);
            contact = itemView.findViewById(R.id.textContact);
            imageView = itemView.findViewById(R.id.image_view_upload);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem deleteItem = contextMenu.add(contextMenu.NONE, 1, 1, "Delete");
            deleteItem.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:
                            mListener.onDeleteClick(position);
                            return  true;
                    }
                }
            }
            return false;
        }
    }

    public interface onItemClickListener {
       void onItemClick(int position);
       void onDeleteClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

}
