package pe.edu.upc.digitalholics.appmobile.ui.srceens.Navigation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.edu.upc.digitalholics.appmobile.data.model.Patient
import pe.edu.upc.digitalholics.appmobile.data.model.Physiotherapist
import pe.edu.upc.digitalholics.appmobile.data.model.Treatment
import pe.edu.upc.digitalholics.appmobile.data.remote.ApiClient
import pe.edu.upc.digitalholics.appmobile.data.remote.ApiResponse
import pe.edu.upc.digitalholics.appmobile.data.remote.TreatmentResponse

import pe.edu.upc.digitalholics.appmobile.ui.srceens.PatientList.PatientList
//import pe.edu.upc.digitalholics.appmobile.ui.srceens.PatientsDetails.Patient
import pe.edu.upc.digitalholics.appmobile.ui.srceens.PatientsDetails.PatientDetails
import pe.edu.upc.digitalholics.appmobile.ui.srceens.TreatmentsDetails.TreatmentDetails
import pe.edu.upc.digitalholics.appmobile.ui.srceens.TreatmentsDetails.Treatments
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "TreatmentDetailTest") {
        composable("PatientList") {
            val patients = remember {
                //mutableStateOf(Patient("1","Jose","Del Carpio","20","30","jose@gmail.com","2","https://img.europapress.es/fotoweb/fotonoticia_20081004164743_420.jpg"))
                mutableStateOf(emptyList<Patient>())
            }

            //PatientDetails(patient = patients.value)

            val patientInterface = ApiClient.buildPatientInterface()
            val getAllPatients = patientInterface.getAllPatients()

            getAllPatients.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    if (response.isSuccessful) {
                        patients.value = response.body()?.patients!!

                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {

                }
            })
            PatientList(
                patients = patients.value,
                selectPatient = { index ->
                    navController.navigate("patient/$index")
               }
            )
        }
        composable("TreatmentDetailTest"){


            val treatments = remember {
                mutableStateOf(emptyList<Treatment>())
            }
            val treatmentInterface = ApiClient.buildTreatmentInterface()
            val getAllTreatments = treatmentInterface.getAllTreatments()

            getAllTreatments.enqueue(object : Callback<TreatmentResponse> {
                override fun onResponse(
                    call: Call<TreatmentResponse>,
                    response: Response<TreatmentResponse>
                ) {
                    if (response.isSuccessful) {
                        treatments.value = response.body()?.treatments!!

                    }
                }

                override fun onFailure(call: Call<TreatmentResponse>, t: Throwable) {

                }
            })



           /* val physiotherapist = Physiotherapist("1", "Roberto","Loza","Perez","45","4","Lima",
                "","04/05/1994","20","Neck","roberto@email.com","2")
            val firstPatient: Treatment = treatments.value.firstOrNull() ?: Treatment("1","asdadad Spine","asda may include nonsteroidal, anti-inflammatory medicines that relieve pain and swelling, and steroid injections that reduce swelling. Surgical treatments include removing bone spurs and widening the space between vertebrae. The lower back may also be stabilized by fusing together some of the vertebrae.","https://post.healthline.com/wp-content/uploads/2020/08/642x361-Treating_Spinal_Stenosis-Exercise_Surgery_and_More.jpg","25",physiotherapist)
            */
            Treatments(treatments.value)

        }
    }
}