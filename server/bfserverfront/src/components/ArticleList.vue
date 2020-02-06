<template>
  <div>
    <div class="crumb">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>文章列表</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button v-on:click="toAddpage" class="add" type="primary" icon="el-icon-circle-plus">添加文章</el-button>
    </div>

    <div>
      <el-table :data="tableData.slice((currentPage-1)*pagesize,currentPage*pagesize)" style="width: 60%" class="tab">
        <el-table-column label="id" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column label="标题" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.title }}</span>
          </template>
        </el-table-column>
        <el-table-column label="简述" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.description }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, tableData)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="page">
      <el-pagination  background 
      layout="prev, pager, next" 
       :total="totalData"
       @current-change="current_change">
       </el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tableData: [],
      totalData: 0,
      pagesize: 8,
      currentPage:1
    };
  },
  mounted() {
    var g_articleList = [];
    this.$axios({
      method: "get",
      url: "http://localhost:8887/article/getlist",
      xhrFields: { withCredentials: true }
    }).then(res => {
      g_articleList = res.data;
      console.log(res.data.data);
      this.tableData = res.data.data;
      this.totalData = this.tableData.length;
      //alert(this.totalData);
    });
  },

  methods: {
    handleEdit(index, row) {
      var item = row.id;
      this.$router.push({
        name: "editarticle",
        params: {
          id: item
        }
      });
    },
    handleDelete(index, row) {
      console.log(index, row);
      this.$axios({
        method: "get",
        url: "http://localhost:8887/article/del",
        params: {
          id: row[index].id
        }
      }).then(res => {
        var resData = res.data;
        if (resData.status == 1) {
          this.$message.success(resData.msg);
        } else this.$message.error(resData.msg);
      });
      row.splice(index, 1);
    },
    toAddpage: function() {
      this.$router.push({
        path: "/addarticle"
      });
    },
    toEditpage: function(vtitle) {
      this.$router.push({
        path: "/editarticle",
        params: {
          title: vtitle
        }
      });
    },
    test: function() {
      this.$axios({
        method: "get",
        url: "http://localhost:8888/article/edit",
        xhrFields: { withCredentials: true }
      }).then(res => {
        g_articleList = res.data;
        console.log(res.data);
        this.tableData = res.data;
      });
    },
    current_change:function(currentPage){
        this.currentPage = currentPage;
      },
  }
};
</script>

<style scoped>
.crumb {
  margin-top: 50px;
  margin-left: 80px;
}
.tab {
  position: absolute;
  top: 150px;
  left: 300px;
  line-height: 12px;
}

.el-table th {
  background: black !important;
  font-size: large;
  height: 100px;
}

.add {
  position: absolute;
  left: 1000px;
  top: 100px;
}
.page{
  margin-top: 550px;
  margin-left: 400px
}
</style>
