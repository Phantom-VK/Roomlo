package com.app.roomlo.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.roomlo.dataclasses.Property
import com.app.roomlo.repository.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository

) : ViewModel() {

    fun addPropertyToDatabase(property: Property, context: Context, uid: String) {
        viewModelScope.launch {
            val success =propertyRepository.addPropertyToDatabase(property)
            if (success) {
                Toast.makeText(context, "Successfully uploaded property !", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Error while uploading property", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun updatePropertyDetails(updatedUser: Property, context: Context) {
        viewModelScope.launch {
            val success = propertyRepository.updatePropertyDetails(updatedUser)
            if (success) {
                Toast.makeText(context, "Property updated!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Error updating property", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun uploadPropertyImages(property: Property,
                             context: Context){

            viewModelScope.launch {
                val success = propertyRepository.uploadPropertyPictures(property, context)
                if (success) {
                    Toast.makeText(context, "Property updated!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Error updating property", Toast.LENGTH_LONG).show()
                }
            }


    }




}