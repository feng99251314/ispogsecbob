<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('enterprise:fabric:save')" type="primary" @click="addOrUpdateHandle()">上传凭证文件</el-button>
        <el-button type="success" @click="checkHandler">上传文件验证</el-button>
        <el-button v-if="isAuth('enterprise:fabric:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-card>
      <el-radio-group v-model="hasApply" @change="getDataList">
        <el-radio label="1">我的存证</el-radio>
        <el-radio label="2">存证列表</el-radio>
      </el-radio-group>
    </el-card>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        min-width="20"
        label="序号">
        <template slot-scope="props">
          <p v-text="props.$index+1"></p>
        </template>
      </el-table-column>
      <el-table-column
        prop="fileName"
        header-align="center"
        align="center"
        label="文件名">
      </el-table-column>
      <el-table-column
        prop="fileHash"
        header-align="center"
        align="center"
        label="文件哈希">
      </el-table-column>
      <el-table-column
        prop="fileTime"
        header-align="center"
        align="center"
        min-width="30"
        label="保存时间">
      </el-table-column>
      <el-table-column
        prop="sysUserEntity"
        header-align="center"
        align="center"
        min-width="40"
        label="持有人">
        <template slot-scope="scope">
          <el-tag class="small"><span v-text="scope.row.sysUserEntity.name"></span></el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        min-width="20"
        label="状态">
        <template slot-scope="scope">
          <el-tag class="el-tag--danger" effect="dark" v-if="scope.row.status === 0">待审核</el-tag>
          <el-tag class="el-alert--warning" effect="dark" v-else-if="scope.row.status <= 3 && scope.row.status > 0">审核中</el-tag>
          <el-tag class="small" type="success" effect="dark" v-else="scope.row.status > 3">已存入</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button  v-if="checkProPermissions(scope.row)" type="text" size="small" @click="applyHandle(scope.row.id)">审核</el-button>
          <el-button v-if="scope.row.userId === userId" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
          <el-button v-else-if="scope.row.userId != userId" type="text" size="small">无权限</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <fabric-file-check v-if="checkVisible" ref="check" @refreshDataList="getDataList"></fabric-file-check>
  </div>
</template>

<script>
  import AddOrUpdate from './entfabricfile-add-or-update'
  import FabricFileCheck from './fabric-file-check'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        hasApply: '1',
        userId: this.$store.state.user.id,
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        checkVisible: false,
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate,
      FabricFileCheck
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/enterprise/fabric/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key,
            'hasApply': this.hasApply,
            'userId': this.userId
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            console.log(data)
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 新增 / 修改
      checkHandler (id) {
        this.checkVisible = true
        this.$nextTick(() => {
          this.$refs.check.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/enterprise/fabric/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      },
      // 检查权限
      checkProPermissions (item) {
        console.log(item)
        let user = this.$store.state.user.fabricNodeType
        console.log(user)
        if (this.isAuth('enterprise:fabric:apply')) {
          if (item.allowUser !== null ? item.allowUser.indexOf(user) === -1 : true) {
            if (item.sysUserEntity.fabricNodeType !== user) {
              return true
            }
          }
        }
        return false
      },
      // 审核
      applyHandle (id) {
        this.$confirm(`确定对其审核操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/enterprise/fabric/apply'),
            method: 'post',
            params: this.$http.adornParams({
              'id': id,
              'userId': this.$store.state.user.id
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
          // this.$http({
          //   url: this.$http.adornUrl('/enterprise/fabric/apply'),
          //   method: 'post',
          //   params: this.$http.adornParams({'id': id, 'userId': this.$store.state.user.id})
          // }).then(({data}) => {
          //   if (data && data.code === 0) {
          //     this.$message({
          //       message: '操作成功',
          //       type: 'success',
          //       duration: 1500,
          //       onClose: () => {
          //         this.getDataList()
          //       }
          //     })
          //   } else {
          //     this.$message.error(data.msg)
          //   }
          // })
        })
      }
    }
  }
</script>
