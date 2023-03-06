
//editModal

let editForm = document.forms['editForm']

$(async function () {
    await editUser()
})

async function getUserById(id) {
    let userFetch = await fetch(`http://localhost:8080/api/${id}`)
    return await userFetch.json()
}

async function openAndFillModal(form, modal, id) {
    modal.show()

    let user = await getUserById(id)

    form.id.value = user.id
    form.firstname.value = user.firstname
    form.lastname.value = user.lastname
    form.age.value = user.age
    form.email.value = user.email
    form.username.value = user.username
    form.password.value = user.password
    form.roles.value = user.roles[0].id
}

async function editModalData(id) {
    const modal = new bootstrap.Modal(document.querySelector('#editModal'))
    await openAndFillModal(editForm, modal, id)
}

async function editUser() {
    editForm.addEventListener('submit', e => {
        e.preventDefault()

        let checkRole = () => {
            let roles = []
            let option = document.querySelector("#rolesEdit").options

            for (let i = 0; i < option.length; i++) {
                if (option[i].selected) {
                    roles.push(roleList[i])
                }
            }

            return roles
        }

        fetch('http://localhost:8080/api/edit/' + editForm.id.value, {
            method: 'PATCH',
            headers: {
                'Content-Type' : 'application/json',
            },

            body: JSON.stringify({
                id: editForm.id.value,
                firstname: editForm.firstname.value,
                lastname: editForm.lastname.value,
                age: editForm.age.value,
                email: editForm.email.value,
                username: editForm.username.value,
                password: editForm.password.value,
                roles: checkRole()

            })
        })

            .then(() => {
                $('#editCloseButton').click();
                getTableWithAllUsers();
            })

    })
}

//deleteModal

let deleteForm = document.forms['deleteForm']

$(async function () {
    await deleteUser()
})


async function deleteModalData(id) {
    const modal = new bootstrap.Modal(document.querySelector('#deleteModal'))
    await openAndFillModal(deleteForm, modal, id)
}

async function deleteUser() {
    deleteForm.addEventListener('submit', e => {
        e.preventDefault()

        fetch('http://localhost:8080/api/delete/' + deleteForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        })

            .then(() => {
                $('#deleteCloseButton').click();

                getTableWithAllUsers();
            })
    })
}
