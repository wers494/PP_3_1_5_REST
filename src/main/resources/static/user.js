$(async function () {
    await getTableWithUserInfo()
    await headerFillingUser()
})


async function getTableWithUserInfo() {
    const table = $('#tbodyUser')
    table.empty()

    await fetch("http://localhost:8080/api/info")
        .then(response => response.json())
        .then(info => {
            $('#loginUserPage').append(info.username)
            let roles = info.roles.map(r => r.role.substring(5) + " ")
            $('#roleUserPage').append(roles)

            let filling = `$(
                <tr>
                    <td>${info.id}</td>
                    <td>${info.firstname}</td>
                    <td>${info.lastname}</td>
                    <td>${info.age}</td>
                    <td>${info.email}</td>
                    <td>${info.roles.map(r => r.role.substring(5))}</td>
                </tr>
            )`
            table.append(filling)
        })

        .catch(err => console.log(err))
}

async function headerFillingUser() {
    await fetch('http://localhost:8080/api/info')
        .then(res => res.json())
        .then(info => {
                $('#loginUser').append(info.username)
                let roles = info.roles.map(r => r.role.substring(5) + " ")
                $('#userRoleHeaderUser').append(roles)
            }
        )
}