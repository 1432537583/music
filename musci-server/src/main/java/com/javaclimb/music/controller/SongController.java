package com.javaclimb.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Singer;
import com.javaclimb.music.domain.Song;
import com.javaclimb.music.service.SongService;
import com.javaclimb.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 歌曲管理控制类
 */
@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    /**
     * 添加歌曲
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Object addSong(HttpServletRequest request,@RequestParam("file") MultipartFile mpFile){
        JSONObject jsonObject = new JSONObject();
        String singerId  = request.getParameter("singerId").trim();
        String name = request.getParameter("name").trim();
        String pic = "/img/songPic/hai.jpg";
        String introduction = request.getParameter("introduction").trim();
        String lyric = request.getParameter("lyric").trim();
        if(mpFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来文件名
        String fileName = System.currentTimeMillis()+mpFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件路径
        String storeSongPath = "/song/" +fileName;
        try{
            mpFile.transferTo(dest);
            Song song = new Song();
            song.setSinger_id(Integer.parseInt(singerId));
            song.setName(name);
            song.setPic(pic);
            song.setIntroduction(introduction);
            song.setLyric(lyric);
            song.setUrl(storeSongPath);
            boolean flag = songService.insert(song);
            if(flag){       //如果上传成功
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"保存成功");
                jsonObject.put("pic",storeSongPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败");
            return jsonObject;
        }catch (IOException e){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败"+e.getMessage());
        }finally {
            return jsonObject;
        }

    }

    /**
     * 修改歌曲
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSong(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String singer_id = request.getParameter("singerId").trim();
        String name = request.getParameter("name").trim();
        String lyric = request.getParameter("lyric").trim();
        String introduction = request.getParameter("introduction").trim();
        //保存到歌手的对象中
        Song song = new Song();
        song.setId(Integer.parseInt(id));
        song.setSinger_id(Integer.parseInt(singer_id));
        song.setName(name);
        song.setLyric(lyric);
        song.setIntroduction(introduction);
        boolean flag = songService.update(song);
        if(flag){       //如果修改成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }

    /**
     * 删除歌曲
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Object deleteSong(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        boolean flag = songService.delete(Integer.parseInt(id));
        if(flag){       //如果删除成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"删除成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"删除失败");
        return jsonObject;
    }

    /**
     * 根据主键查询整个对象
     */
    @RequestMapping(value = "/seleteByPrimaryKey",method = RequestMethod.POST)
    public Object seleteByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return songService.selectByPrimaryKey(Integer.parseInt(id));
    }

    /**
     * 查询所有歌曲
     */
    @RequestMapping(value = "/allSong",method = RequestMethod.GET)
    public Object allSong(HttpServletRequest request){
        return songService.allSong();
    }

    /**
     * 根据歌名模糊查询
     */
    @RequestMapping(value = "/likeSongOfName",method = RequestMethod.GET)
    public Object songOfName(HttpServletRequest request){
        String name = request.getParameter("name").trim();
        return songService.songOfName("%"+name+"%");
    }

    /**
     * 根据歌手id查询歌曲
     */
    @RequestMapping(value = "/songOfSingerId",method = RequestMethod.GET)
    public Object songOfSingerId(HttpServletRequest request){
        String singerId = request.getParameter("singerId").trim();
        return songService.songOfSingerId(Integer.parseInt(singerId));
    }
    /**
     * 根据歌曲id查询歌曲
     */
    @RequestMapping(value = "/songOfSongId",method = RequestMethod.GET)
    public Object songOfSongId(HttpServletRequest request){
        String songId = request.getParameter("id").trim();
        return songService.songOfSongId(Integer.parseInt(songId));
    }
    /**
     * 根据歌名查询歌曲
     */
    @RequestMapping(value = "/songOfSongName",method = RequestMethod.GET)
    public Object songOfSongName(HttpServletRequest request){
        String songName = request.getParameter("songName").trim();
        return songService.songOfSongName(songName);
    }
    /**
     * 更新歌手头像
     */
    @RequestMapping(value = "/updateSongPic",method = RequestMethod.POST)
    public Object updateSongPic(@RequestParam("file") MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来文件名
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"songPic";
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件路径
        String storeAvatorPath = "/img/songPic/" +fileName;
        try{
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setPic(storeAvatorPath);
            boolean flag = songService.update(song);
            if(flag){       //如果上传成功
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        }catch (IOException e){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }

}
