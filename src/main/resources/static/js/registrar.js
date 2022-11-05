async function registrarUsuario() {

    let datos = {
        nombre: document.getElementById('txtName').value,
        apellido: document.getElementById('txtLastName').value,
        email: document.getElementById('txtEmail').value,
        telefono: document.getElementById('txtPhone').value,
        password: document.getElementById('txtPassword').value
    }

    let repetirPassword = document.getElementById('txtRepeatPassword').value;

    if (repetirPassword == datos.password) {

        const request = await fetch('api/usuarios', {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
        });

        window.location.href = 'login.html'

    } else {

        alert('Las contrasenas no coinciden.');

    }



}