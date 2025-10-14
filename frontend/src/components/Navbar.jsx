import React from "react";
import cloudpitLogo from "../assets/cloudpit-logo.png";

export default function Navbar() {
    return (
        <nav className="site-nav">
            <div className="nav-content">
                <div className="logo">
                    <img src={cloudpitLogo} alt="Cloudpit Logo" className="logo-img" />
                    <div className="logo-text">
                        <span className="brand">CloudPit</span>
                    </div>
                </div>
            </div>
        </nav>
    );
}
