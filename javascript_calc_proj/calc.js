
function operate(operation){
    console.log(operation)
    return eval(operation)
}

let operation = ""
let numbers = document.querySelector(".first-portion")
let outputBar = document.querySelector(".output-bar")
numbers.addEventListener("click", function(){
    let message = event.target.innerText
    operation += message
    if (message === "C"){
       operation = ""
       outputBar.innerText = 0
    }else if(message === "←"){
        if (outputBar.innerText.length <= 1){
            outputBar.innerText = 0
            operation = ''
        }else{
            outputBar.innerText = outputBar.innerText.substring(0,(outputBar.innerText.length - 1))
            operation = operation.slice(0,-1)
        }

    }else{
        if (outputBar.innerText === "0"){
            outputBar.innerText = message
        }else{
            outputBar.innerText += message
        }
        
    }
})



let operators = document.querySelector(".operators")
operators.addEventListener("click", function(){
    let message2 = event.target.innerText
    if (message2 === "="){ 
        try{
            outputBar.innerText = operate(operation)
            operation = outputBar.innerText
        }catch(err){
            outputBar.innerText = 0
            operation = ""
        }
        
    }else{
        if (!isNaN(outputBar.innerText)){
            outputBar.innerText += message2
        }else{
            outputBar.innerText = outputBar.innerText.slice(0,-1) + message2
        }
        
    }
    if (!isNaN(operation)){
        if (message2 === "x"){
            operation += "*"
        }else if (message2 === "÷"){
            operation += "/"
        }else{
            operation += message2
        }
    }else{
        if (message2 === "x"){
            operation= operation.slice(0,-1) + "*"
        }else if (message2 === "÷"){
            operation = operation.slice(0,-1) + "/"
        }else{
            operation = operation.slice(0,-1) + message2
        }
    } 
    
        
})

