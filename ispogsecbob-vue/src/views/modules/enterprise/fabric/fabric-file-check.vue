<template>
  <el-dialog
    title="验证"
    :close-on-click-modal="false"
    width="600px"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="95px">
      <el-col :span="24">
        <el-row :gutter="12">
          <el-form-item label="文件">
            <el-upload
              class="upload-demo"
              drag
              ref="upload"
              :action=uploadUrl
              multiple
              :limit="1"
              :show-file-list="false"
              :on-exceed="overlimit"
              :on-success="successHandler"
              :on-error="errorHandler"
              :file-list="fileList"
            >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            </el-upload>
          </el-form-item>
        </el-row>
      </el-col>
      <el-col v-if="checkNotFoundVisible">
        <el-row>
          <el-form-item label="结果">
            <el-tag class="el-alert--error">存证系统未存在该记录</el-tag>
          </el-form-item>
        </el-row>
      </el-col>
      <div v-if="resultVisible">
        <el-col>
          <el-row>
            <el-form-item label="结果">
              <el-tag class="el-alert--success">验证成功</el-tag>
            </el-form-item>
          </el-row>
        </el-col>
        <el-col>
          <el-row>
            <el-form-item label="文件名">
              <el-tag class="el-alert--success"><span v-text="dataForm.fileName"></span></el-tag>
            </el-form-item>
          </el-row>
        </el-col>
        <el-col>
          <el-row>
            <el-form-item label="时间">
              <el-tag class="el-alert--success"><span v-text="dataForm.fileTime"></span></el-tag>
            </el-form-item>
          </el-row>
        </el-col>
        <el-col>
          <el-row>
            <el-form-item label="持有人">
              <el-tag class="el-alert--success"><span v-text="dataForm.sysUserEntity.name"></span></el-tag>
            </el-form-item>
          </el-row>
        </el-col>

        <el-col>
          <el-row>
            <el-form-item label="哈希">
              <el-tag class="el-alert--success"><span v-text="dataForm.fileHash"></span></el-tag>
            </el-form-item>
          </el-row>
        </el-col>
      </div>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        resultVisible: false,
        checkNotFoundVisible: false,
        fileList: [],
        uploadUrl: this.$http.adornUrl(`/enterprise/fabric/check?token=${this.$cookie.get('token')}`),
        dataForm: {
          id: 0,
          fileName: '',
          filePath: '',
          fileHash: '',
          fileTime: '',
          userId: '',
          status: '',
          sysUserEntity: ''
        },
        dataRule: {
          fileName: [
            {required: true, message: '文件名不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.fileList = []
        this.resultVisible = false
        this.checkNotFoundVisible = false
        this.dataForm = {}
        this.visible = true
        this.$nextTick(() => {
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/enterprise/fabric/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                console.log(data)
              }
            })
          }
        })
      },
      overlimit () {
        this.$notify.error({
          title: '错误',
          message: '超出文件上传个数限制'
        })
      },
      successHandler (res) {
        // console.log(res)
        if (res.code === 0) {
          if (res.entFabricFileEntity != null) {
            this.dataForm = res.entFabricFileEntity
            this.resultVisible = true
            this.$notify({
              title: '提示',
              message: '验证成功',
              type: 'success'
            })
          } else {
            this.checkNotFoundVisible = true
            this.$notify({
              title: '提示',
              message: '存证系统未存在该记录',
              type: 'success'
            })
          }
        } else {
          this.$message.error(res.msg)
        }
        this.fileList = []
      },
      errorHandler () {
        this.$notify.error({
          title: '错误',
          message: '上传失败'
        })
      }
    }
  }
</script>
