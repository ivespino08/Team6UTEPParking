package com.example.team6utepparking.ui


import androidx.lifecycle.ViewModel
import com.example.team6utepparking.domain.model.Car
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CarListViewModel: ViewModel() {

    private var _carList = MutableStateFlow<List<Car>>(emptyList())
    var carList = _carList.asStateFlow()

    fun getCars(uid: String?) {
        val db = Firebase.firestore

        if (uid != null) {
            db.collection("users")
                .document(uid)
                .collection("vehicles")
                .addSnapshotListener {value, error ->
                    if (error != null) {
                        return@addSnapshotListener
                    }

                    if (value != null) {
                        _carList.value =  value.toObjects()
                    }
                }
        }
    }

}