const API_URL = "https://g1gdx3o40j.execute-api.us-east-1.amazonaws.com/lambda";

export async function uploadCsv(file) {
    const arrayBuffer = await file.arrayBuffer();

    const response = await fetch(API_URL, {
        method: "POST",
        headers: {
            "spring.cloud.function.definition": "uploadCsv",
            "Content-Type": "application/octet-stream",
        },
        body: arrayBuffer,
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Upload failed: ${response.status} ${response.statusText}\n${errorText}`);
    }

    return response.json();
}

