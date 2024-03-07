package com.nariman.library;

/**
 *
 * @author Nariman
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import java.io.InputStream;

public class FirebaseConfig {

   public void initFirebase() {

      InputStream serviceAccount = null;
      try {
         serviceAccount = FirebaseConfig.class.getResourceAsStream("/credentials.json");
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      FirebaseOptions options = null;
      try {
         options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setStorageBucket("library-c6bfc.appspot.com")
            .build();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      FirebaseApp.initializeApp(options);
      Bucket bucket = StorageClient.getInstance().bucket();

   }

}