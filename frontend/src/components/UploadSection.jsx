import React from "react";
import FileUploader from "./FileUploader";

export default function UploadSection({ onUpload }) {
    return (
        <section className="card">
            <header>
                <h2>Upload CSV</h2>
                <p>Upload your AWS cost CSV to analyze your cloud expenses.</p>
            </header>
            <FileUploader onUpload={onUpload} />
        </section>
    );
}
