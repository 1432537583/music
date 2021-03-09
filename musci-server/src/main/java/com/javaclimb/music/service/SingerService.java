package com.javaclimb.music.service;

import com.javaclimb.music.domain.Singer;

import java.util.List;

public interface SingerService {
    /**
     * 增加是否成功
     */
    public boolean insert(Singer singer);

    /**
     * 修改是否成功
     */
    public boolean update(Singer singer);

    /**
     * 删除是否成功
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public Singer selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌手
     */
    public List<Singer> allSinger();

    /**
     * 根据歌手名模糊查询列表
     */
    public List<Singer> singerOfName(String name);

    /**
     * 根据歌手性别模糊查询列表
     */
    public List<Singer> singerOfSex(Integer sex);
}
