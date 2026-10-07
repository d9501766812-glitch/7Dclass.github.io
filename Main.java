<!-- ПОДКЛЮЧЕНИЕ СТАНДАРТНЫХ СКРИПТОВ FIREBASE -->
<script src="https://gstatic.com"></script>
<script src="https://gstatic.com"></script>

<script>
    // ⚠️ ВСТАВЬ СЮДА СВОИ ДАННЫЕ ИЗ КОНСОЛИ FIREBASE
    const firebaseConfig = {
        apiKey: "ТВОЙ_API_KEY",
        authDomain: "ТВОЙ_://firebaseapp.com",
        databaseURL: "https://ТВОЙ_PROJECT-default-rtdb.firebaseio.com",
        projectId: "ТВОЙ_PROJECT_ID",
        storageBucket: "ТВОЙ_://appspot.com",
        messagingSenderId: "ТВОЙ_SENDER_ID",
        appId: "ТВОЙ_APP_ID"
    };

    // Инициализация по классической схеме
    firebase.initializeApp(firebaseConfig);
    const database = firebase.database();

    // 🔑 ТВОЙ СЕКРЕТНЫЙ ПАРОЛЬ (Поменяй на свой!)
    const MY_SECRET_PASSWORD = "sus67"; 

    // Чтение данных в реальном времени
    const dbRef = database.ref('homework');
    dbRef.on('value', (snapshot) => {
        const data = snapshot.val() || {};
        
        renderDay('monday', data.monday, 'monday-list');
        renderDay('tuesday', data.tuesday, 'tuesday-list');
        renderDay('wednesday', data.wednesday, 'wednesday-list');
    }, (error) => {
        alert("Ошибка доступа к базе! Проверь вкладку Rules в Firebase. " + error.message);
    });

    function renderDay(dayName, dayData, elementId) {
        const listElement = document.getElementById(elementId);
        if (!listElement) return;
        listElement.innerHTML = '';
        
        if (!dayData || Object.keys(dayData).length === 0) {
            listElement.innerHTML = '<li>Ничего не задано 🎉</li>';
            return;
        }

        for (const subject in dayData) {
            const li = document.createElement('li');
            li.innerHTML = `<span><strong>${subject}:</strong> ${dayData[subject]}</span>`;
            listElement.appendChild(li);
        }
    }

    // Функция записи ДЗ
    function saveHomework() {
        const day = document.getElementById('day-select').value;
        const subject = document.getElementById('subject-input').value.trim();
        const task = document.getElementById('task-input').value.trim();
        const pass = document.getElementById('secret-password').value;

        if (pass !== MY_SECRET_PASSWORD) {
            alert('❌ Ошибка: Неверный секретный пароль!');
            return;
        }

        if (!subject || !task) {
            alert('⚠️ Ошибка: Заполни поля!');
            return;
        }

        // Сохранение
        database.ref('homework/' + day + '/' + subject).set(task)
        .then(() => {
            alert('✅ Успешно сохранено!');
            document.getElementById('subject-input').value = '';
            document.getElementById('task-input').value = '';
        })
        .catch((error) => {
            alert('❌ Ошибка Firebase: ' + error.message);
        });
    }
</script>