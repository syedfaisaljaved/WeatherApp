//package com.weatherapp.data.api
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.weatherapp.util.AppExecuters
//
//
//class GithubApiClient private constructor() {
//    //public repo list
//    private val mPublicRepoList: MutableLiveData<MutableList<GithubPublicRepo?>?>?
//    private var retrievePublicRepoListRunnable: RetrievePublicRepoRunnable? = null
//
//    //repo contributers
//    private val mContributerList: MutableLiveData<MutableList<Owner?>?>?
//    private var retrieveContributorRunnable: RetrieveContributorRunnable? = null
//
//    //user repos
//    private val mUserRepoList: MutableLiveData<MutableList<GithubPublicRepo?>?>?
//    private var retrieveUserRepoRunnable: RetrieveUserRepoRunnable? = null
//    val publicRepoList: LiveData<MutableList<Any?>?>?
//        get() = mPublicRepoList
//
//    val userRepoList: LiveData<MutableList<Any?>?>?
//        get() = mUserRepoList
//
//    val contributorList: LiveData<MutableList<Any?>?>?
//        get() = mContributerList
//
//    fun searchPublicRepo() {
//        if (retrievePublicRepoListRunnable != null) {
//            retrievePublicRepoListRunnable = null
//        }
//        retrievePublicRepoListRunnable = RetrievePublicRepoRunnable()
//        AppExecuters.instance?.networkIO().submit(retrievePublicRepoListRunnable)
//    }
//
//    fun fetchUserRepo(username: String?) {
//        if (retrieveUserRepoRunnable != null) {
//            retrieveUserRepoRunnable = null
//        }
//        retrieveUserRepoRunnable = RetrieveUserRepoRunnable(username)
//        AppExecuters.getInstance().networkIO().submit(retrieveUserRepoRunnable)
//    }
//
//    fun fetchContributors(user: String?, repo: String?) {
//        if (retrieveContributorRunnable != null) {
//            retrieveContributorRunnable = null
//        }
//        retrieveContributorRunnable = RetrieveContributorRunnable(user, repo)
//        AppExecuters.getInstance().networkIO().submit(retrieveContributorRunnable)
//    }
//
//    private inner class RetrievePublicRepoRunnable : Runnable {
//        override fun run() {
//            try {
//                val response: Response<MutableList<GithubPublicRepo?>?> =
//                    publicRepo.execute()
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        var list: MutableList<GithubPublicRepo?> = ArrayList()
//                        for (i in 0..19) {
//                            list = response.body()
//                        }
//                        mPublicRepoList!!.postValue(list)
//                    }
//                } else {
//                    Log.d(
//                        TAG,
//                        "run: error " + response.errorBody().string()
//                    )
//                    mPublicRepoList!!.postValue(null)
//                }
//            } catch (e: IOException) {
//                e.printStackTrace()
//                mPublicRepoList!!.postValue(null)
//            }
//        }
//
//        private val publicRepo: Call<MutableList<GithubPublicRepo?>?>?
//            private get() = ServiceGenerator.getGithubApi().searchPublicRepositories()
//    }
//
//    private inner class RetrieveUserRepoRunnable(private val username: String?) :
//        Runnable {
//        override fun run() {
//            try {
//                val response: Response<MutableList<GithubPublicRepo?>?> =
//                    getUserRepo(username).execute()
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        val list: MutableList<GithubPublicRepo?> =
//                            ArrayList(response.body())
//                        mUserRepoList!!.postValue(list)
//                    }
//                } else {
//                    Log.d(
//                        TAG,
//                        "run: error " + response.errorBody().string()
//                    )
//                    mUserRepoList!!.postValue(null)
//                }
//            } catch (e: IOException) {
//                e.printStackTrace()
//                mUserRepoList!!.postValue(null)
//            }
//        }
//
//        private fun getUserRepo(username: String?): Call<MutableList<GithubPublicRepo?>?>? {
//            return ServiceGenerator.getGithubApi().getUserRepositories(username)
//        }
//
//    }
//
//    private inner class RetrieveContributorRunnable(
//        private val user: String?,
//        private val repo: String?
//    ) :
//        Runnable {
//        override fun run() {
//            try {
//                val response: Response<MutableList<Owner?>?> =
//                    fetchContributors(user, repo).execute()
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        val list: MutableList<Owner?> =
//                            ArrayList(response.body())
//                        mContributerList!!.postValue(list)
//                    }
//                } else {
//                    Log.d(
//                        TAG,
//                        "run: error " + response.errorBody().string()
//                    )
//                    mContributerList!!.postValue(null)
//                }
//            } catch (e: IOException) {
//                e.printStackTrace()
//                mContributerList!!.postValue(null)
//            }
//        }
//
//        private fun fetchContributors(
//            user: String?,
//            repo: String?
//        ): Call<MutableList<Owner?>?>? {
//            return ServiceGenerator.getGithubApi().getContributors(user, repo)
//        }
//
//    }
//
//    companion object {
//        private val TAG: String? = "GithubApiClient"
//        var instance: GithubApiClient? = null
//            get() {
//                if (field == null) {
//                    field = GithubApiClient()
//                }
//                return field
//            }
//            private set
//    }
//
//    init {
//        mPublicRepoList = MutableLiveData<MutableList<GithubPublicRepo?>?>()
//        mContributerList = MutableLiveData<MutableList<Owner?>?>()
//        mUserRepoList = MutableLiveData<MutableList<GithubPublicRepo?>?>()
//    }
//}