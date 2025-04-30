package com.example.team6utepparking.ui

import androidx.lifecycle.ViewModel
import com.example.team6utepparking.domain.model.ParkingLot
import com.example.team6utepparking.domain.model.ParkingPermit
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ParkingViewModel:ViewModel() {

    private val db = Firebase.firestore
    private val _userId = Firebase.auth.currentUser?.uid
    private var _parkingPermit = MutableStateFlow<ParkingPermit?>(null)
    private var _parkingLot = MutableStateFlow<ParkingLot?>(null)
    var parkinglot = _parkingLot.asStateFlow()
    var parkingPermit = _parkingPermit.asStateFlow()



    init {
        getPermit()
    }

    fun getLot(permitType: String?){
        //val db = Firebase.firestore

        if (permitType != null) {
            db.collection("Parking Lots")
                .document(permitType)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    _parkingLot.value = documentSnapshot.toObject()
                }
        }

    }

    private fun getPermit() {
        //val db = Firebase.firestore

        if (_userId != null) {
            db.collection("permits")
                .document(_userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    _parkingPermit.value = documentSnapshot.toObject()
                }
        }
    }

    fun park(permitType: String?) {
        //val db = Firebase.firestore

        if (_userId != null) {
            db.collection("users")
                .document(_userId)
                .update("parked", true)
        }

        if (permitType != null) {
            db.collection("Parking Lots")
                .document(permitType)
                .update("occupied", FieldValue.increment(1))
        }

    }

    fun unpark(permitType: String?) {
        //val db = Firebase.firestore

        if (_userId != null) {
            db.collection("users")
                .document(_userId)
                .update("parked", false)
        }

        if (permitType != null) {
            db.collection("Parking Lots")
                .document(permitType)
                .update("occupied", FieldValue.increment(-1))
        }
    }
}