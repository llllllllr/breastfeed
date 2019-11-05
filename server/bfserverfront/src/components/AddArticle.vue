<template>
<div >
    <div style="margin: 20px;"></div>
    <div class="form">
        <el-form :label-position="labelPosition" label-width="50px" :model="formLabelAlign">
        <el-form-item label="标题">
            <el-input v-model="formLabelAlign.name"></el-input>
        </el-form-item>
        <el-form-item label="简介">
            <el-input v-model="formLabelAlign.region"></el-input>
        </el-form-item>
        </el-form>
    </div>
   
       <div id="editor"></div>
</div>
</template>


<script type="text/javascript" src="/wangEditor.min.js"></script>
<script>
import Editor from 'wangeditor'
export default {
    data() {
        return {
            editor:'',
            labelPosition: 'right',
            formLabelAlign: {
            name: '',
            region: '',
            type: ''
        }
        }
    },
    mounted() {
       
        this.editor= new Editor('#editor')
        // 或者 var editor = new E( document.getElementById('editor') )
        this.editor.create()

    },
}
 
        
</script>  

<style scoped>

#editor{

    position: absolute;
    top:300px;
    left: 
    320px;
}
.form{
    position: absolute;
    width:500px;
    left: 320px;
    top: 150px;
}
</style>