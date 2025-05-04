    package com.jj.shore.data

    import android.accounts.Account
    import android.content.Context
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.firestore.FirebaseFirestore
    import com.jj.shore.data.auth.AuthRemoteDataSource
    import com.jj.shore.data.auth.AuthRepository
    import com.jj.shore.data.task.TaskRemoteDataSource
    import com.jj.shore.data.task.TaskRepository

    /**
     * REFERENCES
     *
     * https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app/blob/e0773b718f2670e401c039ee965879c5e88ca424/app/src/main/java/com/example/inventory/data/AppContainer.kt
     */

    /**
     * AppContainer Interface
     */
    interface AppContainer {
        val firestore: FirebaseFirestore
        val authRepository: AuthRepository
        val taskRepository: TaskRepository
    }

    /**
     *
     */
    class AppDataContainer(private val context: Context) : AppContainer {

        private val firebaseAuth: FirebaseAuth by lazy {
            FirebaseAuth.getInstance()
        }

        private val authRemoteDataSource: AuthRemoteDataSource by lazy {
            AuthRemoteDataSource(firebaseAuth)
        }

        private val taskRemoteDataSource: TaskRemoteDataSource by lazy {
            TaskRemoteDataSource(firestore)
        }

        override val firestore: FirebaseFirestore by lazy {
            FirebaseFirestore.getInstance()
        }

        override val authRepository: AuthRepository by lazy {
            AuthRepository(authRemoteDataSource)
        }

        override val taskRepository: TaskRepository by lazy {
            TaskRepository(taskRemoteDataSource)
        }

    }
