import React, { useMemo, useState } from "react";
import {
    ResponsiveContainer,
    BarChart,
    Bar,
    XAxis,
    YAxis,
    Tooltip,
    Legend,
} from "recharts";
import "../App.css";

export default function CostChart({ data }) {
    const [visibleCount, setVisibleCount] = useState(8);

    const { chartData, topServices } = useMemo(() => {
        const chartData = Object.entries(data).map(([date, costs]) => ({
            date,
            ...costs,
        }));

        const totals = {};
        chartData.forEach((entry) => {
            Object.entries(entry).forEach(([key, val]) => {
                if (key !== "date") totals[key] = (totals[key] || 0) + val;
            });
        });

        const sorted = Object.entries(totals)
            .sort((a, b) => b[1] - a[1])
            .map(([key]) => key);

        return { chartData, topServices: sorted };
    }, [data]);

    const visibleServices = topServices.slice(0, visibleCount);

    return (
        <div className="chart-wrapper">
            <div className="chart-controls">
                <label>
                    Show top{" "}
                    <select
                        value={visibleCount}
                        onChange={(e) => setVisibleCount(Number(e.target.value))}
                    >
                        {[5, 8, 10, 15, 20].map((n) => (
                            <option key={n} value={n}>
                                {n}
                            </option>
                        ))}
                    </select>{" "}
                    services
                </label>
            </div>

            <ResponsiveContainer>
                <BarChart
                    data={chartData}
                    margin={{ top: 20, right: 30, left: 0, bottom: 10 }}
                >
                    <XAxis dataKey="date" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    {visibleServices.map((service, index) => (
                        <Bar
                            key={service}
                            dataKey={service}
                            stackId="a"
                            fill={`hsl(${(index * 47) % 360}, 65%, 55%)`}
                            animationDuration={300}
                            isAnimationActive={false}
                        />
                    ))}
                </BarChart>
            </ResponsiveContainer>
        </div>
    );
}



