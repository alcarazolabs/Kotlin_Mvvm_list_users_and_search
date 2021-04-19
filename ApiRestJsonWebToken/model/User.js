const mongoose = require('mongoose');
const userSchema = new mongoose.Schema({
    name:{
        type: String,
        require: true,
        min: 6,
        max: 255
    },
    email:{
        type: String,
        required: true,
        min: 6,
        max: 255
    },
    phone:{
        type: String,
        required: true,
        min: 9,
        max: 12
    },
    image:{
        type: String,
        required: true,
        min: 9,
        max: 255
    },
    password:{
        type: String,
        require: true,
        max: 1024,
        min: 6
    },
    created_at:{
        type: Date,
        default: Date.now
    }
});

module.exports = mongoose.model('User', userSchema)