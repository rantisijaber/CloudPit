import React, { useState } from "react";
import Navbar from "./components/Navbar";
import UploadSection from "./components/UploadSection";
import SummaryCard from "./components/SummaryCard";
import PlaceholderChart from "./components/PlaceholderChart";
import CostChart from "./components/CostChart";
import Loader from "./components/Loader";
import { uploadCsv } from "./api/api";
import "./App.css";

export default function App() {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleUpload = async (file) => {
        setLoading(true);
        try {
            const result = await uploadCsv(file);
            setData(result);
        } catch (err) {
            console.error(err);
            alert(err.message);
        } finally {
            setLoading(false);
        }
    };

    const totals = data
        ? Object.values(data).reduce((acc, month) => {
            Object.entries(month).forEach(([service, cost]) => {
                acc[service] = (acc[service] || 0) + cost;
            });
            return acc;
        }, {})
        : {};

    return (
        <div>
            <Navbar />
            <div className="container">
                <UploadSection onUpload={handleUpload} />
                {loading && <Loader />}
                {data ? (
                    <>
                        <SummaryCard totals={totals} />
                        <section className="card chart-container">
                            <h2>Monthly Cost Breakdown</h2>
                            <CostChart data={data} />
                        </section>
                    </>
                ) : (
                    <>
                        <PlaceholderChart title="Total Per Service" />
                        <PlaceholderChart title="Monthly Cost Breakdown" />
                    </>
                )}
            </div>
        </div>
    );
}
