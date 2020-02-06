<template>
<div>
  <div class="crumb">
  <el-breadcrumb separator-class="el-icon-arrow-right">
  <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
  <el-breadcrumb-item>视频列表</el-breadcrumb-item>
  </el-breadcrumb>
    <el-button v-on:click="toAddpage" class="add"  type="primary" icon="el-icon-circle-plus">添加视频</el-button>
  </div>
  
 <el-table
    :data="tableData"
    style="width: 60%"
    class="tab">
    <el-table-column
      label="id"
      width="180">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.id }}</span>
      </template>
    </el-table-column>
    <el-table-column
      label="标题"
      width="180">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.title }}</span>
      </template>
    </el-table-column>
    <el-table-column
      label="分类"
      width="180">
      <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.category }}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, tableData)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        tableData:[]
        
      }
    },
    mounted() {
        var g_articleList =[]
            this.$axios({
                method:'get',
                url:"http://localhost:8887/vedio/getlist",
                xhrFields:{withCredentials:true},
            }).then((res)=>{
                  g_articleList = res.data;              
                  console.log(res.data.data)                
                  this.tableData=res.data.data   
            })
    },
    
    methods:{
      handleEdit(index, row) {
        var item = row.title
         this.$router.push({
          name: 'editarticle',
          params:{
            title:item
          }
        })
      },
      handleDelete(index, row) {
        console.log(index, row);
        this.$axios({
                method:'get',
                url:"http://localhost:8887/vedio/del",
                params:{
                  id:row[index].id
                }
            }).then((res)=>{               
                   var resData=res.data;
                  if(resData.status==1){
                        this.$message.success(resData.msg);
                  }
                  else
                      this.$message.error(resData.msg);
            })
            row.splice(index,1)
      },
      toAddpage:function(){
          this.$router.push({
          path: '/addvedio',
        })
      },
      test:function(){
           
            this.$axios({
                method:'get',
                url:"http://localhost:8887/article/edit",
                xhrFields:{withCredentials:true},
            }).then((res)=>{
                g_articleList = res.data;   
                  console.log(res.data)
                  this.tableData=res.data
                  
            })
        }
    } 
  }
</script>

<style scoped>
.crumb{
  margin-top: 50px;
  margin-left: 80px
}
.tab{
    position: absolute;
    top:150px;
    left: 300px;
    line-height: 12px
}

.el-table th{
        background:black !important;
        font-size: large;
        height: 100px;
    }

    .add{
       position: absolute;
      left:1000px;
      top: 100px;

    }
</style>
