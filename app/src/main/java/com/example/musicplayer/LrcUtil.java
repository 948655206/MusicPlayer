package com.example.musicplayer;

import android.text.TextUtils;
import android.util.Log;

import com.example.musicplayer.Bean.LrcBean;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 12453
 * @since : 2021/6/25
 * 作用: 将歌词文件或者json数据解析成List<LrcBean>
 */
public class LrcUtil {

    private static final String TAG = "LrcUtil";
    /**
     * 传入的参数为标准歌词字符串
     * @param lrcStr 歌词字符串
     * @return List<LrcBean>
     */
    public static List<LrcBean> parseStr2List(String lrcStr) {
        if (TextUtils.isEmpty(lrcStr)) return null;
        List<LrcBean> list = new ArrayList<>();
        //1.更替转义字符
        String lrcText = lrcStr.replaceAll("&#58;", ":")
                .replaceAll("&#10;", "\n")
                .replaceAll("&#46;", ".")
                .replaceAll("&#32;", " ")
                .replaceAll("&#45;", "-")
                .replaceAll("&#13;", "\r")
                .replaceAll("&#39;", "'")
                .replaceAll("&nbsp;", " ")//空格替换
                .replaceAll("&apos;", "'")//分号替换
                .replaceAll("&&", "/")//空格替换
                .replaceAll("\\|", "/");

        //logJsonUtil.e("更替转义字符后",lrcStr);
        //2.更替转义字符后，将此字符串转换成字符数组，区分每个字符的边界是"\n"换行符
        String[] split = lrcText.split("\n");
        boolean isWithTranslation = false;
        //3.根据定界正则表达式【换行符】为条件，转换成字符数组后，对每行字符串进行信息提炼（开始时间，每行歌词）
        for (int i = 0; i < split.length; i++) {
            String lrcInfo = split[i];
            //Log.d("未处理: 第"+i+"行", lrcInfo);
            if (" ".equals(lrcInfo) || TextUtils.isEmpty(lrcInfo)) continue;
            if (lrcInfo.contains("[ti:") || lrcInfo.contains("[ar:") || lrcInfo.contains("[offset:") ||
                    lrcInfo.contains("[al:") || lrcInfo.contains("[by:")) {
                continue;
            }
            String lrc = lrcInfo.substring(lrcInfo.indexOf("]") + 1);
            //如果该行文字为空或者1个空格符
            if (TextUtils.isEmpty(lrc) || " ".equals(lrc) || "//".equals(lrc)) continue;

            Log.d(i+"行", lrc);

            String min = lrcInfo.substring(lrcInfo.indexOf("[")+ 1, lrcInfo.indexOf("[")+ 3);
            String seconds = lrcInfo.substring(lrcInfo.indexOf(":")+ 1, lrcInfo.indexOf(":") + 3);
            String mills = lrcInfo.substring(lrcInfo.indexOf(".") + 1, lrcInfo.indexOf(".") + 3);
//            Log.e(TAG, "parseStr2List:"+min+seconds+mills );
            long startTime = Long.parseLong(min) * 60 * 1000 +   //解析分钟
                    Long.parseLong(seconds) * 1000 + //解析秒钟
                    Long.parseLong(mills)*10;  //解析毫秒
            if (list.size() > 1 && startTime < list.get(list.size() - 2).getStart())
                startTime = list.get(list.size() - 2).getStart() + 600;

            LrcBean lrcBean = new LrcBean(lrc,startTime);
            list.add(lrcBean);
            Log.e(TAG, "parseStr2List: "+list );

            if (list.size() > 1) list.get(list.size() - 2).setEnd(startTime);
            if (i == split.length - 1) list.get(list.size() - 1).setEnd(startTime + 100000);
        }
        return list;
    }

    /**
     * 读取本地文件中的歌词
     * @param MusicPath 本地歌词文件的绝对路径
     * */
    public static String parseLrcFile(String MusicPath) {
        Log.d(TAG, "parseLrcFile: "+MusicPath);
        if (TextUtils.isEmpty(MusicPath) || !MusicPath.contains(".lrc")) return null;
        System.out.println("你的歌词存储路径--------->"+MusicPath);

        File file = new File(MusicPath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        BufferedReader reader;
        StringBuilder LrcStr = new StringBuilder();
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            //首先确定文件的文本编码类型，"utf-8"或者"gbk"不然乱码
            reader = new BufferedReader(new InputStreamReader(bis,"gb2312"));
            String str = reader.readLine();
            while (str != null) {
                LrcStr.append(str).append("\n");
                str = reader.readLine();
            }//logJsonUtil.e("LrcUtil",LrcStr);
        } catch (IOException e) {
            return null;
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                    if(bis != null) bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return LrcStr.toString();
    }

    /**
     * @param musicFileName "歌名 - 歌手"
     * */
    public static String getLrcFilePath(String musicFileName){

        if (musicFileName == null || TextUtils.isEmpty(musicFileName)) {
            return null;
        }

        String absPath = getLocalPathPictures(musicFileName);

        Log.d(TAG, "getLrcFilePath: "+absPath+", 是否存在 "+FileExists(absPath));

        return FileExists(absPath) ? absPath : null;
    }

    /** 获取Picture文件夹的绝对路径*/
    public static String getLocalPathPictures(String fileName){
//        fileName = fileName.replaceAll("/","&");
//        String absPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/";
//        return absPath+fileName;
        Log.e(TAG, "getLocalPathPictures: " );
        return fileName;
    }

    /** 判断该路径的文件是否存在*/
    public static boolean FileExists(String targetFileAbsPath){
        try {
            File f = new File(targetFileAbsPath);
            if(!f.exists()) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
