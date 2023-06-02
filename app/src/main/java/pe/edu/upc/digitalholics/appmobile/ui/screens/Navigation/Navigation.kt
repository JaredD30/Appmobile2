package pe.edu.upc.digitalholics.appmobile.ui.screens.Navigation

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.edu.upc.digitalholics.appmobile.data.model.Appointment
import pe.edu.upc.digitalholics.appmobile.data.model.Patient
import pe.edu.upc.digitalholics.appmobile.data.model.Physiotherapist
import pe.edu.upc.digitalholics.appmobile.data.model.Review
import pe.edu.upc.digitalholics.appmobile.data.model.Treatment
import pe.edu.upc.digitalholics.appmobile.data.remote.ApiClient
import pe.edu.upc.digitalholics.appmobile.data.remote.AppointmentResponse
import pe.edu.upc.digitalholics.appmobile.data.remote.PatientResponse
import pe.edu.upc.digitalholics.appmobile.data.remote.PhysiotherapistResponse
import pe.edu.upc.digitalholics.appmobile.data.remote.ReviewResponse
import pe.edu.upc.digitalholics.appmobile.data.remote.TreatmentResponse
import pe.edu.upc.digitalholics.appmobile.ui.screens.AddRiew.AddReview
import pe.edu.upc.digitalholics.appmobile.ui.screens.PatientProfile.PatientProfile
//import pe.edu.upc.digitalholics.appmobile.ui.srceens.PatientsDetails.Patient
import pe.edu.upc.digitalholics.appmobile.ui.screens.PatientsDetails.PatientDetails
import pe.edu.upc.digitalholics.appmobile.ui.screens.PhysiotherapistProfile.PhysiotherapistProfile
import pe.edu.upc.digitalholics.appmobile.ui.screens.PhysiotherapistsList.PhysiotherapistList
import pe.edu.upc.digitalholics.appmobile.ui.screens.TreatmentDetails.TreatmentDetails
import pe.edu.upc.digitalholics.appmobile.ui.screens.TreatmentList.Treatments
import pe.edu.upc.digitalholics.appmobile.ui.srceens.AppointmentList.AppointmentList
import pe.edu.upc.digitalholics.appmobile.ui.srceens.HomePatient.Home
import pe.edu.upc.digitalholics.appmobile.ui.srceens.HomePatient.HomePatient
import pe.edu.upc.digitalholics.appmobile.ui.srceens.Payment.Payment
import pe.edu.upc.digitalholics.appmobile.ui.srceens.Schedule.Schedule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "HomePatient") {



        composable("Payment/{index}/{topic}/{date}",
        arguments = listOf(navArgument("index") { type = NavType.StringType },
        navArgument("topic") {type = NavType.StringType},
            navArgument("date") {type = NavType.StringType},
            )
        ) {

            val index = it.arguments?.getString("index") as String
            val topic = it.arguments?.getString("topic") as String

            val date = it.arguments?.getString("date") as String

            val physiotherapist = remember {
                mutableStateOf(
                    Physiotherapist(
                        id = 0,
                        firstName = "",
                        paternalSurname = "",
                        maternalSurname = "",
                        age = 0,
                        rating = 0,
                        location = "",
                        photoUrl = "",
                        birthdayDate = "",
                        consultationsQuantity = 0,
                        specialization = "",
                        email = "",
                        userId = 0
                    )
                )
            }

            val driverInterface = ApiClient.buildPhysiotherapistInterface()
            val getDriver = driverInterface.getPhysiotherapistById(index.toInt())

            getDriver.enqueue(object : Callback<Physiotherapist> {
                override fun onResponse(call: Call<Physiotherapist>, response: Response<Physiotherapist>) {
                    if (response.isSuccessful) {
                        physiotherapist.value = response.body()!!
                    }
                }

                override fun onFailure(call: Call<Physiotherapist>, t: Throwable) {
                }
            })


            Payment(physiotherapist = physiotherapist.value,
                topic = topic, date = date,
            navController = navController)
        }

        composable("Schedule/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })) {

            val index = it.arguments?.getString("index") as String

            val physiotherapist = remember {
                mutableStateOf(
                    Physiotherapist(
                        id = 0,
                        firstName = "",
                        paternalSurname = "",
                        maternalSurname = "",
                        age = 0,
                        rating = 0,
                        location = "",
                        photoUrl = "",
                        birthdayDate = "",
                        consultationsQuantity = 0,
                        specialization = "",
                        email = "",
                        userId = 0
                    )
                )
            }

            val driverInterface = ApiClient.buildPhysiotherapistInterface()
            val getDriver = driverInterface.getPhysiotherapistById(index.toInt())

            getDriver.enqueue(object : Callback<Physiotherapist> {
                override fun onResponse(call: Call<Physiotherapist>, response: Response<Physiotherapist>) {
                    if (response.isSuccessful) {
                        physiotherapist.value = response.body()!!
                    }
                }

                override fun onFailure(call: Call<Physiotherapist>, t: Throwable) {
                }
            })


            Schedule(
                physiotherapist = physiotherapist.value,
                navController = navController
            )

        }
        composable(
            "patient/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) {
            val index = it.arguments?.getString("index") as String

            val patients = remember {
                mutableStateOf(
                    Patient(
                        0,
                        0,
                        " ",
                        "20",
                        0,
                        "jose@gmail.com",
                        0,
                        "https://img.europapress.es/fotoweb/fotonoticia_20081004164743_420.jpg",
                        ""
                    )
                )
            }

            val driverInterface = ApiClient.buildPatientInterface()
            val getDriver = driverInterface.getPatientById(index)

            getDriver.enqueue(object : Callback<Patient> {
                override fun onResponse(call: Call<Patient>, response: Response<Patient>) {
                    if (response.isSuccessful) {
                        patients.value = response.body()!!
                    }
                }

                override fun onFailure(call: Call<Patient>, t: Throwable) {
                }
            })

            PatientDetails(patient = patients.value)
        }


        composable("AppointmentList") {

            val appointments = remember {
                //mutableStateOf(Appointment("1","Jose","Del Carpio","20","30","jose@gmail.com","2"))
                mutableStateOf(emptyList<Appointment>())
            }

            AppointmentList(
                //patients = patients.value,
                appointments = appointments.value,
                navController = navController
            )

            //AppointmentList(0, appointments = appointments.value)

            val appointmentInterface = ApiClient.buildAppointmentInterface()
            val getAllAppointment = appointmentInterface.getAllAppointments()

            getAllAppointment.enqueue(object : Callback<AppointmentResponse> {
                override fun onResponse(
                    call: Call<AppointmentResponse>,
                    response: Response<AppointmentResponse>
                ) {
                    if (response.isSuccessful) {
                        appointments.value = response.body()?.appointments!!

                    }
                }

                override fun onFailure(call: Call<AppointmentResponse>, t: Throwable) {
                }
            })
            //appointments.value. +=
        }

        //treatmentList
        composable("TreatmentList") {
            val treatments = remember {
                mutableStateOf(emptyList<Treatment>())
            }

            //PatientDetails(patient = patients.value)

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
            Treatments(
                treatments = treatments.value,
                selectTreatment = { index ->
                    navController.navigate("treatment/$index")
                },navController
            )
        }

        //treatmentDetail
        composable(
            "treatment/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) {
            val index = it.arguments?.getString("index") as String
            val physiotherapist = Physiotherapist(1, "Roberto","Loza","Perez",45,4,"Lima",
                "","04/05/1994",20,"Neck","roberto@email.com",2)


            val treatment = remember {
                mutableStateOf(
                    Treatment(
                        " "," "," "," ","",physiotherapist
                    )
                )
            }

            val treatmentInterface = ApiClient.buildTreatmentInterface()
            val getTreatment = treatmentInterface.getTreatmentById(index)

            getTreatment.enqueue(object : Callback<Treatment> {
                override fun onResponse(call: Call<Treatment>, response: Response<Treatment>) {
                    if (response.isSuccessful) {
                        treatment.value = response.body()!!
                    }
                }

                override fun onFailure(call: Call<Treatment>, t: Throwable) {
                }
            })

            TreatmentDetails(treatment = treatment.value,navController)
        }

        composable("patient"){
            //val index = it.arguments?.getString("index") as String

            val patient = remember {
                mutableStateOf(
                    Patient(0,0,"","",0,"",0,"", " "
                    )
                )
            }

            val patientInterface = ApiClient.buildPatientInterface()
            val getPatient = patientInterface.getPatientById("1")

            getPatient.enqueue(object : Callback<Patient> {
                override fun onResponse(call: Call<Patient>, response: Response<Patient>) {
                    if (response.isSuccessful) {
                        patient.value = response.body()!!
                    }
                }

                override fun onFailure(call: Call<Patient>, t: Throwable) {
                }
            })

            PatientProfile(patient = patient.value,navController)

        }


        composable("physiotherapistList") {
            val physiotherapists = remember {
                mutableStateOf(emptyList<Physiotherapist>())
            }

            //PatientDetails(patient = patients.value)

            val physiotherapistInterface = ApiClient.buildPhysiotherapistInterface()
            val getAllPhysiotherapist = physiotherapistInterface.getAllPhysiotherapist()

            getAllPhysiotherapist.enqueue(object : Callback<PhysiotherapistResponse> {
                override fun onResponse(
                    call: Call<PhysiotherapistResponse>,
                    response: Response<PhysiotherapistResponse>
                ) {
                    if (response.isSuccessful) {
                        physiotherapists.value = response.body()?.physiotherapists!!

                    }
                }

                override fun onFailure(call: Call<PhysiotherapistResponse>, t: Throwable) {

                }
            })

            PhysiotherapistList(navController,
                physiotherapists = physiotherapists.value
            )
        }

        composable("physiotherapistProfile/{index}",  arguments = listOf(navArgument("index") { type = NavType.StringType })){
            val index = it.arguments?.getString("index") as String

            val physiotherapist = remember {
                mutableStateOf(
                    Physiotherapist(
                        0,
                        " ",
                        " ",
                        " ",
                        0,
                        0,
                        " ",
                        "",
                        "",
                        0,
                        " ",
                        " ",
                        0
                    )
                )
            }

            val reviews = remember{
                mutableStateOf(emptyList<Review>())
            }

            val physiotherapistInterface = ApiClient.buildPhysiotherapistInterface()
            val getPhysiotherapist = physiotherapistInterface.getPhysiotherapistById(index.toInt())

            val reviewInterface = ApiClient.buildReviewInterface()
            val getReviews = reviewInterface.getAllReviews()

            getPhysiotherapist.enqueue(object : Callback<Physiotherapist> {
                override fun onResponse(call: Call<Physiotherapist>, response: Response<Physiotherapist>) {
                    if (response.isSuccessful) {
                        physiotherapist.value = response.body()!!
                    }
                }

                override fun onFailure(call: Call<Physiotherapist>, t: Throwable) {
                }
            })

            getReviews.enqueue(object : Callback<ReviewResponse> {
                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if (response.isSuccessful) {
                        reviews.value = response.body()?.reviews!!

                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {

                }
            })

            PhysiotherapistProfile(physiotherapist.value,reviews.value,navController)

        }

        composable("addReview/{physiotherapistId}", arguments = listOf(navArgument("physiotherapistId") { type = NavType.StringType })){
            val physiotherapistId = it.arguments?.getString("physiotherapistId") as String
            val physiotherapist = remember {
                mutableStateOf(
                    Physiotherapist(
                        0,
                        " ",
                        " ",
                        " ",
                        0,
                        0,
                        " ",
                        "",
                        "",
                        0,
                        " ",
                        " ",
                        0
                    )
                )
            }

            val reviews = remember{
                mutableStateOf(emptyList<Review>())
            }

            val physiotherapistInterface = ApiClient.buildPhysiotherapistInterface()
            val getPhysiotherapist = physiotherapistInterface.getPhysiotherapistById(physiotherapistId.toInt())

            val reviewInterface = ApiClient.buildReviewInterface()
            val getReviews = reviewInterface.getAllReviews()

            getPhysiotherapist.enqueue(object : Callback<Physiotherapist> {
                override fun onResponse(call: Call<Physiotherapist>, response: Response<Physiotherapist>) {
                    if (response.isSuccessful) {
                        physiotherapist.value = response.body()!!
                    }
                }

                override fun onFailure(call: Call<Physiotherapist>, t: Throwable) {
                }
            })

            getReviews.enqueue(object : Callback<ReviewResponse> {
                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if (response.isSuccessful) {
                        reviews.value = response.body()?.reviews!!

                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {

                }
            })
            AddReview(physiotherapist = physiotherapist.value, reviews =reviews.value , navController =navController )

        }

        composable("HomePatient"){
            val context = LocalContext.current
            val sharedPreferences = remember {
                context.getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
            }
            val editor = sharedPreferences.edit()
            editor.putString("userLogged", "1")
            editor.apply()

//            val patients = remember {
//                mutableStateOf(emptyList<Patient>())
//            }
            val treatments = remember {
                //mutableStateOf(Patient("1","Jose","Del Carpio","20","30","jose@gmail.com","2","https://img.europapress.es/fotoweb/fotonoticia_20081004164743_420.jpg"))
                mutableStateOf(emptyList<Treatment>())
            }
           // val patientInterface = ApiClient.buildPatientInterface()
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

//            val index = it.arguments?.getString("index") as String

            val patients = remember {
                mutableStateOf(
                    Patient(
                        1,
                        1,
                        "Jose",
                        "Del Carpio",
                        20,
                        "jose@gmail.com",
                        30,
                        "https://img.europapress.es/fotoweb/fotonoticia_20081004164743_420.jpg",
                        "2023-07-25"
                    )
                )
            }

            val driverInterface = ApiClient.buildPatientInterface()
            val getDriver = driverInterface.getPatientById("1")

            getDriver.enqueue(object : Callback<Patient> {
                override fun onResponse(call: Call<Patient>, response: Response<Patient>) {
                    if (response.isSuccessful) {
                        patients.value = response.body()!!
                    }
                }

                override fun onFailure(call: Call<Patient>, t: Throwable) {
                }
            })
//            HomePatient(
//               treatment = treatments.value
//            )

            val physiotherapists = remember {
                mutableStateOf(emptyList<Physiotherapist>())
            }

            //PatientDetails(patient = patients.value)

            val physiotherapist = remember {
                mutableStateOf(
                    Physiotherapist(
                        id = 0,
                        firstName = "",
                        paternalSurname = "",
                        maternalSurname = "",
                        age = 0,
                        rating = 0,
                        location = "",
                        photoUrl = "",
                        birthdayDate = "",
                        consultationsQuantity = 0,
                        specialization = "",
                        email = "",
                        userId = 0
                    )
                )
            }

            val physiotherapistInterface = ApiClient.buildPhysiotherapistInterface()
            val getPhysiotherapistById = physiotherapistInterface.getPhysiotherapistById(1)

            getPhysiotherapistById.enqueue(object : Callback<Physiotherapist> {
                override fun onResponse(call: Call<Physiotherapist>, response: Response<Physiotherapist>){
                    if (response.isSuccessful) {
                        physiotherapist.value = response.body()!!

                    }
                }

                override fun onFailure(call: Call<Physiotherapist>, t: Throwable) {

                }
            })

//            getDriver.enqueue(object : Callback<Physiotherapist> {
//                override fun onResponse(call: Call<Physiotherapist>, response: Response<Physiotherapist>) {
//                    if (response.isSuccessful) {
//                        physiotherapist.value = response.body()!!
//                    }
//                }
//
//                override fun onFailure(call: Call<Physiotherapist>, t: Throwable) {
//                }
//            })

//            getDriver.enqueue(object : Callback<Patient> {
//                override fun onResponse(call: Call<Patient>, response: Response<Patient>) {
//                    if (response.isSuccessful) {
//                        patients.value = response.body()!!
//                    }
//                }
//
//                override fun onFailure(call: Call<Patient>, t: Throwable) {
//                }
//            })

            Home(
                treatments = treatments.value,
                selectTreatment = { index ->
                    navController.navigate("treatment/$index")
                },
                patient = patients.value,
                physiotherapist = physiotherapist.value,
                navController = navController
            )

        }
    }
}