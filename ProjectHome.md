iHungry will allow users to search restaurants, delis and cafes around him and order food on their android devices. It is a much faster and more convenient way to order food directly from your phone in case you do not have the menu or phone number for your desired restaurants - we have them! We will take advantage of several features like multi-touch, GPS and Google Maps when the application is running on Android. We will build a PC-side restaurant application that receives order from customers, sends confirmation to them and a server, which stores information of restaurants information and order details. The protocol among these three parties is the most important.

To run server, one must first launch the MongoDB in default mode, then run the server. The JUnitTest of the MessageReactor and DatabaseReactor also need MongoDB's running. To change the IP address, change the corresponding static string in CommunicationProtocol.

To run the Android app, make sure you get the Android SDK and include the Android project library (http://ugrad.cs.jhu.edu/~group14/Resources/iteration3_resources/android-mapviewballoons.zip).
Then right click on the Android project => Android Tools => Fix Project Properties.
At last, clean and rebuild the project.

To run the Restaurant app, run as Java application.