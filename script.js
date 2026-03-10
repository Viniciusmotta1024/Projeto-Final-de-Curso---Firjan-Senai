const API = "http://localhost:8080/tarefas";


// CARREGAR TAREFAS
async function carregarTarefas(){

    const res = await fetch(API);
    const tarefas = await res.json();

    const lista = document.getElementById("listaTarefas");

    lista.innerHTML = "";

    tarefas.forEach(t => {

        let li = document.createElement("li");

        li.innerHTML = `
        <strong>${t.titulo}</strong> - ${t.descricao} - ${t.status}

        <button onclick="concluirTarefa(${t.id})">
        Concluir
        </button>

        <button onclick="excluirTarefa(${t.id})">
        Excluir
        </button>
        `;

        lista.appendChild(li);

    });

}


// CRIAR TAREFA
async function criarTarefa(){

    const titulo = document.getElementById("titulo").value;
    const descricao = document.getElementById("descricao").value;

    if(titulo === "" || descricao === ""){
        alert("Preencha todos os campos!");
        return;
    }

    await fetch(API, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            titulo: titulo,
            descricao: descricao
        })

    });

    document.getElementById("titulo").value = "";
    document.getElementById("descricao").value = "";

    carregarTarefas();

}


// EXCLUIR TAREFA
async function excluirTarefa(id){

    await fetch(API + "?id=" + id, {
        method: "DELETE"
    });

    carregarTarefas();

}


// CONCLUIR TAREFA
async function concluirTarefa(id){

    await fetch(API + "?id=" + id, {
        method: "PUT"
    });

    carregarTarefas();

}


// CARREGAR LISTA AUTOMATICAMENTE
carregarTarefas();