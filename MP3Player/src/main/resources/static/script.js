document.addEventListener("DOMContentLoaded", function () {
    const audioElement = document.getElementById("audio");
    const audioSource = document.getElementById("audio-source");
    const songList = document.getElementById("songs");


    fetch("/api/songs")
        .then(response => response.json())
        .then(songs => {
            songs.forEach(song => {
                const li = document.createElement("li");
                li.textContent = `${song.title} - ${song.artist}`;
                li.setAttribute("data-id", song.id); // Добавляем атрибут data-id
                li.setAttribute("data-title", song.title); // Добавляем атрибут data-title
                li.addEventListener("click", () => playSong(song.filePath));

                deleteByIdButton = addButtonDelById(song);

               deleteByTitleButton = addButtonDelByName(song);

                // Добавляем кнопки в элемент списка
                li.appendChild(deleteByIdButton);
                li.appendChild(deleteByTitleButton);

                // Добавляем элемент списка в DOM
                songList.appendChild(li);
            });
        }).catch(error => console.error("Ошибка загрузки песен:", error));
})


document.getElementById("add-song-form").addEventListener("submit", function (event) {
    event.preventDefault(); // Предотвращаем стандартное поведение формы

    const title = document.getElementById("title").value;
    const artist = document.getElementById("artist").value;
    const filePath = document.getElementById("filePath").value;

    const newSong = {
        title: title,
        artist: artist,
        filePath: filePath
    };

    fetch("/api/songs", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(newSong)
    })
        .then(response => response.json())
        .then(song => {
            // Добавляем новый трек в список
            const li = document.createElement("li");
            li.textContent = `${newSong.title} - ${newSong.artist}`;
            song.filePath = newSong.filePath;
            li.addEventListener("click", () => playSong(newSong.filePath));
            deleteByIdButton = addButtonDelById(newSong);

            deleteByTitleButton = addButtonDelByName(newSong);

            // Добавляем кнопки в элемент списка
            li.appendChild(deleteByIdButton);
            li.appendChild(deleteByTitleButton);
            document.getElementById("songs").appendChild(li);

            // Очищаем форму
            document.getElementById("add-song-form").reset();
        })
        .catch(error => console.error("Ошибка при добавлении песни:", error));
});


function playSong(filename) {
    const audioElement = document.getElementById("audio");
    const audioSource = document.getElementById("audio-source");
    audioSource.src = `/files/${filename}`; // Запрашиваем файл через бэкенд
    console.log(`Запрашиваем файл: ${audioSource.src}`);
    audioElement.load();
    audioElement.play();
}

function deleteSongById(id) {
    fetch(`/api/songs/${id}`, {
        method: "DELETE"
    })
        .then(response => {
            if (response.ok) {
                // Удаляем элемент из списка
                const songElement = document.querySelector(`li[data-id="${id}"]`);
                if (songElement) {
                    songElement.remove();
                }
                console.log("Песня удалена по ID:", id);
            } else {
                console.error("Ошибка при удалении песни по ID:", id);
            }
        })
        .catch(error => console.error("Ошибка:", error));
}


function deleteSongByTitle(title) {
    fetch(`/api/songs/Bytitle/${encodeURIComponent(title)}`, {
        method: "DELETE"
    })
        .then(response => {
            if (response.ok) {
                // Удаляем элемент из списка
                const songElement = document.querySelector(`li[data-title="${title}"]`);
                if (songElement) {
                    songElement.remove();
                }
                console.log("Песня удалена по названию:", title);
            } else {
                console.error("Ошибка при удалении песни по названию:", title);
            }
        })
        .catch(error => console.error("Ошибка:", error));
}


function addButtonDelById(song){
    const deleteByIdButton = document.createElement("button");
    deleteByIdButton.textContent = "Удалить по ID";
    deleteByIdButton.addEventListener("click", (event) => {
        event.stopPropagation(); // Предотвращаем всплытие события
        deleteSongById(song.id);
    });
    return deleteByIdButton;
}

function addButtonDelByName(song){
    // Кнопка удаления по названию
    const deleteByTitleButton = document.createElement("button");
    deleteByTitleButton.textContent = "Удалить по названию";
    deleteByTitleButton.addEventListener("click", (event) => {
        event.stopPropagation(); // Предотвращаем всплытие события
        deleteSongByTitle(song.title);
    });
    return deleteByTitleButton
}