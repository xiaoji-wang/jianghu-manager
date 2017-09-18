/**
 * Created by wxji on 2017-09-18.
 */
Vue.component('scene-cell-npc-dialog', {
    template: '\
    <el-dialog title="NPC设置窗口" :visible.sync="visible.npcSetting" size="small">\
        <template>\
            <el-tabs active-name="view">\
                <el-tab-pane label="基本信息" name="base">\
                    <el-input v-model="npc.name" placeholder="名字" icon="search":on-icon-click="handleSelect">\
                    </el-input>\
                    <el-input type="textarea" :rows="4" placeholder="描述" v-model="npc.desc">\
                    </el-input>\
                </el-tab-pane>\
                <el-tab-pane label="交易" name="deal">\
                    <el-row :gutter="24" v-for="t in npc.talk" style="margin-bottom: 5px;">\
                        <el-col :span="24">\
                            <el-input placeholder="对话" v-model="t.word">\
                            </el-input>\
                        </el-col>\
                    </el-row>\
                    <el-button type="primary" @click="addNpcTalk">新增对话</el-button>\
                </el-tab-pane>\
                <el-tab-pane label="战斗" name="fight"></el-tab-pane>\
                <el-tab-pane label="任务" name="task"></el-tab-pane>\
            </el-tabs>\
        </template>\
        <div slot="footer" class="dialog-footer">\
            <el-button @click="visible.npcSetting = false">取消</el-button>\
            <el-button @click="saveNpc">保存</el-button>\
            <el-button type="primary" @click="addNpcToCell">添加</el-button>\
        </div>\
    </el-dialog>\
    ',
    data:{
        visible: {
            npcSetting: false,
            npcList: false
        }
    },
    methods:{
        saveNpc () {
            this.loading = true
            axios.post('/npc', this.npc).then((response) => {
                this.npc.id = response.data.id
                this.loading = false
                this.$message({
                    message: '保存成功', type: 'success'
                });
            }).catch((response) => {
                this.loading = false
                this.$message.error({message: '保存失败'});
            })
        },
        addNpcTalk () {
            this.npc.talk.push({word: ''})
        },
        deleteNpcTalk (e) {
            // debugger
            // e.target.parentNode.parentNode.parentNode.remove();
            // this.npc.talks.splice(index)
        },
        addNpcToCell () {
            if (!this.npc.id) {
                this.$message.error({message: 'NPC未保存'});
                return
            }
            let npcs = this.current.npcs.filter((n)=> {
                if (n.id === this.npc.id) {
                    return n
                }
            })
            if (npcs.length <= 0) {
                this.current.npcs.push(this.npc)
            } else {
                this.$message({message: 'NPC已添加'});
            }
            this.visible.npcSetting = false
        },
        openNpcSetting (id) {
            axios.get('/npc', {params: {id: id}}).then((response)=> {
                this.npc = response.data
                this.visible.npcSetting = true
            })
        },
        handleSelect () {
            this.npcsDialogVisible = true
        },
        selectNpc () {

        },
        npcsDialogOpen () {
            axios.get('/npc').then((response) => {
                response.data.forEach((v)=> {
                    this.npcs.push(v)
                })
            })
        },
    }
})