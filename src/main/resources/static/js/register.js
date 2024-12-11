document.getElementById("registrationForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const formData = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value,
        name: document.getElementById("name").value,
        telephone: document.getElementById("telephone").value
    };

    try {
        const response = await fetch("api/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formData)
            }
        );

        if (response.ok){
            const message = response.text();
            alert(message)
            window.location.href = "/login";
        }else if (response.status === 409) {
            const errorText = await response.text();
            alert(errorText);
        }else{
            alert("Failed to register. Please try again.");
        }
    }catch (error){
        console.error("Request Error:",error);
        alert("Error connecting to the server. Check the network.");
    }
});