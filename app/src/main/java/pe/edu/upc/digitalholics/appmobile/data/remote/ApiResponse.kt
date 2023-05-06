package pe.edu.upc.digitalholics.appmobile.data.remote

import com.google.gson.annotations.SerializedName
import pe.edu.upc.digitalholics.appmobile.data.model.Patient
import pe.edu.upc.digitalholics.appmobile.data.model.Treatment

data class ApiResponse(
    @SerializedName("content")
    val patients : ArrayList<Patient>,
    val treatments : ArrayList<Treatment>
)
