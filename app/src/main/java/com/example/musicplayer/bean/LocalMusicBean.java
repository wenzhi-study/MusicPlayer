package com.example.musicplayer.bean;

/**
 * @ProjectName: MusicPlayer
 * @Package: com.example.musicplayer.bean
 * @ClassName: LocalMusicBean
 * @Description:
 * @Author: 作者名：小米PRO
 * @CreateDate: 2019/12/21 16:48
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/21 16:48
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class LocalMusicBean {

    private String strId;          //歌曲id
    private String strMusicName;   //歌名
    private String strSingerName;  //歌手名
    private String strSpecialName; //专辑名
    private String strMusicTime;   //歌曲时长
    private String strMusicPath;   //歌曲路径
    private int    intMusicImg;    //专辑照片

    public LocalMusicBean(String strId, String strMusicName, String strSingerName, String strSpecialName, String strMusicTime, String strMusicPath) {
        this.strId = strId;
        this.strMusicName = strMusicName;
        this.strSingerName = strSingerName;
        this.strSpecialName = strSpecialName;
        this.strMusicTime = strMusicTime;
        this.strMusicPath = strMusicPath;
        this.intMusicImg = intMusicImg;
    }

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getStrMusicName() {
        return strMusicName;
    }

    public void setStrMusicName(String strMusicName) {
        this.strMusicName = strMusicName;
    }

    public String getStrSingerName() {
        return strSingerName;
    }

    public void setStrSingerName(String strSingerName) {
        this.strSingerName = strSingerName;
    }

    public String getStrSpecialName() {
        return strSpecialName;
    }

    public void setStrSpecialName(String strSpecialName) {
        this.strSpecialName = strSpecialName;
    }

    public String getStrMusicTime() {
        return strMusicTime;
    }

    public void setStrMusicTime(String strMusicTime) {
        this.strMusicTime = strMusicTime;
    }

    public String getStrMusicPath() {
        return strMusicPath;
    }

    public void setStrMusicPath(String strMusicPath) {
        this.strMusicPath = strMusicPath;
    }

    public int getIntMusicImg() {
        return intMusicImg;
    }

    public void setIntMusicImg(int intMusicImg) {
        this.intMusicImg = intMusicImg;
    }
}
