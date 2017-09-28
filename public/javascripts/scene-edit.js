/**
 * Created by wxji on 2017-08-10.
 */
var app = new Vue({
    el: '#app',
    data: {
        activeIndex: '1',
        loading: false,
        pixelsPoint: {
            click: {x: 0, y: 0}
        },
        axisPoint: {
            current: {x: 0, y: 0}
        },
        isdblclick: false,
        maps: [],
        npc: {attack_able: false},
        npcs: [],
        visible: {
            npcSetting: false,
            npcList: false
        }
    },
    computed: {
        lengthen () {
            return 40
        },
        width () {
            return (Math.floor(this.lengthen * 0.866) << 1)
        },
        halfWidth () {
            return this.width >> 1
        },
        quarterHeight () {
            return this.lengthen >> 1
        },
        canvas () {
            return document.getElementById('canvas')
        },
        current () {
            let map = this.maps[this.axisPoint.current.y]
            if (map) {
                return map[this.axisPoint.current.x]
            }
            return {}
        }
    },
    methods: {
        click (e) {
            this.pixelsPoint.click.x = e.offsetX
            this.pixelsPoint.click.y = e.offsetY
        },
        dblclick () {
            this.isdblclick = true
        },
        axisToPixels (axis) {
            return {
                x: axis.x * this.width + ((this.offset ? axis.y + 1 : axis.y) % 2 === 0 ? 0 : this.halfWidth) + this.halfWidth + 1,
                y: axis.y * ((this.lengthen << 1) - (this.lengthen >> 1)) + this.lengthen
            }
        },
        drawHexagon (ctx, x, y, map) {
            let pixels = this.axisToPixels({x: x, y: y})
            let lx = pixels.x
            let ly = pixels.y
            ctx.beginPath()
            this.drawLine(ctx, lx, ly)
            if (ctx.isPointInPath(this.pixelsPoint.click.x, this.pixelsPoint.click.y)) {
                this.axisPoint.current = {x: x, y: y}
                if (this.isdblclick) {
                    map.arrive = !map.arrive
                    if (map.arrive) {
                        map.color = '#fff'
                    } else {
                        map.color = '#ddd'
                    }
                    this.isdblclick = false
                }
            }
            // ctx.fillStyle = map.color
            ctx.fill()
            ctx.closePath()
            if (x === this.axisPoint.current.x && y === this.axisPoint.current.y) {
                ctx.font = "16px Arial";
                this.drawText(ctx, '#A52A2A', lx, ly, (!map.name || map.name.trim().length === 0) ? x + ',' + y : map.name)
            } else {
                ctx.font = "14px Arial";
                this.drawText(ctx, '#27408B', lx, ly, (!map.name || map.name.trim().length === 0) ? x + ',' + y : map.name)
            }
        },
        drawLine (ctx, x, y) {
            ctx.moveTo(x - this.halfWidth, y - this.quarterHeight)
            ctx.lineTo(x, y - this.lengthen)
            ctx.lineTo(x + this.halfWidth, y - this.quarterHeight)
            ctx.lineTo(x + this.halfWidth, y + this.quarterHeight)
            ctx.lineTo(x, y + this.lengthen)
            ctx.lineTo(x - this.halfWidth, y + this.quarterHeight)
            ctx.lineTo(x - this.halfWidth, y - this.quarterHeight)
            ctx.stroke()
        },
        drawText (ctx, color, x, y, text) {
            ctx.textAlign = 'center'
            ctx.textBaseline = 'middle'
            ctx.fillStyle = color
            ctx.fillText(text, x, y)
        },
        initCanvasSize () {
            this.canvas.width = this.width * (this.maps[0].length + 0.5) + 2
            this.canvas.height = (this.maps.length * 3 + 1) * 0.5 * this.lengthen + 2
            this.canvas.style.width = this.canvas.width + 'px'
            this.canvas.style.height = this.canvas.height + 'px'
            let div = this.canvas.parentNode
            let marginLeft = div.clientWidth - this.canvas.width
            let marginTop = div.clientHeight - this.canvas.height
            this.canvas.style.marginLeft = Math.floor(marginLeft < 0 ? 0 : marginLeft / 2) + 'px'
            this.canvas.style.marginTop = Math.floor(marginTop < 0 ? 0 : marginTop / 2) + 'px'
        },
        initMaps () {
            this.maps = []
            axios.get('/scene/' + this.getQueryString()['id'] + '/cell').then((response) => {
                document.title = response.data.scene.name
                // let maxX = response.data.scene.width
                // let maxY = response.data.scene.height
                // for (let y = 0; y < maxY; y++) {
                //     let row = []
                //     for (let x = 0; x < maxX; x++) {
                //         let cell = response.data.cells.find((c)=> {
                //             if (c.x === x && c.y === y) {
                //                 return c
                //             }
                //         })
                //         // if (!cell) {
                //         //     cell = this.createCell()
                //         // }
                //         // if (!cell.npcs) {
                //         //     cell.npcs = []
                //         // }
                //         row.push(cell)
                //     }
                //     this.maps.push(row)
                // }
                this.initCanvasSize()
            })
        },
        // createCell () {
        //     return {name: '', description: '', arrive: false, color: '#ddd', npcs: []}
        // },
        save () {
            this.loading = true
            axios.post('/scene/' + this.getQueryString()['id'] + '/cell', {
                cells: this.maps
            }).then((response) => {
                this.loading = false
                this.$message({
                    message: '保存成功', type: 'success'
                });
                this.initMaps()
            }).catch((response) => {
                this.loading = false
                this.$message.error({message: '保存失败'});
            })
        },
        openNpcSetting (id) {
            if (!!id) {
                axios.get('/npc', {params: {id: id}}).then((response)=> {
                    this.npc = response.data.data[0]
                    this.visible.npcSetting = true
                })
            } else {
                this.npc = {attack_able: false}
                this.visible.npcSetting = true
            }
        },
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
        deleteNpcTalk (e) {
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
        getQueryString () {
            let queryString = {}
            window.location.search.substr(1).split('&').forEach((arr)=> {
                let s = arr.split('=')
                queryString[s[0]] = s[1]
            })
            return queryString
        }
    },
    mounted () {
        this.initMaps()
        let ctx = this.canvas.getContext('2d')
        ctx.strokeStyle = '#27408B'
        let fn = () => {
            ctx.clearRect(0, 0, this.canvas.width, this.canvas.height)
            try {
                for (let y = 0; y < this.maps.length; y++) {
                    for (let x = 0; x < this.maps[y].length; x++) {
                        let cell = this.maps[y][x]
                        cell.x = x
                        cell.y = y
                        this.drawHexagon(ctx, x, y, cell)
                    }
                }
                window.requestAnimationFrame(fn)
            } catch (e) {
                console.info(e)
            }
        }
        fn()
    }
})