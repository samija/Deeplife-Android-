package Database;

/**
 * Created by Ab on 4/8/2015.
 */
public class User {
    public int id;
    public String fname;
    public String lname;
    public String phone;
    public String email;
public String image;


    public String getImage(){
        return image;}
    public  void setImage(String image){
        this.image=image; }



    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public String getFname(){
       return fname;}
    public  void setFname(String fname){
        this.fname=fname; }

    public String getLname(){ return lname;}
    public  void setLname(String lname){this.lname=lname; }

    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }




    public String toString(){
        //NumberFormat nf = NumberFormat.getCurrencyInstance();

      return fname+" " + lname+"\n("+(phone)+")";

    }



}
