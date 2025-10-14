import React, { useState } from "react";

export default function FileUploader({ onUpload }) {
    const [file, setFile] = useState(null);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (file) onUpload(file);
    };

    return (
        <form onSubmit={handleSubmit} className="file-uploader">
            <input
                type="file"
                accept=".csv"
                onChange={(e) => setFile(e.target.files[0])}
            />
            <button type="submit" disabled={!file}>
                Upload
            </button>
        </form>
    );
}

