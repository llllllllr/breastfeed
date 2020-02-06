<template>
   <div>
        <el-container class="mainContainer">
             <el-header>
                 
            <el-menu
                :default-active="activeIndex2"
                class="el-menu-demo"
                mode="horizontal"
                @select="handleSelect"
                background-color="#545c64"
                text-color="#fff"
                padding:0px
                margin:0

                active-text-color="#ffd04b">
                <h3 class="logo"><i class="el-icon-school"></i>母乳喂养</h3>
                <el-menu-item class="fr" index="1">登录</el-menu-item>
              </el-menu>
            </el-header>
            <el-container>

                <el-aside width="200px">
                        <el-menu
                                default-active="2"
                                class="el-menu-vertical-demo"
                                @open="handleOpen"
                                @close="handleClose"
                                background-color="#545c64"
                                text-color="#fff"
                                active-text-color="#ffd04b">
                                <el-submenu index="1">
                                    <template slot="title">
                                    <i class="el-icon-tickets"></i>
                                     <span>图文管理</span>
                                    </template>
                                    <el-menu-item-group>
                                    <el-menu-item index="1-1"><i class="el-icon-reading"></i><router-link to="/articlelist" tag="span" exact>文章管理</router-link></el-menu-item>
                                    <el-menu-item index="1-1"><i class="el-icon-reading"></i><router-link to="/audiolist" tag="span" exact>音频管理</router-link></el-menu-item>
                                    <el-menu-item index="1-1"><i class="el-icon-reading"></i><router-link to="/vediolist" tag="span" exact>视频管理</router-link></el-menu-item>
                                    </el-menu-item-group>
                                </el-submenu>
                    </el-menu>
                </el-aside>
            <el-main><router-view/></el-main>
        </el-container>
        </el-container>
   </div>
</template>


<script>

    export default {
      data() {
        return {
        activeIndex: '1',
        activeIndex2: '1',
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
      methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
        handleSelect(key, keyPath) {
        console.log(key, keyPath);
      }
    }
    }
  </script>
  
<style scoped>
 .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    /* text-align: center; */
    /* line-height: 60px; */
    height: 125%;
  }
  
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
    height: 125%;
  }
  .el-header .fr{
  float: right;
}

  .logo{
  position: absolute;
  left: 60px;
  color: white
}
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    /* //text-align: center; */
    line-height: 160px;
    height: auto;
    
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }
  
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  .router-link-active {
    text-decoration: none;
}

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
  .el-container{
  position: absolute; 
  width: 100%; 
  top: 0px ; 
  left: 0 ; 
  bottom: 0;
}
/* .el-menu-item{
    position: absolute;
    left: 70%
} */
.el-header{
  padding: 0;
  z-index: 100;
  width:100%;
}
.el-header .el-menu{
  border-bottom: none;
}
.el-aside, .el-main{
  padding-top: 60px;
  height: 125%;
}
.el-aside{
  background: #545c64;
  height: 125%;
}
.el-aside .el-menu{
  border-right: none;
}
</style>
