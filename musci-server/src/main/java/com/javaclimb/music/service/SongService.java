package com.javaclimb.music.service;

import com.javaclimb.music.domain.Song;

import java.util.List;

public interface SongService {
    /**
     * 增加是否成功
     */
    public boolean insert(Song song);

    /**
     * 修改是否成功
     */
    public boolean update(Song song);

    /**
     * 删除是否成功
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public Song selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌曲
     */
    public List<Song> allSong();

    /**
     * 根据歌名模糊查询列表
     */
    public List<Song> songOfName(String name);

    /**
     * 根据歌手id查询
     */
    public List<Song> songOfSingerId(Integer singerId);

    /**
     * 根据歌曲id查询
     */
    public List<Song> songOfSongId(Integer id);

    /**
     * 根据歌名查询
     */
    public List<Song> songOfSongName(String name);
}
