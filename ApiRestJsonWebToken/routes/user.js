const router = require('express').Router();
const User = require('../model/User')
//importar encriptador de passwords
const bcrypt = require('bcryptjs');
//importar jsonwebtoken
const jwt = require('jsonwebtoken')
//importar la clave secreta
const config = require('../config')

const verificarToken = require('./verificarToken')

router.post('/registrar', async (req, res) =>{
    
    const salt = await bcrypt.genSalt(10);
    //Crear hash
    const hashedPassword = await bcrypt.hash(req.body.password, salt);

    const user = new User({
        name: req.body.name,
        email: req.body.email,
        image: req.body.image,
        phone: req.body.phone,
        password: hashedPassword
    });

    try{
        const userguardado = await user.save();
        
        res.send({message: "Registrado correctamente"})
    }catch(err){
        res.send({message: "Ocurrio un error"})
    }
});
//funcion para remover caracteres especiales
function escapeRegExp(string) {
    return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

router.post('/search', async (req, res) =>{
    
    let criteria = escapeRegExp(req.body.name)
   
    User.find({ 'name': new RegExp(criteria, 'i')}, '-_id name email image phone').exec((err, result) => {
        if (err) {
          return res.status(401).json({ err })
        }
        res.json({results: result})
      })
    
});

router.get('/users', async (req, res) =>{
        //-_id se excluye el campo. Si no se hace el _id vendra en la consulta a pesar de haberlo seleccionado.
        try{
            const users = await User.find({}).select('-_id name email image phone')
            res.json({results: users})
        }catch(error){
            return res.status(401).send("Ocurrio un error en la consulta.")
        }
    
}); 


router.post('/login', async (req, res) =>{
   
    const user = await User.findOne({email: req.body.email})
    //if(!user) return res.send({message: "Email no existe"})
    if(!user)  return res.status(401).send('Email no encontrado')

    const validPass = await bcrypt.compare(req.body.password, user.password)
    //if(!validPass) return res.send({message: "Contraseña invalida"})
    if(!validPass) return res.status(401).send('Contraseña invalida')

    //Crear y asignar token
    const token = jwt.sign({_id: user._id}, config.token_secret);
    var usuario = {
        id: user.id,
        access_token: token,
        name: user.name,
        phone: user.phone,
        email: user.email,
        created_at: user.created_at
    }
    
    res.json({user:usuario})
});

module.exports = router;
