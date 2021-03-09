<template>
    
    <div class="table">
        <div>歌曲管理</div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" size="mini" placeholder="请输入歌名" class="handle-input"></el-input>
                <el-button type="primary" size="mini" @click="centerDialogVisible = true" >添加歌曲</el-button>
            </div>
        </div>
        <el-table size="mini" border style="width:100%" height="680px" :data="data">
            <el-table-column label="歌曲图片" width="110" align="center">
                <template slot-scope="scope">
                    <div class="singer-img">
                         <img :src="getUrl(scope.row.pic)" style="width:100%"/>
                    </div>
                    <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload" :on-success="handleAvatorSuccess">
                        <el-button size="mini">更新图片</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="歌名-歌手" width="120" align="center"></el-table-column>
            
            <el-table-column prop="introduction" label="专辑" width="150" align="center">
                <template slot-scope="scope">
                    <p>{{scope.row.introduction}}</p>
                </template>
            </el-table-column>
            <el-table-column prop="lyric" label="歌词" align="center">
                <template slot-scope="scope">
                    <p style="height:100px;overflow:scroll">{{scope.row.lyric}}</p>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="150" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger" @click="deleteSong(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination 
                background
                layout="total,prev,pager,next"
                :current-page="currentPage"
                :page-size="pageSize"
                :total="tableData.length"
                @current-change="handleCurrentChange"
                >
            </el-pagination>
        </div>
        <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="400px" center >
            <el-form :model="registerForm" ref="registerForm" label-width="80px" action="" id="tf">
                <div>
                    <label>歌名</label>
                    <el-input type="text" name="name"></el-input>
                </div>
                <div>
                    <label>专辑</label>
                    <el-input type="text" name="introduction"></el-input>
                </div>
                <div>
                    <label>歌词</label>
                    <el-input type="textarea" name="lyric"></el-input>
                </div>
                <div>
                    <label>歌曲上传</label>
                    <input type="file" name="file">
                </div>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisble = false">取消</el-button>
                <el-button size="mini" @click="addSong" >确定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="修改歌曲" :visible.sync="editVisible" width="400px" center>
            <el-form :model="editForm" ref="editForm" label-width="80px">
                <el-form-item prop="name" label-width="歌名" size="mini">
                    <el-input v-model="editForm.name" placeholder="歌名"></el-input>
                </el-form-item>
                <el-form-item prop="introduction" label-width="专辑" size="mini">
                    <el-input v-model="editForm.introduction" placeholder="专辑"></el-input>
                </el-form-item>
                <el-form-item prop="lyric" label-width="歌词" size="mini">
                    <el-input v-model="editForm.lyric" placeholder="歌词" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="editVisible = false">取消</el-button>
                <el-button size="mini" @click="editSong">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {updateSong,deleteSong,songOfSingerId} from '../api/index';
import {mixin} from '../mixins/index';
export default {
    mixins:[mixin],
    data(){
        return{
            singerId:'',
            singerName:'',
            centerDialogVisible:false,
            editVisible:false,
            registerForm:{      //添加框
                name:'',
                singerName:'',
                lyric:'',
                introduction:''
            },
            editForm:{      //添加框
                name:'',
                id:'',
                lyric:'',
                introduction:''
            },
            tableData:[],
            tempData:[],
            select_word:'',
            currentPage:1,
            pageSize:5
        }
    },
    computed:{
        data(){
            return this.tableData.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
        }
    },
    watch:{
        //搜索框里面的内容发生变化时，搜索结果table列表的内容也跟着改变
        select_word:function(){
            if(this.select_word == ''){
                this.tableData = this.tempData;
            }else{
                this.tableData = [];
                for(let item of this.tempData){
                    if(item.name.includes(this.select_word)){
                        this.tableData.push(item);
                    }
                }
            }
        }
    },
    created(){
        this.singerId=this.$route.query.id;
        this.singerName=this.$route.query.name;
        this.getData()
    },
    methods:{
        //跳转歌曲管理页面
        songManage(id,name){
            this.$router.push({path:'SongPage',query:{id,name}});
        },
        //删除歌曲
        deleteSong(id){
            let params = new URLSearchParams();
            params.append("id",id);
            deleteSong(params)
            .then(res =>{
                if(res.code == 1){
                    this.getData()
                    this.notify("删除成功","success");
                }else{
                    this.notify("删除失败","error");
                }
            })
            .catch(err => {
                console.log(err);
            });
        },
        //给editFrom赋初始值
        handleEdit(row){
            this.editVisible=true;
            this.editForm={
                singerId:row.singer_id,
                id:row.id,
                name:row.name,
                introduction:row.introduction,
                lyric:row.lyric
            }
        },
        //编辑歌手
        editSong(){
            let params = new URLSearchParams();
            params.append('id',this.editForm.id);
            params.append('singerId',this.editForm.singerId);
            params.append('name',this.editForm.name);
            params.append('introduction',this.editForm.introduction);
            params.append('lyric',this.editForm.lyric);
            updateSong(params)
            .then(res =>{
                if(res.code == 1){
                    this.getData()
                    this.notify("修改成功","success");
                }else{
                    this.notify("修改失败","error");
                }
            })
            .catch(err => {
                console.log(err);
            });
            this.editVisible = false;
        },
        //获取当前页面
        handleCurrentChange(val){
            this.currentPage = val;
        },
        //查询歌曲
        getData(){
            this.tempData=[];
            this.tableData = [];
            songOfSingerId(this.singerId).then(res=>{
                this.tempData=res;
                this.tableData=res;
                this.currentPage=1;
            })
        },
        //添加歌曲
        addSong(){
            let _this=this;
            var form = new FormData(document.getElementById('tf'));
            form.append('singerId',this.singerId);
            if(!form.get('lyric')){
                form.set('lyric','[00:00:00]暂无歌词');
            }
            var req = new XMLHttpRequest();
            req.onreadystatechange = function(){
                if(req.readyState == 4&&req.status==200){
                    let res = JSON.parse(req.response);
                    if(res.code){
                        _this.getData();
                        _this.registerForm={};
                        _this.notify(res.msg,'success');
                    }else{
                        _this.notify('保存失败','error');
                    }
                }
            }
            req.open('post',`${_this.$store.state.HOST}/song/insert`,false);
            req.send(form);
            
            this.centerDialogVisible = false;
        },
        //更新歌曲图片
        uploadUrl(id){
            return `${this.$store.state.HOST}/song/updateSongPic?id=${id}`
        }
    }
}
</script>

<style scoped>
    .handle-box{
        margin-bottom: 20px;
    }
    .singer-img{
        width:100%;
        height: 80px;
        border-radius: 5px;
        margin-bottom: 5px;
        overflow: hidden;
    }
    .handle-input{
        width:300px;
        display: inline-block;
    }
    .pagination{
        display: flex;
        justify-content: center;
    }
</style>