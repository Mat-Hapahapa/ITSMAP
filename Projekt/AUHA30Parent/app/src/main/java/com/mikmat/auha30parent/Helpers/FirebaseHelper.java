package com.mikmat.auha30parent.Helpers;

import android.app.Activity;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.Context;
import com.firebase.client.snapshot.IndexedNode;
import com.firebase.client.snapshot.Node;
import com.mikmat.auha30parent.Models.Baby;

import java.util.ArrayList;

/**
 * Created by matry on 13-05-2016.
 */
public class FirebaseHelper {


    private static Firebase rootRef;
    private boolean completedTask = false;
    private ArrayList<Baby> babyList = new ArrayList<>();

    public FirebaseHelper(android.content.Context context) {
        Firebase.setAndroidContext(context);
        rootRef = new Firebase("https://auha30.firebaseio.com/web/data");
    }

    public boolean CreateBaby(final Baby newBaby) {
        rootRef.child("/Babies").push().setValue(newBaby, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    completedTask = false;
                } else {
                    newBaby.setFirebaseRef(firebase.getRef().toString());
                    if (EditBaby(newBaby)) {
                        completedTask = true;
                    } else {
                        completedTask = false;
                    }
                }
            }
        });
        return completedTask;
    }

    public boolean DeleteBaby(Baby babyToDelete) {
        Firebase babyRef = new Firebase(babyToDelete.getFirebaseRef());
        babyRef.removeValue(new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    completedTask = false;

                } else {
                    completedTask = true;
                }
            }
        });
        return completedTask;
    }

    public boolean EditBaby(Baby babyToEdit) {
        Firebase babyRef = new Firebase(babyToEdit.getFirebaseRef());
        babyRef.setValue(babyToEdit, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    completedTask = false;

                } else {
                    completedTask = true;
                }
            }
        });
        return completedTask;
    }

    /*
     * Note: Data is probably not ready before return statement
     */
    public ArrayList<Baby> getBabyList() {
        rootRef.child("/Babies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue()); //print whole snapshot

                babyList.clear();
                for (DataSnapshot UserSnapshot : snapshot.getChildren()) {
                    Baby baby = UserSnapshot.getValue(Baby.class);
                    babyList.add(baby);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        return babyList;
    }

    public Baby getBaby(String firebaseRef) {


        return new Baby();
    }

    /*
     *NOTE: The function is returning NULL before it gets a respons for from the listener. Don't use
    public Baby getBabyByID(final int ID) {


           babyFromID = null;
           rootRef.child("/Babies").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot snapshot) {
                   for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                       Baby baby = postSnapshot.getValue(Baby.class);
                       if (baby.getID() == ID) {
                           babyFromID = baby;
                       }
                   }
               }

               @Override
               public void onCancelled(FirebaseError firebaseError) {
               }
           });
               return babyFromID;
       }*/

}
