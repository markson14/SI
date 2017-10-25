package comp5216.sydney.edu.au.SI.Tag;

/**
 * Created by 老虎 on 2017/10/21.
 */

public class Translation {
    private static int savedId=1;
    private String beforeTranslate;
    private String afterTranslate;
    private int id;

    public Translation(String bT, String aT){
        beforeTranslate = bT;
        afterTranslate = aT;
        id = savedId;
        savedId++;
    }

    public String getBeforeTranslate(){
        return beforeTranslate;
    }

    public String getAfterTranslate(){
        return afterTranslate;
    }

}
