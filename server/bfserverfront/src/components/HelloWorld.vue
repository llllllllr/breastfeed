<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h2>Essential Links</h2>
      <el-table
  :header-row-style="{position:absolute,height:'100px',color:'black'}"
    class="tab"
    :data="tableData"
    height:100px
    stripe
    style="width: 70%">
    <template slot-scope="scope">
    <el-table-column
      class="col"
      prop="title"
      label="标题"
      width="150">
    </el-table-column>
    <el-table-column
    class="col"
      prop="description"
      label="副标题"
      width="180">
    </el-table-column>
    <el-table-column
    class="col"
      prop="category"
      label="分类">
    </el-table-column>
    <el-table-column label="操作">
    <template slot-scope="scope">
        <el-button
          size="mini"
          @click="toEditpage(scope.row.title)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
    </template>
  </el-table>
  </div>

  
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to lyf'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
