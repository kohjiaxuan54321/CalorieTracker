# Food Calorie Detector

A simple FastAPI web application that lets you upload a photo of food, detects the food item using a Hugging Face model, and retrieves nutrition facts via the FatSecret API.

### Features
  •  Image upload and preview
  •  Food detection using skylord/swin-fineted-food101
  •  Nutrition lookup via FatSecret API (OAuth1)
  •  User feedback for model retraining
  •  Responsive UI with Jinja2 templates and CSS

### Backend File Structure
```bash
├── app.py              # Main FastAPI application
├── requirements.txt    # Python dependencies
├── static/             # Static assets
│   └── styles.css      # CSS for the web UI
└── templates/          # Jinja2 HTML templates
    └── index.html      # Home page template
```

### Setup
  1.  Clone the repo:
``` bash
git clone <repo-url>
cd <repo-folder>/CalorieTrackerVM
```

  2.  Install dependencies:
```bash
pip install -r requirements.txt
```

  3.  Arrange files:
  •  Move styles.css into static/
  •  Move index.html into templates/

### Running
  1.  Update the host to your machine’s local IP so others on your network can access the app. For example, if your IP is 192.168.1.100, either:
  •  Launch Uvicorn with a host flag:

uvicorn app:app --reload --host 192.168.1.100

  •  Or modify the Uvicorn call in app.py:

if name == "main":
    import uvicorn
    uvicorn.run("app:app", host="192.168.1.100", port=8000, reload=True)

  2.  Start the server:

uvicorn app:app --reload

  3.  Open http://<YOUR_LOCAL_IP>:8000 in your browser.

# XCode Configuration

In ViewController.swift, update the URL string to point at your Mac’s local IP address where the FastAPI server is running. For example:
```bash
// ViewController.swift (excerpt)
if let url = URL(string: "http://192.168.1.100:8000") {
    let request = URLRequest(url: url)
    webView.load(request)
}
```
Replace 192.168.1.100 with your actual IP.

### Usage

Build and run the app on a device or simulator that is connected to the same local network as your server.

The WKWebView will load the Food Calorie Detector web UI.

Upload an image, and interact with the nutrition detector as in the web version.

# Android Configuration

In MainActivity.kt, update the URL passed to WebViewScreen to point at your Mac’s local IP address where the FastAPI server is running. For example:
```bash
setContent {
    NutritionViewerTheme {
        WebViewScreen("http://192.168.1.100:8000/")
    }
}
```
Replace 192.168.1.100 with your actual local IP.

### Usage

Connect your Android device or start an emulator on the same LAN as your server.

Build and run the app from Android Studio.

The embedded WebView will load the Food Calorie Detector web UI.

Use the file chooser to upload images from camera or gallery and view nutrition information.
