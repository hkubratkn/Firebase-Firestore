# Firebase-Firestore

<p align="center">
  <img src="images/sc1.jpg" width="300" />
  <img src="images/sc2.jpg" width="300" />
  <img src="images/sc3.jpg" width="300" />
  <img src="images/sc4.jpg" width="300" />
  <img src="images/sc5.jpg" width="300" />
<p/>

## security rules
match {
  allow read, write : if request.auth != null;
}

match {
  allow read, write;
}
