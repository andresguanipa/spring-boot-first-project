// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarUsuarios();
  $('#usuarios').DataTable();
});

function getHeaders() {

    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    };

}

async function cargarUsuarios() {

    const request = await fetch('api/usuarios', {
        method: 'GET',
        headers: getHeaders(),
    });

    const usuarios = await request.json();

    let usuarioHTML = '';

    for (usuario of usuarios) {

        usuarioHTML = usuarioHTML + `
                <tr>
                    <td>123</td>
                    <td>${usuario.nombre} ${usuario.apellido}</td>
                    <td>${usuario.email}</td>
                    <td>${usuario.telefono}</td>
                    <td>
                        <a href="#" onclick="eliminarUsuario(${usuario.id})" class="btn btn-danger btn-circle">
                            <i class="fas fa-trash"></i>
                        </a>
                    </td>
                </tr>
        `;

    }


    document.querySelector('#usuarios tbody').outerHTML = usuarioHTML;

}

async function eliminarUsuario(id) {

    if(confirm('¿Desea eliminar el usuario?')){

        const request = await fetch(`api/usuarios/${id}`, {
            method: 'DELETE',
            headers: getHeaders(),
        });

        cargarUsuarios();

    }

}
