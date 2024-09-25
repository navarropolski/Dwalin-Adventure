function loadScene(sceneId) {
    fetch(`http://localhost:4567/scene/${sceneId}`)
        .then(response => response.json())
        .then(scene => {
            document.getElementById('scene-name').innerText = scene.name;
            document.getElementById('scene-description').innerText = scene.description;
        })
        .catch(error => {
            alert('Erro ao carregar a cena: ' + error);
        });
}

function getItem() {
    const itemName = document.getElementById('item-name').value;
    const sceneId = document.getElementById('scene-id').value;

    fetch('http://localhost:4567/item/get', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `item_name=${itemName}&scene_id=${sceneId}`
    })
        .then(response => response.text())
        .then(result => {
            alert(result);
        })
        .catch(error => {
            alert('Erro ao pegar o item: ' + error);
        });
}

function checkItem() {
    const itemName = document.getElementById('item-name').value;
    const sceneId = document.getElementById('scene-id').value;

    fetch('http://localhost:4567/item/check', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `item_name=${itemName}&scene_id=${sceneId}`
    })
        .then(response => response.text())
        .then(result => {
            alert(result);
        })
        .catch(error => {
            alert('Erro ao verificar o item: ' + error);
        });
}

function useItem() {
    const itemName = document.getElementById('item-name').value;
    const sceneId = document.getElementById('scene-id').value;

    fetch('http://localhost:4567/item/use', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `item_name=${itemName}&scene_id=${sceneId}`
    })
        .then(response => response.text())
        .then(result => {
            alert(result);
        })
        .catch(error => {
            alert('Erro ao usar o item: ' + error);
        });
}

function useItemWith() {
    const inventoryItem = document.getElementById('inventory-item').value;
    const sceneItem = document.getElementById('scene-item').value;
    const sceneId = document.getElementById('scene-id').value;

    fetch('http://localhost:4567/item/use-with', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `inventory_item=${inventoryItem}&scene_item=${sceneItem}&scene_id=${sceneId}`
    })
        .then(response => response.text())
        .then(result => {
            alert(result);
        })
        .catch(error => {
            alert('Erro ao usar o item com: ' + error);
        });
}

function showInventory() {
    fetch('http://localhost:4567/inventory')
        .then(response => response.text())
        .then(result => {
            alert(result);
        })
        .catch(error => {
            alert('Erro ao carregar o inventário: ' + error);
        });
}

function showHelp() {
    fetch('http://localhost:4567/help')
        .then(response => response.text())
        .then(result => {
            alert(result);
        })
        .catch(error => {
            alert('Erro ao carregar a ajuda: ' + error);
        });
}

function saveGame() {
    fetch('http://localhost:4567/save', { method: 'POST' })
        .then(response => response.text())
        .then(result => {
            alert(result);
        })
        .catch(error => {
            alert('Erro ao salvar o jogo: ' + error);
        });
}

function loadGame() {
    fetch('http://localhost:4567/load', { method: 'POST' })
        .then(response => response.text())
        .then(result => {
            alert(result);
        })
        .catch(error => {
            alert('Erro ao carregar o jogo: ' + error);
        });
}

function restartGame() {
    fetch('http://localhost:4567/restart', { method: 'POST' })
        .then(response => response.text())
        .then(result => {
            alert(result);
        })
        .catch(error => {
            alert('Erro ao reiniciar o jogo: ' + error);
        });
}
