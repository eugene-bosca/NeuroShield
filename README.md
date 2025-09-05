# NeuroShield Mobile App  

The **NeuroShield Mobile App** is a Kotlin-based application designed to control diagnostic tests on a connected medical device and manage electronic medical records (EMR). It enables clinicians to seamlessly capture, store, and review patient test data while providing real-time feedback during assessments.  

---

## 🚀 Features  

- **Patient Management**  
  - Enter and organize patient data with an automatically generated 128-bit UUID per patient.  
  - Securely store results for later review.  

- **Device Integration**  
  - Real-time connection check with the physical device via infrared camera feed.  
  - Head alignment confirmation ensures accurate test conditions.  

- **Diagnostic Tests**  
  - Run **Smooth Pursuit** and **Pupillary Light Reflex (PLR)** tests directly from the app.  
  - Prevent duplicate testing with automatic test completion markers.  
  - Visual warnings and success indicators:  
    - ⚠️ Yellow warning if readings are abnormal (patient requires further assessment).  
    - ✅ Green check if readings are normal (safe to proceed if no other symptoms are present).  

- **Results & Review**  
  - All test data stored securely and accessible from the results page.  
  - Remote access to data even when the physical device is offline.  

---

## 🖥️ System Architecture  

The app follows **MVVM (Model-View-ViewModel)** architecture for a clean separation between UI and data layers.  

### Mobile Layer  
- Built in **Kotlin** with **Jetpack Compose** for a modern, responsive UI.  
- Interfaces with both the physical device (Raspberry Pi) and cloud backend.  

### Backend Architecture  
**1. Cloud Layer (Google Cloud Platform)**  
- **Django app** on App Engine providing REST APIs.  
- **MySQL database** hosted on Cloud SQL.  
- Enables remote result storage and retrieval, independent of the physical device.  

**2. Device Layer (NeuroShield Box - Raspberry Pi)**  
- **Flask server** for local device communication.  
- **Ngrok API gateway** exposes a public URL for secure connections.  
- Auto-deploys Ngrok + Flask server at startup for a plug-and-play user experience.  

---

## 📱 App Workflow  

1. **Enter Patient Info** → Patient assigned unique UUID.  
2. **Eye Alignment Screen** → Confirm device connection + correct head positioning.  
3. **Run Tests** → Smooth Pursuit & PLR (tests greyed out after completion).  
4. **View Results** → Green ✅ (within range) or Yellow ⚠️ (outside range).  
5. **Save & Review** → Results stored and accessible via home screen.  

---

## 🛠️ Tech Stack  

- **Mobile**: Kotlin, Jetpack Compose, MVVM  
- **Backend (Cloud)**: Django, MySQL, Google Cloud App Engine, Cloud SQL  
- **Backend (Device)**: Flask, Raspberry Pi, Ngrok  
- **Infra**: Google Cloud Platform  

---

## 📸 Figures  

<p align="center">
  <img src="https://github.com/user-attachments/assets/a5ad0a18-f5d7-45fd-bc07-50b1e41c91ce" 
       alt="neuroshield_ui_workflow" 
       width="600"/>
</p>  
<p align="center"><em>Figure 1: App UI workflow and architecture</em></p>  

<p align="center" style="background-color:white; padding:10px;">
  <img src="https://github.com/user-attachments/assets/bfc0622a-96e6-4c9e-bb67-5a5f7e0b9bb4" 
       alt="neuroshield_workflow" 
       width="600"/>
</p>  
<p align="center"><em>Figure 2: Backend layers (Cloud + Device)</em></p>  

---

## 🔒 Security  

- Patient data is tied to unique UUIDs for secure identification.  
- Results are transmitted over secure tunnels (Ngrok) and stored in a managed SQL database.  
