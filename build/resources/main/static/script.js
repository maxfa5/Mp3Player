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
                console.log("Песни:", song);
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
            li.textContent = `${song.title} - ${song.artist}`;
            li.addEventListener("click", () => playSong(song.filePath));
            deleteByIdButton = addButtonDelById(song);
            console.log("Песня созданна:", song);
            deleteByTitleButton = addButtonDelByName(song);

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


function addSongToPlaylist() {
    const playlistId = document.getElementById("manage-playlist-id").value;
    const songId = document.getElementById("manage-song-id").value;

    if (!playlistId || !songId) {
        alert("Введите ID плейлиста и ID песни!");
        return;
    }

    fetch(`/api/playlists/${playlistId}/add-song/${songId}`, {
        method: "POST"
    })
        .then(response => response.json())
        .then(playlist => {
            console.log("Песня добавлена в плейлист:", playlist);
        })
        .catch(error => console.error("Ошибка при добавлении песни:", error));
}

function removeSongFromPlaylist() {
    const playlistId = document.getElementById("playlist-id").value;
    const songId = document.getElementById("song-id").value;

    if (!playlistId || !songId) {
        alert("Введите ID плейлиста и ID песни!");
        return;
    }

    fetch(`/api/playlists/${playlistId}/remove-song/${songId}`, {
        method: "POST"
    })
        .then(response => response.json())
        .then(playlist => {
            console.log("Песня удалена из плейлиста:", playlist);
        })
        .catch(error => console.error("Ошибка при удалении песни:", error));
}


function loadPlaylistById() {
    const id = document.getElementById("playlist-id").value;
    if (!id) {
        alert("Введите ID плейлиста!");
        return;
    }

    fetch(`/api/playlists/${id}`)
        .then(response => response.json())
        .then(playlist => {
            displayPlaylist(playlist);
        })
        .catch(error => console.error("Ошибка загрузки плейлиста:", error));
}

function loadPlaylistByName() {
    const name = document.getElementById("playlist-name_finder").value;
    console.log(document.getElementById("playlist-name_finder"));
    if (!name) {
        alert("Введите название плейлиста!");
        return;
    }

    fetch(`/api/playlists/ByName/${encodeURIComponent(name)}`)
        .then(response => response.json())
        .then(playlist => {
            displayPlaylist(playlist);
        })
        .catch(error => console.error("Ошибка загрузки плейлиста:", error));
}

function displayPlaylist(playlist) {
    const playlistTracks = document.getElementById("playlist-tracks");
    playlistTracks.innerHTML = ""; // Очищаем список

    if (playlist.songs && playlist.songs.length > 0) {
        playlist.songs.forEach(song => {
            const li = document.createElement("li");
            li.textContent = `${song.title} - ${song.artist}`;
            playlistTracks.appendChild(li);
        });
    } else {
        const li = document.createElement("li");
        li.textContent = "В плейлисте нет треков.";
        playlistTracks.appendChild(li);
    }
}

function createPlaylist() {
    const name = document.getElementById("playlist-name").value;
    if (!name) {
        alert("Введите название плейлиста!");
        return;
    }

    fetch("/api/playlists", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `name=${encodeURIComponent(name)}`
    })
        .then(response => response.json())
        .then(playlist => {
            console.log("Плейлист создан:", playlist);
            loadPlaylists(); // Обновляем список плейлистов
        })
        .catch(error => console.error("Ошибка при создании плейлиста:", error));
}

function deletePlaylistById(id) {
    fetch(`/api/playlists/${id}`, {
        method: "DELETE"
    })
        .then(response => {
            if (response.ok) {
                console.log("Плейлист удалён по ID:", id);
                loadPlaylists(); // Обновляем список плейлистов
            } else {
                console.error("Ошибка при удалении плейлиста по ID:", id);
            }
        })
        .catch(error => console.error("Ошибка:", error));
}

function deletePlaylistByName(name) {
    fetch(`/api/playlists/by-name/${encodeURIComponent(name)}`, {
        method: "DELETE"
    })
        .then(response => {
            if (response.ok) {
                console.log("Плейлист удалён по названию:", name);
                loadPlaylists(); // Обновляем список плейлистов
            } else {
                console.error("Ошибка при удалении плейлиста по названию:", name);
            }
        })
        .catch(error => console.error("Ошибка:", error));
}

function loadPlaylists() {
    fetch("/api/playlists")
        .then(response => response.json())
        .then(playlists => {
            const playlistList = document.getElementById("playlist-list");
            playlistList.innerHTML = ""; // Очищаем список

            playlists.forEach(playlist => {
                const li = document.createElement("li");
                li.textContent = `${playlist.name}`;

                // Кнопка удаления по ID
                const deleteByIdButton = document.createElement("button");
                deleteByIdButton.textContent = "Удалить по ID";
                deleteByIdButton.addEventListener("click", (event) => {
                    event.stopPropagation();
                    deletePlaylistById(playlist.id);
                });

                // Кнопка удаления по названию
                const deleteByNameButton = document.createElement("button");
                deleteByNameButton.textContent = "Удалить по названию";
                deleteByNameButton.addEventListener("click", (event) => {
                    event.stopPropagation();
                    deletePlaylistByName(playlist.name);
                });

                // Добавляем кнопки в элемент списка
                li.appendChild(deleteByIdButton);
                li.appendChild(deleteByNameButton);

                // Добавляем элемент списка в DOM
                playlistList.appendChild(li);
            });
        })
        .catch(error => console.error("Ошибка загрузки плейлистов:", error));
}

// Загружаем плейлисты при загрузке страницы
document.addEventListener("DOMContentLoaded", loadPlaylists);
