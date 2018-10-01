package prakash.registration;

import android.widget.Spinner;

import com.google.firebase.database.Exclude;
import com.nostra13.universalimageloader.utils.L;

/**
 * Created by praka on 01/04/2018.
 */

public class post {
    public String mImageUrl;
    public String city, state, landmark, pin, rent, description, contact, ptype;
    public String mKey;

    public post(){

    }

    public post(String Ptype, String City, String State, String Landmark, String Pin, String Rent, String Contact, String Desp, String imageurl) {
        this.ptype = Ptype;
        this.city = City;
        this.state = State;
        this.landmark = Landmark;
        this.pin = Pin;
        this.rent = Rent;
        this.contact = Contact;
        this.description = Desp;
        mImageUrl=imageurl;
    }
    public void setImageUrl(String imageurl) { mImageUrl=imageurl; }
    public String getImageUrl() {
        return mImageUrl;
    }

    public void setPtype(String Ptype){ ptype=Ptype; }
    public String getPtype(){
        return ptype ;
    }

    public void setCity(String City) {
        city=City;
    }
    public String getCity(){
        return city;
    }

    public void setState(String State) { state=State; }
    public String getState(){
        return state;
    }

    public void setLandmark(String Landmark) {
        landmark=Landmark;
    }
    public String getLandmark(){
        return landmark;
    }

    public void setPin(String Pin) {
        pin=Pin;
    }
    public String getPin(){ return pin; }

    public void setRent(String Rent) {
        rent=Rent;
    }
    public String getRent(){
        return rent;
    }

    public void setConatct(String Contact) { contact=Contact; }
    public String getContact(){
        return contact;
    }

    public void setDesp(String Desp) {
        description=Desp;
    }
    public String getDesp(){
        return description;
    }

    @Exclude
    public String getKey(){ return mKey; }
    @Exclude
    public void setKey(String key){ mKey=key; }


}
