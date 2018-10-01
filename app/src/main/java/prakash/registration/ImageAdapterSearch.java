package prakash.registration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by praka on 03/04/2018.
 */

public class ImageAdapterSearch extends RecyclerView.Adapter<ImageAdapterSearch.ImageViewHolder1> {
   private Context mContext;
   private List<post> mPosts;

    public  ImageAdapterSearch(Context context, List<post> posts){
        mContext = context;
        mPosts = posts;
    }

    @Override
    public ImageViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return  new ImageViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder1 holder, int position) {
        post postCurrent = mPosts.get(position);
        holder.ptype.setText(postCurrent.getPtype());
        holder.city.setText(postCurrent.getCity());
        holder.state.setText(postCurrent.getState());
        holder.locality.setText(postCurrent.getLandmark());
        holder.pin2.setText(postCurrent.getPin());
        holder.rent.setText(postCurrent.getRent());
        holder.description.setText(postCurrent.getDesp());
        Picasso.with(mContext).load(postCurrent.getImageUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    public class ImageViewHolder1 extends RecyclerView.ViewHolder{
        public TextView state, city, locality, pin2, rent, description, ptype, contact;
        public ImageView imageView;

        public ImageViewHolder1(View itemView){
            super(itemView);
            ptype = itemView.findViewById(R.id.textPtype);
            city = itemView.findViewById(R.id.textCity);
            state = itemView.findViewById(R.id.textState);
            locality = itemView.findViewById(R.id.textLocality);
            pin2 = itemView.findViewById(R.id.textPin);
            rent = itemView.findViewById(R.id.textRent);
            contact = itemView.findViewById(R.id.textContact);
            description = itemView.findViewById(R.id.textDesp);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }
}
