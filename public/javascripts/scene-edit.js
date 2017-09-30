/**
 * Created by wxji on 2017-08-10.
 */
new Vue({
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
        maps: [],
        npc: {attackAble: false},
        npcList: [],
        visible: {
            settingNpc: false,
            npcList: false,
            coordinates: false
        }
    },
    computed: {
        lengthen () {
            return 40
        },
        width () {
            return (Math.floor(this.lengthen * 0.866) << 1)
        },
        offsetWidth () {
            return Math.floor(this.width / 2 * 0.866)
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
            }
            let s = (!map.name || map.name.trim().length === 0) ? (this.visible.coordinates ? x + ',' + y : '') : map.name
            if (x === this.axisPoint.current.x && y === this.axisPoint.current.y) {
                ctx.fillStyle = 'wheat'
                ctx.fill()
                ctx.font = "16px Arial";
                this.drawText(ctx, '#A52A2A', lx, ly, s)
            } else {
                ctx.fillStyle = '#fff'
                ctx.fill()
                ctx.font = "14px Arial";
                this.drawText(ctx, '#27408B', lx, ly, s)
            }
            ctx.closePath()
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
        initMaps () {
            this.maps = []
            this.loading = true
            axios.get('/scene/' + this.getQueryString()['id'] + '/cell').then((response) => {
                document.title = response.data.scene.name
                response.data.cells.forEach((row)=> {
                    row.forEach((cell)=> {
                        if (!cell.npc) {
                            cell.npc = []
                        }
                    })
                })
                this.maps = response.data.cells
                this.canvas.width = this.width * (this.maps[0].length + 0.5) + 2
                this.canvas.height = (this.maps.length * 3 + 1) * 0.5 * this.lengthen + 2
                this.canvas.style.width = this.canvas.width + 'px'
                this.canvas.style.height = this.canvas.height + 'px'
                let div = this.canvas.parentNode
                let marginLeft = div.clientWidth - this.canvas.width
                let marginTop = div.clientHeight - this.canvas.height
                this.canvas.style.marginLeft = Math.floor(marginLeft < 0 ? 0 : marginLeft / 2) + 'px'
                this.canvas.style.marginTop = Math.floor(marginTop < 0 ? 0 : marginTop / 2) + 'px'
                this.loading = false
            })
        },
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
                    debugger
                    this.npc = response.data.data[0]
                    this.visible.settingNpc = true
                })
            } else {
                this.npc = {attackAble: false}
                this.visible.settingNpc = true
            }
        },
        saveNpc () {
            this.loading = true
            this.npc.cellId = this.current.id
            axios.post('/npc', this.npc).then((response) => {
                let exists = false
                for (let i = 0; i < this.current.npc.length; i++) {
                    if (this.current.npc[i].id == response.data.id) {
                        this.current.npc[i] = response.data
                        exists = true
                        break
                    }
                }
                if (!exists) {
                    this.current.npc.push(response.data)
                }
                this.loading = false
                this.$message({
                    message: '保存成功', type: 'success'
                });
                this.visible.settingNpc = false
            }).catch((response) => {
                this.loading = false
                this.$message.error({message: '保存失败'});
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
        switchChange (value) {
            let cell = this.current
            if (cell.x < this.maps[cell.y].length - 1) {
                this.maps[cell.y][cell.x + 1].westOut = cell.eastIn
                this.maps[cell.y][cell.x + 1].westIn = cell.eastOut
            }
            if (cell.x > 0) {
                this.maps[cell.y][cell.x - 1].eastOut = cell.westIn
                this.maps[cell.y][cell.x - 1].eastIn = cell.westOut
            }
            if (cell.y % 2 == 0) {
                if (cell.y > 0) {
                    this.maps[cell.y - 1][cell.x].southWestOut = cell.northEastIn
                    this.maps[cell.y - 1][cell.x].southWestIn = cell.northEastOut
                    if (cell.x > 0) {
                        this.maps[cell.y - 1][cell.x - 1].southEastOut = cell.northWestIn
                        this.maps[cell.y - 1][cell.x - 1].southEastIn = cell.northWestOut
                    }
                }
                if (cell.y < this.maps.length - 1) {
                    this.maps[cell.y + 1][cell.x].northWestOut = cell.southEastIn
                    this.maps[cell.y + 1][cell.x].northWestIn = cell.southEastOut
                    if (cell.x > 0) {
                        this.maps[cell.y + 1][cell.x - 1].northEastOut = cell.southWestIn
                        this.maps[cell.y + 1][cell.x - 1].northEastIn = cell.southWestOut
                    }
                }
            } else {
                if (cell.y > 0) {
                    this.maps[cell.y - 1][cell.x].southEastOut = cell.northWestIn
                    this.maps[cell.y - 1][cell.x].southEastIn = cell.northWestOut
                    if (cell.x < this.maps[cell.y].length - 1) {
                        this.maps[cell.y - 1][cell.x + 1].southWestOut = cell.northEastIn
                        this.maps[cell.y - 1][cell.x + 1].southWestIn = cell.northEastOut
                    }
                }
                if (cell.y < this.maps.length - 1) {
                    this.maps[cell.y + 1][cell.x].northEastOut = cell.southWestIn
                    this.maps[cell.y + 1][cell.x].northEastIn = cell.southWestOut
                    if (cell.x < this.maps[cell.y].length - 1) {
                        this.maps[cell.y + 1][cell.x + 1].northWestOut = cell.southEastIn
                        this.maps[cell.y + 1][cell.x + 1].northWestIn = cell.southEastOut
                    }
                }
            }
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
        // ctx.strokeStyle = '#27408B'
        let fn = () => {
            ctx.clearRect(0, 0, this.canvas.width, this.canvas.height)
            try {
                for (let y = 0; y < this.maps.length; y++) {
                    for (let x = 0; x < this.maps[y].length; x++) {
                        let cell = this.maps[y][x]
                        cell.x = x
                        cell.y = y
                        this.drawHexagon(ctx, x, y, cell)
                        let pixels = this.axisToPixels({x: x, y: y})
                        ctx.beginPath()
                        if (cell.eastIn && cell.eastOut) {
                            ctx.strokeStyle = 'green'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x + this.width / 2, pixels.y)
                            ctx.stroke()
                        } else if (cell.eastIn) {
                            ctx.strokeStyle = 'orange'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x + this.width / 2, pixels.y)
                            ctx.stroke()
                        } else if (cell.eastOut) {
                            ctx.strokeStyle = 'blue'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x + this.width / 2, pixels.y)
                            ctx.stroke()
                        }
                        ctx.closePath()
                        ctx.beginPath()
                        if (cell.southEastIn && cell.southEastOut) {
                            ctx.strokeStyle = 'green'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x + this.width / 4, pixels.y + this.offsetWidth)
                            ctx.stroke()
                        } else if (cell.southEastIn) {
                            ctx.strokeStyle = 'orange'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x + this.width / 4, pixels.y + this.offsetWidth)
                            ctx.stroke()
                        } else if (cell.southEastOut) {
                            ctx.strokeStyle = 'blue'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x + this.width / 4, pixels.y + this.offsetWidth)
                            ctx.stroke()
                        }
                        ctx.closePath()
                        ctx.beginPath()
                        if (cell.southWestIn && cell.southWestOut) {
                            ctx.strokeStyle = 'green'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x - this.width / 4, pixels.y + this.offsetWidth)
                            ctx.stroke()
                        } else if (cell.southWestIn) {
                            ctx.strokeStyle = 'orange'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x - this.width / 4, pixels.y + this.offsetWidth)
                            ctx.stroke()
                        } else if (cell.southWestOut) {
                            ctx.strokeStyle = 'blue'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x - this.width / 4, pixels.y + this.offsetWidth)
                            ctx.stroke()
                        }
                        ctx.closePath()
                        ctx.beginPath()
                        if (cell.westIn && cell.westOut) {
                            ctx.strokeStyle = 'green'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x - this.width / 2, pixels.y)
                            ctx.stroke()
                        } else if (cell.westIn) {
                            ctx.strokeStyle = 'orange'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x - this.width / 2, pixels.y)
                            ctx.stroke()
                        } else if (cell.westOut) {
                            ctx.strokeStyle = 'blue'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x - this.width / 2, pixels.y)
                            ctx.stroke()
                        }
                        ctx.closePath()
                        ctx.beginPath()
                        if (cell.northWestIn && cell.northWestOut) {
                            ctx.strokeStyle = 'green'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x - this.width / 4, pixels.y - this.offsetWidth - 1)
                            ctx.stroke()
                        } else if (cell.northWestIn) {
                            ctx.strokeStyle = 'orange'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x - this.width / 4, pixels.y - this.offsetWidth - 1)
                            ctx.stroke()
                        } else if (cell.northWestOut) {
                            ctx.strokeStyle = 'blue'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x - this.width / 4, pixels.y - this.offsetWidth - 1)
                            ctx.stroke()
                        }
                        ctx.closePath()
                        ctx.beginPath()
                        if (cell.northEastIn && cell.northEastOut) {
                            ctx.strokeStyle = 'green'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x + this.width / 4, pixels.y - this.offsetWidth - 1)
                            ctx.stroke()
                        } else if (cell.northEastIn) {
                            ctx.strokeStyle = 'orange'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x + this.width / 4, pixels.y - this.offsetWidth - 1)
                            ctx.stroke()
                        } else if (cell.northEastOut) {
                            ctx.strokeStyle = 'blue'
                            ctx.moveTo(pixels.x, pixels.y)
                            ctx.lineTo(pixels.x + this.width / 4, pixels.y - this.offsetWidth - 1)
                            ctx.stroke()
                        }
                        ctx.strokeStyle = 'black'
                        ctx.closePath()
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