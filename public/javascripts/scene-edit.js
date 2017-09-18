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
        offset: true,
        maps: [],
        npc: {talk: []},
        npcs: []
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
        },
        minWidth () {
            return 5
        },
        minHeight () {
            return 5
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
                    this.resetCanvasSize(x, y)
                }
            }
            ctx.fillStyle = map.color
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
        resetCanvasSize (x, y) {
            this.resetCanvasHeightSize(y)
            this.resetCanvasWidthSize(x)
        },
        resetCanvasWidthSize (x) {
            if (x === 0) {
                let count = this.maps.filter((r)=> {
                    if (r[0].arrive) return r[0]
                }).length
                if (count > 0) {
                    this.maps.forEach((r)=> {
                        r.unshift(this.createCell())
                    })
                    this.initCanvasSize()
                }
            } else if (x === this.maps[0].length - 1) {
                let count = this.maps.filter((v)=> {
                    if (v[x].arrive) return v[x]
                }).length
                if (count > 0) {
                    this.maps.forEach((r)=> {
                        r.push(this.createCell())
                    })
                    this.initCanvasSize()
                }
            } else if (x === 1) {
                while (true) {
                    if (this.maps[0].length <= this.minWidth) {
                        break
                    }
                    let count = this.maps.filter((v)=> {
                        if (v[x].arrive) return v[x]
                    }).length
                    if (count === 0) {
                        this.maps.forEach((r)=> {
                            r.shift()
                        })
                        this.initCanvasSize()
                    } else {
                        break
                    }
                }
            } else if (x === this.maps[0].length - 2) {
                let t = x
                while (true) {
                    if (this.maps[0].length <= this.minWidth) {
                        break
                    }
                    let count = this.maps.filter((v)=> {
                        if (v[t].arrive) return v[t]
                    }).length
                    if (count === 0) {
                        this.maps.forEach((r)=> {
                            r.splice(t + 1, 1)
                        })
                        this.initCanvasSize()
                        t -= 1
                    } else {
                        break
                    }
                }
            }
        },
        resetCanvasHeightSize (y) {
            if (y === 0) {
                let count = this.maps[y].filter((v)=> {
                    if (v.arrive) return v
                }).length
                if (count > 0) {
                    let row = []
                    this.maps[y].forEach(()=> {
                        row.push(this.createCell())
                    })
                    this.maps.unshift(row)
                    this.offset = !this.offset
                    this.initCanvasSize()
                }
            } else if (y === this.maps.length - 1) {
                let count = this.maps[y].filter((v)=> {
                    if (v.arrive) return v
                }).length
                if (count > 0) {
                    let row = []
                    this.maps[y].forEach(()=> {
                        row.push(this.createCell())
                    })
                    this.maps.push(row)
                    this.initCanvasSize()
                }
            } else if (y === 1 && this.maps.length > this.minHeight) {
                while (true) {
                    let count = this.maps[1].filter((v)=> {
                        if (v.arrive) return v
                    }).length
                    if (count === 0) {
                        this.maps.shift()
                        this.offset = !this.offset
                    } else {
                        break
                    }
                    if (this.maps.length <= this.minHeight) {
                        break
                    }
                }
                this.initCanvasSize()
            } else if (y === this.maps.length - 2 && this.maps.length > this.minHeight) {
                let t = y
                while (true) {
                    let count = this.maps[t].filter((v)=> {
                        if (v.arrive) return v
                    }).length
                    if (count === 0) {
                        this.maps.pop()
                        t -= 1
                    } else {
                        break
                    }
                    if (this.maps.length <= this.minHeight) {
                        break
                    }
                }
                this.initCanvasSize()
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
                let maxX = 0
                let maxY = 0
                this.offset = response.data.isOffset
                response.data.cells.forEach((c)=> {
                    maxX = (maxX > c.x ? maxX : c.x)
                    maxY = (maxY > c.y ? maxY : c.y)
                });
                maxX = (maxX >= this.minWidth ? maxX + 1 : this.minWidth)
                maxY = (maxY >= this.minHeight ? maxY + 1 : this.minHeight)
                for (let y = 0; y < maxY; y++) {
                    let row = []
                    for (let x = 0; x < maxX; x++) {
                        let cell = response.data.cells.find((c)=> {
                            if (c.x === x && c.y === y) {
                                return c
                            }
                        })
                        if (!cell) {
                            cell = this.createCell()
                        }
                        row.push(cell)
                    }
                    this.maps.push(row)
                }
                this.initCanvasSize()
            })
        },
        createCell () {
            return {name: '', description: '', arrive: false, startPoint: false, color: '#ddd'}
        },
        save () {
            this.loading = true
            axios.post('/scene/' + this.getQueryString()['id'] + '/cell', {
                isOffset: this.offset,
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
        document.title = decodeURI(this.getQueryString()['name'])
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
            } catch (e) {
                console.info(e)
            }
            window.requestAnimationFrame(fn)
        }
        fn()
    }
})