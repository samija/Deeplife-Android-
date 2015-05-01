package Database;

/**
 * Created by Ab on 4/8/2015.
 */
public class UserLogin{
    public int idl;
    public String fnamel;
    public String lnamel;
    public String phonel;
    public String emaill;

    public String imagel;




    public String getImagel(){
        return imagel;}
    public  void setImagel(String imagel){
        this.imagel=imagel; }



    public int getIdl(){
        return idl;
    }
    public void setIdl(int idl){
        this.idl=idl;
    }

    public String getFnamel(){
        return fnamel;}
    public  void setFnamel(String fnamel){
        this.fnamel=fnamel; }

    public String getLnamel(){ return lnamel;}
    public  void setLnamel(String lnamel){this.lnamel=lnamel; }






    public String toString(){
        //NumberFormat nf = NumberFormat.getCurrencyInstance();


        return fnamel+" " + lnamel;

    }



}
