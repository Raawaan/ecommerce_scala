const jsonServer = require('json-server')
const server = jsonServer.create()
server.use(jsonServer.bodyParser)

server.post('/account/:id/deduct', (req, res) => {
    res.send({result:`${req.body.amount} is done successfully`})
})
server.listen(3000, () => {console.log('JSON Server is running')})
