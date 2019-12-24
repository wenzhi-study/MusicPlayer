package com.example.musicplayer.bean;

/**
 * @ProjectName: MusicPlayer
 * @Package: com.example.musicplayer.bean
 * @ClassName: Song
 * @Description: 歌曲信息的bean方法
 * @Author: 作者名：王文治
 * @CreateDate: 2019/12/16 9:32
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/16 9:32
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class Song {
    private  String songNameStr;
    private String singerNameStr;
    private String songTimeStr;
    private String filePathStr;
    private String  fileSizeStr;

    public Song(String songNameStr, String singerNameStr, String songTimeStr, String filePathStr, String fileSizeStr) {
        this.songNameStr = songNameStr;
        this.singerNameStr = singerNameStr;
        this.songTimeStr = songTimeStr;
        this.filePathStr = filePathStr;
        this.fileSizeStr = fileSizeStr;
    }

    public String getSongNameStr() {
        return songNameStr;
    }

    public void setSongNameStr(String songNameStr) {
        this.songNameStr = songNameStr;
    }

    public String getSingerNameStr() {
        return singerNameStr;
    }

    public void setSingerNameStr(String singerNameStr) {
        this.singerNameStr = singerNameStr;
    }

    public String getSongTimeStr() {
        return songTimeStr;
    }

    public void setSongTimeStr(String songTimeStr) {
        this.songTimeStr = songTimeStr;
    }

    public String getFilePathStr() {
        return filePathStr;
    }

    public void setFilePathStr(String filePathStr) {
        this.filePathStr = filePathStr;
    }

    public String getFileSizeStr() {
        return fileSizeStr;
    }

    public void setFileSizeStr(String fileSizeStr) {
        this.fileSizeStr = fileSizeStr;
    }
}
