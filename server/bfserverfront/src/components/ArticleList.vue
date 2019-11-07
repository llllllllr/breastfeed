<template>
<div>
    <el-button v-on:click="toAddpage" class="add"  type="primary" icon="el-icon-circle-plus">添加文章</el-button>

  <el-table
  :header-row-style="{position:absolute,height:'100px',color:'black'}"
    class="tab"
    :data="tableData"
    height:100px
    stripe
    style="width: 70%">
    <el-table-column
      class="col"
      prop="date"
      label="日期"
      width="150">
    </el-table-column>
    <el-table-column
    class="col"
      prop="name"
      label="姓名"
      width="180">
    </el-table-column>
    <el-table-column
    class="col"
      prop="address"
      label="地址">
    </el-table-column>
    <el-table-column label="操作">
    <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        tableData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }]
      }
    },
    methods:{
      toAddpage:function(){
          this.$router.push({
          path: '/addarticle',
        })
      }
    }
  }
</script>

<style scoped>
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
