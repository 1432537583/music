import {get,post} from "./http";

//判断管理员是否登陆成功
export const getLoginStatus = (params)=> post('admin/login/status',params);

//查询歌手
export const getAllSinger = ()=> get('singer/allSinger');
//添加歌手
export const setSinger = (params)=> post('singer/insert',params);
//修改歌手
export const updateSinger = (params)=>post('singer/update',params);
//删除歌手
export const deleteSinger = (params)=>post('singer/delete',params);

//查询歌曲
export const getAllSong = ()=> get(`song/allSong`);
//根据歌手id查询歌曲
export const songOfSingerId = (id)=>get(`song/songOfSingerId?singerId=${id}`);
//添加歌曲
export const setSong = (params)=> post('song/insert',params);
//修改歌曲
export const updateSong = (params)=>post('song/update',params);
//删除歌曲
export const deleteSong = (params)=>post('song/delete',params);
//根据歌曲id查询歌曲
export const songOfSongId = (id)=>get(`song/songOfSongId?id=${id}`);
//根据歌曲名称查询歌曲
export const songOfSongName = (songName)=>get(`song/songOfSongName?songName=${songName}`);


//============歌单相关================
//查询歌单
export const getAllSongList =() => get(`songList/allSongList`);
//添加歌单
export const setSongList = (params) => post(`songList/insert`,params);
//编辑歌单
export const updateSongList = (params) => post(`songList/update`,params);
//删除歌单
export const delSongList = (id) => get(`songList/delete?id=${id}`);

//============歌单的歌曲相关============
//根据歌单id查询歌曲列表
export const listSongDetail = (songListId) => get(`listSong/detail?songListId=${songListId}`);
//给歌单增加歌曲
export const listSongAdd = (params) => post(`listSong/insert`,params);
//删除歌单的歌曲
export const delListSong = (songId,songListId) => get(`listSong/delete?songId=${songId}&songListId=${songListId}`);

//============用户相关================
//查询用户
export const getAllConsumer =() => get(`consumer/allConsumer`);
//添加用户
export const setConsumer = (params) => post(`consumer/insert`,params);
//编辑用户
export const updateConsumer = (params) => post(`consumer/update`,params);
//删除用户
export const delConsumer = (id) => get(`consumer/delete?id=${id}`);
//根据用户id查询该用户的详细信息
export const getUserOfId =(id) => get(`/consumer/selectByPrimaryKey?id=${id}`);

//===============收藏===================
//指定用户的收藏列表
export const getCollectOfUserId = (userId) => get(`/collect/collectOfUserId?userId=${userId}`);
//删除用户收藏的歌曲
export const deleteCollection = (userId,songId) => get(`collect/delete?userId=${userId}&songId=${songId}`);

//===============评论===================
//指定歌单的评论列表
export const getCommentOfSongListId = (songListId) => get(`/comment/commentOfSongListId?songListId=${songListId}`);
//删除评论
export const deleteComment = (id) => get(`comment/delete?id=${id}`);