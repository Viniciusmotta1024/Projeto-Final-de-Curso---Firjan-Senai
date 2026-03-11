const API = "http://localhost:8080/tarefas";

function mostrarPopup(mensagem,tipo){

const popup = document.getElementById("popup");
const msg = document.getElementById("popup-msg");

popup.classList.remove("hidden");
popup.classList.remove("sucesso");
popup.classList.remove("erro");

popup.classList.add(tipo);

msg.innerText = mensagem;

setTimeout(()=>{
popup.classList.add("hidden");
},3000);

}

function criarTarefa(){

const titulo = document.getElementById("titulo").value.trim();
const descricao = document.getElementById("descricao").value;
const prioridade = document.getElementById("prioridade").value;
const dataLimite = document.getElementById("dataLimite").value;

if(titulo === ""){

mostrarPopup("O título da tarefa é obrigatório","erro");
return;

}

fetch(API,{
method:"POST",
headers:{
"Content-Type":"application/json"
},
body:JSON.stringify({
titulo:titulo,
descricao:descricao,
prioridade:prioridade,
dataLimite:dataLimite
})
})
.then(()=>{

mostrarPopup("Tarefa criada com sucesso","sucesso");

listarTarefas();

document.getElementById("titulo").value="";
document.getElementById("descricao").value="";
document.getElementById("prioridade").value="";
document.getElementById("dataLimite").value="";

})
.catch(()=>{

mostrarPopup("Erro ao criar tarefa","erro");

});

}

function listarTarefas(){

fetch(API)
.then(res=>res.json())
.then(lista=>{

const div = document.getElementById("tarefas");
div.innerHTML="";


// VERIFICA SE NÃO EXISTEM TAREFAS
if(lista.length === 0){

div.innerHTML = `
<div class="lista-vazia">
📭 Nenhuma tarefa cadastrada
<p>Crie sua primeira tarefa acima.</p>
</div>
`;

return;

}

lista.forEach(t=>{

let statusClass="status-pendente";

if(t.status==="EM_ANDAMENTO"){
statusClass="status-andamento";
}

if(t.status==="CONCLUIDO"){
statusClass="status-concluido";
}

div.innerHTML += `
<div class="tarefa">

<div class="titulo">${t.titulo}</div>

<div class="descricao">${t.descricao || ""}</div>

<div class="info">
Prioridade: ${t.prioridade || "-"} |
Status: <span class="status ${statusClass}">${t.status}</span> |
Data limite: ${t.dataLimite || "-"}
</div>

<div class="acoes">

<button class="btn iniciar" onclick="mudarStatus(${t.id}, 'EM_ANDAMENTO')">
▶ Iniciar
</button>

<button class="btn concluir" onclick="mudarStatus(${t.id}, 'CONCLUIDO')">
✔ Concluir
</button>

<button class="btn excluir" onclick="excluir(${t.id})">
✖ Excluir
</button>

</div>

</div>
`;

});

});

}

function mudarStatus(id,status){

fetch(API+"?id="+id+"&status="+status,{
method:"PUT"
})
.then(()=>{

mostrarPopup("Status atualizado","sucesso");

listarTarefas();

})
.catch(()=>{

mostrarPopup("Erro ao atualizar status","erro");

});

}

function excluir(id){

if(!confirm("Deseja realmente excluir esta tarefa?")){
return;
}

fetch(API+"?id="+id,{
method:"DELETE"
})
.then(()=>{

mostrarPopup("Tarefa excluída com sucesso","sucesso");

listarTarefas();

})
.catch(()=>{

mostrarPopup("Erro ao excluir tarefa","erro");

});

}

listarTarefas();