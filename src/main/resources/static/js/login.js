async function login() {

    let datos = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    }

    const request = await fetch('api/login', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });

    const res = await request.text();

    if (res != 'FAIL'){

        localStorage.token = res;
        localStorage.email = datos.email;
        window.location.href = 'usuarios.html';

    } else {

        alert("Las credenciales son incorrectas");

    }

}