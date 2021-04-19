const jwt = require('jsonwebtoken');
//importar la clave secreta
const config = require('../config')

module.exports = function auth (req, res, next){
    const token = req.header('auth-token');
    if(!token) return res.send({message: "Acceso denegado"})
    try{
    const verificado = jwt.verify(token, config.token_secret)
    req.user = verificado;
    next();
    }catch(err){
    res.status(400).send('Invalid token!');
   }
}

