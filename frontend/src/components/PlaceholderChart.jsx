import React from "react";

export default function PlaceholderChart({ title }) {
    return (
        <section className="card chart-placeholder">
            <h2>{title}</h2>
            <div className="placeholder-chart">
                <p>Your data will appear here after uploading a CSV.</p>
            </div>
        </section>
    );
}
