# LocF
### Fetch location without GPS
LocF is a library using which you can get user's location without enabling GPS.
### Requirements
Internet must be working on the device in order to get the location
### Steps
  - Download this project and add LocF as module in you app.
  - Get Google Api key. Steps to get Api key:
    * Go to the https://goo.gl/qWJHnU
    * Create or select a project.
    * Click Continue to Enable the API.
    * On the Credentials page, get a Server key (and set the API Credentials).
    * Note: If you have an existing Server key, you may use that key.
 - Once you get Api key, say AIZA123456QWERTY098765-qw, in your activity where you want to use the location, call
 ```sh
 LocationFetchSettings.setApiKey("AIZA123456QWERTY098765-qw");
 ```
 - Thenafter, use this code to fetch loaction:
 ```sh
 LocationFetchAction.getLocation(this, new LocationFetchCallback() {
            @Override
            public void onSuccess(final NGLocation ngLocation) {
                ngLocation.getAccuracy();
                ngLocation.getLattitude();
                ngLocation.getLongitude();
            }

            @Override
            public void onFailure(int errorCode) {
            }

            @Override
            public void onException(Exception ex) {
            }
        });

 ```
