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

    fun addPropertyToDatabase(property: Property, context: Context) {
        executeDatabaseOperation(
            context = context,
            operation = { propertyRepository.addPropertyToDatabase(property) },
            successMessage = "Successfully uploaded property!",
            errorMessage = "Error while uploading property"
        )
    }

    fun updatePropertyDetails(updatedProperty: Property, context: Context) {
        executeDatabaseOperation(
            context = context,
            operation = { propertyRepository.updatePropertyDetails(updatedProperty) },
            successMessage = "Property updated!",
            errorMessage = "Error updating property"
        )
    }

    fun uploadPropertyImages(property: Property, context: Context) {
        executeDatabaseOperation(
            context = context,
            operation = { propertyRepository.uploadPropertyPictures(property, context) },
            successMessage = "Property images uploaded!",
            errorMessage = "Error uploading property images"
        )
    }

    private fun executeDatabaseOperation(
        context: Context,
        operation: suspend () -> Boolean,
        successMessage: String,
        errorMessage: String
    ) {
        viewModelScope.launch {
            val success = operation()
            val message = if (success) successMessage else errorMessage
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}
