/**
 * Fleet Management System — Frontend JavaScript
 * Connects the dashboard UI to the Spring Boot REST API.
 * All CRUD operations directly modify the MySQL database via APIs.
 */

// ── API Base URL ──
const API = import.meta.env?.VITE_API_URL || 'http://localhost:8080/api';

// ── Tab Navigation ──
document.querySelectorAll('.nav-tab').forEach(tab => {
    tab.addEventListener('click', () => {
        // Remove active from all tabs and contents
        document.querySelectorAll('.nav-tab').forEach(t => t.classList.remove('active'));
        document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));

        // Activate clicked tab
        tab.classList.add('active');
        const tabId = tab.getAttribute('data-tab');
        document.getElementById('tab-' + tabId).classList.add('active');

        // Load data for the tab
        switch (tabId) {
            case 'dashboard': loadDashboard(); break;
            case 'vehicles': loadVehicles(); break;
            case 'drivers': loadDrivers(); break;
            case 'trips': loadTrips(); break;
            case 'maintenance': loadMaintenance(); break;
            case 'logs': loadLogs(); break;
        }
    });
});

// ── Toast Notification ──
function showToast(message, type = 'success') {
    const container = document.getElementById('toast-container');
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.innerHTML = `
        <span class="toast-icon">${type === 'success' ? '✅' : '❌'}</span>
        <span>${message}</span>
    `;
    container.appendChild(toast);

    // Auto-remove after 3 seconds
    setTimeout(() => {
        toast.style.animation = 'slideOut 0.3s ease forwards';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

// ── Toggle Form Visibility ──
function toggleForm(formId) {
    const form = document.getElementById(formId);
    form.classList.toggle('hidden');
    if (!form.classList.contains('hidden')) {
        loadFleetDropdowns(); // Refresh fleet dropdowns when opening forms
    }
}

// ── API Helper ──
async function apiCall(endpoint, method = 'GET', body = null) {
    const options = {
        method,
        headers: { 'Content-Type': 'application/json' },
    };
    if (body) options.body = JSON.stringify(body);

    const response = await fetch(`${API}${endpoint}`, options);
    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || `HTTP ${response.status}`);
    }

    const text = await response.text();
    return text ? JSON.parse(text) : null;
}

// ── Status Badge Helper ──
function statusBadge(status) {
    if (!status) return '<span class="badge">—</span>';
    const cls = status.toLowerCase()
        .replace(/\s+/g, '-')
        .replace('under-maintenance', 'maintenance');
    return `<span class="badge badge-${cls}">${status}</span>`;
}

// ── Check API Connection ──
async function checkConnection() {
    const statusEl = document.getElementById('apiStatus');
    const dot = statusEl.querySelector('.status-dot');
    const label = statusEl.querySelector('span:last-child');
    try {
        await apiCall('/dashboard/stats');
        dot.className = 'status-dot connected';
        label.textContent = 'API Connected';
    } catch (e) {
        dot.className = 'status-dot error';
        label.textContent = 'API Offline';
    }
}

// ═══════════════════════════════════════════
//  DASHBOARD
// ═══════════════════════════════════════════
async function loadDashboard() {
    try {
        const stats = await apiCall('/dashboard/stats');
        document.getElementById('stat-vehicles').textContent = stats.totalVehicles || 0;
        document.getElementById('stat-drivers').textContent = stats.totalDrivers || 0;
        document.getElementById('stat-trips').textContent = stats.totalTrips || 0;
        document.getElementById('stat-alerts').textContent = stats.maintenanceAlerts || 0;
        document.getElementById('stat-available-vehicles').textContent = stats.availableVehicles || 0;
        document.getElementById('stat-in-service').textContent = stats.inServiceVehicles || 0;
        document.getElementById('stat-available-drivers').textContent = stats.availableDrivers || 0;

        // Load recent logs
        const logs = await apiCall('/logs');
        const tbody = document.getElementById('dashboard-logs');
        if (logs.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4" class="empty-state">No activity logs yet</td></tr>';
            return;
        }
        tbody.innerHTML = logs.slice(0, 10).map(log => `
            <tr>
                <td>${formatDate(log.actionDate)}</td>
                <td><span class="badge badge-${log.actionType === 'INSERT' ? 'available' : log.actionType === 'DELETE' ? 'on-leave' : 'in-service'}">${log.actionType}</span></td>
                <td>${log.tableName}</td>
                <td>${log.description || '—'}</td>
            </tr>
        `).join('');
    } catch (e) {
        showToast('Failed to load dashboard: ' + e.message, 'error');
    }
}

// ═══════════════════════════════════════════
//  VEHICLES
// ═══════════════════════════════════════════
async function loadVehicles() {
    try {
        const vehicles = await apiCall('/vehicles');
        const tbody = document.getElementById('vehicles-table');
        if (vehicles.length === 0) {
            tbody.innerHTML = '<tr><td colspan="10" class="empty-state">No vehicles found</td></tr>';
            return;
        }
        tbody.innerHTML = vehicles.map(v => `
            <tr>
                <td>${v.vehicleId}</td>
                <td>${v.fleetId}</td>
                <td><strong>${v.registrationNumber}</strong></td>
                <td>${v.make} ${v.model}</td>
                <td>${v.year}</td>
                <td>${v.fuelType}</td>
                <td>${v.vehicleType}</td>
                <td>${v.seatingCapacity}</td>
                <td>${statusBadge(v.status)}</td>
                <td>
                    <div class="action-btns">
                        <select class="btn btn-sm" onchange="changeVehicleStatus(${v.vehicleId}, this.value); this.selectedIndex=0;">
                            <option value="">Status ▾</option>
                            <option value="Available">Available</option>
                            <option value="In Service">In Service</option>
                            <option value="Under Maintenance">Under Maintenance</option>
                        </select>
                        <button class="btn btn-sm btn-danger" onclick="deleteVehicle(${v.vehicleId})">✕</button>
                    </div>
                </td>
            </tr>
        `).join('');
    } catch (e) {
        showToast('Failed to load vehicles: ' + e.message, 'error');
    }
}

async function addVehicle(event) {
    event.preventDefault();
    try {
        const vehicle = {
            fleetId: parseInt(document.getElementById('v-fleet').value),
            registrationNumber: document.getElementById('v-reg').value,
            make: document.getElementById('v-make').value,
            model: document.getElementById('v-model').value,
            year: parseInt(document.getElementById('v-year').value),
            fuelType: document.getElementById('v-fuel').value,
            vehicleType: document.getElementById('v-type').value,
            seatingCapacity: parseInt(document.getElementById('v-seats').value) || 5
        };
        await apiCall('/vehicles', 'POST', vehicle);
        showToast('Vehicle registered successfully!');
        toggleForm('vehicle-form');
        event.target.reset();
        loadVehicles();
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

async function changeVehicleStatus(id, newStatus) {
    if (!newStatus) return;
    try {
        await apiCall(`/vehicles/${id}/status`, 'PUT', { status: newStatus });
        showToast(`Vehicle ${id} status → ${newStatus}`);
        loadVehicles();
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

async function deleteVehicle(id) {
    if (!confirm('Are you sure you want to delete Vehicle ' + id + '?')) return;
    try {
        await apiCall(`/vehicles/${id}`, 'DELETE');
        showToast('Vehicle deleted');
        loadVehicles();
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

function toggleSeating() {
    const type = document.getElementById('v-type').value;
    const seats = document.getElementById('v-seats');
    seats.value = type === 'Bus' ? 40 : 5;
}

// ═══════════════════════════════════════════
//  DRIVERS
// ═══════════════════════════════════════════
async function loadDrivers() {
    try {
        const drivers = await apiCall('/drivers');
        const tbody = document.getElementById('drivers-table');
        if (drivers.length === 0) {
            tbody.innerHTML = '<tr><td colspan="8" class="empty-state">No drivers found</td></tr>';
            return;
        }
        tbody.innerHTML = drivers.map(d => `
            <tr>
                <td>${d.driverId}</td>
                <td>${d.fleetId}</td>
                <td><strong>${d.name}</strong></td>
                <td>${d.licenseNumber}</td>
                <td>${d.phone}</td>
                <td>${d.hireDate}</td>
                <td>${statusBadge(d.status)}</td>
                <td>
                    <div class="action-btns">
                        <select class="btn btn-sm" onchange="changeDriverStatus(${d.driverId}, this.value); this.selectedIndex=0;">
                            <option value="">Status ▾</option>
                            <option value="Available">Available</option>
                            <option value="On Trip">On Trip</option>
                            <option value="On Leave">On Leave</option>
                        </select>
                        <button class="btn btn-sm btn-danger" onclick="deleteDriver(${d.driverId})">✕</button>
                    </div>
                </td>
            </tr>
        `).join('');
    } catch (e) {
        showToast('Failed to load drivers: ' + e.message, 'error');
    }
}

async function addDriver(event) {
    event.preventDefault();
    try {
        const driver = {
            fleetId: parseInt(document.getElementById('d-fleet').value),
            name: document.getElementById('d-name').value,
            licenseNumber: document.getElementById('d-license').value,
            phone: document.getElementById('d-phone').value,
            hireDate: document.getElementById('d-hire').value,
            status: 'Available'
        };
        await apiCall('/drivers', 'POST', driver);
        showToast('Driver hired successfully!');
        toggleForm('driver-form');
        event.target.reset();
        loadDrivers();
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

async function changeDriverStatus(id, newStatus) {
    if (!newStatus) return;
    try {
        await apiCall(`/drivers/${id}`, 'PUT', { status: newStatus });
        showToast(`Driver ${id} status → ${newStatus}`);
        loadDrivers();
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

async function deleteDriver(id) {
    if (!confirm('Are you sure you want to delete Driver ' + id + '?')) return;
    try {
        await apiCall(`/drivers/${id}`, 'DELETE');
        showToast('Driver deleted');
        loadDrivers();
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

// ═══════════════════════════════════════════
//  TRIPS
// ═══════════════════════════════════════════
async function loadTrips() {
    try {
        const trips = await apiCall('/trips');
        const tbody = document.getElementById('trips-table');
        if (trips.length === 0) {
            tbody.innerHTML = '<tr><td colspan="9" class="empty-state">No trips found</td></tr>';
            return;
        }
        tbody.innerHTML = trips.map(t => `
            <tr>
                <td>${t.tripId}</td>
                <td>${t.vehicleId}</td>
                <td>${t.driverId}</td>
                <td>${t.departureDate}</td>
                <td>${t.arrivalDate || '—'}</td>
                <td>${t.distance} km</td>
                <td>₹${t.cost.toFixed(2)}</td>
                <td>${statusBadge(t.status)}</td>
                <td>
                    ${t.status !== 'Completed' ?
                        `<button class="btn btn-sm btn-success" onclick="completeTrip(${t.tripId})">✓ Complete</button>`
                        : '—'}
                </td>
            </tr>
        `).join('');

        // Also load available vehicles and drivers for the form
        loadTripDropdowns();
    } catch (e) {
        showToast('Failed to load trips: ' + e.message, 'error');
    }
}

async function createTrip(event) {
    event.preventDefault();
    try {
        const trip = {
            vehicleId: parseInt(document.getElementById('t-vehicle').value),
            driverId: parseInt(document.getElementById('t-driver').value),
            departureDate: document.getElementById('t-departure').value,
            distance: parseFloat(document.getElementById('t-distance').value)
        };
        const result = await apiCall('/trips', 'POST', trip);
        showToast(`Trip created! Cost: ₹${result.cost.toFixed(2)}`);
        toggleForm('trip-form');
        event.target.reset();
        document.getElementById('cost-preview').innerHTML = 'Estimated Cost: <strong>₹0.00</strong>';
        loadTrips();
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

async function completeTrip(id) {
    try {
        await apiCall(`/trips/${id}/complete`, 'PUT');
        showToast('Trip completed! Vehicle and driver are now available.');
        loadTrips();
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

function updateCostPreview() {
    const distance = parseFloat(document.getElementById('t-distance').value) || 0;
    const cost = distance * 12.0;
    document.getElementById('cost-preview').innerHTML =
        `Estimated Cost: <strong>₹${cost.toFixed(2)}</strong>`;
}

async function loadTripDropdowns() {
    try {
        const vehicles = await apiCall('/vehicles');
        const drivers = await apiCall('/drivers');

        const vSelect = document.getElementById('t-vehicle');
        vSelect.innerHTML = vehicles
            .filter(v => v.status === 'Available')
            .map(v => `<option value="${v.vehicleId}">${v.registrationNumber} (${v.make} ${v.model})</option>`)
            .join('');
        if (vSelect.innerHTML === '') vSelect.innerHTML = '<option value="">No available vehicles</option>';

        const dSelect = document.getElementById('t-driver');
        dSelect.innerHTML = drivers
            .filter(d => d.status === 'Available')
            .map(d => `<option value="${d.driverId}">${d.name} (${d.licenseNumber})</option>`)
            .join('');
        if (dSelect.innerHTML === '') dSelect.innerHTML = '<option value="">No available drivers</option>';
    } catch (e) {
        // Silently fail — dropdowns will be empty
    }
}

// ═══════════════════════════════════════════
//  MAINTENANCE
// ═══════════════════════════════════════════
async function loadMaintenance() {
    try {
        // Load alerts
        const alerts = await apiCall('/maintenance/alerts');
        const alertsDiv = document.getElementById('maintenance-alerts');
        if (alerts.length === 0) {
            alertsDiv.innerHTML = '<p class="empty-state">No active maintenance alerts ✓</p>';
        } else {
            alertsDiv.innerHTML = alerts.map(a => `
                <div class="alert-item">
                    <div class="alert-info">
                        <span class="alert-type">🔧 ${a.maintenanceType}</span>
                        <span class="alert-meta">Vehicle ID: ${a.vehicleId} | Date: ${a.maintenanceDate} | ₹${a.cost.toFixed(2)}</span>
                    </div>
                    <button class="btn btn-sm btn-success" onclick="completeMaintenance(${a.maintenanceId})">✓ Done</button>
                </div>
            `).join('');
        }

        // Load all records
        const records = await apiCall('/maintenance');
        const tbody = document.getElementById('maintenance-table');
        if (records.length === 0) {
            tbody.innerHTML = '<tr><td colspan="7" class="empty-state">No maintenance records</td></tr>';
            return;
        }
        tbody.innerHTML = records.map(m => `
            <tr>
                <td>${m.maintenanceId}</td>
                <td>${m.vehicleId}</td>
                <td>${m.maintenanceType}</td>
                <td>${m.maintenanceDate}</td>
                <td>₹${m.cost.toFixed(2)}</td>
                <td>${statusBadge(m.status)}</td>
                <td>
                    ${m.status !== 'Completed' ?
                        `<button class="btn btn-sm btn-success" onclick="completeMaintenance(${m.maintenanceId})">✓ Complete</button>`
                        : '—'}
                </td>
            </tr>
        `).join('');

        // Load vehicle dropdown for form
        loadMaintenanceDropdowns();
    } catch (e) {
        showToast('Failed to load maintenance: ' + e.message, 'error');
    }
}

async function scheduleMaintenance(event) {
    event.preventDefault();
    try {
        const maintenance = {
            vehicleId: parseInt(document.getElementById('m-vehicle').value),
            maintenanceType: document.getElementById('m-type').value,
            maintenanceDate: document.getElementById('m-date').value,
            cost: parseFloat(document.getElementById('m-cost').value)
        };
        await apiCall('/maintenance', 'POST', maintenance);
        showToast('Maintenance scheduled!');
        toggleForm('maint-form');
        event.target.reset();
        loadMaintenance();
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

async function completeMaintenance(id) {
    try {
        await apiCall(`/maintenance/${id}/complete`, 'PUT');
        showToast('Maintenance completed! Vehicle is now available.');
        loadMaintenance();
    } catch (e) {
        showToast('Error: ' + e.message, 'error');
    }
}

async function loadMaintenanceDropdowns() {
    try {
        const vehicles = await apiCall('/vehicles');
        const select = document.getElementById('m-vehicle');
        select.innerHTML = vehicles
            .map(v => `<option value="${v.vehicleId}">${v.registrationNumber} (${v.make} ${v.model})</option>`)
            .join('');
    } catch (e) {
        // Silently fail
    }
}

// ═══════════════════════════════════════════
//  LOGS
// ═══════════════════════════════════════════
async function loadLogs() {
    try {
        const logs = await apiCall('/logs');
        const tbody = document.getElementById('logs-table');
        if (logs.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" class="empty-state">No logs yet</td></tr>';
            return;
        }
        tbody.innerHTML = logs.map(log => `
            <tr>
                <td>${log.logId}</td>
                <td>${formatDate(log.actionDate)}</td>
                <td><span class="badge badge-${log.actionType === 'INSERT' ? 'available' : log.actionType === 'DELETE' ? 'on-leave' : 'in-service'}">${log.actionType}</span></td>
                <td>${log.tableName}</td>
                <td>${log.recordId || '—'}</td>
                <td>${log.description || '—'}</td>
            </tr>
        `).join('');
    } catch (e) {
        showToast('Failed to load logs: ' + e.message, 'error');
    }
}

// ═══════════════════════════════════════════
//  FLEET DROPDOWNS (shared by multiple forms)
// ═══════════════════════════════════════════
async function loadFleetDropdowns() {
    try {
        const fleets = await apiCall('/fleets');
        const options = fleets.map(f =>
            `<option value="${f.fleetId}">${f.fleetName} (${f.companyName})</option>`
        ).join('');

        // Update all fleet dropdowns
        ['v-fleet', 'd-fleet'].forEach(id => {
            const el = document.getElementById(id);
            if (el) el.innerHTML = options;
        });
    } catch (e) {
        // Silently fail
    }
}

// ── Date formatter ──
function formatDate(dateStr) {
    if (!dateStr) return '—';
    const d = new Date(dateStr);
    return d.toLocaleString('en-IN', {
        day: '2-digit', month: 'short', year: 'numeric',
        hour: '2-digit', minute: '2-digit'
    });
}

// ═══════════════════════════════════════════
//  INIT — Load dashboard on page load
// ═══════════════════════════════════════════
document.addEventListener('DOMContentLoaded', () => {
    checkConnection();
    loadDashboard();
});

// ═══════════════════════════════════════════
//  EXPORT TO WINDOW (Fix for type="module")
// ═══════════════════════════════════════════
window.loadDashboard = loadDashboard;
window.loadVehicles = loadVehicles;
window.addVehicle = addVehicle;
window.changeVehicleStatus = changeVehicleStatus;
window.deleteVehicle = deleteVehicle;
window.toggleSeating = toggleSeating;
window.loadDrivers = loadDrivers;
window.addDriver = addDriver;
window.changeDriverStatus = changeDriverStatus;
window.deleteDriver = deleteDriver;
window.loadTrips = loadTrips;
window.createTrip = createTrip;
window.completeTrip = completeTrip;
window.loadMaintenance = loadMaintenance;
window.scheduleMaintenance = scheduleMaintenance;
window.completeMaintenance = completeMaintenance;
window.loadLogs = loadLogs;
window.toggleForm = toggleForm;
window.updateCostPreview = updateCostPreview;
