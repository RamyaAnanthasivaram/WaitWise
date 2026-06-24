async function addPatient() {
    const name = document.getElementById("name").value;
    const emergency = document.getElementById("emergency").checked;

    if(name.trim() === ""){
        alert("Enter patient name");
        return;
    }

    await fetch(`/api/add?name=${name}&emergency=${emergency}`, {
        method: "POST"
    });

    document.getElementById("name").value = "";
    document.getElementById("emergency").checked = false;

    loadPatients();
}

async function callNext() {
    await fetch(`/api/next`, {
        method: "POST"
    });

    loadPatients();
}

async function setAvg() {
    const avg = document.getElementById("avg").value;

    if(avg.trim() === ""){
        alert("Enter average time");
        return;
    }

    await fetch(`/api/avg/${avg}`, {
        method: "POST"
    });

    alert("Average time updated!");
}

async function cancelPatient(id) {
    await fetch(`/api/cancel/${id}`, {
        method: "DELETE"
    });

    loadPatients();
}

async function loadPatients() {
    const response = await fetch('/api/patients');
    const patients = await response.json();

    patients.sort((a,b) => b.token - a.token);

    let table = `
        <tr>
            <th>Token</th>
            <th>Name</th>
            <th>Status</th>
            <th>Emergency</th>
            <th>Action</th>
        </tr>
    `;

    patients.forEach(p => {
        table += `
        <tr>
            <td>#${p.token}</td>
            <td>${p.name}</td>
            <td>${p.status}</td>
            <td>${p.emergency ? "YES" : "NO"}</td>
            <td>
                <button onclick="cancelPatient(${p.id})">Cancel</button>
            </td>
        </tr>
        `;
    });

    document.getElementById("patientTable").innerHTML = table;
}

function toggleDarkMode() {
    document.body.classList.toggle("dark");
}

loadPatients();