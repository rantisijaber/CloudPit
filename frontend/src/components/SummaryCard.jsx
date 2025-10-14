import React from "react";
import "../App.css"

export default function SummaryCard({ totals }) {
    return (
        <section className="card summary-card">
            <header className="summary-header">
                <h2>Total per Service</h2>
            </header>
            <div className="summary-items">
                {Object.entries(totals).map(([service, cost]) => (
                    <div key={service} className="summary-item">
                        <h3>{service}</h3>
                        <p>${cost.toFixed(2)}</p>
                    </div>
                ))}
            </div>
        </section>
    );
}
