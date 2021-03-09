export const mixin = {
    methods:{
        //提示信息
        notify(title,type){
            this.$notify({
                title: title,
                type: type
            })
        },
        //根据相对地址获取绝对地址
        getUrl(url){
            return `${this.$store.state.HOST}/${url}`
        },
        //获取性别
        changeSex(value){
            if(value==0){
                return '女';
            }else if(value==1){
                return '男';
            }else if(value==2){
                return '组合';
            }else if(value==3){
                return '不明';
            }else{
                return value;
            }
        },
        //获取生日
        changeBirth(value){
            return String(value).substr(0,10);
        },
        //上传歌手头像之前校验
        beforeAvatorUpload(file){
            const isJPG = (file.type === 'image/jpeg')||(file.type === 'image/png');
            if(!isJPG){
                this.$message.error("上传头像图片只能是jpg或png格式的");
                return false;
            }
            const isLt2M = (file.size/1024/1024)<2;
            if(!isLt2M){
                this.$message.error("上传头像图片大小不能大于2MB");
                return false;
            }
            return true;
        },
        //上传图片成功之后要做的工作
        handleAvatorSuccess(res){
            let _this = this;
            if(res.code==1){
                _this.getData();
                _this.$notify({
                    title:'上传成功',
                    type:'success'
                });
            }else{
                _this.$notify({
                    title:'上传失败',
                    type:'error'
                });
            }
        },
        //上传歌手头像之前校验
        beforeSongUpload(file){
            const isMP3 = (file.type === 'mp3');
            if(!isMP3){
                this.$message.error("上传歌曲只能是mp3格式的");
                return false;
            }
            const isLt10M = (file.size/1024/1024)<10;
            if(!isLt10M){
                this.$message.error("上传歌曲大小不能大于10MB");
                return false;
            }
            return true;
        },
        //上传图片成功之后要做的工作
        handleSongSuccess(res){
            let _this = this;
            if(res.code==1){
                _this.getData();
                _this.$notify({
                    title:'上传成功',
                    type:'success'
                });
            }else{
                _this.$notify({
                    title:'上传失败',
                    type:'error'
                });
            }
        },
        //弹出删除窗口
        handleDelete(id){
            this.idx = id;
            this.delVisible = true;
        },
        //把已经选择的项赋值给multipleSelection
        handleSelectionChange(val){
            this.multipleSelection = val;
        },
        //批量删除已经选择的项
        delAll(){
            for(let item of this.multipleSelection){
                this.handleDelete(item.id);
                this.deleteRow();
            }
            this.multipleSelection = [];
        }
    }
}