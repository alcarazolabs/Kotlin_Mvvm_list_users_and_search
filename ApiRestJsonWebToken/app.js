//npm install express jsonwebtoken mongoose cors bcryptjs
const express = require('express');
const app = express();
//conectarse a la base de datos:
require('./database.js')
//cors para compartir los recursos CORS 
var cors = require('cors')
app.use(cors())
//importar rutas
const authRoute = require('./routes/user');

//json middleware (post, get requests)..
app.use(express.json());
app.use(express.urlencoded({ extended: true })) 
//rutas middlewares
app.use('/api/user', authRoute);

app.listen(5000, () => console.log('Servidor Corriendo en Puerto 5000'))