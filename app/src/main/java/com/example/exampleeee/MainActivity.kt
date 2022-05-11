package com.example.exampleeee

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.exampleeee.ui.theme.ExampleeeeTheme
import com.google.firebase.Timestamp
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleeeeTheme {
                FirestoreScreen()
            }
        }
    }

}

@Composable
fun FirestoreScreen(){
    val db = Firebase.firestore
    val context = LocalContext.current
    val currentUserId = "456852357951" /** in fact instead auth.currentUser!!.uid */
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = {
                val date = Timestamp.now()
                val email = "example@example.com" /** in fact instead auth.currentUser!!.email */
                val postHashMap = hashMapOf<String, Any>()
                postHashMap["date"] = date
                postHashMap["mail"] = email
                db.collection("example").document(currentUserId).set(postHashMap).addOnCompleteListener { task->
                    if (task.isSuccessful && task.isComplete){
                        Toast.makeText(context, "Button1 Completed", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener { ex->
                    Toast.makeText(context, ex.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        ){
            Text(text="write firestore")
        }
        Button(
            onClick = {
                val postHashMapSecond = hashMapOf<String, Any>()
                postHashMapSecond["capital"] = true
                db.collection("example").document(currentUserId).set(postHashMapSecond, SetOptions.merge()).addOnCompleteListener { task->
                    if (task.isSuccessful && task.isComplete){
                        Toast.makeText(context, "Button2 completed", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener { ex->
                    Toast.makeText(context, ex.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        ){
            Text(text="write exist document")
        }
        Button(
            onClick = {
                db.collection("example").document(currentUserId).addSnapshotListener { value, error ->
                    if (error != null){
                        Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG).show()
                    }else{
                        if (value != null){
                            if (value.exists()){
                                val date = value.get("mail") as String
                                Toast.makeText(context, date, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        ){
            Text(text="read document")
        }
        Button(
            onClick = {
                db.collection("example").document(currentUserId).delete().addOnCompleteListener { task->
                    if(task.isSuccessful){
                        Toast.makeText(context, "deleted", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener { ex->
                    Toast.makeText(context, ex.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        ){
            Text(text="delete document")
        }
    }

}
