package com.example.ubi.login.contact

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.example.ubi.databinding.FragmentContactBinding


class ContactFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private lateinit var mLoader: Any
    private var forSendServer = arrayListOf<String>()
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!


    private val DETAILS_QUERY_ID: Int = 0
    @SuppressLint("InlinedApi")
    private val PROJECTION: Array<out String> = arrayOf(
        Contacts._ID,
        Contacts.LOOKUP_KEY,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            Contacts.DISPLAY_NAME_PRIMARY
        else
            Contacts.DISPLAY_NAME
    )


    val TAG = "ContactFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        loaderManager.initLoader(DETAILS_QUERY_ID, null, this)



        return binding.root
    }
    override fun onCreateLoader(loaderId: Int, args: Bundle?): Loader<Cursor> {
        mLoader = when(loaderId) {
            DETAILS_QUERY_ID -> {
                activity?.let {
                    CursorLoader(
                        it,
                        ContactsContract.Data.CONTENT_URI,
                        PROJECTION,
                        null,
                        null,
                        null
                    )
                }
            }

            else -> {
                Log.d(TAG, "test222122222")
            }
        }!!
        return mLoader as CursorLoader
    }


    override fun onLoaderReset(loader: Loader<Cursor>) {
        when (loader.id) {
            DETAILS_QUERY_ID -> {

                /*
                 * If you have current references to the Cursor,
                 * remove them here.
                 */
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        when(loader.id) {
            DETAILS_QUERY_ID -> {
                data!!.use { cursor ->
                    cursor.moveToFirst()
                    while (cursor.moveToNext()){
                        if(cursor.isLast){
                            break
                        }
                        val phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        val phoneNumber = cursor.getString(phoneIndex)
                        if (phoneNumber != null){
                            if(phoneNumber.contains("010")){
                                Log.d(TAG, phoneNumber)
                                forSendServer.add(phoneNumber)
                            }
                        }
                    }
                    Log.d(TAG, "$forSendServer")
                }

            }
        }

    }


}