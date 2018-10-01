package prakash.registration;

/**
 * Created by praka on 30/03/2018.
 */

public class upload {
    private String mImageUrl;
    public upload(){

    }
    public upload(String imageurl){

        mImageUrl=imageurl;
    }
    public void setImageUrl(String imageurl) {
        mImageUrl=imageurl;
    }
    public String getImageUrl() {
        return mImageUrl;
    }
}
